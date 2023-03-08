package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestStuoptionanswer;
import cn.wstom.exam.service.TestStuoptionanswerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 学生答案 信息操作处理
 *
 * @author hzh
 * @date 20190308
 */
@Controller
@RequestMapping("/school/onlineExam/testStuoptionanswer")
public class TestStuoptionanswerController extends BaseController {
    private String prefix = "school/onlineExam/testStuoptionanswer";

    @Autowired
    private TestStuoptionanswerService testStuoptionanswerService;

    @RequiresPermissions("school:onlineExam:testStuoptionanswer:view")
    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询学生答案列表
     */
    @RequiresPermissions("school:onlineExam:testStuoptionanswer:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestStuoptionanswer testStuoptionanswer) {
        startPage();
        List<TestStuoptionanswer> list = testStuoptionanswerService.list(testStuoptionanswer);
        return wrapTable(list);
    }

    /**
     * 新增学生答案
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存学生答案
     */
    @RequiresPermissions("school:onlineExam:testStuoptionanswer:add")
    @Log(title = "学生答案", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestStuoptionanswer testStuoptionanswer) throws Exception {
        return toAjax(testStuoptionanswerService.save(testStuoptionanswer));
    }

    /**
     * 修改学生答案
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestStuoptionanswer testStuoptionanswer = testStuoptionanswerService.getById(id);
        mmap.put("testStuoptionanswer", testStuoptionanswer);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生答案
     */
    @RequiresPermissions("school:onlineExam:testStuoptionanswer:edit")
    @Log(title = "学生答案", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestStuoptionanswer testStuoptionanswer) throws Exception {
        return toAjax(testStuoptionanswerService.update(testStuoptionanswer));
    }

    /**
     * 删除学生答案
     */
    @RequiresPermissions("school:onlineExam:testStuoptionanswer:remove")
    @Log(title = "学生答案", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(testStuoptionanswerService.removeById(ids));
    }
}
