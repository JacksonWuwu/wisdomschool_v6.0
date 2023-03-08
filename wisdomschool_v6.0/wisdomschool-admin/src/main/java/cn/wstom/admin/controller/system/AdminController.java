package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.SysMenu;
import cn.wstom.admin.entity.SysRole;
import cn.wstom.admin.entity.SysUser;
import cn.wstom.admin.entity.TeacherCourse;
import cn.wstom.admin.service.SysMenuService;
import cn.wstom.admin.service.SysRoleService;
import cn.wstom.admin.service.SysUserService;
import cn.wstom.admin.service.TeacherCourseService;
import cn.wstom.admin.utils.JwtUtils;
import cn.wstom.common.config.Global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

/**
 * 后台首页 业务处理
 *
 * @author dws
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    private String prefix = "/school/";
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 后台首页
     * @param modelMap modelMap
     * @return 视图
     */
    @RequestMapping( "/index")
    public String index(ModelMap modelMap) {
        // 取身份信息
        SysUser sysUser = getUser();
        List<SysMenu> menus = menuService.selectMenusByUser(sysUser);
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        modelMap.put("menus", menus);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        modelMap.put("roles", roles);
        modelMap.put("sysUser", sysUser);
        return prefix + "/select";
    }

    @GetMapping( "/select")
    public String toselect(ModelMap modelMap) {
        SysUser sysUser = getUser();
        List<SysMenu> menus = menuService.selectMenusByUser(sysUser);
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        modelMap.put("menus", menus);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        modelMap.put("roles", roles);
        modelMap.put("sysUser", sysUser);
        return prefix + "select";
    }

    @GetMapping( "/teacher")
    public String toteacher(ModelMap modelMap) {
        // 取身份信息
        SysUser sysUser = getUser();
        List<SysMenu> menus = menuService.selectMenusByUserId_teacher(sysUser.getId());
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        modelMap.put("nowrole", "教师");
        modelMap.put("sysUser", sysUser);
        modelMap.put("roles", roles);
        modelMap.put("menus", menus);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        return prefix + "teacher/index";
    }

    @RequestMapping( "/sys_admin")
    public String tosysadmin(ModelMap modelMap) {
        // 取身份信息
        SysUser sysUser = getUser();
        List<SysMenu> menus = menuService.selectMenusByUser(sysUser);
        System.out.println("menus"+menus);
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        modelMap.put("nowrole", "系统管理员");
        modelMap.put("sysUser", sysUser);
        modelMap.put("roles", roles);
        modelMap.put("menus", menus);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        return prefix + "index";
    }

    @RequestMapping( "/school_admin")
    public String toschooladmin(ModelMap modelMap) {
        // 取身份信息
        SysUser sysUser = getUser();
        List<SysMenu> menus = menuService.selectMenusByUser(sysUser);
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        modelMap.put("nowrole", "学校管理员");
        modelMap.put("sysUser", sysUser);
        modelMap.put("roles", roles);
        modelMap.put("menus", menus);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        return prefix + "index";
    }



    /*
     *注意这里转到SdsadminRoleController
     * */
    @GetMapping( "/sds_admin")
    public String tosdsadmin(ModelMap modelMap){
        //String userId = jwtUtils.getUserIdFromToken(getToken());
        SysUser sysUser = getUser();
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());

        modelMap.put("nowrole", "系部管理员");
        modelMap.put("sysUser", getUser());
        modelMap.put("roles", roles);
        return "/school/sdsadmin/sdsadmin_index";
    }

    /**
     * @param modelMap modelMap
     * @return 视图
     */
    @GetMapping({"/sdsadmin"})
    public String sdsadmin(ModelMap modelMap) {
        // 取身份信息
        //String userId = jwtUtils.getUserIdFromToken(getToken());
        SysUser sysUser = getUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUser(sysUser);
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        modelMap.put("nowrole", "系部管理员");
        modelMap.put("sysUser", getUser());
        modelMap.put("roles", roles);
        modelMap.put("menus",menus);
        return prefix + "sdsadmin/index";
    }



    /*
     *注意
     * */
    @GetMapping( "/classadmin")
    public String toclassadmin(ModelMap modelMap){
        //String userId = jwtUtils.getUserIdFromToken(getToken());
        SysUser sysUser = getUser();
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        for(int i = 0;i < roles.size(); i++) {
            String str = roles.get(i).getRoleKey();
            if ("classadmin".equals(str)) {
                roles.remove(i);
            }
        }
        modelMap.put("nowrole", "班主任");
        modelMap.put("sysUser", getUser());
        modelMap.put("roles", roles);
        return "/classadmin/totea_sds";
    }






    /**
     * @param modelMap modelMap
     * @return 视图
     */
    @GetMapping({"/classadminindex"})
    public String classadmin(ModelMap modelMap) {
        // 取身份信息
        //String userId = jwtUtils.getUserIdFromToken(getToken());
        SysUser sysUser = getUser();
        // 根据用户id取出菜单
        List<SysMenu> menus = menuService.selectMenusByUserId_classadmin(sysUser.getId());
        List<SysRole> roles=roleService.selectAllRolesByUserId(sysUser.getId());
        for(int i = 0;i < roles.size(); i++) {
            String str = roles.get(i).getRoleKey();
            if ("sds_admin".equals(str)) {
                roles.remove(i);
            }
        }
        modelMap.put("nowrole", "班主任");
        modelMap.put("roles", roles);
        modelMap.put("sysUser", getUser());
        modelMap.put("menus",menus);
        return prefix + "classadmin/index";
    }



    /**
     * 系统介绍
     * @param modelMap modelMap
     * @return 视图
     */
    @GetMapping("/system/main")
    public String main(ModelMap modelMap) {
        modelMap.put("version", Global.getVersion());
        return "main";
    }
}
