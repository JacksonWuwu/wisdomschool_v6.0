package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.feign.StudentService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 节的试卷 信息操作处理
 *
 * @author hzh
 * @date 20190314
 */
@Controller
@RequestMapping("/school/onlineExam/testChapterPaper")
public class TestChapterPaperController extends BaseController {
    private String prefix = "/school/onlineExam/testChapterPaper";

    @Autowired
    private TestChapterPaperService testChapterPaperService;

    @Autowired
    private TestpaperTestquestionsService testpaperTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;

    @Autowired
    private TestStuoptionanswerService testStuoptionanswerService;
    @Autowired
    private StudentService studentService;




    @GetMapping("/showTable/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        Chapter chapter = adminService.getChapterById(cid);
        modelMap.put("chapter", chapter);
        return prefix + "/list";
    }

    /**
     * 查询节的试卷列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable  String cid, TestChapterPaper testChapterPaper) {
        System.out.println("+++++++++++");
        startPage();
        int chapterid = Integer.parseInt(cid);
        testChapterPaper.setCId(chapterid);
        List<TestChapterPaper> list = testChapterPaperService.list(testChapterPaper);
        return wrapTable(list);
    }

    /**
     * 新增节的试卷
     */
    @GetMapping("/add/{chapterId}/{courseId}")
    public String toAdd(String chapterId, String courseId, ModelMap modelMap) {
        TestPaper testPaper = new TestPaper();
        testPaper.setCoursrId(courseId);
        modelMap.put("testPaper", testPaper);
        modelMap.put("courseId", courseId);
        modelMap.put("chapterId", chapterId);
        return prefix + "/add";
    }

    /**
     * 新增保存节的试卷
     */

