package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.common.base.Data;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author dws
 * @date 2019/04/13
 */
@Controller
@RequestMapping("/teacher/lead")
public class LeadController extends BaseController {
    private String prefix = "/school/jiaowu/lead";

    @Autowired
    private LeadService leadService;

    @Autowired
    private TeacherCourseService teacherCourseService;


    /**
     * 获取开篇导学
     *
     * @param
     * @return
     */

    @RequestMapping("/leadGetOne")
    Lead leadGetOne(@RequestBody Map<String, Object> params){
       return leadService.getOne(params);
    }
    @GetMapping("/get/{cid}")
    public String list(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        params.clear();
        params.put("tcId", teacherCourse.getId());

        Lead lead = leadService.getOne(params);
        modelMap.put("lead", lead);
        modelMap.put("tcId", teacherCourse.getId());
        return prefix + "/edit";
    }

    /**
     * 获取开篇导学
     *
     * @param
     * @return
     */

    @PostMapping("/save/{id}")
    @ResponseBody
    public Data save(@PathVariable String id, HttpServletRequest request, ModelMap modelMap) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tcId", id);

        String content = request.getParameter("content");
        Assert.notNull(content, "开篇导学不能为空");

        Lead lead = leadService.getOne(params);
        if (lead != null) {
            lead.setContent(content);
            lead.setUpdateBy(getLoginName());
            leadService.update(lead);
        } else {
            lead = new Lead();
            lead.setContent(content);
            lead.setTcId(id);
            lead.setCreateBy(getLoginName());
            leadService.save(lead);
        }
        return Data.success();
    }
}
