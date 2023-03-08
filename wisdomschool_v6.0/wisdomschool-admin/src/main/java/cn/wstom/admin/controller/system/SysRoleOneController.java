//package cn.wstom.admin.controller.system;
//
//import cn.wstom.admin.controller.BaseController;
//import cn.wstom.admin.entity.SysRole;
//import cn.wstom.admin.entity.SysRoleOne;
//import cn.wstom.admin.service.SysRoleOneService;
//import cn.wstom.admin.service.SysRoleService;
//import cn.wstom.admin.utils.ExcelUtil;
//import cn.wstom.common.annotation.Log;
//import cn.wstom.common.base.Data;
//import cn.wstom.common.enums.ActionType;
//import cn.wstom.common.support.Convert;
//
//import cn.wstom.common.web.page.TableDataInfo;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 角色信息
// */
//@Controller
//@RequestMapping("/system/role1")
//public class SysRoleOneController extends BaseController {
//    private String prefix = "system/role1";
//
//    @Autowired
//    private SysRoleOneService roleOneService;
//
//    @Autowired
//    private SysRoleService sysRoleService;
//
//    @GetMapping()
//    public String toList(){
//        return prefix+"/list";
//    }
//
////    @RequiresPermissions("system:role1:list")
//    @PostMapping("/list")
//    @ResponseBody
//    public TableDataInfo list(SysRoleOne roleOne){
//        startPage();
//        List<SysRoleOne> list = roleOneService.list(roleOne);
//        List<SysRole> sysRoles = sysRoleService.selectAllRolesByUserId(getUserId());
//        for (SysRole sysRole : sysRoles) {
//            if ("school_admin".equals(sysRole.getRoleKey())){ //如果是学校管理员，就移除系统管理员的显示
//                for (int i = 0; i < list.size(); i++) {
//                    if("sys_admin".equals(list.get(i).getRoleKey())){
//                        list.remove(i);
//                    }
//                }
//            }
//        }
//        return wrapTable(list);
//    }
//
//    @Log(title = "角色管理*",actionType = ActionType.EXPORT)
//    @PostMapping("/export")
//    @ResponseBody
//    public Data export(SysRoleOne roleOne){
//        try {
//            List<SysRoleOne> list = roleOneService.list(roleOne);
//            ExcelUtil<SysRoleOne> util = new ExcelUtil<>(SysRoleOne.class);
//            return util.exportExcel(list,"role");
//        }catch (Exception e){
//            return Data.error(e.getMessage());
//        }
//    }
//
//    /**
//     * 新增角色
//     */
//    @GetMapping("/add")
//    public String add(){
//        return prefix+"/add";
//    }
//
//    /**
//     * 新增保存角色
//     */
//
//    @Log(title = "角色管理*",actionType = ActionType.INSERT)
//    @PostMapping("/add")
//    @Transactional(rollbackFor = Exception.class)
//    @ResponseBody
//    public Data addSave(SysRoleOne roleOne) throws Exception{
//        roleOne.setCreateBy(getUser().getLoginName());
//        roleOne.getMenuIds();
//        boolean save = roleOneService.save(roleOne)&& roleOneService.updateRoleMenu(roleOne)>0;
//
//        return toAjax(save);
//    }
//
//    /**
//     * 修改角色
//     */
//    @GetMapping("/edit/{roleId}")
//    public String toEdit(@PathVariable("roleId") String roleId, ModelMap mmap){
//        mmap.put("role",roleOneService.getById(roleId));
//        return prefix+"/edit";
//    }
//    /**
//     * 修改保存角色
//     */
//    @Log(title = "角色管理*", actionType = ActionType.UPDATE)
//    @PostMapping("/edit")
//    @Transactional(rollbackFor = Exception.class)
//    @ResponseBody
//    public Data edit(SysRoleOne role) throws Exception {
//        role.setUpdateBy(getUser().getLoginName());
//        roleOneService.update(role);
//        boolean save = roleOneService.update(role) && roleOneService.updateRoleMenu(role) > 0;
//        return toAjax(save);
//    }
//    /**
//     * 获取权限页
//     */
//    @GetMapping("/permission/{roleId}")
//    public String toPermission(@PathVariable("roleId") String roleId, ModelMap mmap) {
//        mmap.put("role", roleOneService.getById(roleId));
//        return prefix + "/permission";
//    }
//    /**
//     * 修改保存数据权限
//     */
//
//    @Log(title = "角色管理*", actionType = ActionType.UPDATE)
//    @PostMapping("/permission")
//    @Transactional(rollbackFor = Exception.class)
//    @ResponseBody
//    public Data permission(SysRoleOne role) throws Exception {
//        role.setUpdateBy(getUser().getLoginName());
//        return toAjax(roleOneService.update(role));
//    }
//
//    @Log(title = "角色管理*", actionType = ActionType.DELETE)
//    @PostMapping("/remove")
//    @ResponseBody
//    public Data remove(String ids) throws Exception {
//        try {
//            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
//            return toAjax(roleOneService.removeByIds(idList));
//        } catch (Exception e) {
//            return error(e.getMessage());
//        }
//    }
//
//    /**
//     * 校验角色名称
//     */
//    @PostMapping("/checkRoleNameUnique")
//    @ResponseBody
//    public String checkRoleNameUnique(SysRoleOne role) {
//        return roleOneService.checkRoleNameUnique(role);
//    }
//
//    /**
//     * 校验角色权限
//     */
//    @PostMapping("/checkRoleKeyUnique")
//    @ResponseBody
//    public String checkRoleKeyUnique(SysRoleOne role) {
//        return roleOneService.checkRoleKeyUnique(role);
//    }
//
//    /**
//     * 选择菜单树
//     */
//    @GetMapping("/selectMenuTree")
//    public String selectMenuTree() {
//        return prefix + "/tree";
//    }
//}
