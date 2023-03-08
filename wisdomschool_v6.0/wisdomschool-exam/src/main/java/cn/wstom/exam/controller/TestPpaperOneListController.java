package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Constants;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/school/onlineExam/testPaperOneList")
public class TestPpaperOneListController extends BaseController {
    private String prefix = "school/onlineExam/testPaperOneList";

    @Autowired
    private TestPaperOneListService testPaperOneListService;
    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Autowired
    private MyTitleTypeService myTitleTypeService;
    @Autowired
    private MyQuestionsService myQuestionsService;

    @Autowired
    private TitleTypeService titleTypeService;

    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;


    @GetMapping("{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/list";
    }

    /**
     * 查询考试列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, TestPaperOneList testPaper) {

        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("2");
        String sj = "随机";
        String gd = "固定";
        String y = "是";
        String n = "否";
        startPage();
        List<TestPaperOneList> list = testPaperOneListService.selectList(testPaper);
        for (TestPaperOneList testPaperOneList : list) {
            if (testPaperOneList.getRule().equals("0")) {
                testPaperOneList.setRule(gd);
            } else {
                testPaperOneList.setRule(sj);
            }
            if (testPaperOneList.getState().equals("0")) {
                testPaperOneList.setState(y);
            } else {
                testPaperOneList.setState(n);
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
     * 保存试卷
     */

    @Log(title = "试卷库", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(TestPaperOneList testPaper) throws Exception {
        testPaper.setCreateBy(getUser().getLoginName());
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setState("1");
        testPaper.setType("2");
        return toAjax(testPaperOneListService.save(testPaper));
    }


    @RequestMapping("/paperPreview")
    public String paperPreview(@RequestParam("id") String id, ModelMap mmap) {
        mmap.put("id", id);
        return prefix + "/paperPreview";
    }

    @RequestMapping("/paperPreviewData")
    @ResponseBody
    public List<TestpaperQuestions> paperPreviewData(@RequestParam("id") String id, ModelMap mmap) {
        TestpaperOneTestquestions testpaperOneTestquestions = new TestpaperOneTestquestions();
        testpaperOneTestquestions.setTestPaperOneId(id);
        List<TestpaperOneTestquestions> list = testpaperOneTestquestionsService.list(testpaperOneTestquestions);
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        for (TestpaperOneTestquestions oneTestquestions : list) {
            TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(oneTestquestions.getTestQuestionsId());
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
            }
        }
        for (TestpaperQuestions testpaperQuestions : tqvolist) {
            String[] anList = testpaperQuestions.getTestPaperOptionAnswerArr().split(";");
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<>();
            for (String s : anList) {
                TestpaperOptinanswer testpaperOptinanswer = testpaperOptinanswerService.getById(s);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }
            testpaperQuestions.setTestpaperOptinanswerList(testpaperOptinanswerList);
        }
        return tqvolist;
    }


    /**
     * 修改试卷
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        TestPaperOneList testPaper = testPaperOneListService.getById(id);
        mmap.put("testPaper", testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存试卷
     */

