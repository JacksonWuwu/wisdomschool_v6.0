package cn.wstom.admin.mapper;



import cn.wstom.admin.entity.SysRoleOne;

import java.util.List;

public interface SysRoleOneMapper extends BaseMapper<SysRoleOne> {
    /**
     *根据用户ID查询角色
     * @param userId
     * @return 角色列表
     */
    List<SysRoleOne> selectRolesOneByUserId(String userId);

    /**
     * 检验角色名称是否唯一
     * @param roleName
     * @return 角色信息
     */
    SysRoleOne checkRoleNameUnique(String roleName);

    /**
     * 检验角色权限是否唯一
     * @param roleKey
     * @return 角色信息
     */
    SysRoleOne checkRoleKeyUnique(String roleKey);
}
