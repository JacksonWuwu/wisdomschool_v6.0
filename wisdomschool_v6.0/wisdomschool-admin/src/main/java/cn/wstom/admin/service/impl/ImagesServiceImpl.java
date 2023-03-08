package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.ImagesService;
import cn.wstom.common.constant.StorageConstants;
import cn.wstom.common.utils.FileOperateUtils;
import cn.wstom.common.utils.Md5Utils;
import cn.wstom.common.utils.ScaleImageUtils;
import cn.wstom.common.utils.StringHelperUtils;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片管理服务
 */
@Service
public class ImagesServiceImpl extends BaseServiceImpl<ImagesMapper, Images>
        implements ImagesService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Pattern pRemoteFileUrl = Pattern.compile("<img.*?src=\"?(.*?)(\"|>|\\s+)");
    private final SysUserService userService;

    @Resource
    private ImagesMapper imagesMapper;

    @Autowired
    public ImagesServiceImpl(SysUserService userService) {
        this.userService = userService;
    }

    // ///////////////////////////////
    // ///// 增加 ////////
    // ///////////////////////////////

    /**
     * 保存内容中的图片本地化路径处理
     *
     * @param type    信息类型，0问题，1答案，2文章，3分享
     * @param infoId  信息id
     * @param userId  用户id
     * @param content 需要分析处理并下载的内容
     * @return
     * @throws Exception
     */
    @Override
    public String replaceContent(Integer type, String infoId, String userId, String content) throws Exception {
        Matcher mRemoteFileUrl = pRemoteFileUrl.matcher(content);
        StringBuffer sb = new StringBuffer();
        String remoteFileUrl;
        String imgPath = getImgPath();
        StringBuilder imgBuffer = new StringBuilder();
        int nFileNum = 0;
        while (mRemoteFileUrl.find()) {
            remoteFileUrl = mRemoteFileUrl.group(1);
            String extension = StringHelperUtils.getImageUrlSuffix(remoteFileUrl);
            extension = "." + extension;
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = Md5Utils.code(df.format(new Date()) + nFileNum, 16) + "_" + nFileNum + extension;
            String reg = "(?!.*((img.baidu.com)|(127.0.0.1)|(^/upload/content/))).*$";
            String pathac = "";
            if (remoteFileUrl.matches(reg)) {
                saveUrlAs(remoteFileUrl, StorageConstants.UPLOAD_PATH + imgPath + filename);
                pathac = imgPath + filename;
                mRemoteFileUrl.appendReplacement(sb, "<img src=\"" + pathac + "\" ");
                if (imgBuffer.toString().length() < 1) {
                    imgBuffer.append(imgPath + filename);
                } else {
                    imgBuffer.append(";").append(imgPath + filename);
                }
                nFileNum = nFileNum + 1;
            } else {
                if (getContentUrl(remoteFileUrl)) {
                    if (FileOperateUtils.isFile(StorageConstants.UPLOAD_PATH + "/" + StringHelperUtils.getImageRootUrl(remoteFileUrl))) {  //判断文件是否存在，不存在则不执行
                        FileOperateUtils.moveFile(StorageConstants.UPLOAD_PATH + "/" + StringHelperUtils.getImageRootUrl(remoteFileUrl), StorageConstants.UPLOAD_PATH + imgPath + filename);//开始移动文件
                    }
                    pathac = imgPath + filename;
                    mRemoteFileUrl.appendReplacement(sb, "<img src=\"" + pathac + "\" ");
                }

                nFileNum = nFileNum + 1;
            }
            String pictureUrl = null;
            if (remoteFileUrl.matches(reg)) {
                pictureUrl = imgPath + filename;
            } else {
                pictureUrl = remoteFileUrl;
            }
            Images imaData = this.findImagesByImgurl(pictureUrl);
            if (imaData == null) {
                File picture = new File(StorageConstants.UPLOAD_PATH + imgPath + filename);
                FileInputStream fis = new FileInputStream(picture);
                BufferedImage sourceImg = ImageIO.read(fis);
                Images images = new Images();
                images.setImgUrl(imgPath + filename);
                images.setFileSize(String.format("%.1f", picture.length() / 1024.0));
                images.setImgWidth(Integer.toString(sourceImg.getWidth()));
                images.setImgHeight(Integer.toString(sourceImg.getHeight()));
                images.setSort(nFileNum);
                images.setCreateTime(new Date());
                images.setInfoCount(1);
                int totalCount = imagesMapper.addImages(images);
                if (totalCount > 0) {
                    ImagesInfoMerge merge = new ImagesInfoMerge();
                    merge.setInfoType(type);
                    merge.setInfoId(infoId);
                    merge.setImgId(images.getId());
                    merge.setUserId(userId);
                    imagesMapper.addImagesInfoMerge(merge);
                }
                fis.close();
            } else {
                ImagesInfoMerge merge = new ImagesInfoMerge();
                merge.setInfoType(type);
                merge.setInfoId(infoId);
                merge.setImgId(imaData.getId());
                merge.setUserId(userId);
                imagesMapper.addImagesInfoMerge(merge);
                imagesMapper.updateImagesCount(imaData.getId());
            }

        }
        mRemoteFileUrl.appendTail(sb);
        return sb.toString();
    }

    @Override
    public boolean saveAvatarDataFile(SysUser user, BufferedImage image) throws Exception {
        try {
            String savePath = StorageConstants.UPLOAD_PATH + "/upload/";
            //文件保存目录URL
            String saveUrl = "/upload/";
            //创建文件夹
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String ymds = sdf.format(user.getCreateTime());
            savePath += "avatar/" + ymds + "/" + user.getId() + "/";
            saveUrl += "avatar/" + ymds + "/" + user.getId() + "/";
            File dirFile = new File(savePath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            String fileName = "avatar.jpg";
            File file = new File(savePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            ImageIO.write(image, "PNG", file);

            return userService.updateAvatar(user.getId(), saveUrl + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    // ///////////////////////////////
    // ///// 刪除 ////////
    // ///////////////////////////////

    /**
     * 按图片索引ID删除图片信息
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteImagesById(String id) {
        int totalCount = imagesMapper.deleteImagesById(id);
        return totalCount > 0;
    }

    /**
     * 按图片信息id和图片指纹删除图片信息
     *
     * @param tid    信息id
     * @param imgurl 图片地址
     * @return
     */
    @Override
    public boolean deleteImagesByTidAndImgurl(String tid, String imgurl) {
        int totalCount = imagesMapper.deleteImagesByTidAndImgurl(tid, imgurl);
        return totalCount > 0;
    }

    /**
     * 按信息分类和内容id删除图片信息
     *
     * @param channelid  信息分类id
     * @param article_id 内容id
     * @return
     */
    @Override
    public boolean deleteImagesByTid(String channelid, String article_id) {
        List<Images> imglist = imagesMapper.getImagesListByTid(article_id);
        if (imglist.size() > 0) {//未做排除有其他内容内有本文中的图片
            for (Images list : imglist) {
                FileOperateUtils.delFileA(StorageConstants.UPLOAD_PATH + list.getImgUrl()); //删除内容中图片
            }
        }
        int totalCount = imagesMapper.deleteImagesByTid(channelid, article_id);
        return totalCount > 0;
    }

    /**
     * 删除图片文件和图片数据
     *
     * @param tid    信息id
     * @param imgurl 图片地址
     * @return
     */
    @Override
    public boolean delImagesByDateAndFile(String tid, String imgurl) {
        FileOperateUtils.delFileA(StorageConstants.UPLOAD_PATH + imgurl); //删除内容中图片
        int totalCount = imagesMapper.deleteImagesByTidAndImgurl(tid, imgurl);
        return totalCount > 0;
    }
    // ///////////////////////////////
    // ///// 修改 ////////
    // ///////////////////////////////


    // ///////////////////////////////
    // ///// 查询 ////////
    // ///////////////////////////////

    /**
     * 按信息类型id和信息id查询第一个文章图片
     *
     * @param imgUrl
     * @return
     */
    @Override
    public Images findImagesByImgurl(String imgUrl) {
        return imagesMapper.findImagesByImgurl(imgUrl);
    }

    /**
     * 用信息id和图片地址查询该图片是否存在
     *
     * @param imgurl 图片指纹
     * @return
     */
    @Override
    public boolean checkImagesByTidAndImgurl(String tid, String imgurl) {
        int totalCount = imagesMapper.checkImagesByTidAndImgurl(tid, imgurl);
        return totalCount > 0;
    }

    /**
     * 查询图片路径是否存在
     *
     * @param imgUrl 图片地址
     * @return
     */
    @Override
    public boolean checkImagesByImgurl(String imgUrl) {
        int totalCount = imagesMapper.checkImagesByImgurl(imgUrl);
        return totalCount > 0;
    }

    /**
     * 按信息id查询所有关联图片的数量
     *
     * @param channelId 信息类别id
     * @param imgWidth  图片宽度
     * @param imgHeight 图片高度
     * @return
     */
    @Override
    public int getImagesByTidListCount(String channelId, Integer imgWidth, Integer imgHeight) {
        return imagesMapper.getImagesByTidListCount(channelId, imgWidth, imgHeight);
    }


    /**
     * 按信息id查询所有关联的图片
     *
     * @param channelId 信息类别id
     * @param imgWidth  图片宽度
     * @param imgHeight 图片高度
     * @param pageNum   页码数
     * @param rows      查询记录条数
     * @return
     */
    @Override
    public PageVo<Question> getImagesByTidList(String channelId, Integer imgWidth, Integer imgHeight, Integer pageNum, Integer rows) {
        PageVo<Question> pageVo = new PageVo<Question>(pageNum);
        pageVo.setRows(rows);
        pageVo.setCount(getImagesByTidListCount(channelId, imgWidth, imgHeight));
        List<Question> imageslist = imagesMapper.getImagesByTidList(channelId, imgWidth, imgHeight, pageVo.getOffset(), pageVo.getRows());
        pageVo.setList(imageslist);
        return pageVo;
    }

    /**
     * 缩放图片服务处理
     *
     * @param width
     * @param height
     * @param savePath
     * @param targetURL
     * @return
     * @throws IOException
     */
    @Override
    public String thumbImages(Integer width, Integer height, String savePath, String targetURL) throws IOException {
        if (width > 0 && height > 0) {
            ScaleImageUtils.forcedResize(width, height, savePath, new File(targetURL));
        } else {
            if (width > 0) {
                ScaleImageUtils.resize(width, savePath, new File(targetURL));
            } else if (height > 0) {
                ScaleImageUtils.resizeByHeight(height, savePath, new File(targetURL));
            }
        }
        return savePath;
    }


    /**
     * 更新内容时对已有的图片数据分析本地化路径处理
     *
     * @param content
     * @param savepath
     * @param accesspath
     * @param channelid
     * @param tid
     * @return
     * @throws Exception
     */
    @Override
    public String replaceContent(String content, String savepath, String accesspath, Integer channelid, Integer tid) throws Exception {
        String sitedirect = StorageConstants.UPLOAD_PATH;
        Pattern pRemoteFileurl = Pattern.compile("<img.*?src=\"?(.*?)(\"|>|\\s+)");
        Matcher mRemoteFileurl = pRemoteFileurl.matcher(content);
        StringBuffer sb = new StringBuffer();
        String remoteFileurl = null;
        int nFileNum = 0;
        //创建文件夹并返回根目录，如：“/upload/content/2018/8/20/4CAF5F732B9E4523_0.jpg”
        String imgpath = getImgPath();
        StringBuffer imgPath = new StringBuffer();
        String pathac = "";
        while (mRemoteFileurl.find()) {
            remoteFileurl = mRemoteFileurl.group(1);
            String extension = StringHelperUtils.getImageUrlSuffix(remoteFileurl);
            extension = "." + extension;
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String filename = Md5Utils.code(df.format(new Date()) + nFileNum, 16) + "_" + nFileNum + extension;
            String reg = "(?!.*((127.0.0.1)|(^/upload/content/))).*$";
            if (remoteFileurl.matches(reg)) {
                saveUrlAs(remoteFileurl, sitedirect + imgpath + filename);
                pathac = imgpath + filename;
                mRemoteFileurl.appendReplacement(sb, "<img src=\"" + pathac + "\" ");
                if (imgPath.toString().length() < 1) {
                    imgPath.append(imgpath + filename);
                } else {
                    imgPath.append(";").append(imgpath + filename);
                }
                nFileNum = nFileNum + 1;
            } else {
                if (getContentUrl(remoteFileurl)) {
                    FileOperateUtils.moveFile(sitedirect + StringHelperUtils.getImageRootUrl(remoteFileurl), sitedirect + imgpath + filename);
                    pathac = imgpath + filename;
                    mRemoteFileurl.appendReplacement(sb, "<img src=\"" + pathac + "\" ");
                }
            }
        }
        mRemoteFileurl.appendTail(sb);
        return sb.toString();
    }

    /**
     * 查询图片list内是否包含该图片路径
     *
     * @param name
     * @param list
     * @return
     */
    @Override
    public boolean listSearch(String name, List<Images> list) {
        for (int i = 0; i < list.size(); i++) {
            if (name.equals(list.get(i).getImgUrl())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断url是否是用户临时文件
     *
     * @param url
     * 需要判断的url
     * @return
     */
    final static Pattern p = Pattern.compile("/upload/usertmp/+[a-zA-Z0-9]+/",
            Pattern.CASE_INSENSITIVE);

    /*
     *
     * 内容下载图片保存路径设置
     */
    static String getImgPath() {
        String path = StorageConstants.UPLOAD_PATH,
                filepath = "/upload/content/" + Calendar.getInstance().get(Calendar.YEAR)
                        + "/" + (1 + Calendar.getInstance().get(Calendar.MONTH)) + "/"
                        + (Calendar.getInstance().get(Calendar.DATE)) + "/";

        File file = new File(path + filepath);
        // 如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        return filepath;
    }

    static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * @param fileUrl  文件来源地址
     * @param savePath 文件保存地址
     * @return
     */
    static boolean saveUrlAs(String fileUrl, String savePath) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0) {
                out.write(buffer, 0, count);
            }
            out.close();
            in.close();
            connection.disconnect();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    static boolean getContentUrl(String url) {
        Matcher matcher = ImagesServiceImpl.p.matcher(url);
        return matcher.find();
    }

    static void main(String[] args) {
        System.out.println(getContentUrl("/upload/content/2019/1/24/30EC1B7906CBFC8B_0.png"));


    }
}
