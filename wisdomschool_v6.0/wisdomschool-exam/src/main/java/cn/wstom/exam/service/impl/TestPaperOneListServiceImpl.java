package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.mapper.TestPaperOneListMapper;
import cn.wstom.exam.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Service
public class TestPaperOneListServiceImpl extends BaseServiceImpl<TestPaperOneListMapper, TestPaperOneList> implements TestPaperOneListService {

    @Resource
    private TestPaperOneListMapper testPaperOneListMapper;
    @Resource
    private TestpaperQuestionsService testpaperQuestionsService;
    @Resource
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Resource
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Resource
    private MyOptionAnswerService myOptionAnswerService;
    @Resource
    private AdminService adminService;
    @Override
    public List<TestPaperOneList> selectList(TestPaperOneList testPaperOneList) {
        return testPaperOneListMapper.selectList(testPaperOneList);
    }

    @Override
    public boolean UpdateTestPaperOneTestQuestion(MyQuestions myQuestionsList, int score, String difficult, String paperId, String attrId) throws Exception {
        TestpaperQuestions tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myQuestionsList.getId()));
        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(attrId);
        SysUser sysUser1 = adminService.getUser(sysUser);
        if (tpq == null) {
            TestpaperQuestions save = new TestpaperQuestions();
            save.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            save.setCreateId(sysUser1.getId());
            save.setCreateBy(sysUser1.getLoginName());
            save.setPersonalQuestionId(Integer.valueOf(myQuestionsList.getId()));
            testpaperQuestionsService.save(save);
            tpq = testpaperQuestionsService.selectByPersonalQuestionId(Integer.valueOf(myQuestionsList.getId()));
        }
        tpq.setQuestionScore(score);
        tpq.setTitleTypeId(myQuestionsList.getTitleTypeId());
        tpq.setDifficulty(difficult);
        tpq.setTitle(myQuestionsList.getTitle());
        tpq.setQexposed(myQuestionsList.getQexposed());
        tpq.setQmaxexposure(myQuestionsList.getQmaxexposure());
        tpq.setParsing(myQuestionsList.getParsing());
        tpq.setYear(myQuestionsList.getYear());

        /*      原来的题目选项   */
        TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
        testpaperOptinanswer.setQuestionId(tpq.getId());
        List answers = testpaperOptinanswerService.list(testpaperOptinanswer);


        TestpaperOneTestquestions testquestions = new TestpaperOneTestquestions();
        testquestions.setTestPaperOneId(paperId);
        testquestions.setTestQuestionsId(tpq.getId());
        List ttq = testpaperOneTestquestionsService.list(testquestions);

        /*  复制MyQuestionAnswer 到 testPaperQuestionAnswer */
        String myoastr = myQuestionsList.getMyoptionAnswerArr();//  题目选项
        if (myoastr != null && !"".equals(myoastr) && answers.isEmpty()) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
            for (String aMyoaArr : myoaArr) {
                MyOptionAnswer myOptionAnswer = myOptionAnswerService.getById(aMyoaArr);
                myoalist.add(myOptionAnswer);
            }
            StringBuilder tpoastr = new StringBuilder();
            for (MyOptionAnswer myoa : myoalist) {
                TestpaperOptinanswer toa = new TestpaperOptinanswer();
                toa.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                toa.setCreateId(attrId);
                toa.setCreateBy(sysUser1.getLoginName());
                toa.setQuestionId(tpq.getId());
                toa.setStanswer(myoa.getStanswer());
                toa.setStoption(myoa.getStoption());
                testpaperOptinanswerService.save(toa);
                tpoastr.append(toa.getId()).append(";");
            }
            tpq.setTestPaperOptionAnswerArr(tpoastr.toString());
        }

        if (ttq == null || ttq.isEmpty()) {
            TestpaperOneTestquestions testPaperTestQuestions = new TestpaperOneTestquestions();
            testPaperTestQuestions.setTestQuestionsId(tpq.getId());
            testPaperTestQuestions.setTestPaperOneId(paperId);
            testPaperTestQuestions.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            testPaperTestQuestions.setCreateId(attrId);
            testPaperTestQuestions.setCreateBy(sysUser1.getLoginName());
            testpaperOneTestquestionsService.save(testPaperTestQuestions);
        }

        /*  更新testpaperquestion */
       return testpaperQuestionsService.update(tpq);
    }

}
