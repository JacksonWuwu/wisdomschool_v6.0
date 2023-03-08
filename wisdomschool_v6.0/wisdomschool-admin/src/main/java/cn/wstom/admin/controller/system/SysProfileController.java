package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysDictData;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.service.SysDictDataService;
import cn.wstom.admin.service.SysUserService;
import cn.wstom.admin.utils.ImageUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.utils.FileUtils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 个人信息 业务处理
 *
 * @author dws
 */
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private SysUserService sysUserService;


    @Autowired
    private SysDictDataService dictDataService;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap map) {
        SysUser sysUser = getUser();
        Map<String, String> params = new HashMap<>(1);
        params.put("sys_user_sex", sysUser.getSex());
        SysDictData dictData = dictDataService.getOne(params);
        sysUser.setSex(dictData == null ? null : dictData.getDictLabel());
        map.put("sysUser", sysUser);
        map.put("roleGroup", sysUserService.selectUserRoleGroup(sysUser.getId()));
        return prefix + "/profile";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser sysUser = getUser();
        String encrypt = new Md5Hash(sysUser.getLoginName() + password + sysUser.getSalt()).toHex().toString();
        if (sysUser.getPassword().equals(encrypt)) {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") String userId, ModelMap mmap) {
        mmap.put("user", sysUserService.getById(userId));
        return prefix + "/resetPwd";
    }

    /**
     * @param sysUser
     * @return
     */
    @Log(title = "重置密码", actionType = ActionType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public Data resetPwd(SysUser sysUser) throws Exception {
        SysUser user = new SysUser();
        user.setId(sysUser.getId());
        user.setSalt(randomSalt());
        user.setPassword(encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
        if (sysUserService.update(user)) {
            return success();
        }
        return error();
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") String userId, ModelMap mmap) {
        mmap.put("user", sysUserService.getById(userId));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar/{userId}")
    public String avatar(@PathVariable("userId") String userId, ModelMap mmap) {
        mmap.put("user", sysUserService.getById(userId));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", actionType = ActionType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public Data update(SysUser sysUser) throws Exception {
        if (sysUserService.update(sysUser)) {
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", actionType = ActionType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public Data updateAvatar(SysUser sysUser, @RequestParam("avatarfile") MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String avatar = ImageUtils.storeImage(file, FileUtils.getBaseDir());
                sysUser.setAvatar(avatar);
                if (sysUserService.update(sysUser)) {
                    return success();
                }
            }
            return error();
        } catch (Exception e) {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
