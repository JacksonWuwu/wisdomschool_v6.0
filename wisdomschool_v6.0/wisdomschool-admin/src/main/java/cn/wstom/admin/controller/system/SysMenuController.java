package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysMenu;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;
import cn.wstom.admin.service.SysMenuService;
import cn.wstom.admin.service.SysRoleMenuService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.HttpConstants;
import cn.wstom.common.enums.ActionType;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    private String prefix = "system/menu";

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 获取列表页面
     * @return
     */
    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 获取列表数据
     * @param menu
     * @return
     */

    @GetMapping("/list")
    @ResponseBody
    public List<SysMenu> list(SysMenu menu) {
        return menuService.selectMenuList(menu);
    }

    /**
     * 删除菜单
     */
    @Log(title = "菜单管理", actionType = ActionType.DELETE)
    @PostMapping("/remove/{menuId}")
    @ResponseBody
    public Data remove(@PathVariable("menuId") String menuId) throws Exception {
        if (menuService.selectCountMenuByParentId(menuId) > 0) {
            return error(HttpConstants.CODE_FAILURE, "存在子菜单,不允许删除");
        }
        if (menuService.selectCountRoleMenuByMenuId(menuId) > 0) {
            return error(HttpConstants.CODE_FAILURE, "该资源已分配,不允许删除");
        }

        return toAjax(menuService.removeById(menuId));
    }

    /**
     * 获取新增页面
     */
    @GetMapping("/add/{parentId}")
    public String toAdd(@PathVariable("parentId") String parentId, ModelMap modelMap) {
        SysMenu menu = null;
        if (!Constants.MENU_PARENT_ID.equals(parentId)) {
            menu = menuService.getById(parentId);
        } else {
            menu = new SysMenu();
            menu.setId(Constants.MENU_PARENT_ID);
            menu.setMenuName("主目录");
        }
        modelMap.put("menu", menu);
        return prefix + "/add";
    }

    /**
     * 新增保存菜单
     */
    @Log(title = "菜单管理", actionType = ActionType.INSERT)
    @RequiresPermissions("system:menu:add")
    @PostMapping("/add")
    @ResponseBody
    public Data add(SysMenu menu) throws Exception {
        menu.setCreateBy(getUser().getLoginName());

        return toAjax(menuService.save(menu));
    }

    /**
     * 修改菜单
     */
    @GetMapping("/edit/{menuId}")
    public String toEdit(@PathVariable("menuId") String menuId, ModelMap mmap) {
        mmap.put("menu", menuService.getById(menuId));
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单
     */
    @Log(title = "菜单管理", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(SysMenu menu) throws Exception {
        menu.setUpdateBy(getUser().getLoginName());
        return toAjax(menuService.update(menu));
    }

    /**
     * 选择菜单图标
     */
    @GetMapping("/icon")
    public String icon() {
        return prefix + "/icon";
    }

    /**
     * 校验菜单名称
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(SysMenu menu) {
        return menuService.checkMenuNameUnique(menu);
    }
    //
    ///**
    // * 加载角色菜单列表树
    // */
    //@GetMapping("/roleMenuTreeData")
    //@ResponseBody
    //public List<Map<String, Object>> roleMenuTreeData(SysRole role) {
    //    //sysRoleMenu.setSchoolId(String.valueOf(getUser().getSchoolId()));
    //    return menuService.roleMenuTreeData(role);
    //}

    /**
     * 加载角色菜单列表树
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Map<String, Object>> roleMenuTreeData(SysRoleMenu sysRoleMenu) {
        sysRoleMenu.setSchoolId(String.valueOf(getUser().getSchoolId()));
        return sysRoleMenuService.roleMenuTreeData(sysRoleMenu);
    }


    /**
     * 加载所有菜单列表树
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Map<String, Object>> menuTreeData() {
        return menuService.menuTreeData();
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") String menuId, ModelMap mmap) {
        mmap.put("menu", menuService.getById(menuId));
        return prefix + "/tree";
    }
}
