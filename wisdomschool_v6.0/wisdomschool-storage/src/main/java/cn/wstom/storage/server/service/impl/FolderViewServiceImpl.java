package cn.wstom.storage.server.service.impl;

import cn.wstom.storage.server.mapper.FolderMapper;
import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.model.Folder;
import cn.wstom.storage.server.pojo.FolderView;
import cn.wstom.storage.server.pojo.SysUser;
import cn.wstom.storage.server.service.FolderService;
import cn.wstom.storage.server.service.FolderViewService;
import cn.wstom.storage.server.util.ConfigureReader;
import cn.wstom.storage.server.util.ServerTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author dws
 */
@Service
public class FolderViewServiceImpl implements FolderViewService {

    @Resource
    private FolderMapper folderMapper;

    @Resource
    private NodeMapper nodeMapper;

    private final FolderService folderService;

    @Autowired
    public FolderViewServiceImpl(FolderService folderService) {
        this.folderService = folderService;
    }

    @Override
    public FolderView getFolderView(String fid, SysUser user) {
        FolderView fv = new FolderView();
        Folder vf = this.folderMapper.queryById(fid);
        if (fid.equals("root")){
            fv.setFolder(vf);
            fv.setParentList(folderService.getParentList(fid));
            List<Folder> fs = new LinkedList<>();
            fs.add(folderMapper.queryById("gongxiang"));
            for (Folder f : this.folderMapper.queryByParentId(fid)) {
                if (ConfigureReader.instance().accessFolder(f, user.getLoginName())) {
                    fs.add(f);
                }
            }
            fv.setFolderList(fs);
            fv.setFileList(this.nodeMapper.queryByMyRoot(user.getLoginName()));
            if (user != null) {
                fv.setAccount(user.getLoginName());
            }
            List<String> authList = Arrays.asList("U", "C", "D", "R", "L", "M");
            fv.setAuthList(authList);
            fv.setPublishTime(ServerTimeUtil.accurateToMinute());
        }
        else {
            fv.setFolder(vf);
            fv.setParentList(folderService.getParentList(fid));
            List<Folder> fs = new LinkedList<>();
            for (Folder f : this.folderMapper.queryByParentId(fid)) {
                if (ConfigureReader.instance().accessFolder(f, user.getLoginName())) {
                    fs.add(f);
                }
            }
            fv.setFolderList(fs);
            fv.setFileList(this.nodeMapper.queryByParentFolderId(fid));
            if (user != null) {
                fv.setAccount(user.getLoginName());
            }
            List<String> authList = Arrays.asList("U", "C", "D", "R", "L", "M");
            fv.setAuthList(authList);
            fv.setPublishTime(ServerTimeUtil.accurateToMinute());
        }




        return fv;
    }
}
