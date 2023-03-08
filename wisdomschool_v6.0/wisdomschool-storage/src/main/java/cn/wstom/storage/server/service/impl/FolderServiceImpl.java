package cn.wstom.storage.server.service.impl;


import cn.wstom.storage.exception.ApplicationException;
import cn.wstom.storage.server.mapper.FolderMapper;
import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Folder;
import cn.wstom.storage.server.model.Node;
import cn.wstom.storage.server.pojo.SysUser;
import cn.wstom.storage.server.service.FolderService;
import cn.wstom.storage.server.util.ConfigureReader;
import cn.wstom.storage.server.util.FileBlockUtil;
import cn.wstom.storage.server.util.ServerTimeUtil;
import cn.wstom.storage.server.util.TextFormatUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author dws
 */
@Service
public class FolderServiceImpl implements FolderService {
    private final FolderMapper folderMapper;

    private final NodeMapper nodeMapper;

    private static ScheduledExecutorService
            worker = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder().namingPattern("folder-service-pool-%d").daemon(true).build());

    @Autowired
    public FolderServiceImpl(FolderMapper folderMapper, NodeMapper nodeMapper) {
        this.folderMapper = folderMapper;
        this.nodeMapper = nodeMapper;
    }

    @Override
    public boolean createFolder(String parentId, String folderName, String folderConstraint, SysUser user) throws ApplicationException {

        Folder parentFolder = checkFolder(parentId, folderName);
        if (folderMapper.queryByParentId(parentId).parallelStream().anyMatch((e) -> e.getFolderName().equals(folderName))) {
            throw new ApplicationException("该文件夹已存在");
        }
        Folder f = new Folder();
        // 设置子文件夹约束等级，不允许子文件夹的约束等级比父文件夹低
        int pc = parentFolder.getFolderConstraint();
        if (folderConstraint == null) {
            throw new ApplicationException("文件夹参数不合法");
        }
        int ifc = Integer.parseInt(folderConstraint);
        if (ifc < pc) {
            throw new ApplicationException("不允许子文件夹的约束等级比父文件夹低");
        }
        f.setFolderConstraint(ifc);
        f.setFolderId(UUID.randomUUID().toString());
        f.setFolderName(folderName);
        f.setFolderCreationDate(ServerTimeUtil.accurateToDay());
        f.setFolderCreator(user.getLoginName());
        f.setFolderParent(parentId);
        int i = this.folderMapper.insertNewFolder(f);
        if (i > 0) {
            // TODO: 2019/2/23 记录日志
            return true;
        }
        throw new ApplicationException("创建新文件夹异常");
    }

    private Folder checkFolder(String folderId, String folderName) throws ApplicationException {
        if (folderId == null || "".equals(folderId) || folderName == null || "".equals(folderName)) {
            throw new ApplicationException("需指定文件夹和文件夹名");
        }
        if (!TextFormatUtil.instance().matcherFolderName(folderName)) {
            throw new ApplicationException("文件夹名不合法，请更改");
        }
        Folder folder = folderMapper.queryById(folderId);
        if (folder == null) {
            throw new ApplicationException("该文件夹不存在");
        }
        return folder;
    }

    @Override
    public List<Folder> getParentList(String fid) {
        Folder f = folderMapper.queryById(fid);
        List<Folder> folderList = new ArrayList<>();
        while (f != null && f.getFolderParent() != null && "".equals(f.getFolderParent())) {
            f = folderMapper.queryById(f.getFolderParent());
            folderList.add(f);
        }
        Collections.reverse(folderList);
        return folderList;
    }

    @Override
    public boolean deleteFolder(String folderId) {

        if (folderId == null || folderId.length() <= 0) {
            return false;
        }
        Folder folder = this.folderMapper.queryById(folderId);
        if (folder == null) {
            return true;
        }
        // TODO: 2019/2/23 记录日志
        return deleteAllChildFolder(folderId) > 0;
    }

    @Override
    public int deleteAllChildFolder(String folderId) {
        String fileBlocks = ConfigureReader.instance().getFileBlockPath();
        worker.execute(() -> iterationDeleteFolder(fileBlocks, folderId));
        return folderMapper.deleteById(folderId);
    }

    private void iterationDeleteFolder(String fileblocks, String folderId) {
        List<Folder> cf = folderMapper.queryByParentId(folderId);
        if (cf.size() > 0) {
            for (Folder f : cf) {
                iterationDeleteFolder(fileblocks, f.getFolderId());
            }
        }
        List<Node> files = nodeMapper.queryByParentFolderId(folderId);
        if (files.size() > 0) {
            nodeMapper.deleteByParentFolderId(folderId);
            for (Node f2 : files) {
                FileBlockUtil.deleteFromFileBlocks(f2);
            }
        }
        folderMapper.deleteById(folderId);
    }

    @Override
    public boolean renameFolder(String folderId, String newName, String folderConstraint) throws ApplicationException {

        Folder folder = checkFolder(folderId, newName);
        Folder parentFolder = folderMapper.queryById(folder.getFolderParent());
        // 设置子文件夹约束等级，不允许子文件夹的约束等级比父文件夹低
        int pc = parentFolder.getFolderConstraint();
        if (folderConstraint != null) {
            int ifc = Integer.parseInt(folderConstraint);
            if (ifc < pc) {
                throw new ApplicationException("不允许子文件夹的约束等级比父文件夹低");
            } else {
                Map<String, Object> params = new HashMap<>(2);
                params.put("newConstraint", ifc);
                params.put("folderId", folderId);
                folderMapper.updateFolderConstraintById(params);
                changeChildFolderConstraint(folderId, ifc);
                if (!folder.getFolderName().equals(newName)) {
                    if (folderMapper.queryByParentId(parentFolder.getFolderId()).parallelStream()
                            .anyMatch((e) -> e.getFolderName().equals(newName))) {
                        throw new ApplicationException("该文件夹已存在");
                    }
                    params.clear();
                    params.put("folderId", folderId);
                    params.put("newName", newName);
                    if (this.folderMapper.updateFolderNameById(params) == 0) {
                        throw new ApplicationException("更新文件夹名异常");
                    }
                }
                // TODO: 2019/2/23 记录日志
                return true;
            }
        }
        throw new ApplicationException("更新文件夹名【" + newName + "】失败");
    }

    /**
     * <h2>迭代修改子文件夹约束</h2>
     * <p>
     * 当某一文件夹的约束被修改时，其所有子文件夹的约束等级均不得低于其父文件夹。 例如：
     * 父文件夹的约束等级改为1（仅小组）时，所有约束等级为0（公开的）的子文件夹的约束等级也会提升为1， 而所有约束等级为2（仅自己）的子文件夹则不会受影响。
     * </p>
     *
     * @param folderId 要修改的文件夹ID
     * @param c        约束等级
     */
    private void changeChildFolderConstraint(String folderId, int c) {
        List<Folder> cfs = folderMapper.queryByParentId(folderId);
        for (Folder cf : cfs) {
            if (cf.getFolderConstraint() < c) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("newConstraint", c);
                map.put("folderId", cf.getFolderId());
                folderMapper.updateFolderConstraintById(map);
            }
            changeChildFolderConstraint(cf.getFolderId(), c);
        }
    }

}
