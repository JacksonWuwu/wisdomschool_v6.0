package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.CoursetestStuoptionanswer;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.service.CoursetestStuoptionanswerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 学生答案（课程测试） 信息操作处理
 * 学生答案（课程测试） 信息操作处理
 *
 * @author hzh
 * @date 20190315
 */
@Controller
@RequestMapping("/school/onlineExam/coursetestStuoptionanswer")
public class CoursetestStuoptionanswerController extends BaseController {
    private String prefix = "/school/onlineExam/coursetestStuoptionanswer";

    @Autowired
    private CoursetestStuoptionanswerService coursetestStuoptionanswerService;


    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询学生答案（课程测试）列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(CoursetestStuoptionanswer coursetestStuoptionanswer) {
        startPage();
        List<CoursetestStuoptionanswer> list = coursetestStuoptionanswerService.list(coursetestStuoptionanswer);
        return wrapTable(list);
    }

    /**
     * 新增学生答案（课程测试）
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存学生答案（课程测试）
     */
    @RequiresPermissions("school:onlineExam:coursetestStuoptionanswer:add")
    @Log(title = "学生答案（课程测试）", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(CoursetestStuoptionanswer coursetestStuoptionanswer) throws Exception {
        return toAjax(coursetestStuoptionanswerService.save(coursetestStuoptionanswer));
    }

    /**
     * 修改学生答案（课程测试）
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        CoursetestStuoptionanswer coursetestStuoptionanswer = coursetestStuoptionanswerService.getById(id);
        mmap.put("coursetestStuoptionanswer", coursetestStuoptionanswer);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生答案（课程测试）
     */
    @RequiresPermissions("school:onlineExam:coursetestStuoptionanswer:edit")
    @Log(title = "学生答案（课程测试）", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(CoursetestStuoptionanswer coursetestStuoptionanswer) throws Exception {
        return toAjax(coursetestStuoptionanswerService.update(coursetestStuoptionanswer));
    }

    /**
     * 删除学生答案（课程测试）
     */
    @RequiresPermissions("school:onlineExam:coursetestStuoptionanswer:remove")
    @Log(title = "学生答案（课程测试）", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(coursetestStuoptionanswerService.removeById(ids));
    }
}
