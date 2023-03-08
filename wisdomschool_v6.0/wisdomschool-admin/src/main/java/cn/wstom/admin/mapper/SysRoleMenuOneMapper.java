package cn.wstom.admin.mapper;




import cn.wstom.admin.entity.SysRoleMenuOne;

import java.util.List;

public interface SysRoleMenuOneMapper {
    /**
     * 通过角色ID删除角色和菜单关联
     * @param roleId
     * @return 结果
     */
    int deleteRoleMenuByRoleOneId(String roleId);

    /**
     * 批量删除角色菜单关联信息
     * @param ids 需要删除数据的ID
     * @return 结果
     */
    int deleteRoleMenuOne(String[] ids);

    /**
     * 查询菜单使用数量
     * @param menuId 菜单ID
     * @return 结果
     */
    int selectCountRoleMenuOneByMenuId(String menuId);

    /**
     * 批量新增角色菜单信息
     * @param roleMenuOneList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenuOne(List<SysRoleMenuOne> roleMenuOneList);
}