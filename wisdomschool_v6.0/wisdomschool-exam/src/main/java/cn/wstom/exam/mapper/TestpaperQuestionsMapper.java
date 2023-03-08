package cn.wstom.exam.mapper;





import cn.wstom.exam.entity.TestpaperQuestions;

import java.util.List;

/**
 * 试卷题目 数据层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestpaperQuestionsMapper extends BaseMapper<TestpaperQuestions> {
    List<TestpaperQuestions> selectListBase(TestpaperQuestions testpaperQuestions);
    TestpaperQuestions selectByPersonalQuestionId(Integer perQueId);
}
