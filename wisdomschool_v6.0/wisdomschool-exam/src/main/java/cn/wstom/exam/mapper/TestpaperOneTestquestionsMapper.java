package cn.wstom.exam.mapper;


import cn.wstom.exam.entity.TestpaperOneTestquestions;

/**
 * 试卷做的题目答案 数据层
 *
 * @author
 * @date
 */
public interface TestpaperOneTestquestionsMapper extends BaseMapper<TestpaperOneTestquestions> {
    int deleteByPaperOneId(String id);
}
