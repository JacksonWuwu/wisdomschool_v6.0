package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.TableDataInfo;
import cn.wstom.exam.entity.TestpaperOneTestquestions;
import cn.wstom.exam.entity.TestpaperQuestions;
import cn.wstom.exam.service.TestpaperOneTestquestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 试卷与试题关联 信息操作处理
 *
 * @author
 * @date
 */
@Controller
@RequestMapping("/module/testpaperOneTestquestions")
public class TestpaperOneTestquestionsController extends BaseController {
    private String prefix = "module/testpaperOneTestquestions";

    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;


    @RequestMapping("/getQuestionsAndOptionsByPaperIdWithUserId/{paperId}/{studentId}")
    @ResponseBody
    List<TestpaperQuestions> getQuestionsAndOptionsByPaperIdWithUserId(@PathVariable("paperId")String paperId,
                                                                       @PathVariable("studentId")String studentId){
        return testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId,studentId);
    }
    @RequestMapping("/getQuestionsByPaperOneIdWithUserId/{paperId}/{userId}")
    @ResponseBody
    List<TestpaperQuestions> getQuestionsByPaperOneIdWithUserId (@PathVariable("paperId")String paperId,
                                                                 @PathVariable("userId")String userId){
        return testpaperOneTestquestionsService.getQuestionsByPaperIdWithUserId(paperId,userId);
    }
    @RequestMapping("/getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId")
    @ResponseBody
    List<TestpaperOneTestquestions>  getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(@RequestParam ("paperId")String paperId,@RequestParam ("tid")String tid){
        return testpaperOneTestquestionsService.getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(paperId,tid);
    }

    @GetMapping()
    public String toList() {
        return prefix + "list";
    }

    /**
     * 查询试卷做的题目答案列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TestpaperOneTestquestions testpaperTestquestions) {
        startPage();
        List<TestpaperOneTestquestions> list = testpaperOneTestquestionsService.list(testpaperTestquestions);
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
    public Data add(TestpaperOneTestquestions testpaperTestquestions) throws Exception {
        return toAjax(testpaperOneTestquestionsService.save(testpaperTestquestions));
    }

    /**
     * 修改试卷做的题目答案
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestpaperOneTestquestions testpaperTestquestions = testpaperOneTestquestionsService.getById(id);
        mmap.put("testpaperOneTestquestions", testpaperTestquestions);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷做的题目答案
     */

    @Log(title = "试卷做的题目答案", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestpaperOneTestquestions testpaperTestquestions) throws Exception {
        return toAjax(testpaperOneTestquestionsService.update(testpaperTestquestions));
    }

    /**
     * 删除试卷做的题目答案
     */

    @Log(title = "试卷做的题目答案", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(testpaperOneTestquestionsService.removeById(ids));
    }
}
