package cn.wstom.admin.service;



import cn.wstom.admin.entity.Images;
import cn.wstom.admin.entity.PageVo;
import cn.wstom.admin.entity.Question;
import cn.wstom.admin.entity.SysUser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface ImagesService extends BaseService<Images> {

    String replaceContent(Integer type, String infoId, String userId, String content) throws Exception;

    boolean saveAvatarDataFile(SysUser user, BufferedImage image) throws Exception;

    boolean deleteImagesById(String id);

    boolean deleteImagesByTidAndImgurl(String tid, String imgurl);

    boolean deleteImagesByTid(String channelid, String article_id);

    boolean delImagesByDateAndFile(String tid, String imgurl);

    Images findImagesByImgurl(String imgUrl);

    boolean checkImagesByTidAndImgurl(String tid, String imgurl);

    boolean checkImagesByImgurl(String imgUrl);

    int getImagesByTidListCount(String channelId, Integer imgWidth, Integer imgHeight);

    PageVo<Question> getImagesByTidList(String channelId, Integer imgWidth, Integer imgHeight, Integer pageNum, Integer rows);

    String thumbImages(Integer width, Integer height, String savePath, String targetURL) throws IOException;

    String replaceContent(String content, String savepath, String accesspath, Integer channelid, Integer tid) throws Exception;

    boolean listSearch(String name, List<Images> list);
}
