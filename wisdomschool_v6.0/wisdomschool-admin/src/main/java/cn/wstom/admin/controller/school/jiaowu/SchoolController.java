package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.utils.StringUtil;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 专业 信息操作处理
 *
 * @author xyl
 * @date 2019-01-25
 */
@Controller
@RequestMapping("/jiaowu/school")
public class SchoolController extends BaseController {
    private String prefix = "school/jiaowu/school";

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;



    @Autowired
    private SysUserService sysUserService;


    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询学校列表
     */
    @RequestMapping("/{schoolId}")
    @ResponseBody
    public School getSchoolById(@PathVariable("schoolId") String schoolId) {
        return schoolService.getById(schoolId);
    }

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(School school) {
        startPage();
        List<School> list = schoolService.list(school);
        for (School school1 : list) {
            String administrator = school1.getAdministrator();
            if(StringUtil.isNotEmpty(administrator)){
                SysUser sysUser = sysUserService.getById(school1.getAdministrator());
                school1.setAdministrator(sysUser.getUserName());
            }

        }
        return wrapTable(list,new PageInfo<>(list).getTotal());
    }


    /**
     * 新增学校
     */
    @GetMapping("/add")
    public String toAdd(ModelMap modelMap) {
        List<SysUser> sysUsers = sysUserService.selectUserByRoleKey("school_admin");
        Iterator<SysUser> iterator = sysUsers.iterator();
        while(iterator.hasNext()){
            SysUser i = iterator.next();
            Map<String, Object> parms = new HashMap<>(1);
            parms.put("administrator", i.getId());
            int count = schoolService.count(parms);
            if (count>=1){
                iterator.remove();
            }
        }
        modelMap.put("sysUsers", sysUsers);
        return prefix + "/add";
    }

    /**
     * 新增保存学校
     */

    @Log(title = "学校", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(School school) throws Exception {
        SysRole sysRole = new SysRole();
        sysRole.setRoleKey("school_admin");
        boolean save = schoolService.save(school);

        HashMap<String, Object> admimMap = new HashMap<>();
        admimMap.put("roleKey","sys_admin");
        SysRole adminRole = sysRoleService.getOne(admimMap);
            List<School> list = schoolService.list(school);
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setSchoolId(String.valueOf(0)); //查询管理员的菜单
               //从超级管理员初始化好的角色菜单那里复制一份学校管理员的菜单,并去除超级管理员的使用的菜单
                List<SysRoleMenu> roleMenuList = sysRoleMenuService.getRoleMenuList(sysRoleMenu);
                Iterator<SysRoleMenu> iterator = roleMenuList.iterator();
                while (iterator.hasNext()){
                    SysRoleMenu roleMenu = iterator.next();
                    if(adminRole.getId().equals(roleMenu.getRoleId())){
                        iterator.remove();
                    }else {
                      roleMenu.setSchoolId(list.get(0).getId());
                    }
                }
                //批量插入修改好的roleMenuList
           int i = sysRoleMenuService.batchRoleMenu(roleMenuList);
        if (StringUtil.isNotEmpty(school.getAdministrator())){
            SysUser sysUser = sysUserService.getById(school.getAdministrator());
            sysUser.setSchoolId(school.getId());
            sysUserService.saveOrUpdate(sysUser);
        }
            return toAjax(save&&i>0);

    }

    /**
     * 修改学校
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap modelMap) {
        School school = schoolService.getById(id);
        List<SysUser> sysUsers = sysUserService.selectUserByRoleKey("school_admin");
        Iterator<SysUser> iterator = sysUsers.iterator();
        while(iterator.hasNext()){
            SysUser i = iterator.next();
            Map<String, Object> parms = new HashMap<>(1);
            if(!i.getId().equals(school.getAdministrator())){
                 parms.put("administrator", i.getId());
                int count = schoolService.count(parms);
                if (count>=1){
                    iterator.remove();
                }
            }

        }
        modelMap.put("school", school);
        modelMap.put("sysUsers", sysUsers);
        return prefix + "/edit";
    }

    /**
     * 修改保存学校
     */
    @Log(title = "学校", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(School school) throws Exception {
        School dbSchool = schoolService.getById(school.getId());
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", school.getName());
        int count = schoolService.count(parms);
        if (count>0&&!dbSchool.getName().equals(school.getName())){
            return Data.error("已经存在该学校");
        }
        if (StringUtil.isNotEmpty(school.getAdministrator())){
          SysUser sysUser = sysUserService.getById(school.getAdministrator());
          sysUser.setSchoolId(school.getId());
          sysUserService.saveOrUpdate(sysUser);
        }
        if (StringUtil.isNotEmpty(school.getAdministrator())&&StringUtil.isNotEmpty(dbSchool.getAdministrator())){
            SysUser newUser = sysUserService.getById(school.getAdministrator());
            SysUser oldUser = sysUserService.getById(school.getAdministrator());
            newUser.setSchoolId(school.getId());
            oldUser.setSchoolId(null);
            sysUserService.saveOrUpdate(newUser);
            sysUserService.saveOrUpdate(oldUser);
        }
        if (StringUtil.isEmpty(school.getAdministrator())&&StringUtil.isNotEmpty(dbSchool.getAdministrator())){
            SysUser oldUser = sysUserService.getById(dbSchool.getAdministrator());
            oldUser.setSchoolId("");
            sysUserService.saveOrUpdate(oldUser);
        }


        return toAjax(schoolService.update(school));
        //保存学校信息
    }

    /**
     * 删除学校
     */

    @Log(title = "学校", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        String[] idArray = (String[]) idList.toArray();
        sysRoleMenuService.deleteRoleMenuBySchoolByIds(idArray);
        return toAjax(schoolService.removeByIds(idList));
    }


    /**
     * 校验学校名称
     */
    @PostMapping("/checkSchoolNameUnique")
    @ResponseBody
    public boolean checkSchoolNameUnique(School school) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", school.getName());
        return schoolService.count(parms)<=0;
    }

    /*  Lin_    */

    @PostMapping("/export")
    @ResponseBody
    public Data export(School school) {
        try {
            List<School> list = schoolService.list(school);
            ExcelUtil<School> util = new ExcelUtil<>(School.class);
            return util.exportExcel(list, "school");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
