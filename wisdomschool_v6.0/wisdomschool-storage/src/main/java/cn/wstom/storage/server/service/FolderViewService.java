package cn.wstom.storage.server.service;

import cn.wstom.storage.server.pojo.FolderView;
import cn.wstom.storage.server.pojo.SysUser;

/**
 * @author dws
 */
public interface FolderViewService {
    /**
     * 获取文件夹视图
     *
     * @param fid  文件夹id
     * @param user 当前用户
     * @return 文件夹视图
     */
    FolderView getFolderView(String fid, SysUser user);
}
