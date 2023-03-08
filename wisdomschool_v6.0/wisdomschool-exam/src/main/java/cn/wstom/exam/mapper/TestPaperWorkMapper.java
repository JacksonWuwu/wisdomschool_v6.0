package cn.wstom.exam.mapper;



import cn.wstom.exam.entity.TestPaperWork;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试卷 数据层
 *
 * @author hzh
 * @date 20190223
 */
public interface TestPaperWorkMapper extends BaseMapper<TestPaperWork> {
    List<TestPaperWork> selectPaper(TestPaperWork testPaperOne);
    /**
     * 校验标题名唯一
     *
     * @param testPaper
     * @return
     */
    TestPaperWork checkTestNameUnique(String testPaper);

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    List<TestPaperWork> getStudentInfo(@Param("teacherId") String teacherId);

    /**
     * 获取科目
     *
     * @param courseId
     * @return
     */
    List<TestPaperWork> getTcoName(@Param("courseId") String courseId);

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    List<TestPaperWork> getTcoStu(@Param("teacherId") String teacherId);


    /**
     * 查找学生试卷
     * @param testPaper
     * @return
     */
    List<TestPaperWork> findStuPaper(TestPaperWork testPaper);

    boolean updateSetExam(TestPaperWork testPaper);


    public TestPaperWork selectOne(TestPaperWork testPaperWork);


}
