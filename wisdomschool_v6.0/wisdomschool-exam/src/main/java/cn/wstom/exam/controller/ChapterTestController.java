package cn.wstom.exam.controller;


import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Uuid;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 章节测试
 *
 * @author hzh
 * @date 20190223
 */
@Controller
@RequestMapping("/school/onlineExam/chapterTest")
public class ChapterTestController extends BaseController {
    private String prefix = "school/onlineExam/chapterTest";
    //@Autowired
    //private OnlineSessionDAO onlineSessionDAO;
    @Autowired
    private StuOptionExamanswerService stuOptionExamanswerService;
    @Autowired
    private UserExamService userExamService;

    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;
    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private UserTestService userTestService;
    @Autowired
    private StuOptionanswerService stuOptionanswerService;
    @Autowired
    private CoursepaperCoursequestionsService coursepaperCoursequestionsService;
    @Autowired
    private StuQuestionScoreService stuQuestionScoreService;
    @Autowired
    private UserPaperWorkService userPaperWorkService;

    /**
     * 开始测试 初始化考试页面
     *
     * @return
     */
    @ApiOperation("初始化章节试题界面")
    @RequestMapping("/startCoursePaper")
    @ResponseBody
    public List<TestpaperQuestions> testStart(String paperId, String studentId, String chapterName, ModelMap mmap) throws Exception {
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();
        testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
            }
        }

        TestpaperQuestions tpq = null;
        //  保存学生作答
        for (TestpaperQuestions tpqvo : tqvolist) {
            tpq = tpqvo;
            String tpoastr = tpqvo.getTestPaperOptionAnswerArr();// 题目选项
            String[] tpoaArr = tpoastr.substring(0, tpoastr.length() - 1).split(";");
            if ("4".equals(tpq.gettQuestiontemplateNum())) {
                for (int i = 0; i < tpoaArr.length; i++) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    toa = testpaperOptinanswerService.getById(tpoaArr[i]);
                    StuOptionanswer stuOptionanswer = new StuOptionanswer();
                    String loginid = getUserId();
                    SysUser sysUser = adminService.getUserById(loginid);
                    String uid = sysUser.getUserAttrId();
                    int newId = Integer.parseInt(uid);
                    stuOptionanswer.setCreateId(Integer.valueOf(studentId));
                    stuOptionanswer.setStuAnswer(toa.getStanswer());
                    List<StuOptionanswer> stuoa2 = stuOptionanswerService.list(stuOptionanswer);
                    if (stuoa2.size() != 0) {
                        StuOptionanswer stuoa = stuoa2.get(0);
                        if (stuoa == null) {
                            stuoa = new StuOptionanswer();
                            stuoa.setStoption(toa.getStoption());
                            stuoa.setStuAnswer("F");
                            stuOptionanswerService.saveOrUpdate(stuoa);
                        }
                    }

                }
            }
        }
        for (int i = 0; i < tqvolist.size(); i++) {
            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
            String[] anList = tqvolist.get(i).getTestPaperOptionAnswerArr().split(";");
            String[] stuAnList = tqvolist.get(i).getTestPaperOptionAnswerArr().split(";");
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
            List<StuOptionanswer> stuOptionanswerList = new ArrayList<StuOptionanswer>();
            for (int j = 0; j < anList.length; j++) {
                testpaperOptinanswer = testpaperOptinanswerService.getById(anList[j]);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }
            List<StuOptionanswer> stuOptionanswerList2 = new ArrayList<StuOptionanswer>();
            for (int j = 0; j < stuAnList.length; j++) {
                StuOptionanswer stuOptionanswer = new StuOptionanswer();
                String loginid = getUserId();
                SysUser sysUser = adminService.getUserById(loginid);
                String uid = sysUser.getUserAttrId();
                int newId = Integer.parseInt(uid);
                stuOptionanswer.setCreateId(Integer.valueOf(studentId));
                stuOptionanswer.setTestpaperOptionanswer(stuAnList[j]);
                stuOptionanswerList = stuOptionanswerService.list(stuOptionanswer);
                if (stuOptionanswerList.size() != 0) {
                    stuOptionanswerList2.add(stuOptionanswerList.get(0));
                }
            }
            tqvolist.get(i).setStuOptionanswerList(stuOptionanswerList2);
            tqvolist.get(i).setTestpaperOptinanswerList(testpaperOptinanswerList);
        }
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        return tqvolist;
    }

    /**
     * 开始考试 初始化考试页面
     *
     * @return
     */
    @ApiOperation("初始化考试界面")
    @RequestMapping("/startCourseExamPaper")
    @ResponseBody
    public List<TestpaperQuestions> examStart(String paperId, String studentId,String paperRule, String chapterName, ModelMap mmap) throws Exception {
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        UserExam userExam = userExamService.selectUserExam(paperId,studentId);
        userExamService.update(userExam);
        TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
        testpaperTestquestions.setTestPaperOneListId(paperId);
        List<TestpaperOneTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperOneTestquestions>();
        testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperTestquestions);

        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
            }
        }

        //  填充学生选项
        for (TestpaperQuestions aTqvolist : tqvolist) {
            //  填充选项对象
            if (aTqvolist.getTestPaperOptionAnswerArr()!=null){
                TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                String[] anList1 = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
                List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
                for (String anAnList : anList1) {
                    testpaperOptinanswer = testpaperOptinanswerService.getById(anAnList);
                    if (testpaperOptinanswer != null) {
                        testpaperOptinanswerList.add(testpaperOptinanswer);
                    }
                }

                //  填充学生选项
                List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                stuOptionExamanswer.setCreateId(Integer.valueOf(studentId));
                stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
                stuOptionExamanswerList1 = stuOptionExamanswerService.list(stuOptionExamanswer);//  查询学生作答记录

                if (stuOptionExamanswerList1.size() != 0) {
                    stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                }

                aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录

                aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加问题选项



            }
        }
