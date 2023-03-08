package cn.wstom.admin.service;


import cn.wstom.admin.entity.SysMenu;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;
import cn.wstom.admin.entity.SysUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 业务层
 *
 * @author dws
 */
public interface SysMenuService extends BaseService<SysMenu> {
	/**
	 * 根据用户ID查询菜单
	 *
	 * @param sysUser 用户信息
	 * @return 菜单列表
	 */
	List<SysMenu> selectMenusByUser(SysUser sysUser);

	/**
	 * 查询系统菜单列表
	 *
	 * @param menu 菜单信息
	 * @return 菜单列表
	 */
	List<SysMenu> selectMenuList(SysMenu menu);

	/**
	 * 查询菜单集合
	 *
	 * @return 所有菜单信息
	 */
	List<SysMenu> selectMenuAll();

	/**
	 * 根据用户ID查询权限
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	Set<String> selectPermsByUserId(String userId);

	/**
	 * 根据角色ID查询菜单
	 *
	 * @param role 角色对象
	 * @return 菜单列表
	 */
	List<Map<String, Object>> roleMenuTreeData(SysRole role);

	/**
	 * 查询所有菜单信息
	 *
	 * @return 菜单列表
	 */
	List<Map<String, Object>> menuTreeData();

	/**
	 * 查询系统所有权限
	 *
	 * @return 权限列表
	 */
	Map<String, String> selectPermsAll();

	/**
	 * 查询菜单数量
	 *
	 * @param parentId 菜单父ID
	 * @return 结果
	 */
	int selectCountMenuByParentId(String parentId);

	/**
	 * 查询菜单使用数量
	 *
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	int selectCountRoleMenuByMenuId(String menuId);

	/**
	 * 校验菜单名称是否唯一
	 *
	 * @param menu 菜单信息
	 * @return 结果
	 */
	String checkMenuNameUnique(SysMenu menu);

	/*
	 * 下级管理员的菜单
	 * */

	List<SysMenu> selectMenusByUserId_sdsadmin(String userId);
	/*
	 * 班主任的菜单
	 * */

	List<SysMenu> selectMenusByUserId_classadmin(String userId);
	/*
	 * 教师的菜单
	 * */
	List<SysMenu> selectMenusByUserId_teacher(String userId);


	 SysRoleMenu getRoleMenu(SysRoleMenu sysRoleMenu);

	 List<SysRoleMenu> getRoleMenuList(SysRoleMenu sysRoleMenu);

	List<SysMenu> selectMenuBySchoolId(String schoolId);

}
