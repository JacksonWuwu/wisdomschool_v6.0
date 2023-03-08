package cn.wstom.exam.service;



import cn.wstom.exam.entity.TestpaperQuestions;
import cn.wstom.exam.entity.TestpaperTestquestions;

import java.util.List;

/**
 * 试卷做的题目答案 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestpaperTestquestionsService extends BaseService<TestpaperTestquestions> {

    /**
     * 根据试卷id和创建者id获得试卷题目
     * @param paperId
     * @param userId
     * @return
     */
    List<TestpaperQuestions> getQuestionsByPaperIdWithUserId(String paperId, String userId);

    /**
     * 根据试卷id和创建者id获得试卷题目以及学生选项
     * @param paperId
     * @param userId
     * @return
     */
    List<TestpaperQuestions> getQuestionsAndOptionsByPaperIdWithUserId(String paperId, String userId);


    /**
     * 根据试卷id和创建者id获得试卷关联题目的信息
     * @param paperId
     * @param userId
     * @return
     */
    List<TestpaperTestquestions> getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(String paperId, String userId);
}
