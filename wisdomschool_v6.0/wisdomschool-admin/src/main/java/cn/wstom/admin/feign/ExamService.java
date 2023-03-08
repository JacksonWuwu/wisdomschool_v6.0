package cn.wstom.admin.feign;

import cn.wstom.admin.config.FeignHttpsConfig;
import cn.wstom.admin.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "https://wisdomschool-exam-service",configuration = {FeignHttpsConfig.class})
public interface ExamService {
    @RequestMapping(value = "/school/onlineExam/testPaper/testPaperList")
    List<TestPaper> testPaperList(@RequestBody TestPaper testPaper,
                                  @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                  @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                  @RequestParam(required = false,value = "orderBy")String orderBy);

    @RequestMapping(value = "/school/onlineExam/testPaperOne/testPaperOneList")
    List<TestPaperOne> testPaperOneList(@RequestBody TestPaperOne testPaperOne);

    @RequestMapping("/school/onlineExam/userExam/selectUserExamListBase")
    List<UserExam> selectUserExamListBase(@RequestBody UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/userExamList")
    List<Map<String, Object>>userExamList(@RequestBody UserExam userExam);

    @RequestMapping("/school/onlineExam/userTest/selectUserTestListBase")
    List<UserTest> selectUserTestListBase(@RequestBody UserTest userTest);

    @RequestMapping("/school/onlineExam/myQuestion/myQuestionsList")
    List<MyQuestions> myQuestionsList(@RequestBody MyQuestions myQuestions);

    @RequestMapping(value = "/school/onlineExam/myOptionAnswer/{myOptionAnswerId}")
    MyOptionAnswer myOptionAnswerById(@PathVariable("myOptionAnswerId") String myOptionAnswerId);

    @DeleteMapping("/school/onlineExam/userTest/userTestRemoveByIds")
    Boolean userTestRemoveByIds(@RequestBody List<String> ids);

    @RequestMapping("/module/testpaperOneTestquestions/getQuestionsAndOptionsByPaperIdWithUserId/{paperId}/{studentId}")
    List<TestpaperQuestions> getQuestionsAndOptionsByPaperIdWithUserId(@PathVariable("paperId")String paperId,
                                                                       @PathVariable("studentId")String studentId);

    @RequestMapping("/school/onlineExam/stuOptionanswer/updateListAndTotalScore/{testPaperOneId}/{userId}")
    int updateListAndTotalScore(@RequestBody List<StuOptionExamanswer> optionanswers,
                                @PathVariable("testPaperOneId")String testPaperOneId,
                                @PathVariable("userId")String userId);

    @RequestMapping("/module/testpaperQuestions/getQuestionsByPaperIdWithUserId/{paperId}/{userId}")
    List<TestpaperQuestions> getQuestionsByPaperIdWithUserId ( @PathVariable("paperId")String paperId,
                                                               @PathVariable("userId")String userId);

    @RequestMapping("/module/testpaperOneTestquestions/getQuestionsByPaperOneIdWithUserId/{paperId}/{userId}")
    List<TestpaperQuestions> getQuestionsByPaperOneIdWithUserId (@PathVariable("paperId")String paperId,
                                                                 @PathVariable("userId")String userId);

    @RequestMapping("/school/onlineExam/stuOptionanswer/saveOptionAnswersByUserId/{userId}")
    int saveOptionAnswersByUserId(@RequestBody List<OptionSubmitVo> options, @PathVariable("userId")String userId);

    @RequestMapping("/school/onlineExam/stuOptionanswer/saveOptionExamAnswersByUserId/{userId}")
    int saveOptionExamAnswersByUserId(@RequestBody List<OptionExamSubmitVo> options, @PathVariable("userId")String userId);

    @RequestMapping(value = "/school/onlineExam/testPaper/getTestPaperById/{testPaperId}")
    TestPaper getTestPaperById(@PathVariable("testPaperId") String testPaperId);

    @RequestMapping(value = "/school/onlineExam/userTest/getTcoExamPaper")
    List<UserTest>  getTcoExamPaperByUserTest(@RequestBody UserTest userTest,
                                              @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                              @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                              @RequestParam(required = false,value = "orderBy")String orderBy);
    @RequestMapping("/school/onlineExam/userPaperWork/getTcoExamPaper")
    List<UserPaperWork> getTcoExamPaperByUserPaperWork(@RequestBody UserPaperWork userPaperWork);

    @RequestMapping("/school/onlineExam/userPaperWork/getTestPaperWorkById/{testPaperWorkId}")
    TestPaperWork getTestPaperWorkById(@PathVariable("testPaperWorkId") String testPaperWorkId);

    @RequestMapping("/school/onlineExam/testPaperOne/getTestPaperOneById/{testPaperOneId}")
    TestPaperOne getTestPaperOneById(@PathVariable("testPaperOneId") String testPaperOneId);

    @RequestMapping("/school/onlineExam/userTest/userTestList")
    List<UserTest> userTestList(@RequestBody UserTest userTest);

    @RequestMapping("/school/onlineExam/userExam/selectUserExam")
    UserExam selectUserExam(@RequestParam("testPaperOneId") String testPaperOneId,@RequestParam("userId")String userId);

    @RequestMapping("/school/onlineExam/userExam/updateUserExam")
    Boolean updateUserExam(@RequestBody  UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/findStuExamPaperTwo")
    List<UserExam> findStuExamPaperTwo(@RequestBody UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/findStuExamPaperOne")
    List<UserExam> findStuExamPaperOne(@RequestBody UserExam userExam);

    @RequestMapping("/school/onlineExam/stuOptionanswer/selectByStudentAnswer")
    List<StuOptionExamanswer> selectByStudentAnswer(@RequestParam("paperId")String paperId, @RequestParam("userId")String userId);

    @RequestMapping("/module/testpaperOneTestquestions/getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId")
    List<TestpaperOneTestquestions>  getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(@RequestParam ("paperId")String paperId, @RequestParam ("tid")String tid);

    @RequestMapping("/school/onlineExam/myTitleType/findByCidAndTid")
    List<MyTitleType>  findByCidAndTid(@RequestParam("tid") String tid,@RequestParam("cid")String cid);

    @RequestMapping("/school/onlineExam/myTitleType/saveMyTitleType")
    Boolean saveMyTitleType(@RequestBody MyTitleType mty) ;
}
