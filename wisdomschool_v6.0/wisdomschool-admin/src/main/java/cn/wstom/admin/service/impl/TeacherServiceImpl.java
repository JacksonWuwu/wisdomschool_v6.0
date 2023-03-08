package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.impl.BaseServiceImpl;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xyl
 * @date 2019/02/14
 */
@Service
public class TeacherServiceImpl extends BaseServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

    @Resource
    private TeacherMapper  teacherMapper;

    @Resource
    private TeacherCourseMapper teacherCourseMapper;

    @Override
    public boolean update(Teacher teacher) {
        if (teacher.getCourses() == null) {
            return false;
        }
        String tId = teacher.getId();

        /*  Lin__   */
        TeacherCourse tc = new TeacherCourse();
        tc.setTid(tId);
        List<TeacherCourse> lastCourseList = teacherCourseMapper.selectList(tc);
        List<Course> courseList = teacher.getCourses();
        Map<String, Course> courseMap = new HashMap<>();
        courseList.forEach(c -> courseMap.put(c.getId(), c));

        List<String> deleteElements = new ArrayList<>();

        for (TeacherCourse teacherCourse : lastCourseList) {
            if (courseMap.get(teacherCourse.getCid()) != null)
                courseList.remove(courseMap.get(teacherCourse.getCid()));
            else deleteElements.add(teacherCourse.getId());
        }
        deleteElements.forEach(e -> {
            teacherCourseMapper.deleteById(e);
        });
        // 删除教师与课程关联????
//        teacherCourseMapper.deleteByTid(tId);


        // 新增教师与课程关联
        List<TeacherCourse> list = new ArrayList<>();
        for (Course course : teacher.getCourses()) {
            list.add(new TeacherCourse(tId, course.getId()));
        }
        if (!list.isEmpty()) {
            return retBool(teacherCourseMapper.insertBatch(list));
        }
        return false;
    }

    @Override
    public Map<String, Teacher> mapByCids(List<String> tcId) {
        List<Teacher> teacherList = teacherMapper.mapByCids(tcId);
        Map<String, Teacher> teachers = new LinkedHashMap<>();
        teacherList.forEach(t -> teachers.put(t.getId(), t));
        return teachers;
    }


    @Override
    public List<Teacher> mapByDeptids(int depe_id) {
        return teacherMapper.mapByDeptids(depe_id);
    }
}
