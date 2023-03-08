package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdsadminRoleServiceImpl extends BaseServiceImpl<SdsadminRoleMapper, SdsadminRole>
        implements SdsadminRoleService {
    @Autowired
    private SdsadminRoleMapper sdsadminRoleMapper;
    @Override
    public SdsadminRole selectBySid(Integer sid) {
        return sdsadminRoleMapper.selectBySid(sid);
    }

    @Override
    public int deleteBySid(Integer sid) {
        return sdsadminRoleMapper.deleteBySid(sid);
    }
}
