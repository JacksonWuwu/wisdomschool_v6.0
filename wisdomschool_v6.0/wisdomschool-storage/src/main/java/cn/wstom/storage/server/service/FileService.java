package cn.wstom.storage.server.service;


import cn.wstom.storage.exception.ApplicationException;
import cn.wstom.storage.server.pojo.SysUser;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件service
 *
 * @author dws
 */
public interface FileService {
    /**
     * 检测上传文件
     *
     * @param folderId 文件夹id
     * @param nameList 上文件名列表
     * @return 检测结果
     * @throws ApplicationException 检测上传文件异常
     */
    boolean checkUploadFile(String folderId, List<String> nameList) throws ApplicationException;

    /**
     * 保存文件
     *
     * @param user             操作用户
     * @param folder           上级目录
     * @param file             需保存的文件
     * @param originalFileName 原文件名
     * @return 保存结果
     * @throws FileUploadException  文件上传异常
     * @throws ApplicationException 文件操作异常
     */
    String save(SysUser user, String folder, MultipartFile file, String originalFileName) throws FileUploadException, ApplicationException;

    /**
     * 删除文件
     *
     * @param fileId 文件id
     * @return 删除结果
     * @throws ApplicationException 删除异常
     */
    boolean deleteFile(String fileId) throws ApplicationException;

    boolean deleteAdjunct(HttpServletRequest request,HttpServletResponse response) throws Exception;

    /**
     * 下载文件
     *
     * @param request  打包请求参数
     * @param response 打包输出响应
     * @throws ApplicationException 下载异常
     */
    void downloadFile(HttpServletRequest request, HttpServletResponse response) throws ApplicationException;
    /**
     *
     * 下载上机文件
     *
     * @param request  打包请求参数
     * @param response 打包输出响应
     * @throws ApplicationException 下载异常
     */
    void downloadFileTwo(HttpServletRequest request, HttpServletResponse response) throws ApplicationException;

    /**
     * 重命名文件
     *
     * @param fileId      目标文件id
     * @param newFileName 新文件名
     * @return 重命名结果
     * @throws ApplicationException 操作异常
     */
    boolean renameFile(String fileId, String newFileName) throws ApplicationException;

    /**
     * 批量删除所有选中文件和文件夹
     *
     * @param fileIdList   需要删除的文件
     * @param folderIdList 需要删除的文件夹
     * @return 删除结果
     * @throws ApplicationException 操作异常
     */
    boolean deleteBatchFiles(List<String> fileIdList, List<String> folderIdList) throws ApplicationException;

    /**
     * 获取打包时间说明
     *
     * @param fileIdList   打包文件id列表
     * @param folderIdList 打包文件夹id列表
     * @param user         操作用户
     * @return 时间说明
     */
    String getPackTime(List<String> fileIdList, List<String> folderIdList, SysUser user);


    /**
     * 打包下载功能：前置——压缩要打包下载的文件
     *
     * @param fileIdList   需打包的文件id列表
     * @param folderIdList 需打包的文件夹id列表
     * @param user         操作用户
     * @return 压缩包路径
     * @throws ApplicationException 操作异常
     */
    String getDownloadFiles(List<String> fileIdList, List<String> folderIdList, SysUser user) throws ApplicationException;

    /**
     * 打包下载功能：执行——下载压缩好的文件
     *
     * @param request  打包请求参数
     * @param response 打包输出响应
     * @return 结果
     */
    boolean downloadFilesZip(HttpServletRequest request, HttpServletResponse response);

    /**
     * 打包下载功能 （中文参数）
     *
     * @param request  打包请求参数
     * @param response 打包输出响应
     * @return 结果
     */
    boolean downloadZip(HttpServletRequest request, HttpServletResponse response);

    /**
     * 校验移动文件
     *
     * @param fileIdList   文件列表id
     * @param folderIdList 文件夹列表id
     * @param newPath      新路径
     * @return 校验结果
     * @throws ApplicationException 校验异常
     */
    boolean checkMoveFiles(List<String> fileIdList, List<String> folderIdList, String newPath) throws ApplicationException;

    /**
     * 移动文件
     *
     * @param fileIdList   文件列表id
     * @param folderIdList 文件夹列表id
     * @param opt          指定重名操作类型，cover：覆盖，both：都保存，skip：跳过
     * @param user         操作用户
     * @param newPath      目标路径
     * @return 操作结果
     * @throws ApplicationException 操作异常
     */
    boolean moveFiles(List<String> fileIdList, List<String> folderIdList, String opt, SysUser user, String newPath) throws ApplicationException;


    /**
     * 下载上传的上机作业
     * @param request
     * @param response
     * @throws ApplicationException
     */
    void downloadadjunct(HttpServletRequest request,HttpServletResponse response) throws ApplicationException;
}
