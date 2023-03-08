package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.entity.Individual.Individual;
import cn.wstom.exam.entity.Individual.QuestionData;
import cn.wstom.exam.entity.Individual.QuestionPaper;
import cn.wstom.exam.entity.Individual.QuestionType;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Convert;
import cn.wstom.exam.utils.Utility;
import cn.wstom.exam.utils.Uuid;
import com.github.pagehelper.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 教师课程
 *
 * @author dws
 * @date 2019/02/18
 */
@Controller
@RequestMapping("/school/onlineExam/course")
public class OnlineExamController extends BaseController {
    private String prefix = "school/onlineExam/onlineExam";
    /**
     * 每种题型的分数
     */
    static String[] typescore;
    /**
     * 存放手动组卷传来的试题
     */
    static String[] questionsid;
    /**
     * 试卷List
     */
    static List<MyQuestions> qList = new ArrayList<MyQuestions>();
    /**
     * 试卷List AGA组卷用
     */
    static List<MyQuestions> AGAqList = new ArrayList<MyQuestions>();
    /**
     * 试卷分析
     */
    static ArrayList nameL = new ArrayList();
    /**
     * 试题类型id
     */
    private String[] types;

    private String[] chapterIds;
    /**
     * 试题类型对应的数量
     */
    private String[] typesAmount;
    static String mytestid="";
    /**
     * 页面操作类型，展示或编辑
     */
    private static final int OPT_TYPE_VIEW = 0;
    private static final int OPT_TYPE_EDIT = 1;


    @Autowired
    private TestPaperService testPaperService;
    @Autowired
    private MyQuestionsService myQuestionsService;
    @Autowired
    private MyOptionAnswerService myOptionAnswerService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private UserTestService userTestService;
    @Autowired
    private CoursepaperCoursequestionsService coursepaperCoursequestionsService;




    /**
     * 获取年级
     */
    @PostMapping("/getQStudents")
    @ResponseBody
    public List<UserTest> getQStudents(String cId, String tid)
    /*UserTest userTest 以后要获取老师和老师绑定的课程*/ {
        //获取老师和老师绑定的课程来获取年级，此处是做测试使用
        UserTest userTest = new UserTest();
        String tid1 = getUser().getUserAttrId();
        userTest.setCreateId(tid1);
        userTest.setTcoId(cId);
        return userTestService.getQStudentInfo(cId);

    }

    /**
     * 获取班级
     */
    @PostMapping("/getQTcoName")
    @ResponseBody
    public List<UserTest> getQTcoName(String cId, String tid) {
        UserTest userTest = new UserTest();;
        String tid1 = getUser().getUserAttrId();
        userTest.setCreateId(tid1);
        userTest.setTcoId(cId);
        List<UserTest> list = userTestService.getQTcoName(cId);
        System.out.println(list);
        return userTestService.getQTcoName(cId);

    }

    /**
     * 获取学生
     */
    @PostMapping("/getQTcoStu")
    @ResponseBody
    public List<UserTest> getQTcoStu(String cId, String tid) {
        UserTest userTest = new UserTest();
        String tid1 = getUser().getUserAttrId();
        userTest.setCreateId(tid1);
        userTest.setTcoId(cId);
        List<UserTest> userTests = userTestService.getQTcoStu(cId);
        return userTestService.getQTcoStu(cId);

    }

    /**
     * 获取试卷列表
     * @return
     */

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 试卷资源
     * @param cid
     * @param modelMap
     * @return
     */

    @GetMapping("/getExamPaper/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/paperList";
    }

    /**
     * 期末试卷 选择学生跳转
     * @param id
     * @param cid
     * @param modelMap
     * @return
     */

    @GetMapping("/show")
    public String show(String id, String cid, ModelMap modelMap) {

        String tid = getUser().getUserAttrId();
        modelMap.put("cid", cid);
        modelMap.put("id", id);
        modelMap.put("tid", tid);
        return prefix + "/selectStudent";
    }

    /**
     * 获取我的课程列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        TeacherCourse tc = new TeacherCourse();
        tc.setTid(getUser().getUserAttrId());
        startPage();
        List<TeacherCourse> list = adminService.teacherCourseList(tc);
        PageInfo<TeacherCourse> pageInfo = new PageInfo(list);
        List<TeacherCourseVo> tcVo = trans(list);
        return wrapTable(tcVo, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    /**
     * 获取课程页面
     *
     * @param opt 操作类型 见变量说明
     * @return 课程页面或课程信息路径
     */

    @GetMapping("/get/{cid}/{opt}")
    public String course(@PathVariable(required = false) Integer opt, @PathVariable String cid, ModelMap modelMap) {

        TeacherCourseVo tcVo = new TeacherCourseVo();
        TeacherCourse teacherCourse=new TeacherCourse();
        teacherCourse.setCid(cid);
        teacherCourse.setTid(getUser().getUserAttrId());
        TeacherCourse tc = adminService.getTeacherCourse(teacherCourse);
        Assert.notNull(tc, "非法参数！");
        BeanUtils.copyProperties(tc, tcVo);
        Course course = adminService.getCourseById(tcVo.getCid());
        tcVo.setCourse(course);
        modelMap.put("teacherCourse", tcVo);
        if (opt == OPT_TYPE_EDIT) {
            return prefix + "/info";
        }
        return prefix + "/course";
    }

    /**
     * 查询试卷列表
     */

