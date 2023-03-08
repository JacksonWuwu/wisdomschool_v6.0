package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.entity.Individual.MyQuestionVO;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Convert;
import cn.wstom.exam.utils.ExcelUtil;
import cn.wstom.exam.utils.Uuid;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;


/**
 * 题库
 *
 * @author hzh
 */
@Controller
@RequestMapping("/school/onlineExam/myQuestion")
public class MyQuestionController extends BaseController {
    private String prefix = "school/onlineExam/myQuestion";

    @Autowired
    private MyQuestionsService myQuestionsService;
    @Autowired
    private PublicQuestionsService publicQuestionsService;
    @Autowired
    private PublicOptionAnswerService publicOptionAnswerService;
    @Autowired
    private TestpaperTestquestionsService paperQuestionsService;
    @Autowired
    private TestpaperOneTestquestionsService paperOneQuestionsService;
    @Autowired
    private TestPaperWorkListService testPaperWorkListService;
    @Autowired
    private MyOptionAnswerService myOptionAnswerService;



    @Autowired
    private MyTitleTypeService myTitleTypeService;

    @Autowired
    private  TestPaperOneListService testPaperOneListService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;


    private MyOptionAnswer optionAnswer;
    /***
     * 登录ID
     */
    String loginid;
    /**
     * SysUser
     */
    SysUser sysUser;
    /**
     * 用户ID
     */
    String uid;

    /**
     * 获取列表页面
     *
     * @return
     */
    @GetMapping("/myQuestionsList")
    List<MyQuestions> myQuestionsList(MyQuestions myQuestions){
        return myQuestionsService.selectList(myQuestions);
    }

    @GetMapping("/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        String tid = adminService.getUserById(getUserId()).getUserAttrId();
        modelMap.put("tid", tid);
        return prefix + "/list";
    }


