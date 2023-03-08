package cn.wstom.exam.constants;

/**
 * @author dws
 * @date 2019/02/18
 */
public interface StorageConstants {

    /**
     * 存储介质，ftp/storage/local
     */
    String STORAGE_MEDIA_FTP = "ftp";
    String STORAGE_MEDIA_STORAGE = "storage";
    String STORAGE_MEDIA_LOCAL = "local";


    String UPLOAD_PATH = "./uploadfiles";

    /**
     * 文件上传id
     */
    String FILE_ID = "fileId";

    /**
     * 存储用途
     * thumbnail：浓缩图
     */
    String STORAGE_THUMBNAIL = "thumbnail";

    /**
     * 文件命名冲突解决方式 -- 跳过
     */
    String DUPLICATE_NAME_SKIP = "skip";
    /**
     * 文件命名冲突解决方式 -- 覆盖
     */
    String DUPLICATE_NAME_COVER = "cover";
    /**
     * 文件命名冲突解决方式 -- 保存
     */
    String DUPLICATE_NAME_BOTH = "both";

    /**
     * 资源类型
     * 1、视频
     * 2、音频
     * 3、文件
     * 4、图片
     */
    Integer RECOURSE_VIDEO = 1;
    Integer RECOURSE_FILE = 2;
    Integer RECOURSE_MEDIA = 3;
    Integer RECOURSE_IMG = 4;
    /**
     * 文件类型 -- ppt
     */
    Integer RECOURSE_FILE_PPT = 5;
    /**
     * 文件类型 -- word
     */
    Integer RECOURSE_FILE_WORD = 6;
    /**
     * 文件类型 -- excel
     */
    Integer RECOURSE_FILE_EXCEL = 7;
    /**
     * 文件类型 -- pdf
     */
    Integer RECOURSE_FILE_PDF = 8;
}
