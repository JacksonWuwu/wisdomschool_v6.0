package cn.wstom.admin.service;






import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;
import cn.wstom.admin.entity.SysRoleOne;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单关联表 数据层
 *
 * @author dws
 */
public interface SysRoleMenuService  {
    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param sysRoleMenu 角色ID
     * @return 结果
     */
    int deleteRoleMenuBySysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 查询菜单树
     * @param sysRoleMenu
     * @return
     */
    List<String> selectMenuTree(SysRoleMenu sysRoleMenu);

    /**
     * 批量删除角色菜单关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleMenu(String[] ids);

    int deleteRoleMenuBySchoolByIds(String[] ids);



    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int selectCountRoleMenuByMenuId(String menuId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenu(List<SysRoleMenu> roleMenuList);


    SysRoleMenu getRoleMenu(SysRoleMenu sysRoleMenu);

    List<SysRoleMenu> getRoleMenuList(SysRoleMenu sysRoleMenu);

    List<Map<String, Object>> roleMenuTreeData(SysRoleMenu sysRoleMenu);

   int updateRoleMenuBySchoolId(String newId,String oldId);

}
