package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.Course;
import cn.wstom.admin.entity.TeacherCourse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程 数据层
 *
 * @author hyb
 * @date 2019-01-29
 */
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 根据班级查找课程
     *
     * @param cid
     * @return
     */
    List<Course> selectBatchClbumIds(List<String> cid);

    TeacherCourse selectTeacherCourse(@Param("userId") String userId, @Param("courseId") String courseId);
    List<Course> selectByCourses(Course course);

    List<String> selectTree(String tid);
}
