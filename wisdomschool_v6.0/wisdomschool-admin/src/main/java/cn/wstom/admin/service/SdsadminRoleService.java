package cn.wstom.admin.service;

import cn.wstom.admin.entity.SdsadminRole;


public interface SdsadminRoleService extends BaseService<SdsadminRole> {
    SdsadminRole selectBySid(Integer sid);
    int deleteBySid(Integer sid);
}
