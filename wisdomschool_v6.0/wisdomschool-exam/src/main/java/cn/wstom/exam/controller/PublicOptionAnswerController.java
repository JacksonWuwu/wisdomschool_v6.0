package cn.wstom.exam.controller;


import cn.wstom.exam.entity.PublicOptionAnswer;
import cn.wstom.exam.service.PublicOptionAnswerService;
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
@RequestMapping("/school/onlineExam/publicOptionAnswer")
public class PublicOptionAnswerController extends BaseController {
    private String prefix = "school/onlineExam/publicOptionAnswer";
    @Autowired
    private PublicOptionAnswerService publicOptionAnswerService;


    /**
     * 查找答案
     * @param getoptionAnswerArr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestionsAns")
    public PublicOptionAnswer getoptionAnswerArr(String getoptionAnswerArr) {
        String answerId = getoptionAnswerArr;
        PublicOptionAnswer myoa = new PublicOptionAnswer();
        myoa = publicOptionAnswerService.getById(answerId);
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
                System.out.println("myoaArr[i]).getStoption():"+myoaArr[i]);
                questionsAnsList.add(publicOptionAnswerService.getById( myoaArr[i]).getStoption());
            }
        }
        return questionsAnsList;
    }

}
