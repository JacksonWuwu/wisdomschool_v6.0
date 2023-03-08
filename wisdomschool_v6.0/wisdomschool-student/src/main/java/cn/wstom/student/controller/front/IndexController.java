package cn.wstom.student.controller.front;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import cn.wstom.student.feign.ExamService;
import cn.wstom.student.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dws
 * @date 2019/02/23
 */
@Controller
public class IndexController extends BaseController {
    private final String prefix = "/front";

    @Autowired
    private ExamService examService;

    @Autowired
    private ArticleService articleService;

    @ApiOperation("首页数据")
    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public Data home() {
        startPage();
        List<Banner> bannerList = adminService.bannerList(new Banner());

        Map<String, Object> map = new HashMap<>();
        map.put("banner", bannerList);

        startPage();
        List<Article> articles = articleService.list(new Article());
        map.put("articles", articles);

        return Data.success(map);
    }

    @RequestMapping(value =  "/studentIndex")
    public String index(ModelMap model, HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", "newest"); //newest
        int pageNum = ServletRequestUtils.getIntParameter(request, "pn", 1); //1
        model.put("order", order);
        model.put("pn", pageNum);
        model.put("sysUser", getUser());
        return prefix + "/index";
    }

    @RequestMapping(value = {"/course"})
    public String index2(ModelMap model, HttpServletRequest request) {
        String order = ServletRequestUtils.getStringParameter(request, "order", "newest");
        int pageNum = ServletRequestUtils.getIntParameter(request, "pn", 1);
        model.put("order", order);
        model.put("pn", pageNum);
        model.put("stuid", getUser().getUserAttrId());
        return prefix + "/index";

    }

//    public String index3(ModelMap model, HttpServletRequest request) {
//        String order = ServletRequestUtils.getStringParameter(request, "order", "newest");
//        int pageNum = ServletRequestUtils.getIntParameter(request, "pn", 1);
//
//        SysUser user = sysUserService.getById(getUserId());
//
//
//        model.put("order", order);
//        model.put("pn", pageNum);
//        model.put("stuid", getUser().getUserAttrId());
//        return prefix + "/exam_index";
//    }

    @RequestMapping(value = {"/onlineStudent"})
    public String index4(String cid,String tid,String paperId, UserExam userExam2,ModelMap model, HttpServletRequest request) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = null;
        //TeacherCourse teacherCourse = teacherCourseService.selectId(cid,tid);
        TestPaperOne testPaperOne = examService.getTestPaperOneById(paperId);
        UserExam userExam = new UserExam();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(tid);
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperOneId(paperId);
        List<UserExam> list = examService.findStuExamPaperOne(userExam);
        String percentage = "0%";
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (UserExam exam : list) {
                List<StuOptionExamanswer> stuOptionExamanswer = examService.selectByStudentAnswer(paperId, exam.getUserId());
                List<TestpaperOneTestquestions> testpaperOneTestquestions = examService.getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(paperId, tid);
                int questionNum = testpaperOneTestquestions.size();
                int answerNum = stuOptionExamanswer.size();
                percentage = String.format("%.2f", answerNum / (questionNum * 1.0) * 100) + "%";
                exam.setPercentage(percentage);
                if (exam.getMadeScore().equals(doneFlag)) {
                    exam.setMadeScore(done);
                } else {
                    exam.setMadeScore(none);
                }
                if (exam.getSumbitState().equals(noneFlag)) {
                    exam.setSumbitState(none);
                } else {
                    exam.setSumbitState(done);
                }
                Date now = new Date();
                long diff = now.getTime() - exam.getStartTimestamp().getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                time = String.valueOf(minutes);
                exam.setTestTime(time);
            }
        }
        model.put("list",list);
        model.put("cid",cid);
        model.put("tid",tid);
        model.put("paperId",paperId);
        model.put("testPaperOne",testPaperOne);
        return prefix + "/online_student";
    }
    @RequestMapping(value = {"/infoStudent"})
    public String index5(String cid,String tid,String paperId, UserExam userExam2,ModelMap model, HttpServletRequest request) {
        int LoginPeople = 0;
        int NoLoginPeople = 0;
        int OffLinePeople = 0;
        int submitPeople = 0;
        int scorePeople = 0;
        //TeacherCourse teacherCourse = teacherCourseService.selectId(cid,tid);
        TestPaperOne testPaperOne = examService.getTestPaperOneById(paperId);
        UserExam userExam = new UserExam();
        userExam.setcId(cid);
        userExam.setType("2");
        userExam.setCreateId(tid);
        userExam.setStuName(userExam2.getStuName());
        userExam.setTgId(userExam2.getTgId());
        userExam.setTcId(userExam2.getTcId());
        userExam.setTestPaperOneId(paperId);
        UserExam userExam1 = new UserExam();
        userExam1.setcId(cid);
        userExam1.setCreateId(tid);
        userExam1.setTestPaperOneId(paperId);
        List<UserExam> list = examService.findStuExamPaperTwo(userExam);
        List<UserExam> listOne = examService.findStuExamPaperOne(userExam);//登录人数
        List<UserExam> listTwo = examService.selectUserExamListBase(userExam1);//总数
        List<UserExam> listThree = new ArrayList<>();
        List<UserExam> listFour = new ArrayList<>();
        List<UserExam> listOffLine = new ArrayList<>();
        if (list.size() != 0) {
            final String done = "是";
            final String doneFlag = "1";
            final String none = "否";
            final String noneFlag = "0";
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getMadeScore().equals(doneFlag)) {
                    scorePeople++;
                    list.get(i).setMadeScore(done);
                } else {
                    list.get(i).setMadeScore(none);
                }
                if (list.get(i).getSumbitState().equals(noneFlag)) {
                    list.get(i).setSumbitState(none);
                } else {
                    list.get(i).setSumbitState(done);

                }
                if (list.get(i).getSumbitState().equals("是")){
                    submitPeople++;
                    listThree.add(list.get(i));
                }

                if (list.get(i).getStuStartTime()==null){
                    NoLoginPeople++;
                    listFour.add(list.get(i));
                }
                if (list.get(i).getSumScore().equals("1")&&list.get(i).getSumbitState().equals("0")){
                    OffLinePeople++;
                    listOffLine.add(list.get(i));
                }
            }
        }
        if (listOne.size()==0){
            LoginPeople = 0;
        }else{
            LoginPeople = listOne.size();
        }
        model.put("list",list);
        model.put("listThree",listThree);//交卷
        model.put("listFour",listFour);//缺考
        model.put("listOffLine",listOffLine);//掉线人
        model.put("LoginPeople",LoginPeople);
        model.put("NoLoginPeople",NoLoginPeople);//缺考人数
        model.put("OffLinePeople",OffLinePeople);//掉线人数
        model.put("PeopleSum",listTwo.size());
        model.put("submitPeople",submitPeople);//交卷人数
        model.put("scorePeople",scorePeople);//评分人数
        model.put("testPaperOne",testPaperOne);
        model.put("cid",cid);
        model.put("tid",tid);
        model.put("paperId",paperId);
        return prefix + "/info_student";
    }
}
