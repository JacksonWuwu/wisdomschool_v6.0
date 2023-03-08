package cn.wstom.student.controller.course;


import cn.wstom.student.constants.Constants;
import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import cn.wstom.student.feign.ExamService;
import cn.wstom.student.service.StudentService;
import cn.wstom.student.service.TopicService;
import cn.wstom.student.service.VideoChapterService;
import cn.wstom.student.service.VideoChapterUserService;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private StudentService studentService;



    @Autowired
    private ExamService examService;

    @Autowired
    private TopicService topicService;
    @Autowired
    private VideoChapterService videoChapterService;
    @Autowired
    private VideoChapterUserService videoChapterUserService;
    /**
     *
     * 课程列表获取
     * @param pageNum 页数
     * @return
     */
    @ApiOperation("学生课程列表")
    @GetMapping(value = "/list")
    public Data list(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum) {
        String studentId = getUser().getUserAttrId();
        Student student = studentService.getById(studentId);
        //  班级
//        Clbum clbum = clbumService.getById(student.getCid());
        //  课程数
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("cid", student.getCid());
        int courseCount = adminService.courseCount(params);
        //  课程列表
        Page page = loadNumData(pageNum);
        List<ClbumCourseVo> courseVoList = adminService.selectCoursesByStuId(Integer.parseInt(studentId));
        Map<String, Object> map = new HashMap<>();
        System.out.println("ScourseList"+courseVoList);
//        System.out.println("Sclbum"+clbum);
        System.out.println("ScourseCount"+courseCount);
        System.out.println("SpageSize"+page.getPages());
//        map.put("clbum", clbum);
        map.put("courseCount", courseCount);
        map.put("courseList", courseVoList);
        map.put("pageSize", page.getPages());
        return Data.success(map);
    }

    /**
     *
     * 课程列表获取
     * @param pageNum 页数
     * @return
     */
    @ApiOperation("教师课程列表")
    @GetMapping(value = "/teacherlist")
    public Data Teacherlist(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum) {
        String teacherId = getUser().getUserAttrId();
        TeacherCourse teacher = new TeacherCourse();
        teacher.setTid(teacherId);
        //  班级
        ClbumCourse clbum = new ClbumCourse();
        clbum.setTcid(teacher.getId());
        //  课程数
        Clbum clbums = adminService.getClbumById(clbum.getTcid());
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("cid", clbum.getCid());
        int courseCount = adminService.courseCount(params);
        //  课程列表
        Page page = loadNumData(pageNum);
//        List<ClbumCourseVo> courseVoList = courseService.selectCoursesByTeaId(teacherId);
//        List<TeacherCourse> list = teacherCourseService.list(teacher);
        List<ClbumCourseVo> courseVoList = adminService.selectClbumCourseVosByTeacherId(teacherId);
//        List<ClbumCourseVo> tcVo = trans(courseVoList);
        Map<String, Object> map = new HashMap<>();
//        map.put("clbum", clbums);
        map.put("courseCount", courseCount);
        map.put("courseList", courseVoList);
        map.put("pageSize", page.getPages());
        System.out.println("TcourseList"+courseVoList);
        System.out.println("Tclbum"+clbum);
        System.out.println("TcourseCount"+courseCount);
        System.out.println("TpageSize"+page.getPages());
        return Data.success(map);
    }
    /**
     * 批量转换teacherVo类型
     *
     * @param teacherCourses 将<code>TeacherCourse</code>类型转为<code>TeacherCourseVo</code>
     * @return <code>TeacherCourseVo</code>类型数据
     */
    private List<TeacherCourseVo> trans(List<TeacherCourse> teacherCourses) {
        List<TeacherCourseVo> tcVos = new LinkedList<>();
        Map<String, Course> courseMap = adminService.courseMap(null);

        teacherCourses.forEach(u -> {
            TeacherCourseVo teacherCourseVo = new TeacherCourseVo();
            BeanUtils.copyProperties(u, teacherCourseVo);
            teacherCourseVo.setCourse(courseMap.get(u.getCid()));
            tcVos.add(teacherCourseVo);
        });
        return tcVos;
    }
    /**
     * 课程内容获取
     * @param tcid
     * @return
     */
    @ApiOperation("课程内容")
    @GetMapping("/detail/{tcid}")
    public Data detail(@PathVariable String tcid,
                       @RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum,
                       @RequestParam(required = false, defaultValue = "1", value = "sort") int sort) {
        //  课程信息
        TeacherCourse teachCourse = adminService.getTeacherCourseById(tcid);
        Course course = adminService.getCourseById(teachCourse.getCid());

        //  资料类型列表
        RecourseType type = new RecourseType();
        type.setTcId(String.valueOf(tcid));
        List<RecourseType> types = adminService.recourseTypeList(type);

        //  课程章
        List chapters = adminService.getCourseChapterInfoTree(teachCourse.getCid());


        //  课程论坛
        List topics = topicService.getCourseAllTopic(teachCourse.getId(), sort);

        //  教师
        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(teachCourse.getTid());
        SysUser user = adminService.getUser(sysUser);


        Map<String, Object> map = new HashMap<>();
        map.put("course", course);
        if (user != null) {
            map.put("teacherName", user.getUserName());
        }
        map.put("types", types);
        map.put("chapters", chapters);
        map.put("topics", topics);
        return Data.success(map);
    }

    /**
     *
     * @param tcid 教授课程
     * @param type 资源类型
     * @param search 搜索字段
//     * @param pageNum 分页参数
     * @return
     */
    @ApiOperation("类型资源内容")
    @PostMapping("/resources/{tcid}")
    public Data resources(
            @RequestParam(required = false, defaultValue = "0", value = "type") String type,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @PathVariable String tcid) {

        ChapterResource chapterResource=new ChapterResource();
        chapterResource.setTcId(tcid);
        List<ChapterResource> chapterResources=adminService.chapterResourceList(chapterResource);
        List<Recourse> result=new ArrayList<>();
        RecourseType recourseType = new RecourseType();
        if (!"0".equals(type)) {
            recourseType.setId(type);
        }
        chapterResources.forEach(c->{
//            Chapter chapter=chapterService.getById(c.getCid());
            Recourse recourse = new Recourse();
            recourse.setCategory(recourseType);
            recourse.setId(c.getRid());
            if (StringUtils.isNotEmpty(search)) {
                recourse.setName(search);
            }
            recourse.setTcId(tcid);
            List<Recourse> result1 = null;
            try {
                PageDomain pageDomain = TableSupport.buildPageRequest();
                 result1 = adminService.recourseList(recourse,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            result1.forEach(r -> {
////                r.setFileName(chapter.getName());
//            });
            assert result1 != null;
            result.addAll(result1);
        });
        System.out.println("result123"+result);
        return Data.success(result);
    }


    @ApiOperation("类型资源内容")
    @PostMapping("/resources1")
    public Data resources1() {
        String studentId = getUser().getUserAttrId();
        List<Recourse> result = adminService.getRecourseByStuId(studentId);
        return Data.success(result);
    }


    @ApiOperation("ppt资源内容")
    @PostMapping("/resourcesppt")
    public Data  getResourcePpt() {
        String studentId = getUser().getUserAttrId();
        List<Recourse> result = adminService.getRecourseByStuId(studentId);
        return Data.success(result);
    }
    @ApiOperation("视频资源内容")
    @PostMapping("/resourcesvideo")
    public Data  getResourceVideo() {
        String studentId = getUser().getUserAttrId();
        List<Recourse> result = adminService.getRecourseByStuId(studentId);
        return Data.success(result);
    }
    @ApiOperation("考试试卷")
    @PostMapping("/exam/{cid}/{tcid}/{resourceType}")
    public Data chapterResources(
            @PathVariable String cid,
            @PathVariable String tcid,
            @PathVariable Integer resourceType) {
      ChapterResource chapterResource = new ChapterResource();
      chapterResource.setCid(cid);
      chapterResource.setTcId(tcid);
      chapterResource.setResourceType(resourceType);
        List<ChapterResource> result = adminService.chapterResourceList(chapterResource);
        System.out.println(result);
        return Data.success(result);
    }
    @ApiOperation("学生观看详情")
    @PostMapping("/statis/showVideoDetail/{tcid}/{userId}")
    public Data videoList(@PathVariable("tcid") String tcId,@PathVariable("userId") String userId, VideoChapterUserVo vo)throws  Exception{
        SysUser stu = null;

        try {
            stu = adminService.getUserById(userId);
        } catch (Exception e) {
            throw new Exception("未找到该学生用户");
        }

        VideoChapter vc = new VideoChapter();
        vc.setCourseTeacherId(Integer.valueOf(tcId));
        startPage();
        List<VideoChapter> vcs = videoChapterService.list(vc);

        if (!CollectionUtils.isEmpty(vcs)) {
            SysUser finalStu = stu;
            vcs.forEach(v -> {
                ChapterResource cr = adminService.getChapterResourceById(String.valueOf(v.getResourceChapterId()));
                Chapter chapter = null;
                Recourse recourse = null;
                if (cr != null) {
                    chapter = adminService.getChapterById(cr.getCid());
                    recourse = adminService.getRecourseById(cr.getRid());
                }

                VideoChapterUser vcu = new VideoChapterUser();
                vcu.setVideoChapterId(Integer.valueOf(v.getId()));
                vcu.setUserId(Integer.valueOf(userId));

                Map<String, Object> params = new HashMap<>();
                List vcus = videoChapterUserService.list(vcu);
                if (!vcus.isEmpty()) {
                    params.put("videoChapterUser", vcus.get(0));
                } else {
                    params.put("videoChapterUser", null);
                }
                if (chapter != null) {
                    params.put("chapterName", chapter.getName());
                } else {
                    params.put("chapterName", "未找到章节");
                }
                if (recourse != null) {
                    params.put("resourceName", recourse.getName());
                } else {
                    params.put("resourceName", "未找到资源");
                }

                if (finalStu != null) {
                    params.put("studentName", finalStu.getUserName());
                }
                v.setParams(params);
            });
        }
        return Data.success(vcs);
    }
    @ApiOperation("学生测试详情")
    @PostMapping("/statis/showTestDetail/{userId}/{tcid}")
    public Data courseStatis(@PathVariable("userId") String userId,
                             @PathVariable("tcid") String tcId ){
        SysUser stu = adminService.getUserById(userId);
        TeacherCourse tc = adminService.getTeacherCourseById(tcId);
        TestPaper paper = new TestPaper();
        paper.setCoursrId(tc.getCid());
        paper.setCreateId(tc.getTid());
        paper.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
        List<TestPaper> testPapers = examService.testPaperList(paper);
        testPapers.forEach(p -> {
            Map<String, Object> params = new HashMap<>();
            params.put("stuNum", stu.getLoginName());
            params.put("stuName", stu.getUserName());
            params.put("userId", stu.getId());

            UserTest ut = new UserTest();
            ut.setcId(tc.getCid());
            ut.setUserId(userId);
            ut.setTestPaperId(p.getId());
            List uts = examService.selectUserTestListBase(ut);
            if (!uts.isEmpty()) {
                params.put("userTest", uts.get(0));
            } else {
                params.put("userTest", null);
            }

            p.setParams(params);
        });
        return Data.success(testPapers);
    }
    @ApiOperation("学生考试详情")
    @PostMapping("/statis/showExamDetail/{userId}/{tcid}")
    public Data courseExamStatis(@PathVariable("userId") String userId,
                             @PathVariable("tcid") String tcId ){
        SysUser stu = adminService.getUserById(userId);
        TeacherCourse tc = adminService.getTeacherCourseById(tcId);

        TestPaperOne paper = new TestPaperOne();
        paper.setCoursrId(tc.getCid());
        paper.setCreateId(tc.getTid());
        paper.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
        List<TestPaperOne> testPapers = examService.testPaperOneList(paper);

        testPapers.forEach(p -> {
            Map<String, Object> params = new HashMap<>();
            params.put("stuNum", stu.getLoginName());
            params.put("stuName", stu.getUserName());
            params.put("userId", stu.getId());

            UserExam ut = new UserExam();
            ut.setcId(tc.getCid());
            ut.setUserId(userId);
            ut.setTestPaperOneId(p.getId());
            List uts = examService.selectUserExamListBase(ut);
            if (!uts.isEmpty()) {
                params.put("userExam", uts.get(0));
            } else {
                params.put("userExam", null);
            }

            p.setParams(params);
        });
        return Data.success(testPapers);
    }
    @ApiOperation("学习统计")
    @PostMapping("/statis/{cid}/{tid}")
    public Data courseStatis(@PathVariable("cid") String cid,
                             @PathVariable("tid") String tid,
                             SysUser user,
                             Model model) {
        TeacherCourse tcI = new TeacherCourse();
        tcI.setCid(cid);
        tcI.setTid(tid);
        TeacherCourse tc = adminService.teacherCourseList(tcI).get(0);
        model.addAttribute("tid", tid);
        model.addAttribute("tcId", tc.getId());

        String clbumId = user.getNation();
        String loginName = user.getLoginName();
        if (user.getLoginName() == null || "".equals(user.getLoginName())) {
            loginName = null;
        }
        if (user.getNation() == null || "".equals(user.getNation())) {
            clbumId = null;
        }
        //  课堂班级学生列表
        startPage();
        List<SysUser> users = adminService.selectStudentByCourseIdAndTeacherId(cid, tid, loginName, clbumId);

        user.setNation(null);
        users.forEach(u -> {
            u.setPassword("");
            Student student = studentService.getById(u.getUserAttrId());
            Clbum clbum = adminService.getClbumById(student.getCid());

            AtomicReference<Integer> watchVideoCount = new AtomicReference<>(0);
            VideoChapter vc = new VideoChapter();
            vc.setCourseTeacherId(Integer.valueOf(tc.getId()));
            List<VideoChapter> vcs = videoChapterService.list(vc);
            VideoChapterUser videoUser = new VideoChapterUser();
            videoUser.setUserId(Integer.valueOf(u.getId()));
            vcs.forEach(v -> {
                videoUser.setVideoChapterId(Integer.valueOf(v.getId()));
                if (!videoChapterUserService.list(videoUser).isEmpty()) {
                    watchVideoCount.getAndSet(watchVideoCount.get() + 1);
                }
            });

            //TestPaper Count
            UserTest param = new UserTest();
            param.setcId(cid);
            param.setUserId(u.getId());
            param.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            List userTests = examService.selectUserTestListBase(param);
            UserExam param1 = new UserExam();
            param1.setcId(cid);
            param1.setUserId(u.getId());
            param1.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            List userExams = examService.selectUserExamListBase(param1);
            Map<String, Object> object = new HashMap<>();
            object.put("clbumName", clbum.getName());
            object.put("questionCount", userTests.size());
            object.put("questionCount1", userExams.size());
            object.put("watchVideoCount", watchVideoCount);
            object.put("tcid",tc.getId());
            u.setParams(object);

        });
        return Data.success(users);
    }

    /**
     * 视频播放
     * MultiValueMap<String, Object> params = new LinkedMultiValueMap<>(1);
     *         params.add("fileId", fid);
     *         Data result = restOperations.postForObject(STORAGE_URL_PREFIX + "/playVideo", params, Data.class);
     */
}
