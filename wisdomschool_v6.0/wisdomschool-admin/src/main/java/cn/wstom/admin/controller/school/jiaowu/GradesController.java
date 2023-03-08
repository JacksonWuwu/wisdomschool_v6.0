package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 年级 信息操作处理
 *
 * @author xyl
 * @date 2019-01-22
 */
@Controller
@RequestMapping("/jiaowu/grades")
public class GradesController extends BaseController {
    private String prefix = "/school/jiaowu/grades";

    @Autowired
    private GradesService gradesService;
    private Integer grades2;
    @RequiresPermissions("jiaowu:grades:view")
    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }




    @RequestMapping(value = "/getGradesById/{gradesId}")
    @ResponseBody
    Grades getGradesById(@PathVariable(value = "gradesId")String gradesId){
        return gradesService.getById(gradesId);
    }
    /**
     * 查询年级列表
     */
//    @RequiresPermissions("module:grades:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Grades grades) {
        startPage();
        grades.setCreateBy(getUser().getId());
        List<Grades> list = gradesService.list(grades);
        return wrapTable(list,new PageInfo<>(list).getTotal());
    }

    //    @RequiresPermissions("module:grades:list")
    @PostMapping("/listpage")
    @ResponseBody
    public TableDataInfo listpage(Grades grades) {
        grades.setCreateBy(getUser().getId());
        grades2 = (Integer) WebUtils.getSession().getAttribute("gid");
        if (grades2 != null) {
            grades.setId(grades2.toString());
            List<Grades> list = gradesService.list(grades);
            return wrapTable(list);
        } else {
            List<Grades> list = gradesService.list(grades);
            return wrapTable(list);
        }
    }
    /**
     * 新增年级
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存年级
     */
    @RequiresPermissions("module:grades:add")
    @Log(title = "年级", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(Grades grades) throws Exception {
        grades.setCreateBy(getUser().getId());
        return toAjax(gradesService.save(grades));
    }

    /**
     * 修改年级
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        Grades grades = gradesService.getById(id);
        mmap.put("grades", grades);
        return prefix + "/edit";
    }

    /**
     * 修改保存年级
     */
    @RequiresPermissions("module:grades:edit")
    @Log(title = "年级", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Grades grades) throws Exception {
        grades.setCreateBy(getUser().getId());
        return toAjax(gradesService.update(grades));
    }

    /**
     * 删除年级
     */
    @RequiresPermissions("module:grades:remove")
    @Log(title = "年级", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(gradesService.removeById(ids));
    }


    /**
     * 校验年级名称
     */
    @PostMapping("/checkGradesNameUnique")
    @ResponseBody
    public boolean checkGradesNameUnique(Grades grades) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", grades.getName());
        return gradesService.count(parms)<=0;
    }


    /*  Lin_    */
    @RequiresPermissions("jiaowu:grades:export")
    @PostMapping("/export")
    @ResponseBody
    public Data export(Grades grades) {
        try {
            List<Grades> list = gradesService.list(grades);
            ExcelUtil<Grades> util = new ExcelUtil<>(Grades.class);
            return util.exportExcel(list, "grades");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
