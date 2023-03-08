package cn.wstom.exam.mapper;



import cn.wstom.exam.entity.TestPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
        * 试卷 数据层
        *
        * @author hzh
        * @date 20190223
        */
public interface TestPaperMapper extends BaseMapper<TestPaper> {

    /**
     * 校验标题名唯一
     *
     * @param testPaper
     * @return
     */
    TestPaper checkTestNameUnique(String testPaper);

    /**
     * 查找年级
     *
     * @param teacherId
     * @return
     */
    List<TestPaper> getStudentInfo(@Param("teacherId") String teacherId);

    /**
     * 获取科目
     *
     * @param teacherId
     * @return
     */
    List<TestPaper> getTcoName(@Param("teacherId") String teacherId);

    /**
     * 获取学生
     *
     * @param teacherId
     * @return
     */
    List<TestPaper> getTcoStu(@Param("teacherId") String teacherId);


    /**
     * 查找学生试卷
     * @param testPaper
     * @return
     */
    List<TestPaper> findStuPaper(TestPaper testPaper);



}
