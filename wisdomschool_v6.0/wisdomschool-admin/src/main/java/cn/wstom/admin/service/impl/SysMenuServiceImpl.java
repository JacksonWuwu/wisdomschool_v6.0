package cn.wstom.admin.service.impl;



import cn.wstom.admin.entity.SysMenu;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.mapper.SysMenuMapper;
import cn.wstom.admin.mapper.SysRoleMenuMapper;
import cn.wstom.admin.service.SysMenuService;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.utils.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 业务层处理
 *
 * @author dws
 */
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Resource
	private SysMenuMapper menuMapper;

    @Resource
	private SysRoleMenuMapper roleMenuMapper;

	/**
	 * 根据用户查询菜单
	 *
	 * @param sysUser 用户信息
	 * @return 菜单列表
	 */
	@Override
	public List<SysMenu> selectMenusByUser(SysUser sysUser) {
		List<SysMenu> menus;
		// 管理员显示所有菜单信息
//		if (sysUser != null && UserConstants.ADMIN_ID.equals(sysUser.getId())) {
//			menus = menuMapper.selectMenuNormalAll();
//		} else {
			menus = menuMapper.selectMenusByUser(sysUser);
//		}
		return getChildPerms(menus, "0");
	}

	@Override
	public SysRoleMenu getRoleMenu(SysRoleMenu sysRoleMenu){
		return roleMenuMapper.getRoleMenu(sysRoleMenu);
	}

	@Override
	public List<SysRoleMenu> getRoleMenuList(SysRoleMenu sysRoleMenu){
		return roleMenuMapper.getRoleMenuList(sysRoleMenu);
	}

	@Override
	public List<SysMenu> selectMenuBySchoolId(String schoolId) {
		return menuMapper.selectMenuBySchoolId(schoolId);
	}

	/**
	 * 查询菜单集合
	 *
	 * @return 所有菜单信息
	 */
	@Override
	public List<SysMenu> selectMenuList(SysMenu menu) {
		return menuMapper.selectMenuList(menu);
	}

	/**
	 * 查询菜单集合
	 *
	 * @return 所有菜单信息
	 */
	@Override
	public List<SysMenu> selectMenuAll() {
		return menuMapper.selectMenuAll();
	}

	/**
	 * 根据用户ID查询权限
	 *
	 * @param userId 用户ID
	 * @return 权限列表
	 */
	@Override
	public Set<String> selectPermsByUserId(String userId) {
		List<String> perms = menuMapper.selectPermsByUserId(userId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtil.isNotEmpty(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * 根据角色ID查询菜单
	 *
	 * @param role 角色对象
	 * @return 菜单列表
	 */
	@Override
	public List<Map<String, Object>> roleMenuTreeData(SysRole role) {
		String roleId = role.getId();
		List<Map<String, Object>> trees;
		List<SysMenu> menuList = menuMapper.selectMenuAll();
		if (StringUtil.isNotNull(roleId)) {
			List<String> roleMenuList = menuMapper.selectMenuTree(roleId);
			trees = getTrees(menuList, true, roleMenuList, true);
		} else {
			trees = getTrees(menuList, false, null, true);
		}
		return trees;
	}

	/**
	 * 查询所有菜单
	 *
	 * @return 菜单列表
	 */
	@Override
	public List<Map<String, Object>> menuTreeData() {
		List<Map<String, Object>> trees;
		List<SysMenu> menuList = menuMapper.selectMenuAll();
		trees = getTrees(menuList, false, null, false);
		return trees;
	}

	/**
	 * 查询系统所有权限
	 *
	 * @return 权限列表
	 */
	@Override
	public LinkedHashMap<String, String> selectPermsAll() {
		LinkedHashMap<String, String> section = new LinkedHashMap<>();
		List<SysMenu> permissions = menuMapper.selectMenuAll();
		if (StringUtil.isNotEmpty(permissions)) {
			for (SysMenu menu : permissions) {
				section.put(menu.getUrl(), MessageFormat.format(PREMISSION_STRING, menu.getPerms()));
			}
		}
		return section;
	}

	/**
	 * 对象转菜单树
	 *
	 * @param menuList     菜单列表
	 * @param isCheck      是否需要选中
	 * @param roleMenuList 角色已存在菜单列表
	 * @param permsFlag    是否需要显示权限标识
	 * @return
	 */
	public List<Map<String, Object>> getTrees(List<SysMenu> menuList, boolean isCheck, List<String> roleMenuList,
											  boolean permsFlag) {
        List<Map<String, Object>> trees = new ArrayList<>();
		for (SysMenu menu : menuList) {
			Map<String, Object> menuMap = new HashMap<>(16);
			menuMap.put("id", menu.getId());
			menuMap.put("pId", menu.getParentId());
			menuMap.put("name", transMenuName(menu, roleMenuList, permsFlag));
			menuMap.put("title", menu.getMenuName());
			if (isCheck) {
				menuMap.put("checked", roleMenuList.contains(menu.getId() + menu.getPerms()));
			} else {
				menuMap.put("checked", false);
			}
			trees.add(menuMap);
		}
		return trees;
	}

	public String transMenuName(SysMenu menu, List<String> roleMenuList, boolean permsFlag) {
		StringBuffer sb = new StringBuffer();
		sb.append(menu.getMenuName());
		if (permsFlag) {
			sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
		}
		return sb.toString();
	}

	/**
	 * 查询子菜单数量
	 *
	 * @param parentId 菜单ID
	 * @return 结果
	 */
	@Override
	public int selectCountMenuByParentId(String parentId) {
		return menuMapper.selectCountMenuByParentId(parentId);
	}

	/**
	 * 查询菜单使用数量
	 *
	 * @param menuId 菜单ID
	 * @return 结果
	 */
	@Override
	public int selectCountRoleMenuByMenuId(String menuId) {
		return roleMenuMapper.selectCountRoleMenuByMenuId(menuId);
	}

	/**
	 * 校验菜单名称是否唯一
	 *
	 * @param menu 菜单信息
	 * @return 结果
	 */
	@Override
	public String checkMenuNameUnique(SysMenu menu) {
        String menuId = StringUtil.isNull(menu.getId()) ? StringUtils.EMPTY : menu.getId();
		SysMenu info = menuMapper.checkMenuNameUnique(menu.getMenuName(), menu.getParentId());
		if (StringUtil.isNotNull(info) && info.getId().equals(menuId)) {
            return Constants.CHECK_NOT_UNIQUE;
        }
        return Constants.CHECK_UNIQUE;
	}

	@Override
	public List<SysMenu> selectMenusByUserId_sdsadmin(String userId) {
		List<SysMenu> menus=menuMapper.selectMenusByUserId_sdsadmin(userId);

		return getChildPerms(menus, "0");
	}

	@Override
	public List<SysMenu> selectMenusByUserId_classadmin(String userId) {
		List<SysMenu> menus=menuMapper.selectMenusByUserId_classadmin(userId);
		return getChildPerms(menus, "0");
	}

	@Override
	public List<SysMenu> selectMenusByUserId_teacher(String userId) {
		List<SysMenu> menus=menuMapper.selectMenusByUserId_teacher(userId);
		return getChildPerms(menus, "0");
	}

	/**
	 * 根据父节点的ID获取所有子节点
	 *
	 * @param list   分类表
	 * @param parentId 传入的父节点ID
	 * @return String
	 */
	public List<SysMenu> getChildPerms(List<SysMenu> list, String parentId) {
		List<SysMenu> returnList = new ArrayList<>();
		if (parentId == null) {
			return list;
		}
		for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
			SysMenu t = iterator.next();
			// 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
			if (parentId.equals(t.getParentId())) {
				recursionFn(list, t);
				returnList.add(t);
			}
		}
		return returnList;
	}

	/**
	 * 递归列表
	 *
	 * @param list
	 * @param t
	 */
	private void recursionFn(List<SysMenu> list, SysMenu t) {
		// 得到子节点列表
		List<SysMenu> childList = getChildList(list, t);
		t.setChildren(childList);
		for (SysMenu tChild : childList) {
			if (hasChild(list, tChild)) {
				// 判断是否有子节点
				for (SysMenu n : childList) {
					recursionFn(list, n);
				}
			}
		}
	}

	/**
	 * 得到子节点列表
	 */
	private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
		List<SysMenu> sysMenus = new ArrayList<>();
		if (t == null) {
			return list;
		}
		for (SysMenu n : list) {
			if (t.getId().equals(n.getParentId())) {
				sysMenus.add(n);
			}
		}
		return sysMenus;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return !getChildList(list, t).isEmpty();
	}
}
