package cn.wstom.storage.api;


import cn.wstom.storage.exception.ApplicationException;
import cn.wstom.storage.server.enumeration.Data;
import cn.wstom.storage.server.enumeration.StorageConstants;
import cn.wstom.storage.server.pojo.SysUser;
import cn.wstom.storage.server.service.*;
import cn.wstom.storage.server.util.JSONUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <h2>主控制器</h2>
 * <p>
 * 该控制器用于负责处理文件的所有请求，具体过程请见各个方法注释。
 * </p>
 *
 * @author dws
 * @version 1.0
 */
@Controller
@RequestMapping({"/storage"})
public class ApiController extends BaseController {

    private final FolderViewService folderViewService;
    private final FolderService folderService;
    private final FileService fileService;
    private final PlayVideoService playVideoService;
    private final ShowPictureService showPictureService;
    private final PlayAudioService playAudioService;
    private final ShowPPTService showPPTService;
    @Autowired
    public ApiController(FolderViewService folderViewService, FolderService folderService, FileService fileService, PlayVideoService playVideoService, ShowPictureService showPictureService, PlayAudioService playAudioService,ShowPPTService showPPTService) {
        this.folderViewService = folderViewService;
        this.folderService = folderService;
        this.fileService = fileService;
        this.playVideoService = playVideoService;
        this.showPictureService = showPictureService;
        this.showPPTService = showPPTService;
        this.playAudioService = playAudioService;
    }

    @RequestMapping("/getFolderView")
    @ResponseBody
    public Data getFolderView(String fid, String user) {
        SysUser sysUser = JSONUtils.readValue(user, SysUser.class);
        if (sysUser == null || sysUser.getUserName() == null) {
            return Data.error("您还没登陆！请登陆");
        }
        return Data.success().put(Data.RESULT_KEY_DATA, folderViewService.getFolderView(fid, sysUser));
    }

    @RequestMapping({"/createFolder"})
    @ResponseBody
    public Data newFolder(String parentId, String folderName, String folderConstraint, String user) throws ApplicationException {
        SysUser sysUser = JSONUtils.readValue(user, SysUser.class);
        if (sysUser == null || sysUser.getUserName() == null) {
            return Data.error("您还没登陆！请登陆");
        }
        return toAjax(folderService.createFolder(parentId, folderName, folderConstraint, sysUser));
    }

    @RequestMapping({"/deleteFolder"})
    @ResponseBody
    public Data deleteFolder(String folderId) {
        return toAjax(folderService.deleteFolder(folderId));
    }

    @RequestMapping({"/renameFolder"})
    @ResponseBody
    public Data renameFolder(String folderId, String newName, String folderConstraint) throws ApplicationException {
        return toAjax(folderService.renameFolder(folderId, newName, folderConstraint));
    }

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 上传结果
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Data upload(String userId, String folder, MultipartFile file, String originalFileName) throws ApplicationException {
        System.out.println(userId);
        System.out.println(folder);
        System.out.println(file);
        System.out.println(originalFileName);
        if (file == null || file.getSize() <= 0) {
            return Data.error("文件不能为空");
        }
        SysUser sysUser = adminService.getUserById(userId);
        if (sysUser == null || sysUser.getUserName() == null) {
            return Data.error("您还没登陆！请登陆");
        }
        try {
            if (originalFileName == null || "".equals(originalFileName.trim())) {
                originalFileName = file.getOriginalFilename();
            }

            return Data.success().put(StorageConstants.FILE_ID, fileService.save(sysUser, folder, file, originalFileName));
        } catch (FileUploadException e) {
            return Data.error(e.getMessage());
        }
    }

    @RequestMapping({"/checkUploadFile"})
    @ResponseBody
    public Data checkUploadFile(String folderId, String[] name) throws ApplicationException {
        return toAjax(fileService.checkUploadFile(folderId, Arrays.asList(name)));
    }

    @RequestMapping({"/deleteFile"})
    @ResponseBody
    public Data deleteFile(String fileId) throws ApplicationException {
        return toAjax(fileService.deleteFile(fileId));
    }

    @RequestMapping("/deleteadjunct")
    @ResponseBody
    public Data deleteAdjunct(HttpServletResponse response,HttpServletRequest request) throws Exception {
        return toAjax(fileService.deleteAdjunct(request,response));
    }


