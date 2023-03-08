package cn.wstom.exam.feign;

import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
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

    @RequestMapping(value = "/system/user/selectStudentByCourseIdAndTeacherId/{courseId}/{teacherId}/{loginName}/{clbumId}/")
    List<SysUser> selectStudentByCourseIdAndTeacherId(@PathVariable("courseId")String courseId,
                                                      @PathVariable("teacherId")String teacherId,
                                                      @PathVariable("loginName")String loginName,
                                                      @PathVariable("clbumId")String clbumId);

    @RequestMapping(value = "/system/user/getUserList")
    List<SysUser> getUserList(@RequestBody SysUser sysUser);

    @RequestMapping(value = "/monitor/online/sysUserOnlineList")
    List<SysUserOnline> sysUserOnlineList(@RequestBody SysUserOnline sysUserOnline);

    @RequestMapping(value = "/monitor/online/{sysUserOnlineId}")
    Boolean removeSysUserOnlineById(@PathVariable(value = "sysUserOnlineId")String sysUserOnlineId);

    //clbum
    @RequestMapping(value = "/jiaowu/clbum/{clbumId}")
    Clbum getClbumById(@PathVariable(value = "clbumId")String clbumId);

    @RequestMapping(value = "/teacher/course/selectCoursesByStuId/{studentId}")
    List<ClbumCourseVo> selectCoursesByStuId(@PathVariable(value = "studentId")int studentId);

    @RequestMapping(value = "/teacher/course/selectClbumCourseVosByTeacherId/{teacherId}")
    List<ClbumCourseVo> selectClbumCourseVosByTeacherId(@PathVariable(value = "teacherId")String teacherId);


    @RequestMapping(value = "/jiaowu/clbumcourse/clbumCourseVos")
    List<ClbumCourseVo> clbumCourseVos (ClbumCourseVo clbumCourseVo);

    //course
    @RequestMapping(value = "/teacher/course/getCourseById/{courseId}")
    Course getCourseById(@PathVariable(value = "courseId")String courseId);

    @RequestMapping(value = "/teacher/course/courseCount")
    int courseCount(@RequestBody Map<String, Object> params);

    @RequestMapping(value = "/teacher/course/courseMap")
    Map<String, Course> courseMap(Course course);


    @RequestMapping(value = "/teacher/course/courseList")
    List<Course> courseList(@RequestBody Course course);

    @RequestMapping(value = "/teacher/course/{cid}")
    List<Map<String, Object>> getCourseChapterInfoTree(@PathVariable(value = "cid")String cid);


    //Major
    @RequestMapping(value = "/jiaowu/major/{majorId}")
    Major getMajorById(@PathVariable(value = "majorId")String majorId);

    //Department
    @RequestMapping(value = "/jiaowu/department/{departmentId}")
    Department getDepartmentById(@PathVariable(value = "departmentId")String departmentId);


    //Teacher
    @RequestMapping(value = "/jiaowu/teacher/{teacherId}")
    Teacher getTeacherById(@PathVariable(value = "teacherId")String teacherId);

    @RequestMapping(value = "/jiaowu/teacherCourse/{teacherCourseId}")
    TeacherCourse getTeacherCourseById(@PathVariable(value = "teacherCourseId")String teacherCourseId);

    @RequestMapping(value = "/jiaowu/teacherCourse/teacherCourseList")
    List<TeacherCourse> teacherCourseList (@RequestBody TeacherCourse teacherCourse);

    @RequestMapping(value = "/jiaowu/teacherCourse/getTeacherCourse")
    TeacherCourse getTeacherCourse(@RequestBody TeacherCourse teacherCourse);

    //Banner
    @RequestMapping("/jiaowu/banner/list")
    List<Banner> bannerList(@RequestBody Banner banner);

    //Preview
    @RequestMapping(value = "/teacher/preview/{previewId}")
    Preview getPreviewById(@PathVariable(value = "previewId")String previewId);

    @RequestMapping(value = "/teacher/preview/listBySidAndTcid")
    List<String> listBySidAndTcid(@RequestBody PreviewStudent previewStudent);

    //Chapter
    @RequestMapping(value = "/teacher/chapter/chapterList")
    List<Chapter>  chapterList(@RequestBody Chapter chapter);

    @RequestMapping("/teacher/chapter/{chapterId}")
    Chapter getChapterById(@PathVariable(value = "chapterId")String chapterId);

    @RequestMapping("/teacher/chapter/getChapterResourceById/{chapterResourceId}")
    ChapterResource getChapterResourceById(@PathVariable(value = "chapterResourceId")String chapterResourceId);


    //Recourse
    @RequestMapping(value = "/recourse/{recourseId}")
    Recourse getRecourseById(@PathVariable(value = "recourseId")String recourseId);

    @RequestMapping(value = "/recourse/recourseList")
    List<Recourse> recourseList(@RequestBody Recourse recourse);

    @RequestMapping(value = "/recourse/recourseTypeList")
    List<RecourseType> recourseTypeList(@RequestBody RecourseType recourseType);

    @RequestMapping(value = "/teacher/chapter/chapterResourceList")
    List<ChapterResource>  chapterResourceList(@RequestBody ChapterResource ChapterResource);

    @RequestMapping(value = "/recourse/{studentId}")
    List<Recourse> getRecourseByStuId(@PathVariable(value = "studentId")String studentId);

    //chapter,resource
    @RequestMapping("/teacher/chapter/updateChapterResource")
    boolean updateChapterResource(@RequestBody ChapterResource chapterResource);

    @RequestMapping("/teacher/chapter/{chapterResourceId}")
    boolean deleteChapterResource(@PathVariable(value = "chapterResourceId")String chapterResourceId);



}
