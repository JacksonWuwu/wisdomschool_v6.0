package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysRoleMenu;
import cn.wstom.admin.service.SysRoleService;
import cn.wstom.admin.utils.ExcelUtil;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;

import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 角色信息
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    private String prefix = "system/role";

    @Autowired
    private SysRoleService roleService;


    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    //    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRole role) {
        startPage();
        List<SysRole> list = roleService.list(role);
        List<SysRole> sysRoles = roleService.selectAllRolesByUserId(getUserId());
        System.out.println("sysRoles"+sysRoles);
        for (SysRole sysRole : sysRoles) {
            if ("school_admin".equals(sysRole.getRoleKey())){ //如果是学校管理员，就移除系统管理员的显示
                for (int i = 0; i < list.size(); i++) {
                    if("sys_admin".equals(list.get(i).getRoleKey())){
                        list.remove(i);
                    }
                }
            }
        }


        return wrapTable(list);
    }

    @Log(title = "角色管理", actionType = ActionType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysRole role) {
        try {
            List<SysRole> list = roleService.list(role);
            ExcelUtil<SysRole> util = new ExcelUtil<>(SysRole.class);
            return util.exportExcel(list, "role");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }

    /**
     * 新增角色
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存角色
     */

    @Log(title = "角色管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data addSave(SysRole role) throws Exception {
        role.setCreateBy(getUser().getLoginName());
        role.getMenuIds();
        boolean save = roleService.save(role) && roleService.updateRoleMenu(role) > 0;

        return toAjax(save);
    }

    /**
     * 修改角色
     */
    @GetMapping("/edit/{roleId}")
    public String toEdit(@PathVariable("roleId") String roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/edit";
    }

    /**
     * 修改保存角色
     */

    @Log(title = "角色管理", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data edit(SysRole role) throws Exception {
        role.setUpdateBy(getUser().getLoginName());
        roleService.update(role);
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        sysRoleMenu.setSchoolId(String.valueOf(getUser().getSchoolId()));
        sysRoleMenu.setRoleId(role.getId());
        boolean save = roleService.update(role) && roleService.updateRoleMenu(role,sysRoleMenu) > 0;

        return toAjax(save);
    }

    /**
     * 获取权限页
     */
    @GetMapping("/permission/{roleId}")
    public String toPermission(@PathVariable("roleId") String roleId, ModelMap mmap) {
        mmap.put("role", roleService.getById(roleId));
        return prefix + "/permission";
    }

    /**
     * 修改保存数据权限
     */

    @Log(title = "角色管理", actionType = ActionType.UPDATE)
    @PostMapping("/permission")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data permission(SysRole role) throws Exception {
        role.setUpdateBy(getUser().getLoginName());
        return toAjax(roleService.update(role));
    }


    @Log(title = "角色管理", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            return toAjax(roleService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验角色名称
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(SysRole role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(SysRole role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 选择菜单树
     */
    @GetMapping("/selectMenuTree")
    public String selectMenuTree() {
        return prefix + "/tree";
    }
}
