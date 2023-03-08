package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.SdsadminRole;


public interface SdsadminRoleMapper extends BaseMapper<SdsadminRole> {
    SdsadminRole selectBySid(Integer sid);
    int deleteBySid(Integer sid);
}