    @Log(title = "节的试卷", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(String cid, String chapterId, String id) throws Exception {
        TestChapterPaper testChapterPaper=new TestChapterPaper();
            testChapterPaper.setPaperId(id);
            testChapterPaper.setCId(Integer.parseInt(chapterId));
            testChapterPaper.setCreateBy(getUser().getLoginName());
        return toAjax(testChapterPaperService.save(testChapterPaper));
    }

    /**
     * 修改节的试卷
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
        TestChapterPaper testChapterPaper = testChapterPaperService.getById(id);
        mmap.put("testChapterPaper", testChapterPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存节的试卷
     */

    @Log(title = "节的试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(TestChapterPaper testChapterPaper) throws Exception {
        return toAjax(testChapterPaperService.update(testChapterPaper));
    }

    /**
     * 删除节的试卷
     */

    @Log(title = "节的试卷", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(testChapterPaperService.removeById(ids));
    }


    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/stuToTest")
    public String stuToTest(String id, ModelMap modelMap) {
        System.out.println("Let's Go");
        modelMap.put("paperId", id);
        return prefix + "/stuTest";
    }

    /**
     * 章节开始测试
     *
     * @return
     */
    @RequestMapping("/startChaptestPaper")
    @ResponseBody
    public List<TestpaperQuestions> testStart(String paperId, String chapterName, ModelMap mmap) throws Exception {
        System.out.println("just do it");
        TestpaperTestquestions testpaperTestquestions  =new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();
        testpaperTestquestionsList = testpaperTestquestionsService.list(testpaperTestquestions);
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        for (TestpaperTestquestions testquestions : testpaperTestquestionsList) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = testpaperQuestionsService.getById(testquestions.getTestQuestionsId());
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
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
                    TestStuoptionanswer testStuoptionanswer = new TestStuoptionanswer();
                    String uid = getUser().getUserAttrId();
                    int newId = Integer.parseInt(uid);
                    testStuoptionanswer.setCreateId(newId);
                    testStuoptionanswer.setStuanswer(toa.getStanswer());
                    List<TestStuoptionanswer> stuoa2 = testStuoptionanswerService.list(testStuoptionanswer);
                    if (stuoa2.size() != 0) {
                        TestStuoptionanswer stuoa = stuoa2.get(0);
                        if (stuoa == null) {
                            stuoa = new TestStuoptionanswer();
                            stuoa.setStoption(toa.getStoption());
                            stuoa.setStuanswer("F");
                            testStuoptionanswerService.saveOrUpdate(stuoa);
                        }
                    }

                }
            }
        }
        for (TestpaperQuestions testpaperQuestions : tqvolist) {
            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
            String[] anList = testpaperQuestions.getTestPaperOptionAnswerArr().split(";");
            String[] stuAnList = testpaperQuestions.getTestPaperOptionAnswerArr().split(";");
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
            List<TestStuoptionanswer> testStuoptionanswerListLater = new ArrayList<TestStuoptionanswer>();
            for (String s : anList) {
                testpaperOptinanswer = testpaperOptinanswerService.getById(s);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }
            List<TestStuoptionanswer> testStuoptionanswerList = new ArrayList<TestStuoptionanswer>();
            for (String s : stuAnList) {
                TestStuoptionanswer testStuoptionanswer = new TestStuoptionanswer();
                testStuoptionanswer.setTestPaperOptionAnswer(s);
                testStuoptionanswerList = testStuoptionanswerService.list(testStuoptionanswer);
                if (testStuoptionanswerList.size() != 0) {
                    testStuoptionanswerListLater.add(testStuoptionanswerList.get(0));
                }
            }
            testpaperQuestions.setTestStuoptionanswerList(testStuoptionanswerListLater);
            testpaperQuestions.setTestpaperOptinanswerList(testpaperOptinanswerList);
        }
        tqvolist.sort((x, y) -> Double.compare(Double.parseDouble(x.getTitleTypeNum()), Double.parseDouble(y.getTitleTypeNum())));//这方法需要jdk1.8以上
        return tqvolist;
    }


    /**
     * 章节考试上传答案
     *
     * @param questionNum
     * @param tpquestionId
     * @param anwserId
     * @param anwserStr
     */
    @PostMapping("/saveChapterTestAnswer")
    @ResponseBody
    public String SaveOrUpdateStuAnwser(@RequestParam String questionNum, @RequestParam String tpquestionId,
                                        @RequestParam String anwserId, @RequestParam String anwserStr) {
        try {
            TestpaperQuestions tpquestion = new TestpaperQuestions();
            boolean istf = true;
            String uid = getUser().getUserAttrId();
            Student  stuinfo = studentService.getStudentById(uid);
//            if (stuinfo != null && !"".equals(stuinfo)) {
                tpquestion = testpaperQuestionsService.getById(tpquestionId);
                String tpoastr = tpquestion.getTestPaperOptionAnswerArr();
                String[] tpoaArr = tpoastr.substring(0, tpoastr.length() - 1).split(";");
                String[] mytpoaArr = anwserStr.substring(0, anwserStr.length() - 1).split(";");
                for (int i = 0; i < tpoaArr.length; i++) {
                    TestpaperOptinanswer tpoa = testpaperOptinanswerService.getById(tpoaArr[i]);
                    List<TestStuoptionanswer> stuoaList = new ArrayList<TestStuoptionanswer>();
                    TestStuoptionanswer stuoa = new TestStuoptionanswer();
                    stuoa.setCreateId(Integer.parseInt(uid));
                    stuoa.setTestPaperOptionAnswer(tpoa.getId());
                    stuoaList = testStuoptionanswerService.list(stuoa);
                    if (stuoaList.size() == 0) {
                        stuoa = new TestStuoptionanswer();
                        stuoa.setTestPaperOptionAnswer(tpoa.getId());
                        stuoa.setCreateId(Integer.parseInt(uid) );
                        stuoa.setCreateBy(getUser().getLoginName());
                        stuoa.setStuanswer(mytpoaArr[i]);
                        int id = Integer.parseInt(stuinfo.getId());
                        stuoa.setCreateId(id );
                        Uuid uid2 = new Uuid();
                        stuoa.setId(uid2.UId());
                        testStuoptionanswerService.save(stuoa);
                    } else {
                        stuoa.setUpdateId(Integer.parseInt(uid) );
                        stuoa.setUpdateBy(getUser().getLoginName());
                        stuoa.setTestPaperOptionAnswer(tpoa.getId());
                        stuoa.setStuanswer(mytpoaArr[i]);
                        stuoa.setCreateId(stuoaList.get(0).getCreateId());
                        stuoa.setTestPaperOptionAnswer(tpoaArr[i]);
                        testStuoptionanswerService.updateByIdAns(stuoa);
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

//            } else {
//                System.out.println("没有找到学生");
//            }
        } catch (Exception e) {
            System.out.println(e.getCause());
            return e.getMessage();

        }
        return "End";
    }





}
