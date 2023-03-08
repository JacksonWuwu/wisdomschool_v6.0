package cn.wstom.admin.controller.school.sdsadminrole;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.common.config.Global;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sdsadmin/depart")
public class SdsadminDepartController extends BaseController {
    private String prefix = "/school/sdsadmin/depart";
    @Autowired
    private GradesService gradesService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SdsadminVoService sdsadminVoService;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private SysRoleService roleService;

//    @RequiresPermissions("teacher:course:view")
    @GetMapping("/{cid}")
    public String toList(ModelMap modelMap, @PathVariable String cid) {

        List<SysMenu> menus = getSysMenus();
        System.out.println(menus);
         String d = StringUtils.substringBefore(cid, ",");
         String g = StringUtils.substringAfter(cid, ",");
         String gradename = (gradesService.getById(g)).getName();
         String departmentname=(departmentService.getById(d)).getName();
         WebUtils.getSession().setAttribute("gradename", gradename);
        WebUtils.getSession().setAttribute("departmentname", departmentname);
        WebUtils.getSession().setAttribute("did",Integer.valueOf(d));
        WebUtils.getSession().setAttribute("gid",Integer.valueOf(g));
        modelMap.put("gradename", gradename);
        modelMap.put("departmentname", departmentname);
        modelMap.put("menus", menus);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        return "/admin/sdsadmin";
    }



    private List<SysMenu> getSysMenus() {
        List<SysMenu> menus1 = menuService.selectMenusByUser(getUser());
        menus1.forEach(menus -> {
            menus.getChildren().forEach( c -> {
                c.setUrl(c.getUrl());
            });
        });
        return menus1;
    }
    /**
     * 获取列表
     */
//    @RequiresPermissions("teacher:course:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        SdsadminVo sds_admin =new SdsadminVo();
//        sds_admin.setUserType(UserConstants.USER_STUDENT);
        sds_admin.setRid(Integer.valueOf(getUser().getUserAttrId()));
//        System.out.println(sds_admin.getRid());
        startPage();
        List<SdsadminVo> sdsadminVos = sdsadminVoService.selectBySdsadminVo2(sds_admin);
        sdsadminVos.forEach(t ->t.setGdid(t.getDepartment()+","+t.getGrades()));
        fillGrades(sdsadminVos);
        fillDepartments(sdsadminVos);
        System.out.println("SDS"+sdsadminVos);
        PageInfo<SdsadminVo> pageInfo = new PageInfo(sdsadminVos);
        return wrapTable(sdsadminVos, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    ///*
    //* 跳转到教师下级管理
    //* */
    //@GetMapping("/totea_sds")
    //public String toTeacherSdsadmin(ModelMap modelMap){
    //    return "school/teacher/sdsadmin/sdsadmin_index";
    //}


    /*
     * 获取系部信息
     * */
    private void fillDepartments(List<SdsadminVo> sdsadminVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        sdsadminVos.forEach(t -> {
            t.setDepartments(departmentMap.get(String.valueOf(t.getDepartment())));
        });
    }
    /*
     * 获取年级信息
     * */
    private void fillGrades(List<SdsadminVo> sdsadminVos) {

        Map<String, Grades> gradesMap = gradesService.map(null);
        sdsadminVos.forEach(t -> t.setGradess(gradesMap.get(String.valueOf(t.getGrades()))));
    }
}
