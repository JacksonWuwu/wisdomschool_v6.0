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

/**zzrdws
 * @date 2019/03/28
 */
@Controller
@RequestMapping("/teacher/outpeizhi")
public class OutpeizhiController  extends BaseController {
    private String prefix = "/school/jiaowu/outpeizhi";

    @Autowired
    private OutpeizhiService outpeizhiService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @RequestMapping("/outpeizhiGetOne")
    Outpeizhi outpeizhiGetOne(@RequestBody Map<String, Object> params){
        return outpeizhiService.getOne(params);
    }

    /**
     * 获取环境配置
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

        Outpeizhi outpeizhi = outpeizhiService.getOne(params);
        modelMap.put("outpeizhi", outpeizhi);
        modelMap.put("tcId", teacherCourse.getId());
        return prefix + "/edit";
    }

    /**
     * 获取环境配置
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
        Assert.notNull(content, "环境配置不能为空");

        Outpeizhi outpeizhi = outpeizhiService.getOne(params);
        if (outpeizhi != null) {
            outpeizhi.setContent(content);
            outpeizhi.setUpdateBy(getLoginName());
            outpeizhiService.update(outpeizhi);
        } else {
            outpeizhi = new Outpeizhi();
            outpeizhi.setContent(content);
            outpeizhi.setTcId(id);
            outpeizhi.setCreateBy(getLoginName());
            outpeizhiService.save(outpeizhi);
        }
        return Data.success();
    }
}
