
package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.socket.work.CourseWorkSocket;
import cn.wstom.exam.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 课前考试
 *
 * @author lph
 * @date 20190919
 */

@Controller
@RequestMapping("/school/onlineExam/frontExam")
public class FrontExamController extends BaseController {
    private String prefix = "school/onlineExam/frontExam";

    @Autowired
    private UserTestService userTestService;

    @Autowired
    private TestPaperService testPaperService;

    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;
    @Autowired
    private StuQuestionScoreService stuQuestionScoreService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private CoursepaperCoursequestionsService coursepaperCoursequestionsService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;

    @Autowired
    private CoursetestStuoptionanswerService coursetestStuoptionanswerService;
    /**
     * 作业分析
     */
    static ArrayList nameL = new ArrayList();

    private final String PAGER_TYPE = "3";
    /**
     * 课程作业
     *
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        return prefix + "/list";
    }


    /**
     * 获取学生作业
     *
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping("/studentPaper/{cid}")
    public String tostudentPaperList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        modelMap.put("tid", getUser().getUserAttrId());
        return prefix + "/pager_tree";
    }

    /**
     * 构建目录树
     * @param cid
     * @return
     */
    @GetMapping("/studentPagerTree/{cid}")
    @ResponseBody
    public List<Map<String, Object>> getTree(@PathVariable String cid) {
        //  构建树形
        return testPaperService.getTestPagerAndClbumTree(getUser(), cid, PAGER_TYPE);
    }

    /**
     * 目录点击返回视图
     */
    @GetMapping("/studentPagerContent/{courseId}/{pagerId}/{clbumId}")
    public String pagerContent(@PathVariable String courseId, @PathVariable String pagerId, @PathVariable String clbumId, ModelMap modelMap) {
        modelMap.put("courseId", courseId);
        modelMap.put("pageId", pagerId);
        modelMap.put("clbumId", clbumId);
        modelMap.put("tid", getUser().getUserAttrId());
        return prefix + "/stu_paper_list";
    }


    /**
     * 查询学生作业列表
     */


    @PostMapping("/studentPaper/list/{courseId}/{pagerId}/{clbumId}")
    @ResponseBody
    public TableDataInfo paperList(@PathVariable String courseId, @PathVariable String pagerId, @PathVariable String clbumId, UserTest userTest2, TestPaper testPaper) {
        UserTest userTest = new UserTest();
        userTest.setcId(courseId);
        userTest.setTcId(clbumId);
        userTest.setTestPaperId(pagerId);
        userTest.setType(PAGER_TYPE);
        userTest.setCreateId(getUser().getUserAttrId());
        userTest.setStuName(userTest2.getStuName());
        startPage();
        List<UserTest> list = userTestService.findStuExamPaper(userTest);
        return wrapTable(list);
    }


    /**
     * 删除作业
     */

    @Log(title = "作业", actionType = ActionType.DELETE)
    @PostMapping("/studentPaper/remove")
    @ResponseBody
    public Data removeStuPaper(String id, String stuNum) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        List<SysUser> sysUserList = adminService.getUserList(sysUser);
        String studentId = "";
        if (sysUserList.size() != 0) {
            studentId = sysUserList.get(0).getUserAttrId();
        }
        UserTest userTest = new UserTest();
        userTest.setTestPaperId(id);
        userTest.setUserId(studentId);
        List<UserTest> userTestList = userTestService.list(userTest);
        String utestId = "";
        try {
            if (userTestList.size() != 0) {
                utestId = userTestList.get(0).getId();
            }
            return toAjax(userTestService.removeById(utestId));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     * 查询作业列表
     */


    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaper testPaper) {
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("3");
        String sj = "随机";
        String gd = "固定";
        String y = "是";
        String n = "否";
        startPage();
        List<TestPaper> list = testPaperService.list(testPaper);
        for (TestPaper paper : list) {
            if ("0".equals(paper.getRule())) {
                paper.setRule(gd);
            } else {
                paper.setRule(sj);
            }
            if ("0".equals(paper.getSetExam())) {
                paper.setSetExam(n);
            } else {
                paper.setSetExam(y);
            }
            if ("0".equals(paper.getState())) {
                paper.setState(y);
            } else {
                paper.setState(n);
            }

        }
        return wrapTable(list);
    }


