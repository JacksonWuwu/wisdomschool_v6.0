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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 专业 信息操作处理
 *
 * @author xyl
 * @date 2019-01-25
 */
@Controller
@RequestMapping("/jiaowu/major")
public class MajorController extends BaseController {
    private String prefix = "school/jiaowu/major";
    private Integer department;
    @Autowired
    private MajorService majorService;

    @Autowired
    private DepartmentService departmentService;


    @GetMapping(value = "/{majorId}")
    Major getMajorById(@PathVariable(value = "majorId")String majorId){
        return majorService.getById(majorId);
    }

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询专业列表
     */
//    @RequiresPermissions("module:major:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MajorVo major) {

        Object fid = major.getParams().get("fid");
        if (fid != null) {
            major.setDid(fid.toString());
        }
        department= (Integer) WebUtils.getSession().getAttribute("did");
        if(department !=null ){
            major.setDid(department.toString());
        }
        major.setCreateBy(getUser().getId());
        startPage();
        List<Major> list = majorService.list(major);
        List<MajorVo> majorVos = trans(list);
        //填充系部信息
        fillDepartment(majorVos);
        return wrapTable(majorVos,new PageInfo<>(list).getTotal());
    }

    //    @RequiresPermissions("module:major:list")
    @PostMapping("/listpage")
    @ResponseBody
    public TableDataInfo listpage(MajorVo major) {
        Object fid = major.getParams().get("fid");
        if (fid != null) {
            major.setDid(fid.toString());
        }
        major.setCreateBy(getUser().getId());
        List<Major> list = majorService.list(major);
        List<MajorVo> majorVos = trans(list);
        //填充系部信息
        fillDepartment(majorVos);
        return wrapTable(majorVos);
    }

    /**
     * 新增专业
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存专业
     */
    @RequiresPermissions("module:major:add")
    @Log(title = "专业", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(MajorVo majorVo) throws Exception {
        //保存专业信息
        Major major = new Major();
        BeanUtils.copyProperties(majorVo, major);
        major.setDid(majorVo.getDepartment().getId());
        major.setCreateBy(getUser().getId());
        return toAjax(majorService.save(major));
    }

    /**
     * 修改专业
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap modelMap) {
        Major major = majorService.getById(id);
        MajorVo majorVo = trans(major);
        majorVo.setDepartment(departmentService.getById(major.getDid()));
        modelMap.put("major", majorVo);
        return prefix + "/edit";
    }

    /**
     * 修改保存专业
     */
    @RequiresPermissions("module:major:edit")
    @Log(title = "专业", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(MajorVo majorVo) throws Exception {
        //保存专业信息
        Major major = new Major();
        BeanUtils.copyProperties(majorVo, major);
        major.setDid(majorVo.getDepartment().getId());
        major.setCreateBy(getUser().getId());
        return toAjax(majorService.update(major));
    }

    /**
     * 删除专业
     */
    @RequiresPermissions("module:major:remove")
    @Log(title = "专业", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(majorService.removeById(ids));
    }

    /**
     * 批量转换majorVo类型
     *
     * @param majors
     * @return
     */
    private List<MajorVo> trans(List<Major> majors) {
        List<MajorVo> majorVos = new LinkedList<>();
        majors.forEach(m -> majorVos.add(trans(m)));
        return majorVos;
    }


    /**
     * 转换majorVo类型
     *
     * @param major
     * @return
     */
    private MajorVo trans(Major major) {
        MajorVo majorVo = new MajorVo();
        BeanUtils.copyProperties(major, majorVo);
        return majorVo;
    }

    /**
     * 获取系部信息
     *
     * @param majorVos
     */
    private void fillDepartment(List<MajorVo> majorVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        majorVos.forEach(m -> m.setDepartment(departmentMap.get(m.getDid())));
    }

    /**
     * 校验专业名称
     */
    @PostMapping("/checkMajorNameUnique")
    @ResponseBody
    public boolean checkMajorNameUnique(MajorVo majorVo) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", majorVo.getName());
        parms.put("did",majorVo.getDepartment().getId());
        return majorService.count(parms)<=0;
    }

    /*  Lin_    */
    @RequiresPermissions("jiaowu:department:export")
    @PostMapping("/export")
    @ResponseBody
    public Data export(Major major) {
        try {
            List<Major> list = majorService.list(major);
            ExcelUtil<Major> util = new ExcelUtil<>(Major.class);
            return util.exportExcel(list, "major");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
