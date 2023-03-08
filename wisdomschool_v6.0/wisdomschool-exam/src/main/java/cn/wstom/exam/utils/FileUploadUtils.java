package cn.wstom.exam.utils;


import cn.wstom.exam.config.Global;
import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.constants.StorageConstants;
import cn.wstom.exam.exception.ApplicationException;
import cn.wstom.exam.exception.FileNameLimitExceededException;
import cn.wstom.exam.feign.StorageService;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 文件处理工具类
 *
 * @author dws
 */
public class FileUploadUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadUtils.class);


    /**
     * 验证文件
     *
     * @param file
     */
    public static void validateFile(MultipartFile file, String[] allowExt) throws IOException, ApplicationException {
        if (file == null || file.isEmpty()) {
            throw new IOException("文件不能为空");
        }

        if (!FileUtils.checkFileType(file.getOriginalFilename(), allowExt)) {
            throw new ApplicationException("文件格式不支持");
        }
    }

    /**
     * 文件上传
     *
     * @param file     上传的文件
     * @param allowExt 允许上传的文件扩展名
     * @return 返回上传成功的文件名
     * @throws FileNameLimitExceededException 文件名太长
     * @throws IOException                    比如读写文件出错时
     */
    public static Data upload(String userId,String folder, MultipartFile file, boolean isOriginalName, String[] allowExt)
            throws IOException, FileNameLimitExceededException, ApplicationException {
        FileUploadUtils.validateFile(file, allowExt);

        assertAllowed(file);

        String originalFilename = file.getOriginalFilename();
        String originalFileExt = originalFilename.substring(Objects.requireNonNull(originalFilename).lastIndexOf(Constants.SEPARATOR_DOT) + 1);
        String fileName = FileUtils.encodingFilename(originalFilename, originalFileExt);

        //判断上传local\ftp\storage
        //此处还需对上传失败的文件进行清理
        switch (FileUtils.STORAGE_MEDIA) {
            case StorageConstants.STORAGE_MEDIA_FTP:
                try {
                    FTPClient ftpClient = FtpUtil.connectFtp();
                    String ftpPath = FtpUtil.uploadFile(ftpClient, folder, file.getInputStream(), fileName);
                    return StringUtil.isNotEmpty(ftpPath) ? Data.error("上传文件失败") : Data.success().put("path", ftpPath);
                } catch (Exception e) {
                    throw new ApplicationException(e.getMessage(), e);
                }
            case StorageConstants.STORAGE_MEDIA_LOCAL:
                File desc = FileUtils.getAbsoluteFile(folder, fileName);
                System.out.println(desc.getPath());
                file.transferTo(desc);
                return StringUtil.isNotEmpty(desc.getPath()) ? Data.error("上传文件失败") : Data.success().put("path", desc.getPath());
            case StorageConstants.STORAGE_MEDIA_STORAGE:
                String storageUrlPrefix = Global.getConfig("wstom.file.storage-url");

                String tmpFile = FileUploadUtils.storageFile(file, "storage", FileUtils.getTmpDir());

                MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(4);
                FileSystemResource resource = new FileSystemResource(FileUtils.getTmpDir() + tmpFile);
                System.out.println("resource:"+resource);
                System.out.println("params:"+params);
                params.add("userId", userId);
                params.add("folder", folder);
                params.add("file", resource);
                params.add("originalFileName", isOriginalName ? originalFilename : null);
                RestOperations restOperations = SpringUtils.getBean(RestOperations.class);
                StorageService storageService = SpringUtils.getBean(StorageService.class);
                Data data = restOperations.postForObject(storageUrlPrefix + "/upload", params, Data.class);
                //Data data = storageService.upload(userId,folder,isOriginalName ? originalFilename : null,file);
                FileUtils.deleteFile(FileUtils.getTmpDir() + File.separator + tmpFile);
                return data;
            default:
                throw new ApplicationException("请指定存储介质!");
        }
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @return
     * @throws FileUploadBase 如果超出最大大小
     */
    public static void assertAllowed(MultipartFile file) throws FileNameLimitExceededException {
        long fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (fileNameLength > FileUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLimitExceededException(file.getOriginalFilename(), fileNameLength,
                    FileUtils.DEFAULT_FILE_NAME_LENGTH);
        }
    }

    /**
     * 存储文件
     *
     * @param file
     * @param folder
     * @param baseDir
     * @return
     * @throws IOException
     */
    public static String storageFile(MultipartFile file, String folder, String baseDir) throws IOException {
        String path = folder + File.separator + FileUtils.getPathAndFileName(FileUtils.getExt(Objects.requireNonNull(file.getOriginalFilename())));
        File temp = new File(baseDir + path);
        FileUtils.validatePath(temp);
        file.transferTo(temp);
        return path;
    }
}
