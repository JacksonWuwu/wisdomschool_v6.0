package cn.wstom.exam.service.impl;


import cn.wstom.exam.entity.StuOptionExamanswer;
import cn.wstom.exam.entity.TestpaperOneTestquestions;
import cn.wstom.exam.entity.TestpaperOptinanswer;
import cn.wstom.exam.entity.TestpaperQuestions;
import cn.wstom.exam.mapper.*;
import cn.wstom.exam.service.TestpaperOneTestquestionsService;
import cn.wstom.exam.service.TestpaperQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 试卷做的题目答案 服务层实现
 *
 * @author
 * @date
 */
@Service
public class TestpaperOneTestquestionsServiceImpl extends BaseServiceImpl<TestpaperOneTestquestionsMapper, TestpaperOneTestquestions>
        implements TestpaperOneTestquestionsService {
    @Autowired
    private TestpaperOneTestquestionsMapper testpaperOneTestquestionsMapper;//   试卷与题目的一对多关系映射
    @Autowired
    private TestpaperQuestionsMapper questionsMapper;//     试卷题目
    @Autowired
    private TestpaperOptinanswerMapper optinanswerMapper;//     题目和答案关系映射
    @Autowired
    private StuOptionanswerMapper stuOptionanswerMapper;//  学生选项
    @Autowired
    private StuOptionExamanswerMapper stuOptionExamanswerMapper;//      学生选项
    @Autowired
    private TestPaperOneListMapper testPaperOneListMapper;//试卷
    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;

    /**
     * 根据paperoneid删除原来的试题
     *
     * @param id
     * @return
     */
    @Override
    public int deleteByPaperOneId(String id) {
        return testpaperOneTestquestionsMapper.deleteByPaperOneId(id);
    }

    @Override
    public List<TestpaperQuestions> getQuestionsByPaperIdWithUserId(String paperId, String userId) {
        TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
        testpaperTestquestions.setTestPaperOneListId(paperId);
        List<TestpaperOneTestquestions> testpaperTestquestionsList;  //  试卷与题目的一对多关系表
        testpaperTestquestionsList = testpaperOneTestquestionsMapper.selectList(testpaperTestquestions);//    获取试卷题目集合
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();//  题目集合

        for (TestpaperOneTestquestions aTestpaperTestquestionsList : testpaperTestquestionsList) {
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
    public List<TestpaperQuestions> getQuestionsByPaperIdWithUserIdSelect(String paperId, String userId,String tQuestiontemplateNum,String titleTypeNum) {
        TestpaperOneTestquestions testpaperOneTestquestions = new TestpaperOneTestquestions();
        testpaperOneTestquestions.setTestPaperOneListId(paperId);
        List<TestpaperOneTestquestions> testpaperOneTestquestionsList = new ArrayList<TestpaperOneTestquestions>();  //  试卷与题目的一对多关系表
        testpaperOneTestquestionsList = testpaperOneTestquestionsMapper.selectList(testpaperOneTestquestions);//    获取试卷题目集合
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();//  题目集合
        for (TestpaperOneTestquestions aTestpaperOneTestquestionsList : testpaperOneTestquestionsList) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = questionsMapper.selectById(aTestpaperOneTestquestionsList.getTestQuestionsId());//   遍历获取每个题目
            if (testpaperQuestions != null&& testpaperQuestions.gettQuestiontemplateNum().equals(tQuestiontemplateNum)&&testpaperQuestions.getTitleTypeNum().equals(titleTypeNum)) {
                tqvolist.add(testpaperQuestions);
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
                List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                stuOptionExamanswer.setCreateId(Integer.valueOf(userId));
                stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
                stuOptionExamanswerList1 = stuOptionExamanswerMapper.selectList(stuOptionExamanswer);//  查询学生作答记录

                if (stuOptionExamanswerList1.size() != 0) {
                    stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                }

                aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项

            }
        }
        return tqvolist;
    }
    @Override
    public List<TestpaperQuestions> getQuestionsByPaperIdWithUserIdSelectOne(String paperId, String userId,String tQuestiontemplateNum) {
        TestpaperOneTestquestions testpaperOneTestquestions = new TestpaperOneTestquestions();
        testpaperOneTestquestions.setTestPaperOneListId(paperId);
        List<TestpaperOneTestquestions> testpaperOneTestquestionsList = new ArrayList<TestpaperOneTestquestions>();  //  试卷与题目的一对多关系表
        testpaperOneTestquestionsList = testpaperOneTestquestionsMapper.selectList(testpaperOneTestquestions);//    获取试卷题目集合
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();//  题目集合
        for (TestpaperOneTestquestions aTestpaperOneTestquestionsList : testpaperOneTestquestionsList) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = questionsMapper.selectById(aTestpaperOneTestquestionsList.getTestQuestionsId());//   遍历获取每个题目
            if (testpaperQuestions != null&& testpaperQuestions.gettQuestiontemplateNum().equals(tQuestiontemplateNum)) {
                tqvolist.add(testpaperQuestions);
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
                List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                stuOptionExamanswer.setCreateId(Integer.valueOf(userId));
                stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
                stuOptionExamanswerList1 = stuOptionExamanswerMapper.selectList(stuOptionExamanswer);//  查询学生作答记录

                if (stuOptionExamanswerList1.size() != 0) {
                    stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                }

                aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项

            }
        }
        return tqvolist;
    }

    @Override
    public List<TestpaperQuestions> getQuestionsAndOptionsByPaperIdWithUserId(String paperId, String userId) {
        List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
        TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
        testpaperTestquestions.setTestPaperOneListId(paperId);
        List<TestpaperOneTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperOneTestquestions>();
        testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperTestquestions);
        for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
            TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
            testpaperQuestions = testpaperQuestionsService.getById(testpaperTestquestionsList.get(i).getTestQuestionsId());
            if (testpaperQuestions != null) {
                tqvolist.add(testpaperQuestions);
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
                List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                stuOptionExamanswer.setCreateId(Integer.valueOf(userId));
                stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                stuOptionExamanswer.setPaperOneId(Integer.valueOf(paperId));
                stuOptionExamanswerList1 = stuOptionExamanswerMapper.selectList(stuOptionExamanswer);//  查询学生作答记录
                if (stuOptionExamanswerList1.size() != 0) {
                    stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                }

                aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项

            }

        }
        return tqvolist;
    }


    @Override
    public List<TestpaperOneTestquestions> getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId (String paperId, String createId) {
        TestpaperOneTestquestions testquestions = new TestpaperOneTestquestions();
        testquestions.setTestPaperOneId(paperId);
        testquestions.setCreateId(createId);
        List<TestpaperOneTestquestions> testpaperTestquestions = testpaperOneTestquestionsMapper.selectList(testquestions);
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

    @Override
    public List<TestpaperOneTestquestions> getPaperQuestionsAndTestPaperInfoByPaperWorkIdWithCreateId (String paperId, String createId) {
        TestpaperOneTestquestions testquestions = new TestpaperOneTestquestions();
        testquestions.setTestPaperWorkId(paperId);
        testquestions.setCreateId(createId);
        List<TestpaperOneTestquestions> testpaperTestquestions = testpaperOneTestquestionsMapper.selectList(testquestions);
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
