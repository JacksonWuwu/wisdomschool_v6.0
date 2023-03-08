package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.constants.StorageConstants;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.entity.Individual.QuestionPaper;
import cn.wstom.exam.exception.ApplicationException;
import cn.wstom.exam.exception.FileNameLimitExceededException;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.feign.StudentService;
import cn.wstom.exam.mapper.StuOptionExamanswerMapper;
import cn.wstom.exam.mapper.TestpaperOptinanswerMapper;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * 试卷 信息操作处理   课程作业形式的试卷
 *
 *      -   处理作业、试卷的提交和查询
 * @author
 * @date 20200526
 */
@Controller
@RequestMapping("/school/onlineExam/testPaperWork")
public class TestPaperWorkController extends BaseController {
    private String prefix = "school/onlineExam/testPaperWork";
    /**
     * 科目ID
     */
    private String subjectId;
    /**
     * 章节
     */
    private String[] charpter;
    /**
     * 试卷名
     */
    static String testPaperName;
    /**
     * 试卷难度系数
     */
    private String difficulty;
    /**
     * 试题类型id
     */
    private String[] types;
    /**
     * 试题类型对应的数量
     */
    private String[] typesAmount;
    /**
     * 试卷总时间
     */
    private String altogetherTime;
    /**
     * 获取学生
     */
    private String[] mark;
    /**
     * 考试卷题目(节测试)
     */
    static List<MyQuestions> qList = new ArrayList<MyQuestions>();
    /**
     * 试卷List AGA组卷用
     */
    static List<MyQuestions> AGAqList = new ArrayList<MyQuestions>();
    /**
     * 测试题目
     */
    static List<MyQuestions> qList_chapter;
    /**
     * 课程考试题目
     */
    static List<MyQuestions> qList_course;


    /**
     * 存放题目
     */
    public static List<MyQuestions> lists = new ArrayList<MyQuestions>();
    /**
     * 试卷分析
     */
    static ArrayList nameL = new ArrayList();
    /**
     * 试卷名
     */
    static String title_name;
    /**
     * 存放手动组卷传来的试题
     */
    static String[] questionsid;

    static String testid="";
    /**
     * 某题型的分数
     */
    private String[] typescore;

    @Autowired
    private TestPaperWorkService testPaperWorkService;
    @Autowired
    private TestPaperWorkListService testPaperWorkListService;
    @Autowired
    private MyOptionAnswerService myOptionAnswerService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;
    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private UserPaperWorkService userExamService;
    @Autowired
    private StuOptionanswerService stuOptionanswerService;
    @Autowired
    private StuOptionExamanswerService stuOptionExamanswerService;

    @Autowired
    private CoursetestStuoptionanswerService coursetestStuoptionanswerService;
    @Autowired
    private MyQuestionsService myQuestionsService;
    @Autowired
    private CoursepaperCoursequestionsService coursepaperCoursequestionsService;
    @Autowired
    private UserPaperWorkService userPaperWorkService;


    /**
     * 组卷确定后更新总分
     */
    @RequestMapping("/updateScore")
    @ResponseBody
    public int updateScore(String paperId){
        System.out.println(paperId);
        int sumScore=0;
        TestpaperOneTestquestions testpaperOneTestquestions1 = new TestpaperOneTestquestions();
        testpaperOneTestquestions1.setTestPaperWorkId(paperId);
        List<TestpaperOneTestquestions> list = testpaperOneTestquestionsService.list(testpaperOneTestquestions1);
        for (TestpaperOneTestquestions testpaperOneTestquestions : list) {
            TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperOneTestquestions.getTestQuestionsId());
            Integer score = testpaperQuestions.getQuestionScore();
            sumScore=sumScore+score;
        }
        System.out.println(sumScore);
        //更新分数
        TestPaperWorkList testPaperWorkList = new TestPaperWorkList();
        testPaperWorkList.setId(paperId);
        testPaperWorkList.setScore(String.valueOf(sumScore));
        try {
            testPaperWorkListService.update(testPaperWorkList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumScore;
    }










    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/paperlist";
    }

    /**
     * 查询试卷列表
     */

    @PostMapping("/paperlist/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaperWork testPaper) {
        String uid = getUser().getUserAttrId();
        System.out.println("----" + getUser().getUserAttrId());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("2");
        String sj = "随机";
        String gd = "固定";
        String y = "是";
        String n = "否";
        startPage();
        List<TestPaperWork> list = testPaperWorkService.list(testPaper);
        for (int i = 0; i < list.size(); i++) {
            System.out.println("====" + list.get(i).getSetExam());

            if (list.get(i).getRule()!=null && list.get(i).getRule().equals("0")) {

                list.get(i).setRule(gd);
            } else {
                list.get(i).setRule(sj);
            }
            if (list.get(i).getSetExam().equals("0")) {
                list.get(i).setSetExam(n);
            } else {
                list.get(i).setSetExam(y);
            }
            if (list.get(i).getState().equals("0")) {
                list.get(i).setState(y);
            } else {
                list.get(i).setState(n);
            }

        }

        //  List<TestPaper> list =testPaperService.findStuPaper(testPaper);
        return wrapTable(list);

    }

    /**
     * lzj
     * @param cid
     * @param testPaper
     * @return
     */

    @PostMapping("/listOne/{cid}")
    @ResponseBody
    public TableDataInfo listOne(@PathVariable String cid, TestPaperWork testPaper) {
        testPaper.setCoursrId(cid);
        testPaper.setType("0");
        testPaper.setCreateId(getUser().getUserAttrId());
        startPage();
        List<TestPaperWork> list = testPaperWorkService.list(testPaper);

        return wrapTable(list);
    }

