package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestpaperQuestions;
import cn.wstom.exam.service.TestpaperQuestionsService;
import cn.wstom.exam.service.TestpaperTestquestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试卷题目 信息操作处理
 *
 * @author hzh
 * @date 20190223
 */
@Controller
@RequestMapping("/module/testpaperQuestions")
public class TestpaperQuestionsController extends BaseController {
    private String prefix = "module/testpaperQuestions";

    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;

    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;




    @GetMapping("/getQuestionsByPaperIdWithUserId/{paperId}/{userId}")
    List<TestpaperQuestions> getQuestionsByPaperIdWithUserId ( @PathVariable("paperId")String paperId,
                                                               @PathVariable("userId")String userId){
        return testpaperTestquestionsService.getQuestionsByPaperIdWithUserId(paperId,userId);
    }




    @GetMapping()
    public String toList() {
        return prefix + "list";
    }

    /**
     * 查询试卷题目列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestpaperQuestions testpaperQuestions) {
        startPage();
        List<TestpaperQuestions> list = testpaperQuestionsService.list(testpaperQuestions);
        return wrapTable(list);
    }

    /**
     * 新增试卷题目
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存试卷题目
     */

    @Log(title = "试卷题目", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestpaperQuestions testpaperQuestions) throws Exception {
        return toAjax(testpaperQuestionsService.save(testpaperQuestions));
    }

    /**
     * 修改试卷题目
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(id);
        mmap.put("testpaperQuestions", testpaperQuestions);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷题目
     */

    @Log(title = "试卷题目", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestpaperQuestions testpaperQuestions) throws Exception {
        return toAjax(testpaperQuestionsService.update(testpaperQuestions));
    }

    /**
     * 删除试卷题目
     */

    @Log(title = "试卷题目", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(testpaperQuestionsService.removeById(ids));
    }
}
