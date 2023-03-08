package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.Grades;
import cn.wstom.admin.entity.Sdsadmin;
import cn.wstom.admin.entity.SdsadminVo;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.service.*;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.UserConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.StringUtil;

import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
    private String prefix = "system/post";

    @Autowired
    private SdsadminVoService sdsadminVoService;
    @Autowired
    private SdsadminService sdsadminService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private GradesService gradesService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取列表页面
     * @return
     */

    @GetMapping(value = "/")
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 获取下属管理员列表
     *
     * @param sds_admin
     * @return
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SdsadminVo sds_admin) {
        if("0".equals(getUser().getSchoolId())){
            List<SysUser> schoolAdmin = sysUserService.selectUserByRoleKey("school_admin");
            return wrapTable(schoolAdmin, new PageInfo(schoolAdmin).getTotal());
        }
        sds_admin.setUserType(UserConstants.USER_STUDENT);
        SysUser sysUser=new SysUser();
        sysUser.setSchoolId(getUser().getSchoolId());
        sysUser.setUserType(UserConstants.USER_SCHOOL_ADMIN);
        startPage();
        List<SysUser> list1=sysUserService.list(sysUser);


        return wrapTable(list1, new PageInfo(list1).getTotal());
    }

    //    @RequiresPermissions("module:grades:list")
    @PostMapping("/listpage")
    @ResponseBody
    public TableDataInfo listpage() {
        List<SysUser> list=sysUserService.selectByrid();
        List<SysUser> sdsadmins=null;
        sdsadmins=new LinkedList<>();
        for (SysUser sysUser : list) {
            if (sysUser != null) {
                if (sysUser.getSchoolId().equals(getUser().getSchoolId())) {
                    sysUser.setId(sysUser.getUserAttrId());
                    sdsadmins.add(sysUser);
                }
            }
        }
        fillName(sdsadmins);
        return wrapTable(sdsadmins);
    }
    /**
     * 获取新增页面
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 保存新增
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "下级管理员管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data add(SdsadminVo sdsadminVo) throws Exception {
        System.out.println(sdsadminVo);
        Sdsadmin sdsadmin = new Sdsadmin();
        System.out.println(sdsadmin.getId());
//        BeanUtils.copyProperties(sdsadminVo.getSdsadmin(),sdsadmin);
        sdsadmin.setCreateBy(getLoginName());
//        studentService.save(student);
        sdsadmin.setDepartment(sdsadminVo.getDepartment());
        sdsadmin.setGrades(sdsadminVo.getGrades());
        sdsadminService.save(sdsadmin);
        System.out.println(sdsadmin.getId());
        Sdsadmin sdsadmin1=new Sdsadmin();
        sdsadmin1.setRid(Integer.parseInt(sdsadmin.getId()));
        sdsadmin1.setId(sdsadmin.getId());
        sdsadminService.update(sdsadmin1);

        SysUser user = new SysUser();
        BeanUtils.copyProperties(sdsadminVo, user);
        user.setSalt(randomSalt());
        user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setUserType(UserConstants.USER_SCHOOL_ADMIN);
        user.setUserAttrId(sdsadmin.getId());
        user.setCreateBy(getLoginName());

        user.setAvatar(Constants.STUDENT_DEFAULT_AVATAR);
        return toAjax(sysUserService.save(user));
    }
    /**
     * 获取新增页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap modelMap) {
        SysUser users = sysUserService.getById(id);
        Sdsadmin sdsadmin = sdsadminService.getById(users.getUserAttrId());
        SdsadminVo sdsadminVo=trans(users,sdsadmin);
        sdsadminVo.setGradess(gradesService.getById((sdsadmin.getGrades())));
        sdsadminVo.setDepartments(departmentService.getById(sdsadmin.getDepartment()));
        List<Grades> grades = gradesService.list(null);
        modelMap.put("grades", grades);
        modelMap.put("sdsadminVo",sdsadminVo);
        return prefix + "/edit";
    }
    /**
     * 转换studentVo类型
     *
     */
    private SdsadminVo trans(SysUser users, Sdsadmin sdsadmin) {
        SdsadminVo sdsadminVo = new SdsadminVo();
        BeanUtils.copyProperties(users, sdsadminVo);
        sdsadminVo.setSdsadmin(sdsadmin);
        return sdsadminVo;
    }

    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data edit(SdsadminVo sdsadminVo) throws Exception {
        if (StringUtil.isNotNull(sdsadminVo.getId()) && UserConstants.ADMIN_ID.equals(sdsadminVo.getId())) {
            return error("不允许修改超级管理员用户");
        }
        SysUser oldUser = sysUserService.getById(sdsadminVo.getId());
        SysUser user = new SysUser();
        BeanUtils.copyProperties(sdsadminVo, user);
        user.setUserAttrId(null);
        user.setUserType(null);
        user.setUpdateBy(getLoginName());
        if (sdsadminVo.getPassword() != null && !"".equals(sdsadminVo.getPassword().trim())) {
            user.setSalt(randomSalt());
            user.setPassword(encryptPassword(oldUser.getLoginName(), sdsadminVo.getPassword(), user.getSalt()));
        }

        Sdsadmin sdsadmin = new Sdsadmin();
        System.out.println(sdsadminVo);
        sdsadmin.setUpdateBy(getLoginName());
        sdsadmin.setId(oldUser.getUserAttrId());
        System.out.println(sdsadmin);
        sdsadminService.update(sdsadmin);
        return toAjax(sysUserService.update(user, true));
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
//    @RequiresPermissions("jiaowu:student:remove")
    @Log(title = "用户管理", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        String[] userIds = Convert.toStrArray(ids);
        List<String> idList = Arrays.asList(userIds);
        if (idList.contains(UserConstants.ADMIN_ID)) {
            throw new Exception("不允许删除超级管理员用户");
        }

        List<SysUser> users = sysUserService.listByIds(idList);
        List<String> sids = new ArrayList<>(users.size());
        users.forEach(u -> sids.add(u.getUserAttrId()));
        sdsadminService.removeByIds(sids);
        return toAjax(sysUserService.removeByIds(idList));
    }

    /*
     * 获取信息
     * */
    private void fillName(List<SysUser> sysUsers) {
        sysUsers.forEach(t -> t.setName(t.getUserName()));
    }
}
