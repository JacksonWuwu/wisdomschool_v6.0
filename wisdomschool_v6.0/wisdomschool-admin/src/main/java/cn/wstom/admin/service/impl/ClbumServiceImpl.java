package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级 服务层实现
 *
 * @author xyl
 * @date 2019-01-25
 */
@Service
public class ClbumServiceImpl extends BaseServiceImpl<ClbumMapper, Clbum>
        implements ClbumService {

    @Autowired
    private ClbumMapper clbumMapper;

    @Override
    public List<Clbum> selectBytcid(String tcid) {
        return clbumMapper.selectBytcid(tcid);
    }

    @Override
    public List<Clbum> selectBytid(String tid) {
        return clbumMapper.selectBytcid(tid);
    }
}
