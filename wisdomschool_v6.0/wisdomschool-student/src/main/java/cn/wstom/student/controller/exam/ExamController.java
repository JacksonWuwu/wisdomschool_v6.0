package cn.wstom.student.controller.exam;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import cn.wstom.student.feign.ExamService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ExamController.class);
    @Autowired
    private ExamService examService;

    @ApiOperation("考试系统列表")
    @GetMapping(value="/list/{tcid}")
    public Data list(@PathVariable String tcid, TestPaperOne testPaperOne){
//        TeacherCourse teacherCourse = teacherCourseService.getById(tcid);
       testPaperOne.setCoursrId(tcid);
       testPaperOne.setType("0");
       testPaperOne.setCreateId(getUser().getUserAttrId());
       List<TestPaperOne> list = examService.testPaperOneList(testPaperOne);
       return Data.success(list);
    }
    @ApiOperation("考试详情")
    @GetMapping(value="/detailList/{id}")
    public Data detailList(@PathVariable String id) throws Exception {
        startPage();
        UserExam userExam = new UserExam();
        userExam.setTestPaperOneId(id);
        List list = examService.userExamList(userExam);
        return Data.success(list);
    }
    @ApiOperation("查看学生考试作答")
    @GetMapping(value="/startMakePaperOne/{paperId}/{studentId}")
    public Data showStuPaperOne(@PathVariable String paperId,@PathVariable String studentId)  {
        List<TestpaperQuestions> tqvolist = examService.getQuestionsAndOptionsByPaperIdWithUserId(paperId,studentId);
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        return Data.success(tqvolist);
    }
    @ApiOperation("查看学生测试作答")
    @GetMapping(value="/startMakePaper/{paperId}/{studentId}")
    public Data showStuPaper(@PathVariable String paperId,@PathVariable String studentId)  {
        List<TestpaperQuestions> tqvolist = examService.getQuestionsAndOptionsByPaperIdWithUserId(paperId,studentId);
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        return Data.success(tqvolist);
    }

    @ApiOperation("删除学生测试作答")
    @PostMapping("/remove")
    public Data remove(String ids) {
        String[] userId = ids.split(",");
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, userId);
        try {
            return toAjax(examService.userTestRemoveByIds(list));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error(e.getMessage());
        }
    }
    @ApiOperation("提交考试分数")
    @RequestMapping(method = RequestMethod.POST, value ="/saveScore")
    @ResponseBody
    public Data saveScore(String paperId, String userId, @RequestBody List<OptionExamSubmitVo> scoreArray) throws Exception {
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        scoreArray.forEach(o -> {
            StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
            stuOptionExamanswer.setId(o.getStuOptionanswerId());
            stuOptionExamanswer.setQuestionScore(o.getScore());
            stuOptionExamanswer.setUpdateId(Integer.valueOf(getUserId()));
            optionanswers.add(stuOptionExamanswer);
        });
        return toAjax(examService.updateListAndTotalScore(optionanswers, paperId, userId));
    }

    @ApiOperation("组卷")
    @PostMapping(value="/AGlist/{cid}")
    public Data AGlist(@PathVariable String cid, MyQuestions myQuestions){
        myQuestions.setXzsubjectsId(cid);
        myQuestions.setCreateId(getUser().getUserAttrId());
        System.out.println("tid" + myQuestions.getTitleTypeId());
        startPage();
        List<MyQuestions> list = examService.myQuestionsList(myQuestions);
        String easy = "容易";
        String easyNum = "1";
        String relativelyEasy = "较易";
        String relativelyEasyNum = "2";
        String secondary = "中等";
        String secondaryNum = "3";
        String moreDifficult = "较难";
        String moreDifficultNum = "4";
        String difficulty = "困难";
        String difficultyNum = "5";
        if (list.size() != 0) {
            for (MyQuestions questions : list) {
                if (questions.getDifficulty().equals(easyNum)) {
                    questions.setDifficulty(easy);
                }
                if (questions.getDifficulty().equals(relativelyEasyNum)) {
                    questions.setDifficulty(relativelyEasy);
                }
                if (questions.getDifficulty().equals(secondaryNum)) {
                    questions.setDifficulty(secondary);
                }
                if (questions.getDifficulty().equals(moreDifficultNum)) {
                    questions.setDifficulty(moreDifficult);
                }
                if (questions.getDifficulty().equals(difficultyNum)) {
                    questions.setDifficulty(difficulty);
                }
            }
        }
        return Data.success(list);
    }
}
