package cn.wstom.exam.service;





import cn.wstom.exam.entity.TestpaperOneTestquestions;
import cn.wstom.exam.entity.TestpaperQuestions;

import java.util.List;

/**
 * 试卷做的题目答案 服务层
 *
 * @author
 * @date
 */
public interface TestpaperOneTestquestionsService extends BaseService<TestpaperOneTestquestions> {
    /**
     * 根据paperoneid删除原来的试题
     * @param id
     * @return
     */
    int deleteByPaperOneId(String id);

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
    List<TestpaperQuestions> getQuestionsByPaperIdWithUserIdSelect(String paperId, String userId,String tQuestiontemplateNum,String titleTypeNum);
    List<TestpaperQuestions> getQuestionsByPaperIdWithUserIdSelectOne(String paperId, String userId,String tQuestiontemplateNum);

    /**
     * 根据试卷id和创建者id获得试卷关联题目的信息
     * @param paperId
     * @param userId
     * @return
     */
    List<TestpaperOneTestquestions> getPaperQuestionsAndTestPaperInfoByPaperIdWithCreateId(String paperId, String userId);
    List<TestpaperOneTestquestions> getPaperQuestionsAndTestPaperInfoByPaperWorkIdWithCreateId(String paperId, String userId);
}
