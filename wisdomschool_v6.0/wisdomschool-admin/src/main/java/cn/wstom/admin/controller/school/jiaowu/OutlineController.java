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
 * @date 2019/03/28
 */
@Controller
@RequestMapping("/teacher/outline")
public class OutlineController extends BaseController {
    private String prefix = "/school/jiaowu/outline";

    @Autowired
    private OutlineService outlineService;

    @Autowired
    private TeacherCourseService teacherCourseService;


    @RequestMapping("/outlineGetOne")
    Outline outlineGetOne( @RequestBody Map<String, Object> params){
        return outlineService.getOne(params);
    }
    /**
     * 获取教学大纲
     *
     * @param
     * @return
     */
    @RequiresPermissions("teacher:course:view")
    @GetMapping("/get/{cid}")
    public String list(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        params.clear();
        params.put("tcId", teacherCourse.getId());

        Outline outline = outlineService.getOne(params);
        modelMap.put("outline", outline);
        modelMap.put("tcId", teacherCourse.getId());
        return prefix + "/edit";
    }

    /**
     * 获取教学大纲
     *
     * @param
     * @return
     */
    @RequiresPermissions("teacher:course:view")
    @PostMapping("/save/{id}")
    @ResponseBody
    public Data save(@PathVariable String id, HttpServletRequest request, ModelMap modelMap) throws Exception {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tcId", id);

        String content = request.getParameter("content");
        Assert.notNull(content, "教学大纲不能为空");

        Outline outline = outlineService.getOne(params);
        if (outline != null) {
            outline.setContent(content);
            outline.setUpdateBy(getLoginName());
            outlineService.update(outline);
        } else {
            outline = new Outline();
            outline.setContent(content);
            outline.setTcId(id);
            outline.setCreateBy(getLoginName());
            outlineService.save(outline);
        }
        return Data.success();
    }
}
