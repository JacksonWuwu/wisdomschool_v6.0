package cn.wstom.admin.service;


import cn.wstom.admin.entity.TeacherCourse;

/**
 * 教师课程 服务层
 *
 * @author hyb
 * @date 2019-01-29
 */
public interface TeacherCourseService extends BaseService<TeacherCourse> {
    String selectCourseId(String courseId);
    TeacherCourse selectId(String cid,String tid);
    int deletetids(String tid);

    String selectCourseById(String tcid);

    TeacherCourse selectOne(TeacherCourse teacherCourse);

}
