package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dws
 * @date 2019/01/14
 */
@Controller
@RequestMapping("/jiaowu/department")
public class DepartmentController extends BaseController {
    private String prefix = "/school/jiaowu/department";

    private Integer department2;
    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/{departmentId}")
    Department getDepartmentById(@PathVariable(value = "departmentId")String departmentId){
        return departmentService.getById(departmentId);
    }

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    //    @RequiresPermissions("jiaowu:department:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Department department) {
        startPage();
        department.setCreateBy(getUser().getId());
        List<Department> list = departmentService.list(department);
        return wrapTable(list,new PageInfo<>(list).getTotal());
    }

    //    @RequiresPermissions("jiaowu:department:list")
    @PostMapping("/listpage")
    @ResponseBody
    public TableDataInfo listpage(Department department) {
        System.out.println("666666666666666666");
        department.setCreateBy(getUser().getId());
        department2= (Integer) WebUtils.getSession().getAttribute("did");
        if (department2!=null){
            department.setId(department2.toString());
            List<Department> list = departmentService.list(department);
            return wrapTable(list);
        }else {
            List<Department> list = departmentService.list(department);
            return wrapTable(list);
        }

    }

    /**
     * 校验系部名称
     */
    @PostMapping("/checkDepartmentName")
    @ResponseBody
    public String checkDepartmentName(Department department) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", department.getName());
        return departmentService.count(params) > 0 ? Constants.CHECK_UNIQUE : Constants.CHECK_NOT_UNIQUE;
    }

    /**
     * 新增系部
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }


    /**
     * 保存新增系部
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "系部管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data save(Department department) throws Exception {
        department.setCreateBy(getUser().getId());
        return toAjax(departmentService.save(department));
    }


    /**
     * 修改系部
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        Department department = departmentService.getById(id);
        mmap.put("department", department);
        return prefix + "/edit";
    }

    /**
     * 修改保存系部
     */
    @RequiresPermissions("module:department:edit")
    @Log(title = "系部", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Department department) throws Exception {
        department.setUpdateBy(getUser().getName());
        return toAjax(departmentService.update(department));
    }

    /**
     * 删除系部
     */
    @RequiresPermissions("module:department:remove")
    @Log(title = "系部", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(departmentService.removeById(ids));
    }

    /**
     * 校验年级名称
     */
    @PostMapping("/checkDepartmentNameUnique")
    @ResponseBody
    public boolean checkDepartmentNameUnique(Department department) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", department.getName());
        return departmentService.count(parms)<=0;
    }

    /*  Lin_    */
    @RequiresPermissions("jiaowu:department:export")
    @PostMapping("/export")
    @ResponseBody
    public Data export(Department department) {
        try {
            List<Department> list = departmentService.list(department);
            ExcelUtil<Department> util = new ExcelUtil<>(Department.class);
            return util.exportExcel(list, "department");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
