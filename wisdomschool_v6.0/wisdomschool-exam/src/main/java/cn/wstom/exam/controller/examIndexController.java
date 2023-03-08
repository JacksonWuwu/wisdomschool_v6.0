package cn.wstom.exam.controller;

import cn.wstom.exam.entity.TestPaperOne;
import cn.wstom.exam.entity.UserExam;
import cn.wstom.exam.service.TestPaperOneService;
import cn.wstom.exam.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
@Controller
public class examIndexController extends BaseController {
    @Autowired
    private TestPaperOneService testPaperOneService;
    @Autowired
    private UserExamService userExamService;

    @RequestMapping("/exam")
    public String toCourseOneDetail(String tcid, String cid, String testPaperOneId, ModelMap modelMap) {
        TestPaperOne testPaper;
        testPaper = testPaperOneService.getById(testPaperOneId);
        UserExam userExam = userExamService.selectUserExam(testPaperOneId,getUser().getId());
        try {
            if(userExam.getStuStartTime()==null || "".equals(userExam.getStuStartTime())){
                String examStartTime=null;
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startTimeDate = new Date();
                examStartTime = sdf.format(startTimeDate);
                userExam.setStuStartTime(examStartTime);
                userExamService.update(userExam);
                userExam = userExamService.selectUserExam(testPaperOneId,getUser().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelMap.put("testPaper", testPaper);
        modelMap.put("paperId", testPaperOneId);
        modelMap.put("studentId", getUser().getId());
        modelMap.put("tutId",userExam.getId());
        modelMap.put("submit_state",userExam.getSumbitState());
        modelMap.put("stuName", getUser().getLoginName());
        modelMap.put("name", getUser().getUserName());
        modelMap.put("stu_start_time",userExam.getStuStartTime());
        modelMap.put("tcid",tcid);
        modelMap.put("cid",cid);
        modelMap.put("testPaperOneId",testPaperOneId);
        return "front/exercise/chapterExamWork";
    }
}
