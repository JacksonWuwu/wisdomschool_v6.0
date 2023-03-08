package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.constants.StorageConstants;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.entity.Individual.Individual;
import cn.wstom.exam.entity.Individual.QuestionData;
import cn.wstom.exam.entity.Individual.QuestionPaper;
import cn.wstom.exam.entity.Individual.QuestionType;
import cn.wstom.exam.exception.ApplicationException;
import cn.wstom.exam.exception.FileNameLimitExceededException;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.feign.StudentService;
import cn.wstom.exam.mapper.StuOptionExamanswerMapper;
import cn.wstom.exam.mapper.TestpaperOptinanswerMapper;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.*;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.style.TableStyle;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.ddr.poi.html.HtmlRenderPolicy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * 试卷 信息操作处理   课程作业形式的试卷
 *
 *      -   处理作业、试卷的提交和查询
 * @author
 * @date 20200526
 */
@Controller
@RequestMapping("/school/onlineExam/testPaperOne")
public class TestPaperOneController extends BaseController {
    private String prefix = "school/onlineExam/testPaperOne";
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
    private TestPaperOneListService testPaperOneListService;

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


    @RequestMapping(value = "/testPaperOneList")
    @ResponseBody
    List<TestPaperOne> testPaperOneList(TestPaperOne testPaperOne){
        return testPaperOneService.list(testPaperOne);
    }

    @RequestMapping("/getTestPaperOneById/{testPaperOneId}")
    @ResponseBody
    TestPaperOne getTestPaperOneById(@PathVariable("testPaperOneId") String testPaperOneId){
       return testPaperOneService.getById(testPaperOneId);
    }
    //人脸注册连接
    @GetMapping("/faceRegister")
    public String faceRegister(String id, ModelMap modelMap) {
        modelMap.put("url",ip+"/face/registerIndex?paperId="+id);
        return prefix + "/examLink";
    }

