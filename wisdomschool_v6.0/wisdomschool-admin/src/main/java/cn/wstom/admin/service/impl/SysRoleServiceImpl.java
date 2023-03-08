package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;
import cn.wstom.admin.mapper.SysRoleMapper;
import cn.wstom.admin.mapper.SysRoleMenuMapper;
import cn.wstom.admin.mapper.SysUserRoleMapper;
import cn.wstom.admin.service.SysRoleService;
import cn.wstom.common.annotation.DataScope;

import cn.wstom.common.constant.Constants;
import cn.wstom.common.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色 业务层处理
 *
 * @author dws
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
	private SysRoleMapper roleMapper;

    @Resource
	private SysRoleMenuMapper roleMenuMapper;

    @Resource
	private SysUserRoleMapper sysUserRoleMapper;

	/**
	 * 根据条件分页查询角色数据
	 *
	 * @param role 角色信息
	 * @return 角色数据集合信息
	 */
	@Override
	@DataScope(tableAlias = "u")
	public List<SysRole> list(SysRole role) {
		return roleMapper.selectList(role);
	}

	/**
	 * 根据用户ID查询权限
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	@Override
	public Set<String> selectRoleKeys(String userId) {
		List<SysRole> perms = roleMapper.selectRolesByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (SysRole perm : perms) {
			permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
		}
		return permsSet;
	}

	/**
	 * 根据用户ID查询角色的所有信息
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	@Override
	public List<SysRole> selectAllRolesByUserId(String userId) {
		List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
		return userRoles;
	}


	/**
	 * 根据用户ID查询角色
	 *
	 * @param userId 用户ID
	 * @return 角色列表
	 */
	@Override
	public List<SysRole> selectRolesByUserId(String userId) {
		List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
		List<SysRole> roles = list(null);
		for (SysRole role : roles) {
			for (SysRole userRole : userRoles) {
				if (role.getId().equals(userRole.getId())) {
					role.setFlag(true);
					break;
				}
			}
		}
		return roles;
	}

	/**
	 * 更新角色菜单
	 *
	 * @param role
	 * @return
	 */
	@Override
	public int updateRoleMenu(SysRole role) {
		return 0;
	}

	/**
	 * 批量删除角色信息
	 *
	 * @param idList 需要删除的数据ID
	 * @throws Exception
	 */
	@Override
	public boolean removeByIds(List<? extends Serializable> idList) throws Exception {
		Map<String, Object> params = new HashMap<>(1);
		for (Serializable id : idList) {
			/*
			判断该角色是否已被使用
			 */
			params.clear();
			params.put("roleId", id);
			if (sysUserRoleMapper.selectCount(params) > 0) {
				SysRole role = getById(id);
				throw new Exception(String.format("%1$s已分配,不能删除", role.getRoleName()));
			}
		}
		return retBool(roleMapper.deleteBatchIds(idList));
	}

	/**
	 * 更新角色菜单信息
	 *
	 * @param role 角色对象
	 */
	@Override
	public int updateRoleMenu(SysRole role,SysRoleMenu sysRoleMenu) {
		int rows = 1;
		// 删除角色与菜单关联
		sysRoleMenu.setRoleId(role.getId());
		roleMenuMapper.deleteRoleMenuBySysRoleMenu(sysRoleMenu);
		// 新增用户与角色管理
		List<SysRoleMenu> list = new ArrayList<>();
		for (String menuId : role.getMenuIds()) {
			SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getId());
			rm.setMenuId(menuId);
			rm.setSchoolId(sysRoleMenu.getSchoolId());
			list.add(rm);
		}
        if (!list.isEmpty()) {
			rows = roleMenuMapper.batchRoleMenu(list);
		}
		return rows;
	}

	/**
	 * 校验角色名称是否唯一
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public String checkRoleNameUnique(SysRole role) {
        String roleId = StringUtil.isNull(role.getId()) ? StringUtils.EMPTY : role.getId();
		SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
		if (StringUtil.isNotNull(info) && info.getId().equals(roleId)) {
            return Constants.CHECK_NOT_UNIQUE;
        }
        return Constants.CHECK_UNIQUE;
	}

	/**
	 * 校验角色权限是否唯一
	 *
	 * @param role 角色信息
	 * @return 结果
	 */
	@Override
	public String checkRoleKeyUnique(SysRole role) {
        String roleId = StringUtil.isNull(role.getId()) ? StringUtils.EMPTY : role.getId();
		SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
		if (StringUtil.isNotNull(info) && info.getId().equals(roleId)) {
            return Constants.CHECK_NOT_UNIQUE;
        }
        return Constants.CHECK_UNIQUE;
	}
}
