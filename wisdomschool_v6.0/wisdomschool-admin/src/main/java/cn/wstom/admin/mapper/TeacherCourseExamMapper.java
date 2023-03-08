package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.TeacherCourseExam;
import org.apache.ibatis.annotations.Param;

/**
 * 教师课程 数据层
 *
 * @author hyb
 * @date 2019-01-29
 */
public interface TeacherCourseExamMapper extends BaseMapper<TeacherCourseExam> {

    /**
     * 删除指定教师课程
     *
     * @param tid
     * @return
     */
    int deleteByTid(String tid);
    TeacherCourseExam selectCourseId(@Param("courseId") String courseId);


}
