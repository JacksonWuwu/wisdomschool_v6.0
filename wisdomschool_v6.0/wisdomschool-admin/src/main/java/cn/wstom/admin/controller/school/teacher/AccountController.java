package cn.wstom.admin.controller.school.teacher;

import cn.wstom.common.base.Data;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * 教师账户
 *
 * @author liniec
 * @date 2020/02/14 17:33
 */
@Slf4j
@Controller
@RequestMapping("/teacher/account")
public class AccountController extends BaseController {
    private String prefix = "/school/teacher/account";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TeacherService teacherService;
    //@Autowired
    //private LoginService loginService;

//    @RequiresPermissions("teacher:course:view")
    @GetMapping("/info")
    public String toInfo(ModelMap modelMap) {
        SysUser sysUser = sysUserService.getById(getUserId());
        sysUser.setPassword(null);
        modelMap.put("info", sysUser);
        return prefix + "/info";
    }

//    @RequiresPermissions("teacher:course:view")
    @GetMapping("/changePwd")
    public String toPwd(ModelMap modelMap) {
        return prefix + "/changePwd";
    }

    @RequiresPermissions("teacher:course:view")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(SysUser user) throws Exception {
        user.setUserAttrId(null);
        user.setUserType(null);
        user.setPassword(null);
        user.setUpdateBy(getLoginName());
        try {
            return toAjax(sysUserService.update(user, false));
        } catch (Exception e) {
            log.info("【教师用户修改信息】 error:{}", e.getMessage());
            throw new Exception("更新失败");
        }
    }

}
