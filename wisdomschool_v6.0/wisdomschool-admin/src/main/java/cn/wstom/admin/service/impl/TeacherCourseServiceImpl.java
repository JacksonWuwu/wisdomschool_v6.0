package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 教师课程 服务层实现
 *
 * @author hyb
 * @date 2019-01-29
 */
@Service
public class TeacherCourseServiceImpl extends BaseServiceImpl
        <TeacherCourseMapper, TeacherCourse>
        implements TeacherCourseService {

    @Resource
    private TeacherCourseMapper teacherCourseMapper;
    @Override
    public String selectCourseId(String courseId) {
        return teacherCourseMapper.selectCourseId(courseId);
    }

    @Override
    public TeacherCourse selectId(String cid, String tid) {
        return teacherCourseMapper.selectId(cid,tid);
    }

    @Override
    public int deletetids(String tid) {
        return teacherCourseMapper.deleteByTid(tid);
    }
    @Override
    public String selectCourseById(String tcid){
        return teacherCourseMapper.selectCourseById(tcid);
    }

    @Override
    public TeacherCourse selectOne(TeacherCourse teacherCourse){
        return teacherCourseMapper.selectOne(teacherCourse);
    }

}
