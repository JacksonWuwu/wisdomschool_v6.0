package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.StuOptionanswer;
import cn.wstom.exam.entity.TestpaperOptinanswer;
import cn.wstom.exam.entity.TestpaperQuestions;
import cn.wstom.exam.entity.TestpaperTestquestions;
import cn.wstom.exam.mapper.StuOptionanswerMapper;
import cn.wstom.exam.mapper.TestpaperOptinanswerMapper;
import cn.wstom.exam.mapper.TestpaperQuestionsMapper;
import cn.wstom.exam.mapper.TestpaperTestquestionsMapper;
import cn.wstom.exam.service.TestpaperTestquestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试卷做的题目答案 服务层实现
 *
 * @author hzh
 * @date 20190223
 */
@Service
public class TestpaperTestquestionsServiceImpl extends BaseServiceImpl<TestpaperTestquestionsMapper, TestpaperTestquestions>
        implements TestpaperTestquestionsService {

    @Autowired
    private TestpaperTestquestionsMapper paper_questionsMapper;//   试卷与题目的一对多关系映射
    @Autowired
    private TestpaperQuestionsMapper questionsMapper;//     试卷题目
    @Autowired
    private TestpaperOptinanswerMapper optinanswerMapper;//     题目和答案关系映射
    @Autowired
    private StuOptionanswerMapper stuOptionanswerMapper;//      学生选项

    @Override
    public List<TestpaperQuestions> getQuestionsByPaperIdWithUserId(String paperId, String userId) {
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList;  //  试卷与题目的一对多关系表

        testpaperTestquestionsList = paper_questionsMapper.selectList(testpaperTestquestions);//    获取试卷题目集合
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();//  题目集合
        for (TestpaperTestquestions aTestpaperTestquestionsList : testpaperTestquestionsList) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = questionsMapper.selectById(aTestpaperTestquestionsList.getTestQuestionsId());//   遍历获取每个题目
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
            }
        }

        for (TestpaperQuestions aTqvolist : tqvolist) {
            //  填充选项对象
            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
            String[] anList = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
            for (String anAnList : anList) {
                testpaperOptinanswer = optinanswerMapper.selectById(anAnList);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }
            aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项
        }
        return tqvolist;
    }

    @Override
    public List<TestpaperQuestions> getQuestionsAndOptionsByPaperIdWithUserId(String paperId, String userId) {
        TestpaperTestquestions testpaperTestquestions = new TestpaperTestquestions();
        testpaperTestquestions.setTestPaperId(paperId);
        List<TestpaperTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperTestquestions>();  //  试卷与题目的一对多关系表
        testpaperTestquestionsList = paper_questionsMapper.selectList(testpaperTestquestions);//    获取试卷题目集合
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();//  题目集合
        for (TestpaperTestquestions aTestpaperTestquestionsList : testpaperTestquestionsList) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = questionsMapper.selectById(aTestpaperTestquestionsList.getTestQuestionsId());//   遍历获取每个题目
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
            }
        }

        for (TestpaperQuestions aTqvolist : tqvolist) {
            //  填充选项对象
            TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
            String[] anList = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
            List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
            for (String anAnList : anList) {
                testpaperOptinanswer = optinanswerMapper.selectById(anAnList);
                if (testpaperOptinanswer != null) {
                    testpaperOptinanswerList.add(testpaperOptinanswer);
                }
            }

            //  填充学生选项
            List<StuOptionanswer> stuOptionanswerListLater = new ArrayList<StuOptionanswer>();
            List<StuOptionanswer> stuOptionanswerList1 = new ArrayList<StuOptionanswer>();
            StuOptionanswer stuOptionanswer = new StuOptionanswer();
            stuOptionanswer.setCreateId(Integer.valueOf(userId));
            stuOptionanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
            stuOptionanswerList1 = stuOptionanswerMapper.selectList(stuOptionanswer);//  查询学生作答记录
            if (stuOptionanswerList1.size() != 0) {
                stuOptionanswerListLater.add(stuOptionanswerList1.get(0));
            }

            aTqvolist.setStuOptionanswerList(stuOptionanswerListLater);// 填加学生作答记录
            aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项
        }
        return tqvolist;
    }

    @Override
    public List<TestpaperTestquestions> getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(String paperId, String createId) {
        TestpaperTestquestions testquestions = new TestpaperTestquestions();
        testquestions.setTestPaperId(paperId);
        testquestions.setCreateId(createId);
        List<TestpaperTestquestions> testpaperTestquestions = paper_questionsMapper.selectList(testquestions);

        testpaperTestquestions.forEach(t -> {
            TestpaperQuestions tq = questionsMapper.selectById(t.getTestQuestionsId());
            Map<String, Object> index = new HashMap<>();
            index.put("paperQuestionId", t.getId());
            index.put("question", tq.getTitle());
            index.put("score", tq.getQuestionScore());
            index.put("titleType", tq.getTitleTypeName());
            t.setParams(index);
        });

        return testpaperTestquestions;
    }
}
