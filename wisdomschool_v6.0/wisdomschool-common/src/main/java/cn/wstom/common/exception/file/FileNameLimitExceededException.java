package cn.wstom.common.exception.file;

import org.apache.commons.fileupload.FileUploadException;

/**
 * 文件名超长 异常类
 *
 * @author dws
 */
public class FileNameLimitExceededException extends FileUploadException {
    private static final long serialVersionUID = 1L;
    private long length;
    private long maxLength;
    private String filename;

    public FileNameLimitExceededException(String filename, long length, long maxLength) {
        super("file name:【" + filename + "】, length:【" + length + "】, max length:【" + maxLength + "】");
        this.length = length;
        this.maxLength = maxLength;
        this.filename = filename;
    }

    public FileNameLimitExceededException(String filename, String msg) {
        super("file【" + filename + "】异常：" + msg);
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }

    public long getLength() {
        return length;
    }

    public long getMaxLength() {
        return maxLength;
    }
}
