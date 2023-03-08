package cn.wstom.storage.server.service;


import cn.wstom.storage.exception.ApplicationException;
import cn.wstom.storage.server.model.Folder;
import cn.wstom.storage.server.pojo.SysUser;

import java.util.List;

/**
 * 文件夹service
 *
 * @author dws
 */
public interface FolderService {
    /**
     * 新建文件夹
     *
     * @param parentId         父级文件夹id
     * @param folderName       文件夹名
     * @param folderConstraint 文件夹限制级别
     * @param user             操作用户
     * @return 新建结果
     * @throws ApplicationException 新建异常
     */
    boolean createFolder(String parentId, String folderName, String folderConstraint, SysUser user) throws ApplicationException;

    /**
     * 删除文件夹
     *
     * @param folderId 文件夹id
     * @return 删除结果
     */
    boolean deleteFolder(String folderId);

    /**
     * 删除文件夹和子文件
     *
     * @param folderId 目标文件夹
     * @return 影响条数
     */
    int deleteAllChildFolder(String folderId);

    /**
     * 重命名文件夹
     *
     * @param folderId         指定文件夹id
     * @param newName          新文件夹名
     * @param folderConstraint 文件夹等级
     * @return 重命名结果
     * @throws ApplicationException 操作异常
     */
    boolean renameFolder(String folderId, String newName, String folderConstraint) throws ApplicationException;

    /**
     * 获得父级文件夹列表
     *
     * @param fid 指定文件id
     * @return 父级文件夹列表
     */
    List<Folder> getParentList(String fid);
}