    /**
     * 跳转添加页面
     *
     * @return
     */

    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/add";
    }


    @PostMapping("/add")
    @ResponseBody
    public Data addQuestion(MyQuestions myQuestions) throws Exception {
        System.out.println("myQuestions==================add"+myQuestions);
        uid = getUser().getUserAttrId();
        String[] optionanswersArr = null;
        String[] optionanswerArr = null;
        String trimstr = "option=";
        String myoaStr = "";
        String optionanswers = myQuestions.getMyoptionAnswerArr();
        List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
        optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr按;进行分割，这里分割成4个
        for (String s : optionanswersArr) {
            optionanswerArr = s.substring(0, s.length() - 1).split(":");//按：进行分割
            String optionone = "";
            if (optionanswerArr[0].contains(trimstr)) {
                System.out.println(optionanswerArr[0]);
                optionone = optionanswerArr[0].substring(7);// 去掉option=开头字符串
            } else {
                optionone = optionanswerArr[0];
            }
            String answerone = optionanswerArr[1];
            try {
                String optionstr = java.net.URLDecoder.decode(optionone, "UTF-8");
                optionAnswer = new MyOptionAnswer(); //生成一个选项对象存放选项的详细信息
                Uuid answerId = new Uuid(); //随机生成一个id
                optionAnswer.setCreateBy(getUser().getLoginName());
                optionAnswer.setCreateId(uid);
                optionAnswer.setId(answerId.UId());
                optionAnswer.setStoption(optionstr);
                optionAnswer.setStanswer(answerone);
                toAjax(myOptionAnswerService.save(optionAnswer)); //MyOptionAnswerMapper.xml->insert  涉及的表：tk_my_optinanswer
                myoaStr += optionAnswer.getId() + ";";
            } catch (Exception e) {
                System.out.println("乱码解决错误：" + e);
            }
        }
        myQuestions.setCreateBy(getUser().getLoginName());
        myQuestions.setCreateId(uid);
        myQuestions.setQexposed("0");
        myQuestions.setQmaxexposure("10000");
        myQuestions.setMyoptionAnswerArr(myoaStr);
        System.out.println(myQuestions.getJchapterId());
        return toAjax(myQuestionsService.save(myQuestions)); //MyQuestion.xml->insert 涉及的数据表：tk_my_questions

    }

    @Log(title = "修改题目", actionType = ActionType.INSERT)
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data update(MyQuestions myQuestions) throws Exception {
        System.out.println("myQuestions==================update"+myQuestions+" myQuestions.getId()"+ myQuestions.getId());

        String[] optionanswersArr;
        String[] optionanswerArr;
        String trimstr = "option=";
        StringBuilder myoaStr = new StringBuilder();
        String optionanswers = myQuestions.getMyoptionAnswerArrContent();
        List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
        //optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr按;进行分割，这里分割成4个
        myoaStr = new StringBuilder(myQuestions.getMyoptionAnswerArr());
        if (myoaStr != null && !"".equals(myoaStr.toString())) {
            String[] myoaArr = myoaStr.substring(0, myoaStr.length() - 1).split(";");
            for (String s : myoaArr) {
                MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(s);
                System.out.println("myOptionAnswer"+myOptionAnswer);
                if (myOptionAnswer != null) {
                    myoalist.add(myOptionAnswer);
                }
            }
        }

        if (optionanswers != null && !"".equals(optionanswers)) {
            optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");
            for (int i = 0; i < optionanswersArr.length; i++) {
                optionanswerArr = optionanswersArr[i].substring(0, optionanswersArr[i].length() - 1).split(":");
                String optionone = "";
                if (optionanswerArr[0].contains(trimstr)) {
                    optionone = optionanswerArr[0].substring(7);// 去掉option=开头字符串
                } else {
                    optionone = optionanswerArr[0];
                }
                try {
                    String optionstr = java.net.URLDecoder.decode(optionone,
                            "UTF-8");
                    String answerone = optionanswerArr[1];
                    System.out.println("optionstr"+optionstr+"||||"+"answerone"+answerone);
                    if (myoalist.size() != 0 && !myoalist.isEmpty()) {
                        if (i < myoalist.size()) {
                            optionAnswer = myoalist.get(i);
                            optionAnswer.setUpdateBy(getUser().getLoginName());
                            optionAnswer.setUpdateId(getUser().getUserAttrId());
                            optionAnswer.setStoption(optionstr);
                            optionAnswer.setStanswer(answerone);
                            System.out.println("%%%%%%%%%%%%%%%%%");
                            myOptionAnswerService.update(optionAnswer);
                            System.out.println("*********************");
                        } else {
                            MyOptionAnswer optionAnswer = new MyOptionAnswer();
                            Uuid answerId = new Uuid();
                            optionAnswer.setCreateBy(getUser().getLoginName());
                            optionAnswer.setCreateId(uid);
                            optionAnswer.setId(answerId.UId());
                            optionAnswer.setStoption(optionstr);
                            optionAnswer.setStanswer(answerone);
                            myOptionAnswerService.save(optionAnswer);
                            myoaStr.append(optionAnswer.getId()).append(";");
                        }
                    }
                } catch (Exception e) {
                    return error("更新出错:" + e.getCause() + ";" + e.getMessage());
                }
            }
        }
        myQuestions.setMyoptionAnswerArr(myoaStr.toString());
        myQuestions.setCreateId(getUser().getUserAttrId());
        myQuestions.setCreateBy(getUser().getLoginName());
        return toAjax(myQuestionsService.update(myQuestions));

    }

    /**
     * 编辑
     *
     * @param id
     * @param mmap
     * @return
     */

    @GetMapping("/edit/{id}/{cid}")
    public String toEdit(@PathVariable("id") String id, @PathVariable("cid") String cid,ModelMap mmap) {
        mmap.put("MyQuestionsId", id);
        mmap.put("cid", cid);
        return prefix + "/edit";
    }


    @RequestMapping("/MyQuestionsInfo/{MyQuestionsId}")
    @ResponseBody
    public Data MyQuestionsInfo(@PathVariable("MyQuestionsId")String MyQuestionsId) throws Exception {
        //保存学校信息
        MyQuestions myQuestions = myQuestionsService.getById(MyQuestionsId);
        String[] myoptionAnswers =myQuestions.getMyoptionAnswerArr().split(";");
        List<MyOptionAnswer> myQAlist = new ArrayList<>();
        for (String myoptionAnswer : myoptionAnswers) {
            myQAlist.add(myOptionAnswerService.getById(myoptionAnswer));
        }
        Chapter chapter = adminService.getChapterById(myQuestions.getChapterId());
        Chapter subchapter = adminService.getChapterById(myQuestions.getJchapterId());
        myQuestions.setChapterName(chapter.getTitle()+" "+chapter.getName());
        myQuestions.setChapterName2(subchapter.getTitle()+" "+subchapter.getName());
        Map<String, Object> map = new HashMap<>();
        map.put("myQuestions",myQuestions);
        map.put("myQAlist",myQAlist);
        return Data.success(map);
    }


    /**
     * 题库页面
     *
     * @param myQuestions
     * @return
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, MyQuestions myQuestions) {
        myQuestions.setXzsubjectsId(cid);
        myQuestions.setCreateId(getUser().getUserAttrId());
        System.out.println("tid" + myQuestions.getTitleTypeId());
        startPage();
        List<MyQuestions> list = myQuestionsService.list(myQuestions);
        String easy = "容易";
        String easyNum = "1";
        String relativelyEasy = "较易";
        String relativelyEasyNum = "2";
        String secondary = "中等";
        String secondaryNum = "3";
        String moreDifficult = "较难";
        String moreDifficultNum = "4";
        String difficulty = "困难";
        String difficultyNum = "5";
        if (list.size() != 0) {
            for (MyQuestions questions : list) {
                if (questions.getDifficulty().equals(easyNum)) {
                    questions.setDifficulty(easy);
                }
                if (questions.getDifficulty().equals(relativelyEasyNum)) {
                    questions.setDifficulty(relativelyEasy);
                }
                if (questions.getDifficulty().equals(secondaryNum)) {
                    questions.setDifficulty(secondary);
                }
                if (questions.getDifficulty().equals(moreDifficultNum)) {
                    questions.setDifficulty(moreDifficult);
                }
                if (questions.getDifficulty().equals(difficultyNum)) {
                    questions.setDifficulty(difficulty);
                }
            }
        }
        return wrapTable(list);
    }



    @PostMapping("/shapelist/{cid}")
    @ResponseBody
    public TableDataInfo shapeList(@PathVariable String cid, MyQuestions myQuestions) {
        myQuestions.setXzsubjectsId(cid);
//        myQuestions.setCreateId(getUser().getUserAttrId());
        myQuestions.setStstatus(2);
//        System.out.println("tid" + myQuestions.getTitleTypeId());
        startPage();
        List<MyQuestions> list = myQuestionsService.list(myQuestions);
        String easy = "容易";
        String easyNum = "1";
        String relativelyEasy = "较易";
        String relativelyEasyNum = "2";
        String secondary = "中等";
        String secondaryNum = "3";
        String moreDifficult = "较难";
        String moreDifficultNum = "4";
        String difficulty = "困难";
        String difficultyNum = "5";
        if (list.size() != 0) {
            for (MyQuestions questions : list) {
                if (questions.getDifficulty().equals(easyNum)) {
                    questions.setDifficulty(easy);
                }
                if (questions.getDifficulty().equals(relativelyEasyNum)) {
                    questions.setDifficulty(relativelyEasy);
                }
                if (questions.getDifficulty().equals(secondaryNum)) {
                    questions.setDifficulty(secondary);
                }
                if (questions.getDifficulty().equals(moreDifficultNum)) {
                    questions.setDifficulty(moreDifficult);
                }
                if (questions.getDifficulty().equals(difficultyNum)) {
                    questions.setDifficulty(difficulty);
                }
            }
        }
        return wrapTable(list);
    }


    @RequestMapping("/getById")
    @ResponseBody
    public  MyQuestions getbyID(@RequestParam String id){
        System.out.println("questionsId:"+id);
        return myQuestionsService.getById(id);
    }

    @Log(title = "删除试题", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            for (String s : idList) {
                String IdStr = myQuestionsService.getById(s).getMyoptionAnswerArr();
                if (IdStr != null) {
                    String[] answerIds = IdStr.split(";");
                    for (String answerId : answerIds) {
                        myOptionAnswerService.removeById(answerId);
                    }
                }
            }
            return toAjax(myQuestionsService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }





    @Log(title = "新增题目", actionType = ActionType.INSERT)

    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data addSave(MyQuestions myQuestions) throws Exception {
        uid = getUser().getUserAttrId();
        String[] optionanswersArr = null;
        String[] optionanswerArr = null;
        String trimstr = "option=";
        String myoaStr = "";
        String optionanswers = myQuestions.getMyoptionAnswerArr();
        List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
        optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr按;进行分割，这里分割成4个

        for (String s : optionanswersArr) {
            optionanswerArr = s.substring(0, s.length() - 1).split(":");//按：进行分割
            String optionone = "";
            if (optionanswerArr[0].contains(trimstr)) {
                System.out.println(optionanswerArr[0]);
                optionone = optionanswerArr[0].substring(7);// 去掉option=开头字符串
            } else {
                optionone = optionanswerArr[0];
            }
            String answerone = optionanswerArr[1];

            try {
                String optionstr = java.net.URLDecoder.decode(optionone, "UTF-8");
                optionAnswer = new MyOptionAnswer();
                Uuid answerId = new Uuid();
                optionAnswer.setCreateBy(getUser().getLoginName());
                optionAnswer.setCreateId(uid);
                optionAnswer.setId(answerId.UId());
                optionAnswer.setStoption(optionstr);
                optionAnswer.setStanswer(answerone);
                toAjax(myOptionAnswerService.save(optionAnswer));
                myoaStr += optionAnswer.getId() + ";";

            } catch (Exception e) {
                System.out.println("乱码解决错误：" + e);
            }
        }
        myQuestions.setCreateBy(getUser().getLoginName());
        myQuestions.setCreateId(getUser().getUserAttrId());
        myQuestions.setQexposed("0");
        myQuestions.setQmaxexposure("10000");
        myQuestions.setStstatus(1);
        myQuestions.setMyoptionAnswerArr(myoaStr);
        System.out.println(myQuestions.getTitleTypeId());
        return toAjax(myQuestionsService.save(myQuestions));

    }



    @Log(title = "分享到共享题库")
    @PostMapping("/toPublic")
    @ResponseBody
    public Data toPrivate(String ids) {
        String ansId;
        uid = getUser().getUserAttrId();
        MyQuestions myQuestions = new MyQuestions();
        PublicQuestions publicQuestions = new PublicQuestions();
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            String ansString = "";
            for (String s : idList) {
                String IdStr = myQuestionsService.getById(s).getMyoptionAnswerArr();
                myQuestions = myQuestionsService.getById(s);
                publicQuestions.setCreateBy(getUser().getLoginName());
                publicQuestions.setCreateId(getUser().getUserAttrId());
                publicQuestions.setDifficulty(myQuestions.getDifficulty());
                publicQuestions.setParsing(myQuestions.getParsing());
                publicQuestions.setQexposed("0");
                publicQuestions.setQmaxexposure("10000");
                publicQuestions.setStstatus(myQuestions.getStstatus());
                publicQuestions.setXzsubjectsId(myQuestions.getXzsubjectsId());
                publicQuestions.setTitle(myQuestions.getTitle());
                publicQuestions.setYear(myQuestions.getYear());
                publicQuestions.setjChapterId(myQuestions.getJchapterId());
                publicQuestions.setTitleTypeId(myQuestions.getPubilcTitleId());
                if (IdStr != null) {
                    String[] answerIds = IdStr.split(";");
                    for (String answerId : answerIds) {
                        MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                        PublicOptionAnswer publicOptionAnswer = new PublicOptionAnswer();
                        myOptionAnswer = myOptionAnswerService.getById(answerId);
                        Uuid uuid1 = new Uuid();
                        ansId = uuid1.UId();
                        publicOptionAnswer.setId(ansId);
                        ansString += ansId + ";";
                        publicOptionAnswer.setCreateBy(getUser().getLoginName());
                        publicOptionAnswer.setCreateId(uid);
                        if (myOptionAnswer.getUpdateTime() != null) {
                            publicOptionAnswer.setUpdateTime(myOptionAnswer.getUpdateTime());
                        }
                        if (myOptionAnswer.getUpdateBy() != null) {
                            publicOptionAnswer.setUpdateBy(myOptionAnswer.getUpdateBy());
                        }
                        if (myOptionAnswer.getStanswer() != null) {
                            publicOptionAnswer.setStanswer(myOptionAnswer.getStanswer());

                        }
                        if (myOptionAnswer.getStoption() != null) {
                            publicOptionAnswer.setStoption(myOptionAnswer.getStoption());

                        }
                        publicOptionAnswerService.save(publicOptionAnswer);
                    }
                }
            }
            publicQuestions.setPublicoptionAnswerArr(ansString);
            return toAjax(publicQuestionsService.save(publicQuestions));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return Data.error();
        }
    }

    /**
     * 章
     *
     * @param subId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findchapterName1")
    public List<Chapter> findchapterName1(String subId) {
        uid = getUser().getUserAttrId();
        Chapter chapter = new Chapter();
        chapter.setCid(subId);
        chapter.setPid("");
        return adminService.chapterList(chapter);
    }

    /**
     * 节
     *
     * @param chaperId
     * @return
     */
    @ResponseBody
    @RequestMapping("/findchapterName2")
    public List<Chapter> findchapterName2(String chaperId) {
        List<Chapter> allSem = new ArrayList<Chapter>();
        if ("0".equals(chaperId)) {
            return allSem;
        }
        Chapter chapter = new Chapter();
        chapter.setPid(chaperId);
        return adminService.chapterList(chapter);
    }

    /**
     * 查所有类型
     *
     * @param cid
     * @return
     */

    @RequestMapping("/findTitleType")
    @ResponseBody
    public List<MyTitleType> findTitleType(String cid) {
        List<MyTitleType> allSem = new ArrayList<MyTitleType>();
        MyTitleType myTitleType = new MyTitleType();
        myTitleType.setCreateId(getUser().getUserAttrId());
        myTitleType.setCid(cid);
        allSem = myTitleTypeService.list(myTitleType);
        return allSem;
    }

    /**
     * 获取题型
     *
     * @param questionIds
     * @param cid
     * @return
     */
    @ApiOperation("题目集映射题型集合")

    @PostMapping("/findTitleTypeByIds")
    @ResponseBody
    public List<Map<String, Object>> findTitleTypeByIds(@RequestBody List<String> questionIds, String cid) {
        List<Map<String, Object>> typeWithId = new ArrayList<>();

        Map<String, MyQuestions> titleMap = new HashMap<>();
        //  取出唯一titleId
        if (questionIds != null) {
            questionIds.forEach(id -> {
                MyQuestions index = myQuestionsService.getById(id);
                titleMap.put(index.getTitleTypeId(), index);
            });
        }

        titleMap.forEach((s, q) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("sort", s);
            map.put("titleTypeId", q.getTitleTypeId());
            map.put("titleTypeName", q.getTitleTypeName());
            //  TODO: 获取缓存的分值

            typeWithId.add(map);
        });

        return typeWithId;
    }

    /**
     * 删除试卷题目关联
     *
     * @return
     */
    @ApiOperation("获取试卷题目")

    @RequestMapping(value = "/paperQuestion/list", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listPaperQuestion(String paperId) {
        startPage();//  对第一句sql进行分页
        List<TestpaperTestquestions> result = paperQuestionsService.getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(paperId, getUser().getUserAttrId());
        return wrapTable(result);
    }

    /**
     * 考试系统
     * @param paperId
     * @return
     */
    @ApiOperation("获取试卷题目")

    @RequestMapping(value = "/paperQuestion/list1", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listPaperOneQuestion(String paperId) {
        startPage();//  对第一句sql进行分页
        List<TestpaperOneTestquestions> result = paperOneQuestionsService.getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(paperId, getUser().getUserAttrId());
        return wrapTable(result);
    }
    /**
     * 删除考试试卷题目关联
     *
     * @return
     */
    @ApiOperation("删除试卷题目")

    @RequestMapping(value = "/paperQuestion/remove1", method = RequestMethod.POST)
    @ResponseBody
    public Data removePaperOneQuestion(String id,String paperId) throws Exception {

        TestpaperOneTestquestions testpaperOneTestquestions = paperOneQuestionsService.getById(id);
        TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperOneTestquestions.getTestQuestionsId());

        //获取删除题的分数
        Integer score = testpaperQuestions.getQuestionScore();
        TestPaperOneList testPaperOneList = testPaperOneListService.getById(paperId);
        Integer testPaperOneListScore = Integer.valueOf(testPaperOneList.getScore());

        testPaperOneList.setScore(String.valueOf(testPaperOneListScore - score));
        testPaperOneListService.update(testPaperOneList);

        return toAjax( paperOneQuestionsService.removeById(id));
    }

    /**
     * 作业系统
     * @param paperId
     * @return
     */
    @ApiOperation("获取作业题目")

    @RequestMapping(value = "/paperQuestion/list2", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo listPaperWorkQuestion(String paperId) {
        startPage();//  对第一句sql进行分页
        List<TestpaperOneTestquestions> result = paperOneQuestionsService.getPaperQuestionsAndTestPaperInfoByPaperWorkIdWithCreateId(paperId, getUser().getUserAttrId());
        return wrapTable(result);
    }
    /**
     * 删除考试试卷题目关联
     *
     * @return
     */
    @ApiOperation("删除试卷题目")

    @RequestMapping(value = "/paperQuestion/remove2", method = RequestMethod.POST)
    @ResponseBody
    public Data removePaperWorkQuestion(String id,String paperId) throws Exception {

        TestpaperOneTestquestions testpaperOneTestquestions = paperOneQuestionsService.getById(id);
        TestpaperQuestions testpaperQuestions = testpaperQuestionsService.getById(testpaperOneTestquestions.getTestQuestionsId());
        //获取删除题的分数
        Integer score = testpaperQuestions.getQuestionScore();
        TestPaperWorkList testPaperWorkList = testPaperWorkListService.getById(paperId);
        Integer testPaperWorkListScore = Integer.valueOf(testPaperWorkList.getScore());
        testPaperWorkList.setScore(String.valueOf(testPaperWorkListScore - score));
        testPaperWorkListService.update(testPaperWorkList);

        return toAjax(paperOneQuestionsService.removeById(id));
    }
    /**
     * 删除试卷题目关联
     *
     * @return
     */
    @ApiOperation("删除试卷题目")
    @RequestMapping(value = "/paperQuestion/remove", method = RequestMethod.POST)
    @ResponseBody
    public Data removePaperQuestion(String id) throws Exception {
        return toAjax(paperQuestionsService.removeById(id));
    }

    @RequestMapping("/stufindTitleType")
    @ResponseBody
    public List<MyTitleType> stufindTitleType(String cid, String tid) {
        List<MyTitleType> allSem = new ArrayList<MyTitleType>();
        MyTitleType myTitleType = new MyTitleType();
        myTitleType.setCreateId(tid);
        myTitleType.setCid(cid);
        allSem = myTitleTypeService.list(myTitleType);
        System.out.println("*********" + allSem);
        return allSem;
    }


    /**
     * 获取某一种题型的数量
     *
     * @param myQuestions
     * @return
     */
    @RequestMapping("/getQuestionSum")
    @ResponseBody
    public List<MyQuestions> getQuestionSum(MyQuestions myQuestions) {
        return myQuestionsService.list(myQuestions);
    }

    /*导入*/
    @Log(title = "题目管理", actionType = ActionType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public Data importData(MultipartFile file, boolean updateSupport, String tid, String cid) throws Exception {
        StringBuilder errorMsg = new StringBuilder();
        ExcelUtil<MyQuestionVO> util = new ExcelUtil<>(MyQuestionVO.class);
        List<MyQuestionVO> myQuestionVOList = util.importExcel(file.getInputStream());
        System.out.println("控制器输出" + myQuestionVOList.get(0).getMyTitleType().getTitleTypeName());
        System.out.println("tid-----------" + tid);
        System.out.println("cid-----------" + cid);
        List<MyTitleType> mtys = myTitleTypeService.findByCidAndTid(cid, tid);
        System.out.println("mtys:"+mtys);
        String dataone = "";
        for (MyQuestionVO q : myQuestionVOList) {
            String titleTypeName = q.getMyTitleType().getTitleTypeName();
            if (q.getDifficulty().trim().equals("")) {
                dataone = dataone + "题目：：" + q.getTitle() + "难度不能为空";
            }
            if (titleTypeName.trim().equals(mtys.get(0).getTitleTypeName().trim())) {/**为单选题**/

                if (q.getMyOptionA().equals("")||q.getMyOptionB().equals("")||q.getMyOptionC().equals("")||q.getMyOptionD().equals("")) {
                    dataone = "题目：：" + q.getTitle() + "。单项题选项不能为空###";
                }
                else if (q.getMyoptionAnswerArr().equals("")) {
                    dataone = dataone + "题目：：" + q.getTitle() + "。单项题至少要有一个答案###";
                }

            }
            if (titleTypeName.trim().equals(mtys.get(2).getTitleTypeName().trim())) {/**为判断题**/
                if (!q.getMyoptionAnswerArr().trim().equals("")) {

                    if (!q.getMyoptionAnswerArr().trim().equals("T") && !q.getMyoptionAnswerArr().trim().equals("F")) {
                        dataone = dataone + "题目：：" + q.getTitle() + "。判断题答案请输入 “T” or “F” ###";
                    }
                } else {
                    dataone = dataone + "题目：：" + q.getTitle() + "。判断题需要填写答案###";
                }
            }
            if (titleTypeName.trim().equals(mtys.get(4).getTitleTypeName().trim())) {/**为多选题与单选题一样**/
                if (q.getMyOptionA().equals("")||q.getMyOptionB().equals("")||q.getMyOptionC().equals("")||q.getMyOptionD().equals("")) {
                    dataone = "题目：：" + q.getTitle() + "。多项题选项不能为空###";
                } else if (q.getMyoptionAnswerArr().equals("")) {
                    dataone = dataone + "题目：：" + q.getTitle() + "。多项题至少要有一个答案###";
                }
            }
            if (!dataone.equals("")) {
                throw new Exception(dataone);
            }
        }
        System.out.println("dataone:"+dataone);
        if (dataone.equals("")) {
            int i = 0;

            for (MyQuestionVO q : myQuestionVOList) {
                String titleTypeId = null;/*题目类型id*/
                String aid = "";/*选项id集*/
                String titleTypeName = q.getMyTitleType().getTitleTypeName();
                if (titleTypeName.trim().equals(mtys.get(0).getTitleTypeName().trim())) {/**为单选题**/
                    titleTypeId = mtys.get(0).getId();
                    if (!q.getMyOptionA().equals("")) {
                        if (q.getMyoptionAnswerArr().trim().equals("A")) {/*判断选项A是否为答案*/
                            Uuid uuid = new Uuid();
                            System.out.println(uuid.UId());
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("A");
                            myOptionAnswer.setStoption(q.getMyOptionA().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid = aid + myOptionAnswer.getId() + ";";
                        } else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionA().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                    if (!q.getMyOptionB().equals("")) {
                        if (q.getMyoptionAnswerArr().trim().equals("B")) {/*判断选项B是否为答案*/

                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("B");
                            myOptionAnswer.setStoption(q.getMyOptionB().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        } else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionB().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                    if (!q.getMyOptionC().equals("")) {
                        if (q.getMyoptionAnswerArr().trim().equals("C")) {/*判断选项C是否为答案*/

                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("C");
                            myOptionAnswer.setStoption(q.getMyOptionC().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        } else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionC().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                    if (!q.getMyOptionD().equals("")) {
                        if (q.getMyoptionAnswerArr().trim().equals("D")) {/*判断选项D是否为答案*/

                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("D");
                            myOptionAnswer.setStoption(q.getMyOptionD().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        } else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionD().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                } else if (titleTypeName.trim().equals(mtys.get(2).getTitleTypeName().trim())) {/**为判断题**/
                    titleTypeId = mtys.get(2).getId();
                    if (!q.getMyoptionAnswerArr().trim().equals("")) {
                        if (q.getMyoptionAnswerArr().trim().equals("T")) {/*判断该判断题目答案是否为“正确”*/
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("T");
                            myOptionAnswer.setStoption("0");
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        } else if (q.getMyoptionAnswerArr().trim().equals("F")) {/*判断该判断题目答案是否为“错误”*/
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("F");
                            myOptionAnswer.setStoption("0");
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                } else if (titleTypeName.trim().equals(mtys.get(4).getTitleTypeName().trim())) {/**为多选题与单选题一样**/
                    titleTypeId = mtys.get(4).getId();
                    String answer = q.getMyoptionAnswerArr().replace(",","");
                    System.out.println("answer"+answer);
                    if (!q.getMyOptionA().equals("")) {
                        if (answer.substring(0,1).equals("A")) {/*判断选项A是否为答案*/
                            Uuid uuid = new Uuid();
                            System.out.println(uuid.UId());
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("A");
                            myOptionAnswer.setStoption(q.getMyOptionA().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid = aid + myOptionAnswer.getId() + ";";
                        } else if(answer.substring(1,2).equals("A")) {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("A");
                            myOptionAnswer.setStoption(q.getMyOptionA().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                        else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionA().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                    if (!q.getMyOptionB().equals("")) {
                        if (answer.substring(0,1).equals("B")) {/*判断选项B是否为答案*/

                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("B");
                            myOptionAnswer.setStoption(q.getMyOptionB().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                        else if(answer.substring(1,2).equals("B")) {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("B");
                            myOptionAnswer.setStoption(q.getMyOptionB().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                        else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionB().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                    if (!q.getMyOptionC().equals("")) {
                        if (answer.substring(0,1).equals("C")) {/*判断选项C是否为答案*/

                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("C");
                            myOptionAnswer.setStoption(q.getMyOptionC().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }else if(answer.substring(1,2).equals("C")) {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("C");
                            myOptionAnswer.setStoption(q.getMyOptionC().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                        else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionC().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                    if (!q.getMyOptionD().equals("")) {
                        if (answer.substring(0,1).equals("D")) {/*判断选项D是否为答案*/

                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("D");
                            myOptionAnswer.setStoption(q.getMyOptionD().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        } else if(answer.substring(1,2).equals("D")) {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("D");
                            myOptionAnswer.setStoption(q.getMyOptionD().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                        else {
                            Uuid uuid = new Uuid();
                            MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                            myOptionAnswer.setId(uuid.UId());
                            myOptionAnswer.setStanswer("0");
                            myOptionAnswer.setStoption(q.getMyOptionD().toString());
                            myOptionAnswerService.save(myOptionAnswer);
                            aid += myOptionAnswer.getId() + ";";
                        }
                    }
                } else {
                    System.out.println("异常异常异常异常异常");
                }
                System.out.println("q.getJchapterId()"+q.getJchapterId());
                MyQuestions myQuestions = new MyQuestions();
                Calendar date = Calendar.getInstance();
                String year = String.valueOf(date.get(Calendar.YEAR));
                myQuestions.setYear(Integer.parseInt(year));
                myQuestions.setCreateBy(getUser().getLoginName());
                Chapter chapter = new Chapter();
                chapter.setCid(cid);
                chapter.setCreateBy(getUser().getLoginName());
                List<Chapter> chapters = adminService.chapterList(chapter);
                List<Chapter> chapterList = new ArrayList<>();
                for (Chapter value : chapters) {
                    if (!value.getPid().equals("0")) {
                        chapterList.add(value);
                    }
                }
                myQuestions.setCreateId(tid);/*教师id*/
                myQuestions.setCreateBy(getUser().getLoginName());
                myQuestions.setTitleTypeId(titleTypeId);/*题型id*/
                myQuestions.setXzsubjectsId(cid);/*课程id*/
                myQuestions.setJchapterId(chapterList.get(0).getId());/*默认了一个章节*/
                myQuestions.setDifficulty(q.getDifficulty());/*题目难度*/
                myQuestions.setTitle(q.getTitle());/*题目正文*/
                myQuestions.setParsing(q.getParsing());/*题目解析*/
                myQuestions.setStstatus(1);
                myQuestions.setQexposed("0");
                myQuestions.setQmaxexposure("1000");
                myQuestions.setMyoptionAnswerArr(aid);
//                System.out.println(tid);
////                System.out.println(cid);
////                System.out.println(q.getDifficulty());
////                System.out.println(q.getMyQuestions().getTitle());
////                System.out.println(q.getParsing());
////                System.out.println(aid);
                myQuestionsService.save(myQuestions);
                i++;
                System.out.println("成功" + i + "个");
            }


            dataone = dataone + "共成功" + i + "条数据。";
        }
        return Data.success();
    }

    /**
     * 下载导入模板
     *
     * @return
     */

    @GetMapping("/importTemplate")
    @ResponseBody
    public Data importTemplate() {
        try {
            ExcelUtil<MyQuestionVO> util = new ExcelUtil<>(MyQuestionVO.class);
            return util.importTemplateExcel("myQuestions");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }


    private Map<String, MyTitleType> transMyTitleType(List<MyTitleType> myTitleTypeList) {
        Map<String, MyTitleType> myTitleTypeMap = new HashMap<>(myTitleTypeList.size());
        myTitleTypeList.forEach(d -> myTitleTypeMap.put(d.getTitleTypeName(), d));
        return myTitleTypeMap;
    }


    private Map<String, MyQuestions> transMyQuestions(List<MyQuestions> myQuestionsList) {
        Map<String, MyQuestions> myQuestionsMap = new HashMap<>(myQuestionsList.size());
        myQuestionsList.forEach(d -> myQuestionsMap.put(d.getTitle(), d));
        return myQuestionsMap;
    }

    private Map<String, MyOptionAnswer> transMyOptionAnswer(List<MyOptionAnswer> myOptionAnswerList) {
        Map<String, MyOptionAnswer> myOptionAnswerMap = new HashMap<>(myOptionAnswerList.size());
        myOptionAnswerList.forEach(d -> myOptionAnswerMap.put(d.getStoption(), d));
        return myOptionAnswerMap;
    }

    private Map<String, Course> transCourse(List<Course> courseList) {
        Map<String, Course> courseMap = new HashMap<>(courseList.size());
        courseList.forEach(d -> courseMap.put(d.getName(), d));
        return courseMap;
    }

    private Map<String, Chapter> transChapter(List<Chapter> chapterList) {
        Map<String, Chapter> chapterMap = new HashMap<>(chapterList.size());
        chapterList.forEach(d -> chapterMap.put(d.getName(), d));
        return chapterMap;
    }

}
