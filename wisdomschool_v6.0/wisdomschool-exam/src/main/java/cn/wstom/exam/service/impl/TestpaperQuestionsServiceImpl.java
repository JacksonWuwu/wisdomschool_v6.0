package cn.wstom.exam.service.impl;

import cn.wstom.exam.entity.TestpaperQuestions;
import cn.wstom.exam.mapper.TestpaperQuestionsMapper;
import cn.wstom.exam.service.TestpaperQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 试卷题目 服务层实现
 *
 * @author hzh
 * @date 20190223
 */
@Service
public class TestpaperQuestionsServiceImpl extends BaseServiceImpl<TestpaperQuestionsMapper, TestpaperQuestions>
        implements TestpaperQuestionsService {

    @Autowired
    private TestpaperQuestionsMapper testpaperQuestionsMapper;

    @Override
    public List<TestpaperQuestions> selectListBase(TestpaperQuestions testpaperQuestions) {
        return testpaperQuestionsMapper.selectListBase(testpaperQuestions);
    }

    @Override
    public TestpaperQuestions selectByPersonalQuestionId(Integer perQueId) {
        return testpaperQuestionsMapper.selectByPersonalQuestionId(perQueId);
    }
}
