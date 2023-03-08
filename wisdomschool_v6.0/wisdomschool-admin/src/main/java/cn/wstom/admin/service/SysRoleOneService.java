package cn.wstom.admin.service;

import cn.wstom.admin.entity.SysRoleOne;

import java.util.List;
import java.util.Set;

/**
 * 角色业务层One
 */
public interface SysRoleOneService extends BaseService<SysRoleOne> {

    /**
     * 根据用户ID查询角色
     * @param userId
     * @return 权限列表
     */
    Set<String> selectRoleOneKeys(String userId);

    /**
     *根据用户ID查询角色
     * @param userId
     * @return 角色列表
     */
    List<SysRoleOne> selectRolesOneByUserId(String userId);

    /**
     * 更新角色菜单
     * @param roleOne
     * @return
     */
    int updateRoleMenu(SysRoleOne roleOne);

    /**
     * 检验角色名称是否唯一
     * @param roleOne
     * @return 结果
     */
    String checkRoleNameUnique(SysRoleOne roleOne);

    /**
     * 检验角色权限是否唯一
     * @param roleOne
     * @return 结果
     */
    String checkRoleKeyUnique(SysRoleOne roleOne);
}
