package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.TeacherCourse;
import org.apache.ibatis.annotations.Param;

/**
 * 教师课程 数据层
 *
 * @author hyb
 * @date 2019-01-29
 */
public interface TeacherCourseMapper extends BaseMapper<TeacherCourse> {

    /**
     * 删除指定教师课程
     *
     * @param tid
     * @return
     */
    int deleteByTid(String tid);
    String selectCourseId(@Param("courseId")String courseId);
    TeacherCourse selectId(@Param("cid")String cid, @Param("tid")String tid);

    String selectCourseById(String tcid);


    TeacherCourse selectOne(TeacherCourse teacherCourse);

}
