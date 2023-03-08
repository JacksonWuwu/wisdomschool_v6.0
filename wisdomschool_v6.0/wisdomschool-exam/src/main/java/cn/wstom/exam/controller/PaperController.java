package cn.wstom.exam.controller;


import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Uuid;
import cn.wstom.exam.utils.WordUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

;

@Controller
@RequestMapping("/school/paper")
public class PaperController extends BaseController {
    private String prefix = "/school/paper";


    @Autowired
    private TestPaperOneListService testPaperOneListService;
    @Autowired
    private TestPaperService testPaperService;
    @Autowired
    private MyQuestionsService myQuestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private MyOptionAnswerService myOptionAnswerService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;

    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Autowired
    private TestPaperWorkListService testPaperWorkListService;
    @Autowired
    private TestPaperOneService testPaperOneService;//考试系统组卷
    /**
     * 组卷页面跳转
     */
    @GetMapping("/build")

    public String build(String id, ModelMap mmap) {
        TestPaper testPaper = testPaperService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("tid", getUser().getUserAttrId());
        mmap.put("cid",testPaper.getCoursrId());
        mmap.put("id", id);
        mmap.put("rule",testPaper.getRule());
        return prefix + "/build";
    }
    /**
     * 考试系统组卷页面跳转
     */
    @GetMapping("/buildOne")
    public String buildOne(String id, ModelMap mmap) {
        TestPaperOneList testPaper = testPaperOneListService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("tid", getUser().getUserAttrId());
        mmap.put("cid",testPaper.getCoursrId());
        mmap.put("id", id);
        mmap.put("rule",testPaper.getRule());
        return prefix + "/buildOne";
    }

    /**
     * 考试系统组卷页面跳转
     */
    @GetMapping("/buildTwo")
    public String buildTwo(String id, ModelMap mmap) {
        TestPaperWorkList testPaper = testPaperWorkListService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("tid", getUser().getUserAttrId());
        mmap.put("cid",testPaper.getCoursrId());
        mmap.put("id", id);
        mmap.put("rule",testPaper.getRule());
        return prefix + "/buildTwo";
    }
    /**
     * 添加题目界面
     */
    @GetMapping("/question/add")

    public String add(String paperId, String myQuestionId, ModelMap modelMap) {
        MyQuestions questions = myQuestionsService.getById(myQuestionId);
        modelMap.put("question", questions);
        modelMap.put("paperId", paperId);
        return prefix + "/add";
    }
    /**
     * 添加题目界面
     */
    @GetMapping("/question/addOne")
    public String addOne(String paperId, String myQuestionId, ModelMap modelMap) {
        MyQuestions questions = myQuestionsService.getById(myQuestionId);
        String title = questions.getTitle();
        String htmlText = WordUtil.getTextByHtml(title);
        questions.setTitle(htmlText);
        modelMap.put("question", questions);
        modelMap.put("paperId", paperId);
        return prefix + "/addOne";
    }

    /**
     * 添加题目界面
     */
    @GetMapping("/question/addTwo")
    public String addTwo(String paperId, String myQuestionId, ModelMap modelMap) {
        MyQuestions questions = myQuestionsService.getById(myQuestionId);
        String title = questions.getTitle();
        String htmlText = WordUtil.getTextByHtml(title);
        questions.setTitle(htmlText);
        modelMap.put("question", questions);
        modelMap.put("paperId", paperId);
        return prefix + "/addTwo";
    }
    /**
     * 添加题目
     * @param paperId
     * @param myQuestionId
     * @param questions
     * @return
     * @throws Exception
     */
    @PostMapping("/question/add/{paperId}/{myQuestionId}")
    @ResponseBody
    @Transactional
    public Data add(@PathVariable("paperId") String paperId,
                    @PathVariable("myQuestionId") String myQuestionId,
                    MyQuestions questions) throws Exception {
        String score = String.valueOf(questions.getQuestionscore());
        String attrId = getUser().getUserAttrId();
        /*  查询题目 */
        MyQuestions myq = myQuestionsService.getById(myQuestionId);
        int questionExposed = Integer.parseInt(myq.getQexposed());
        questionExposed += 1;
        MyQuestions myQuestions2 = new MyQuestions();
        myQuestions2.setQexposed(String.valueOf(questionExposed));
        myQuestions2.setId(myQuestionId);
        myQuestions2.setUpdateBy(getUser().getLoginName());
        myQuestionsService.update(myQuestions2);
        /*  保存题目和试卷的关联 */
        TestpaperQuestions tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myq.getId()));

        Uuid uuid = new Uuid();
        if (tpq == null) {
            TestpaperQuestions save = new TestpaperQuestions();
            save.setId(uuid.UId());
            save.setCreateId(getUserId());
            save.setCreateBy(getUser().getLoginName());
            save.setPersonalQuestionId(Integer.valueOf(myq.getId()));
            testpaperQuestionsService.save(save);
            tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myq.getId()));
        }
        tpq.setQuestionScore(Integer.parseInt(score));
        tpq.setTitleTypeId(myq.getTitleTypeId());
        tpq.setDifficulty(myq.getDifficulty());
        tpq.setTitle(myq.getTitle());
        tpq.setQexposed(myQuestions2.getQexposed());
        tpq.setQmaxexposure(myq.getQmaxexposure());
        tpq.setParsing(myq.getParsing());
        tpq.setYear(myq.getYear());

        /*      原来的题目选项   */