    /**
     * 新增作业
     */

    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        System.out.println("success");
        return prefix + "/add";
    }


    /**
     * 新增保存作业
     */


    @Log(title = "作业", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaper testPaper) throws Exception {
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setState("1");
        testPaper.setType(Constants.TEST_PAPER_TYPE_WORK);
        testPaper.setTime("120");
        testPaper.setSetExam(Constants.EXAM_TYPE_CANDLE);
        return toAjax(testPaperService.save(testPaper));
    }


    /**
     * 修改作业
     */

    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存作业
     */


    @Log(title = "作业", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestPaper testPaper) throws Exception {
        return toAjax(testPaperService.update(testPaper));
    }

    /**
     * 删除作业
     */

    @Log(title = "作业", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            return toAjax(testPaperService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 课程 组卷 页面跳转 人手一份
     */
    @GetMapping("/Coursebuild")
    public String Coursebuild(String id, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        String tid = getUser().getUserAttrId();
        mmap.put("cid", testPaper.getCoursrId());
        mmap.put("tid", tid);
        mmap.put("testPaper", testPaper);
        return prefix + "/course_build";
    }







    @Log(title = "启用作业")
    @PostMapping("/setExamY")
    @ResponseBody
    public Data setExamY(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        TestPaper testPaper = new TestPaper();
        testPaper.setSetExam(Constants.EXAM_TYPE_WAIT);
        testPaper.setId(idList.get(0));
        //  通知Socket更新试题
        CourseWorkSocket.notifyNowTimeWorkPaperMap();
        try {
            return toAjax(testPaperService.update(testPaper));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Data.error();
        }
    }


    @Log(title = "取消作业")
    @PostMapping("/setExamN")
    @ResponseBody
    public Data setExamN(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        TestPaper testPaper = new TestPaper();
        testPaper.setSetExam(Constants.EXAM_TYPE_CANDLE);
        testPaper.setId(idList.get(0));
        try {
            return toAjax(testPaperService.update(testPaper));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Data.error();
        }
    }

    /**
     * 分析作业 页面跳转
     *
     * @param id
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping("/analyzePaper")
    public String analyzePaper(String id, String cid, ModelMap modelMap) {
        final String ones = "差";
        final String twos = "较差";
        final String threes = "中等";
        final String fours = "良好";
        final String fives = "优秀";
        nameL.add(ones);
        nameL.add(twos);
        nameL.add(threes);
        nameL.add(fours);
        nameL.add(fives);
        modelMap.put("id", id);
        modelMap.put("cid", cid);
        modelMap.put("nameL", nameL);
        TestPaper testPaper1 = testPaperService.getById(id);
        Integer flag = 0;
        Integer errorFlag = 0;
        List<TestpaperQuestions> testpaperQuestionsList = new ArrayList<>();
        if (testPaper1.getRule().equals("1")) {
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setTestPaperId(id);
            List<CoursepaperCoursequestions> coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
            for (int i = 0; i < coursepaperCoursequestionsList.size(); i++) {
                if (coursepaperCoursequestionsList.get(i).getId() != "" || coursepaperCoursequestionsList.get(i).getId() != null) {

                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        StuQuestionScore stuQuestionScore = new StuQuestionScore();
                        stuQuestionScore.setTestPaperQuestion(testpaperQuestions.getId());
                        List<StuQuestionScore> stuQuestionScoreList = new ArrayList<StuQuestionScore>();
                        stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
                        flag = stuQuestionScoreList.size();
                        testpaperQuestions.setDoneStu(flag);
                        stuQuestionScore.setQuestionScore("0");
                        stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
                        errorFlag = stuQuestionScoreList.size();
                        testpaperQuestions.setErrorDone(errorFlag);
                        testpaperQuestionsList.add(testpaperQuestions);
                    }
                }

            }

        } else {
            TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
            testpaperTestquestions.setTestPaperId(id);
            List<TestpaperTestquestions> testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
            for (TestpaperTestquestions testquestions : testpaperTestquestionsList) {
                if (!"".equals(testquestions.getId()) || testquestions.getId() != null) {
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testquestions.getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        StuQuestionScore stuQuestionScore = new StuQuestionScore();
                        stuQuestionScore.setTestPaperQuestion(testpaperQuestions.getId());
                        List<StuQuestionScore> stuQuestionScoreList = new ArrayList<StuQuestionScore>();
                        stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
                        flag = stuQuestionScoreList.size();
                        testpaperQuestions.setDoneStu(flag);
                        stuQuestionScore.setQuestionScore("0");
                        stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
                        errorFlag = stuQuestionScoreList.size();
                        testpaperQuestions.setErrorDone(errorFlag);
                        testpaperQuestionsList.add(testpaperQuestions);
                    }
                }

            }

        }

        TestPaper testPaper = testPaperService.getById(id);
        modelMap.put("testpaperQuestionsList", testpaperQuestionsList);
        modelMap.put("testPaper", testPaper);
        return prefix + "/analyzePaper";
    }


    /**
     * 获取分析作业的数据，只是计算出来
     * 还没有保存到数据库
     *
     * @param paperId
     * @param userTest
     * @return
     */
    @ResponseBody
    @RequestMapping("/getChartData")
    public UserTest getChartData(String paperId, UserTest userTest) {
        Integer sumscore = 0;
        ModelMap modelMap = new ModelMap();
        userTest.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();
        List<CoursepaperCoursequestions> coursepaperCoursequestionsList = new ArrayList<CoursepaperCoursequestions>();
        List<TestpaperQuestions> testpaperQuestionsList = new ArrayList<TestpaperQuestions>();
        TestPaper testPaper2 = testPaperService.getById(paperId);
        List<UserTest> userTestList = userTestService.list(userTest);
        CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        if ("1".equals(testPaper2.getRule())) {
            coursepaperCoursequestions.setTestPaperId(paperId);
            coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
            modelMap.put("questionList", coursepaperCoursequestionsList);
            CoursepaperCoursequestions coursepaperCoursequestions1 = new CoursepaperCoursequestions();
            coursepaperCoursequestions1.setTestPaperId(paperId);
            List<CoursepaperCoursequestions> coursepaperCoursequestionsList1 = coursepaperCoursequestionsService.list(coursepaperCoursequestions1);
            if (coursepaperCoursequestionsList1.size() != 0) {
                for (CoursepaperCoursequestions coursequestions : coursepaperCoursequestionsList1) {
                    String qid = coursequestions.getTestQuestionsId();
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(qid);
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList.add(testpaperQuestions);
                    }
                }
            }
        } else {
            testpaperTestquestions.setTestPaperId(paperId);
            testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
            modelMap.put("questionList", testpaperTestquestionsList);
            TestpaperTestquestions testpaperTestquestions1 = new TestpaperTestquestions();
            testpaperTestquestions1.setTestPaperId(paperId);
            List<TestpaperTestquestions> testpaperTestquestionsList1 = testpaperTestquestionsService.list(testpaperTestquestions1);
            if (testpaperTestquestionsList1.size() != 0) {
                for (TestpaperTestquestions testquestions : testpaperTestquestionsList1) {
                    String qid = testquestions.getTestQuestionsId();
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(qid);
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList.add(testpaperQuestions);
                    }
                }
            }
        }

        if (testpaperQuestionsList.size() != 0) {
            for (TestpaperQuestions testpaperQuestions : testpaperQuestionsList) {
                sumscore += testpaperQuestions.getQuestionScore();
            }
        }
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        NumberFormat nf = new DecimalFormat("0.0 ");
        ArrayList scoreL = new ArrayList();
        UserTest userTest1 = new UserTest();
        for (UserTest test : userTestList) {
            double score = 0.0;
            double bili = 0.0;
            if (!"".equals(test.getStuScore()) || test.getStuScore() != null) {
                score = Double.parseDouble(test.getStuScore());
                bili = score / sumscore;
                bili = Double.parseDouble(nf.format(bili));
                System.out.println("*****************" + bili);
                if (bili < 0.4) {
                    one += 1;
                } else if ((0.4 <= bili) && (bili < 0.6)) {
                    two += 1;
                } else if ((0.6 <= bili) && (bili < 0.7)) {
                    three += 1;
                } else if ((0.7 <= bili) && (bili < 0.8)) {
                    four += 1;
                } else {
                    five += 1;
                }
            }

        }
        scoreL.add(one);
        scoreL.add(two);
        scoreL.add(three);
        scoreL.add(four);
        scoreL.add(five);
        userTest1.setScore(scoreL);
        userTest1.setSpaceName(nameL);
        List<TestpaperQuestions> testpaperQuestionsList1 = new ArrayList<>();
        if (testPaper2.getRule().equals("1")) {
            for (int i = 0; i < coursepaperCoursequestionsList.size(); i++) {
                if (!"".equals(coursepaperCoursequestionsList.get(i).getId()) || coursepaperCoursequestionsList.get(i).getId() != null) {
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList1.add(testpaperQuestions);
                    }
                }
            }
        } else {
            for (TestpaperTestquestions testquestions : testpaperTestquestionsList) {
                if (!"".equals(testquestions.getId()) || testquestions.getId() != null) {
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testquestions.getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList1.add(testpaperQuestions);
                    }
                }
            }
        }

        userTest1.setTestpaperQuestionsList(testpaperQuestionsList);
        return userTest1;
    }


