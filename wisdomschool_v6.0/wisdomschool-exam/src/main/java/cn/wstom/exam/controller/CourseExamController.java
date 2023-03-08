
package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.socket.exam.CourseExamSocket;
import cn.wstom.exam.utils.Convert;
import io.swagger.annotations.ApiOperation;
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
 * 课程考试
 *
 * @author hzh
 * @date 20190223
 */

@Controller
@RequestMapping("/school/onlineExam/courseExam")
public class CourseExamController extends BaseController {
    private String prefix = "school/onlineExam/courseExam";

    @Autowired
    private UserTestService userTestService;

    @Autowired
    private TestPaperService testPaperService;
    @Autowired
    private AdminService adminService;
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
     * 试卷分析
     */
    static ArrayList nameL = new ArrayList();

    private final String PAGER_TYPE = "2";

    /**
     * 课程试卷
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
     * 获取学生试卷
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
     * 查询学生试卷列表 31/37/66
     */

    @PostMapping("/studentPaper/list/{courseId}/{pagerId}/{clbumId}")
    @ResponseBody
    public TableDataInfo paperList(@PathVariable String courseId, @PathVariable String pagerId, @PathVariable String clbumId, UserTest userTest2, TestPaper testPaper) {
        startPage();
        UserTest userTest = new UserTest();
        userTest.setcId(courseId);
        userTest.setTcId(clbumId);
        userTest.setTestPaperId(pagerId);
        userTest.setType(PAGER_TYPE);
        userTest.setCreateId(getUser().getUserAttrId());
        userTest.setStuName(userTest2.getStuName());
        List<UserTest> list = userTestService.findStuExamPaper(userTest);
        return wrapTable(list);
    }


