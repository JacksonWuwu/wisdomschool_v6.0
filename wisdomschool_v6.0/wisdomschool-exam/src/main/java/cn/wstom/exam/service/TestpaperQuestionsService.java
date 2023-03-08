package cn.wstom.exam.service;





import cn.wstom.exam.entity.TestpaperQuestions;

import java.util.List;

/**
 * 试卷题目 服务层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestpaperQuestionsService extends BaseService<TestpaperQuestions> {
    List<TestpaperQuestions> selectListBase(TestpaperQuestions testpaperQuestions);
    TestpaperQuestions selectByPersonalQuestionId(Integer perQueId);
}