    @PostMapping("/PaperList/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaper testPaper) {
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        List<TestPaper> list = testPaperService.list(testPaper);
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "0";
            final String none = "否";
            final String noneFlag = "1";
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getState().equals(doneFlag)) {
                    list.get(i).setState(done);
                } else if (list.get(i).getState().equals(noneFlag)) {
                    list.get(i).setState(none);
                }
            }
        }
        return wrapTable(list);
    }


    /**
     * 新增期末试卷
     */
    @GetMapping("/Paperadd/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        System.out.println("add");
        modelMap.put("id", cid);
        return prefix + "/add";
    }

    /**
     * 分析试卷 页面跳转
     * @param id
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping("/analyzePaper")
    public String analyzePaper(String id, String cid, ModelMap modelMap) {
        final String ones = "0-20%";
        final String twos = "20%-40%";
        final String threes = "40%-60%";
        final String fours = "60%-80%";
        final String fives = "80%-100%";
        nameL.add(ones);
        nameL.add(twos);
        nameL.add(threes);
        nameL.add(fours);
        nameL.add(fives);
        modelMap.put("id", id);
        modelMap.put("cid", cid);
        modelMap.put("nameL", nameL);
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(id);
        List<TestpaperTestquestions> testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> testpaperQuestionsList = new ArrayList<>();
        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            if(testpaperTestquestionsList.get(i).getId()!=""||testpaperTestquestionsList.get(i).getId()!=null) {

                TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                if(testpaperQuestions!=null){
                    testpaperQuestionsList.add(testpaperQuestions);
                }
            }
        }
        TestPaper testPaper = testPaperService.getById(id);
        for (int i = 0; i < testpaperQuestionsList.size(); i++) {
            System.out.println(testpaperQuestionsList.get(i).getTitle());
        }
        modelMap.put("testpaperQuestionsList",testpaperQuestionsList);
        modelMap.put("testPaper",testPaper);
        return prefix + "/analyzePaper";
    }

    /**
     *  分析试卷时，获取期末试卷的试题
     * @param id
     * @param cid
     * @param modelMap
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestion")
    public List<TestpaperQuestions> getQuestion(String id, String cid, ModelMap modelMap) {
        final String ones = "60以下";
        final String twos = "60-69";
        final String threes = "70-79";
        final String fours = "80-89";
        final String fives = "90以上";
        nameL.add(ones);
        nameL.add(twos);
        nameL.add(threes);
        nameL.add(fours);
        nameL.add(fives);
        modelMap.put("id", id);
        modelMap.put("cid", cid);
        modelMap.put("nameL", nameL);
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(id);
        List<TestpaperTestquestions> testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> testpaperQuestionsList = new ArrayList<>();
        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            if(testpaperTestquestionsList.get(i).getId()!=""||testpaperTestquestionsList.get(i).getId()!=null) {
                TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                if(testpaperQuestions!=null){
                    testpaperQuestionsList.add(testpaperQuestions);
                }
            }
        }
        TestPaper testPaper = testPaperService.getById(id);
        for (int i = 0; i < testpaperQuestionsList.size(); i++) {
            System.out.println(testpaperQuestionsList.get(i).getTitle());
        }
        return testpaperQuestionsList;
    }


    /**
     * 获取分析试卷的数据，只是计算出来
     * 还没有保存到数据库
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
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();
        testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
        modelMap.put("questionList", testpaperTestquestionsList);
        List<UserTest> userTestList = userTestService.list(userTest);
        TestpaperTestquestions testpaperTestquestions1 = new TestpaperTestquestions();
        testpaperTestquestions1.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList1 = testpaperTestquestionsService.list(testpaperTestquestions1);
        List<TestpaperQuestions> testpaperQuestionsList = new ArrayList<TestpaperQuestions>();
        if(testpaperTestquestionsList1.size()!=0){
            for (int i = 0; i < testpaperTestquestionsList1.size(); i++) {
                String qid = testpaperTestquestionsList1.get(i).getTestQuestionsId();
                TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(qid);
                if(testpaperQuestions!=null){
                    testpaperQuestionsList.add(testpaperQuestions);
                }
            }
        }
       if(testpaperQuestionsList.size()!=0){
           for (int i = 0; i < testpaperQuestionsList.size(); i++) {
               sumscore+=testpaperQuestionsList.get(i).getQuestionScore();
           }
       }
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        int five = 0;
        NumberFormat   nf=new DecimalFormat( "0.0 ");
        ArrayList scoreL = new ArrayList();
        UserTest userTest1 = new UserTest();
        for (int i = 0; i < userTestList.size(); i++) {
            int score = 0;
            double bili = 0.0;
            if (userTestList.get(i).getStuScore() != "") {
                score = Integer.parseInt(userTestList.get(i).getStuScore());
                bili = score/sumscore;
                bili = Double.parseDouble(nf.format(bili));
            }
            if ( bili < 0.2) {
                one += 1;
            }
            if (0.2 <= bili && bili < 0.4) {
                two += 1;
            }
            if (0.4 <= bili && bili < 0.6) {
                three += 1;
            }
            if (0.6 <= bili && bili < 0.8) {
                four += 1;
            }
            if ( 0.8>=bili) {
                five += 1;
            }
        }
        scoreL.add(one);
        scoreL.add(two);
        scoreL.add(three);
        scoreL.add(four);
        scoreL.add(five);
        userTest1.setScore(scoreL);
        userTest1.setSpaceName(nameL);
        TestpaperTestquestions testpaperTestquestions2 = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList2 = testpaperTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> testpaperQuestionsList1 = new ArrayList<>();
        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            if(testpaperTestquestionsList.get(i).getId()!=""||testpaperTestquestionsList.get(i).getId()!=null) {
                TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                if(testpaperQuestions!=null){
                    testpaperQuestionsList1.add(testpaperQuestions);
                }
            }
        }
        TestPaper testPaper = testPaperService.getById(paperId);
        userTest1.setTestpaperQuestionsList(testpaperQuestionsList);
        return userTest1;
    }

    /**
     * 新增保存期末试卷
     */

    @Log(title = "试卷", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaper testPaper) throws Exception {
        System.out.println(testPaper.getCoursrId());
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setState("1");
        testPaper.setType("1");
        return toAjax(testPaperService.save(testPaper));
    }


    /**
     * 修改试卷
     */
    @GetMapping("/Paperedit/{id}")


    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
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
     * 课程考试（平时作业形式 ） 组卷
     * 页面跳转
     */
    @GetMapping("/buileTest")

    public String buileTest(String id, String cid, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("id", id);
        mmap.put("cid", cid);
        return prefix + "/selectList";
    }

    /**
     * AGA设置考试
     * 页面跳转
     */
    @GetMapping("/AGABuileTest")

    public String AGABuileTest(String id, String cid, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("id", id);
        return prefix + "/selectAGAList";
    }

    /**
     * 人工组卷
     *
     * @param finalQid
     * @param score
     * @param mmap
     * @return
     */
    @PostMapping("/humanBuild")

    @ResponseBody
    public List<MyQuestions> humanBuild(String finalQid, String score, ModelMap mmap) {
        System.out.println("231231231231");
        questionsid = finalQid.substring(0, finalQid.length() - 1).split(",");
        String[] questionScor = score.split(";");
        //为了方便 这里的qList用的是静态变量 不置空的话在次手动选题目的时候会出现上次的题目
        if (qList.size() != 0) {
            qList.removeAll(qList);
        }
        for (int i = 0; i < questionsid.length; i++) {
            MyQuestions myQuestions = myQuestionsService.getById(questionsid[i]);
            for (int j = 0; j < questionScor.length; j++) {
                String[] titleAndScore = questionScor[j].split(":");
                if (myQuestions.getTitleTypeId().equals(titleAndScore[0])) {
                    myQuestions.setQuestionscore(Integer.parseInt(titleAndScore[1]));
                }
            }
            qList.add(myQuestions);
        }
        for (int i = 0; i < qList.size(); i++) {
            System.out.println(qList.get(i).getTitleTypeName());
        }
        qList.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        for (int i = 0; i < qList.size(); i++) {
            System.out.println(qList.get(i).getTitleTypeName());
        }
        return qList;
    }



    /**
     * 展示手动组卷试卷
     *
     * @param mmap
     * @return
     */
    @PostMapping("/buildTestStepTwo")

    @ResponseBody
    public List<QuestionPaper> buildTestStepTwo(ModelMap mmap) {
        List<QuestionPaper> questions = new ArrayList<QuestionPaper>();
        for (int i = 0; i < qList.size(); i++) {
            QuestionPaper up = new QuestionPaper();
            List<String> tarr = new ArrayList<String>();
            List<String> tList = new ArrayList<String>();
            up.setTid(qList.get(i).getId());
            up.setTitle(qList.get(i).getTitle());
            up.setTitleType(qList.get(i).getTitleTypeName());
            up.setNumber(qList.get(i).getTitleTypeId());
            up.setDifficulty(qList.get(i).getDifficulty());
            up.setQexposed(qList.get(i).getQexposed());
            up.setScore(qList.get(i).getQuestionscore());
            for (int j = 0; j < tList.size(); j++) {
                if (tList.get(j) != null) {
                    tarr.add(tList.get(j));
                }
            }
            up.setOptlist(tarr);
            questions.add(up);
        }
        System.out.println(questions);

        return questions;
    }

    /**
     * 展示AGA试卷
     *
     * @param mmap
     * @return
     */
    @PostMapping("/AGAbuildTestStepTwo")
    @ResponseBody
    public List<QuestionPaper> AGAbuildTestStepTwo(ModelMap mmap) {
        System.out.println("show");
        List<QuestionPaper> questions = new ArrayList<QuestionPaper>();
        for (int i = 0; i < AGAqList.size(); i++) {
            QuestionPaper up = new QuestionPaper();
            List<String> tarr = new ArrayList<String>();
            List<String> tList = new ArrayList<String>();
            up.setTid(AGAqList.get(i).getId());
            up.setTitle(AGAqList.get(i).getTitle());
            up.setTitleType(AGAqList.get(i).getTitleTypeName());
            up.setNumber(AGAqList.get(i).getTitleTypeId());
            up.setDifficulty(AGAqList.get(i).getDifficulty());
            up.setQexposed(AGAqList.get(i).getQexposed());
            up.setScore(AGAqList.get(i).getQuestionscore());
            for (int j = 0; j < tList.size(); j++) {
                if (tList.get(j) != null) {
                    tarr.add(tList.get(j));
                }
            }
            up.setOptlist(tarr);
            questions.add(up);
        }
        System.out.println(questions);

        return questions;
    }



    /**
     * 展示AGA试卷
     *
     * @param mmap
     * @return
     */
    @PostMapping("/stuAGAbuildTestStepTwo")
    @ResponseBody
    public List<QuestionPaper> stuAGAbuildTestStepTwo(ModelMap mmap) {
        System.out.println("show");
        List<QuestionPaper> questions = new ArrayList<QuestionPaper>();
        for (int i = 0; i < AGAqList.size(); i++) {
            QuestionPaper up = new QuestionPaper();
            List<String> tarr = new ArrayList<String>();
            List<String> tList = new ArrayList<String>();
            up.setTid(AGAqList.get(i).getId());
            up.setTitle(AGAqList.get(i).getTitle());
            up.setTitleType(AGAqList.get(i).getTitleTypeName());
            up.setNumber(AGAqList.get(i).getTitleTypeId());
            up.setDifficulty(AGAqList.get(i).getDifficulty());
            up.setQexposed(AGAqList.get(i).getQexposed());
            up.setScore(AGAqList.get(i).getQuestionscore());
            up.setTestpaperId(mytestid);
            for (int j = 0; j < tList.size(); j++) {
                if (tList.get(j) != null) {
                    tarr.add(tList.get(j));
                }
            }
            up.setOptlist(tarr);
            questions.add(up);
        }
        System.out.println(questions);

        return questions;
    }


    /**
     * 手动添加试卷  保存试卷试题
     *
     * @param testQuestionsId
     * @param testPaperId
     * @return
     * @throws Exception
     */
    @PostMapping("/addCoursePaper")

    @ResponseBody
    public Data addCoursePaper(@RequestParam String testQuestionsId, @RequestParam String testPaperId) throws
            Exception {

        String uid = getUser().getUserAttrId();
        String testid = testPaperId;
        String questionid = testQuestionsId;
        TestPaper testPaper2 = testPaperService.getById(questionid);
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
        int testpaperScore  =0;
        for (int i = 0; i < tidarry.length; i++) {
            String[] strarr = tidarry[i].substring(0, tidarry[i].length() - 1).split(";");
            String id = strarr[0];
            MyQuestions myq = this.myQuestionsService.getById(id);
            int questionExposed = Integer.parseInt(myq.getQexposed());
            questionExposed += 1;
            MyQuestions myQuestions2 = new MyQuestions();
            myQuestions2.setQexposed(String.valueOf(questionExposed));
            myQuestions2.setId(id);
            myQuestions2.setUpdateBy(getUser().getLoginName());
            myQuestionsService.update(myQuestions2);
            TestpaperQuestions tpq = new TestpaperQuestions();
            tpq.setQuestionScore(Integer.parseInt(strarr[1]));
            testpaperScore+=Integer.parseInt(strarr[1]);
            tpq.setTitleTypeId(myq.getTitleTypeId());
            tpq.setDifficulty(myq.getDifficulty());
            tpq.setTitle(myq.getTitle());
            tpq.setQexposed(myQuestions2.getQexposed());
            tpq.setQmaxexposure(myq.getQmaxexposure());
            tpq.setParsing(myq.getParsing());
            tpq.setYear(myq.getYear());
            String myoastr = myq.getMyoptionAnswerArr();
            if (myoastr != null && !"".equals(myoastr)) {
                String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
                List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
                for (int j = 0; j < myoaArr.length; j++) {
                    MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(myoaArr[j]);
                    myoalist.add(myOptionAnswer);
                }
                String tpoastr = "";
                for (MyOptionAnswer myoa : myoalist) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    Uuid uuid = new Uuid();
                    toa.setId(uuid.UId());
                    toa.setCreateId(uid);
                    toa.setCreateBy(getUser().getLoginName());
                    toa.setStanswer(myoa.getStanswer());
                    toa.setStoption(myoa.getStoption());
                    testpaperOptinanswerService.save(toa);
                    tpoastr += toa.getId() + ";";
                }
                tpq.setTestPaperOptionAnswerArr(tpoastr);
            }
            Uuid uuid = new Uuid();
            tpq.setId(uuid.UId());
            tpq.setCreateId(uid);
            tpq.setCreateBy(getUser().getLoginName());
            testpaperQuestionsService.save(tpq);
            testQuestionsList.add(tpq);
        }
        String time = new Date().getYear() + 1900 + "";
        TestPaper testPaper = testPaperService.getById(testid);
        for (int i = 0; i < testQuestionsList.size(); i++) {
            TestpaperTestquestions testPaperTestQuestions = new TestpaperTestquestions();
            testPaperTestQuestions.setTestQuestionsId(testQuestionsList.get(i).getId());
            testPaperTestQuestions.setTestPaperId(testPaper.getId());
            Uuid uuid = new Uuid();
            testPaperTestQuestions.setId(uuid.UId());
            testPaperTestQuestions.setCreateId(uid);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperTestquestionsService.save(testPaperTestQuestions);

        }
        testPaper.setTestYear(time);
        testPaper.setState("0");
        testPaper.setScore(String.valueOf(testpaperScore));
        return toAjax(testPaperService.update(testPaper));
    }


    /**
     * AGA添加试卷 保存试卷试题
     *
     * @param testQuestionsId
     * @param testPaperId
     * @return
     * @throws Exception
     */
    @PostMapping("/AGAaddCoursePaper")

    @ResponseBody
    public Data AGAaddCoursePaper(@RequestParam String testQuestionsId, @RequestParam String testPaperId) throws
            Exception {

        String uid = getUser().getUserAttrId();
        String testid = testPaperId;
        String questionid = testQuestionsId;
        TestPaper testPaper2 = testPaperService.getById(questionid);
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
         int testpaperScore  = 0;
        for (int i = 0; i < tidarry.length; i++) {
            String[] strarr = tidarry[i].substring(0, tidarry[i].length() - 1).split(";");
            String id = strarr[0];
            MyQuestions myq = this.myQuestionsService.getById(id);
            int questionExposed = Integer.parseInt(myq.getQexposed());
            questionExposed += 1;
            MyQuestions myQuestions2 = new MyQuestions();
            myQuestions2.setQexposed(String.valueOf(questionExposed));
            myQuestions2.setId(id);
            myQuestions2.setUpdateBy(getUser().getLoginName());
            myQuestionsService.update(myQuestions2);
            TestpaperQuestions tpq = new TestpaperQuestions();
            tpq.setQuestionScore(Integer.parseInt(strarr[1]));
            testpaperScore+=Integer.parseInt(strarr[1]);
            tpq.setTitleTypeId(myq.getTitleTypeId());
            tpq.setDifficulty(myq.getDifficulty());
            tpq.setTitle(myq.getTitle());
            tpq.setQexposed(myQuestions2.getQexposed());
            tpq.setQmaxexposure(myq.getQmaxexposure());
            tpq.setParsing(myq.getParsing());
            tpq.setYear(myq.getYear());
            String myoastr = myq.getMyoptionAnswerArr();
            if (myoastr != null && !"".equals(myoastr)) {
                String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
                List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
                for (int j = 0; j < myoaArr.length; j++) {
                    MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(myoaArr[j]);
                    myoalist.add(myOptionAnswer);
                }
                String tpoastr = "";
                for (MyOptionAnswer myoa : myoalist) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    Uuid uuid = new Uuid();
                    toa.setId(uuid.UId());
                    toa.setCreateId(uid);
                    toa.setCreateBy(getUser().getLoginName());
                    toa.setStanswer(myoa.getStanswer());
                    toa.setStoption(myoa.getStoption());
                    testpaperOptinanswerService.save(toa);
                    tpoastr += toa.getId() + ";";
                }
                tpq.setTestPaperOptionAnswerArr(tpoastr);
            }
            Uuid uuid = new Uuid();
            tpq.setId(uuid.UId());
            tpq.setCreateId(uid);
            tpq.setCreateBy(getUser().getLoginName());
            testpaperQuestionsService.save(tpq);
            testQuestionsList.add(tpq);
        }

        System.out.println("总分："+testpaperScore);
        String time = new Date().getYear() + 1900 + "";
        TestPaper testPaper = testPaperService.getById(testid);
        for (int i = 0; i < testQuestionsList.size(); i++) {
            TestpaperTestquestions testPaperTestQuestions = new TestpaperTestquestions();
            testPaperTestQuestions.setTestQuestionsId(testQuestionsList.get(i).getId());
            testPaperTestQuestions.setTestPaperId(testPaper.getId());
            Uuid uuid = new Uuid();
            testPaperTestQuestions.setId(uuid.UId());
            testPaperTestQuestions.setCreateId(uid);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperTestquestionsService.save(testPaperTestQuestions);

        }
        testPaper.setTestYear(time);
        testPaper.setState("0");
        testPaper.setScore(String.valueOf(testpaperScore));
        return toAjax(testPaperService.update(testPaper));
    }



    /**
     * AGA添加试卷 保存试卷试题
     *
     * @param testQuestionsId
     * @return
     * @throws Exception
     */
    @PostMapping("/stuAGAaddCoursePaper")
    @ResponseBody
    public Data stuAGAaddCoursePaper(@RequestParam String testQuestionsId) throws
            Exception {
        String uid = getUser().getUserAttrId();
        String testid = mytestid;
        String questionid = testQuestionsId;
        TestPaper testPaper2 = testPaperService.getById(questionid);
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
        int testpaperScore  = 0;
        for (int i = 0; i < tidarry.length; i++) {
            String[] strarr = tidarry[i].substring(0, tidarry[i].length() - 1).split(";");
            String id = strarr[0];
            MyQuestions myq = this.myQuestionsService.getById(id);
            int questionExposed = Integer.parseInt(myq.getQexposed());
            questionExposed += 1;
            MyQuestions myQuestions2 = new MyQuestions();
            myQuestions2.setQexposed(String.valueOf(questionExposed));
            myQuestions2.setId(id);
            myQuestions2.setUpdateBy(getUser().getLoginName());
            myQuestionsService.update(myQuestions2);
            TestpaperQuestions tpq = new TestpaperQuestions();
            tpq.setQuestionScore(Integer.parseInt(strarr[1]));
            testpaperScore+=Integer.parseInt(strarr[1]);
            tpq.setTitleTypeId(myq.getTitleTypeId());
            tpq.setDifficulty(myq.getDifficulty());
            tpq.setTitle(myq.getTitle());
            tpq.setQexposed(myQuestions2.getQexposed());
            tpq.setQmaxexposure(myq.getQmaxexposure());
            tpq.setParsing(myq.getParsing());
            tpq.setYear(myq.getYear());
            String myoastr = myq.getMyoptionAnswerArr();
            if (myoastr != null && !"".equals(myoastr)) {
                String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
                List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
                for (int j = 0; j < myoaArr.length; j++) {
                    MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(myoaArr[j]);
                    myoalist.add(myOptionAnswer);
                }
                String tpoastr = "";
                for (MyOptionAnswer myoa : myoalist) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    Uuid uuid = new Uuid();
                    toa.setId(uuid.UId());
                    toa.setCreateId(uid);
                    toa.setCreateBy(getUser().getLoginName());
                    toa.setStanswer(myoa.getStanswer());
                    toa.setStoption(myoa.getStoption());
                    testpaperOptinanswerService.save(toa);
                    tpoastr += toa.getId() + ";";
                }
                tpq.setTestPaperOptionAnswerArr(tpoastr);
            }
            Uuid uuid = new Uuid();
            tpq.setId(uuid.UId());
            tpq.setCreateId(uid);
            tpq.setCreateBy(getUser().getLoginName());
            testpaperQuestionsService.save(tpq);
            testQuestionsList.add(tpq);
        }

        System.out.println("总分："+testpaperScore);
        String time = new Date().getYear() + 1900 + "";
        TestPaper testPaper = testPaperService.getById(testid);
        for (int i = 0; i < testQuestionsList.size(); i++) {
            TestpaperTestquestions testPaperTestQuestions = new TestpaperTestquestions();
            testPaperTestQuestions.setTestQuestionsId(testQuestionsList.get(i).getId());
            testPaperTestQuestions.setTestPaperId(testPaper.getId());
            Uuid uuid = new Uuid();
            testPaperTestQuestions.setId(uuid.UId());
            testPaperTestQuestions.setCreateId(uid);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            System.out.println("********保存成功");
            testpaperTestquestionsService.save(testPaperTestQuestions);

        }
        testPaper.setTestYear(time);
        testPaper.setState("0");
        testPaper.setScore(String.valueOf(testpaperScore));
        return toAjax(testPaperService.update(testPaper));
    }




    /**
     * AGA添加试卷 保存试卷试题
     *
     * @param testQuestionsId
     * @param testPaperId
     * @return
     * @throws Exception
     */
    @PostMapping("/AGAaddGdCoursePaper")

    @ResponseBody
    public Data AGAaddGdCoursePaper(@RequestParam String testQuestionsId, @RequestParam String testPaperId, @RequestParam String studentIDs) throws
            Exception {
        String uid = getUser().getUserAttrId();
        String testid = testPaperId;
        String questionid = testQuestionsId;
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
        int testpaperScore  = 0;
        for (int i = 0; i < tidarry.length; i++) {
            String[] strarr = tidarry[i].substring(0, tidarry[i].length() - 1).split(";");
            String id = strarr[0];
            MyQuestions myq = this.myQuestionsService.getById(id);
            int questionExposed = Integer.parseInt(myq.getQexposed());
            questionExposed += 1;
            MyQuestions myQuestions2 = new MyQuestions();
            myQuestions2.setQexposed(String.valueOf(questionExposed));
            myQuestions2.setId(id);
            myQuestions2.setUpdateBy(getUser().getLoginName());
            myQuestionsService.update(myQuestions2);
            TestpaperQuestions tpq = new TestpaperQuestions();
            tpq.setQuestionScore(Integer.parseInt(strarr[1]));
            testpaperScore+=Integer.parseInt(strarr[1]);
            tpq.setTitleTypeId(myq.getTitleTypeId());
            tpq.setDifficulty(myq.getDifficulty());
            tpq.setTitle(myq.getTitle());
            tpq.setQexposed(myQuestions2.getQexposed());
            tpq.setQmaxexposure(myq.getQmaxexposure());
            tpq.setParsing(myq.getParsing());
            tpq.setYear(myq.getYear());
            String myoastr = myq.getMyoptionAnswerArr();
            if (myoastr != null && !"".equals(myoastr)) {
                String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
                List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
                for (int j = 0; j < myoaArr.length; j++) {
                    MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(myoaArr[j]);
                    myoalist.add(myOptionAnswer);
                }
                String tpoastr = "";
                for (MyOptionAnswer myoa : myoalist) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    Uuid uuid = new Uuid();
                    toa.setId(uuid.UId());
                    toa.setCreateId(uid);
                    toa.setCreateBy(getUser().getLoginName());
                    toa.setStanswer(myoa.getStanswer());
                    toa.setStoption(myoa.getStoption());
                    testpaperOptinanswerService.save(toa);
                    tpoastr += toa.getId() + ";";
                }
                tpq.setTestPaperOptionAnswerArr(tpoastr);
            }
            Uuid uuid = new Uuid();
            tpq.setId(uuid.UId());
            tpq.setCreateId(uid);
            tpq.setCreateBy(getUser().getLoginName());
            testpaperQuestionsService.save(tpq);
            testQuestionsList.add(tpq);
        }

        System.out.println("总分："+testpaperScore);
        String time = new Date().getYear() + 1900 + "";
        TestPaper testPaper = testPaperService.getById(testid);
        for (int i = 0; i < testQuestionsList.size(); i++) {
            TestpaperTestquestions testPaperTestQuestions = new TestpaperTestquestions();
            testPaperTestQuestions.setTestQuestionsId(testQuestionsList.get(i).getId());
            testPaperTestQuestions.setTestPaperId(testPaper.getId());
            Uuid uuid = new Uuid();
            testPaperTestQuestions.setId(uuid.UId());
            testPaperTestQuestions.setCreateId(uid);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperTestquestionsService.save(testPaperTestQuestions);

        }
        testPaper.setTestYear(time);
        testPaper.setState("0");
        testPaper.setScore(String.valueOf(testpaperScore));
        String[] userId = studentIDs.split(",");
        List<UserTest> list = new ArrayList<UserTest>();
        for (int i = 0; i < userId.length; i++) {
            UserTest userTest2 = new UserTest();
            userTest2.setTestPaperId(testPaperId);
            userTest2.setUserId(userId[i]);
            List<UserTest> userTestList = new ArrayList<UserTest>();
            userTestList = userTestService.list(userTest2);
            if (userTestList.size() == 0) {
                UserTest userTest1 = new UserTest();
                userTest1.setTestPaperId(testPaperId);
                userTest1.setUserId(userId[i]);
                userTest1.setCreateBy(getUser().getLoginName());
                userTest1.setSumbitState("0"); //0表示未提交,1为提交
                userTest1.setcId(testPaper.getCoursrId());
                userTest1.setCreateId(getUser().getUserAttrId());
                list.add(userTest1);
            }
        }
        userTestService.saveBatch(list);
        return toAjax(testPaperService.update(testPaper));
    }




    /**
     * 课程组卷 保存
     *
     * @param testQuestionsId
     * @param testPaperId
     * @return
     * @throws Exception
     */
    @PostMapping("/CourseAddPaper")

    @ResponseBody
    public Data CourseAddPaper(String testQuestionsId, String testPaperId, String studentId) throws
            Exception {
        String uid = getUser().getUserAttrId();
        String testid = testPaperId;
        String questionid = testQuestionsId;
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
        int testpaperScore  = 0;
        for (int i = 0; i < tidarry.length; i++) {
            String[] strarr = tidarry[i].substring(0, tidarry[i].length() - 1).split(";");
            String id = strarr[0];
            MyQuestions myq = this.myQuestionsService.getById(id);
            int questionExposed = Integer.parseInt(myq.getQexposed());
            questionExposed += 1;
            MyQuestions myQuestions2 = new MyQuestions();
            myQuestions2.setQexposed(String.valueOf(questionExposed));
            myQuestions2.setId(id);
            myQuestions2.setUpdateBy(getUser().getLoginName());
            myQuestionsService.update(myQuestions2);
            TestpaperQuestions tpq = new TestpaperQuestions();
            tpq.setQuestionScore(Integer.parseInt(strarr[1]));
            testpaperScore+=Integer.parseInt(strarr[1]);
            tpq.setTitleTypeId(myq.getTitleTypeId());
            tpq.setDifficulty(myq.getDifficulty());
            tpq.setTitle(myq.getTitle());
            tpq.setQexposed(myQuestions2.getQexposed());
            tpq.setQmaxexposure(myq.getQmaxexposure());
            tpq.setParsing(myq.getParsing());
            tpq.setYear(myq.getYear());
            String myoastr = myq.getMyoptionAnswerArr();
            if (myoastr != null && !"".equals(myoastr)) {
                String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
                List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
                for (int j = 0; j < myoaArr.length; j++) {
                    MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(myoaArr[j]);
                    myoalist.add(myOptionAnswer);
                }
                String tpoastr = "";
                for (MyOptionAnswer myoa : myoalist) {
                    TestpaperOptinanswer toa = new TestpaperOptinanswer();
                    Uuid uuid = new Uuid();
                    toa.setId(uuid.UId());
                    toa.setCreateId(uid);
                    toa.setCreateBy(getUser().getLoginName());
                    toa.setStanswer(myoa.getStanswer());
                    toa.setStoption(myoa.getStoption());
                    testpaperOptinanswerService.save(toa);
                    tpoastr += toa.getId() + ";";
                }
                tpq.setTestPaperOptionAnswerArr(tpoastr);
            }
            Uuid uuid = new Uuid();
            tpq.setId(uuid.UId());
            tpq.setCreateId(uid);
            tpq.setCreateBy(getUser().getLoginName());
            testpaperQuestionsService.save(tpq);
            testQuestionsList.add(tpq);
        }

        System.out.println("总分："+testpaperScore);
        String time = new Date().getYear() + 1900 + "";
        TestPaper testPaper = testPaperService.getById(testid);
        for (int i = 0; i < testQuestionsList.size(); i++) {
            CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
            coursepaperCoursequestions.setTestQuestionsId(testQuestionsList.get(i).getId());
            coursepaperCoursequestions.setTestPaperId(testPaper.getId());
            coursepaperCoursequestions.setCreateId(uid);
            coursepaperCoursequestions.setCreateBy(getUser().getLoginName());
            coursepaperCoursequestions.setStudentId(studentId);
            coursepaperCoursequestionsService.save(coursepaperCoursequestions);

        }
        testPaper.setTestYear(time);
        testPaper.setState("0");
        testPaper.setScore(String.valueOf(testpaperScore));
        UserTest userTest2 = new UserTest();
        userTest2.setTestPaperId(testPaperId);
        userTest2.setUserId(studentId);
        List<UserTest> userTestList = new ArrayList<UserTest>();
        userTestList = userTestService.list(userTest2);
        if (userTestList.size() == 0) {
            UserTest userTest1 = new UserTest();
            userTest1.setTestPaperId(testPaperId);
            userTest1.setUserId(studentId);
            userTest1.setCreateBy(getUser().getLoginName());
            userTest1.setSumbitState("0"); //0表示未提交,1为提交
            userTest1.setcId(testPaper.getCoursrId());
            userTest1.setCreateId(getUser().getUserAttrId());
            userTestService.save(userTest1);
        }
        return toAjax(testPaperService.update(testPaper));
    }

    /**
     * AGA 组卷  生成试卷
     * @param tqt
     * @param testId
     * @param txid
     * @param selectType
     * @param coursrId
     * @param selectTypescore
     * @param diff
     * @return
     */

    @PostMapping("/AGAbuildPaper")

    @ResponseBody
    public List<MyQuestions> builePaper(@RequestParam String tqt,
                                        @RequestParam String testId,
                                        @RequestParam String txid,
                                        @RequestParam String selectType,
                                        @RequestParam String coursrId,
                                        @RequestParam String selectTypescore,
                                        @RequestParam String diff) {
        try {
            AGAqList = null;
            String selecttype = selectType;//每种题型的数量
            String selecttypescore = selectTypescore;//每种题型的分数
            String tid = txid;//题型的id 属于哪种类型题 比如选择题还是填空题
            String titleQuestion = tqt;
            String subjectId = coursrId;//具体课程的id
            String dif = diff;//难度等级

            if (selecttype != null && !selecttype.equals("")) {
                typesAmount = selecttype.substring(0, selecttype.length() - 1).split(",");
            }

            if (selecttype != null && !selecttype.equals("")) {
                typescore = selecttypescore.substring(0, selecttypescore.length() - 1).split(",");
            }

            if (tid != null && !tid.equals("")) {
                types = tid.substring(0, tid.length() - 1).split(",");
            }

            Logger logger = Logger.getRootLogger();
            //定义一个存放每种题型属性的list
            List<QuestionType> userType = new ArrayList<QuestionType>();

            //全部题型的数量 比如选择题和填空题 那么长度就是2
            int typeLength = typesAmount.length;
            for (int i = 0; i < typeLength; i++) {
                //每种题型的数量
                String tmp = typesAmount[i];
                if (!"".equals(tmp) && !"0".equals(tmp)) {
                    //把每种题型的id 分数 和数量循环遍历放到usertype中
                    QuestionType ut = new QuestionType();
                    ut.setTid(types[i]);
                    ut.setNumber(Integer.parseInt(typesAmount[i]));
                    ut.setScore(Integer.parseInt(typescore[i]));
                    userType.add(ut);

                }
            }
            //题型类型的数量 选择题就是1  选择题.不定项就是2
            int typeNum = userType.size();
            //定义一个长度为typeNum的题库表的list
            List<MyQuestions>[] typeName = new List[typeNum];
            String tqtString[];
            tqtString = tqt.split("&");
            for (int i = 0; i < typeNum; i++) {
                //定义题库表的list
                List<MyQuestions> tmp = new ArrayList<MyQuestions>();
                //获取每种题型的id
                String tyid = userType.get(i).getTid();
                //查询一门科目中的一种题型的全部试题
                String flag[];
                flag = tqtString[i].split(";");
                String questionList[] = flag[1].split(",");
                List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
                for (int j = 0; j < questionList.length; j++) {
                    MyQuestions myQuestions = new MyQuestions();
                    myQuestions = myQuestionsService.getById(questionList[j]);
                    if (myQuestions != null) {
                        if (myQuestions.getTitleTypeId().equals(flag[0])) {
                            tmp.add(myQuestions);
                        }
                    }
                }

                //将一门科目中的全部题型的试题存放到typeName中
                typeName[i] = tmp;
            }

            //题型类型的数量
            int[] questionCount = new int[typeNum];
            int questionsum = 0;

            for (int i = 0; i < typeNum; i++) {
                questionCount[i] = typeName[i].size();
                System.out.println("第" + (i + 1) + "道题型符合要求的全部数量" + questionCount[i]);
                //获取每种题型符合条件的试题的数量之和
                questionsum = questionsum + questionCount[i];
            }
            //难度等级
            String difficulty = diff;
            int level = Integer.parseInt(difficulty);
            //50  种群数量初始值
            int popNum = QuestionData.POP_NUM;
            //定义50个population对象
            Individual[] population = new Individual[popNum];
            //最大适应度
            double maxFitness = 0;
            for (int i = 0; i < popNum; i++) {
                Individual ind = new Individual(typeName, questionCount, userType,
                        null, Integer.parseInt(difficulty));
                population[i] = ind;
                if (maxFitness < ind.getFitness()) {
                    maxFitness = ind.getFitness();
                }
            }
            System.out.println("**************初代MaxTitness:" + maxFitness);
            Utility ut = new Utility();
            ut.calcPi(popNum, population, maxFitness);
            int finalPopNum = QuestionData.FINAL_POP_NUM;
            Individual[] finalPopulation = new Individual[finalPopNum];
            for (int i = 0; i < finalPopNum; i++) {
                finalPopulation[i] = Utility.selection(population);
            }
            for (int i = 0; i < finalPopNum; i++) {
                System.out.println("交叉后的Fitness" + finalPopulation[i].getFitness());
            }
            int tt = 0;
            double minFit = 20.0;
            Individual bestInd = new Individual();
            Individual bestInd1 = new Individual();
            int lj = 0;
            do {
                Utility.crossover(finalPopulation);
                for (int i = 0; i < finalPopNum; i++) {//finalPopNum=30
                    Individual ind = finalPopulation[i];
                    ind.resetIndiWithChro(typeName, questionCount, userType,
                            null, Integer.parseInt(difficulty));
                    double tmpFit = ind.getFitness();
                    if (tmpFit < minFit) {
                        minFit = tmpFit;
                        bestInd = (Individual) finalPopulation[i].deepClone();
                    }
                    lj++;
                }
                lj++;
                tt++;
            } while (tt != 2);
            System.out.println("进化代数：" + lj);
            bestInd.resetIndiWithChro(typeName, questionCount, userType, null, level);
            double f = bestInd.calcualateFitness(null, level);
            System.out.println("最优适应度：" + f);
            System.out.println("染色体：" + bestInd.getChromosome());
            // 存入被选中的试题testPaper
            AGAqList = bestInd.getList();
            for (int i = 0; i < AGAqList.size(); i++) {
                System.out.println(AGAqList.get(i).getTitle());
            }
            AGAqList.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
            return AGAqList;
        } catch (Exception e) {
            System.out.println("找不到匹配的题目");
            List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
            return myQuestionsList;
        }
    }





    @PostMapping("/stuAGAbuildPaper")
    @ResponseBody
    public List<MyQuestions> stubuilePaper(@RequestParam String tqt,
                                        @RequestParam String txid,
                                        @RequestParam String selectType,
                                        @RequestParam String coursrId,
                                        @RequestParam String selectTypescore,
                                           @RequestParam String chapterId) {

              mytestid="";
            System.out.println("chapterL"+chapterId);
            TestPaper testPaper = new TestPaper();
            testPaper.setType("4");
            testPaper.setCreateId(getUser().getUserAttrId());
            testPaper.setCreateBy(getUser().getLoginName());
            testPaper.setCoursrId(coursrId);
            testPaper.setTestName("在线自测");
            testPaper.setHeadline("自测");

            List<TestPaper> testPaperList = testPaperService.list(new TestPaper());
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i <testPaperList.size(); i++) {
                list.add(Integer.parseInt(testPaperList.get(i).getId()));
            }
            Collections.sort(list);

            int l=list.size();
            mytestid=String.valueOf(list.get(l-1));
            AGAqList = null;
            String selecttype = selectType;//每种题型的数量
            String selecttypescore = selectTypescore;//每种题型的分数
            String tid = txid;//题型的id 属于哪种类型题 比如选择题还是填空题
            String titleQuestion = tqt;
            String subjectId = coursrId;//具体课程的id
            String diff="3";
            String dif = diff;//难度等级

            if (selecttype != null && !selecttype.equals("")) {
                typesAmount = selecttype.substring(0, selecttype.length() - 1).split(",");
            }

            if (selecttype != null && !selecttype.equals("")) {
                typescore = selecttypescore.substring(0, selecttypescore.length() - 1).split(",");
            }

            if (tid != null && !tid.equals("")) {
                types = tid.substring(0, tid.length() - 1).split(",");
            }
            if (chapterId != null && !chapterId.equals("")) {
                chapterIds = chapterId.substring(0, chapterId.length() - 1).split(",");
            }
            Logger logger = Logger.getRootLogger();
            //定义一个存放每种题型属性的list
            List<QuestionType> userType = new ArrayList<QuestionType>();

            //全部题型的数量 比如选择题和填空题 那么长度就是2
            int typeLength = typesAmount.length;
            for (int i = 0; i < typeLength; i++) {
                //每种题型的数量
                String tmp = typesAmount[i];
                if (!"".equals(tmp) && !"0".equals(tmp)) {
                    //把每种题型的id 分数 和数量循环遍历放到usertype中
                    QuestionType ut = new QuestionType();
                    ut.setTid(types[i]);
                    ut.setNumber(Integer.parseInt(typesAmount[i]));
                    ut.setScore(Integer.parseInt(typescore[i]));
                    userType.add(ut);

                }
            }
            //题型类型的数量 选择题就是1  选择题.不定项就是2
            int typeNum = userType.size();
            //定义一个长度为typeNum的题库表的list
            List<MyQuestions>[] typeName = new List[typeNum];
