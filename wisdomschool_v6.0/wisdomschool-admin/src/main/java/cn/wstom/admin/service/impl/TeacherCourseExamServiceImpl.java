package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.TeacherCourseExam;
import cn.wstom.admin.mapper.TeacherCourseExamMapper;
import cn.wstom.admin.service.TeacherCourseExamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 教师课程 服务层实现
 *
 * @author hyb
 * @date 2019-01-29
 */
@Service
public class TeacherCourseExamServiceImpl extends BaseServiceImpl
        <TeacherCourseExamMapper, TeacherCourseExam>
        implements TeacherCourseExamService {

    @Resource
    private TeacherCourseExamMapper teacherCourseExamMapper;
    @Override
    public TeacherCourseExam selectCourseId(String courseId) {
        return teacherCourseExamMapper.selectCourseId(courseId);
    }
}
