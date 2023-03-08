package cn.wstom.student.feign;

import cn.wstom.student.config.FeignHttpsConfig;
import cn.wstom.student.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "https://wisdomschool-exam-service",configuration = {FeignHttpsConfig.class})
public interface ExamService {
    @RequestMapping(value = "/school/onlineExam/testPaper/testPaperList")
    List<TestPaper> testPaperList(TestPaper testPaper);

    @RequestMapping(value = "/school/onlineExam/testPaperOne/testPaperOneList")
    List<TestPaperOne> testPaperOneList(TestPaperOne testPaperOne);

    @RequestMapping("/school/onlineExam/userExam/selectUserExamListBase")
    List<UserExam> selectUserExamListBase(UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/userExamList")
    List<Map<String, Object>>userExamList(UserExam userExam);

    @RequestMapping("/school/onlineExam/userTest/selectUserTestListBase")
    List<UserTest> selectUserTestListBase(UserTest userTest);

    @RequestMapping("/school/onlineExam/myQuestion/myQuestionsList")
    List<MyQuestions> myQuestionsList(MyQuestions myQuestions);

    @RequestMapping(value = "/school/onlineExam/myOptionAnswer/{myOptionAnswerId}")
    MyOptionAnswer myOptionAnswerById(@PathVariable("myOptionAnswerId") String myOptionAnswerId);

    @DeleteMapping("/school/onlineExam/userTest/userTestRemoveByIds")
    Boolean userTestRemoveByIds(List<String> ids);

    @RequestMapping("/module/testpaperOneTestquestions/getQuestionsAndOptionsByPaperIdWithUserId/{paperId}/{studentId}")
     List<TestpaperQuestions> getQuestionsAndOptionsByPaperIdWithUserId(@PathVariable("paperId")String paperId,
                                                                        @PathVariable("studentId")String studentId);

    @RequestMapping("/school/onlineExam/stuOptionanswer/updateListAndTotalScore/{testPaperOneId}/{userId}")
     int updateListAndTotalScore(List<StuOptionExamanswer> optionanswers,
                                 @PathVariable("testPaperOneId")String testPaperOneId,
                                 @PathVariable("userId")String userId);

    @RequestMapping("/module/testpaperQuestions/getQuestionsByPaperIdWithUserId/{paperId}/{userId}")
    List<TestpaperQuestions> getQuestionsByPaperIdWithUserId ( @PathVariable("paperId")String paperId,
                                                               @PathVariable("userId")String userId);

    @RequestMapping("/module/testpaperOneTestquestions/getQuestionsByPaperOneIdWithUserId/{paperId}/{userId}")
    List<TestpaperQuestions> getQuestionsByPaperOneIdWithUserId (@PathVariable("paperId")String paperId,
                                                                 @PathVariable("userId")String userId);

    @RequestMapping("/school/onlineExam/stuOptionanswer/saveOptionAnswersByUserId/{userId}")
    int saveOptionAnswersByUserId(List<OptionSubmitVo> options, @PathVariable("userId")String userId);

    @RequestMapping("/school/onlineExam/stuOptionanswer/saveOptionExamAnswersByUserId/{userId}")
    int saveOptionExamAnswersByUserId(List<OptionExamSubmitVo> options, @PathVariable("userId")String userId);

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
    List<UserTest> userTestList(UserTest userTest);

    @RequestMapping("/school/onlineExam/userExam/selectUserExam")
    UserExam selectUserExam(@RequestParam("testPaperOneId") String testPaperOneId,@RequestParam("userId")String userId);

    @RequestMapping("/school/onlineExam/userExam/updateUserExam")
    Boolean updateUserExam(@RequestBody UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/findStuExamPaperTwo")
    List<UserExam> findStuExamPaperTwo(UserExam userExam);

    @RequestMapping("/school/onlineExam/userExam/findStuExamPaperOne")
    List<UserExam> findStuExamPaperOne(UserExam userExam);

    @RequestMapping("/school/onlineExam/stuOptionanswer/selectByStudentAnswer")
    List<StuOptionExamanswer> selectByStudentAnswer(@RequestParam("paperId")String paperId,@RequestParam("userId")String userId);

    @RequestMapping("/module/testpaperOneTestquestions/getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId")
    List<TestpaperOneTestquestions>  getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(@RequestParam ("paperId")String paperId,@RequestParam ("tid")String tid);
}