    @Log(title = "试卷", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edithomework(TestPaperOneList testPaper) throws Exception {
        testPaper.setUpdateId(getUser().getUserAttrId());
        testPaper.setUpdateBy(getUser().getLoginName());
        return toAjax(testPaperOneListService.update(testPaper));
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
            return toAjax(testPaperOneListService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 课程 组卷 页面跳转 人手一份
     */
    @GetMapping("/Coursebuild")
    public String Coursebuild(String id, ModelMap mmap) {
        TestPaperOneList testPaper = testPaperOneListService.getById(id);
        String tid = getUser().getUserAttrId();
        mmap.put("cid", testPaper.getCoursrId());
        mmap.put("tid", tid);
        mmap.put("testPaper", testPaper);
        mmap.put("id", id);
        MyTitleType myTitleType = new MyTitleType();
        myTitleType.setCreateId(tid);
        myTitleType.setCid(testPaper.getCoursrId());
        int choiceNum = 0;
        int soEasyChoiceNum = 0;
        int easyChoiceNum = 0;
        int mediumChoiceNum = 0;
        int difficultChoiceNum = 0;
        int soDifficultChoiceNum = 0;
        int checkboxNum = 0;
        int soEasyCheckboxNum = 0;
        int easyCheckboxNum = 0;
        int mediumCheckboxNum = 0;
        int difficultCheckboxNum = 0;
        int soDifficultCheckboxNum = 0;
        int judgeNum = 0;
        int soEasyJudgeNum = 0;
        int easyJudgeNum = 0;
        int mediumJudgeNum = 0;
        int difficultJudgeNum = 0;
        int soDifficultJudgeNum = 0;
        int jiandaNum = 0;
        int soEasyJiandaNum = 0;
        int easyJiandaNum = 0;
        int mediumJiandaNum = 0;
        int difficultJiandaNum = 0;
        int soDifficultJiandaNum = 0;
        int programNum = 0;
        int soEasyProgramNum = 0;
        int easyProgramNum = 0;
        int mediumProgramNum = 0;
        int difficultProgramNum = 0;
        int soDifficultProgramNum = 0;
        int blankNum = 0;
        int soEasyBlankNum = 0;
        int easyBlankNum = 0;
        int mediumBlankNum = 0;
        int difficultBlankNum = 0;
        int soDifficultBlankNum = 0;
        List<MyQuestions> choiceList = new ArrayList<>();
        List<MyQuestions> soEasyChoiceList = new ArrayList<>();
        List<MyQuestions> easyChoiceList = new ArrayList<>();
        List<MyQuestions> mediumChoiceList = new ArrayList<>();
        List<MyQuestions> difficultChoiceList = new ArrayList<>();
        List<MyQuestions> soDifficultChoiceList = new ArrayList<>();
        List<MyQuestions> checkboxList = new ArrayList<>();
        List<MyQuestions> soEasyCheckboxList = new ArrayList<>();
        List<MyQuestions> easyCheckboxList = new ArrayList<>();
        List<MyQuestions> mediumCheckboxList = new ArrayList<>();
        List<MyQuestions> difficultCheckboxList = new ArrayList<>();
        List<MyQuestions> soDifficultCheckboxList = new ArrayList<>();
        List<MyQuestions> soEasyJudgeList = new ArrayList<>();
        List<MyQuestions> judgeList = new ArrayList<>();
        List<MyQuestions> easyJudgeList = new ArrayList<>();
        List<MyQuestions> mediumJudgeList = new ArrayList<>();
        List<MyQuestions> difficultJudgeList = new ArrayList<>();
        List<MyQuestions> soDifficultJudgeList = new ArrayList<>();
        List<MyQuestions> jiandaList = new ArrayList<>();
        List<MyQuestions> easyjiandaList = new ArrayList<>();
        List<MyQuestions> soEasyjiandaList = new ArrayList<>();
        List<MyQuestions> mediumjiandaList = new ArrayList<>();
        List<MyQuestions> difficultjiandaList = new ArrayList<>();
        List<MyQuestions> soDifficultjiandaList = new ArrayList<>();
        List<MyQuestions> blankList = new ArrayList<>();
        List<MyQuestions> easyBlankList = new ArrayList<>();
        List<MyQuestions> soEasyBlankList = new ArrayList<>();
        List<MyQuestions> mediumBlankList = new ArrayList<>();
        List<MyQuestions> difficultBlankList = new ArrayList<>();
        List<MyQuestions> soDifficultBlankList = new ArrayList<>();
        List<MyQuestions> programList = new ArrayList<>();
        List<MyQuestions> easyProgramList = new ArrayList<>();
        List<MyQuestions> soEasyProgramList = new ArrayList<>();
        List<MyQuestions> mediumProgramList = new ArrayList<>();
        List<MyQuestions> difficultProgramList = new ArrayList<>();
        List<MyQuestions> soDifficultProgramList = new ArrayList<>();
        List<MyTitleType> myTitleTypes = myTitleTypeService.list(myTitleType);
        for (int i = 0; i < myTitleTypes.size(); i++) {
            if (myTitleTypes.get(i).getTitleTypeName().equals("选择题")) {
                MyQuestions myQuestion = new MyQuestions();
                myQuestion.setCreateId(tid);
                myQuestion.setXzsubjectsId(testPaper.getCoursrId());
                myQuestion.setTitleTypeId(myTitleTypes.get(i).getId());
                choiceList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("1");
                soEasyChoiceList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("2");
                easyChoiceList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("3");
                mediumChoiceList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("4");
                difficultChoiceList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("5");
                soDifficultChoiceList = myQuestionsService.selectList(myQuestion);
            }
            if (myTitleTypes.get(i).getTitleTypeName().equals("双选题")) {
                MyQuestions myQuestion = new MyQuestions();
                myQuestion.setCreateId(tid);
                myQuestion.setXzsubjectsId(testPaper.getCoursrId());
                myQuestion.setTitleTypeId(myTitleTypes.get(i).getId());
                checkboxList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("1");
                soEasyCheckboxList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("2");
                easyCheckboxList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("3");
                mediumCheckboxList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("4");
                difficultCheckboxList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("5");
                soDifficultCheckboxList = myQuestionsService.selectList(myQuestion);
            }
            if (myTitleTypes.get(i).getTitleTypeName().equals("判断题")) {
                MyQuestions myQuestion = new MyQuestions();
                myQuestion.setCreateId(tid);
                myQuestion.setXzsubjectsId(testPaper.getCoursrId());
                myQuestion.setTitleTypeId(myTitleTypes.get(i).getId());
                judgeList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("1");
                soEasyJudgeList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("2");
                easyJudgeList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("3");
                mediumJudgeList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("4");
                difficultJudgeList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("5");
                soDifficultJudgeList = myQuestionsService.selectList(myQuestion);
            }
            if (myTitleTypes.get(i).getTitleTypeName().equals("简答题")) {
                MyQuestions myQuestion = new MyQuestions();
                myQuestion.setCreateId(tid);
                myQuestion.setXzsubjectsId(testPaper.getCoursrId());
                myQuestion.setTitleTypeId(myTitleTypes.get(i).getId());
                jiandaList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("1");
                soEasyjiandaList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("2");
                easyjiandaList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("3");
                mediumjiandaList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("4");
                difficultjiandaList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("5");
                soDifficultjiandaList = myQuestionsService.selectList(myQuestion);
            }
            if (myTitleTypes.get(i).getTitleTypeName().equals("程序设计题")) {
                MyQuestions myQuestion = new MyQuestions();
                myQuestion.setCreateId(tid);
                myQuestion.setXzsubjectsId(testPaper.getCoursrId());
                myQuestion.setTitleTypeId(myTitleTypes.get(i).getId());
                programList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("1");
                soEasyProgramList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("2");
                easyProgramList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("3");
                mediumProgramList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("4");
                difficultProgramList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("5");
                soDifficultProgramList = myQuestionsService.selectList(myQuestion);
            }
            if (myTitleTypes.get(i).getTitleTypeName().equals("填空题")) {
                MyQuestions myQuestion = new MyQuestions();
                myQuestion.setCreateId(tid);
                myQuestion.setXzsubjectsId(testPaper.getCoursrId());
                myQuestion.setTitleTypeId(myTitleTypes.get(i).getId());
                blankList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("1");
                soEasyBlankList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("2");
                easyBlankList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("3");
                mediumBlankList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("4");
                difficultBlankList = myQuestionsService.selectList(myQuestion);
                myQuestion.setDifficulty("5");
                soDifficultBlankList = myQuestionsService.selectList(myQuestion);
            }
        }
        if (choiceList != null) {
            choiceNum = choiceList.size();
        }
        if (easyChoiceList != null) {
            easyChoiceNum = easyChoiceList.size();
        }
        if (soEasyChoiceList != null) {
            soEasyChoiceNum = soEasyChoiceList.size();
        }
        if (mediumChoiceList != null) {
            mediumChoiceNum = mediumChoiceList.size();
        }
        if (difficultChoiceList != null) {
            difficultChoiceNum = difficultChoiceList.size();
        }
        if (soDifficultChoiceList != null) {
            soDifficultChoiceNum = soDifficultChoiceList.size();
        }
        if (checkboxList != null) {
            checkboxNum = checkboxList.size();
        }
        if (soEasyCheckboxList != null) {
            soEasyCheckboxNum = soEasyCheckboxList.size();
        }
        if (easyCheckboxList != null) {
            easyCheckboxNum = easyCheckboxList.size();
        }
        if (mediumCheckboxList != null) {
            mediumCheckboxNum = mediumCheckboxList.size();
        }
        if (difficultCheckboxList != null) {
            difficultCheckboxNum = difficultCheckboxList.size();
        }
        if (soDifficultCheckboxList != null) {
            soDifficultCheckboxNum = soDifficultCheckboxList.size();
        }
        if (judgeList != null) {
            judgeNum = judgeList.size();
        }
        if (soEasyJudgeList != null) {
            soEasyJudgeNum = soEasyJudgeList.size();
        }
        if (easyJudgeList != null) {
            easyJudgeNum = easyJudgeList.size();
        }
        if (mediumJudgeList != null) {
            mediumJudgeNum = mediumJudgeList.size();
        }
        if (difficultJudgeList != null) {
            difficultJudgeNum = difficultJudgeList.size();
        }
        if (soDifficultJudgeList != null) {
            soDifficultJudgeNum = soDifficultJudgeList.size();
        }
        if (jiandaList != null) {
            jiandaNum = jiandaList.size();
        }
        if (soEasyjiandaList != null) {
            soEasyJiandaNum = soEasyjiandaList.size();
        }
        if (easyjiandaList != null) {
            easyJiandaNum = easyjiandaList.size();
        }
        if (mediumjiandaList != null) {
            mediumJiandaNum = mediumjiandaList.size();
        }
        if (difficultjiandaList != null) {
            difficultJiandaNum = difficultjiandaList.size();
        }
        if (soDifficultjiandaList != null) {
            soDifficultJiandaNum = soDifficultjiandaList.size();
        }
        if (programList != null) {
            programNum = programList.size();
        }
        if (soEasyProgramList != null) {
            soEasyProgramNum = soEasyProgramList.size();
        }
        if (easyProgramList != null) {
            easyProgramNum = easyProgramList.size();
        }
        if (mediumProgramList != null) {
            mediumProgramNum = mediumProgramList.size();
        }
        if (difficultProgramList != null) {
            difficultProgramNum = difficultProgramList.size();
        }
        if (soDifficultProgramList != null) {
            soDifficultProgramNum = soDifficultProgramList.size();
        }
        if (blankList != null) {
            blankNum = blankList.size();
        }
        if (easyBlankList != null) {
            easyBlankNum = easyBlankList.size();
        }
        if (soEasyBlankList != null) {
            soEasyBlankNum = soEasyBlankList.size();
        }
        if (mediumBlankList != null) {
            mediumBlankNum = mediumBlankList.size();
        }
        if (difficultBlankList != null) {
            difficultBlankNum = difficultBlankList.size();
        }
        if (soDifficultBlankList != null) {
            soDifficultBlankNum = soDifficultBlankList.size();
        }
        mmap.put("choiceNum", choiceNum);
        mmap.put("easyChoiceNum", easyChoiceNum);
        mmap.put("soEasyChoiceNum", soEasyChoiceNum);
        mmap.put("mediumChoiceNum", mediumChoiceNum);
        mmap.put("difficultChoiceNum", difficultChoiceNum);
        mmap.put("soDifficultChoiceNum", soDifficultChoiceNum);
        mmap.put("checkboxNum", checkboxNum);
        mmap.put("easyCheckboxNum", easyCheckboxNum);
        mmap.put("soEasyCheckboxNum", soEasyCheckboxNum);
        mmap.put("mediumCheckboxNum", mediumCheckboxNum);
        mmap.put("difficultCheckboxNum", difficultCheckboxNum);
        mmap.put("soDifficultCheckboxNum", soDifficultCheckboxNum);
        mmap.put("judgeNum", judgeNum);
        mmap.put("easyJudgeNum", easyJudgeNum);
        mmap.put("soEasyJudgeNum", soEasyJudgeNum);
        mmap.put("mediumJudgeNum", mediumJudgeNum);
        mmap.put("difficultJudgeNum", difficultJudgeNum);
        mmap.put("soDifficultJudgeNum", soDifficultJudgeNum);
        mmap.put("jiandaNum", jiandaNum);
        mmap.put("easyJiandaNum", easyJiandaNum);
        mmap.put("soEasyJiandaNum", soEasyJiandaNum);
        mmap.put("mediumJiandaNum", mediumJiandaNum);
        mmap.put("difficultJiandaNum", difficultJiandaNum);
        mmap.put("soDifficultJiandaNum", soDifficultJiandaNum);
        mmap.put("programNum", programNum);
        mmap.put("easyProgramNum", easyProgramNum);
        mmap.put("soEasyProgramNum", soEasyProgramNum);
        mmap.put("mediumProgramNum", mediumProgramNum);
        mmap.put("difficultProgramNum", difficultProgramNum);
        mmap.put("soDifficultProgramNum", soDifficultProgramNum);
        mmap.put("blankNum", blankNum);
        mmap.put("easyBlankNum", easyBlankNum);
        mmap.put("soEasyBlankNum", soEasyBlankNum);
        mmap.put("mediumBlankNum", mediumBlankNum);
        mmap.put("difficultBlankNum", difficultBlankNum);
        mmap.put("soDifficultBlankNum", soDifficultBlankNum);
        return prefix + "/course_build_v1";
    }

    @PostMapping("/questionNum")
    @ResponseBody
    public List<List<MyQuestions>> questionNum(String chapterIds, String cid, String tid) {
        String[] chapterId = chapterIds.split(",");//章节
        Set<String> ids = new HashSet<>();
        Collections.addAll(ids, chapterId);
        System.out.println(ids);
        List<MyQuestions> myQuestionsList;
        List<List<MyQuestions>> myQuestionsList1 = new ArrayList<>();
        for (String id : ids) {
            MyQuestions myQuestions = new MyQuestions();
            myQuestions.setXzsubjectsId(cid);
            myQuestions.setCreateId(tid);
            myQuestions.setChapterId(id);
            myQuestionsList = myQuestionsService.selectList(myQuestions);
            myQuestionsList1.add(myQuestionsList);
        }
        return myQuestionsList1;
    }

    /**
     * 手动 组卷 页面跳转
     */
    @GetMapping("/Humanbuild")
    public String Humanbuild(String id, ModelMap mmap) {
        TestPaperOneList testPaper = testPaperOneListService.getById(id);
        mmap.put("testPaper", testPaper);
        mmap.put("tid", getUser().getUserAttrId());
        mmap.put("cid", testPaper.getCoursrId());
        mmap.put("id", id);
        mmap.put("rule", testPaper.getRule());
        return prefix + "/human_build";
    }






    /**
     * 添加题目
     *
     * @param paperId
     * @return
     * @throws Exception
     */
    @PostMapping("/question/add/{paperId}/{tid}/{cid}")
    @ResponseBody
    @Transactional
    public Data addOne(@PathVariable("paperId") String paperId,
                       @PathVariable("tid") String tid,
                       @PathVariable("cid") String cid,
                       String chapterIds,
                       Integer soEasyChoiceNum, Integer easyChoiceNum, Integer mediumChoiceNum, Integer difficultChoiceNum, Integer soDifficultChoiceNum,
                       Integer soEasyCheckboxNum, Integer easyCheckboxNum, Integer mediumCheckboxNum, Integer difficultCheckboxNum, Integer soDifficultCheckboxNum,
                       Integer soEasyJudgeNum, Integer easyJudgeNum, Integer mediumJudgeNum, Integer difficultJudgeNum, Integer soDifficultJudgeNum,
                       Integer soEasyBlankNum, Integer easyBlankNum, Integer mediumBlankNum, Integer difficultBlankNum, Integer soDifficultBlankNum,
                       Integer soEasyJiandaNum, Integer easyJiandaNum, Integer mediumJiandaNum, Integer difficultJiandaNum, Integer soDifficultJiandaNum,
                       Integer soEasyProgramNum, Integer easyProgramNum, Integer mediumProgramNum, Integer difficultProgramNum, Integer soDifficultProgramNum,
                       Integer choiceScore, Integer checkboxScore, Integer judgeScore, Integer jiandaScore, Integer programScore, Integer blankScore
                     ) throws Exception {

        String[] chapterId = chapterIds.split(",");//章节
        Set<String> ids = new HashSet<>(Arrays.asList(chapterId));
        List<String> id = new ArrayList<>(ids);
        testpaperOneTestquestionsService.deleteByPaperOneId(paperId);
        List<MyQuestions> myQuestions = myQuestionsService.selectBatchByChapterIds(id);

        SysUser user=getUser();
        String attrId = user.getUserAttrId();

        /*  查询题目 */
        List<MyQuestions> myQuestionsChoiceNumList = new ArrayList<>(); //选择题
        List<MyQuestions> myQuestionsJiandaList = new ArrayList<>(); //简答题
        List<MyQuestions> myQuestionsJudgeList = new ArrayList<>();//判断题
        List<MyQuestions> myQuestionsBlankList = new ArrayList<>();//填空
        List<MyQuestions> myQuestionsCheckBoxList = new ArrayList<>();//多选题
        List<MyQuestions> myQuestionsProgramList = new ArrayList<>();//编程题，程序题
        for (MyQuestions myQuestion:myQuestions) {
            if ("01,05".contains(myQuestion.getTitleTypeNum())){ //单项选择题模板
                myQuestionsChoiceNumList.add(myQuestion);
            }else if("07".contains(myQuestion.getTitleTypeNum())){ //简单题
                myQuestionsJiandaList.add(myQuestion);
            }else if("06".contains(myQuestion.getTitleTypeNum())){//判断
                myQuestionsJudgeList.add(myQuestion);
            }else if("02,03".contains(myQuestion.getTitleTypeNum())){//多选
                myQuestionsCheckBoxList.add(myQuestion);
            }else if ("04".contains(myQuestion.getTitleTypeNum())){ //填空题模板
                myQuestionsBlankList.add(myQuestion);
            }else{//主观题
                myQuestionsProgramList.add(myQuestion);
            }
        }

        LinkedList<MyQuestions> paperQuestion = new LinkedList<>();

        //单选题
        /*  保存题目和试卷的关联 */
        if (soEasyChoiceNum != null) {
           createRandoms(paperQuestion,myQuestionsChoiceNumList,soEasyChoiceNum,"1");
        }
        if (easyChoiceNum != null) {
            createRandoms(paperQuestion,myQuestionsChoiceNumList,easyChoiceNum,"2");
        }
        if (mediumChoiceNum != null) {
            createRandoms(paperQuestion,myQuestionsChoiceNumList,mediumChoiceNum,"3");
        }
        if (difficultChoiceNum != null) {
            createRandoms(paperQuestion,myQuestionsChoiceNumList,difficultChoiceNum,"4");
        }
        if (soDifficultChoiceNum != null) {
            createRandoms(paperQuestion,myQuestionsChoiceNumList,soDifficultChoiceNum,"5");
        }
        //填空题
        if (soEasyBlankNum != null) {
            createRandoms(paperQuestion,myQuestionsBlankList,soEasyBlankNum,"1");
        }
        if (easyBlankNum != null) {
            createRandoms(paperQuestion,myQuestionsBlankList,easyBlankNum,"2");
        }
        if (mediumBlankNum != null) {
            createRandoms(paperQuestion,myQuestionsBlankList,mediumBlankNum,"3");
        }
        if (difficultBlankNum != null) {
            createRandoms(paperQuestion,myQuestionsBlankList,difficultBlankNum,"4");
        }
        if (soDifficultBlankNum != null) {
            createRandoms(paperQuestion,myQuestionsBlankList,soDifficultBlankNum,"5");
        }
//        //简答题
        if (soEasyJiandaNum != null) {
            createRandoms(paperQuestion,myQuestionsJiandaList,soEasyJiandaNum,"1");
        }
        if (easyJiandaNum != null) {
            createRandoms(paperQuestion,myQuestionsJiandaList,easyJiandaNum,"2");
        }
        if (mediumJiandaNum != null) {
            createRandoms(paperQuestion,myQuestionsJiandaList,mediumJiandaNum,"3");
        }
        if (difficultJiandaNum != null) {
            createRandoms(paperQuestion,myQuestionsJiandaList,difficultJiandaNum,"4");
        }
        if (soDifficultJiandaNum != null) {

            createRandoms(paperQuestion,myQuestionsJiandaList,soDifficultJiandaNum,"5");
        }
//        //判断题
        if (soEasyJudgeNum != null) {
            createRandoms(paperQuestion,myQuestionsJudgeList,soEasyJudgeNum,"1");
        }
        if (easyJudgeNum != null) {
            createRandoms(paperQuestion,myQuestionsJudgeList,easyJudgeNum,"2");
        }
        if (mediumJudgeNum != null) {
            createRandoms(paperQuestion,myQuestionsJudgeList,mediumJudgeNum,"3");
        }
        if (difficultJudgeNum != null) {
            createRandoms(paperQuestion,myQuestionsJudgeList,difficultJudgeNum,"4");
        }
        if (soDifficultJudgeNum != null) {
            createRandoms(paperQuestion,myQuestionsJudgeList,soDifficultJudgeNum,"5");
        }
//        //多选题
        if (soEasyCheckboxNum != null) {

            createRandoms(paperQuestion,myQuestionsCheckBoxList,soEasyCheckboxNum,"1");
        }
        if (easyCheckboxNum != null) {
            createRandoms(paperQuestion,myQuestionsCheckBoxList,easyCheckboxNum,"2");
        }
        if (mediumCheckboxNum != null) {
            createRandoms(paperQuestion,myQuestionsCheckBoxList,mediumCheckboxNum,"3");
        }
        if (difficultCheckboxNum != null) {
            createRandoms(paperQuestion,myQuestionsCheckBoxList,difficultCheckboxNum,"4");
        }
        if (soDifficultCheckboxNum != null) {
            createRandoms(paperQuestion,myQuestionsCheckBoxList,soDifficultCheckboxNum,"5");
        }

//        //程序设计题
        if (soEasyProgramNum != null) {

            createRandoms(paperQuestion,myQuestionsProgramList,soEasyProgramNum,"1");
        }
        if (easyProgramNum != null) {
            createRandoms(paperQuestion,myQuestionsProgramList,easyProgramNum,"2");
        }
        if (mediumProgramNum != null) {
            createRandoms(paperQuestion,myQuestionsProgramList,mediumProgramNum,"3");
        }
        if (difficultProgramNum != null) {
            createRandoms(paperQuestion,myQuestionsProgramList,difficultProgramNum,"4");
        }
        if (soDifficultProgramNum != null) {
            createRandoms(paperQuestion,myQuestionsProgramList,soDifficultProgramNum,"5");
        }

        int sumScore=0;
        for (MyQuestions myQuestion:paperQuestion) {
            int Score=0;
            if ("01,05".contains(myQuestion.getTitleTypeNum())){ //单项选择题模板
                Score=choiceScore;

            }else if("07".contains(myQuestion.getTitleTypeNum())){ //简答题
                Score=jiandaScore;

            }else if("06".contains(myQuestion.getTitleTypeNum())){//判断
                Score=judgeScore;

            } else if("02,03".contains(myQuestion.getTitleTypeNum())){//多选
                Score=checkboxScore;

            } else if ("04".contains(myQuestion.getTitleTypeNum())){ //填空题
                Score=blankScore;

            } else if ("08,09,10,11,12".contains(myQuestion.getTitleTypeNum())){ //编程题
                Score=programScore;

            }
            testPaperOneListService.UpdateTestPaperOneTestQuestion(myQuestion,Score,myQuestion.getDifficulty(),paperId,attrId);
            sumScore=sumScore+Score;
        }

        TestPaperOneList testPaper = testPaperOneListService.getById(paperId);
        String time = new Date().getYear() + 1900 + "";
        testPaper.setTestYear(time);
        testPaper.setState(Constants.EXAM_TYPE_WAIT);
        testPaper.setScore(String.valueOf(sumScore));
        testPaper.setSchoolNo(String.valueOf(user.getSchoolId()));
        return toAjax(testPaperOneListService.update(testPaper));
    }


    /**
     * 从集合中随机取出N个不重复的元素
     * @param list 需要被取出数据的集合
     * @param sumlist 放置数据的集合
     * @param n 取出的元素数量
     * @param str 判断条件
     * @return
     */
    public static List<MyQuestions> createRandoms(List<MyQuestions> sumlist, List<MyQuestions> list,int n,String str) {
        Map<Integer,String> map = new HashMap<>();
        if (list.size() <= n) {
            sumlist.addAll(list);
            return sumlist;
        } else {
            int x=0;
            while (map.size() <list.size()) {
                if (x==n){
                    break;
                }
                int random = (int)(Math.random() * list.size());
                System.out.println(random);
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    MyQuestions myQuestions = list.get(random);
                    if(myQuestions.getDifficulty().equals(str)){
                       sumlist.add(myQuestions);
                        x++;
                    }
                }
            }
            return sumlist;
        }
    }

}
