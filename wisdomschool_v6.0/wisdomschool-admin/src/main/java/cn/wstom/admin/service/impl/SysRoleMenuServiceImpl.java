package cn.wstom.admin.service.impl;

import cn.wstom.admin.entity.*;
import cn.wstom.admin.mapper.ArticleCountMapper;
import cn.wstom.admin.mapper.SysMenuMapper;
import cn.wstom.admin.mapper.SysRoleMenuMapper;
import cn.wstom.admin.service.SysRoleMenuService;
import cn.wstom.common.utils.StringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuServiceImpl  implements SysRoleMenuService {
    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    SysMenuMapper sysMenuMapper;

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param sysRoleMenu 角色ID
     * @return 结果
     */
    @Override
    public int deleteRoleMenuBySysRoleMenu(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuMapper.deleteRoleMenuBySysRoleMenu(sysRoleMenu);
    }

    /**
     * 查询菜单树
     *
     * @param sysRoleMenu
     * @return
     */
    @Override
    public List<String> selectMenuTree(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuMapper.selectMenuTree(sysRoleMenu);
    }

    /**
     * 批量删除角色菜单关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRoleMenu(String[] ids) {
        return sysRoleMenuMapper.deleteRoleMenu(ids);
    }

    @Override
    public int deleteRoleMenuBySchoolByIds(String[] ids) {
        return sysRoleMenuMapper.deleteRoleMenuBySchoolByIds(ids);
    }

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    @Override
    public int selectCountRoleMenuByMenuId(String menuId) {
        return sysRoleMenuMapper.selectCountRoleMenuByMenuId(menuId);
    }

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    @Override
    public int batchRoleMenu(List<SysRoleMenu> roleMenuList) {
        return sysRoleMenuMapper.batchRoleMenu(roleMenuList);
    }

    @Override
    public SysRoleMenu getRoleMenu(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuMapper.getRoleMenu(sysRoleMenu);
    }

    @Override
    public List<SysRoleMenu> getRoleMenuList(SysRoleMenu sysRoleMenu) {
        return sysRoleMenuMapper.getRoleMenuList(sysRoleMenu);
    }

    @Override
    public List<Map<String, Object>> roleMenuTreeData(SysRoleMenu sysRoleMenu) {
        List<Map<String, Object>> trees;
        List<SysMenu> menuList;
        System.out.println("sysRoleMenu:"+sysRoleMenu);
        if ("0".equals(sysRoleMenu.getSchoolId())){
            System.out.println("1");
            menuList=sysMenuMapper.selectMenuAll();
        }else {
            System.out.println("2");
            menuList = sysMenuMapper.selectMenuBySchoolId(sysRoleMenu.getSchoolId());
        }
        //set集合去重
            Set<SysMenu> newMenuList = new TreeSet<>(Comparator.comparing(o -> (o.getMenuName() + "" + o.getParentId())));
            newMenuList.addAll(menuList);
        ArrayList<SysMenu> sysMenus = new ArrayList<>(newMenuList);
        if (StringUtil.isNotNull(sysRoleMenu.getRoleId())) {
            List<String> roleMenuList = sysRoleMenuMapper.selectMenuTree(sysRoleMenu);
            trees = getTrees(sysMenus, true, roleMenuList, true);
        } else {
            trees = getTrees(sysMenus, false, null, true);
        }
        return trees;
    }

    @Override
    public int updateRoleMenuBySchoolId(String newId, String oldId) {
        return sysRoleMenuMapper.updateRoleMenuBySchoolId(newId,oldId);
    }


    /**
     * 更新角色菜单信息
     * @param roleOne
     * @return
     */



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









}