//        }
//        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        if (paperRule.equals("1")){
            Collections.shuffle(tqvolist);
            Collections.sort(tqvolist,(x,y)->Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));
        }else{
            Collections.sort(tqvolist,(x,y)->Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));
        }
        return tqvolist;
    }



    /**
     * 开始考试 初始化作业界面
     *
     * @return
     */
    @ApiOperation("初始化作业界面")
    @RequestMapping("/startCourseWorkPaper")
    @ResponseBody
    public List<TestpaperQuestions> startCourseWorkPaper(String paperId, String studentId,String paperRule, String chapterName, ModelMap mmap) throws Exception {
        System.out.println("paperRule:"+paperRule);
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        UserPaperWork userExam = userPaperWorkService.selectUserExam(paperId,studentId);
//        userExam.setStuStartTime(new Date());
        userPaperWorkService.update(userExam);
        if (paperRule.equals("1")) {
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setTestPaperId(paperId);
            coursepaperCoursequestions.setStudentId(studentId);
            List<CoursepaperCoursequestions> coursepaperCoursequestionsList = new ArrayList<CoursepaperCoursequestions>();
            coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
            for (int i = 0; i < coursepaperCoursequestionsList.size(); i++) {
                TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                if (testpaperQuestions != null) {
                    tqvolist.add(testpaperQuestions);
                }
                for (TestpaperQuestions aTqvolist : tqvolist) {
                    //  填充选项对象
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    String[] anList = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
                    List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
                    for (String anAnList : anList) {
                        testpaperOptinanswer = testpaperOptinanswerService.getById(anAnList);
                        if (testpaperOptinanswer != null) {
                            testpaperOptinanswerList.add(testpaperOptinanswer);
                        }
                    }

                    //  填充学生选项
                    List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                    List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                    StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                    stuOptionExamanswer.setCreateId(Integer.valueOf(studentId));
                    stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                    stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
                    stuOptionExamanswerList1 = stuOptionExamanswerService.list(stuOptionExamanswer);//  查询学生作答记录

                    if (stuOptionExamanswerList1.size() != 0) {
                        stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                    }

                    aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                    aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项

                }
            }
        }else {
            TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
            testpaperTestquestions.setTestPaperWorkListId(paperId);
            List<TestpaperOneTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperOneTestquestions>();
            testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperTestquestions);

            for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
                TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                if (testpaperQuestions != null) {
                    tqvolist.add(testpaperQuestions);
                }
            }
