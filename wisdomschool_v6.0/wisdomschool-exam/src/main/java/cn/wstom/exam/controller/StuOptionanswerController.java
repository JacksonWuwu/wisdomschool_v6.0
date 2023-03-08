package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.service.StuOptionExamanswerService;
import cn.wstom.exam.service.StuOptionanswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 学生考试答案 信息操作处理
 *
 * @author hzh
 * @date 20190304
 */
@Controller
@RequestMapping("/school/onlineExam/stuOptionanswer")
public class StuOptionanswerController extends BaseController {
    private String prefix = "/school/onlineExam/stuOptionanswer";

    @Autowired
    private StuOptionanswerService stuOptionanswerService;

    @Autowired
    private StuOptionExamanswerService stuOptionExamanswerService;

    @GetMapping()
    public String toList() {
        return prefix + "list";
    }

    /**
     * 查询学生考试答案列表
     */

    @RequestMapping("/saveOptionAnswersByUserId/{userId}")
    @ResponseBody
    int saveOptionAnswersByUserId(List<OptionSubmitVo> options, @PathVariable("userId")String userId){
       return stuOptionanswerService.saveOptionAnswersByUserId(options,userId);
    }
    @RequestMapping("/saveOptionExamAnswersByUserId/{userId}")
    @ResponseBody
    int saveOptionExamAnswersByUserId(List<OptionExamSubmitVo> options, @PathVariable("userId")String userId){
        return stuOptionExamanswerService.saveOptionAnswersByUserId(options,userId);
    }

    @RequestMapping("/updateListAndTotalScore/{testPaperOneId}/{userId}")
    @ResponseBody
    int updateListAndTotalScore(List<StuOptionExamanswer> optionanswers,
                                @PathVariable("testPaperOneId")String testPaperOneId,
                                @PathVariable("userId")String userId){
        return stuOptionExamanswerService.updateListAndTotalScore(optionanswers,testPaperOneId,userId);
    }

    @RequestMapping("/selectByStudentAnswer")
    @ResponseBody
    List<StuOptionExamanswer> selectByStudentAnswer(@RequestParam("paperId")String paperId,@RequestParam("userId")String userId){
        return stuOptionExamanswerService.selectByStudentAnswer(paperId,userId);
    }



    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(StuOptionanswer stuOptionanswer) {
        startPage();
        List<StuOptionanswer> list = stuOptionanswerService.list(stuOptionanswer);
        return wrapTable(list);
    }

    /**
     * 新增学生考试答案
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 新增保存学生考试答案
     */

    @Log(title = "学生考试答案", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(StuOptionanswer stuOptionanswer) throws Exception {
        return toAjax(stuOptionanswerService.save(stuOptionanswer));
    }

    /**
     * 修改学生考试答案
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        StuOptionanswer stuOptionanswer = stuOptionanswerService.getById(id);
        mmap.put("stuOptionanswer", stuOptionanswer);
        return prefix + "/edit";
    }

    /**
     * 修改保存学生考试答案
     */

    @Log(title = "学生考试答案", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(StuOptionanswer stuOptionanswer) throws Exception {
        return toAjax(stuOptionanswerService.update(stuOptionanswer));
    }

    /**
     * 删除学生考试答案
     */

    @Log(title = "学生考试答案", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(stuOptionanswerService.removeById(ids));
    }
}
