package cn.wstom.storage.server.model;

/**
 * 文件节点
 *
 * @author dws
 */
public class Node {
    /**
     * 文件id
     */
    private String fileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 父级目录
     */
    private String fileParentFolder;
    /**
     * 创建时间
     */
    private String fileCreationDate;
    /**
     * 创建者
     */
    private String fileCreator;
    /**
     * 存放路径
     */
    private String filePath;

    public String getFileId() {
        return this.fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileParentFolder() {
        return this.fileParentFolder;
    }

    public void setFileParentFolder(String fileParentFolder) {
        this.fileParentFolder = fileParentFolder;
    }

    public String getFileCreationDate() {
        return this.fileCreationDate;
    }

    public void setFileCreationDate(String fileCreationDate) {
        this.fileCreationDate = fileCreationDate;
    }

    public String getFileCreator() {
        return this.fileCreator;
    }

    public void setFileCreator(String fileCreator) {
        this.fileCreator = fileCreator;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