//        ModelMap mmapcid = new ModelMap();
//        String name = getUser().getLoginName();
//        TestpaperQuestions tpq = null;
//        //  保存学生作答
//        for (TestpaperQuestions tpqvo : tqvolist) {
//            tpq = tpqvo;
//            String tpoastr = tpqvo.getTestPaperOptionAnswerArr();// 题目选项
//            String[] tpoaArr = tpoastr.substring(0, tpoastr.length() - 1).split(";");
//            if ("4".equals(tpq.gettQuestiontemplateNum())) {
//                for (int i = 0; i < tpoaArr.length; i++) {
//                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
//                    toa = testpaperOptinanswerService.getById(tpoaArr[i]);
//                    StuOptionExamanswer stuOptionanswer = new StuOptionExamanswer();
//                    String loginid = getUserId();
//                    System.out.println("loginid"+loginid+"|studentId"+studentId);
//                    SysUser sysUser = sysUserService.getById(loginid);
//                    String uid = sysUser.getUserAttrId();
//                    int newId = Integer.parseInt(uid);
//                    stuOptionanswer.setCreateId(Integer.valueOf(studentId));
//                    stuOptionanswer.setPaperOneId(Integer.parseInt(paperId));
//                    List<StuOptionExamanswer> stuoa2 = stuOptionExamanswerService.list(stuOptionanswer);
//                    System.out.println("stuoa2"+stuoa2);
//
//                    if (stuoa2.size() != 0) {
//                        StuOptionExamanswer stuoa = stuoa2.get(0);
//                        if (stuoa == null) {
//                            stuoa = new StuOptionExamanswer();
//                            stuoa.setStoption(toa.getStoption());
//                            stuoa.setStuAnswer("F");
//                            stuOptionExamanswerService.saveOrUpdate(stuoa);
//                        }
//                    }
//
//                }
//            }
//        }
//        for (int i = 0; i < tqvolist.size(); i++) {
//            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
//            String[] anList = tqvolist.get(i).getTestPaperOptionAnswerArr().split(";");
//            String[] stuAnList = tqvolist.get(i).getTestPaperOptionAnswerArr().split(";");
//            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
//            List<StuOptionExamanswer> stuOptionanswerList = new ArrayList<StuOptionExamanswer>();
//            for (int j = 0; j < anList.length; j++) {
//                testpaperOptinanswer = testpaperOptinanswerService.getById(anList[j]);
//                if (testpaperOptinanswer != null) {
//                    testpaperOptinanswerList.add(testpaperOptinanswer);
//                }
//            }
//            List<StuOptionExamanswer> stuOptionanswerList2 = new ArrayList<StuOptionExamanswer>();
//            for (int j = 0; j < stuAnList.length; j++) {
//                StuOptionExamanswer stuOptionanswer = new StuOptionExamanswer();
//                String loginid = getUserId();
//                SysUser sysUser = sysUserService.getById(loginid);
//                String uid = sysUser.getUserAttrId();
//                int newId = Integer.parseInt(uid);
//                stuOptionanswer.setCreateId(Integer.valueOf(studentId));
//                stuOptionanswer.setTestpaperOptionanswer(stuAnList[j]);
//                stuOptionanswerList = stuOptionExamanswerService.list(stuOptionanswer);
//                if (stuOptionanswerList.size() != 0) {
//                    stuOptionanswerList2.add(stuOptionanswerList.get(0));
//                }
//                System.out.println("stuOptionanswerList"+stuOptionanswerList);
//            }
//            StuOptionExamanswer stuOptionanswer = new StuOptionExamanswer();
//            String loginid = getUserId();
//            System.out.println("loginid"+loginid+"|studentId"+studentId);
//            SysUser sysUser = sysUserService.getById(loginid);
//            String uid = sysUser.getUserAttrId();
//            int newId = Integer.parseInt(uid);
//            stuOptionanswer.setCreateId(Integer.valueOf(studentId));
//            stuOptionanswer.setPaperOneId(Integer.parseInt(paperId));
//            List<StuOptionExamanswer> stuoa2 = stuOptionExamanswerService.list(stuOptionanswer);
//            tqvolist.get(i).setStuOptionExamanswerList(stuoa2);
//            tqvolist.get(i).setTestpaperOptinanswerList(testpaperOptinanswerList);
            //  填充学生选项
            for (TestpaperQuestions aTqvolist : tqvolist) {
                //  填充选项对象
                if (aTqvolist.getTestPaperOptionAnswerArr()!=null){
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    String[] anList1 = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
                    List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
                    for (String anAnList : anList1) {
                        testpaperOptinanswer = testpaperOptinanswerService.getById(anAnList);
                        if (testpaperOptinanswer != null) {
                            testpaperOptinanswerList.add(testpaperOptinanswer);
                        }
                    }

                    //  填充学生选项
                    List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                    List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                    StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                    stuOptionExamanswer.setCreateId(Integer.valueOf(studentId));
                    stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                    stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
                    stuOptionExamanswerList1 = stuOptionExamanswerService.list(stuOptionExamanswer);//  查询学生作答记录

                    if (stuOptionExamanswerList1.size() != 0) {
                        stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                    }

                    aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                    aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项

                }
            }
        }
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        System.out.println("tqvolist:"+tqvolist);
        return tqvolist;
    }

    /**
     * 上传答案
     *
     * @param questionNum 题目总数
     * @param tpquestionId 题目Id
     * @param anwserId 选项Id
     * @param anwserStr 答案
     */
    @ApiOperation("章节试题提交")
    @PostMapping("/saveChapterTestAnswer")
    @ResponseBody
    public String saveChapterTestAnswer(@RequestParam String questionNum, @RequestParam String tpquestionId,
                                        @RequestParam String anwserId, @RequestParam String anwserStr, @RequestParam String studentId) {
        try {

            TestpaperQuestions tpquestion = new TestpaperQuestions();
            boolean istf = true;
            String loginid = getUserId();
            SysUser sysUser = adminService.getUserById(loginid);
            String uid = sysUser.getUserAttrId();
//            Student stuinfo = new Student();
//            stuinfo = studentService.getById(1);
            tpquestion = testpaperQuestionsService.getById(tpquestionId);//获取题目
            String tpoastr = tpquestion.getTestPaperOptionAnswerArr();//获取题目选项Id数组
            String[] tpoaArr = tpoastr.substring(0, tpoastr.length() - 1).split(";");
            String[] mytpoaArr = anwserStr.substring(0, anwserStr.length() - 1).split(";");
            //  循环选项
            for (int i = 0; i < tpoaArr.length; i++) {
                TestpaperOptinanswer tpoa = testpaperOptinanswerService.getById(tpoaArr[i]);//获取选项详细信息
                List<StuOptionanswer> stuoaList = new ArrayList<StuOptionanswer>();
                StuOptionanswer stuoa = new StuOptionanswer();
                stuoa.setCreateId(Integer.valueOf(studentId));
                stuoa.setTestpaperOptionanswer(tpoa.getId());
                stuoaList = stuOptionanswerService.list(stuoa);//查询学生提交记录表
                //  存在则保存否则更新
                if (stuoaList.size() == 0) {
                    stuoa = new StuOptionanswer();
                    stuoa.setTestpaperOptionanswer(tpoa.getId());//答案Id
                    stuoa.setCreateId(Integer.valueOf(studentId));
                    stuoa.setCreateBy(getUser().getLoginName());
                    stuoa.setStuAnswer(mytpoaArr[i]);//学生答案
                    stuoa.setCreateId(Integer.valueOf(studentId));
                    Uuid uid2 = new Uuid();
                    stuoa.setId(uid2.UId());
                    System.out.println(stuoa);
                    stuOptionanswerService.save(stuoa);
                } else {
                    stuoa.setUpdateId(Integer.valueOf(uid));
                    stuoa.setUpdateBy(getUser().getLoginName());
                    stuoa.setTestpaperOptionanswer(tpoa.getId());
                    stuoa.setStuAnswer(mytpoaArr[i]);
                    stuoa.setCreateId(stuoaList.get(0).getCreateId());
                    stuoa.setTestpaperOptionanswer(tpoaArr[i]);
                    stuOptionanswerService.updateByIdAns(stuoa);
                }
                TestpaperOptinanswer tpoa2 = new TestpaperOptinanswer();
                String testPaperQuestionAnsw = stuoa.getTestpaperOptionanswer();
                tpoa2.setId(testPaperQuestionAnsw);
                tpoa2 = testpaperOptinanswerService.getById(testPaperQuestionAnsw);// 查询学生作答的题目
                String typenum = tpquestion.gettQuestiontemplateNum();//获取题型模板
                String str2 = stuoa.getStuAnswer();
                String str1 = "";
                //  对比题目答案和学生答案
                if (!"3".equals(typenum) && !"5".equals(typenum)) {
                    str1 = tpoa2.getStanswer();
                } else {
                    str1 = tpoa2.getStoption();
                }
                if (str1 != null) {
                    if (!str1.equals(str2)) {
                        istf = false;
                    }
                }
            }

//                } else {
//                    System.out.println("没有找到学生");
//                }
        } catch (Exception e) {
            System.out.println(e.getCause());
            return e.getMessage();

        }
        return "End";
    }


    @ApiOperation("章节试题提交")
    @PostMapping("/saveChapterWorkAnswer")
    @ResponseBody
    public Data saveChapterTestAnswer(@RequestBody List<OptionSubmitVo> options) {
        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);
        });

        return toAjax(stuOptionanswerService.saveOptionAnswersByUserId(options, getUserId()));
    }
    @ApiOperation("考试答案保存")
    @RequestMapping("/saveExamWorkAnswer")
    @ResponseBody
    public Data saveChapterExamAnswer(@RequestBody List<OptionExamSubmitVo> options, ServletRequest request)throws Exception {

        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);
        });
        SysUser sysUser =adminService.getUserById(getUserId());
        UserExam userExams = userExamService.selectUserExam(options.get(0).getTestPaperOneId(),getUserId());

        try {
            if ("0".equals(userExams.getSumbitState())){
                return toAjax(stuOptionExamanswerService.saveOptionAnswersByUserId(options, getUserId()));
            }
            System.out.println("`该学生已交卷"+userExams.getUserId());
            return Data.success("该学生已交卷");
        }catch (Exception e){
            UserExam userExam = userExamService.selectUserExam(options.get(0).getTestPaperOneId(),getUserId());
            String startTime = userExam.getStuStartTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(startTime);
            long ts = date.getTime();
            Date date1 = new Date();
            long ts1 = date1.getTime();
            long result = ts + (60 * 1000);
            if (ts1>result){
                userExam.setSumScore("1");//已掉线
                userExamService.update(userExam);
                SysUserOnline info = new SysUserOnline();
                info.setLoginName(sysUser.getLoginName());
                List<SysUserOnline> sysUserOnline = adminService.sysUserOnlineList(info);
                for (SysUserOnline userOnline : sysUserOnline) {
                    adminService.removeSysUserOnlineById(userOnline.getId());
                }
            }else{
                UserExam userExam1 = userExamService.selectUserExam(options.get(0).getTestPaperOneId(),getUserId());
                //if (userExam1.getSumScore().equals("1")){
                //    OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
                //    if (session != null && session.getUserId() != null && session.getStopTimestamp() == null) {
                //        onlineSessionDAO.syncToDb(session);
                //    }
                //}
                userExam.setSumScore("0");
                userExamService.update(userExam);
            }
            System.out.println(e.getCause());
            System.out.println("eeee"+e);
            return Data.error();
        }


    }

    @ApiOperation("考试提交")
    @PostMapping("/submitExam")
    @ResponseBody
    public Data submitExam(@RequestBody List<OptionExamSubmitVo> options, ServletRequest request)throws Exception {

        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);
        });
        UserExam userExams = userExamService.selectUserExam(options.get(0).getTestPaperOneId(),getUserId());
        try {
            if (userExams.getSumbitState().equals("0")){
                return toAjax(stuOptionExamanswerService.submitOptionAnswersByUserId(options, getUserId()));
            }
            return Data.error(1,"您已提交本场考试，不可重复提交！");
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println("eeee"+e);
            return Data.error();
        }

    }

    /**
     * 考试超时自动提交
     * @param options
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation("考试超时自动提交")
    @PostMapping("/autosubmitExam")
    @ResponseBody
    public Data autoSubmitExam(@RequestBody List<OptionExamSubmitVo> options, ServletRequest request)throws Exception {

        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);
        });
        UserExam userExams = userExamService.selectUserExam(options.get(0).getTestPaperOneId(),getUserId());
        try {
            if ("0".equals(userExams.getSumbitState())){
                return toAjax(stuOptionExamanswerService.autoSubmitOptionAnswersByUserId(options, getUserId())); //stuOptionExamanswerService.xml  涉及的数据表：tk_stu_optinexamanswer
            }
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println("eeee"+e);
            return Data.error();
        }
        return Data.error();
    }

    @ApiOperation("作业提交")
    @PostMapping("/submitPaperWork")
    @ResponseBody
    public Data submitPaperWork(@RequestBody List<OptionExamSubmitVo> options, ServletRequest request)throws Exception {
        UserPaperWork userExams = null;
        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            o.setScore(10);
        });
        if (options.size()>0){
            userExams  = userPaperWorkService.selectUserExam(options.get(0).getTestPaperOneId(),getUserId());
        }
        try {
            assert userExams != null;
            if ("0".equals(userExams.getSumbitState())){
                return toAjax(stuOptionExamanswerService.submitPaperWorkOptionAnswersByUserId(options, getUserId()));
            }
            return Data.error(1,"您已提交本次作业，不可重复提交！");
        }catch (Exception e){
            System.out.println(e.getCause());
            System.out.println("eeee"+e);
            return Data.error();
        }


    }

    /**
     * 改卷时初始化页面
     *
     * @return
     */
    @ApiOperation("显示做题情况")
    @RequestMapping("/startMakePaper")
    @ResponseBody
    public List<TestpaperQuestions> startMakePaper(String paperId, String studentId, ModelMap mmap) throws Exception {
        List<TestpaperQuestions> tqvolist = testpaperTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, studentId);
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        return tqvolist;
    }


    @RequestMapping("setLog")
    @ResponseBody
    public Data setLog(String paperId, String studentId, String stuscore,
                       String courseId, String questionsId, String studentQusScore) throws Exception {
        UserTest userTest = new UserTest();
        System.out.println("分数："+stuscore+"111:"+questionsId+"^^^"+studentQusScore);
        userTest.setTestPaperId(paperId);
        userTest.setcId(courseId);
        userTest.setUserId(studentId);
        List<UserTest>userTestList = new ArrayList<UserTest>();
        userTestList = userTestService.list(userTest);
        StuQuestionScore stuQuestionScore  =new StuQuestionScore();
        List<StuQuestionScore> stuQuestionScoreList = new ArrayList<StuQuestionScore>();
        String [] quesId = questionsId.split(";");
        String [] stuScore  = studentQusScore.split(";");
        for (int i = 0; i < quesId.length; i++) {
            stuQuestionScore.setStuId(studentId);
            stuQuestionScore.setTestPaperQuestion(quesId[i]);
            stuQuestionScoreList = stuQuestionScoreService.list(stuQuestionScore);
            if(stuQuestionScoreList.size()!=0){
                System.out.println("更新***************"+stuQuestionScoreList.get(0));
                System.out.println("更新***************"+stuQuestionScoreList.get(0).getId());

                StuQuestionScore stuQuestionScore1 = new StuQuestionScore();
                stuQuestionScore1.setStuId(studentId);
                stuQuestionScore1.setTestPaperQuestion(quesId[i]);
                stuQuestionScore1.setId(stuQuestionScoreList.get(0).getId());
                stuQuestionScore1.setUpdateId(studentId);
                stuQuestionScore1.setQuestionScore(stuScore[i]);
                stuQuestionScoreService.update(stuQuestionScore1);
            }else{
                System.out.println("新增***************");
                StuQuestionScore stuQuestionScore1 = new StuQuestionScore();
                stuQuestionScore1.setStuId(studentId);
                stuQuestionScore1.setTestPaperQuestion(quesId[i]);
                stuQuestionScore1.setCreateId(studentId);
                stuQuestionScore1.setQuestionScore(stuScore[i]);
                stuQuestionScoreService.save(stuQuestionScore1);
            }
        }

        if(userTestList.size()==0){
            UserTest userTest1 = new UserTest();
            userTest1.setTestPaperId(paperId);
            userTest1.setcId(courseId);
            userTest1.setUserId(studentId);
            userTest1.setStuScore(stuscore);
            userTest1.setCreateBy(getUser().getLoginName());
            userTest1.setCreateId(studentId);
            userTest1.setMadeScore("1");
            return toAjax(userTestService.save(userTest1));
        }else {
            UserTest userTest1 = new UserTest();
            userTest1.setTestPaperId(paperId);
            userTest1.setcId(courseId);
            userTest1.setUserId(studentId);
            userTest1.setUpdateBy(getUser().getLoginName());
            userTest1.setUpdateId(studentId);
            userTest1.setStuScore(stuscore);
            userTest1.setMadeScore("1");
            userTest1.setId(userTestList.get(0).getId());
            return toAjax(userTestService.update(userTest1));
        }

    }

}
