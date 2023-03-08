
package cn.wstom.exam.controller;


import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 学生试卷列表、用于教师改卷
 *
 * @author hzh
 * @date 20190223
 */

@Controller
@RequestMapping("/school/onlineExam/examList")
public class examListController extends BaseController {
    private String prefix = "school/onlineExam/examList";

    @Autowired
    private UserTestService userTestService;

    @Autowired
    private TestPaperService testPaperService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    StuQuestionScoreService stuQuestionScoreService;
    @Autowired
    CoursetestStuoptionanswerService coursetestStuoptionanswerService;
    @Autowired
    CoursepaperCoursequestionsService coursepaperCoursequestionsService;

    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        return prefix + "/list";
    }


    /**
     * 查询试卷列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, UserTest userTest) {
        startPage();
        userTest.setcId(cid);
        userTest.setType("1");
        List<UserTest> list = userTestService.findStuExamPaper(userTest);
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
                if (list.get(i).getMadeScore().equals(doneFlag)) {
                    list.get(i).setMadeScore(done);
                } else if(list.get(i).getMadeScore().equals(noneFlag)) {
                    list.get(i).setMadeScore(none);
                }

            }
        }
        return wrapTable(list);
    }

    /**
     * 教师改卷
     */

    @GetMapping("/makeScore")
    public String makeScore(String id,String stuNum, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        List<SysUser> sysUserList = adminService.getUserList(sysUser);
        String studentId = "";
        if(sysUserList.size()!=0){
            studentId = sysUserList.get(0).getUserAttrId();
        }
        System.out.println("studentId:"+studentId);
        mmap.put("testPaper", testPaper);
        mmap.put("studentId",studentId);
        mmap.put("sysUser",sysUserList.get(0));
        mmap.put("paperId", id);
        return prefix + "/makeScore";
    }


    /**
     * 改卷时初始化页面
     *
     * @return
     */
    @RequestMapping("/startMakePaper")
    @ResponseBody
    public List<TestpaperQuestions> startMakePaper(String paperId, String studentId, ModelMap mmap) throws Exception {
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        TestPaper testPaper =  testPaperService.getById(paperId);
        if("1".equals(testPaper.getRule())){
            System.out.println("课程考试");
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setTestPaperId(paperId);
            coursepaperCoursequestions.setStudentId(studentId);
            List<CoursepaperCoursequestions> coursepaperCoursequestionsList = new ArrayList<CoursepaperCoursequestions>();
            coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
            for (int i = 0; i < coursepaperCoursequestionsList.size(); i++) {
                System.out.println("题目数量："+coursepaperCoursequestionsList.size());
                TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                if (testpaperQuestions != null) {
                    System.out.println("加入");
                    tqvolist.add(testpaperQuestions);
                }
            }
        }else {
            TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
            testpaperTestquestions.setTestPaperId(paperId);
            List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();
            testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
            for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
                TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                if (testpaperQuestions != null) {
                    tqvolist.add(testpaperQuestions);

                }
            }
        }

        ModelMap mmapcid = new ModelMap();
        String name = getUser().getLoginName();
        TestpaperQuestions tpq = null;
        for (TestpaperQuestions tpqvo : tqvolist) {
            tpq = tpqvo;
            String tpoastr = tpqvo.getTestPaperOptionAnswerArr();
            String[] tpoaArr = tpoastr.substring(0, tpoastr.length() - 1).split(";");
            if ("4".equals(tpq.gettQuestiontemplateNum())) {
                for (String s : tpoaArr) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    toa = testpaperOptinanswerService.getById(s);
                    CoursetestStuoptionanswer coursetestStuoptionanswer = new CoursetestStuoptionanswer();
                    coursetestStuoptionanswer.setCreateId(Integer.parseInt(studentId));
                    coursetestStuoptionanswer.setStuanswer(toa.getStanswer());
                    List<CoursetestStuoptionanswer> stuoa2 = coursetestStuoptionanswerService.list(coursetestStuoptionanswer);
                    if (stuoa2.size() != 0) {
                        CoursetestStuoptionanswer stuoa = stuoa2.get(0);
                        if (stuoa == null) {
                            stuoa = new CoursetestStuoptionanswer();
                            stuoa.setStoption(toa.getStoption());
                            stuoa.setStuanswer("F");
                            coursetestStuoptionanswerService.saveOrUpdate(stuoa);
                        }
                    }

                }
            }
        }
        for (TestpaperQuestions testpaperQuestions : tqvolist) {
            StuQuestionScore stuQuestionScore = new StuQuestionScore();
            stuQuestionScore.setTestPaperQuestion(testpaperQuestions.getId());
            stuQuestionScore.setStuId(studentId);
            List<StuQuestionScore> stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
            if (stuQuestionScoreList.size() != 0) {
                testpaperQuestions.setStuQuestionScore(stuQuestionScoreList.get(0));
            }
            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
            String[] anList = testpaperQuestions.getTestPaperOptionAnswerArr().split(";");
            String[] stuAnList = testpaperQuestions.getTestPaperOptionAnswerArr().split(";");
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
            List<CoursetestStuoptionanswer> coursetestStuoptionanswerListLater = new ArrayList<CoursetestStuoptionanswer>();
            for (String value : anList) {
                testpaperOptinanswer = testpaperOptinanswerService.getById(value);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }
            List<CoursetestStuoptionanswer> coursetestStuoptionanswerList = new ArrayList<CoursetestStuoptionanswer>();
            for (String s : stuAnList) {
                CoursetestStuoptionanswer coursetestStuoptionanswer = new CoursetestStuoptionanswer();
                coursetestStuoptionanswer.setCreateId(Integer.parseInt(studentId));
                coursetestStuoptionanswer.setTestPaperOptionAnswer(s);
                coursetestStuoptionanswerList = coursetestStuoptionanswerService.list(coursetestStuoptionanswer);
                if (coursetestStuoptionanswerList.size() != 0) {
                    coursetestStuoptionanswerListLater.add(coursetestStuoptionanswerList.get(0));
                }
            }

            testpaperQuestions.setCoursetestStuoptionanswerList(coursetestStuoptionanswerListLater);
            testpaperQuestions.setTestpaperOptinanswerList(testpaperOptinanswerList);
        }
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        return tqvolist;
    }



    /**
     * 改卷保存答案
     *
     * @param paperId
     * @param
     */
    @RequestMapping("/saveScore")
    public Data saveScore(String paperId, String studentId) throws Exception {
        int sumscore = 0;
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> listtptq = testpaperTestquestionsService.list(testpaperTestquestions);
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setTestPaperId(paperId);
        userTest.setCreateId(getUser().getUserAttrId());
        UserTest userTest1 = new UserTest();
        List<UserTest> userTestList = userTestService.list(userTest);
        if (userTestList.size() != 0) {
            userTest1 = userTestList.get(0);
        }
        if (listtptq != null) {
            for (TestpaperTestquestions tptq : listtptq) {
                List<StuQuestionScore> stuQuestionScoreList = new ArrayList<StuQuestionScore>();
                StuQuestionScore stuQuestionScore = new StuQuestionScore();
                stuQuestionScore.setStuId(studentId);
                stuQuestionScore.setTestPaperQuestion(tptq.getTestQuestionsId());
                stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);

                if (stuQuestionScoreList.size() != 0) {
                    sumscore += Integer.parseInt(stuQuestionScoreList.get(0).getQuestionScore());
                }
            }
        }
        StuQuestionScore stuQuestionScore = new StuQuestionScore();
        userTest1.setStuScore(String.valueOf(sumscore));
        userTest1.setUpdateBy(getUser().getLoginName());
        userTest1.setUpdateId(getUser().getUserAttrId());
        userTest1.setMadeScore("1");
        return toAjax(userTestService.update(userTest1));
    }


    /**
     * 改卷的时候上传
     *
     * @param
     * @param questionid
     * @param questionscore
     * @return
     */
    @PostMapping("/saveStuScore")
    @ResponseBody
    public StuQuestionScore saveStuScore(String studentId, String questionid, String questionscore, String paperId) {
        StuQuestionScore stuQuestionScore = new StuQuestionScore();
        stuQuestionScore.setCreateId(studentId);
        stuQuestionScore.setStuId(studentId);
        stuQuestionScore.setTestPaperQuestion(questionid);
        List<StuQuestionScore> stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
        if (stuQuestionScoreList.size() != 0) {
            StuQuestionScore stuQuestionScore1 = new StuQuestionScore();
            stuQuestionScore1.setUpdateBy(studentId);
            stuQuestionScore1.setQuestionScore(questionscore);
            stuQuestionScore1.setId(stuQuestionScoreList.get(0).getId());
            try {
                stuQuestionScoreService.update(stuQuestionScore1);
            } catch (Exception e) {
                System.out.println("ERROR:" + e.getCause());
                return null;
            }
        } else {
            StuQuestionScore stuQuestionScore1 = new StuQuestionScore();
            stuQuestionScore1.setCreateId(studentId);
            stuQuestionScore1.setStuId(studentId);
            stuQuestionScore1.setQuestionScore(questionscore);
            stuQuestionScore1.setTestPaperQuestion(questionid);
            try {
                stuQuestionScoreService.save(stuQuestionScore1);

            } catch (Exception e) {
                System.out.println("Error：" + e.getCause());
                return null;
            }
        }

        System.out.println("pap:"+paperId+":::"+studentId);
        int sumscore = 0;
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> listtptq = testpaperTestquestionsService.list(testpaperTestquestions);
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setTestPaperId(paperId);
        userTest.setCreateId(getUser().getUserAttrId());
        UserTest userTest1 = new UserTest();
        List<UserTest> userTestList = userTestService.list(userTest);
        if (userTestList.size() != 0) {
            userTest1 = userTestList.get(0);
        }
        if (listtptq != null) {
            for (TestpaperTestquestions tptq : listtptq) {
                List<StuQuestionScore> stuQuestionScoreList2 = new ArrayList<StuQuestionScore>();
                StuQuestionScore stuQuestionScore2 = new StuQuestionScore();
                stuQuestionScore2.setStuId(studentId);
                stuQuestionScore2.setTestPaperQuestion(tptq.getTestQuestionsId());
                stuQuestionScoreList2 = stuQuestionScoreService.list(stuQuestionScore);

                if (stuQuestionScoreList2.size() != 0) {
                    sumscore += Integer.parseInt(stuQuestionScoreList2.get(0).getQuestionScore());
                }
            }
        }
        StuQuestionScore stuQuestionScore3 = new StuQuestionScore();
        userTest1.setStuScore(String.valueOf(sumscore));
        userTest1.setUpdateBy(getUser().getLoginName());
        userTest1.setUpdateId(getUser().getUserAttrId());
        userTest1.setMadeScore("1");
        return stuQuestionScore;
    }


}