    /**
     * 删除试卷
     */
    @Log(title = "试卷", actionType = ActionType.DELETE)
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
     * 查询试卷列表
     */


    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaper testPaper) {
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("2");
        String sj = "随机";
        String gd = "固定";
        startPage();
        List<TestPaper> list = testPaperService.list(testPaper);
        //  List<TestPaper> list =testPaperService.findStuPaper(testPaper);
        return wrapTable(list);
    }


    /**
     * 新增试卷
     */

    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        System.out.println("success");
        return prefix + "/add";
    }


    /**
     * 新增保存试卷
     */


    @Log(title = "试卷", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaper testPaper) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>" + testPaper);
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setState("1");
        testPaper.setType(Constants.TEST_PAPER_TYPE_EXAM);//  设置考卷状态
        testPaper.setSetExam(Constants.EXAM_TYPE_CANDLE);// 设置考卷状态
        return toAjax(testPaperService.save(testPaper));
    }


    /**
     * 修改试卷
     */

    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        System.out.println("跳转");
        TestPaper testPaper = testPaperService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷
     */


    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestPaper testPaper) throws Exception {
        System.out.println("编辑");
        return toAjax(testPaperService.update(testPaper));
    }

    /**
     * 删除试卷
     */

    @Log(title = "试卷", actionType = ActionType.DELETE)
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


    /**
     *  设置考试时间
     * @param testpaperId
     * @param startTime
     * @param endTime
     * @return
     */

    @ApiOperation("设置考试时间")
    @RequestMapping(value = "/setExamTime/{testpaperId}", method = RequestMethod.POST)
    @ResponseBody
    public Data setExamTime(@PathVariable("testpaperId") String testpaperId, String startTime, String endTime) {
        System.out.println(testpaperId + startTime + endTime);
        TestPaper testPaper = new TestPaper();
        testPaper.setStartTime(startTime);
        testPaper.setEndTime(endTime);
        testPaper.setId(testpaperId);
        try {
            CourseExamSocket.notifyNowTimeExamPaperMap();// 通知更新
            return toAjax(testPaperService.update(testPaper));
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error();
        }
    }


    @GetMapping("/setExamTime/{testpaperId}")
    public String setExamTime(@PathVariable("testpaperId") String testpaperId, ModelMap modelMap) {
        modelMap.put("paperId", testpaperId);
        return prefix + "/time_setting";
    }


    @Log(title = "启用考试")
    @PostMapping("/setExamY")
    @ResponseBody
    public Data setExamY(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        TestPaper testPaper = new TestPaper();
        testPaper.setSetExam(Constants.EXAM_TYPE_WAIT);
        testPaper.setId(idList.get(0));
        try {
            toAjax(testPaperService.update(testPaper));
            CourseExamSocket.notifyNowTimeExamPaperMap();
            return Data.success();
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Data.error();
        }
    }


    @Log(title = "取消考试")
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
     * 分析试卷 页面跳转
     *
     * @param id
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping("/analyzePaper")
    public String analyzePaper(String id, String cid, ModelMap modelMap) {
        System.out.println("zhelizheli**************");
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
            for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
                if (testpaperTestquestionsList.get(i).getId() != "" || testpaperTestquestionsList.get(i).getId() != null) {

                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
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

        for (int i = 0; i < testpaperQuestionsList.size(); i++) {
            System.out.println(testpaperQuestionsList.get(i).getTitle());
        }
        modelMap.put("testpaperQuestionsList", testpaperQuestionsList);
        modelMap.put("testPaper", testPaper);
        return prefix + "/analyzePaper";
    }


    /**
     * 获取分析试卷的数据，只是计算出来
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
        if (testPaper2.getRule().equals("1")) {
            coursepaperCoursequestions.setTestPaperId(paperId);
            coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
            modelMap.put("questionList", coursepaperCoursequestionsList);
            CoursepaperCoursequestions coursepaperCoursequestions1 = new CoursepaperCoursequestions();
            coursepaperCoursequestions1.setTestPaperId(paperId);
            List<CoursepaperCoursequestions> coursepaperCoursequestionsList1 = coursepaperCoursequestionsService.list(coursepaperCoursequestions1);
            if (coursepaperCoursequestionsList1.size() != 0) {
                for (int i = 0; i < coursepaperCoursequestionsList1.size(); i++) {
                    String qid = coursepaperCoursequestionsList1.get(i).getTestQuestionsId();
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
                for (int i = 0; i < testpaperTestquestionsList1.size(); i++) {
                    String qid = testpaperTestquestionsList1.get(i).getTestQuestionsId();
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(qid);
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList.add(testpaperQuestions);
                    }
                }
            }
        }

        if (testpaperQuestionsList.size() != 0) {
            for (int i = 0; i < testpaperQuestionsList.size(); i++) {
                sumscore += testpaperQuestionsList.get(i).getQuestionScore();
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
        for (int i = 0; i < userTestList.size(); i++) {
            double score = 0.0;
            double bili = 0.0;
            if (userTestList.get(i).getStuScore() != "" || userTestList.get(i).getStuScore() != null) {
                score = Double.parseDouble(userTestList.get(i).getStuScore());
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
                if (coursepaperCoursequestionsList.get(i).getId() != "" || coursepaperCoursequestionsList.get(i).getId() != null) {
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList1.add(testpaperQuestions);
                    }
                }
            }
        } else {
            for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
                if (testpaperTestquestionsList.get(i).getId() != "" || testpaperTestquestionsList.get(i).getId() != null) {
                    TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        testpaperQuestionsList1.add(testpaperQuestions);
                    }
                }
            }
        }

        userTest1.setTestpaperQuestionsList(testpaperQuestionsList);
        return userTest1;
    }


    /**
     * 教师改卷
     */

    @GetMapping("/makeScore")
    public String makeScore(String id, String stuNum, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        List<SysUser> sysUserList = adminService.getUserList(sysUser);
        String studentId = "";
        if (sysUserList.size() != 0) {
            studentId = sysUserList.get(0).getUserAttrId();
        }
        System.out.println("studentId:" + studentId);
        mmap.put("testPaper", testPaper);
        mmap.put("studentId", studentId);
        mmap.put("sysUser", sysUserList.get(0));
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
        String studentID = studentId;
        TestPaper testPaper = testPaperService.getById(paperId);
        if (testPaper.getRule().equals("1")) {
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
                for (int i = 0; i < tpoaArr.length; i++) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    toa = testpaperOptinanswerService.getById(tpoaArr[i]);
                    CoursetestStuoptionanswer coursetestStuoptionanswer = new CoursetestStuoptionanswer();
                    coursetestStuoptionanswer.setCreateId(Integer.parseInt(studentID));
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
        for (int i = 0; i < tqvolist.size(); i++) {
            StuQuestionScore stuQuestionScore = new StuQuestionScore();
            stuQuestionScore.setTestPaperQuestion(tqvolist.get(i).getId());
            stuQuestionScore.setStuId(studentID);
            List<StuQuestionScore> stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
            if (stuQuestionScoreList.size() != 0) {
                tqvolist.get(i).setStuQuestionScore(stuQuestionScoreList.get(0));
            }
            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
            String[] anList = tqvolist.get(i).getTestPaperOptionAnswerArr().split(";");
            String[] stuAnList = tqvolist.get(i).getTestPaperOptionAnswerArr().split(";");
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
            List<CoursetestStuoptionanswer> coursetestStuoptionanswerListLater = new ArrayList<CoursetestStuoptionanswer>();
            for (int j = 0; j < anList.length; j++) {
                testpaperOptinanswer = testpaperOptinanswerService.getById(anList[j]);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }
            List<CoursetestStuoptionanswer> coursetestStuoptionanswerList = new ArrayList<CoursetestStuoptionanswer>();
            for (int j = 0; j < stuAnList.length; j++) {
                CoursetestStuoptionanswer coursetestStuoptionanswer = new CoursetestStuoptionanswer();
                coursetestStuoptionanswer.setCreateId(Integer.parseInt(studentID));
                coursetestStuoptionanswer.setTestPaperOptionAnswer(stuAnList[j]);
                coursetestStuoptionanswerList = coursetestStuoptionanswerService.list(coursetestStuoptionanswer);
                if (coursetestStuoptionanswerList.size() != 0) {
                    coursetestStuoptionanswerListLater.add(coursetestStuoptionanswerList.get(0));
                }
            }

            tqvolist.get(i).setCoursetestStuoptionanswerList(coursetestStuoptionanswerListLater);
            tqvolist.get(i).setTestpaperOptinanswerList(testpaperOptinanswerList);
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

        System.out.println("pap:" + paperId + ":::" + studentId);
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
     * 页面跳转  预览试卷
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
            System.out.println("######ids:"+ids);
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




