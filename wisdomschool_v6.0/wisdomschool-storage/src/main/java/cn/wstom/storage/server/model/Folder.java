package cn.wstom.storage.server.model;

/**
 * 文件实体
 *
 * @author dws
 */
public class Folder {
    /**
     * 文件id
     */
    private String folderId;
    /**
     * 文件名
     */
    private String folderName;
    /**
     * 创建时间
     */
    private String folderCreationDate;
    /**
     * 创建者
     */
    private String folderCreator;
    /**
     * 父文件夹
     */
    private String folderParent;
    /**
     * 权限
     */
    private Integer folderConstraint;

    public String getFolderId() {
        return this.folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderCreationDate() {
        return this.folderCreationDate;
    }

    public void setFolderCreationDate(String folderCreationDate) {
        this.folderCreationDate = folderCreationDate;
    }

    public String getFolderCreator() {
        return this.folderCreator;
    }

    public void setFolderCreator(String folderCreator) {
        this.folderCreator = folderCreator;
    }

    public String getFolderParent() {
        return this.folderParent;
    }

    public void setFolderParent(String folderParent) {
        this.folderParent = folderParent;
    }

    public Integer getFolderConstraint() {
        return folderConstraint;
    }

    public void setFolderConstraint(Integer folderConstraint) {
        this.folderConstraint = folderConstraint;
    }

    @Override
    public String toString() {
        return getFolderName();
    }
}
