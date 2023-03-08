package cn.wstom.exam.controller;


import cn.wstom.exam.entity.MyOptionAnswer;
import cn.wstom.exam.service.MyOptionAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 试题答案
 *
 * @author hzh
 */
@Controller
@RequestMapping("/school/onlineExam/myOptionAnswer")
public class MyOptionAnswerController extends BaseController {
    private String prefix = "school/onlineExam/myOptionAnswer";

    @Autowired
    private MyOptionAnswerService myOptionAnswerService;

    /**
     * 查找答案
     * @param getoptionAnswerArr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestionsAns")
    public MyOptionAnswer getoptionAnswerArr(String getoptionAnswerArr) {
        String answerId = getoptionAnswerArr;
        MyOptionAnswer myoa = new MyOptionAnswer();
        myoa = myOptionAnswerService.getById(answerId);
        System.out.println("stans" + myoa.getStanswer());
        System.out.println("option" + myoa.getStoption());
        return myoa;
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
        if (myoastr != null && !myoastr.equals("")) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            for (int i = 0; i < myoaArr.length; i++) {
                System.out.println(myOptionAnswerService.getById( myoaArr[i]).getStoption()+"????????");
                questionsAnsList.add(myOptionAnswerService.getById( myoaArr[i]).getStoption());
            }
        }
        return questionsAnsList;
    }
}