    /**
     * 新增试卷
     */
    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/add";
    }

    /**
     * 新增保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaperWork testPaper) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>>" + testPaper);
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setState("1");
        testPaper.setType("2");
        return toAjax(testPaperWorkService.save(testPaper));
    }
    /**
     * 修改试卷
     */
    @GetMapping("/edit/{id}")

    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestPaperWork testPaper = testPaperWorkService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edithomework(TestPaperWork testPaper) throws Exception {
        testPaper.setUpdateId(getUser().getUserAttrId());
        testPaper.setUpdateBy(getUser().getLoginName());
        return toAjax(testPaperWorkService.update(testPaper));
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

            idList.forEach(id->{
                userPaperWorkService.deleteByPaperWorkId(id);
            });

            return toAjax(testPaperWorkService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 删除学生试卷
     * @param ids
     * @return
     * @throws Exception
     */
    @PostMapping("/showDetailRemove")
    @ResponseBody
    public Data showDetailRemove(String ids) throws Exception {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            return toAjax(userExamService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
    /**
     * 校验标题名唯一
     */
    @PostMapping("/checkTestNameUnique")

    @ResponseBody
    public String checkHeadlineUnique(TestPaperWork testPaper) {
        return testPaperWorkService.checkTestNameUnique(testPaper);
    }

    /**
     * 课程设置考试
     */
    @GetMapping("/buileTest")

    public String buileTest(String id, ModelMap mmap) {
        TestPaperWork testPaper = testPaperWorkService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/buildTestPage";
    }
    /**
     * 10.13 lzj复用
     * @param ids
     * @return
     */

    @Log(title = "开始考试")
    @PostMapping("/setExamY")
    @ResponseBody
    public Data setExamY(String ids)  {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        System.out.println("idList.get(0):"+idList.get(0));
        TestPaperWork testPaperOne = new TestPaperWork();
        testPaperOne.setSetExam("1");
        testPaperOne.setId(idList.get(0));
        try {
            System.out.println(testPaperWorkService.updateSetExam(testPaperOne));
            return toAjax(testPaperWorkService.updateSetExam(testPaperOne));
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
        System.out.println("setExamN:"+idList);
        TestPaperWork testPaperOne = new TestPaperWork();
        testPaperOne.setSetExam("0");
        testPaperOne.setId(idList.get(0));
        try {
            return toAjax(testPaperWorkService.updateSetExam(testPaperOne));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Data.error();
        }
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
     * 手动 组卷 页面跳转
     */
    @GetMapping("/Humanbuild")
    public String Humanbuild(String id, ModelMap mmap) {
        TestPaperWork testPaper = testPaperWorkService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("tid", getUser().getUserAttrId());
        mmap.put("cid",testPaper.getCoursrId());
        mmap.put("id", id);
        mmap.put("rule",testPaper.getRule());
        return prefix + "/human_build";
    }


    /**
     * 课程 组卷 页面跳转 人手一份
     */
    @GetMapping("/Coursebuild")
    public String Coursebuild(String id, ModelMap mmap) {
        TestPaperWork testPaper = testPaperWorkService.getById(id);
        String tid = getUser().getUserAttrId();
        mmap.put("cid", testPaper.getCoursrId());
        mmap.put("tid", tid);
        mmap.put("testPaper", testPaper);
        return prefix + "/course_build";
    }

    /**
     *  展示组卷
     * @param questions 试题分值数据
     * @param mmap
     * @return
     */
    @PostMapping("/buildTestToShow")
    @ResponseBody
    public List<QuestionPaper> buildTestToShow(@RequestBody List<MyQuestions> questions, ModelMap mmap) {
        //  TODO:   参数读取数据
        List<QuestionPaper> pager = new ArrayList<QuestionPaper>();
        if (!questions.isEmpty()) {
            for (MyQuestions question : questions) {
                QuestionPaper up = new QuestionPaper();
                List<String> tarr = new ArrayList<String>();
                List<String> tList = new ArrayList<String>();
                up.setTid(question.getId());
                up.setTitle(question.getTitle());
                up.setTitleType(question.getTitleTypeName());
                up.setNumber(question.getTitleTypeId());
                up.setDifficulty(question.getDifficulty());
                up.setQexposed(question.getQexposed());
                up.setScore(question.getQuestionscore());
                for (String aTList : tList) {
                    if (aTList != null) {
                        tarr.add(aTList);
                    }
                }
                up.setOptlist(tarr);
                pager.add(up);
            }
        }

        return pager;
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
     * 手动添加试卷  保存试卷试题
     *
     * @param testQuestionsId
     * @param testPaperId
     * @return
     * @throws Exception
     */
    @PostMapping("/humanAdd")
    @ResponseBody
    public Data humanAdd(@RequestParam String testQuestionsId, @RequestParam String testPaperId) throws
            Exception {
        String uid = getUser().getUserAttrId();
        String testid = testPaperId;
        String questionid = testQuestionsId;
        TestPaperWork testPaper2 = testPaperWorkService.getById(questionid);
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
        int testpaperScore = 0;
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
            testpaperScore += Integer.parseInt(strarr[1]);
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
        TestPaperWork testPaper = testPaperWorkService.getById(testid);
        //  一对多关系映射
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
        return toAjax(testPaperWorkService.update(testPaper));
    }


    /**
     * 获取年级
     */
    @PostMapping("/getStudents")

    @ResponseBody
    public List<TestPaperWork> getStudents() {
        return testPaperWorkService.getStudentInfo("37");

    }

    /**
     * 获取科目
     */
    @PostMapping("/getTcoName")
    @ResponseBody
    public List<TestPaperWork> getTcoName(String cId) {
        System.out.println("cId"+cId);
        System.out.println("testPaperOneService.getTcoName(cId)"+testPaperWorkService.getTcoName(cId));
        return testPaperWorkService.getTcoName(cId);

    }

    /**
     * 获取学生
     */
    @PostMapping("/getTcoStu")

    @ResponseBody
    public List<TestPaperWork> getTcoStu() {
        return testPaperWorkService.getTcoStu("37");

    }

    /**
     * 页面跳转 开始考试
     *
     * @return
     */
    @GetMapping("/stuToTest")
    public String stuToTest(String id, ModelMap modelMap) {
        String uid = getUser().getUserAttrId();
        TestPaperWork testPaper = new TestPaperWork();
        testPaper = testPaperWorkService.getById(id);
        modelMap.put("stuName", getUser().getLoginName());
        modelMap.put("name", getUser().getUserName());
        modelMap.put("studentId", uid);
        modelMap.put("testPaper", testPaper);
        modelMap.put("paperId", id);
        return prefix + "/stuTest";
    }

    /**
     * 开始测试 初始化考试页面
     *
     * @return
     */
    @RequestMapping("/startCoursePaper")
    @ResponseBody
    public List<TestpaperQuestions> testStart(String paperId, String studentId, String paperRule, String chapterName, ModelMap mmap) throws Exception {
        System.out.println("type:"+paperRule);
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        if (paperRule.equals("1")) {
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
        } else {
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
                    String uid = getUser().getUserAttrId();
                    int newId = Integer.parseInt(uid);
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
        for (int i = 0; i < tqvolist.size(); i++) {
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
                String uid = getUser().getUserAttrId();
                int newId = Integer.parseInt(uid);
                coursetestStuoptionanswer.setCreateId(Integer.parseInt(studentId));
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
        System.out.println("tqvolist:::"+tqvolist);
        return tqvolist;
    }

//
//    @GetMapping("PoiWord")
//    public String PoiWord(String id, String stuNum,String paperId, HttpServletResponse response){
//        XWPFDocument document= new XWPFDocument();
//        try {
//            // 这里写你在数据库中查出的数据
//
//            List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
////        TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
////        testpaperTestquestions.setTestPaperOneId(paperId);
////        List<TestpaperOneTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperOneTestquestions>();
////        testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperTestquestions);
////        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
////            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
////            testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
////            if (testpaperQuestions != null) {
////                tqvolist.add(testpaperQuestions);
////            }
////        }
//            TestPaperWork testPaperOne = testPaperWorkService.getById(paperId);
//            UserPaperWork userExam = userExamService.getById(id);
//            SysUser sysUser = sysUserService.getById(userExam.getUserId());
//            tqvolist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, sysUser.getId());
//            int flag = 64;
//            int flag1 = 64;
//            int num=0;
//            //然后循环你的数据，因为有多条，不想循环就直接set
//            XWPFParagraph titleParagraph = document.createParagraph();
//            //设置段落居中
//            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
//
//            // 标题
//            XWPFRun titleParagraphRun = titleParagraph.createRun();
//            // 然后把你查出的数据插入到document中去就可以了
//            titleParagraphRun.setText(testPaperOne.getHeadline()+"\r");
//
//
//            // 设置字体颜色
//            titleParagraphRun.setColor("000000");
//            // 设置字体大小
//            titleParagraphRun.setFontSize(20);
//            XWPFRun titleParagraphRun1 = titleParagraph.createRun();
//            titleParagraphRun1.setText(testPaperOne.getTestName()+"\r");
//
//            // 设置字体颜色
//            titleParagraphRun1.setColor("000000");
//            // 设置字体大小
//            titleParagraphRun1.setFontSize(15);
//            XWPFRun titleParagraphRun2 = titleParagraph.createRun();
//            titleParagraphRun2.setText("学号："+sysUser.getLoginName()+" 姓名："+sysUser.getUserName()+"\r");
//
//            // 设置字体颜色
//            titleParagraphRun2.setColor("000000");
//            // 设置字体大小
//            titleParagraphRun2.setFontSize(10);
//
//            for (TestpaperQuestions oralHisStructureText:tqvolist){
//                num+=1;
//
//
//
//                //添加标题
//
//                //段落
//                XWPFParagraph firstParagraph = document.createParagraph();
//                XWPFRun run = firstParagraph.createRun();
//                run.setText(oralHisStructureText.getTitleTypeName()+"\r");
//                run.setColor("000000");
//                run.setFontSize(13);
//                XWPFRun run1 = firstParagraph.createRun();
//
//                if (oralHisStructureText.gettQuestiontemplateNum().equals("1")){
//                    run1.setText(num+"."+oralHisStructureText.getTitle()+"\r");
//                    for (int i = 0; i < oralHisStructureText.getTestpaperOptinanswerList().size(); i++) {
//                        flag +=1;
//                        char fla = (char)flag;
//                        run1.setText(fla + "." + oralHisStructureText.getTestpaperOptinanswerList().get(i).getStoption()+"\r");
//                    }
//                    run1.setColor("000000");
//                    run1.setFontSize(10);
//                }else  if (oralHisStructureText.gettQuestiontemplateNum().equals("2")){
//                    run1.setText(num+"."+oralHisStructureText.getTitle()+"\r");
//                    for (int i = 0; i < oralHisStructureText.getTestpaperOptinanswerList().size(); i++) {
//                        flag1 +=1;
//                        char fla = (char)flag1;
//                        run1.setText(fla + "." + oralHisStructureText.getTestpaperOptinanswerList().get(i).getStoption()+"\r");
//                    }
//                    run1.setColor("000000");
//                    run1.setFontSize(10);
//                }
//                else {
//                    run1.setText(num + "." + oralHisStructureText.getTitle() + "\r");
//                    run1.setColor("000000");
//                    run1.setFontSize(10);
//                }
//
//
//                System.out.println("oralHisStructureText"+oralHisStructureText.getTestpaperOptinanswerList());
//                //换行
//                XWPFParagraph paragraph1 = document.createParagraph();
//                XWPFRun paragraphRun1 = paragraph1.createRun();
//                paragraphRun1.setText("\r");
//            }
//            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
//            String s = String.valueOf(System.currentTimeMillis());
//            String fileName = sysUser.getLoginName()+""+s.substring(7,13);
//
////        new WordUtil().exportWord(document,response,fileName,file);
//        } catch (Exception e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        System.out.println("导出成功！！！！");
//        return "success";
//
//    }
//
//    @RequestMapping("/exportNullExamUrl")
//    @ResponseBody
//    public Data exportNullExamUrl(String ids, String stuNums, String paperId, HttpServletResponse response) throws IOException {
//
//        try {
//            // 这里写你在数据库中查出的数据
//            List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
//            String[] userExam = Convert.toStrArray(ids);
//            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
//            TestPaperWork testPaperOne = testPaperWorkService.getById(paperId);
//            List<UserPaperWork> userExamList = userExamService.listByIds(idList);
//            System.out.println("userExamList"+userExamList);
//            SysUser sysUser1 = sysUserService.selectUserByUserAttrId(testPaperOne.getCreateId());
//            Course course = courseService.getById(testPaperOne.getCoursrId());
//            for (int i = 0; i <userExamList.size(); i++) {
//                XWPFDocument document= new XWPFDocument();
//                SysUser sysUser = sysUserService.getById(userExamList.get(i).getUserId());
//                Student student =  studentService.getById(sysUser.getUserAttrId());
//                Clbum clbum = clbumService.getById(student.getCid());
//                tqvolist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, sysUser.getId());
//
//
//                int num=0;
//                //然后循环你的数据，因为有多条，不想循环就直接set
//                XWPFParagraph titleParagraph = document.createParagraph();
//                //设置段落居中
//                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
//
//                // 标题
//                XWPFRun titleParagraphRun = titleParagraph.createRun();
//                // 然后把你查出的数据插入到document中去就可以了
//                titleParagraphRun.setText("东莞理工学院城市学院（本科）试卷（A卷）\r");
//                titleParagraphRun.setBold(true);
//
//                // 设置字体颜色
//                titleParagraphRun.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun.setFontSize(16);
//                XWPFRun titleParagraphRun1 = titleParagraph.createRun();
//                titleParagraphRun1.setText("2020 -2021 学年第二学期\r");
//                titleParagraphRun1.setBold(true);
//                // 设置字体颜色
//                titleParagraphRun1.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun1.setFontSize(12);
//                XWPFRun titleParagraphRun11 = titleParagraph.createRun();
//                titleParagraphRun11.setText(testPaperOne.getTestName()+"\r\r");
//                titleParagraphRun11.setBold(true);
//
//                // 设置字体颜色
//                titleParagraphRun11.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun11.setFontSize(12);
//                XWPFRun titleParagraphRun2 = titleParagraph.createRun();
//                titleParagraphRun2.setText("开课单位："+testPaperOne.getHeadline()+"  考试形式：机试（闭） 卷，允许带\t入场\r科目："+course.getName()+" 班级："+clbum.getName()+"\r学号：" + sysUser.getLoginName() + " 姓名：" + sysUser.getUserName() + "\r");
//                // 设置字体颜色
//                titleParagraphRun2.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun2.setFontSize(10);
//
//                for (TestpaperQuestions oralHisStructureText:tqvolist){
//                    num+=1;
//
//
//
//                    //添加标题
//
//                    //段落
//                    XWPFParagraph firstParagraph = document.createParagraph();
//                    XWPFRun run = firstParagraph.createRun();
//                    run.setText(oralHisStructureText.getTitleTypeName()+"\r");
//                    run.setColor("000000");
//                    run.setFontSize(13);
//                    XWPFRun run1 = firstParagraph.createRun();
//
//                    if (oralHisStructureText.gettQuestiontemplateNum().equals("1")){
//                        int flag = 64;
//                        run1.setText(num+"、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）"+oralHisStructureText.getTitle()+"\r");
//                        for (int j = 0; j < oralHisStructureText.getTestpaperOptinanswerList().size(); j++) {
//                            flag += 1;
//                            char fla = (char) flag;
//                            run1.setText(fla + "、" + oralHisStructureText.getTestpaperOptinanswerList().get(j).getStoption() + "\r");
//                        }
//                        run1.setColor("000000");
//                        run1.setFontSize(10);
//                    }else  if (oralHisStructureText.gettQuestiontemplateNum().equals("2")){
//                        int flag1 = 64;
//                        run1.setText(num+"、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）"+oralHisStructureText.getTitle()+"\r");
//                        for (int j = 0; j < oralHisStructureText.getTestpaperOptinanswerList().size(); j++) {
//                            flag1 +=1;
//                            char fla = (char)flag1;
//                            run1.setText(fla + "、" + oralHisStructureText.getTestpaperOptinanswerList().get(j).getStoption()+"\r");
//                        }
//                        run1.setColor("000000");
//                        run1.setFontSize(10);
//                    }
//                    else {
//                        run1.setText(num + "、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）" + oralHisStructureText.getTitle() + "\r");
//                        run1.setColor("000000");
//                        run1.setFontSize(10);
//                    }
//                    //换行
//                    XWPFParagraph paragraph1 = document.createParagraph();
//                    XWPFRun paragraphRun1 = paragraph1.createRun();
//                    paragraphRun1.setText("\r");
//                }
//                String fileName = sysUser.getLoginName() + ""+sysUser.getUserName()+""+testPaperOne.getTestName();
//                File file = new File("G:\\"+sysUser1.getUserName()+"\\"+clbum.getName()+"_"+course.getName());
////                File file = new File("/root/soft/teacher/"+sysUser1.getUserName()+"/"+clbum.getName()+"_"+course.getName());
//                if (!file.exists()){
//                    file.mkdirs();
//                }
////                new WordUtil().exportWord(document,response,fileName,file);
//            }
////            FileOutputStream fos1 = new FileOutputStream(new File("G:\\"+sysUser1.getUserName()+"\\"+sysUser1.getUserName()+".zip"));
////            String fileName=sysUser1.getUserName();
////            String zipPath="G:\\"+sysUser1.getUserName();
////            ZipUtils.toZip(zipPath,fos1,response,true);
//
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            System.out.println("e1"+e1);
//        }
//        return toAjax(true);
//    }
//



//    @RequestMapping("/exportExamUrl")
//    @ResponseBody
//    public Data exportExamUrl(String ids, String stuNums, String paperId, HttpServletResponse response) throws IOException {
//
//        try {
//            // 这里写你在数据库中查出的数据
//            List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
//            String[] userExam = Convert.toStrArray(ids);
//            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
//            TestPaperWork testPaperOne = testPaperWorkService.getById(paperId);
//            List<UserPaperWork> userExamList = userExamService.listByIds(idList);
//            System.out.println("userExamList" + userExamList);
//            SysUser sysUser1 = sysUserService.selectUserByUserAttrId(testPaperOne.getCreateId());
//            Course course = courseService.getById(testPaperOne.getCoursrId());
//            for (int k = 0; k < userExamList.size(); k++) {
//                XWPFDocument document = new XWPFDocument();
//                SysUser sysUser = sysUserService.getById(userExamList.get(k).getUserId());
//                Student student = studentService.getById(sysUser.getUserAttrId());
//                Clbum clbum = clbumService.getById(student.getCid());
//                tqvolist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, sysUser.getId());
//
//                int num = 0;
//                //然后循环你的数据，因为有多条，不想循环就直接set
//                XWPFParagraph titleParagraph = document.createParagraph();
//                //设置段落居中
//                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
//
//                // 标题
//                XWPFRun titleParagraphRun = titleParagraph.createRun();
//                // 然后把你查出的数据插入到document中去就可以了
//                titleParagraphRun.setText("东莞理工学院城市学院（本科）试卷（A卷）\r");
//
//
//                // 设置字体颜色
//                titleParagraphRun.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun.setFontSize(16);
//                titleParagraphRun.setFontFamily("黑体");
//                titleParagraphRun.setBold(true);
//                XWPFRun titleParagraphRun1 = titleParagraph.createRun();
//                titleParagraphRun1.setText("2020 -2021 学年第二学期\r\r");
//
//                // 设置字体颜色
//                titleParagraphRun1.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun1.setFontSize(12);
//                titleParagraphRun1.setFontFamily("Calibri");
//                titleParagraphRun1.setBold(true);
//                XWPFRun titleParagraphRun2 = titleParagraph.createRun();
//                titleParagraphRun2.setText("开课单位： " + testPaperOne.getHeadline() + "  考试形式： 机试（闭）卷，允许带_____入场\r");
//                // 设置字体颜色
//                titleParagraphRun2.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun2.setFontSize(12);
//                titleParagraphRun2.setUnderline(UnderlinePatterns.WORDS);
//                XWPFRun titleParagraphRun3 = titleParagraph.createRun();
//                titleParagraphRun3.setText("科目： " + course.getName() + " 班级： " + clbum.getName() + "\r学号： " + sysUser.getLoginName() + " 姓名： " + sysUser.getUserName() + "\r");
//                // 设置字体颜色
//                titleParagraphRun3.setColor("000000");
//                // 设置字体大小
//                titleParagraphRun3.setFontSize(12);
//                titleParagraphRun3.setUnderline(UnderlinePatterns.WORDS);
//                for (TestpaperQuestions oralHisStructureText : tqvolist) {
//                    num += 1;
//
//
//                    //添加标题
//
//                    //段落
//                    XWPFParagraph firstParagraph = document.createParagraph();
//                    XWPFRun run = firstParagraph.createRun();
//                    run.setText(oralHisStructureText.getTitleTypeName() + "\r");
//                    run.setColor("000000");
//                    run.setFontSize(13);
//                    XWPFRun run1 = firstParagraph.createRun();
//
//                    if (oralHisStructureText.gettQuestiontemplateNum().equals("1")) {
//                        int flag = 64;
//
//                        String[] stuAnswerList = oralHisStructureText.getStuOptionExamanswerList().get(0).getStuAnswer().split(";");
//                        String stuAnswer = null;
//                        for (int i = 0; i < stuAnswerList.length; i++) {
//                            if (stuAnswerList[i].equals("0")) {
//
//                            } else {
//                                stuAnswer = stuAnswerList[i];
//                            }
//                        }
//                        run1.setText(num + "、(得分：_______)（" + oralHisStructureText.getQuestionScore() + "分）" + oralHisStructureText.getTitle() + "\r学生答案：" + stuAnswer + "\r");
//                        for (int j = 0; j < oralHisStructureText.getTestpaperOptinanswerList().size(); j++) {
//                            flag += 1;
//                            char fla = (char) flag;
//                            run1.setText(fla + "、" + oralHisStructureText.getTestpaperOptinanswerList().get(j).getStoption() + "\r");
//                        }
//                        run1.setColor("000000");
//                        run1.setFontSize(10);
//                    } else if (oralHisStructureText.gettQuestiontemplateNum().equals("2")) {
//                        int flag1 = 64;
//                        String[] stuAnswerList = oralHisStructureText.getStuOptionExamanswerList().get(0).getStuAnswer().split(";");
//                        String stuAnswer = "";
//                        for (int i = 0; i < stuAnswerList.length; i++) {
//                            System.out.println("stuAnswerList[i]" + stuAnswerList[i]);
//                            if (stuAnswerList[i].equals("0")) {
//                            } else {
//                                stuAnswer += stuAnswerList[i];
//                            }
//                        }
//                        run1.setText(num + "、(得分：_______)（" + oralHisStructureText.getQuestionScore() + "分）" + oralHisStructureText.getTitle() + "\r学生答案：" + stuAnswer + "\r");
//                        for (int j = 0; j < oralHisStructureText.getTestpaperOptinanswerList().size(); j++) {
//                            flag1 += 1;
//                            char fla = (char) flag1;
//                            run1.setText(fla + "、" + oralHisStructureText.getTestpaperOptinanswerList().get(j).getStoption() + "\r");
//                        }
//                        run1.setColor("000000");
//                        run1.setFontSize(10);
//
//                    } else {
//                        for (int i = 0; i < oralHisStructureText.getStuOptionExamanswerList().size(); i++) {
//                            if (oralHisStructureText.getStuOptionExamanswerList().get(i).getAnswerType() != "") {
//                                String[] s = oralHisStructureText.getStuOptionExamanswerList().get(i).getAnswerType().split(";");
//                                run1.setText(num + "、(得分：_______)（" + oralHisStructureText.getQuestionScore() + "分）" + oralHisStructureText.getTitle() + "\r学生答案：\r" + oralHisStructureText.getStuOptionExamanswerList().get(i).getStuAnswer() + "\r");
//                                run1.setColor("000000");
//                                run1.setFontSize(10);
//                                for (int j = 0; j < s.length; j++) {
//                                    String path = "http://localhost:8081/storage/showCondensedPicture?fileId=" + s[j];
//                                    System.out.println("path" + path);
//                                    URL url = new URL(path);
//                                    InputStream is1 = url.openStream();
//                                    // 因为FileInputStream没有重写reset() 所有将流转为了byte数组
//                                    byte[] bs1 = IOUtils.toByteArray(is1);
//                                    BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(bs1));
//                                    run1.addPicture(new ByteArrayInputStream(bs1), Document.PICTURE_TYPE_JPEG, "", 1385800, 785800);
//                                }
//                            } else {
//                                run1.setText(num + "、(得分：_______)（" + oralHisStructureText.getQuestionScore() + "分）" + oralHisStructureText.getTitle() + "\r学生答案：" + oralHisStructureText.getStuOptionExamanswerList().get(i).getStuAnswer() + "\r");
//                                run1.setColor("000000");
//                                run1.setFontSize(10);
//                            }
//                        }
//                    }
//                    //换行
//                    XWPFParagraph paragraph1 = document.createParagraph();
//                    XWPFRun paragraphRun1 = paragraph1.createRun();
//                    paragraphRun1.setText("\r");
//                }
//
//////                String fileName = sysUser.getLoginName() + "" + s.substring(7, 13);
//                String fileName = sysUser.getLoginName() + "" + sysUser.getUserName() + "" + testPaperOne.getTestName();
//                File file = new File("F:\\" + sysUser1.getUserName() + "\\" + clbum.getName() + "_" + course.getName());
////                File file = new File("/root/soft/"+sysUser1.getUserName()+"/"+clbum.getName()+"_"+course.getName());
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//                new WordUtilOne().exportWord(document, response, fileName, file);
//            }
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
//        return toAjax(true);
//    }

    //excel导出
    @Log(title = "学生成绩信息", actionType = ActionType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public Data export(UserPaperWork userExam) {
        try{
            List<UserPaperWork> users = userExamService.list(userExam);
            ExcelUtil<UserPaperWork> util = new ExcelUtil<>(UserPaperWork.class);
            return util.exportExcel(users, "studentPaperWork");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }

    //excel导出某卷成绩
    @Log(title = "学生某卷成绩信息", actionType = ActionType.EXPORT)
    @PostMapping("/exportByPaper")
    @ResponseBody
    public Data exportByPaper(UserPaperWork userPaperWork) {
        try{
            List<UserPaperWork> users = userExamService.list(userPaperWork);
            ExcelUtil<UserPaperWork> util = new ExcelUtil<>(UserPaperWork.class);
            return util.exportExcel(users, "studentPaperWork");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
    ///**
    // * 获取专业信息
    // *
    // * @param teacherVos
    // */
    //private void fillMajor(List<UserPaperWorkVo> teacherVos) {
    //    Map<String, Major> majorMap = majorService.map(null);
    //    teacherVos.forEach(t -> t.setMajor(majorMap.get(t.getDepartment().getName())));
    //}
    //
    ///**
    // * 获取系部信息
    // *
    // * @param teacherVos
    // */
    //private void fillDepartment(List<UserPaperWorkVo> teacherVos) {
    //    Map<String, Department> departmentMap = departmentService.map(null);
    //    teacherVos.forEach(t -> t.setDepartment(departmentMap.get(t.getMajor().getDid())));
    //}

    private List<UserPaperWorkVo> trans(List<UserPaperWork> users, Map<String, UserPaperWork> userExamMap) {
        List<UserPaperWorkVo> teacherVos = new LinkedList<>();
        users.forEach(u -> teacherVos.add(trans(u)));
        return teacherVos;
    }
    private UserPaperWorkVo trans(UserPaperWork userExam) {
        UserPaperWorkVo userExamVo = new UserPaperWorkVo();
        BeanUtils.copyProperties(userExam, userExamVo);
        userExamVo.setUserPaperWork(userExam);
        return userExamVo;
    }

    //TODO: 前台修改为提交Json对象
    @ApiOperation("作业提交")
    @PostMapping("/saveCourseWorkAnswer")
    @ResponseBody
    public Data saveChapterTestAnswer(@RequestBody List<OptionSubmitVo> options) {
        options.forEach(o -> {
            Uuid uuid = new Uuid();
            o.setuUid(uuid.UId());
            if (!o.getTrue()) {
                o.setScore(0);
            }
        });
        return toAjax(stuOptionanswerService.saveOptionAnswers(options, getUserId()));
    }


    @GetMapping("/showDetail")

    public String showDetail(String id, ModelMap modelMap) {
        TestPaperWork testPaperOne = testPaperWorkService.getById(id);
        modelMap.put("cid", testPaperOne.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("id", id);
        modelMap.put("PaperRule", testPaperOne.getRule());
        System.out.println(id);
        return prefix + "/detailList";
    }
    /**
     * 获取监考链接
     */
    @Value("${wstom.contextPath}")
    private String ip;
    @GetMapping("/examControl")

    public String examControl(String id, ModelMap modelMap) {
        TestPaperWork testPaperOne = testPaperWorkService.getById(id);
        modelMap.put("cid", testPaperOne.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("id", id);
        modelMap.put("PaperRule", testPaperOne.getRule());

        modelMap.put("url",ip+"/examControl/login?cid="+testPaperOne.getCoursrId()+"&tid="+getUser().getUserAttrId()+"&paperId="+id);
        return prefix + "/examControl";
    }
    /**
     * 学生试卷详情
     */

    @PostMapping("/detailList/{id}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String id) throws Exception {

        UserPaperWork userExam = new UserPaperWork();
        userExam.setTestPaperWorkId(id);

        startPage();
        List list = userExamService.selectList(userExam);
        return wrapTable(list);
    }
    /**
     * 在线考生
     *
     * @param cid
     * @param modelMap
     * @return
     */

    @GetMapping("/onlineStudent/{cid}")
    public String onlineStudentList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        modelMap.put("tid", getUser().getUserAttrId());
        return prefix + "/onine_stu_list";
    }
    /**
     * 查询在线考生列表
     */


    @PostMapping("/onlineStudent/list/{cid}")
    @ResponseBody
    public TableDataInfo onlineList(@PathVariable String cid, UserPaperWork userExam2, SysUserOnline sysUserOnline, TestPaperWork testPaper) {
        UserPaperWork userExam = new UserPaperWork();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(getUser().getUserAttrId());
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperWorkId(userExam2.getTestPaperWorkId());
        startPage();
        List<UserPaperWork> list = userExamService.findStuExamPaperOne(userExam);
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMadeScore().equals(doneFlag)) {
                    list.get(i).setMadeScore(done);
                } else {
                    list.get(i).setMadeScore(none);
                }
                if (list.get(i).getSumbitState().equals(noneFlag)) {
                    list.get(i).setSumbitState(none);
                } else {
                    list.get(i).setSumbitState(done);

                }
            }
        }
        return wrapTable(list);
    }
    /**
     * 考场监控
     *
     * @param cid
     * @param modelMap
     * @return
     */

    @GetMapping("/infoStudent/{cid}")
    public String infoStudentList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        modelMap.put("tid", getUser().getUserAttrId());
        return prefix + "/info_stu_list";
    }
    /**
     * 查询考场监控列表
     */


    @PostMapping("/infoStudent/list/{cid}")
    @ResponseBody
    public TableDataInfo infoList(@PathVariable String cid, UserPaperWork userExam2, SysUserOnline sysUserOnline, TestPaperWork testPaper) {
        UserPaperWork userExam = new UserPaperWork();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(getUser().getUserAttrId());
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperWorkId(userExam2.getTestPaperWorkId());
        startPage();
        List<UserPaperWork> list = userExamService.findStuExamPaperTwo(userExam);

        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMadeScore().equals(doneFlag)) {
                    list.get(i).setMadeScore(done);
                } else {
                    list.get(i).setMadeScore(none);
                }
                if (list.get(i).getSumbitState().equals(noneFlag)) {
                    list.get(i).setSumbitState(none);
                } else {
                    list.get(i).setSumbitState(done);

                }
            }
        }
        return wrapTable(list);
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
        return prefix + "/stu_paper_list";
    }
    /**
     * 查询学生试卷列表
     */


    @PostMapping("/studentPaper/list/{cid}")
    @ResponseBody
    public TableDataInfo paperList(@PathVariable String cid, UserPaperWork userExam2, TestPaperWork testPaper) {

        UserPaperWork userExam = new UserPaperWork();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(getUser().getUserAttrId());
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperWorkId(userExam2.getTestPaperWorkId());
        startPage();
        List<UserPaperWork> list = userExamService.findStuExamPaper(userExam);
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (UserPaperWork userPaperWork : list) {
                System.out.println(userPaperWork);
                if (userPaperWork.getMadeScore().equals(doneFlag)) {
                    userPaperWork.setMadeScore(done);
                } else {
                    userPaperWork.setMadeScore(none);
                }
                if (userPaperWork.getSumbitState().equals(noneFlag)) {
                    userPaperWork.setSumbitState(none);
                } else {
                    userPaperWork.setSumbitState(done);

                }
            }
        }
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

        SysUser user = adminService.getUser(sysUser);

        UserPaperWork userTest = new UserPaperWork();
        userTest.setTestPaperWorkId(id);
        userTest.setUserId(user.getId());
        List<UserPaperWork> userTestList = userExamService.list(userTest);
        String utestId = "";
        try {
            if (userTestList.size() != 0) {
                utestId = userTestList.get(0).getId();
            }
            return toAjax(userExamService.removeById(id));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     *批量删除记录
     */
    /**
     * 删除试卷
     */

    @Log(title = "试卷", actionType = ActionType.DELETE)
    @PostMapping("/studentPaper/removeAll")
    @ResponseBody
    public Data removeAllStuPaper(String ids) {
        try {
            System.out.println("ids:" + ids);
            String[] both;
            String[] tid;
            String[] stuNum;
            both = ids.split("&");
            if (both.length >= 1) {
                System.out.println("开始删除1");
                tid = both[0].split(",");
                stuNum = both[1].split(",");
                for (int i = 0; i <tid.length ; i++) {
                    System.out.println("开始删除2");
                    TestPaperWork testPaper = testPaperWorkService.getById(tid[i]);
                    SysUser sysUser = new SysUser();
                    sysUser.setLoginName(stuNum[i]);
                    List<SysUser> sysUserList = adminService.getUserList(sysUser);
                    UserPaperWork userTest = new UserPaperWork();
                    userTest.setTestPaperWorkId(tid[i]);
                    userTest.setUserId(sysUserList.get(0).getUserAttrId());
                    List<UserPaperWork> userTestList = userExamService.list(userTest);
                    userExamService.removeById(userTestList.get(0).getId());
                    if (testPaper.getRule().equals("1")) {
                        CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
                        coursepaperCoursequestions.setTestPaperId(tid[i]);
                        coursepaperCoursequestions.setStudentId(sysUserList.get(0).getUserAttrId());
                        List<CoursepaperCoursequestions> coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
                        for (int j = 0; j < coursepaperCoursequestionsList.size(); j++) {
                            System.out.println("删除");
                            coursepaperCoursequestionsService.removeById(coursepaperCoursequestionsList.get(j).getId());
                        }
                    }
                }
                return Data.success("删除成功");
            } else {
                return error("删除出错");
            }
        } catch (Exception e) {
            System.out.println("错误详情："+e.getCause());
            return error(e.getMessage());
        }
    }

    /**
     * 查看学生考试
     */
    @GetMapping("/showStuPaper")

    public String showStuPaper(String id, String stuNum, ModelMap mmap) {

        //UserPaperWork userExam = userExamService.selectByExamId(id);
        //TestPaperWork testPaper = testPaperWorkService.getById(userExam.getTestPaperWorkId());
        UserPaperWork userPaperWork = userPaperWorkService.selectByExamId(id);
        TestPaperWork testPaperWork = testPaperWorkService.getById(userPaperWork.getTestPaperWorkId());
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        mmap.put("testPaper", testPaperWork);
        mmap.put("studentId", user.getId());
        mmap.put("stuUserId", sysUser.getId());
        mmap.put("stuName", sysUser.getLoginName());
        mmap.put("name", sysUser.getUserName());
        mmap.put("sysUser", user);
        mmap.put("paperId", userPaperWork.getTestPaperWorkId());

        return prefix + "/showStuPaper";

    }
    /**
     * 修改学生试卷提交状态
     */

    @Log(title = "学生试卷", actionType = ActionType.UPDATE)
    @PostMapping("/updateStateEdit")
    @ResponseBody
    public Data updateStateEdit(String id, String userId, String testPaperOneId) throws Exception {
        UserPaperWork userExam = new UserPaperWork();
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(userId);
        SysUser user = adminService.getUser(sysUser);
        userExam.setId(id);
        userExam.setUserId(user.getId());
        userExam.setUpdateBy(getUser().getLoginName());
        userExam.setTestPaperWorkId(testPaperOneId);
        userExam.setSumScore("0");
        return toAjax(userExamService.update(userExam));
    }

    /**
     * 修改学生考试
     */
    @GetMapping("/checkStuPaper")

    public String checkStuPaper(String id, String stuNum, ModelMap mmap) {
        UserPaperWork userExam = userExamService.selectByExamId(id);
        TestPaperWork testPaper = testPaperWorkService.getById(userExam.getTestPaperWorkId());
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        mmap.put("testPaper", testPaper);
        mmap.put("studentId", user.getId());
        mmap.put("stuUserId", sysUser.getId());
        mmap.put("stuName", sysUser.getLoginName());
        mmap.put("name", sysUser.getUserName());
        mmap.put("sysUser", user);
        mmap.put("paperId", userExam.getTestPaperWorkId());
        return prefix + "/checkStuPaper";
    }
    /**
     * 教师改卷
     */
    @GetMapping("/makeScore")
    public String makeScore(String id, String stuNum, ModelMap mmap) {
        UserPaperWork userExam = userExamService.getById(id);
        TestPaperWork testPaper = testPaperWorkService.getById(userExam.getTestPaperWorkId());
        System.out.println("TestPaperOneId::"+userExam.getTestPaperWorkId());
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        String studentId = "";
        mmap.put("testPaper", testPaper);
        mmap.put("studentId", user.getId());
        mmap.put("sysUser", user);
        mmap.put("paperId", userExam.getTestPaperWorkId());
        return prefix + "/makeScore";
    }

    /**
     * 改卷时初始化页面
     */
    @ApiOperation("显示做题情况")
    @RequestMapping("/startMakePaper")
    @ResponseBody
    public List<TestpaperQuestions> startMakePaper(String paperId, String studentId, ModelMap mmap) throws Exception {
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
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
                String uid = getUser().getUserAttrId();
                int newId = Integer.parseInt(uid);
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
        for (int i = 0; i < tqvolist.size(); i++) {
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
                String uid = getUser().getUserAttrId();
                int newId = Integer.parseInt(uid);
                coursetestStuoptionanswer.setCreateId(Integer.parseInt(studentId));
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
     * 提交作业分数
     */
    @ApiOperation("提交作业分数")
    @RequestMapping(value = "/saveScore", method = RequestMethod.POST)
    @ResponseBody
    public Data saveScore(String paperId, String userId, @RequestBody List<OptionExamSubmitVo> scoreArray) throws Exception {
        System.out.println(scoreArray);
        int sumScore = 0;
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        for (OptionExamSubmitVo o:scoreArray) {
            StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
            stuOptionExamanswer.setId(o.getStuOptionExamanswerId());
            stuOptionExamanswer.setQuestionScore(o.getScore());
            stuOptionExamanswer.setUpdateId(Integer.valueOf(getUserId()));
            stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
            optionanswers.add(stuOptionExamanswer);
            sumScore=sumScore+Integer.parseInt(String.valueOf(o.getScore()));
        }
        int i = stuOptionExamanswerService.updateListAndTotalScore(optionanswers, paperId, userId);
        if (i>0){
            //通过paperid 和学生userid更新学生作业分数
            UserPaperWork userPaperWork = new UserPaperWork();
            userPaperWork.setUserId(userId);
            userPaperWork.setUpdateId(getUserId());
            userPaperWork.setStuScore(String.valueOf(sumScore));
            userPaperWork.setTestPaperWorkId(paperId);
            Data data = toAjax(userPaperWorkService.updatePaperWorkScore(userPaperWork));
            System.out.println(data);
            return data;
        } else {
             return toAjax(false);
        }


    }


    @PostMapping(value = "/uploadImg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Data uploadImg(Integer paperId, Integer studentId, String tutId, String stoption, StuOptionExamanswer stuOptionExamanswer, MultipartFile[] file) throws Exception {
        String filename = null;
        StuOptionExamanswer stuOptionExamanswer1 = stuOptionExamanswerService.selectByCreatId(paperId.toString(),studentId.toString(),stoption);
        for (int i = 0; i < file.length; i++) {
            System.out.println(file[i].getOriginalFilename());
            if (!file[i].isEmpty()) {
                try {
                    //保存图片
                    Data result = FileUploadUtils.upload(jwtUtils.getUserIdFromToken(getToken()),StorageConstants.STORAGE_THUMBNAIL, file[i], false, FileUtils.allowImage);
                    if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
                        System.out.println("resultresult"+result);
                        return result;
                    }
                    filename = (String) result.get(StorageConstants.FILE_ID);

                } catch (FileNameLimitExceededException | IOException | ApplicationException e) {
                    return Data.error(e.getMessage());
                }
                System.out.println(paperId);
                System.out.println(studentId);
                System.out.println(stoption);
                stuOptionExamanswer.setPaperOneId(paperId);
                stuOptionExamanswer.setCreateId(studentId);
                stuOptionExamanswer.setStoption(stoption);
                stuOptionExamanswer.setAnswerType(filename);
                System.out.println(stuOptionExamanswer1.getAnswerType());
                System.out.println(filename);
            }
        }
        return toAjax(stuOptionExamanswerService.updateStuAnswer(stuOptionExamanswer));
    }
    @PostMapping("/deleteFileId")
    @ResponseBody
    public Data deleteFileId(String fileId, String stoption, String testPaperOneId, String studentId) throws Exception {
        StuOptionExamanswer stuOptionExamanswer =  stuOptionExamanswerService.selectByCreatId(testPaperOneId,studentId,stoption);
        String[] answerType = stuOptionExamanswer.getAnswerType().split(";");
        List<String> list = new ArrayList<>();
        for (int i = 1; i < answerType.length; i++) {
            list.add(answerType[i]);
            System.out.println(answerType[i]);
            System.out.println(fileId);
        }

        System.out.println("listlistlist"+list);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(fileId)){
                list.remove(list.get(i));
            }
        }
        System.out.println("listlistlist1"+list);
        StuOptionExamanswer stuOptionExamanswer1 = new StuOptionExamanswer();
        stuOptionExamanswer1.setPaperOneId(Integer.parseInt(testPaperOneId));
        stuOptionExamanswer1.setCreateId(Integer.parseInt(studentId));
        stuOptionExamanswer1.setStoption(stoption);
        stuOptionExamanswer1.setAnswerType(StringUtil.strip(list.toString(),"[]").replace(",",";"));
        System.out.println(StringUtil.strip(list.toString(),"[]").replace(",",";"));

        return toAjax(stuOptionExamanswerService.updateAnswerType(stuOptionExamanswer1));
    }
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String[] getCharpter() {
        return charpter;
    }

    public void setCharpter(String[] charpter) {
        this.charpter = charpter;
    }

    public static String getTestPaperName() {
        return testPaperName;
    }

    public static void setTestPaperName(String testPaperName) {
        TestPaperWorkController.testPaperName = testPaperName;
        TestPaperWorkController.testPaperName = testPaperName;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String[] getTypesAmount() {
        return typesAmount;
    }

    public void setTypesAmount(String[] typesAmount) {
        this.typesAmount = typesAmount;
    }

    public String getAltogetherTime() {
        return altogetherTime;
    }

    public void setAltogetherTime(String altogetherTime) {
        this.altogetherTime = altogetherTime;
    }

    public String[] getMark() {
        return mark;
    }

    public void setMark(String[] mark) {
        this.mark = mark;
    }

    public static List<MyQuestions> getqList() {
        return qList;
    }

    public static void setqList(List<MyQuestions> qList) {
        TestPaperWorkController.qList = qList;
    }

    public static List<MyQuestions> getqList_chapter() {
        return qList_chapter;
    }

    public static void setqList_chapter(List<MyQuestions> qList_chapter) {
        TestPaperWorkController.qList_chapter = qList_chapter;
    }

    public static List<MyQuestions> getqList_course() {
        return qList_course;
    }

    public static void setqList_course(List<MyQuestions> qList_course) {
        TestPaperWorkController.qList_course = qList_course;
    }

    public static List<MyQuestions> getLists() {
        return lists;
    }

    public static void setLists(List<MyQuestions> lists) {
        TestPaperWorkController.lists = lists;
    }

    public static String getTitle_name() {
        return title_name;
    }

    public static void setTitle_name(String title_name) {
        TestPaperWorkController.title_name = title_name;
    }

    public String[] getTypescore() {
        return typescore;
    }

    public void setTypescore(String[] typescore) {
        this.typescore = typescore;
    }

    public static List<MyQuestions> getAGAqList() {
        return AGAqList;
    }

    public static void setAGAqList(List<MyQuestions> AGAqList) {
        TestPaperWorkController.AGAqList = AGAqList;
    }

    public static String[] getQuestionsid() {
        return questionsid;
    }

    public static void setQuestionsid(String[] questionsid) {
        TestPaperWorkController.questionsid = questionsid;
    }

    public static ArrayList getNameL() {
        return nameL;
    }

    public static void setNameL(ArrayList nameL) {
        TestPaperWorkController.nameL = nameL;
    }
}
