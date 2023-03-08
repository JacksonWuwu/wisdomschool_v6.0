package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.feign.ExamService;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.constant.Constants;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.PageDomain;
import cn.wstom.common.web.page.TableDataInfo;
import cn.wstom.common.web.page.TableSupport;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/teacher/statis")
public class StatisController extends BaseController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private ExamService examService;
    @Autowired
    private VideoChapterService videoChapterService;
    @Autowired
    private VideoChapterUserService videoChapterUserService;
    @Autowired
    private PptChapterUserService pptChapterUserService;

    @Autowired
    private ChapterResourceService chapterResourceService;
    /**
     * 学习统计
     * @param cid
     * @param tid
     * @param model
     * @return
     */
    @PostMapping("/{cid}/{tid}")
    @ResponseBody
    public TableDataInfo courseStatis(@PathVariable("cid") String cid,
                                      @PathVariable("tid") String tid,
                                      SysUser user,
                                      Model model) throws Exception{
        TeacherCourse tcI = new TeacherCourse();
        tcI.setCid(cid);
        tcI.setTid(tid);
        TeacherCourse tc = teacherCourseService.list(tcI).get(0);
        model.addAttribute("tid", tid);
        model.addAttribute("tcId", tc.getId());
        String clbumId = user.getNation();
        String loginName = user.getLoginName();
        if (user.getLoginName() == null || user.getLoginName().equals("")) {
            loginName = null;
        }
        if (user.getNation() == null || user.getNation().equals("")) {
            clbumId = null;
        }

        ChapterResource chapterResource = new ChapterResource();
        chapterResource.setTcId(tc.getId());
        System.out.println("tcId====="+tc.getId());
        List<ChapterResource> crlist = chapterResourceService.list(chapterResource);
        List<String> list=new ArrayList<>();
        crlist.forEach(c->{
            list.add(c.getId());

        });
        //  课堂班级学生列表
        startPage();
        List<SysUser> users = userService.selectStudentByCourseIdAndTeacherId(cid, tid, loginName, clbumId);
        user.setNation(null);
        users.forEach(u -> {
            u.setPassword("");
            Student student = studentService.getStudentById(u.getUserAttrId());
            Clbum clbum = clbumService.getById(student.getCid());
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
            UserTest userTest = new UserTest();
            userTest.setcId(cid);
            userTest.setUserId(u.getId());
            userTest.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            List<UserTest> userTests = examService.selectUserTestListBase(userTest);


            //PPT
            PptChapterUser pptChapterUser=new PptChapterUser();
            pptChapterUser.setUserId(Integer.valueOf(u.getId()));
            pptChapterUser.setCrids(list);
            List<PptChapterUser> pptChapterUserList=pptChapterUserService.list(pptChapterUser);
            AtomicInteger pptsize= new AtomicInteger();
            pptChapterUserList.forEach(p->{
//                int pptsize2=pptsize;
                 pptsize.set(p.getCout() + pptsize.get());
            });

            System.out.println(pptsize);
            //考试
            UserExam param1 = new UserExam();
            param1.setcId(cid);
            param1.setUserId(u.getId());
            param1.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            Map<String, Object> object = new HashMap<>();
            object.put("clbumName", clbum.getName());
            object.put("questionCount", userTests.size());
            object.put("pptCount", pptsize);
//            object.put("questionCount1", userExams.size());
            object.put("watchVideoCount", watchVideoCount);
            u.setParams(object);
        });

        return wrapTable(users);
    }



    @GetMapping("/showDetail")
    public String toList(@RequestParam("id") String userId, @RequestParam("tcId") String tcId, ModelMap modelMap) {
        modelMap.put("userId", userId);
        modelMap.put("tcId", tcId);
        return "school/teacher/statis/detailPaper";
        }







    @GetMapping("/showExamDetail")
    public String toExamList(@RequestParam("id") String userId, @RequestParam("tcId") String tcId, ModelMap modelMap) {
        modelMap.put("userId", userId);
        modelMap.put("tcId", tcId);
        return "school/teacher/statis/ExamdetailPaper";
    }


    @PostMapping("/showDetail/{userId}/{tcId}")
    @ResponseBody
    public TableDataInfo toDetail(@PathVariable("userId") String userId, @PathVariable("tcId") String tcId) throws Exception {
        SysUser stu = sysUserService.getById(userId);
        TeacherCourse tc = teacherCourseService.getById(tcId);

        TestPaper paper = new TestPaper();
        paper.setCoursrId(tc.getCid());
        paper.setCreateId(tc.getTid());
        paper.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<TestPaper> testPapers = examService.testPaperList(paper,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
        testPapers.forEach(p -> {
            Map<String, Object> params = new HashMap<>();
            params.put("stuNum", stu.getLoginName());
            params.put("stuName", stu.getUserName());
            params.put("userId", stu.getId());
            UserTest ut = new UserTest();
            ut.setcId(tc.getCid());
            ut.setUserId(userId);
            System.out.println(p.getId());
            ut.setTestPaperId(p.getId());
            List uts = examService.selectUserTestListBase(ut);
            if (!uts.isEmpty()) {
                params.put("userTest", uts.get(0));
            } else {
                params.put("userTest", null);
            }

            p.setParams(params);
        });
        return wrapTable(testPapers);
    }



    @PostMapping("/showExamDetail/{userId}/{tcId}")
    @ResponseBody
    public TableDataInfo toExamDetail(@PathVariable("userId") String userId, @PathVariable("tcId") String tcId) throws Exception {
        SysUser stu = sysUserService.getById(userId);
        TeacherCourse tc = teacherCourseService.getById(tcId);

        TestPaperOne paper = new TestPaperOne();
        paper.setCoursrId(tc.getCid());
        paper.setCreateId(tc.getTid());
        paper.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
        startPage();
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
                params.put("userTest", uts.get(0));
            } else {
                params.put("userTest", null);
            }

            p.setParams(params);
        });
        return wrapTable(testPapers);
    }
}