//            Map<String, Object> params = new HashMap<>();
//            params.put("question_id", tpq.getId());
        TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
        testpaperOptinanswer.setQuestionId(tpq.getId());
        List answers = testpaperOptinanswerService.list(testpaperOptinanswer);


//            params = new HashMap<>();
//            params.put("test_questions_id", tpq.getId());
//            params.put("test_paper_id", paperId);

        TestpaperTestquestions testquestions = new TestpaperTestquestions();
        testquestions.setTestPaperId(paperId);
        testquestions.setTestQuestionsId(tpq.getId());
        List ttq = testpaperTestquestionsService.list(testquestions);


        /*  复制MyQuestionAnswer 到 testPaperQuestionAnswer */
        String myoastr = myq.getMyoptionAnswerArr();//  题目选项
        if (myoastr != null && !"".equals(myoastr) && answers.isEmpty()) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
            for (String aMyoaArr : myoaArr) {
                MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(aMyoaArr);
                myoalist.add(myOptionAnswer);
            }
            StringBuilder tpoastr = new StringBuilder();
            for (MyOptionAnswer myoa : myoalist) {
                TestpaperOptinanswer toa = new TestpaperOptinanswer();
                Uuid uu = new Uuid();
                toa.setId(uu.UId());
                toa.setCreateId(attrId);
                toa.setCreateBy(getUser().getLoginName());
                toa.setQuestionId(tpq.getId());
                toa.setStanswer(myoa.getStanswer());
                toa.setStoption(myoa.getStoption());
                testpaperOptinanswerService.save(toa);
                tpoastr.append(toa.getId()).append(";");
            }
            tpq.setTestPaperOptionAnswerArr(tpoastr.toString());
        }

        if (ttq == null || ttq.isEmpty()) {
            TestpaperTestquestions testPaperTestQuestions = new TestpaperTestquestions();
            testPaperTestQuestions.setTestQuestionsId(tpq.getId());
            testPaperTestQuestions.setTestPaperId(paperId);
            Uuid tpUuid = new Uuid();
            testPaperTestQuestions.setId(tpUuid.UId());
            testPaperTestQuestions.setCreateId(attrId);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperTestquestionsService.save(testPaperTestQuestions);
        }

        /*  更新testpaperquestion */
        testpaperQuestionsService.update(tpq);

        TestPaper testPaper = testPaperService.getById(paperId);
        String time = new Date().getYear() + 1900 + "";
        testPaper.setTestYear(time);
        testPaper.setState(Constants.EXAM_TYPE_WAIT);
        int so1 = Integer.valueOf(testPaper.getScore());
        int so2 = Integer.valueOf(score);
        testPaper.setScore(String.valueOf(so1 + so2));

        return toAjax(testPaperService.update(testPaper));
    }
    /**
     * 添加题目
     * @param paperId
     * @param myQuestionId
     * @param questions
     * @return
     * @throws Exception
     */
    @PostMapping("/question/addOne/{paperId}/{myQuestionId}")

    @ResponseBody
    @Transactional
    public Data addOne(@PathVariable("paperId") String paperId,
                       @PathVariable("myQuestionId") String myQuestionId,
                       MyQuestions questions) throws Exception {
        String score = String.valueOf(questions.getQuestionscore());
        String attrId = getUser().getUserAttrId();
        /*  查询题目 */
        MyQuestions myq = myQuestionsService.getById(myQuestionId);
        int questionExposed = Integer.parseInt(myq.getQexposed());
        questionExposed += 1;
        MyQuestions myQuestions2 = new MyQuestions();
        myQuestions2.setQexposed(String.valueOf(questionExposed));
        myQuestions2.setId(myQuestionId);
        myQuestions2.setUpdateBy(getUser().getLoginName());
        myQuestionsService.update(myQuestions2);
        /*  保存题目和试卷的关联 */
        TestpaperQuestions tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myq.getId()));
        System.out.println("Integer.valueOf(myq.getId()"+Integer.valueOf(myq.getId()));
        System.out.println("tpq"+tpq);
        Uuid uuid = new Uuid();
        if (tpq == null) {
            TestpaperQuestions save = new TestpaperQuestions();
            save.setId(uuid.UId());
            save.setCreateId(getUserId());
            save.setCreateBy(getUser().getLoginName());
            save.setPersonalQuestionId(Integer.valueOf(myq.getId()));
            testpaperQuestionsService.save(save);
            tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myq.getId()));
        }
        tpq.setQuestionScore(Integer.parseInt(score));
        tpq.setTitleTypeId(myq.getTitleTypeId());
        tpq.setDifficulty(myq.getDifficulty());
        tpq.setTitle(myq.getTitle());
        tpq.setQexposed(myQuestions2.getQexposed());
        tpq.setQmaxexposure(myq.getQmaxexposure());
        tpq.setParsing(myq.getParsing());
        tpq.setYear(myq.getYear());
        /*      原来的题目选项   */
        TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
        testpaperOptinanswer.setQuestionId(tpq.getId());
        List answers = testpaperOptinanswerService.list(testpaperOptinanswer);
        TestpaperOneTestquestions testquestions = new TestpaperOneTestquestions();
        testquestions.setTestPaperOneId(paperId);
        testquestions.setTestQuestionsId(tpq.getId());
        List ttq = testpaperOneTestquestionsService.list(testquestions);
        /*  复制MyQuestionAnswer 到 testPaperQuestionAnswer */
        String myoastr = myq.getMyoptionAnswerArr();//  题目选项
        if (myoastr != null && !"".equals(myoastr) && answers.isEmpty()) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
            for (String aMyoaArr : myoaArr) {
                MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(aMyoaArr);
                myoalist.add(myOptionAnswer);
                System.out.println("myOptionAnswer:"+myOptionAnswer);
            }
            StringBuilder tpoastr = new StringBuilder();
            for (MyOptionAnswer myoa : myoalist) {
                TestpaperOptinanswer toa = new TestpaperOptinanswer();
                Uuid uu = new Uuid();
                toa.setId(uu.UId());
                toa.setCreateId(attrId);
                toa.setCreateBy(getUser().getLoginName());
                toa.setQuestionId(tpq.getId());
                toa.setStanswer(myoa.getStanswer());
                toa.setStoption(myoa.getStoption());
                testpaperOptinanswerService.save(toa);
                tpoastr.append(toa.getId()).append(";");
            }
            tpq.setTestPaperOptionAnswerArr(tpoastr.toString());
        }
        if (ttq == null || ttq.isEmpty()) {
            TestpaperOneTestquestions testPaperTestQuestions = new TestpaperOneTestquestions();
            testPaperTestQuestions.setTestQuestionsId(tpq.getId());
            testPaperTestQuestions.setTestPaperOneId(paperId);
            Uuid tpUuid = new Uuid();
            testPaperTestQuestions.setId(tpUuid.UId());
            testPaperTestQuestions.setCreateId(attrId);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperOneTestquestionsService.save(testPaperTestQuestions);
            System.out.println("testPaperTestQuestions："+testPaperTestQuestions);
        }
        /*  更新testpaperquestion */
        testpaperQuestionsService.update(tpq);

        TestPaperOneList testPaper = testPaperOneListService.getById(paperId);
        String time = new Date().getYear() + 1900 + "";
        testPaper.setTestYear(time);
        testPaper.setState(Constants.EXAM_TYPE_WAIT);
        int so1 = Integer.valueOf(testPaper.getScore());
        int so2 = Integer.valueOf(score);
        testPaper.setScore(String.valueOf(so1 + so2));
        return toAjax(testPaperOneListService.update(testPaper));
    }


    /**
     * 添加题目
     * @param paperId
     * @param myQuestionId
     * @param questions
     * @return
     * @throws Exception
     */
    @PostMapping("/question/addTwo/{paperId}/{myQuestionId}")

    @ResponseBody
    @Transactional
    public Data addTwo(@PathVariable("paperId") String paperId,
                       @PathVariable("myQuestionId") String myQuestionId,
                       MyQuestions questions) throws Exception {
        String score = String.valueOf(questions.getQuestionscore());
        String attrId = getUser().getUserAttrId();
        /*  查询题目 */
        MyQuestions myq = myQuestionsService.getById(myQuestionId);
        int questionExposed = Integer.parseInt(myq.getQexposed());
        questionExposed += 1;
        MyQuestions myQuestions2 = new MyQuestions();
        myQuestions2.setQexposed(String.valueOf(questionExposed));
        myQuestions2.setId(myQuestionId);
        myQuestions2.setUpdateBy(getUser().getLoginName());
        myQuestionsService.update(myQuestions2);
        /*  保存题目和试卷的关联 */
        TestpaperQuestions tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myq.getId()));
        System.out.println("Integer.valueOf(myq.getId()"+Integer.valueOf(myq.getId()));
        System.out.println("tpq"+tpq);
        Uuid uuid = new Uuid();
        if (tpq == null) {
            TestpaperQuestions save = new TestpaperQuestions();
            save.setId(uuid.UId());
            save.setCreateId(getUserId());
            save.setCreateBy(getUser().getLoginName());
            save.setPersonalQuestionId(Integer.valueOf(myq.getId()));
            testpaperQuestionsService.save(save);
            tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myq.getId()));
        }
        tpq.setQuestionScore(Integer.parseInt(score));
        tpq.setTitleTypeId(myq.getTitleTypeId());
        tpq.setDifficulty(myq.getDifficulty());
        tpq.setTitle(myq.getTitle());
        tpq.setQexposed(myQuestions2.getQexposed());
        tpq.setQmaxexposure(myq.getQmaxexposure());
        tpq.setParsing(myq.getParsing());
        tpq.setYear(myq.getYear());

        /*      原来的题目选项   */
