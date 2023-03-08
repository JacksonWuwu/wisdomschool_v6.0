package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.entity.Individual.Individual;
import cn.wstom.exam.entity.Individual.QuestionData;
import cn.wstom.exam.entity.Individual.QuestionPaper;
import cn.wstom.exam.entity.Individual.QuestionType;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.feign.StudentService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Convert;
import cn.wstom.exam.utils.Utility;
import cn.wstom.exam.utils.Uuid;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 已发布试卷
 * @author
 * @date 20200526
 */
@Controller
@RequestMapping("/school/onlineExam/testPaperTwo")
public class TestPaperTwoController extends BaseController {
    private String prefix = "school/onlineExam/testPaperTwo";
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
    private TestPaperOneService testPaperOneService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private MyQuestionsService qService;
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
    private UserTestService userTestService;
    @Autowired
    private UserExamService userExamService;
    @Autowired
    private StuOptionanswerService stuOptionanswerService;
    @Autowired
    private StuOptionExamanswerService stuOptionExamanswerService;
    @Autowired
    private StuQuestionScoreService stuQuestionScoreService;
    @Autowired
    private CoursetestStuoptionanswerService coursetestStuoptionanswerService;
    @Autowired
    private MyQuestionsService myQuestionsService;
    @Autowired
    private CoursepaperCoursequestionsService coursepaperCoursequestionsService;


    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/list";
    }

    /**
     * 课程作业
     *
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping("/homework/{cid}")
    public String toHomeworkList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        System.out.println("huangzhih");
        return prefix + "/homeworkList";
    }

    /**
     * 查询试卷列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, ChapterResource chapterResource) {
        String tid = getUser().getUserAttrId();
        TeacherCourse teacherCourseQ = new TeacherCourse();
        teacherCourseQ.setTid(tid);
        teacherCourseQ.setCid(cid);
        TeacherCourse teacherCourse = adminService.getTeacherCourse(teacherCourseQ);
        chapterResource.setCid("1");
        chapterResource.setResourceType(4);
        chapterResource.setTcId(teacherCourse.getId());
        startPage();
        List<ChapterResource> list = adminService.chapterResourceList(chapterResource);
//        if (list.size() != 0) {
//            final String done = "是";
//            final String doneFlag = "0";
//            final String none = "否";
//            final String noneFlag = "1";
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getState().equals(doneFlag)) {
//                    list.get(i).setState(done);
//                } else if (list.get(i).getState().equals(noneFlag)) {
//                    list.get(i).setState(none);
//                }
//            }
//        }
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
    public TableDataInfo listOne(@PathVariable String cid, TestPaperOne testPaper) {
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setType("0");
        testPaper.setCreateId(getUser().getUserAttrId());
        List<TestPaperOne> list = testPaperOneService.list(testPaper);
//        if (list.size() != 0) {
//            final String done = "是";
//            final String doneFlag = "0";
//            final String none = "否";
//            final String noneFlag = "1";
//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getState().equals(doneFlag)) {
//                    list.get(i).setState(done);
//                } else if (list.get(i).getState().equals(noneFlag)) {
//                    list.get(i).setState(none);
//                }
//            }
//        }
        return wrapTable(list);
    }

    /**
     * 查询作业列表
     */

    @PostMapping("/homework/list/{cid}")
    @ResponseBody
    public TableDataInfo homeworkList(@PathVariable String cid, TestPaperOne testPaper) {
        System.out.println("@#####");
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        testPaper.setCreateId(getUser().getUserAttrId());
        List<TestPaperOne> list = testPaperOneService.list(testPaper);
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
     * 新增试卷
     */
    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/add";
    }

    /**
     * 新增作业
     */
    @GetMapping("/homework/add/{cid}")
    public String tohomeworkAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/addhomework";
    }


    /**
     * 新增保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaperOne testPaper) throws Exception {
        System.out.println(testPaper.getCoursrId());
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setType("0");
        testPaper.setCreateId(getUser().getUserAttrId());
        return toAjax(testPaperOneService.save(testPaper));
    }


    /**
     * 新增保存作业
     */

    @Log(title = "试卷", actionType = ActionType.INSERT)
    @PostMapping("/homework/add")
    @ResponseBody
    public Data addhomework(TestPaperOne testPaper) throws Exception {
        System.out.println(testPaper.getCoursrId());
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setType("1");
        testPaper.setCreateId(getUser().getUserAttrId());
        return toAjax(testPaperOneService.save(testPaper));
    }




    /**
     * 修改试卷
     */
    @GetMapping("/edit/{id}")

    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        ChapterResource chapterResource = adminService.getChapterResourceById(id);

        mmap.put("testPaper", chapterResource);
        return prefix + "/edit";
    }

    /**
     * 修改作业
     */
    @GetMapping("/homework/edit/{id}")

    public String tohomeworkEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestPaperOne testPaper = testPaperOneService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edithomework";
    }

    /**
     * 修改保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edithomework(ChapterResource chapterResource) throws Exception {
        chapterResource.setUpdateBy(getUser().getLoginName());
        return toAjax(adminService.updateChapterResource(chapterResource));
    }

    /**
     * 修改保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("homework/edit")
    @ResponseBody
    public Data homeworkedit(TestPaperOne testPaper) throws Exception {
        testPaper.setUpdateId(getUser().getUserAttrId());
        testPaper.setUpdateBy(getUser().getLoginName());
        return toAjax(testPaperOneService.update(testPaper));
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
            return toAjax(testPaperOneService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @Log(title = "试卷", actionType = ActionType.DELETE)
    @PostMapping("/remove/{id}")
    @ResponseBody
    public Data removeId(@PathVariable String id) throws Exception {
        return toAjax(adminService.deleteChapterResource(id));
    }
    /**
     * 删除试卷
     */

    @Log(title = "试卷", actionType = ActionType.DELETE)
    @PostMapping("/homework/remove")
    @ResponseBody
    public Data removehomework(String ids) {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            return toAjax(testPaperOneService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     * 校验标题名唯一
     */
    @PostMapping("/checkTestNameUnique")

    @ResponseBody
    public String checkHeadlineUnique(TestPaperOne testPaper) {
        return testPaperOneService.checkTestNameUnique(testPaper);
    }

    /**
     * 课程设置考试
     */
    @GetMapping("/buileTest")

    public String buileTest(String id, ModelMap mmap) {
        TestPaperOne testPaper = testPaperOneService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/buildTestPage";
    }

    /**
     * AGA 组卷 页面跳转
     */
    @GetMapping("/AGAbuild")

    public String AGAbuild(String id, ModelMap mmap) {
        TestPaperOne testPaper = testPaperOneService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/aga_build";
    }


    /**
     * AGA 组卷  生成试卷
     *
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





    /**
     * AGA 组卷  生成试卷
     *
     * @param tqt
     * @param txid
     * @param txid
     * @param selectType
     * @param coursrId
     * @param selectTypescore

     * @return
     */

    @PostMapping("/stuAGAbuildPaper")
    @ResponseBody
    public List<MyQuestions> stubuilePaper(@RequestParam String tqt,
                                           @RequestParam String txid,
                                           @RequestParam String selectType,
                                           @RequestParam String coursrId,
                                           @RequestParam String selectTypescore , @RequestParam String  chapterId) {
        try {
            System.out.println("chapterL"+chapterId);
            TestPaperOne testPaper = new TestPaperOne();
            testPaper.setType("4");
            testPaper.setCreateId(getUser().getUserAttrId());
            testPaper.setCreateBy(getUser().getLoginName());
            testPaper.setCoursrId(coursrId);
            testPaperOneService.save(testPaper);
            List<TestPaperOne> testPaperList = testPaperOneService.list(new TestPaperOne());
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i <testPaperList.size(); i++) {
                list.add(Integer.parseInt(testPaperList.get(i).getId()));
            }
            Collections.sort(list);

            int l=list.size();
            testid=String.valueOf(list.get(l-1));
            String maxid="";

            AGAqList = null;
            String selecttype = selectType;//每种题型的数量
            String selecttypescore = selectTypescore;//每种题型的分数
            String tid = txid;//题型的id 属于哪种类型题 比如选择题还是填空题
            String titleQuestion = tqt;
            String subjectId = coursrId;//具体课程的id
            String diff="3";
            String dif = "3";//难度等级

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
            System.out.println("找不到匹配的题目"+e.getCause()+":::"+e.getMessage());
            List<MyQuestions> myQuestionsList = new ArrayList<MyQuestions>();
            return myQuestionsList;
        }
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
        TestPaperOne testPaper2 = testPaperOneService.getById(questionid);
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

        System.out.println("总分：" + testpaperScore);
        String time = new Date().getYear() + 1900 + "";
        TestPaperOne testPaper = testPaperOneService.getById(testid);
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
        return toAjax(testPaperOneService.update(testPaper));
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
        TestPaperOne testPaper = testPaperOneService.getById(id);
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
        TestPaperOne testPaper = testPaperOneService.getById(id);
        String tid = getUser().getUserAttrId();
        mmap.put("cid", testPaper.getCoursrId());
        mmap.put("tid", tid);
        mmap.put("testPaper", testPaper);
        return prefix + "/course_build";
    }

    /**
     * 人工组卷
     *
     * @param finalQid  题目集
     * @param score 分值集
     * @param mmap
     * @return
     */
    @PostMapping("/humanBuild")
    @ResponseBody
    public List<MyQuestions> humanBuild(String finalQid, String score, ModelMap mmap) {
        String[] questionsid = finalQid.substring(0, finalQid.length() - 1).split(",");
        String[] questionScor = score.split(";");
        List<MyQuestions> tempArray = new ArrayList<>();
        //  已修改 ————为了方便 这里的qList用的是静态变量 不置空的话在次手动选题目的时候会出现上次的题目—————
        //TODO: qList为全局的，所有用户不置空都为同一题目    修改：返回临时对象
        for (int i = 0; i < questionsid.length; i++) {
            MyQuestions myQuestions = myQuestionsService.getById(questionsid[i]);
            for (int j = 0; j < questionScor.length; j++) {
                String[] titleAndScore = questionScor[j].split(":");
                if (myQuestions.getTitleTypeId().equals(titleAndScore[0])) {
                    myQuestions.setQuestionscore(Integer.parseInt(titleAndScore[1]));
                }
            }
            tempArray.add(myQuestions);
        }
        /*for (int i = 0; i < tempArray.size(); i++) {
            System.out.println(tempArray.get(i).getTitleTypeName());
        }*/
        tempArray.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        /*for (int i = 0; i < tempArray.size(); i++) {
            System.out.println(tempArray.get(i).getTitleTypeName());
        }*/
        return tempArray;
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
        // TODO:  修改：添加到数据库中（保存题目以及分值）
        SysUser sysUser = getUser();
        String uid = sysUser.getUserAttrId();
        String testid = testPaperId;
        String questionid = testQuestionsId;
        TestPaperOne testPaper2 = testPaperOneService.getById(questionid);
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
        TestPaperOne testPaper = testPaperOneService.getById(testid);
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
        return toAjax(testPaperOneService.update(testPaper));
    }


    /**
     * 获取年级
     */
    @PostMapping("/getStudents")

    @ResponseBody
    public List<TestPaperOne> getStudents() {
        return testPaperOneService.getStudentInfo("37");

    }

    /**
     * 获取科目
     */
    @PostMapping("/getTcoName")

    @ResponseBody
    public List<TestPaperOne> getTcoName() {
        return testPaperOneService.getTcoName("37");

    }

    /**
     * 获取学生
     */
    @PostMapping("/getTcoStu")

    @ResponseBody
    public List<TestPaperOne> getTcoStu() {
        return testPaperOneService.getTcoStu("37");

    }


    /**
     * 添加作业试卷
     *
     * @param testQuestionsId
     * @param testPaperOneId
     * @return
     * @throws Exception
     */
    @PostMapping("/addCoursePaper")
    @ResponseBody
    public Data addCoursePaper(@RequestParam String testQuestionsId, @RequestParam String testPaperOneId) throws
            Exception {

        String uid = getUser().getUserAttrId();
        String testid = testPaperOneId;
        String questionid = testQuestionsId;
        TestPaperOne testPaper2 = testPaperOneService.getById(questionid);
        String[] tidarry = null;
        if (questionid != null && !questionid.equals("")) {
            tidarry = questionid.substring(0, questionid.length() - 1).split(",");
        }
        List<TestpaperQuestions> testQuestionsList = new ArrayList<TestpaperQuestions>();
        int testpaperScore = 0;
        for (int i = 0; i < tidarry.length; i++) {
            String[] strarr = tidarry[i].substring(0, tidarry[i].length() - 1).split(";");
            String id = strarr[0];
            MyQuestions myq = this.qService.getById(id);
            int questionExposed = Integer.parseInt(myq.getQexposed());
            questionExposed += 1;
            MyQuestions myQuestions2 = new MyQuestions();
            myQuestions2.setQexposed(String.valueOf(questionExposed));
            myQuestions2.setId(id);
            myQuestions2.setUpdateBy(getUser().getLoginName());
            myQuestions2.setUpdateId(getUser().getUserAttrId());
            qService.update(myQuestions2);
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
        TestPaperOne testPaper = testPaperOneService.getById(testid);

        for (int i = 0; i < testQuestionsList.size(); i++) {
            TestpaperOneTestquestions testPaperOneTestQuestions = new TestpaperOneTestquestions();
            testPaperOneTestQuestions.setTestQuestionsId(testQuestionsList.get(i).getId());
            testPaperOneTestQuestions.setTestPaperOneId(testPaper.getId());
            Uuid uuid = new Uuid();
            testPaperOneTestQuestions.setId(uuid.UId());
            testPaperOneTestQuestions.setCreateId(uid);
            testPaperOneTestQuestions.setCreateBy(getUser().getLoginName());
            testpaperOneTestquestionsService.save(testPaperOneTestQuestions);

        }
        testPaper.setTestYear(time);
        testPaper.setState("0");
        testPaper.setScore(String.valueOf(testpaperScore));
        return toAjax(testPaperOneService.update(testPaper));
    }

    /**
     * 页面跳转 开始考试
     *
     * @return
     */
    @GetMapping("/stuToTest")

    public String stuToTest(String id, ModelMap modelMap) {
        String loginid = getUserId();
        SysUser sysUser = adminService.getUserById(loginid);
        String uid = sysUser.getUserAttrId();
        TestPaperOne testPaper = new TestPaperOne();
        testPaper = testPaperOneService.getById(id);
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
                    String loginid = getUserId();
                    SysUser sysUser = adminService.getUserById(loginid);
                    String uid = sysUser.getUserAttrId();
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
                String loginid = getUserId();
                SysUser sysUser = adminService.getUserById(loginid);
                String uid = sysUser.getUserAttrId();
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
     * 上传答案
     *
     * @param questionNum
     * @param tpquestionId
     * @param anwserId
     * @param anwserStr
     */
    @PostMapping("/saveCourseTestAnswer")
    @ResponseBody
    public String SaveOrUpdateStuAnwser(@RequestParam String questionNum, @RequestParam String tpquestionId,
                                        @RequestParam String anwserId, @RequestParam String anwserStr, @RequestParam String studentId) {
        try {


            System.out.println("答案："+anwserStr);
            TestpaperQuestions tpquestion = new TestpaperQuestions();
            boolean istf = true;
            String loginid = getUserId();
            SysUser sysUser = adminService.getUserById(loginid);
            String uid = sysUser.getUserAttrId();
            Student stuinfo = new Student();
            stuinfo = studentService.getStudentById(String.valueOf(1));
//                if (stuinfo != null && !"".equals(stuinfo)) {
            tpquestion = testpaperQuestionsService.getById(tpquestionId);
            String tpoastr = tpquestion.getTestPaperOptionAnswerArr();
            String[] tpoaArr = tpoastr.substring(0, tpoastr.length() - 1).split(";");
            String[] mytpoaArr = anwserStr.substring(0, anwserStr.length() - 1).split("ks;");
            for (int i = 0; i < tpoaArr.length; i++) {
                TestpaperOptinanswer tpoa = testpaperOptinanswerService.getById(tpoaArr[i]);
                List<CoursetestStuoptionanswer> stuoaList = new ArrayList<CoursetestStuoptionanswer>();
                CoursetestStuoptionanswer stuoa = new CoursetestStuoptionanswer();
                stuoa.setCreateId(Integer.parseInt(studentId));
                stuoa.setTestPaperOptionAnswer(tpoa.getId());
                stuoaList = coursetestStuoptionanswerService.list(stuoa);
                if (stuoaList.size() == 0) {
                    stuoa = new CoursetestStuoptionanswer();
                    stuoa.setTestPaperOptionAnswer(tpoa.getId());
                    stuoa.setCreateId(Integer.parseInt(studentId));
                    stuoa.setCreateBy(getUser().getLoginName());
                    stuoa.setStuanswer(mytpoaArr[i]);
                    stuoa.setCreateId(Integer.parseInt(studentId));
                    Uuid uid2 = new Uuid();
                    stuoa.setId(uid2.UId());
                    System.out.println("&&&&&&&&&&&&&&&&&&&"+stuoa.getStuanswer());
                    coursetestStuoptionanswerService.save(stuoa);
                } else {
                    stuoa.setUpdateId(Integer.parseInt(uid));
                    stuoa.setUpdateBy(getUser().getLoginName());
                    stuoa.setTestPaperOptionAnswer(tpoa.getId());
                    stuoa.setStuanswer(mytpoaArr[i]);
                    stuoa.setCreateId(stuoaList.get(0).getCreateId());
                    stuoa.setTestPaperOptionAnswer(tpoaArr[i]);
                    System.out.println(" stuoa:"+ stuoa.getStuanswer());
                    coursetestStuoptionanswerService.updateByIdAns(stuoa);
                }
                TestpaperOptinanswer tpoa2 = new TestpaperOptinanswer();
                String testPaperQuestionAnsw = stuoa.getTestPaperOptionAnswer();
                tpoa2.setId(testPaperQuestionAnsw);
                tpoa2 = testpaperOptinanswerService.getById(testPaperQuestionAnsw);
                String typenum = tpquestion.gettQuestiontemplateNum();
                String str2 = stuoa.getStuanswer();
                String str1 = "";
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
        ChapterResource chapterResource = adminService.getChapterResourceById(id);
        System.out.println(id);
        modelMap.put("id",chapterResource.getRid());

        return prefix + "/detailList";
    }

    /**
     * 学生试卷详情
     */

    @PostMapping("/detailList/{id}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String id) throws Exception {
        startPage();
        UserExam userExam = new UserExam();

        userExam.setTestPaperOneId(id);
        List list = userExamService.selectList(userExam);
        return wrapTable(list);
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
        ChapterResource chapterResource = adminService.getChapterResourceById(id);
        System.out.println(chapterResource.getRid());
        modelMap.put("id", chapterResource.getRid());
        modelMap.put("cid", cid);
        modelMap.put("nameL", nameL);
        TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
        testpaperTestquestions.setTestPaperOneId(id);
        List<TestpaperOneTestquestions> testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> testpaperQuestionsList = new ArrayList<>();
        Integer flag = 0;
        Integer errorFlag = 0;
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
        TestPaperOne testPaper = testPaperOneService.getById(id);

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
        if (testpaperTestquestionsList1.size() != 0) {
            for (int i = 0; i < testpaperTestquestionsList1.size(); i++) {
                String qid = testpaperTestquestionsList1.get(i).getTestQuestionsId();
                TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(qid);
                if (testpaperQuestions != null) {
                    testpaperQuestionsList.add(testpaperQuestions);
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
        TestpaperTestquestions testpaperTestquestions2 = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList2 = testpaperTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> testpaperQuestionsList1 = new ArrayList<>();
        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            if (testpaperTestquestionsList.get(i).getId() != "" || testpaperTestquestionsList.get(i).getId() != null) {
                TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
                if (testpaperQuestions != null) {
                    testpaperQuestionsList1.add(testpaperQuestions);
                }
            }
        }
        TestPaperOne testPaper = testPaperOneService.getById(paperId);
        userTest1.setTestpaperQuestionsList(testpaperQuestionsList);
        return userTest1;
    }


    /**
     * 查看学生作业
     */
    @GetMapping("/showStuPaper")

    public String showStuPaper(String id, String userId, ModelMap mmap) {
        TestPaperOne testPaper = testPaperOneService.getById(id);

        SysUser user = adminService.getUserById(userId);

        mmap.put("testPaper", testPaper);
        mmap.put("studentId", user.getUserAttrId());
        mmap.put("stuUserId", user.getId());
        mmap.put("stuName", user.getLoginName());
        mmap.put("name", user.getUserName());
        mmap.put("sysUser", user);
        mmap.put("paperId", id);
        return prefix + "/showStuPaper";
    }

    /**
     * 教师改卷
     */
    @ApiOperation("作业修改界面")
    @GetMapping("/makeScore")
    public String makeScore(String id, String stuNum, ModelMap mmap) {
        TestPaperOne testPaper = testPaperOneService.getById(id);
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
     */
    @ApiOperation("显示做题情况")
    @RequestMapping("/startMakePaper")
    @ResponseBody
    public List<TestpaperQuestions> startMakePaper(String paperId, String studentId, ModelMap mmap) throws Exception {
        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(studentId);
        SysUser user = adminService.getUser(sysUser);
        List<TestpaperQuestions> tqvolist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, user.getId());
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
        List<StuOptionExamanswer> optionanswers = new ArrayList<>();
        scoreArray.forEach(o -> {
            StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
            stuOptionExamanswer.setId(o.getStuOptionExamanswerId());
            stuOptionExamanswer.setQuestionScore(o.getScore());
            stuOptionExamanswer.setUpdateId(Integer.valueOf(getUserId()));
            optionanswers.add(stuOptionExamanswer);
        });
        return toAjax(stuOptionExamanswerService.updateListAndTotalScore(optionanswers, paperId, userId));
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
        TestPaperTwoController.testPaperName = testPaperName;
        TestPaperTwoController.testPaperName = testPaperName;
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
        TestPaperTwoController.qList = qList;
    }

    public static List<MyQuestions> getqList_chapter() {
        return qList_chapter;
    }

    public static void setqList_chapter(List<MyQuestions> qList_chapter) {
        TestPaperTwoController.qList_chapter = qList_chapter;
    }

    public static List<MyQuestions> getqList_course() {
        return qList_course;
    }

    public static void setqList_course(List<MyQuestions> qList_course) {
        TestPaperTwoController.qList_course = qList_course;
    }

    public static List<MyQuestions> getLists() {
        return lists;
    }

    public static void setLists(List<MyQuestions> lists) {
        TestPaperTwoController.lists = lists;
    }

    public static String getTitle_name() {
        return title_name;
    }

    public static void setTitle_name(String title_name) {
        TestPaperTwoController.title_name = title_name;
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
        TestPaperTwoController.AGAqList = AGAqList;
    }

    public static String[] getQuestionsid() {
        return questionsid;
    }

    public static void setQuestionsid(String[] questionsid) {
        TestPaperTwoController.questionsid = questionsid;
    }

    public static ArrayList getNameL() {
        return nameL;
    }

    public static void setNameL(ArrayList nameL) {
        TestPaperTwoController.nameL = nameL;
    }
}
