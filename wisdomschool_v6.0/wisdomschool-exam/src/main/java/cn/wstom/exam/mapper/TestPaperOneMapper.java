package cn.wstom.exam.mapper;


import cn.wstom.exam.entity.TestPaperOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试卷 数据层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestPaperOneMapper extends BaseMapper<TestPaperOne> {
    List<TestPaperOne> selectPaper(TestPaperOne testPaperOne);
    /**
     * 校验标题名唯一
     *
     * @param testPaper
     * @return
     */
    TestPaperOne checkTestNameUnique(String testPaper);

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    List<TestPaperOne> getStudentInfo(@Param("teacherId") String teacherId);

    /**
     * 获取科目
     *
     * @param courseId
     * @return
     */
    List<TestPaperOne> getTcoName(@Param("courseId") String courseId);

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    List<TestPaperOne> getTcoStu(@Param("teacherId") String teacherId);


    /**
     * 查找学生试卷
     * @param testPaper
     * @return
     */
    List<TestPaperOne> findStuPaper(TestPaperOne testPaper);

    boolean updateSetExam(TestPaperOne testPaper);

    public int updateTimeById(TestPaperOne testPaperOne) throws Exception;

    public TestPaperOne getById(@Param("id") String id);

    public TestPaperOne selectOne(TestPaperOne testPaperOne);


}