//            Map<String, Object> params = new HashMap<>();
//            params.put("question_id", tpq.getId());
        TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
        testpaperOptinanswer.setQuestionId(tpq.getId());
        List answers = testpaperOptinanswerService.list(testpaperOptinanswer);


//            params = new HashMap<>();
//            params.put("test_questions_id", tpq.getId());
//            params.put("test_paper_id", paperId);
        System.out.println("answers:"+answers);
        TestpaperOneTestquestions testquestions = new TestpaperOneTestquestions();
        testquestions.setTestPaperWorkId(paperId);
        testquestions.setTestQuestionsId(tpq.getId());
        List ttq = testpaperOneTestquestionsService.list(testquestions);

        System.out.println("ttq:"+ttq);
        /*  复制MyQuestionAnswer 到 testPaperQuestionAnswer */
        String myoastr = myq.getMyoptionAnswerArr();//  题目选项
        if (myoastr != null && !"".equals(myoastr) && answers.isEmpty()) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
            for (String aMyoaArr : myoaArr) {
                MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(aMyoaArr);
                myoalist.add(myOptionAnswer);
                System.out.println("myOptionAnswer:"+myOptionAnswer);
            }
            StringBuilder tpoastr = new StringBuilder();
            for (MyOptionAnswer myoa : myoalist) {
                TestpaperOptinanswer toa = new TestpaperOptinanswer();
                Uuid uu = new Uuid();
                toa.setId(uu.UId());
                toa.setCreateId(attrId);
                toa.setCreateBy(getUser().getLoginName());
                toa.setQuestionId(tpq.getId());
                toa.setStanswer(myoa.getStanswer());
                toa.setStoption(myoa.getStoption());
                testpaperOptinanswerService.save(toa);
                tpoastr.append(toa.getId()).append(";");
            }
            tpq.setTestPaperOptionAnswerArr(tpoastr.toString());
        }

        if (ttq == null || ttq.isEmpty()) {
            TestpaperOneTestquestions testPaperTestQuestions = new TestpaperOneTestquestions();
            testPaperTestQuestions.setTestQuestionsId(tpq.getId());
            testPaperTestQuestions.setTestPaperWorkId(paperId);
            Uuid tpUuid = new Uuid();
            testPaperTestQuestions.setId(tpUuid.UId());
            testPaperTestQuestions.setCreateId(attrId);
            testPaperTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperOneTestquestionsService.save(testPaperTestQuestions);
            System.out.println("testPaperTestQuestions："+testPaperTestQuestions);
        }

        /*  更新testpaperquestion */
        testpaperQuestionsService.update(tpq);

        List<TestpaperQuestions> tpotqsList = testpaperOneTestquestionsService.getQuestionsByPaperIdWithUserId(paperId, getUserId());
        System.out.println(tpotqsList);
        TestPaperWorkList testPaper = testPaperWorkListService.getById(paperId);
        String time = new Date().getYear() + 1900 + "";
        testPaper.setTestYear(time);
        testPaper.setState(Constants.EXAM_TYPE_WAIT);
        int so1 = Integer.valueOf(testPaper.getScore());
        int so2 = Integer.valueOf(score);
        testPaper.setScore(String.valueOf(so1 + so2));
        return toAjax(testPaperWorkListService.update(testPaper));
    }
}