    @RequestMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/paperlist";
    }

    @RequestMapping("/updateListAndTotalScore")
    @ResponseBody
    public int updateListAndTotalScore(List<StuOptionExamanswer> optionanswers, String testPaperOneId, String userId){
        return stuOptionExamanswerService.updateListAndTotalScore(optionanswers,testPaperOneId,userId);
    }
    /**
     * 试卷试题打乱
     */
    @RequestMapping("/randomQuestion")
    @ResponseBody
    public TestPaperOne randomQuestion(String paperId){
        TestPaperOne testPaperOne = new TestPaperOne();
        testPaperOne.setId(paperId);
        return testPaperOneService.selectOne(testPaperOne);
    }

    /**
     * 组卷确定后更新总分
     */
    @RequestMapping("/updateScore")
    @ResponseBody
    public int updateScore(String paperId){
        int sumScore=0;
        TestpaperOneTestquestions testpaperOneTestquestions1 = new TestpaperOneTestquestions();
        testpaperOneTestquestions1.setTestPaperOneId(paperId);
        List<TestpaperOneTestquestions> list = testpaperOneTestquestionsService.list(testpaperOneTestquestions1);
        for (TestpaperOneTestquestions testpaperOneTestquestions : list) {
            TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperOneTestquestions.getTestQuestionsId());
            Integer score = testpaperQuestions.getQuestionScore();
            sumScore=sumScore+score;

        }
        //更新分数
        TestPaperOneList testPaperOneList = new TestPaperOneList();
        testPaperOneList.setId(paperId);
        testPaperOneList.setScore(String.valueOf(sumScore));
        try {
            testPaperOneListService.update(testPaperOneList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sumScore;
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

    @GetMapping("/examFaceStatus/{paperId}")
    @ResponseBody
    public TestPaperOne testPaperOneStatus(@PathVariable("paperId") String paperId) {
        return testPaperOneService.getById(paperId);
    }

    /**
     * 查询试卷列表
     */

    @PostMapping("/paperlist/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaperOne testPaper) {
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("2");
        String sj = "随机";
        String gd = "固定";
        String y = "是";
        String n = "否";
        startPage();
        List<TestPaperOne> list = testPaperOneService.list(testPaper);
        for (TestPaperOne testPaperOne : list) {
            if (testPaperOne.getRule().equals("0")) {
                testPaperOne.setRule(gd);
            } else {
                testPaperOne.setRule(sj);
            }
            if (testPaperOne.getSetExam().equals("0")) {
                testPaperOne.setSetExam(n);
            } else {
                testPaperOne.setSetExam(y);
            }
            if (testPaperOne.getState().equals("0")) {
                testPaperOne.setState(y);
            } else {
                testPaperOne.setState(n);
            }

        }

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
        testPaper.setCoursrId(cid);
        testPaper.setType("0");
        testPaper.setCreateId(getUser().getUserAttrId());
        startPage();
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
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        testPaper.setCreateId(getUser().getUserAttrId());
        startPage();
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
        System.out.println(">>>>>>>>>>>>>>>>>" + testPaper);
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setState("1");
        testPaper.setType("2");
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
        TestPaperOne testPaper = testPaperOneService.getById(id);
        mmap.put("testPaper", testPaper);
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
    public Data edithomework(TestPaperOne testPaper) throws Exception {
        testPaper.setUpdateId(getUser().getUserAttrId());
        testPaper.setUpdateBy(getUser().getLoginName());
        boolean update = testPaperOneService.update(testPaper);
        System.out.println(update);
        return toAjax(update);
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
     * 10.13 lzj复用
     * @param ids
     * @return
     */

    @Log(title = "开始考试")
    @PostMapping("/setExamY")
    @ResponseBody
    public  Data setExamY(String ids) {
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        TestPaperOne testPaperOne = new TestPaperOne();
        testPaperOne.setSetExam("1");
        testPaperOne.setId(idList.get(0));
        try {
            return toAjax(testPaperOneService.updateSetExam(testPaperOne));
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
        TestPaperOne testPaperOne = new TestPaperOne();
        testPaperOne.setSetExam("0");
        testPaperOne.setId(idList.get(0));
        try {
            return toAjax(testPaperOneService.updateSetExam(testPaperOne));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Data.error();
        }
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
    @RequiresPermissions("school:onlineExam:course:view")
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
    @RequiresPermissions("school:onlineExam:course:view")
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
    @RequiresPermissions("school:onlineExam:course:view")
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
     * @param score 分值集buildTestToShow
     * @param mmap
     * @return
     */
    @PostMapping("/humanBuild")
    @RequiresPermissions("school:onlineExam:course:view")
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
    @RequiresPermissions("school:onlineExam:course:view")
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
    @RequiresPermissions("school:onlineExam:course:view")
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
    public List<TestPaperOne> getTcoName(String cId) {
        return testPaperOneService.getTcoName(cId);

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
        String uid = getUser().getUserAttrId();
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
            testpaperTestquestions.setTestPaperOneListId(paperId);
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
        return tqvolist;
    }


    @GetMapping("PoiWord")
    public String PoiWord(String id, String stuNum,String paperId, HttpServletResponse response){
        XWPFDocument document= new XWPFDocument();
        return "success";

    }

    //导出空白试卷

    @RequestMapping("/exportNullExamUrl")
    @ResponseBody
    public Data exportNullExamUrl(String ids, String stuNums,String paperId, HttpServletResponse response) {
        try {
            // 这里写你在数据库中查出的数据
            //通过ids查询用户考试信息
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            TestPaperOne testPaperOne = testPaperOneService.getById(paperId); //获得试卷信息
            List<UserExam> userExamList = userExamService.listByIds(idList);  //获得该场用户考试信息
            SysUser dbUser = new SysUser();
            dbUser.setUserAttrId(testPaperOne.getCreateId());
            SysUser sysUser = adminService.getUser(dbUser); //获通过试卷创建者id或取该用户信息
            Course course = adminService.getCourseById(testPaperOne.getCoursrId()); //获取该试卷的课程名
            for (UserExam userExam : userExamList) {
                exportExam("空白试卷", userExam, testPaperOne, sysUser, course);
            }
        }catch(Exception e){
            e.printStackTrace();
            return toAjax(false);
        }
        //return show;
        return toAjax(true);
    }

    //导出已经作答的试卷

    @RequestMapping("/exportExamUrlTest")
    @ResponseBody
    public Data exportExamAnswered(String ids, String stuNums,String paperId, HttpServletResponse response) throws IOException {

        try {
            // 这里写你在数据库中查出的数据
            //通过ids查询用户考试信息
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            TestPaperOne testPaperOne = testPaperOneService.getById(paperId); //获得试卷信息
            List<UserExam> userExamList = userExamService.listByIds(idList);  //获得该场用户考试信息
            SysUser dbUser=new SysUser();
            dbUser.setUserAttrId(testPaperOne.getCreateId());
            SysUser sysUser = adminService.getUser(dbUser); //获通过试卷创建者id或取该用户信息
            Course course = adminService.getCourseById(testPaperOne.getCoursrId()); //获取该试卷的课程名

            for (int k=0;k<userExamList.size();k++) {
                String madeScore = userExamList.get(k).getMadeScore();
                if(madeScore.equals("0")){ //判断是否已经改卷
                    continue;
                }
                //获取该学生信息
                SysUser sysUserStu = adminService.getUserById(userExamList.get(k).getUserId());
                Student student = studentService.getStudentById(sysUserStu.getUserAttrId());
                //该学生班级
                Clbum clbum = adminService.getClbumById(student.getCid());
                File file = new File("C:\\已作答试卷\\" + sysUser.getUserName() + "\\" + clbum.getName() + "_" + course.getName() + "模板");//创建导出目录
                if (!file.exists()) {
                    file.mkdirs();
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                String filePath = file + "\\" + sysUserStu.getLoginName() + "" + sysUserStu.getUserName() + "" + testPaperOne.getTestName() + ".docx";//导出后文件存放的路径
                String templatePath = "C:\\wordTemplate\\demo.docx";//模板存放地址
                Map<String, Object> map = new HashMap<>(); //模板信息
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


                //试卷基本信息
                String subject = course.getName();  //科目
                String clumb = clbum.getName();  //班级
                String name = sysUserStu.getUserName();  //姓名
                String sno = sysUserStu.getLoginName();  //学号

                //得分表
                int choiceSum=0; //选择题总分
                int simpleQuestionsSum=0; //简单题总分
                int completionSum=0; //填空题总分
                int programSum=0; //编程题总分

                //学生选择题答案,及得分
                String answer="";
                Integer score=0;

                //选择题
                String choice="";

                //填空题
                String completion="";
                Integer completionScore=0;

                //简答题
                String simpleQuestions="";
                Integer simpleQuestionsScore=0;

                //编程题
                String program="";
                Integer programScore=0;

                //选择题题号
                int  choiceNum=1;
                //填空题题号
                int  completionNum=1;
                //简答题题号
                int simpleQuestionsNum=1;
                //编程题题号
                int programNum=1;

                int sum=0;
                //学生试卷信息

                int stuAnswerNum=0;
                List<TestpaperQuestions> tpqlist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, sysUserStu.getId());
                ConfigureBuilder builder= Configure.builder();
                for (int i = 0; i < tpqlist.size(); i++) {
                    String stuAnswer="";//学生回答
                    int num=i+1;

                        if(tpqlist.get(i).getStuOptionExamanswerList().size()>0) {
                            stuAnswerNum++;
                        }
                    //选择题
                    if(tpqlist.get(i).gettQuestiontemplateNum().equals("1")){
                        if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){//防止数组越界异常
                            String s = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//获取学生答案
                            answer=s.replaceAll("[^a-zA-z]", "").trim();//提取字母
                            score = tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取学生答案
                            choiceSum=choiceSum+score; //选择题总分
                            map.put("answer"+choiceNum,answer); //参数放入map
                            map.put("score"+choiceNum,score);

                        }
                        //生成题目内容
                        String option="";//选择题选项
                        int ascii=65;// A选项(ascii)
                        for (int j = 0; j < tpqlist.get(i).getTestpaperOptinanswerList().size(); j++) {
                            String stoption="";
                            if(tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption()!=null){
                                stoption=tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption().trim();
                                option=option+ WordUtil.asciiToString(ascii)+"、"+stoption+"\n";
                                ascii++;
                            }
                        }
                        choice=choice+
                                choiceNum+"、(得分:"+score+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n" +
                                option+"\n";
                        choiceNum++;
                    }

                    //多选题
                    if(tpqlist.get(i).gettQuestiontemplateNum().equals("2")){
                        if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){//防止数组越界异常
                            String s = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//获取学生答案
                            answer=s.replaceAll("[^a-zA-z]", "").trim();//提取字母
                            score = tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取学生答案
                            choiceSum=choiceSum+score; //选择题总分
                            map.put("answer"+choiceNum,answer); //参数放入map
                            map.put("score"+choiceNum,score);
                        }
                        //生成题目内容
                        String option="";//选择题选项
                        int ascii=65;// A选项(ascii)
                        for (int j = 0; j < tpqlist.get(i).getTestpaperOptinanswerList().size(); j++) {
                            String stoption="";
                            if(tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption()!=null){
                                stoption=tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption().trim();
                                option=option+WordUtil.asciiToString(ascii)+"、"+stoption+"\n";
                                ascii++;
                            }
                        }
                        choice=choice+
                                num+"、(得分:"+score+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n" +
                                option+"\n";
                        System.out.println("多选题");
                        choiceNum++;
                    }


                    //填空
                    if(tpqlist.get(i).gettQuestiontemplateNum().equals("3")){
                        String s=" ";
                        String option="";
                        if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){//防止数组越界异常
                            s = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//获取学生答案
                            completionScore = tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取学生答案
                            completionSum=completionSum+completionScore; //总分
                        }
                        for (int j = 0; j < tpqlist.get(i).getTestpaperOptinanswerList().size(); j++) {
                            String stoption="";
                            if(tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption()!=null){
                                stoption=tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption().trim();
                                option=option+";"+stoption;
                            }
                        }
                        completion=completion+
                                completionNum+"、(得分:"+completionScore+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n"
                                + "学生答案:"+s
                                +"\n"
                                + "答案解析:"+option +"\n";
                        System.out.println("填空");
                        completionNum++;
                    }

                    //简答
                    if(tpqlist.get(i).gettQuestiontemplateNum().equals("5")&&tpqlist.get(i).getTitleTypeNum().equals("07")){
                        stuAnswer="";
                        if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){
                            simpleQuestionsScore=tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取简单题得分
                            stuAnswer=tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();
                        }

                        simpleQuestionsSum=simpleQuestionsSum+simpleQuestionsScore;
                        //题目内容
                        simpleQuestions=simpleQuestions+
                                simpleQuestionsNum+"、(得分:"+simpleQuestionsScore+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n" +
                                stuAnswer+"\n"+
                                "答案解析:"+tpqlist.get(i).getParsing()+"\n";
                        System.out.println("简答");
                        simpleQuestionsNum++;
                    }


                    //编程
                    if(tpqlist.get(i).gettQuestiontemplateNum().equals("5")&&tpqlist.get(i).getTitleTypeNum().equals("10")){
                        String images=""; //学生截图
                        //动态标签动态添加插件
                        builder.bind("richText"+i, new HtmlRenderPolicy());
                        builder.bind("richTextTitle"+i, new HtmlRenderPolicy());
                        if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){
                            programScore=tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取编程题得分
                            if(tpqlist.get(i).getStuOptionExamanswerList().get(0).getAnswerType()!=null){
                                String richText = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//学生的答案
                                String htmlText = WordUtil.getHtmlText(richText); //获取去除图片的富文本
                                List<String> imageSrcs = WordUtil.getImageSrc(richText);//获取富文本里面的图片
                                map.put("richText"+i,htmlText);
                                for (int j = 0; j < imageSrcs.size(); j++) {
                                    String path=imageSrcs.get(j);
                                    map.put("image"+j+"s"+i, Pictures.ofUrl(path, PictureType.PNG).size(600, 400).create());
                                    images=images+"\n"+"{{@image"+j+"s"+i+"}}";
                                }
                            }
                        }
                        //题目内容
                        String title="";
                        title=tpqlist.get(i).getTitle();
                        System.out.println(title);
                        map.put("richTextTitle"+i,title);
                        map.put("table"+i, Tables.of(new String[][] { //编程题表格
                                new String[] {  "题目:\n"+programNum+"、(得分:"+ programScore+"分)  "+"{{richTextTitle"+i+"}}"+"("+tpqlist.get(i).getQuestionScore()+"分)" },
                                new String[] { "代码:\n{{richText"+i+"}}" },
                                new String[] { "运行结果截图：\n"+images }
                        }).border(TableStyle.BorderStyle.DEFAULT).create());
                        programSum=programSum+programScore;//编程题总分
                        program=program+
                                "{{#table"+i+"}}"+"\n";
                        programNum++;
                    }
                }

                if(stuAnswerNum==0){
                    continue;
                }

                sum=choiceSum+completionSum+simpleQuestionsSum+programSum;
                //放入参数
                map.put("subject", subject);
                map.put("clumb", clumb);
                map.put("name", name);
                map.put("sno", sno);
                map.put("choiceSum", choiceSum);
                map.put("choice", choice);
                map.put("completion", completion);
                map.put("completionSum", completionSum);
                map.put("simpleQuestions", simpleQuestions);
                map.put("simpleQuestionsSum", simpleQuestionsSum);
                map.put("program", program);
                map.put("programSum", programSum);
                map.put("sum", sum);
                //执行
                WordUtil.exportWord(templatePath, filePath, map,builder.build());
                // show=tpqlist;
            }
        }catch(Exception e){
            e.printStackTrace();
            return toAjax(false);
        }
        //return show;
        return toAjax(true);



    }

    @RequestMapping("/exportExamAll")
    @ResponseBody
    public Data exportExamAll(String ids, String stuNums,String paperId, HttpServletResponse response) throws IOException {
    try {
        // 这里写你在数据库中查出的数据
        //通过ids查询用户考试信息
        List<String> idList = Arrays.asList(Convert.toStrArray(ids));
        TestPaperOne testPaperOne = testPaperOneService.getById(paperId); //获得试卷信息
        List<UserExam> userExamList = userExamService.listByIds(idList);  //获得该场用户考试信息
        SysUser dbUser = new SysUser();
        dbUser.setUserAttrId(testPaperOne.getCreateId());
        SysUser sysUser = adminService.getUser(dbUser); //获通过试卷创建者id或取该用户信息
        Course course = adminService.getCourseById(testPaperOne.getCoursrId()); //获取该试卷的课程名

        for (UserExam userExam : userExamList) {
            String madeScore = userExam.getMadeScore();
            if (madeScore.equals("0")) { //判断是否已经改卷
                continue;
            }
            exportExam("已经批阅试卷", userExam, testPaperOne, sysUser, course);
        }
       }catch(Exception e){
            e.printStackTrace();
          return toAjax(false);
        }
    return toAjax(true);
}

   //学生提交的时候导出试卷
    @RequestMapping("/exportExam")
    @ResponseBody
    public Data exportExam(String id,String paperId) throws IOException {
        try {

            // 这里写你在数据库中查出的数据
            TestPaperOne testPaperOne = testPaperOneService.getById(paperId); //获得试卷信息
            UserExam userExam = userExamService.selectUserExam(paperId, id);//获得该场用户考试信息
            SysUser dbUser = new SysUser();
            dbUser.setUserAttrId(testPaperOne.getCreateId());
            SysUser sysUser = adminService.getUser(dbUser);//获通过试卷创建者id或取该用户信息
            Course course = adminService.getCourseById(testPaperOne.getCoursrId()); //获取该试卷的课程名
            exportExam("已经提交试卷",userExam,testPaperOne,sysUser,course);

        }catch(Exception e){
            e.printStackTrace();
            return toAjax(false);
        }
        //return show;
        return toAjax(true);
    }
    public  void  exportExam(String type,UserExam userExam,TestPaperOne testPaperOne,SysUser sysUser, Course course){
        //获取该学生信息
        SysUser sysUserStu = adminService.getUserById(userExam.getUserId());
        Student student = studentService.getStudentById(sysUserStu.getUserAttrId());
        //该学生班级
        Clbum clbum = adminService.getClbumById(student.getCid());
        File file = new File("C:\\"+type+"\\" + sysUser.getUserName() + "\\" + clbum.getName() + "_" + course.getName() + "模板");//创建导出目录
        if (!file.exists()) {
            file.mkdirs();
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        String filePath = file + "\\" + sysUserStu.getLoginName() + "" + sysUserStu.getUserName() + "" + testPaperOne.getTestName() + ".docx";//导出后文件存放的路径
        String templatePath = "C:\\wordTemplate\\demo.docx";//模板存放地址
        Map<String, Object> map = new HashMap<>(); //模板信息
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //试卷基本信息
        String subject = course.getName();  //科目
        String clumb = clbum.getName();  //班级
        String name = sysUserStu.getUserName();  //姓名
        String sno = sysUserStu.getLoginName();  //学号

        //得分表
        int choiceSum=0; //选择题总分
        int simpleQuestionsSum=0; //简单题总分
        int completionSum=0; //填空题总分
        int programSum=0; //编程题总分

        //学生选择题答案,及得分
        String answer="";
        Integer score=0;

        //选择题
        String choice="";

        //填空题
        String completion="";
        Integer completionScore=0;

        //简答题
        String simpleQuestions="";
        Integer simpleQuestionsScore=0;

        //编程题
        String program="";
        Integer programScore=0;

        //选择题题号
        int  choiceNum=1;
        //填空题题号
        int  completionNum=1;
        //简答题题号
        int simpleQuestionsNum=1;
        //编程题题号
        int programNum=1;

        int sum=0;
        //学生试卷信息
        List<TestpaperQuestions> tpqlist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(testPaperOne.getId(), sysUserStu.getId());
        ConfigureBuilder builder = Configure.builder();//绑定标签
        for (int i = 0; i < tpqlist.size(); i++) {
            String stuAnswer="";//学生回答
            int num=i+1;

            //选择题
            if(tpqlist.get(i).gettQuestiontemplateNum().equals("1")){
                if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){//防止数组越界异常
                    String s = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//获取学生答案
                    answer=s.replaceAll("[^a-zA-z]", "").trim();//提取字母
                    score = tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取学生答案
                    choiceSum=choiceSum+score; //选择题总分
                    map.put("answer"+choiceNum,answer); //参数放入map
                    map.put("score"+choiceNum,score);
                }
                //生成题目内容
                String option="";//选择题选项
                int ascii=65;// A选项(ascii)
                for (int j = 0; j < tpqlist.get(i).getTestpaperOptinanswerList().size(); j++) {
                    String stoption="";
                    if(tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption()!=null){
                        stoption=tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption().trim();
                        option=option+WordUtil.asciiToString(ascii)+"、"+stoption+"\n";
                        ascii++;
                    }
                }
                choice=choice+
                        choiceNum+"、(得分:"+score+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n" +
                        option+"\n";
                choiceNum++;
            }

            //多选题
            if(tpqlist.get(i).gettQuestiontemplateNum().equals("2")){
                if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){//防止数组越界异常
                    String s = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//获取学生答案
                    answer=s.replaceAll("[^a-zA-z]", "").trim();//提取字母
                    score = tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取学生答案
                    choiceSum=choiceSum+score; //选择题总分
                    map.put("answer"+choiceNum,answer); //参数放入map
                    map.put("score"+choiceNum,score);
                }
                //生成题目内容
                String option="";//选择题选项
                int ascii=65;// A选项(ascii)
                for (int j = 0; j < tpqlist.get(i).getTestpaperOptinanswerList().size(); j++) {
                    String stoption="";
                    if(tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption()!=null){
                        stoption=tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption().trim();
                        option=option+WordUtil.asciiToString(ascii)+"、"+stoption+"\n";
                        ascii++;
                    }
                }
                choice=choice+
                        num+"、(得分:"+score+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n" +
                        option+"\n";
                System.out.println("多选题");
                choiceNum++;
            }


            //填空
            if(tpqlist.get(i).gettQuestiontemplateNum().equals("3")){
                String s=" ";
                String option="";
                if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){//防止数组越界异常
                    s = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//获取学生答案
                    completionScore = tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取学生答案
                    completionSum=completionSum+completionScore; //总分
                }
                for (int j = 0; j < tpqlist.get(i).getTestpaperOptinanswerList().size(); j++) {
                    String stoption="";
                    if(tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption()!=null){
                        stoption=tpqlist.get(i).getTestpaperOptinanswerList().get(j).getStoption().trim();
                        option=option+";"+stoption;
                    }
                }
                completion=completion+
                        completionNum+"、(得分:"+completionScore+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n"
                        + "学生答案:"+s
                        +"\n"
                        + "答案解析:"+option +"\n";
                System.out.println("填空");
                completionNum++;
            }

            //简答
            if(tpqlist.get(i).gettQuestiontemplateNum().equals("5")&&tpqlist.get(i).getTitleTypeNum().equals("07")){
                stuAnswer="";
                if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){
                    simpleQuestionsScore=tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取简单题得分
                    stuAnswer=tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();
                }

                simpleQuestionsSum=simpleQuestionsSum+simpleQuestionsScore;
                //题目内容
                simpleQuestions=simpleQuestions+
                        simpleQuestionsNum+"、(得分:"+simpleQuestionsScore+"分)  "+tpqlist.get(i).getTitle()+"("+tpqlist.get(i).getQuestionScore()+"分)"+"\n" +
                        stuAnswer+"\n"+
                        "答案解析:"+tpqlist.get(i).getParsing()+"\n";
                System.out.println("简答");
                simpleQuestionsNum++;
            }


            //编程
            if(tpqlist.get(i).gettQuestiontemplateNum().equals("5")&&tpqlist.get(i).getTitleTypeNum().equals("10")){
                String images=""; //学生截图
                //动态标签动态添加插件
                builder.bind("richText"+i, new HtmlRenderPolicy());
                builder.bind("richTextTitle"+i, new HtmlRenderPolicy());
                if(tpqlist.get(i).getStuOptionExamanswerList().size()>0){
                    programScore=tpqlist.get(i).getStuOptionExamanswerList().get(0).getQuestionScore();//获取编程题得分
                    if(tpqlist.get(i).getStuOptionExamanswerList().get(0).getAnswerType()!=null){
                        String richText = tpqlist.get(i).getStuOptionExamanswerList().get(0).getStuAnswer();//学生的答案
                        String htmlText = WordUtil.getHtmlText(richText); //获取去除图片的富文本
                        List<String> imageSrcs = WordUtil.getImageSrc(richText);//获取富文本里面的图片
                        map.put("richText"+i,htmlText);
                        for (int j = 0; j < imageSrcs.size(); j++) {
                            String path=imageSrcs.get(j);
                            map.put("image"+j+"s"+i, Pictures.ofUrl(path, PictureType.PNG).size(600, 400).create());
                            images=images+"\n"+"{{@image"+j+"s"+i+"}}";
                        }
                    }
                }
                //题目内容
                String title="";
                title=tpqlist.get(i).getTitle();
                System.out.println(title);
                map.put("richTextTitle"+i,title);
                map.put("table"+i, Tables.of(new String[][] { //编程题表格
                        new String[] {  "题目:\n"+programNum+"、(得分:"+ programScore+"分)  "+"{{richTextTitle"+i+"}}"+"("+tpqlist.get(i).getQuestionScore()+"分)" },
                        new String[] { "代码:\n{{richText"+i+"}}" },
                        new String[] { "运行结果截图：\n"+images }
                }).border(TableStyle.BorderStyle.DEFAULT).create());
                programSum=programSum+programScore;//编程题总分
                program=program+
                        "{{#table"+i+"}}"+"\n";
                programNum++;
            }
        }
        sum=choiceSum+completionSum+simpleQuestionsSum+programSum;
        //放入参数
        map.put("subject", subject);
        map.put("clumb", clumb);
        map.put("name", name);
        map.put("sno", sno);
        map.put("choiceSum", choiceSum);
        map.put("choice", choice);
        map.put("completion", completion);
        map.put("completionSum", completionSum);
        map.put("simpleQuestions", simpleQuestions);
        map.put("simpleQuestionsSum", simpleQuestionsSum);
        map.put("program", program);
        map.put("programSum", programSum);
        map.put("sum", sum);
        //执行
        WordUtil.exportWord(templatePath, filePath, map,builder.build());
    }





    @Log(title = "学生成绩信息", actionType = ActionType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public Data export(UserExam userExam) {
        try{
            List<UserExam> users = userExamService.list(userExam);
            ExcelUtil<UserExam> util = new ExcelUtil<>(UserExam.class);
            return util.exportExcel(users, "studentExam");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
    ///**
    // * 获取专业信息
    // *
    // * @param teacherVos
    // */
    //private void fillMajor(List<UserExamVo> teacherVos) {
    //    Map<String, Major> majorMap = majorService.map(null);
    //    teacherVos.forEach(t -> t.setMajor(majorMap.get(t.getDepartment().getName())));
    //}
    //
    ///**
    // * 获取系部信息
    // *
    // * @param teacherVos
    // */
    //private void fillDepartment(List<UserExamVo> teacherVos) {
    //    Map<String, Department> departmentMap = departmentService.map(null);
    //    teacherVos.forEach(t -> t.setDepartment(departmentMap.get(t.getMajor().getDid())));
    //}

    private List<UserExamVo> trans(List<UserExam> users, Map<String, UserExam> userExamMap) {
        List<UserExamVo> teacherVos = new LinkedList<>();
        users.forEach(u -> teacherVos.add(trans(u)));
        return teacherVos;
    }
    private UserExamVo trans(UserExam userExam) {
        UserExamVo userExamVo = new UserExamVo();
        BeanUtils.copyProperties(userExam, userExamVo);
        userExamVo.setUserExam(userExam);
        return userExamVo;
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
            String uid = getUser().getUserAttrId();
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
        TestPaperOne testPaperOne = testPaperOneService.getById(id);
        modelMap.put("cid", testPaperOne.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("id", id);
        modelMap.put("PaperRule", testPaperOne.getRule());
        return prefix + "/detailList";
    }
    /**
     * 获取监考链接
     */
    @Value("${wstom.contextPath}")
    private String ip;
    @GetMapping("/examControl")
    public String examControl(String id, ModelMap modelMap) {
        TestPaperOne testPaperOne = testPaperOneService.getById(id);
        modelMap.put("cid", testPaperOne.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("id", id);
        modelMap.put("PaperRule", testPaperOne.getRule());
        modelMap.put("url",ip+"/examControl/login?cid="+testPaperOne.getCoursrId()+"&tid="+getUser().getUserAttrId()+"&paperId="+id);
        return prefix + "/examControl";
    }

    @GetMapping("/examLink")
    public String examLink(String id, ModelMap modelMap) {
        TestPaperOne testPaperOne = testPaperOneService.getById(id);
        TeacherCourse teacherCourseQ = new TeacherCourse();
        teacherCourseQ.setCid(testPaperOne.getCoursrId());
        teacherCourseQ.setTid(getUser().getUserAttrId());
        TeacherCourse teacherCourse = adminService.getTeacherCourse(teacherCourseQ);
        modelMap.put("cid", testPaperOne.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("id", id);
        modelMap.put("PaperRule", testPaperOne.getRule());
        modelMap.put("url",ip+"/examLogin?tcid="+teacherCourse.getId()+"&&cid="+testPaperOne.getCoursrId()+"&&testPaperOneId="+id);
//        modelMap.put("url",ip+"/student/login?cid="+testPaperOne.getCoursrId()+"&tid="+getUser().getUserAttrId()+"&paperId="+id);
        return prefix + "/examLink";
    }
    /**
     * 学生试卷详情
     */

    @PostMapping("/detailList/{id}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String id) throws Exception {
        UserExam userExam = new UserExam();
        userExam.setTestPaperOneId(id);
        List<Map<String, Object>> list = userExamService.selectList(userExam);
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
        modelMap.put("id", id);
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
    public TableDataInfo onlineList(@PathVariable String cid, UserExam userExam2, SysUserOnline sysUserOnline, TestPaperOne testPaper) {
        UserExam userExam = new UserExam();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(getUser().getUserAttrId());
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperOneId(userExam2.getTestPaperOneId());
        startPage();
        List<UserExam> list = userExamService.findStuExamPaperOne(userExam);
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
    public TableDataInfo infoList(@PathVariable String cid, UserExam userExam2,SysUserOnline sysUserOnline, TestPaperOne testPaper) {
        UserExam userExam = new UserExam();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(getUser().getUserAttrId());
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperOneId(userExam2.getTestPaperOneId());
        startPage();
        List<UserExam> list = userExamService.findStuExamPaperTwo(userExam);

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
    public TableDataInfo paperList(@PathVariable String cid, UserExam userExam2, TestPaperOne testPaper) {
        UserExam userExam = new UserExam();

        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(getUser().getUserAttrId());
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperOneId(userExam2.getTestPaperOneId());
        startPage();
        List<UserExam> list = userExamService.findStuExamPaper(userExam);
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
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
     * 删除试卷
     */

    @Log(title = "试卷", actionType = ActionType.DELETE)
    @PostMapping("/studentPaper/remove")
    @ResponseBody
    public Data removeStuPaper(String id, String stuNum) {
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        UserExam userTest = new UserExam();
        userTest.setTestPaperOneId(id);
        userTest.setUserId(user.getId());
        List<UserExam> userTestList = userExamService.list(userTest);
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
            String[] both = new String[]{};
            String[] tid = new String[]{};
            String[] stuNum = new String[]{};
            both = ids.split("&");
            if (both.length >= 1) {
                System.out.println("开始删除1");
                tid = both[0].split(",");
                stuNum = both[1].split(",");
                for (int i = 0; i <tid.length ; i++) {
                    System.out.println("开始删除2");
                    TestPaperOne testPaper = testPaperOneService.getById(tid[i]);
                    SysUser sysUser = new SysUser();
                    sysUser.setLoginName(stuNum[i]);
                    List<SysUser> sysUserList = adminService.getUserList(sysUser);
                    UserExam userTest = new UserExam();
                    userTest.setTestPaperOneId(tid[i]);
                    userTest.setUserId(sysUserList.get(0).getUserAttrId());
                    List<UserExam> userTestList = userExamService.list(userTest);
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
        UserExam userExam = userExamService.selectByExamId(id);
        TestPaperOne testPaper = testPaperOneService.getById(userExam.getTestPaperOneId());
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        mmap.put("testPaper", testPaper);
        mmap.put("studentId", user.getId());
        mmap.put("stuUserId", sysUser.getId());
        mmap.put("stuName", sysUser.getLoginName());
        mmap.put("name", sysUser.getUserName());
        mmap.put("sysUser", user);
        mmap.put("paperId", userExam.getTestPaperOneId());
        return prefix + "/showStuPaper";

    }
    /**
     * 修改学生试卷提交状态
     */

    @Log(title = "学生试卷", actionType = ActionType.UPDATE)
    @PostMapping("/updateStateEdit")
    @ResponseBody
    public Data updateStateEdit(String id, String userId, String testPaperOneId) throws Exception {
        UserExam userExam = new UserExam();
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(userId);
        SysUser user = adminService.getUser(sysUser);
        userExam.setId(id);
        userExam.setUserId(user.getId());
        userExam.setUpdateBy(getUser().getLoginName());
        userExam.setTestPaperOneId(testPaperOneId);
        userExam.setSumScore("0");
        return toAjax(userExamService.update(userExam));
    }

    /**
     * 修改学生考试
     */
    @GetMapping("/checkStuPaper")
    public String checkStuPaper(String id, String stuNum, ModelMap mmap) {
        UserExam userExam = userExamService.selectByExamId(id);
        TestPaperOne testPaper = testPaperOneService.getById(userExam.getTestPaperOneId());
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        mmap.put("testPaper", testPaper);
        mmap.put("studentId", user.getId());
        mmap.put("stuUserId", sysUser.getId());
        mmap.put("stuName", sysUser.getLoginName());
        mmap.put("name", sysUser.getUserName());
        mmap.put("sysUser", user);
        mmap.put("paperId", userExam.getTestPaperOneId());
        return prefix + "/checkStuPaper";
    }
    /**
     * 教师改卷
     */
    @ApiOperation("作业修改界面")
    @GetMapping("/makeScore")
    public String makeScore(String id, String stuNum, ModelMap mmap) {
        UserExam userExam = userExamService.getById(id);
        TestPaperOne testPaper = testPaperOneService.getById(userExam.getTestPaperOneId());
        System.out.println("TestPaperOneId::"+userExam.getTestPaperOneId());
        SysUser sysUser = new SysUser();
        sysUser.setLoginName(stuNum);
        SysUser user = adminService.getUser(sysUser);
        mmap.put("testPaper", testPaper);
        mmap.put("studentId", user.getId());
        mmap.put("sysUser", user);
        mmap.put("paperId", userExam.getTestPaperOneId());
        return prefix + "/makeScore";
    }

    /**
     * 改卷时初始化页面
     */
    @ApiOperation("显示做题情况")
    @RequestMapping("/startMakePaper")
    @ResponseBody
    public List<TestpaperQuestions> startMakePaper(String paperId, String studentId, ModelMap mmap) throws Exception {
        System.out.println("studentId11111："+studentId);
        System.out.println("paperId："+paperId);
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        String studentID = studentId;
        TestPaperOne testPaper =  testPaperOneService.getById(paperId);
//            tqvolist = testpaperOneTestquestionsService.getQuestionsAndOptionsByPaperIdWithUserId(paperId, studentId);
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
        for (TestpaperQuestions aTqvolist : tqvolist) {
            //  填充选项对象
            if (aTqvolist.getTestPaperOptionAnswerArr()!=null){
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
        System.out.println(tqvolist);

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
            stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
            optionanswers.add(stuOptionExamanswer);
        });
        System.out.println("userId:"+userId);
        System.out.println("optionanswers:"+optionanswers);
        return toAjax(stuOptionExamanswerService.updateListAndTotalScore(optionanswers, paperId, userId));
    }

    @PostMapping(value = "/uploadImg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Data uploadImg(Integer paperId,Integer studentId,String tutId,String stoption,StuOptionExamanswer stuOptionExamanswer, MultipartFile[] file) throws Exception {
        String filename = null;
        StuOptionExamanswer stuOptionExamanswer1 = stuOptionExamanswerService.selectByCreatId(paperId.toString(),studentId.toString(),stoption);
        System.out.println(stuOptionExamanswer1);
        for (int i = 0; i < file.length; i++) {
            System.out.println(file[i].getOriginalFilename());
            if (!file[i].isEmpty()) {
                try {
                    //保存图片
                    Data result = FileUploadUtils.upload(jwtUtils.getUserIdFromToken(getToken()),StorageConstants.STORAGE_THUMBNAIL, file[i], false, FileUtils.allowImage);
                    System.out.println("result"+result);
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

            }
        }
        return toAjax(stuOptionExamanswerService.updateStuAnswer(stuOptionExamanswer));
    }


    //@RequestMapping(value = "/uploadExamImg", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //@ResponseBody
    //public String  uploadExamImg(MultipartFile[] file, HttpServletRequest request,HttpServletResponse response) throws Exception {
    //    String filename = null;
    //    Data result = null;
    //    Map<String, String> reMap = new HashMap<String, String>();
    //    for (int i = 0; i < file.length; i++) {
    //        System.out.println(file[i].getOriginalFilename());
    //        if (!file[i].isEmpty()) {
    //            try {
    //                //保存图片
    //                result = FileUploadUtils.upload(StorageConstants.STORAGE_THUMBNAIL, file[i], false, FileUtils.allowImage);
    //                System.out.println("result" + result);
    //                if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
    //                    System.out.println("resultresult" + result);
    //                }
    //                filename = (String) result.get(StorageConstants.FILE_ID);
    //            } catch (FileNameLimitExceededException | IOException | ApplicationException e) {
    //                return "false";
    //            }
    //        }
    //    }
    //          String url=request.getContextPath()+"showCondensedPicture?fileId="+filename;
    //            //必须返回这样格式的json字符串
    //            reMap.put("uploaded", "1");
    //            reMap.put("url", url);
    //       return url;
    //
    //
    //}
    @PostMapping("/deleteFileId")
    @ResponseBody
    public Data deleteFileId(String fileId,String stoption,String testPaperOneId,String studentId) throws Exception {
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

    /**
     * 增加考试时长
     * @return
     */
    @GetMapping("/addExamTime")
    public String addExamTime(String testPaperOneid,ModelMap modelMap){
        modelMap.put("testPaperOneid",testPaperOneid);
        return prefix + "/addExamTime";
    }


    @PostMapping("/addExamTime")
    @ResponseBody
    public Data addTestPaperTime(String addTime,String testPaperOneid){
        try{
            TestPaperOne testPaperOne =testPaperOneService.getById(testPaperOneid);
            String before_time=testPaperOne.getTime();
            int current_time=Integer.parseInt(addTime)+Integer.parseInt(before_time);
            testPaperOne.setTime(String.valueOf(current_time));

            //将自动提交的考生筛选出来，延长考试时间

            UserExam userExam=new UserExam();
            userExam.setSumbitState("2");
            userExam.setTestPaperOneId(testPaperOneid);
            userExamService.updateSubmitState("0",userExam);
            return toAjax(testPaperOneService.updateTimeById(testPaperOne));
        }catch(Exception e){
            System.out.println("eee"+e);
            e.printStackTrace();
            return Data.error();
        }
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
        TestPaperOneController.testPaperName = testPaperName;
        TestPaperOneController.testPaperName = testPaperName;
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
        TestPaperOneController.qList = qList;
    }

    public static List<MyQuestions> getqList_chapter() {
        return qList_chapter;
    }

    public static void setqList_chapter(List<MyQuestions> qList_chapter) {
        TestPaperOneController.qList_chapter = qList_chapter;
    }

    public static List<MyQuestions> getqList_course() {
        return qList_course;
    }

    public static void setqList_course(List<MyQuestions> qList_course) {
        TestPaperOneController.qList_course = qList_course;
    }

    public static List<MyQuestions> getLists() {
        return lists;
    }

    public static void setLists(List<MyQuestions> lists) {
        TestPaperOneController.lists = lists;
    }

    public static String getTitle_name() {
        return title_name;
    }

    public static void setTitle_name(String title_name) {
        TestPaperOneController.title_name = title_name;
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
        TestPaperOneController.AGAqList = AGAqList;
    }

    public static String[] getQuestionsid() {
        return questionsid;
    }

    public static void setQuestionsid(String[] questionsid) {
        TestPaperOneController.questionsid = questionsid;
    }

    public static ArrayList getNameL() {
        return nameL;
    }

    public static void setNameL(ArrayList nameL) {
        TestPaperOneController.nameL = nameL;
    }
}
