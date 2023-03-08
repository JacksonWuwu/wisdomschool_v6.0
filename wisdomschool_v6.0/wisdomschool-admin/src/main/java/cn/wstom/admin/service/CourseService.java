package cn.wstom.admin.service;

import cn.wstom.admin.entity.*;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

/**
 * 课程 服务层
 *
 * @author hyb
 * @date 2019-01-29
 */
public interface CourseService extends BaseService<Course> {
    /**
     * 根据班级查找课程
     *
     * @param cid
     * @return
     */
    Map<String, Course> mapByClbumId(List<String> cid);

    TeacherCourse getTeacherCourse(String userAttrId, String courseId);

    @Cacheable(value = "courseComment")
    PageVo<Comment> getCourseCommentListPage(String courseId,
                                             String userId,
                                             String createTime,
                                             String parentId,
                                             String orderby,
                                             String order,
                                             int pageNum,
                                             int rows);

    /**
     * 学生id 查询课程
     * @param studentId
     * @return
     */
    List<ClbumCourseVo> selectCoursesByStuId(int studentId);
    List<ClbumCourseVo> selectCoursesByTeaId(String teacherId);
    List<Course> selectByCourses(Course course);
    List<ClbumCourseVo> selectClbumCourseVosByTeacherId(String tId);


    List<Map<String, Object>> roleCourseTreeData(TeacherVo teacherVo);
}
