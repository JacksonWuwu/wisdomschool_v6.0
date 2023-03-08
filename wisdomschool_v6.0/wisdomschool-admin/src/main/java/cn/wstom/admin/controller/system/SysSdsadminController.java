package cn.wstom.admin.controller.system;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.Department;
import cn.wstom.admin.entity.Grades;
import cn.wstom.admin.entity.SdsadminRole;
import cn.wstom.admin.entity.SdsadminVo;
import cn.wstom.admin.service.*;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;

import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/sdsadmin")
public class SysSdsadminController extends BaseController {
    private String prefix = "system/sdsadmin";

    @Autowired
    private SdsadminVoService sdsadminVoService;

    @Autowired
    private GradesService gradesService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SdsadminRoleService sdsadminRoleService;

    /**
     * 获取列表页面
     * @return
     */

    @GetMapping("/")
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
        startPage();
        sds_admin.setSchoolId(getUser().getSchoolId());
        List<SdsadminVo> sdsadminVos = sdsadminVoService.selectBySdsadminVos(sds_admin);
        sdsadminVos.forEach(s->s.setId(String.valueOf(s.getSid())));
        fillGrades(sdsadminVos);
        fillDepartments(sdsadminVos);
        return wrapTable(sdsadminVos, new PageInfo(sdsadminVos).getTotal());
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
//    @RequiresPermissions("system:sdsadmin:add")
    @Log(title = "新增", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data add(SdsadminVo sdsadminVo) throws Exception {
        System.out.println(sdsadminVo);
        SdsadminRole sdsadminRole =new SdsadminRole();
        sdsadminRole.setSid(Integer.valueOf(getUser().getId()));
        sdsadminRole.setRid(sdsadminVo.getRid());
        sdsadminRole.setDepartment(sdsadminVo.getDepartment());
        sdsadminRole.setGrades(sdsadminVo.getGrades());

        System.out.println(sdsadminRole.getRid());
        return toAjax(sdsadminRoleService.save(sdsadminRole));
    }
    /**
     * 获取新增页面
     */
    @GetMapping("/edit/{id}")
    public String toedit(@PathVariable("id") String id, ModelMap modelMap) {
        System.out.println(id);
        SdsadminRole sdsadminRole = sdsadminRoleService.selectBySid(Integer.valueOf(id));
        sdsadminRole.setGradess(gradesService.getById((sdsadminRole.getGrades())));
        sdsadminRole.setDepartments(departmentService.getById(sdsadminRole.getDepartment()));
        List<Grades> grades = gradesService.list(null);
        modelMap.put("grades", grades);
        modelMap.put("sdsadminRole",sdsadminRole);
        return prefix + "/edit";
    }

    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data edit(SdsadminRole sdsadminRole) throws Exception {
        System.out.println(sdsadminRole);
        return toAjax( sdsadminRoleService.update(sdsadminRole));
    }

    @PostMapping("/remove")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data remove(Integer ids) throws Exception {
        return toAjax( sdsadminRoleService.deleteBySid(ids));
    }
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
