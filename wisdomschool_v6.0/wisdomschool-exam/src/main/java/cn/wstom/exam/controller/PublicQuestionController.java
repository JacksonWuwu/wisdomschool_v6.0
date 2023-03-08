package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Convert;
import cn.wstom.exam.utils.Uuid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 题库中心
 *
 * @author hzh
 */
@Controller
@RequestMapping("/school/onlineExam/publicQuestion")
public class PublicQuestionController extends BaseController {
    private String prefix = "school/onlineExam/publicQuestion";

    @Autowired
    private PublicQuestionsService publicQuestionsService;
    @Autowired
    private MyQuestionsService myQuestionsService;
    @Autowired
    private PublicOptionAnswerService publicOptionAnswerService;

    @Autowired
    private MyOptionAnswerService myOptionAnswerService;


    private PublicOptionAnswer publicOptionAnswer;

    @Autowired
    private MyTitleTypeService myTitleTypeService;


    @GetMapping("/{cid}")
    public String toPublicList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/list";
    }

    /**
     * 题库页面
     *
     * @param publicQuestions
     * @return
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, PublicQuestions publicQuestions) {
        publicQuestions.setXzsubjectsId(cid);
        startPage();
        List<PublicQuestions> list = publicQuestionsService.list(publicQuestions);
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
        if (list != null) {
            for (PublicQuestions questions : list) {
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


    /**
     * 添加
     */
    @Log(title = "新增题目", actionType = ActionType.INSERT)
    @PostMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data addSave(PublicQuestions publicQuestions) throws Exception {
        String[] optionanswersArr = null;
        String[] optionanswerArr = null;
        String optionAnswerIDList = "";
        String trimstr = "option=";
        String myoaStr = "";
        String optionanswers = publicQuestions.getPublicoptionAnswerArr();
        List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
        optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr按;进行分割，这里分割成4个
        for (String s : optionanswersArr) {
            optionanswerArr = s.substring(0, s.length() - 1).split(":");//按：进行分割
            String optionone = "";
            if (optionanswerArr[0].contains(trimstr)) {
                optionone = optionanswerArr[0].substring(7);// 去掉option=开头字符串
            } else {
                optionone = optionanswerArr[0];
            }
            String answerone = optionanswerArr[1];

            try {
                String optionstr = java.net.URLDecoder.decode(optionone, "UTF-8");
                publicOptionAnswer = new PublicOptionAnswer();
                Uuid answerId = new Uuid();
                publicOptionAnswer.setCreateBy(getUser().getLoginName());
                publicOptionAnswer.setCreateId(getUserId());
                publicOptionAnswer.setId(answerId.UId());
                publicOptionAnswer.setStoption(optionstr);
                publicOptionAnswer.setStanswer(answerone);
                toAjax(publicOptionAnswerService.save(publicOptionAnswer));
                myoaStr += publicOptionAnswer.getId() + ";";

            } catch (Exception e) {
                System.out.println("乱码解决错误：" + e);
            }
        }
        publicQuestions.setCreateBy(getUser().getLoginName());
        publicQuestions.setCreateId(getUserId());
        publicQuestions.setQexposed("0");
        publicQuestions.setQmaxexposure("10000");
        publicQuestions.setStstatus(1);
        publicQuestions.setPublicoptionAnswerArr(myoaStr);
        return toAjax(publicQuestionsService.save(publicQuestions));

    }


    /**
     * 跳转添加页面
     *
     * @return
     */

    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 答案数组
     * @param getoptionAnswerArr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestionsAnsList")
    public List<String> getQuestionsAnsList(String getoptionAnswerArr) {
        String myoastr = getoptionAnswerArr;
        List<String> questionsAnsList = new ArrayList<String>();
        if (myoastr != null && !"".equals(myoastr)) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            for (String s : myoaArr) {
//                System.out.println(myOptionAnswerService.getById( myoaArr[i]).getStoption()+"????????");
                questionsAnsList.add(publicOptionAnswerService.getById(s).getStoption());
            }
        }
        return questionsAnsList;

    }

    /**
     * 查找答案
     * @param getoptionAnswerArr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestionsAns")
    public PublicOptionAnswer getoptionAnswerArr(String getoptionAnswerArr) {
        PublicOptionAnswer publicOptionAnswer = publicOptionAnswerService.getById(getoptionAnswerArr);

        return publicOptionAnswer;
    }

    /**
     * 编辑
     *
     * @param id
     * @param mmap
     * @return
     */

    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        PublicQuestions publicQuestions = publicQuestionsService.getById(id);
        String uid = getUser().getUserAttrId();
        MyTitleType myTitleType = new MyTitleType();
        myTitleType.setCreateId(uid);
        myTitleType.setPublicTitleId(publicQuestionsService.getTypeIdById(id));
        List<MyTitleType> list = myTitleTypeService.list(myTitleType);
        publicQuestions.setTitleTypeId(list.get(0).getId());
        mmap.put("publicQuestion", publicQuestions);
        return prefix + "/edit";
    }



    @Log(title = "删除试题", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {

        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            for (String s : idList) {
                String IdStr = publicQuestionsService.getById(s).getPublicoptionAnswerArr();
                if (IdStr != null) {
                    String[] answerIds = IdStr.split(";");
                    for (String answerId : answerIds) {
                        publicOptionAnswerService.removeById(answerId);
                    }
                }
            }
            return toAjax(publicQuestionsService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }



    @Log(title = "移入我的题库", actionType = ActionType.DELETE)
    @PostMapping("/toPrivate")
    @ResponseBody
    public Data toPrivate(String ids) {
        System.out.println("***********"+ids);
        MyQuestions myQuestions = new MyQuestions();
        String ansString = "";
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            for (int i = 0; i < idList.size(); i++) {
                String IdStr = publicQuestionsService.getById(idList.get(i)).getPublicoptionAnswerArr();
                PublicQuestions publicQuestions = publicQuestionsService.getById(idList.get(i));
                String uid = getUser().getUserAttrId();
                myQuestions.setCreateId(uid);
                myQuestions.setCreateBy(getUser().getLoginName());
                myQuestions.setDifficulty(publicQuestions.getDifficulty());
                myQuestions.setParsing(publicQuestions.getParsing());
                myQuestions.setQexposed("0");
                myQuestions.setQmaxexposure("10000");
                myQuestions.setStstatus(publicQuestions.getStstatus());
                myQuestions.setTitle(publicQuestions.getTitle());
                myQuestions.setYear(publicQuestions.getYear());
//                MyQuestions myQuestions1 = new MyQuestions();
//                myQuestions1.setTemplateNum(publicQuestions.getTemplateNum());
                MyTitleType myTitleType = new MyTitleType();
                myTitleType.setCreateId(uid);
                myTitleType.setPublicTitleId(publicQuestionsService.getTypeIdById(publicQuestions.getId()));
                List<MyTitleType> list = myTitleTypeService.list(myTitleType);
//                List<MyQuestions> myQuestionsList=myQuestionsService.list(myQuestions1);
                if (list.size()==0){
                    return Data.error("题库没有相应的题型");
                }else{
                    for (int j = 0; j < list.size(); j++) {
                        myQuestions.setTitleTypeId(list.get(i).getId());
                    }

                }
                myQuestions.setXzsubjectsId(publicQuestions.getXzsubjectsId());
                myQuestions.setJchapterId(publicQuestions.getjChapterId());
                if (IdStr != null) {
                    String[] answerIds = IdStr.split(";");
                    for (String answerId : answerIds) {
                        MyOptionAnswer myOptionAnswer = new MyOptionAnswer();
                        PublicOptionAnswer publicOptionAnswer = new PublicOptionAnswer();
                        publicOptionAnswer = publicOptionAnswerService.getById(answerId);
                        Uuid uuid1 = new Uuid();
                        String newID = uuid1.UId();
                        myOptionAnswer.setId(newID);
                        myOptionAnswer.setCreateBy(getUser().getLoginName());
                        String uid1 = getUser().getUserAttrId();
                        myOptionAnswer.setCreateId(uid1);
                        ansString += newID + ";";
                        if (publicOptionAnswer.getUpdateTime() != null) {
                            myOptionAnswer.setUpdateTime(publicOptionAnswer.getUpdateTime());

                        }
                        if (publicOptionAnswer.getUpdateBy() != null) {
                            myOptionAnswer.setUpdateBy(publicOptionAnswer.getUpdateBy());

                        }
                        if (publicOptionAnswer.getStanswer() != null) {
                            myOptionAnswer.setStanswer(publicOptionAnswer.getStanswer());

                        }
                        if (publicOptionAnswer.getStoption() != null) {
                            myOptionAnswer.setStoption(publicOptionAnswer.getStoption());

                        }

                        myOptionAnswerService.save(myOptionAnswer);
                    }
                }
            }
            myQuestions.setMyoptionAnswerArr(ansString);
                return toAjax(myQuestionsService.save(myQuestions));

        } catch (Exception e) {
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return Data.error();
        }
    }

    @Log(title = "修改题目", actionType = ActionType.INSERT)
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data addUpdate(PublicQuestions publicQuestions) throws Exception {
        String  uid = getUser().getUserAttrId();
        String[] optionanswersArr = null;
        String[] optionanswerArr = null;
        String optionAnswerIDList = "";
        String trimstr = "option=";
        String myoaStr = "";
        String optionanswers = publicQuestions.getPublicoptionAnswerArrContent();
        List<PublicOptionAnswer> myoalist = new ArrayList<PublicOptionAnswer>();
        optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr按;进行分割，这里分割成4个
        myoaStr = publicQuestions.getPublicoptionAnswerArr();
        if (myoaStr != null && !"".equals(myoaStr)) {
            String[] myoaArr = myoaStr.substring(0, myoaStr.length() - 1).split(";");
            for (String s : myoaArr) {
                myoalist.add(publicOptionAnswerService.getById(s));
            }
        }
        if (optionanswers != null && !optionanswers.equals("")) {
            optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");
            for (int i = 0; i < optionanswersArr.length; i++) {
                optionanswerArr = optionanswersArr[i].substring(0,
                        optionanswersArr[i].length() - 1).split(":");
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
                    if (myoalist.size() != 0 && !myoalist.isEmpty()) {
                        publicOptionAnswer = myoalist.get(i);
                        publicOptionAnswer.setUpdateBy(getUser().getLoginName());
                        publicOptionAnswer.setUpdateId(uid);
                        publicOptionAnswer.setStoption(optionstr);
                        publicOptionAnswer.setStanswer(answerone);
                        publicOptionAnswer.getStoption();
                        publicOptionAnswer.getStanswer();
                        this.publicOptionAnswerService.update(publicOptionAnswer);
                        myoalist.remove(i);
                    } else {
                        publicOptionAnswer = new PublicOptionAnswer();
                        Uuid answerId = new Uuid();
                        publicOptionAnswer.setUpdateBy(getUser().getLoginName());
                        publicOptionAnswer.setUpdateId(uid);
                        publicOptionAnswer.setId(answerId.UId());
                        publicOptionAnswer.setStoption(optionstr);
                        publicOptionAnswer.setStanswer(answerone);
                        this.publicOptionAnswerService.save(publicOptionAnswer);
                        myoaStr += publicOptionAnswer.getId() + ";";
                    }
                } catch (Exception e) {
                }
            }
        }
        publicQuestions.setPublicoptionAnswerArr(myoaStr);
        publicQuestions.setCreateId(uid);
        publicQuestions.setCreateBy(getUser().getLoginName());
        return toAjax(publicQuestionsService.update(publicQuestions));

    }

}
