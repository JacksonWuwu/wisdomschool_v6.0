package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestpaperOptinanswer;
import cn.wstom.exam.service.TestpaperOptinanswerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试卷答案 信息操作处理
 *
 * @author hzh
 * @date 20190223
 */
@Controller
@RequestMapping("/school/onlineExam/testpaperOptinanswer")
public class TestpaperOptinanswerController extends BaseController {
    private String prefix = "school/onlineExam/testpaperOptinanswer";

    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询试卷答案列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestpaperOptinanswer testpaperOptinanswer) {
        startPage();
        List<TestpaperOptinanswer> list = testpaperOptinanswerService.list(testpaperOptinanswer);
        return wrapTable(list);
    }

    /**
     * 新增试卷答案
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存试卷答案
     */
    @RequiresPermissions("school:onlineExam:testpaperOptinanswer:add")
    @Log(title = "试卷答案", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestpaperOptinanswer testpaperOptinanswer) throws Exception {
        return toAjax(testpaperOptinanswerService.save(testpaperOptinanswer));
    }

    /**
     * 修改试卷答案
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestpaperOptinanswer testpaperOptinanswer = testpaperOptinanswerService.getById(id);
        mmap.put("testpaperOptinanswer", testpaperOptinanswer);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷答案
     */
    @RequiresPermissions("school:onlineExam:testpaperOptinanswer:edit")
    @Log(title = "试卷答案", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestpaperOptinanswer testpaperOptinanswer) throws Exception {
        return toAjax(testpaperOptinanswerService.update(testpaperOptinanswer));
    }

    /**
     * 删除试卷答案
     */
    @RequiresPermissions("school:onlineExam:testpaperOptinanswer:remove")
    @Log(title = "试卷答案", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(testpaperOptinanswerService.removeById(ids));
    }
}
