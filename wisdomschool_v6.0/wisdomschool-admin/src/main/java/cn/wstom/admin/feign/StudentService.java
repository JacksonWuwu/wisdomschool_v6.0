package cn.wstom.admin.feign;

import cn.wstom.admin.entity.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "wisdomschool-student-service")
public interface StudentService {

    @RequestMapping(value = "/user/{studentId}")
    Student getStudentById(@PathVariable(value = "studentId")String studentId);

    @RequestMapping(value = "/user/studentList")
    List<Student> studentList(@RequestBody Student student);

    @RequestMapping(value = "/user/selectBycid/{cId}")
    List<String> selectBycid(@PathVariable(value = "cId")String cId);

    @RequestMapping(value = "/user/saveStudent")
    Boolean saveStudent(@RequestBody Student student);

    @RequestMapping(value = "/user/updateStudent")
    Boolean updateStudent(@RequestBody Student student);

    @RequestMapping(value = "/user/selectByStudentVos")
    TableDataInfo selectByStudentVos(@RequestBody StudentVo studentVo,
                                     @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                     @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                     @RequestParam(required = false,value = "orderBy")String orderBy);

    @RequestMapping(value = "/user/getStatisticsById/{userId}")
    Statistics getStatisticsById(@PathVariable(value = "userId")String userId);

    @RequestMapping(value = "/user/removeStudent")
    Boolean removeStudent(@RequestBody List<String> sids) ;

    @RequestMapping("/vote/getVoteById/{voteId}")
    Vote getVoteById(@PathVariable("voteId") String voteId);

    @RequestMapping("/vote/getVoteAnswerById/{voteAnswerById}")
    VoteAnswer getVoteAnswerById(@PathVariable("voteAnswerById") String voteAnswerById);

    @RequestMapping("/vote/VoteAnswerList")
    List<VoteAnswer> VoteAnswerList(@RequestBody VoteAnswer voteAnswer);

    @RequestMapping("/vote/VoteList")
    List<Vote> VoteList(@RequestBody Vote vote);

    @RequestMapping("/vote/saveVote")
    Boolean saveVote(@RequestBody Vote vote);

    @RequestMapping("/vote/saveVoteAnswer")
    Boolean saveVoteAnswer(@RequestBody VoteAnswer voteAnswer);

    @RequestMapping("/vote/updateVote")
    Boolean updateVote(@RequestBody Vote vote);

    @RequestMapping("/vote/updateVoteAnswer")
    Boolean updateVoteAnswer(@RequestBody VoteAnswer voteAnswer);

    @RequestMapping(value = "/user/getStudentVoById/{studentVoId}")
    StudentVo getStudentVoById(@PathVariable(value = "studentVoId")String studentVoId);

    @RequestMapping("/user/listbyVoteOptionSubmit")
    List<VoteOptionSubmit>  listbyVoteOptionSubmit(@RequestBody VoteOptionSubmit voteOptionSubmit);

    @RequestMapping("/user/savevoteOptionSubmitsBatch")
    boolean savevoteOptionSubmitsBatch(@RequestBody  List<VoteOptionSubmit> voteOptionSubmits);

    @RequestMapping(value = "/user/removeVotes")
    Boolean removeVotes(@RequestParam("ids") String ids);

}
