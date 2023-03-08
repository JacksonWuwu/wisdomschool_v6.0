package cn.wstom.admin.service;


import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色业务层
 *
 * @author dws
 */
public interface SysRoleService extends BaseService<SysRole> {

	/**
	 * 根据用户ID查询角色
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	Set<String> selectRoleKeys(String userId);


	/**
	 * 根据用户ID查询角色的所有信息
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	List<SysRole> selectAllRolesByUserId(String userId);

	/**
	 * 根据用户ID查询角色
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	List<SysRole> selectRolesByUserId(String userId);

	/**
	 * 更新角色菜单
	 * @param role
	 * @return
	 */
	int updateRoleMenu(SysRole role);

	/**
	 * 校验角色名称是否唯一
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	String checkRoleNameUnique(SysRole role);

	/**
	 * 校验角色权限是否唯一
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	String checkRoleKeyUnique(SysRole role);
	int updateRoleMenu(SysRole role, SysRoleMenu sysRoleMenu);
}
