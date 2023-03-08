package cn.wstom.storage.server.service.impl;

import cn.wstom.storage.exception.ApplicationException;
import cn.wstom.storage.server.enumeration.Constants;
import cn.wstom.storage.server.enumeration.StorageConstants;
import cn.wstom.storage.server.mapper.FolderMapper;
import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Folder;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.pojo.SysUser;
import cn.wstom.storage.server.service.FileService;
import cn.wstom.storage.server.service.FolderService;
import cn.wstom.storage.server.util.*;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <h2>文件服务功能实现类</h2>
 * <p>
 * 该类负责对文件相关的服务进行实现操作，例如下载和上传等，各方法功能详见接口定义。
 * </p>
 *
 * @author dws
 * @version 1.0
 * @see FileService
 */
@Service
public class FileServiceImpl extends RangeFileStreamWriter implements FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FolderService folderService;

    @Resource
    private NodeMapper nodeMapper;
    @Resource
    private FolderMapper folderMapper;


    private static String CONTENT_TYPE = "application/octet-stream";
    @Autowired
    public FileServiceImpl(FolderService folderService) {
        this.folderService = folderService;
    }
    @Value("${storage.storage-path}")
    private String AdjunctURl ;

    @Override
    public boolean checkUploadFile(String folderId, List<String> nameList) throws ApplicationException {

        List<String> duplicateFileNameList = new ArrayList<>();
        // 查找目标目录下是否存在与待上传文件同名的文件（或文件夹），如果有，记录在上方的列表中
        for (String fileName : nameList) {
            if (folderId == null || folderId.length() <= 0 || fileName == null || fileName.length() <= 0) {
                throw new ApplicationException("文件名不能为空!");
            }
            List<Node> files = this.nodeMapper.queryByParentFolderId(folderId);
            if (files.stream().parallel().anyMatch((n) -> n.getFileName()
                    .equals(new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8)))) {
                duplicateFileNameList.add(fileName);
            }
            LOGGER.info("checkUploadFile: " + fileName);
        }
        // 如果存在同名文件，返回同名文件的JSON数据，否则直接允许上传
        if (duplicateFileNameList.size() > 0) {
            Map<String, Object> params = new HashMap<>(1);
            params.put("duplicate", duplicateFileNameList);
            throw new ApplicationException("已存在同名文件!", params);
        }
        return true;
    }

    /**
     * 执行上传操作，接收文件并存入文件节点
     *
     * @param file 上传的文件
     * @return 上传结果、成功返回存储的文件id
     */
    @Override
    public String save(SysUser user, String folderId, MultipartFile file, String originalFileName) throws FileUploadException, ApplicationException {
        if (file == null || file.isEmpty()) {
            return "upload file must not empty!";
        }
        String fileName = originalFileName;
        // 再次检查上传文件名与目标目录ID
        if (StringUtil.isEmpty(originalFileName) || StringUtil.isEmpty(folderId)) {
            throw new FileUploadException("文件名或目录异常");
        }

        // 检查是否存在同名文件。不存在：直接存入新节点；存在：保留两者。
        List<Node> files = this.nodeMapper.queryByParentFolderId(folderId);
        if (files.parallelStream().anyMatch((e) -> e.getFileName().equals(originalFileName))) {
            // 针对存在同名文件的操作
            // 保留两者，使用型如“xxxxx (n).xx”的形式命名新文件。其中n为计数，例如已经存在2个文件，则新文件的n记为2
            // 设置新文件名为标号形式
            fileName = FileNodeUtil.getNewNodeName(originalFileName, files);
        }
        // 将文件存入节点并获取其存入生成路径，型如“UUID.block”形式。
        String path = FileBlockUtil.saveToFileBlocks(file);
        if (path == null) {
            throw new ApplicationException("path must not be null!");
        }
        if (path.equals("defeated")){
            throw new FileUploadException("请转成H264编码的MP4");
        }

        String fileId = UUID.randomUUID().toString();

        Node node = new Node();
        node.setFileId(fileId);
        node.setFileCreator(user.getLoginName());
        node.setFileCreationDate(ServerTimeUtil.accurateToDay());
        node.setFileName(fileName);
        node.setFileParentFolder(folderId);
        node.setFilePath(path);
        String fileSize = FileBlockUtil.getFileSize(file);
        node.setFileSize(fileSize);
        if (this.nodeMapper.insert(node) > 0) {
            return fileId;
        }
        return null;
    }
    /**
     * 删除单个文件，该功能与删除多个文件重复，计划合并二者
     *
     * @param fileId 文件id
     * @return 删除结果
     */
    @Override
    public boolean deleteFile(String fileId) throws ApplicationException {

        if (fileId == null || fileId.length() <= 0) {
            throw new ApplicationException("请选择要操作的文件！");
        }
        // 确认要删除的文件存在
        Node file = this.nodeMapper.queryById(fileId);
        if (file == null) {
            throw new ApplicationException("该文件不存在，请检查后再试");
        }
        // 从文件块删除 && 从节点删除
        if (!FileBlockUtil.deleteFromFileBlocks(file)) {
            throw new ApplicationException("删除该文件失败！");
        }
        if (nodeMapper.deleteById(fileId) <= 0) {
            throw new ApplicationException("删除该文件失败！");
        }
        return true;
    }

    @Override
    public boolean deleteAdjunct(HttpServletRequest request,HttpServletResponse response) throws Exception {

        String fileName=request.getParameter("fileName");
        String fileId=request.getParameter("fileId");
        String uid =request.getParameter("uid");
        try{
            int pos = fileName.lastIndexOf(Constants.SEPARATOR_DOT);
            String path = "file_" + uid + "." + fileName.substring(pos + 1);
            String url=AdjunctURl+"\\"+fileId+"/";
            String fileBlocks = url;
            File file = new File(fileBlocks,path);
            file.delete();
            return true;
        }catch (Exception e){
            return false;
        }



    }

    /**
     * 普通下载：下载单个文件
     *
     * @param request 下载文件请求参数
     * @param response 文件下载响应结果
     */
    @Override
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        // 找到要下载的文件节点
        String fileId = request.getParameter("fileId");
        if (fileId != null) {
            Node f = this.nodeMapper.queryById(fileId);
            if (f != null) {
                // 执行写出
                File fo = FileBlockUtil.getFileFromBlocks(f);
                writeRangeFileStream(request, response, fo, f.getFileName(), CONTENT_TYPE);
                // 日志记录（仅针对一次下载）
                if (request.getHeader("Range") == null) {
                    // TODO: 2019/2/23 记录日志
                    LOGGER.info("下载文件【" + f.getFileName() + "】");
                }
                return;
            }
        }
        try {
            //  处理无法下载的资源
            response.sendError(404);
        } catch (IOException e) {
            throw new ApplicationException("下载资源异常", e);
        }
    }

    @Override
    public void downloadadjunct(HttpServletRequest request,HttpServletResponse response) throws ApplicationException {

        String fileId=request.getParameter("fileId");
        String uid=request.getParameter("uid");
        String fileName=request.getParameter("fileName");
        if (fileId != null && uid!=null) {

            int pos = fileName.lastIndexOf(Constants.SEPARATOR_DOT);
            String path = "file_" + uid + "." + fileName.substring(pos + 1);
            String url=AdjunctURl+"\\"+fileId+"/";
            String fileBlocks = url;
            File fo = new File(fileBlocks, path);
            writeRangeFileStream(request, response, fo, path, CONTENT_TYPE);
            // 日志记录（仅针对一次下载）
            if (request.getHeader("Range") == null) {
                // TODO: 2019/2/23 记录日志
                LOGGER.info("下载文件【" + fileName + "】");
            }
            return;
        }
        try {
            //  处理无法下载的资源
            response.sendError(404);
        } catch (IOException e) {
            throw new ApplicationException("下载资源异常", e);
        }
    }

    @Override
    public void downloadFileTwo(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {

        try {
            String path = URLDecoder.decode(request.getParameter("Filename"), "utf-8");
        if (path != null) {
            // 执行写出
            path=AdjunctURl+"\\"+path;
            System.out.println("zip-path" + path);
            File f = new File(path);
            writeRangeFileStream(request, response, f, f.getName(), CONTENT_TYPE);
            // 日志记录（仅针对一次下载）
            if (request.getHeader("Range") == null) {
                // TODO: 2019/2/23 记录日志
                LOGGER.info("下载文件【" + f.getName() + "】");
            }
            return;

        }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            //  处理无法下载的资源
            response.sendError(404);
        } catch (IOException e) {
            throw new ApplicationException("下载资源异常", e);
        }
    }

    /**
     * 重命名文件
     * @param fileId 文件id
     * @param newFileName 新文件名
     * @return 操作姐u哦
     */
    @Override
    public boolean renameFile(String fileId, String newFileName) throws ApplicationException {

        // 参数检查
        if (fileId == null || "".equals(fileId) || newFileName == null || "".equals(newFileName)) {
            throw new ApplicationException("请选择需要更名的文件!");
        }
        if (!TextFormatUtil.instance().matcherFileName(newFileName) || newFileName.indexOf(Constants.SEPARATOR_DOT) == 0) {
            throw new ApplicationException("文件名不合法！");
        }
        Node file = this.nodeMapper.queryById(fileId);
        if (file == null) {
            throw new ApplicationException("该文件不存在！请检查再试");
        }
        if (!file.getFileName().equals(newFileName)) {
            // 不允许重名
            if (nodeMapper.queryBySomeFolder(fileId).parallelStream().anyMatch((e) -> e.getFileName().equals(newFileName))) {
                throw new ApplicationException("已存在同名文件!");
            }
            // 更新文件名
            Map<String, String> map = new HashMap<>(2);
            map.put("fileId", fileId);
            map.put("newFileName", newFileName);
            if (this.nodeMapper.updateFileNameById(map) == 0) {
                // 并写入日志
                throw new ApplicationException("重命名失败!");
            }
        }
        // TODO: 2019/2/23 记录日志
        return true;
    }

    @Override
    public boolean deleteBatchFiles(List<String> fileIdList, List<String> folderIdList) throws ApplicationException {

        // 对每个要删除的文件节点进行确认并删除
        for (String fileId : fileIdList) {
            if (fileId == null || fileId.length() <= 0) {
                throw new ApplicationException("请选择需要删除的文件!");
            }
            Node file = this.nodeMapper.queryById(fileId);
            if (file == null) {
                return true;
            }
            // 删除文件块
            if (!FileBlockUtil.deleteFromFileBlocks(file) && this.nodeMapper.deleteById(fileId) <= 0) {
                throw new ApplicationException("删除文件【" + file.getFileName() + "】失败，请稍后再试！");
            }
            // 日志记录
            // TODO: 2019/2/23 记录日志
        }
        // 删完选中的文件，再去删文件夹
        for (String fid : folderIdList) {
            if (folderService.deleteAllChildFolder(fid) <= 0) {
                Folder folder = folderMapper.queryById(fid);
                throw new ApplicationException("删除文件【" + folder.getFolderName() + "】失败，请稍后再试！");
            }
            // TODO: 2019/2/23 记录日志
        }
        return true;
    }

    @Override
    public String getDownloadFiles(List<String> fileIdList, List<String> folderIdList, SysUser user) throws ApplicationException {

        String loginName = user.getLoginName();

        // 获得要打包下载的文件ID

        // 创建ZIP压缩包并将全部文件压缩
        if (fileIdList.size() > 0 || folderIdList.size() > 0) {
            // TODO: 2019/2/23 记录日志
            // 返回生成的压缩包路径
            return FileBlockUtil.createZip(fileIdList, folderIdList, loginName);
        }
        throw new ApplicationException("请选择要下载的文件");
    }

    @Override
    public boolean downloadFilesZip(HttpServletRequest request, HttpServletResponse response) {
        String zipId = request.getParameter("zipId");
        if (zipId != null && !"".equals(zipId)) {
            String tfPath = ConfigureReader.instance().getTemporaryfilePath();
            File zip = new File(tfPath, zipId);
            String fName = "wstom_" + ServerTimeUtil.accurateToDay() + "_\u6253\u5305\u4e0b\u8f7d.zip";
            if (zip.exists()) {
                writeRangeFileStream(request, response, zip, fName, CONTENT_TYPE);
                return zip.delete();
            }
        }
        return false;
    }

    @Override
    public boolean downloadZip(HttpServletRequest request, HttpServletResponse response) {
        String zipName = null;
        try {
            zipName = URLDecoder.decode(request.getParameter("zipName"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (zipName != null && !"".equals(zipName)) {
            String Path = AdjunctURl+"\\"+zipName;;
            File zip = new File(Path);
            if (zip.exists()) {
                writeRangeFileStream(request, response, zip, zipName, CONTENT_TYPE);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getPackTime(List<String> fileIdList, List<String> folderIdList, SysUser user) {

        for (String fid : folderIdList) {
            countFolderFilesId(user.getLoginName(), fid, folderIdList);
        }
        long packTime = 0L;
        for (String fid : fileIdList) {
            Node n = this.nodeMapper.queryById(fid);
            File f = new File(ConfigureReader.instance().getFileBlockPath(), n.getFilePath());
            if (f.exists()) {
                packTime += f.length() / 25000000L;
            }
        }
        if (packTime < 4L) {
            return "\u9a6c\u4e0a\u5b8c\u6210";
        }
        if (packTime < 10L) {
            return "\u5927\u7ea610\u79d2";
        }
        if (packTime < 35L) {
            return "\u4e0d\u5230\u534a\u5206\u949f";
        }
        if (packTime < 65L) {
            return "\u5927\u7ea61\u5206\u949f";
        }
        return "\u8d85\u8fc7" + packTime / 60L
                + "\u5206\u949f\uff0c\u8017\u65f6\u8f83\u957f\uff0c\u5efa\u8bae\u76f4\u63a5\u4e0b\u8f7d";
    }

    /**
     * 用于迭代获得全部文件夹内的文件ID（方便预测耗时）
     *
     * @param account 用户登陆名
     * @param folderId 文件夹id
     * @param fileIdList 文件id列表
     */
    private void countFolderFilesId(String account, String folderId, List<String> fileIdList) {
        Folder f = folderMapper.queryById(folderId);
        if (ConfigureReader.instance().accessFolder(f, account)) {
            fileIdList.addAll(Arrays.asList(
                    nodeMapper.queryByParentFolderId(folderId).parallelStream().map(Node::getFileId).toArray(String[]::new)));
            List<Folder> cFolders = folderMapper.queryByParentId(folderId);
            for (Folder cFolder : cFolders) {
                countFolderFilesId(account, cFolder.getFolderId(), fileIdList);
            }
        }
    }

    @Override
    public boolean moveFiles(List<String> fileIdList, List<String> folderIdList, String opt, SysUser user, String newPath) throws ApplicationException {
        for (String id : fileIdList) {
            if (id == null || id.length() <= 0) {
                throw new ApplicationException("文件未指定");
            }
            Node node = this.nodeMapper.queryById(id);
            if (node == null) {
                throw new ApplicationException("文件不存在，请检查后再试");
            }
            if (node.getFileParentFolder().equals(newPath)) {
                break;
            }
            if (nodeMapper.queryByParentFolderId(newPath).parallelStream()
                    .anyMatch((e) -> e.getFileName().equals(node.getFileName()))) {
                switch (opt) {
                    case StorageConstants.DUPLICATE_NAME_COVER:
                        Node n = nodeMapper.queryByParentFolderId(newPath).parallelStream()
                                .filter((e) -> e.getFileName().equals(node.getFileName())).findFirst().get();
                        if (nodeMapper.deleteById(n.getFileId()) > 0) {
                            return moveFile(node.getFileId(), newPath);
                        }
                        throw new ApplicationException("移动文件失败");
                        // TODO: 2019/2/23 记录日志
                    case StorageConstants.DUPLICATE_NAME_BOTH:
                        node.setFileName(FileNodeUtil.getNewNodeName(node.getFileName(),
                                nodeMapper.queryByParentFolderId(newPath)));
                        if (nodeMapper.update(node) <= 0) {
                            throw new ApplicationException("移动文件失败");
                        }
                        return moveFile(node.getFileId(), newPath);
                    // TODO: 2019/2/23 记录日志
                    case StorageConstants.DUPLICATE_NAME_SKIP:
                        break;
                    default:
                        throw new ApplicationException("参数异常，请指定重名操作");
                }
            }
            return moveFile(node.getFileId(), newPath);
            // TODO: 2019/2/23 记录日志
        }
        for (String folderId : folderIdList) {
            Folder folder = checkFolder(folderId);
            if (folder.getFolderParent().equals(newPath)) {
                break;
            }
            if (folderId.equals(newPath) || folderService.getParentList(newPath).parallelStream()
                    .anyMatch((e) -> e.getFolderId().equals(folder.getFolderId()))) {
                throw new ApplicationException("操作失败");
            }
            if (folderMapper.queryByParentId(newPath).parallelStream()
                    .anyMatch((e) -> e.getFolderName().equals(folder.getFolderName()))) {

                switch (opt) {
                    case "cover":
                        Folder f = folderMapper.queryByParentId(newPath).parallelStream()
                                .filter((e) -> e.getFolderName().equals(folder.getFolderName())).findFirst().get();
                        Map<String, Object> map = new HashMap<>(2);
                        map.put("folderId", folder.getFolderId());
                        map.put("newPath", newPath);
                        if (this.folderMapper.moveById(map) > 0) {
                            if (folderService.deleteAllChildFolder(f.getFolderId()) > 0) {
                                // TODO: 2019/2/23 记录日志
                                return true;
                            }
                        }
                        throw new ApplicationException("移动文件【" + f.getFolderName() + "】失败");
                    case "both":
                        Map<String, Object> params = new HashMap<>(2);
                        params.put("folderId", folder.getFolderId());
                        params.put("newPath", newPath);
                        if (this.folderMapper.moveById(params) > 0) {
                            params.clear();
                            params.put("folderId", folder.getFolderId());
                            params.put("newName", FileNodeUtil.getNewFolderName(folder.getFolderName(),
                                    folderMapper.queryByParentId(newPath)));
                            if (folderMapper.updateFolderNameById(params) <= 0) {
                                throw new ApplicationException("移动文件【" + folder.getFolderName() + "】失败");
                            }
                            // TODO: 2019/2/23 记录日志
                        }
                        // TODO: 2019/2/23 记录日志
                        break;
                    case "skip":
                        break;
                    default:
                        throw new ApplicationException("移动文件失败");
                }
            } else {
                Map<String, Object> map = new HashMap<>(2);
                map.put("folderId", folder.getFolderId());
                map.put("newPath", newPath);
                this.folderMapper.moveById(map);
                // TODO: 2019/2/23 记录日志
                throw new ApplicationException("移动文件失败");
            }
        }
        return true;
    }

    private boolean moveFile(String fileId, String newPath) throws ApplicationException {
        Map<String, String> map = new HashMap<>(2);
        map.put("fileId", fileId);
        map.put("newPath", newPath);
        if (this.nodeMapper.moveById(map) <= 0) {
            throw new ApplicationException("移动文件失败");
        }
        return true;
    }

    /**
     * 校验文件夹
     *
     * @param folderId 指定文件夹
     * @throws ApplicationException 校验异常
     */
    private Folder checkFolder(String folderId) throws ApplicationException {
        if (folderId == null || folderId.length() <= 0) {
            throw new ApplicationException("文件夹未指定");
        }
        Folder folder = this.folderMapper.queryById(folderId);
        if (folder == null) {
            throw new ApplicationException("该文件夹不存在");
        }
        return folder;
    }

    @Override
    public boolean checkMoveFiles(List<String> fileIdList, List<String> folderIdList, String newPath) throws ApplicationException {
        List<Node> tmpNodes = new ArrayList<>();
        List<Folder> tmpFolders = new ArrayList<>();
        for (String fileId : fileIdList) {
            if (fileId == null || fileId.length() <= 0) {
                throw new ApplicationException("文件未指定");
            }
            Node node = this.nodeMapper.queryById(fileId);
            if (node == null) {
                throw new ApplicationException("该文件不存在");
            }
            if (node.getFileParentFolder().equals(newPath)) {
                break;
            }
            if (nodeMapper.queryByParentFolderId(newPath).parallelStream()
                    .anyMatch((e) -> e.getFileName().equals(node.getFileName()))) {
                tmpNodes.add(node);
            }
        }
        for (String folderId : folderIdList) {
            Folder folder = checkFolder(folderId);
            if (folder.getFolderParent().equals(newPath)) {
                break;
            }
            if (folderId.equals(newPath) || folderService.getParentList(newPath).parallelStream()
                    .anyMatch((e) -> e.getFolderId().equals(folder.getFolderId()))) {
                throw new ApplicationException("文件【" + folder.getFolderName() + "】移动失败");
            }
            if (folderMapper.queryByParentId(newPath).parallelStream()
                    .anyMatch((e) -> e.getFolderName().equals(folder.getFolderName()))) {
                tmpFolders.add(folder);
            }
        }
        if (tmpNodes.size() > 0 || tmpFolders.size() > 0) {
            Map<String, List<?>> repeMap = new HashMap<>(2);
            repeMap.put("duplicateFolders", tmpFolders);
            repeMap.put("duplicateNodes", tmpNodes);
            throw new ApplicationException("已存在同名文件或文件夹", repeMap);
        }
        return true;
    }

}