    @RequestMapping({"/downloadFile"})
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        fileService.downloadFile(request, response);
    }

    @RequestMapping("/downloadadjunct")
    public void downloadadjunct(HttpServletRequest request,HttpServletResponse response) throws ApplicationException {
        fileService.downloadadjunct(request,response);
    }


    @RequestMapping({"/downloadFileTwo"})
    public void downloadFileTwo(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        fileService.downloadFileTwo(request, response);
    }

    @RequestMapping({"/renameFile"})
    @ResponseBody
    public Data renameFile(String fileId, String newFileName) throws ApplicationException {
        return toAjax(fileService.renameFile(fileId, newFileName));
    }

    @RequestMapping({"/playVideo"})
    @ResponseBody
    public Data playVideo(String fileId) {
        return Data.success().put(Data.RESULT_KEY_DATA, playVideoService.getPlayVideoJson(fileId));
    }

    /**
     * <h2>预览图片请求</h2>
     * <p>
     * 该方法用于处理预览图片请求。配合Viewer.js插件，返回指定格式的JSON数据。
     * </p>
     *
     * @param request HttpServletRequest 请求对象
     * @return String 预览图片的JSON信息
     */
    @RequestMapping({"/getPrePicture"})
    @ResponseBody
    public String getPrePicture(HttpServletRequest request) {
        return this.showPictureService.getPreviewPictureJson(request);
    }

    /**
     * <h2>获取压缩的预览图片</h2>
     * <p>
     * 该方法用于预览较大图片时获取其压缩版本以加快预览速度，该请求会根据预览目标的大小自动决定压缩等级。
     * </p>
     *
     * @param request  HttpServletRequest 请求对象，其中应包含fileId指定预览图片的文件块ID。
     * @param response HttpServletResponse 响应对象，用于写出压缩后的数据流。
     */
    @RequestMapping({"/showCondensedPicture"})
    public void showCondensedPicture(HttpServletRequest request, HttpServletResponse response) {
        showPictureService.getCondensedPicture(request, response);
    }

    /**
     * <h2>获取预览PPT</h2>
     *
     * @param request  HttpServletRequest 请求对象，其中应包含fileId指定预览图片的文件块ID。
     * @param response HttpServletResponse 响应对象，用于写出压缩后的数据流。
     */
    @RequestMapping({"/showCondensedPPT"})
    public void showCondensedPPT(HttpServletRequest request, HttpServletResponse response) {
        showPPTService.getCondensedPPT(request, response);
    }



    @RequestMapping({"/deleteFiles"})
    @ResponseBody
    public Data deleteCheckedFiles(List<String> fileIdList, List<String> folderIdList) throws ApplicationException {
        return toAjax(fileService.deleteBatchFiles(fileIdList, folderIdList));
    }

    @RequestMapping({"/getPackTime"})
    @ResponseBody
    public Data getPackTime(List<String> fileIdList, List<String> folderList, String user) {
        SysUser sysUser = JSONUtils.readValue(user, SysUser.class);
        if (sysUser == null || sysUser.getUserName() == null) {
            return Data.error("您还没登陆！请登陆");
        }
        return Data.success().put(Data.RESULT_KEY_DATA, fileService.getPackTime(fileIdList, folderList, sysUser));
    }

    @RequestMapping({"/downloadFiles"})
    @ResponseBody
    public Data downloadFiles(List<String> fileIdList, List<String> folderIdList) throws ApplicationException {
        return toAjax(fileService.deleteBatchFiles(fileIdList, folderIdList));
    }

    @RequestMapping({"/downloadFilesZip"})
    public void downloadCheckedFilesZip(HttpServletRequest request, HttpServletResponse response) {
        this.fileService.downloadFilesZip(request, response);
    }

    @RequestMapping({"/playAudios"})
    @ResponseBody
    public String playAudios(HttpServletRequest request) {
        return this.playAudioService.getAudioInfoListByJson(request);
    }

    @RequestMapping({"/checkMoveFiles"})
    @ResponseBody
    public Data checkMoveFiles(List<String> fileIdList, List<String> folderIdList, String newPath) throws ApplicationException {
        return toAjax(fileService.checkMoveFiles(fileIdList, folderIdList, newPath));
    }

    @RequestMapping({"/moveFiles"})
    @ResponseBody
    public Data moveFiles(List<String> fileIdList, List<String> folderIdList, String opt, SysUser user, String newPath) throws ApplicationException {
        return toAjax(fileService.moveFiles(fileIdList, folderIdList, opt, user, newPath));
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return "hello world";
    }
}