//    /**
//     * 教师改卷
//     */
//    @ApiOperation("作业修改界面")
//    @GetMapping("/makeScore")
//    public String makeScore(String id, String stuNum, ModelMap mmap) {
//        TestPaper testPaper = testPaperService.getById(id);
//        SysUser sysUser = new SysUser();
//        sysUser.setLoginName(stuNum);
//        List<SysUser> sysUserList = sysUserService.list(sysUser);
//        String studentId = "";
//        if (sysUserList.size() != 0) {
//            studentId = sysUserList.get(0).getUserAttrId();
//        }
//        System.out.println("studentId:" + studentId);
//        mmap.put("testPaper", testPaper);
//        mmap.put("studentId", studentId);
//        mmap.put("sysUser", sysUserList.get(0));
//        mmap.put("paperId", id);
//        return prefix + "/makeScore";
//    }


    /**
     * 改卷时初始化页面
     *
     * @return
     */
    @RequestMapping("/startMakePaper")
    @ResponseBody
    public List<TestpaperQuestions> startMakePaper(String paperId, String studentId, ModelMap mmap) throws Exception {
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        TestPaper testPaper = testPaperService.getById(paperId);//  获取试卷
        if (testPaper.getRule().equals("1")) {  // rule  ==  1 ？？？
            System.out.println("课程考试");
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setTestPaperId(paperId);
            coursepaperCoursequestions.setStudentId(studentId);
            List<CoursepaperCoursequestions> coursepaperCoursequestionsList = new ArrayList<CoursepaperCoursequestions>();
            coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
            for (int i = 0; i < coursepaperCoursequestionsList.size(); i++) {
                System.out.println("题目数量：" + coursepaperCoursequestionsList.size());
                TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                if (testpaperQuestions != null) {
                    System.out.println("加入");
                    tqvolist.add(testpaperQuestions);
                }
            }
        } else {
            TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
            testpaperTestquestions.setTestPaperId(paperId);
            List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();
            testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
            for (TestpaperTestquestions testquestions : testpaperTestquestionsList) {
                TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                testpaperQuestions = testpaperQuestionsService.getById(testquestions.getTestQuestionsId());
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
            for (String s : anList) {
                testpaperOptinanswer = testpaperOptinanswerService.getById(s);
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
     * 改卷保存
     *
     * @param paperId
     * @param
     */
    @RequestMapping("/saveScore")
    public Data saveScore(String paperId, String studentId) throws Exception {
        int sumscore = 0;
        TestPaper testPaper = new TestPaper();
        testPaper = testPaperService.getById(paperId);
        List<TestpaperTestquestions> listtptq = new ArrayList<TestpaperTestquestions>();
        List<CoursepaperCoursequestions> Courselisttptq = new ArrayList<CoursepaperCoursequestions>();
        if (testPaper.getRule().equals("1")) {
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setStudentId(studentId);
            coursepaperCoursequestions.setTestPaperId(paperId);
            Courselisttptq = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
        } else {
            TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
            testpaperTestquestions.setTestPaperId(paperId);
            listtptq = testpaperTestquestionsService.list(testpaperTestquestions);
        }
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setTestPaperId(paperId);
        userTest.setCreateId(getUser().getUserAttrId());
        UserTest userTest1 = new UserTest();
        List<UserTest> userTestList = userTestService.list(userTest);
        if (userTestList.size() != 0) {
            userTest1 = userTestList.get(0);
        }
        if ((listtptq != null) && (testPaper.getRule().equals("0"))) {
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
        if ((Courselisttptq != null) && (testPaper.getRule().equals("1"))) {
            for (CoursepaperCoursequestions tptq : Courselisttptq) {
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

        int sumscore = 0;
        TestPaper testPaper = new TestPaper();
        testPaper = testPaperService.getById(paperId);
        List<TestpaperTestquestions> listtptq = new ArrayList<TestpaperTestquestions>();
        List<CoursepaperCoursequestions> Courselisttptq = new ArrayList<CoursepaperCoursequestions>();

        if (testPaper.getRule().equals("1")) {
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setTestPaperId(paperId);
            coursepaperCoursequestions.setStudentId(studentId);
            Courselisttptq = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
        } else {
            TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
            testpaperTestquestions.setTestPaperId(paperId);
            listtptq = testpaperTestquestionsService.list(testpaperTestquestions);
        }
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setTestPaperId(paperId);
        userTest.setCreateId(getUser().getUserAttrId());
        UserTest userTest1 = new UserTest();
        List<UserTest> userTestList = userTestService.list(userTest);
        if (userTestList.size() != 0) {
            userTest1 = userTestList.get(0);
        }
        if ((listtptq != null) && (testPaper.getRule().equals("0"))) {
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
        if ((Courselisttptq != null) && (testPaper.getRule().equals("1"))) {
            for (CoursepaperCoursequestions tptq : Courselisttptq) {
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


    /**
     * 页面跳转  预览作业
     *
     * @return
     */
    @GetMapping("/showPaper")
    public String showPaper(String id, String stuNum, ModelMap modelMap) {
        TestPaper testPaper = testPaperService.getById(id);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        List<SysUser> sysUserList = adminService.getUserList(sysUser);
        String studentId = "";
        if (sysUserList.size() != 0) {
            studentId = sysUserList.get(0).getUserAttrId();
        }
        modelMap.put("studentId", studentId);
        modelMap.put("paperId", id);
        TestPaper testPaper1 = testPaperService.getById(id);
        modelMap.put("testPaper", testPaper1);
        return prefix + "/showPaper";
    }

    /**
     * 获取班级
     */
    @PostMapping("/getTcoName")
    @ResponseBody
    public List<UserTest> getTcoName(String cId, String tid, String tgId) {
        UserTest userTest = new UserTest();
        String crid = getUser().getUserAttrId();
        userTest.setCreateId(crid);
        userTest.setTcoId(cId);
        userTest.setTgId(tgId);
        return userTestService.getTcoName2(crid, cId, tgId);
    }


    @Log(title = "随机组卷")
    @PostMapping("/isSui")
    @ResponseBody
    public Data suiji(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        try {
            String thisId;
            if(idList.size()!=0){
                thisId=idList.get(0);
            }else {
                return Data.error();
            }
            TestPaper testPaper = new TestPaper();
            testPaper = testPaperService.getById(thisId);
            if (testPaper != null) {
                if (testPaper.getRule().equals("1")) {
                    return Data.success();
                } else {
                    return Data.error();
                }
            }
            return Data.error();
        } catch (Exception e) {
            System.out.println(e.getCause()+":::"+e.getMessage());
            return Data.error("后台出错");
        }
    }
}




