package cn.wstom.admin.service.impl;

import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.constant.ForumConstants;

import cn.wstom.common.utils.StringUtil;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 课程 服务层实现
 *
 * @author hyb
 * @date 2019-01-29
 */
@Transactional
@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseMapper, Course>
        implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private StudentService studentService;

    @Resource
    private ClbumCourseVoMapper clbumCourseVoMapper;


    @Override
    public Map<String, Course> mapByClbumId(List<String> cid) {
        List<Course> courses = courseMapper.selectBatchClbumIds(cid);
        Map<String, Course> result = new LinkedHashMap<>();
        courses.forEach(course -> result.put(course.getId(), course));
        return result;
    }

    @Override
    public TeacherCourse getTeacherCourse(String userAttrId, String courseId) {
        return courseMapper.selectTeacherCourse(userAttrId, courseId);
    }

    @Override
    public PageVo<Comment> getCourseCommentListPage(String courseId, String userId, String createTime, String parentId, String orderby, String order, int pageNum, int rows) {
        PageVo<Comment> pageVo = new PageVo<>(pageNum);
        pageVo.setRows(rows);
        if (orderby == null) {
            orderby = "id";
        }
        if (order == null) {
            order = "desc";
        }
        pageVo.setList(commentMapper.selectCommentList(ForumConstants.INFO_TYPE_COURSE, courseId, userId, createTime, parentId, orderby, order, pageVo.getOffset(), pageVo.getRows()));
        pageVo.setCount(commentMapper.selectCommentCount(ForumConstants.INFO_TYPE_COURSE, courseId, userId, createTime, parentId));
        return pageVo;
    }

    @Override
    public List<ClbumCourseVo> selectCoursesByStuId(int studentId) {
        Student student = studentService.getStudentById(String.valueOf(studentId));
        return clbumCourseVoMapper.selectClbumCourseVosByClbumId(student.getCid());
    }

    @Override
    public List<ClbumCourseVo> selectCoursesByTeaId(String teacherId) {
        TeacherCourse teacher = new TeacherCourse();
        teacher.setTid(teacherId);
        ClbumCourse clbum = new ClbumCourse();
        clbum.setTcid(teacher.getId());
        return clbumCourseVoMapper.selectClbumCourseVosByClbumId(clbum.getCid());
    }

    @Override
    public List<Course> selectByCourses(Course course) {
        return courseMapper.selectByCourses(course);
    }

    @Override
    public List<ClbumCourseVo> selectClbumCourseVosByTeacherId(String tId) {
        return clbumCourseVoMapper.selectClbumCourseVosByTeacherId(tId);
    }

    @Override
    public List<Map<String, Object>> roleCourseTreeData(TeacherVo teacherVo) {
        String tid = teacherVo.getId();
        String schoolId = teacherVo.getSchoolId();
        List<Map<String, Object>> trees;
        Course course=new Course();
        course.setSchoolId(Integer.parseInt(schoolId));
        List<Course> courseList = courseMapper.selectList(course);
        System.out.println("courseList++++++++++++++++++++"+courseList);
        System.out.println("schoolId++++++++++++++++++++"+schoolId);
        if (StringUtil.isNotNull(tid)) {
            List<String> roleMenuList = courseMapper.selectTree(tid);
            trees = getTrees(courseList, true, roleMenuList, true);
        } else {
            trees = getTrees(courseList, false, null, true);
        }
        return trees;
    }


    /**
     * 对象转菜单树
     *
     * @param courseList     菜单列表
     * @param isCheck      是否需要选中
     * @param roleCourseList 角色已存在菜单列表
     * @param permsFlag    是否需要显示权限标识
     * @return
     */
    public List<Map<String, Object>> getTrees(List<Course> courseList, boolean isCheck, List<String> roleCourseList,
                                              boolean permsFlag) {
        List<Map<String, Object>> trees = new ArrayList<>();
        for (Course course : courseList) {
            Map<String, Object> menuMap = new HashMap<>(16);
            menuMap.put("id", course.getId());
            menuMap.put("pId", "0");
            menuMap.put("name", course.getName());
            menuMap.put("title", "");
            if (isCheck) {
//                System.out.println("roleCourseList.contains"+roleCourseList.contains(course.getId()));
                menuMap.put("checked", roleCourseList.contains(course.getId()));
            } else {
                menuMap.put("checked", false);
            }
            trees.add(menuMap);
        }
        return trees;
    }


//    public String transName(Course menu, List<String> roleMenuList, boolean permsFlag) {
//        StringBuffer sb = new StringBuffer();
//        sb.append(menu.getMenuName());
//        if (permsFlag) {
//            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
//        }
//        return sb.toString();
//    }

}
