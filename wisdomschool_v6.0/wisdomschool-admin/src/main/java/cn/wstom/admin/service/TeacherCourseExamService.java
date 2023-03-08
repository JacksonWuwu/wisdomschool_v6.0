package cn.wstom.admin.service;


import cn.wstom.admin.entity.TeacherCourseExam;

/**
 * 教师课程 服务层
 *
 * @author hyb
 * @date 2019-01-29
 */
public interface TeacherCourseExamService extends BaseService<TeacherCourseExam> {
    TeacherCourseExam selectCourseId(String courseId);
}
