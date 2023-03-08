package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Constants;
import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.SysUser;
import cn.wstom.student.utils.KeyUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

/**
 * @author liniec
 * @date 2019/11/06 22:25
 */
@RestController
public class LoginController extends BaseController {



    /**
     * 登陆超时时间：30s
     */
    private static final long TIME_OUT = 30 * 1000;



    @PostMapping("/login")
    @ApiOperation("登录请求")
    @ResponseBody
    public Data login(String account, String password) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(account);
        sysUser.setPassword(password);
        return adminService.login(sysUser);
    }

    @RequestMapping("/logout")
    @ApiOperation("登出")
    @ResponseBody
    public Data logout() {
        Subject currentUser = SecurityUtils.getSubject();
        if (SecurityUtils.getSubject().getSession() != null) {
            currentUser.logout();
        }
        return Data.success();
    }

    @RequestMapping("/publicKey")
    @ApiOperation("加密编码")
    @ResponseBody
    public Data getPublicKey() {
        return Data.success().put("k", KeyUtil.getPublicKey()).put("t", System.currentTimeMillis());
    }

    @GetMapping("/remind")
    @ApiOperation("未登录")
    @ResponseBody
    public Data checkSign() {
        return Data.error(Constants.FAILURE, Constants.LOGIN_FAIL);
    }
}
