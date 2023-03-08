package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.service.SysRoleService;
import cn.wstom.admin.service.SysUserService;
import cn.wstom.admin.utils.ExcelUtil;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.UserConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.StringUtil;

import cn.wstom.common.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    private String prefix = "system/user";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService roleService;


    /**
     * 返回用户列表页面
     *
     * @return
     */

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 获取用户列表信息
     *
     * @param sysUser
     * @return
     */

    @RequestMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestBody SysUser sysUser) {
        startPage();
        return wrapTable(sysUserService.list(sysUser));
    }

    @RequestMapping(value = "/system/user/userMapByIds")
    @ResponseBody
    Map<String, SysUser> userMapByIds(@RequestBody List<String> userIds){
        return sysUserService.mapByIds(userIds);
    }

    @RequestMapping("/{userId}")
    @ResponseBody
    public SysUser getUserById(@PathVariable("userId") String userId){
         return sysUserService.getById(userId);
     }

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public SysUser getUser(@RequestBody SysUser sysUser){
        return sysUserService.selectOne(sysUser);
    }

    @RequestMapping(value = "/getUserList")
    @ResponseBody
    List<SysUser> getUserList(@RequestBody SysUser sysUser){
        return sysUserService.list(sysUser);
    }

    @RequestMapping(value = "/selectStudentByCourseIdAndTeacherId/{courseId}/{teacherId}/{loginName}/{clbumId}/")
    @ResponseBody
    List<SysUser> selectStudentByCourseIdAndTeacherId(@PathVariable("courseId")String courseId,
                                                      @PathVariable("teacherId")String teacherId,
                                                      @PathVariable("loginName")String loginName,
                                                      @PathVariable("clbumId")String clbumId){
        return sysUserService.selectStudentByCourseIdAndTeacherId(courseId,teacherId,loginName,clbumId);
    }

    @RequestMapping(value = "/userlistByTids")
    @ResponseBody
    Map<String, SysUser> userlistByTids(@RequestBody List<String> tcIds){
        return sysUserService.listByTids(tcIds);
    }

    @RequestMapping(value = "/updateUser")
    @ResponseBody
    boolean updateUser(@RequestBody SysUser sysUser) throws Exception {
        return sysUserService.update(sysUser);
    }
    /**
     * 导出用户信息
     *
     * @param sysUser
     * @return
     */
    @Log(title = "用户导出", actionType = ActionType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public Data export(SysUser sysUser) {
        try {
            List<SysUser> list = sysUserService.list(sysUser);
            ExcelUtil<SysUser> util = new ExcelUtil<>(SysUser.class);
            return util.exportExcel(list, "sysUser");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }


    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap map) {
        map.put("roles", roleService.list(null));
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */

    @Log(title = "用户管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data addSave(SysUser sysUser) throws Exception {
        if (StringUtil.isNotNull(sysUser.getId()) && UserConstants.ADMIN_ID.equals(sysUser.getId())) {
            return error("不允许修改超级管理员用户");
        }
        sysUser.setSalt(randomSalt());
        sysUser.setPassword(encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
        sysUser.setCreateBy(getLoginName());
        return toAjax(sysUserService.save(sysUser));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, ModelMap mmap) {
        mmap.put("user", sysUserService.getById(userId));
        mmap.put("roles", roleService.selectRolesByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */

    @Log(title = "用户管理", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data editSave(SysUser sysUser) throws Exception {
        if (StringUtil.isNotNull(sysUser.getId()) && UserConstants.ADMIN_ID.equals(sysUser.getId())) {
            return error("不允许修改超级管理员用户");
        }
        sysUser.setUpdateBy(getUser().getLoginName());
        return toAjax(sysUserService.update(sysUser, true));
    }

    /**
     * 获取重置用户密码页面
     *
     * @param userId 重置用户的id
     * @param map    模型数据
     * @return 重置密码页面
     */

    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") String userId, ModelMap map) {
        map.put("user", sysUserService.getById(userId));
        return prefix + "/resetPwd";
    }

    /**
     * 重置密码
     *
     * @param sysUser
     * @return
     */
    @Log(title = "重置用户密码", actionType = ActionType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public Data resetPwdSave(SysUser sysUser) throws Exception {
        SysUser user = new SysUser();
        user.setId(sysUser.getId());
        user.setSalt(randomSalt());
        user.setPassword(encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
        return toAjax(sysUserService.update(user));
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @Log(title = "删除用户", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            String[] userIds = Convert.toStrArray(ids);
            List<String> idList = Arrays.asList(userIds);
            if (idList.contains(UserConstants.ADMIN_ID)) {
                throw new Exception("不允许删除超级管理员用户");
            }
            return toAjax(sysUserService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public boolean checkLoginNameUnique(SysUser sysUser) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("loginName", sysUser.getLoginName());
        return sysUserService.count(parms) <= 0;
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser sysUser) {
        return sysUserService.checkPhoneUnique(sysUser);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser sysUser) {
        return sysUserService.checkEmailUnique(sysUser);
    }



}
