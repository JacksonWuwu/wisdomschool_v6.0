package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestpaperTestquestions;
import cn.wstom.exam.service.TestpaperTestquestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试卷与试题关联 信息操作处理
 *
 * @author hzh
 * @date 20190223
 */
@Controller
@RequestMapping("/module/testpaperTestquestions")
public class TestpaperTestquestionsController extends BaseController {
    private String prefix = "module/testpaperTestquestions";

    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;

    @GetMapping()
    public String toList() {
        return prefix + "list";
    }

    /**
     * 查询试卷做的题目答案列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestpaperTestquestions testpaperTestquestions) {
        startPage();
        List<TestpaperTestquestions> list = testpaperTestquestionsService.list(testpaperTestquestions);
        return wrapTable(list);
    }

    /**
     * 新增试卷做的题目答案
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存试卷做的题目答案
     */
    @Log(title = "试卷做的题目答案", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestpaperTestquestions testpaperTestquestions) throws Exception {
        return toAjax(testpaperTestquestionsService.save(testpaperTestquestions));
    }

    /**
     * 修改试卷做的题目答案
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestpaperTestquestions testpaperTestquestions = testpaperTestquestionsService.getById(id);
        mmap.put("testpaperTestquestions", testpaperTestquestions);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷做的题目答案
     */
    @Log(title = "试卷做的题目答案", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestpaperTestquestions testpaperTestquestions) throws Exception {
        return toAjax(testpaperTestquestionsService.update(testpaperTestquestions));
    }

    /**
     * 删除试卷做的题目答案
     */
    @Log(title = "试卷做的题目答案", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(testpaperTestquestionsService.removeById(ids));
    }
}
