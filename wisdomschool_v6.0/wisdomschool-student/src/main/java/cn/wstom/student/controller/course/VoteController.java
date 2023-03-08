package cn.wstom.student.controller.course;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.TeacherCourse;
import cn.wstom.student.entity.Vote;
import cn.wstom.student.entity.VoteAnswer;
import cn.wstom.student.entity.VoteOptionSubmit;
import cn.wstom.student.service.VoteAnswerService;
import cn.wstom.student.service.VoteService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/vote")
public class VoteController extends BaseController {


    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteAnswerService voteAnswerService;


    @RequestMapping("/getVoteById/{voteId}")
    @ResponseBody
    Vote getVoteById(@PathVariable("voteId") String voteId){
        return voteService.getById(voteId);
    }

    @RequestMapping("/getVoteAnswerById/{voteAnswerById}")
    @ResponseBody
    VoteAnswer getVoteAnswerById(@PathVariable("voteAnswerById") String voteAnswerById){
        return voteAnswerService.getById(voteAnswerById);
    }
    @RequestMapping("/saveVote")
    @ResponseBody
    Boolean saveVote(@RequestBody Vote vote) throws Exception {
        return voteService.save(vote);
    }

    @RequestMapping("/saveVoteAnswer")
    @ResponseBody
    Boolean saveVoteAnswer(@RequestBody VoteAnswer voteAnswer) throws Exception {
        return voteAnswerService.save(voteAnswer);
    }

    @RequestMapping("/updateVote")
    @ResponseBody
    Boolean updateVote(@RequestBody Vote vote) throws Exception {
        return voteService.update(vote);
    }

    @RequestMapping("/updateVoteAnswer")
    @ResponseBody
    Boolean updateVoteAnswer(@RequestBody VoteAnswer voteAnswer) throws Exception {
        return voteAnswerService.update(voteAnswer);
    }

    @RequestMapping("/VoteAnswerList")
    @ResponseBody
    List<VoteAnswer> VoteAnswerList(@RequestBody VoteAnswer voteAnswer){
        return voteAnswerService.list(voteAnswer);
    }


    @RequestMapping("/VoteList")
    @ResponseBody
    List<Vote> VoteList(@RequestBody Vote vote){
        return voteService.list(vote);
    }
    @RequestMapping("/listbyVoteOptionSubmit")
    @ResponseBody
    List<VoteOptionSubmit>  listbyVoteOptionSubmit(@RequestBody VoteOptionSubmit voteOptionSubmit){
        return voteService.listbyVoteOptionSubmit(voteOptionSubmit);
    }

    @RequestMapping("/savevoteOptionSubmitsBatch")
    @ResponseBody
    boolean savevoteOptionSubmitsBatch(@RequestBody  List<VoteOptionSubmit> voteOptionSubmits){
        return voteService.savevoteOptionSubmitsBatch(voteOptionSubmits);
    }

    @RequestMapping(value = "/removeVotes")
    @ResponseBody
    Boolean removeVotes(@RequestParam("ids") String ids) throws Exception {
        return voteService.removeById(ids);
    }

    /**
     *
     * 课程列表获取
     * @return
     */
    @ApiOperation("学生课程列表")
    @GetMapping(value = "/list")
    public Data list(@RequestParam(required = false, defaultValue = "0", value = "tcid") String tcid) {
        String studentId = getUser().getUserAttrId();
        Vote vote =new Vote();
        TeacherCourse teacherCourse= adminService.getTeacherCourseById(tcid);
        vote.setXzsubjectsId(teacherCourse.getCid());
        vote.setStstatus(1);
        List<Vote> voteList=voteService.list(vote);
        System.out.println("====voteList--List===="+voteList);
        return Data.success(voteList);
    }


    /**
     * 投票内容获取
     * @param voteid
     * @return
     */
    @ApiOperation("课程内容")
    @GetMapping("/detail/{voteid}")
    public Data detail(@PathVariable String voteid) {
        List<Vote> list=new ArrayList<>();
        String studentId = getUser().getUserAttrId();
        VoteOptionSubmit voteOptionSubmit=new VoteOptionSubmit();
        voteOptionSubmit.setStuid(studentId);
        voteOptionSubmit.setVoteId(Integer.valueOf(voteid));
        System.out.println(studentId+voteid);
        VoteOptionSubmit voteOptionSubmit1=voteService.getonebyVoteOptionSubmit(voteOptionSubmit);

        System.out.println("voteOptionSubmit1"+voteOptionSubmit1);
        Vote vote =voteService.getById(voteid);
        String[] anList = vote.getMyoptionAnswerArr().split(";");// 题目选项
        VoteAnswer voteAnswer=new VoteAnswer();
        List<VoteAnswer > voteAnswers = new ArrayList<VoteAnswer>();
        for (String anAnList : anList) {
            voteAnswer = voteAnswerService.getById(anAnList);
            if (voteAnswer != null) {
                voteAnswers.add(voteAnswer);
            }
        }
        vote.setVoteAnswerList(voteAnswers);
        vote.setStstatus(voteOptionSubmit1.getStstatus());
        list.add(vote);
        System.out.println("vote"+vote);
        return Data.success(list);
    }


    @ApiOperation("章节试题提交")
    @RequestMapping(method = RequestMethod.POST, value = "/submit")
    @ResponseBody
    public Data saveChapterTestAnswer(@RequestBody List<VoteOptionSubmit> options) {
        VoteOptionSubmit voteOptionSubmit=new VoteOptionSubmit();
        options.forEach(o -> {
            voteOptionSubmit.setStuid(getUser().getUserAttrId());
            voteOptionSubmit.setStuAnswer(o.getStuAnswer());
            voteOptionSubmit.setVoteId(o.getVoteId());
            voteOptionSubmit.setStuOptionAnswer(o.getStuOptionAnswer());
        });
        String[] anList = voteOptionSubmit.getStuAnswer().split(";");// 题目选项
        for (String anAnList : anList) {
            if (!"0".equals(anAnList)) {
                voteOptionSubmit.setStuAnswerVo(anAnList);
                break;
            }
        }
        voteOptionSubmit.setOptionSize(anList.length);
        voteOptionSubmit.setStstatus(1);
        System.out.println("voteOptionSubmit=="+voteOptionSubmit);
        return toAjax(voteService.updataVoteStudent(voteOptionSubmit));
    }





}
