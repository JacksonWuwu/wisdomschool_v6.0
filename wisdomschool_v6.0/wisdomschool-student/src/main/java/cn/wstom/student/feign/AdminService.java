package cn.wstom.student.feign;

import cn.wstom.student.constants.Data;
import cn.wstom.student.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "wisdomschool-admin-service")
public interface AdminService {
    //sys
    @RequestMapping(value = "/admin/sysLogin/login")
    Data login(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/user/{userId}")
    SysUser getUserById(@PathVariable(value = "userId")String userId);

    @RequestMapping(value = "/system/user/getUser")
    SysUser getUser(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/user/updateUser")
    boolean updateUser(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/system/user/selectStudentByCourseIdAndTeacherId/{courseId}/{teacherId}/{loginName}/{clbumId}/")
    List<SysUser> selectStudentByCourseIdAndTeacherId(@PathVariable("courseId")String courseId,
                                                      @PathVariable("teacherId")String teacherId,
                                                      @PathVariable("loginName")String loginName,
                                                      @PathVariable("clbumId")String clbumId);

    @RequestMapping(value = "/system/user/getUserList")
    List<SysUser> getUserList(SysUser sysUser);

    @RequestMapping(value = "/system/user/userMapByIds")
    Map<String, SysUser> userMapByIds(@RequestBody List<String> userIds);

    @RequestMapping(value = "/system/user/userlistByTids")
    Map<String, SysUser> userlistByTids(@RequestBody List<String> tcIds);

    @RequestMapping(value = "/monitor/online/sysUserOnlineList")
    List<SysUserOnline> sysUserOnlineList(SysUserOnline sysUserOnline);

    @RequestMapping(value = "/monitor/online/{sysUserOnlineId}")
    Boolean removeSysUserOnlineById(@PathVariable(value = "sysUserOnlineId")String sysUserOnlineId);

    //school
    /**
     * 查询学校列表
     */
    @RequestMapping("/jiaowu/school/{schoolId}")
     School getSchoolById(@PathVariable("schoolId") String schoolId);

     //clbum
    @RequestMapping(value = "/jiaowu/clbum/{clbumId}")
    Clbum getClbumById(@PathVariable(value = "clbumId")String clbumId);

    @RequestMapping(value = "/jiaowu/clbumcourse/clbumCourseList")
    List<ClbumCourse> clbumCourseList (@RequestBody ClbumCourse clbumCourse,
                                       @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                       @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                       @RequestParam(required = false,value = "orderBy")String orderBy);

    @RequestMapping(value = "/jiaowu/clbum/clbumList")
    List<Clbum> clbumList (Clbum clbum);

    @RequestMapping(value = "/teacher/course/selectCoursesByStuId/{studentId}")
    List<ClbumCourseVo> selectCoursesByStuId(@PathVariable(value = "studentId")int studentId);

    @RequestMapping(value = "/teacher/course/selectClbumCourseVosByTeacherId/{teacherId}")
    List<ClbumCourseVo> selectClbumCourseVosByTeacherId(@PathVariable(value = "teacherId")String teacherId);


    @RequestMapping(value = "/jiaowu/clbumcourse/clbumCourseVos")
    List<ClbumCourseVo> clbumCourseVos (@RequestBody ClbumCourseVo clbumCourseVo);

    //course
    @RequestMapping(value = "/teacher/course/getCourseById/{courseId}")
    Course getCourseById(@PathVariable(value = "courseId")String courseId);

    @RequestMapping(value = "/teacher/course/courseCount")
    int courseCount(Map<String, Object> params);

    @RequestMapping(value = "/teacher/course/courseMap")
    Map<String, Course> courseMap(Course course);

    @RequestMapping(value = "/teacher/course/courseList")
    List<Course> courseList(Course course);

    @RequestMapping(value = "/teacher/course/{cid}")
    List<Map<String, Object>> getCourseChapterInfoTree(@PathVariable(value = "cid")String cid);

    @RequestMapping(value = "/teacher/course/courseMapByClbumId")
    Map<String, Course> courseMapByClbumId(@RequestBody  List<String> cid);

    //Major
    @RequestMapping(value = "/jiaowu/major/{majorId}")
    Major getMajorById(@PathVariable(value = "majorId")String majorId);

    //grades
    @RequestMapping(value = "/jiaowu/grades/getGradesById/{gradesId}")
    Grades getGradesById(@PathVariable(value = "gradesId")String gradesId);

    //Department
    @RequestMapping(value = "/jiaowu/department/{departmentId}")
    Department getDepartmentById(@PathVariable(value = "departmentId")String departmentId);

    //Teacher
    @RequestMapping(value = "/jiaowu/teacher/{teacherId}")
    Teacher getTeacherById(@PathVariable(value = "teacherId")String teacherId);

    @RequestMapping(value = "/jiaowu/teacherCourse/teacherMapByIds")
    Map<String, Teacher> teacherMapByIds(@RequestBody List<String> tIds);

    @RequestMapping(value = "/jiaowu/teacherCourse/{teacherCourseId}")
    TeacherCourse getTeacherCourseById(@PathVariable(value = "teacherCourseId")String teacherCourseId);

    @RequestMapping(value = "/jiaowu/teacherCourse/teacherCourseList")
    List<TeacherCourse> teacherCourseList (@RequestBody TeacherCourse teacherCourse);

    @RequestMapping(value = "/jiaowu/teacherCourse/getTeacherCourse")
    TeacherCourse getTeacherCourse(@RequestBody TeacherCourse teacherCourse);

    @RequestMapping(value = "/jiaowu/teacherCourse/teacherCourseMapByIds")
    Map<String, TeacherCourse> teacherCourseMapByIds(@RequestBody List<String> tcIds);


    //Banner
    @RequestMapping("/jiaowu/banner/list")
    List<Banner> bannerList(Banner banner);

    //Preview
    @RequestMapping(value = "/teacher/preview/getPreviewById/{previewId}")
    Preview getPreviewById(@PathVariable(value = "previewId")String previewId);

    @RequestMapping(value = "/teacher/preview/listBySidAndTcid")
    List<String> listBySidAndTcid(@RequestBody PreviewStudent previewStudent);

    //Chapter
    @RequestMapping(value = "/teacher/chapter/chapterList")
    List<Chapter> chapterList(@RequestBody Chapter chapter);

    @RequestMapping("/teacher/chapter/{chapterId}")
    Chapter getChapterById(@PathVariable(value = "chapterId")String chapterId);

    @RequestMapping("/teacher/chapter/{chapterResourceId}")
    ChapterResource getChapterResourceById(@PathVariable(value = "chapterResourceId")String chapterResourceId);

    @RequestMapping(value = "/teacher/chapter/selectListByPreviewid")
    List<Chapter> selectListByPreviewid(@RequestBody Chapter chapter);


    //Recourse
    @RequestMapping(value = "/recourse/{recourseId}")
    Recourse getRecourseById(@PathVariable(value = "recourseId")String recourseId);

    @RequestMapping(value = "/recourse/recourseList")
    List<Recourse> recourseList(@RequestBody Recourse recourse,
                                @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                @RequestParam(required = false,value = "orderBy")String orderBy);

    @RequestMapping(value = "/recourse/recourseTypeList")
    List<RecourseType> recourseTypeList(@RequestBody RecourseType recourseType);

    @RequestMapping(value = "/teacher/chapter/chapterResourceList")
    List<ChapterResource>  chapterResourceList(@RequestBody ChapterResource ChapterResource);

    @RequestMapping(value = "/getRecourseByStuId/{studentId}")
    List<Recourse> getRecourseByStuId(@PathVariable(value = "studentId")String studentId);

    @RequestMapping(value = "/recourse/recourseTypeMap")
     Map<String, RecourseType> recourseTypeMap(@RequestBody RecourseType recourseType);

    @RequestMapping(value = "/recourse/recourseUpdate")
    boolean recourseUpdate(@RequestBody Recourse recourse);


    //chapter,resource
    @RequestMapping("/teacher/chapter/updateChapterResource")
    boolean updateChapterResource(@RequestBody ChapterResource chapterResource);

    @RequestMapping("/teacher/chapter/{chapterResourceId}")
    boolean deleteChapterResource(@PathVariable(value = "chapterResourceId")String chapterResourceId);



    @RequestMapping("/teacher/lead/leadGetOne")
    Lead leadGetOne(@RequestBody Map<String, Object> params);

    @RequestMapping("/teacher/outline/outlineGetOne")
    Outline outlineGetOne(@RequestBody Map<String, Object> params);

    @RequestMapping("/teacher/outpeizhi/outpeizhiGetOne")
    Outpeizhi outpeizhiGetOne(@RequestBody Map<String, Object> params);

    @RequestMapping("/comment/commentSave")
    Boolean commentSave(@RequestBody Comment comment);
    //commnet
    @RequestMapping(value = "/teacher/course/getCourseCommentListPage")
    PageVo<Comment> getCourseCommentListPage(
           @RequestParam("typeId") String typeId,
           @RequestParam("userId") String userId,
           @RequestParam("createTime") String createTime,
           @RequestParam("parentId") String parentId,
           @RequestParam("orderBy") String orderBy,
           @RequestParam("order") String order,
           @RequestParam("pageNum") int  pageNum,
           @RequestParam("pageSize") int pageSize);

    @RequestMapping("/comment/listByPids")
    List<Comment> listByPids(@RequestBody List<String> parentIds);

    @RequestMapping("/{CommentId}")
    Comment getCommentById(@PathVariable("CommentId")String CommentId);

    @RequestMapping("/{CommentId}")
    Boolean removeCommentById(@PathVariable("CommentId")String CommentId);

    //attendanceVo

    @RequestMapping("/teacher/attendance/attendanceVolist")
    List<AttendanceVo> attendanceVolist(@RequestBody AttendanceVo attendanceVo);


    //adjunct
    @RequestMapping("/teacher/adjunct/getAdjunctById/{adjunctId}")
    Adjunct getAdjunctById(@PathVariable("adjunctId")String adjunctId);

    @RequestMapping("/teacher/adjunct/adjunctStudentList")
    List<AdjunctStudent> adjunctStudentList(@RequestBody AdjunctStudent adjunctStudent);

    @RequestMapping("/teacher/adjunct/selectListByAdjunctStudent")
    List<AdjunctStudent> selectListByAdjunctStudent(@RequestBody AdjunctStudent adjunctStudent);

    @RequestMapping("/teacher/adjunct/updateByAidAndSid")
    Boolean updateByAidAndSid (@RequestBody AdjunctStudent adjunctStudent);

    @RequestMapping("/teacher/adjunct/adjunctMap")
    Map<String, Adjunct> adjunctMap();

}