//            String tqtString[];
//            tqtString = tqt.split("&");
            for (int i = 0; i < typeNum; i++) {
                //定义题库表的list
                List<MyQuestions> tmp = new ArrayList<MyQuestions>();
                //获取每种题型的id
                String tyid = userType.get(i).getTid();
                //查询一门科目中的一种题型的全部试题
//                String flag[];
//                flag = tqtString[i].split(";");
//                String questionList[] = flag[1].split(",");
                List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
                for (int j = 0; j < types.length; j++) {
                    MyQuestions myQuestions = new MyQuestions();
                    myQuestions.setTitleTypeId(types[j]);
                    myQuestions.setXzsubjectsId(coursrId);
                    List<MyQuestions> myQuestionsList1 = myQuestionsService.list(myQuestions);
                    if (myQuestionsList1.size()!=0) {
                        for (int q= 0; q<myQuestionsList1.size() ; q++) {
                            for (int k = 0; k < chapterIds.length; k++) {
                                if(myQuestionsList1.get(q).getChapterId().equals(chapterIds[k])){
                                    tmp.add(myQuestionsList1.get(q));
                                }
                            }
                        }

                    }
                }

                //将一门科目中的全部题型的试题存放到typeName中
                typeName[i] = tmp;
            }

            //题型类型的数量
            int[] questionCount = new int[typeNum];
            int questionsum = 0;

            for (int i = 0; i < typeNum; i++) {
                questionCount[i] = typeName[i].size();
                System.out.println("第" + (i + 1) + "道题型符合要求的全部数量" + questionCount[i]);
                //获取每种题型符合条件的试题的数量之和
                questionsum = questionsum + questionCount[i];
            }
            //难度等级
            String difficulty = diff;
            int level = Integer.parseInt(difficulty);
            //50  种群数量初始值
            int popNum = QuestionData.POP_NUM;
            //定义50个population对象
            Individual[] population = new Individual[popNum];
            //最大适应度
            double maxFitness = 0;
        System.out.println("type:"+typeName+","+",questionCount:"+questionCount+",userType:"+userType+",diff:"+Integer.parseInt(difficulty));
            for (int i = 0; i < popNum; i++) {
                Individual ind = new Individual(typeName, questionCount, userType,
                        null, Integer.parseInt(difficulty));
                population[i] = ind;
                if (maxFitness < ind.getFitness()) {
                    maxFitness = ind.getFitness();
                }
            }
            System.out.println("**************初代MaxTitness:" + maxFitness);
            Utility ut = new Utility();
            ut.calcPi(popNum, population, maxFitness);
            int finalPopNum = QuestionData.FINAL_POP_NUM;
            Individual[] finalPopulation = new Individual[finalPopNum];
            for (int i = 0; i < finalPopNum; i++) {
                finalPopulation[i] = Utility.selection(population);
            }
            for (int i = 0; i < finalPopNum; i++) {
                System.out.println("交叉后的Fitness" + finalPopulation[i].getFitness());
            }
            int tt = 0;
            double minFit = 20.0;
            Individual bestInd = new Individual();
            Individual bestInd1 = new Individual();
            int lj = 0;
            do {
                Utility.crossover(finalPopulation);
                for (int i = 0; i < finalPopNum; i++) {//finalPopNum=30
                    Individual ind = finalPopulation[i];
                    ind.resetIndiWithChro(typeName, questionCount, userType,
                            null, Integer.parseInt(difficulty));
                    double tmpFit = ind.getFitness();
                    if (tmpFit < minFit) {
                        minFit = tmpFit;
                        bestInd = (Individual) finalPopulation[i].deepClone();
                    }
                    lj++;
                }
                lj++;
                tt++;
            } while (tt != 2);
            System.out.println("进化代数：" + lj);
            bestInd.resetIndiWithChro(typeName, questionCount, userType, null, level);
            double f = bestInd.calcualateFitness(null, level);
            System.out.println("最优适应度：" + f);
            System.out.println("染色体：" + bestInd.getChromosome());
        try {
            testPaperService.save(testPaper);
            // 存入被选中的试题testPaper
            AGAqList = bestInd.getList();
            for (int i = 0; i < AGAqList.size(); i++) {
                System.out.println(AGAqList.get(i).getTitle());
            }
            AGAqList.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
            return AGAqList;
        } catch (Exception e) {
            System.out.println("找不到匹配的题目");
            List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
            return myQuestionsList;
        }
    }






    /**
     * 课程组卷  生成试卷  人手一份
     * @param tqt
     * @param testId
     * @param txid
     * @param selectType
     * @param coursrId
     * @param selectTypescore
     * @param diff
     * @return
     */

    @PostMapping("/CoursebuildPaper")

    @ResponseBody
    public List<MyQuestions> CoursebuildPaper(@RequestParam String tqt,
                                              @RequestParam String testId,
                                              @RequestParam String txid,
                                              @RequestParam String selectType,
                                              @RequestParam String coursrId,
                                              @RequestParam String selectTypescore,
                                              @RequestParam String stuId,
                                              @RequestParam String diff) {

        String StudentIds = stuId;
        List<CoursepaperCoursequestions> coursepaperCoursequestionsList = new ArrayList<CoursepaperCoursequestions>();
        System.out.println("抽题。。。。。。。");
            AGAqList = null;
            String selecttype = selectType;//每种题型的数量
            String selecttypescore = selectTypescore;//每种题型的分数
            String tid = txid;//题型的id 属于哪种类型题 比如选择题还是填空题
            String titleQuestion = tqt;
            String subjectId = coursrId;//具体课程的id
            String dif = diff;//难度等级

            if (selecttype != null && !selecttype.equals("")) {
                typesAmount = selecttype.substring(0, selecttype.length() - 1).split(",");
            }

            if (selecttype != null && !selecttype.equals("")) {
                typescore = selecttypescore.substring(0, selecttypescore.length() - 1).split(",");
            }

            if (tid != null && !tid.equals("")) {
                types = tid.substring(0, tid.length() - 1).split(",");
            }

            Logger logger = Logger.getRootLogger();
            //定义一个存放每种题型属性的list
            List<QuestionType> userType = new ArrayList<QuestionType>();

            //全部题型的数量 比如选择题和填空题 那么长度就是2
            int typeLength = typesAmount.length;
            for (int i = 0; i < typeLength; i++) {
                //每种题型的数量
                String tmp = typesAmount[i];
                if (!"".equals(tmp) && !"0".equals(tmp)) {
                    //把每种题型的id 分数 和数量循环遍历放到usertype中
                    QuestionType ut = new QuestionType();
                    ut.setTid(types[i]);
                    ut.setNumber(Integer.parseInt(typesAmount[i]));
                    ut.setScore(Integer.parseInt(typescore[i]));
                    userType.add(ut);

                }
            }
            //题型类型的数量 选择题就是1  选择题.不定项就是2
            int typeNum = userType.size();
            //定义一个长度为typeNum的题库表的list
            List<MyQuestions>[] typeName = new List[typeNum];
            String tqtString[];
            tqtString = tqt.split("&");
            for (int i = 0; i < typeNum; i++) {
                //定义题库表的list
                List<MyQuestions> tmp = new ArrayList<MyQuestions>();
                //获取每种题型的id
                String tyid = userType.get(i).getTid();
                //查询一门科目中的一种题型的全部试题
                String flag[];
                flag = tqtString[i].split(";");
                String questionList[] = flag[1].split(",");
                List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
                for (int j = 0; j < questionList.length; j++) {
                    MyQuestions myQuestions = new MyQuestions();
                    myQuestions = myQuestionsService.getById(questionList[j]);
                    if (myQuestions != null) {
                        if (myQuestions.getTitleTypeId().equals(flag[0])) {
                            tmp.add(myQuestions);
                        }
                    }
                }

                //将一门科目中的全部题型的试题存放到typeName中
                typeName[i] = tmp;
            }

            //题型类型的数量
            int[] questionCount = new int[typeNum];
            int questionsum = 0;

            for (int i = 0; i < typeNum; i++) {
                questionCount[i] = typeName[i].size();
//                System.out.println("第" + (i + 1) + "道题型符合要求的全部数量" + questionCount[i]);
                //获取每种题型符合条件的试题的数量之和
                questionsum = questionsum + questionCount[i];
            }
            //难度等级
            String difficulty = diff;
            int level = Integer.parseInt(difficulty);
            //50  种群数量初始值
            int popNum = QuestionData.POP_NUM;
            //定义50个population对象
            Individual[] population = new Individual[popNum];
            //最大适应度
            double maxFitness = 0;
            for (int i = 0; i < popNum; i++) {
                Individual ind = new Individual(typeName, questionCount, userType,
                        null, Integer.parseInt(difficulty));
                population[i] = ind;
                if (maxFitness < ind.getFitness()) {
                    maxFitness = ind.getFitness();
                }
            }
//            System.out.println("**************初代MaxTitness:" + maxFitness);
            Utility ut = new Utility();
            ut.calcPi(popNum, population, maxFitness);
            int finalPopNum = QuestionData.FINAL_POP_NUM;
            Individual[] finalPopulation = new Individual[finalPopNum];
            for (int i = 0; i < finalPopNum; i++) {
                finalPopulation[i] = Utility.selection(population);
            }
            for (int i = 0; i < finalPopNum; i++) {
//                System.out.println("交叉后的Fitness" + finalPopulation[i].getFitness());
            }
            int tt = 0;
            double minFit = 20.0;
            Individual bestInd = new Individual();
            Individual bestInd1 = new Individual();
            int lj = 0;
            do {
                Utility.crossover(finalPopulation);
                for (int i = 0; i < finalPopNum; i++) {//finalPopNum=30
                    Individual ind = finalPopulation[i];
                    ind.resetIndiWithChro(typeName, questionCount, userType,
                            null, Integer.parseInt(difficulty));
                    double tmpFit = ind.getFitness();
                    if (tmpFit < minFit) {
                        minFit = tmpFit;
                        bestInd = (Individual) finalPopulation[i].deepClone();
                    }
                    lj++;
                }
                lj++;
                tt++;
            } while (tt != 2);
//            System.out.println("进化代数：" + lj);
            bestInd.resetIndiWithChro(typeName, questionCount, userType, null, level);
            double f = bestInd.calcualateFitness(null, level);
//            System.out.println("最优适应度：" + f);
//            System.out.println("染色体：" + bestInd.getChromosome());
            // 存入被选中的试题testPaper
            AGAqList = bestInd.getList();
        try {
            AGAqList.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上

            for (int i = 0; i < AGAqList.size(); i++) {
                System.out.println("^^^^^^^"+AGAqList.get(i).getTitle());
            }
            return AGAqList;
        } catch (Exception e) {
            System.out.println("找不到匹配的题目");
            List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
            return myQuestionsList;
        }
    }





    /**
     * 页面跳转  预览试卷
     *
     * @return
     */
    @GetMapping("/showPaper")

    public String showPaper(String id, ModelMap modelMap) {
        String uid = getUser().getUserAttrId();
        modelMap.put("studentId", uid);
        modelMap.put("paperId", id);
        TestPaper testPaper = testPaperService.getById(id);
        modelMap.put("testPaper", testPaper);
        return prefix + "/showPaper";
    }



    /**
     * 批量转换teacherVo类型
     *
     * @param teacherCourses 将<code>TeacherCourse</code>类型转为<code>TeacherCourseVo</code>
     * @return <code>TeacherCourseVo</code>类型数据
     */
    private List<TeacherCourseVo> trans(List<TeacherCourse> teacherCourses) {
        List<TeacherCourseVo> tcVos = new LinkedList<>();
        Map<String, Course> courseMap = adminService.courseMap(null);

        teacherCourses.forEach(u -> {
            TeacherCourseVo teacherCourseVo = new TeacherCourseVo();
            BeanUtils.copyProperties(u, teacherCourseVo);
            teacherCourseVo.setCourse(courseMap.get(u.getCid()));
            tcVos.add(teacherCourseVo);
        });
        return tcVos;
    }

    //private void fillCourse(TeacherCourseVo teacherCourse) {
    //    String cid = teacherCourse.getCid();
    //    Course course = courseService.getById(cid);
    //    teacherCourse.setCourse(course);
    //}
    //
    //private void fillTeacher(TeacherCourseVo teacherCourse) {
    //    String tid = teacherCourse.getTid();
    //    Teacher teacher = teacherService.getById(tid);
    //    teacherCourse.setTeacher(teacher);
    //}

    public String[] getTypescore() {
        return typescore;
    }

    public void setTypescore(String[] typescore) {
        this.typescore = typescore;
    }

    public String[] getQuestionsid() {
        return questionsid;
    }

    public void setQuestionsid(String[] questionsid) {
        this.questionsid = questionsid;
    }

    public static List<MyQuestions> getqList() {
        return qList;
    }

    public static void setqList(List<MyQuestions> qList) {
        OnlineExamController.qList = qList;
    }
}
