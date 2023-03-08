package cn.wstom.student.controller.exercise;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.core.Uuid;
import cn.wstom.student.entity.*;
import cn.wstom.student.feign.ExamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liniec
 * @date 2019/12/22 00:42
 *  章节练习
 *
 *  特别人才的拼音类名
 *  ps: 不是我写的
 */

@RestController
@RequestMapping("/exercise")
public class ChapterExeController extends BaseController {

    private final String SUBMIT_SUCCESS = "1";
    private final String SUBMIT_FAILURE = "0";

    @Autowired
    private ExamService examService;




    @ApiOperation("章节题目信息")
    @RequestMapping(method = RequestMethod.GET, value = "/info/{chapterId}")
    public Data exeInfo(@PathVariable(value = "chapterId") String chapterId) {
        return Data.success();
    }

    @ApiOperation("章节题目")
    @RequestMapping(method = RequestMethod.GET, value = "/{chapterId}")
    public Data question(@PathVariable(value = "chapterId") String chapterId) {
        MyQuestions questions = new MyQuestions();
//        questions.setChapterId(chapterId); //chapterId 为 pId字段
        questions.setJchapterId(chapterId);
        List<MyQuestions> list = examService.myQuestionsList(questions);

        List<Shuati> shuatis = new ArrayList<>();
        Shuati index;
        for (MyQuestions questions1 : list) {
            String[] answerStr = questions1.getMyoptionAnswerArr().split(";");
            List<MyOptionAnswer> answerList = new ArrayList<>();
            for (String answer :  answerStr) {
                answerList.add(examService.myOptionAnswerById(answer));
            }
            index = new Shuati();
            index.setMyQuestions(questions1);
            index.setMyOptionAnswerList(answerList);
            shuatis.add(index);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("exercises", shuatis);
        return Data.success(result);
    }

    @ApiOperation("章节刷题提交")
    @RequestMapping(method = RequestMethod.POST, value = "/submit")
    public Data submit(@RequestBody List<OptionSubmitVo> optionSubmitVos) {
//        for (ShuatiHistory s : histories) {
//            s.setUserId(getUserId());
//        }

        System.out.println("章节刷题提交" + optionSubmitVos.toString());
//        try {
//            shuatiHistoryService.saveBatch(histories);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Data.success(SUBMIT_FAILURE);
//        }

        return Data.success(SUBMIT_SUCCESS);
    }

    @ApiOperation("章节试卷")
    @RequestMapping(value = "/test/{paperId}", method = RequestMethod.GET)
    @ResponseBody
    public Data chapterExam(@PathVariable(value = "paperId") String paperId) {
        List<TestpaperQuestions> questions;
        questions = examService.getQuestionsByPaperIdWithUserId(paperId, getUserId());
        return Data.success(questions);
    }



    @ApiOperation("章节考试")
    @RequestMapping(value = "/testOne/{paperId}", method = RequestMethod.GET)
    @ResponseBody
    public Data chapterExamOne(@PathVariable(value = "paperId") String paperId) {
        List<TestpaperQuestions> questions;
        questions = examService.getQuestionsByPaperOneIdWithUserId(paperId, getUserId());
        return Data.success(questions);
    }


    @ApiOperation("章节试题提交")
    @RequestMapping(method = RequestMethod.POST, value = "/test/submit")
    @ResponseBody
    public Data saveChapterTestAnswer(@RequestBody List<OptionSubmitVo> options) {
        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);//TODO: 暂时
        });
        return toAjax(examService.saveOptionAnswersByUserId(options, getUserId()));
    }


    @ApiOperation("章节考试提交")
    @RequestMapping(method = RequestMethod.POST, value = "/exam/submit")
    @ResponseBody
    public Data saveChapterExamAnswer(@RequestBody List<OptionExamSubmitVo> options) {
        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);//TODO: 暂时
        });
        return toAjax(examService.saveOptionExamAnswersByUserId(options, getUserId()));
    }
}
