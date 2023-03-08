package cn.wstom.admin.controller.school.ClassAdmin;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.admin.feign.ExamService;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.config.Global;
import cn.wstom.common.constant.Constants;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.web.page.PageDomain;
import cn.wstom.common.web.page.TableDataInfo;
import cn.wstom.common.web.page.TableSupport;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequestMapping("/classadmin")
public class ClassAdminController extends BaseController {
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private ClbumCourseService clbumCourseService;
    @Autowired
    private GradesService gradesService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private SysMenuService menuService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ExamService examService;
    @Autowired
    private VideoChapterService videoChapterService;
    @Autowired
    private VideoChapterUserService videoChapterUserService;
    @Autowired
    private ChapterResourceService chapterResourceService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private RecourseService recourseService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private PptChapterUserService pptChapterUserService;
    @GetMapping("/toStatis/")
    @Log(title = "学习统计")
//    @RequiresPermissions("teacher:course:view")
    public String toCourseStatis( ModelMap modelMap) {
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        modelMap.addAttribute("cid", cid);
        modelMap.addAttribute("tid", getUser().getUserAttrId());
        return "/school/classadmin/info";
    }


    /*
     * 跳转到教师下级管理
     * */
    @GetMapping("/totea_sds")
    public String toTeacherSdsadmin(ModelMap modelMap){
        return "school/teacher/classadmin/classadmin_index";
    }

    /**
     * 获取列表
     */
//    @RequiresPermissions("teacher:course:view")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        Clbum clbum=new Clbum();
        clbum.setTid(getUser().getUserAttrId());
        startPage();
        List<Clbum> clbums = clbumService.list(clbum);
        List<ClbumVo> clbumVos = trans(clbums);
//        clbumVos.forEach(t ->t.setGdid(t.getDepartment()+","+t.getGrades()));
        fillDepartments(clbumVos);
        PageInfo<SdsadminVo> pageInfo = new PageInfo(clbums);
        return wrapTable(clbums, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }



//        @RequiresPermissions("teacher:course:view")
    @GetMapping("/role/{id}")
    public String toList(ModelMap modelMap, @PathVariable String id) {
        /*
         * id为班级id
         * */
        System.out.println(id);
        List<SysMenu> menus1 = getSysMenus();
        WebUtils.getSession().setAttribute("cid",Integer.valueOf(id));
        Clbum clbum=clbumService.getById(id);
        WebUtils.getSession().setAttribute("name",clbum.getName());
        modelMap.put("cid", Integer.valueOf(id));
        modelMap.put("name", clbum.getName());
        modelMap.put("menus", menus1);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        return "/admin/classadminindex";
    }

    /**
     * 学习统计
     * @param model
     * @return
     */
    @PostMapping("/statis")
//    @RequiresPermissions("teacher:course:view")
    @ResponseBody
    public TableDataInfo courseStatis(StatisSysUser user, Model model){
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        System.out.println("cid"+cid);
        ClbumCourse clbumCourse=new ClbumCourse();
        clbumCourse.setCid(cid);
        List<ClbumCourse> list= clbumCourseService.list(clbumCourse);
        List<String> tclist=new ArrayList<>();
        list.forEach(t->{
            tclist.add(t.getTcid());
        });
//        tclist.add(list.get(0).getTcid());
        System.out.println("tclist"+tclist);
        String loginName = user.getLoginName();
        System.out.println("getNation23"+loginName);
        if (user.getLoginName() == null || user.getLoginName().equals("")) {
            loginName = null;
        }
        String tcid = user.getUserName();
        System.out.println("getNation"+tcid);
        StatisSysUser statisSysUser=new StatisSysUser();
        statisSysUser.setClbumId(cid);
        if (tcid!=null){
            List<String> tclist2=new ArrayList<>();
            tclist2.add(tcid);
            statisSysUser.setTcids(tclist2);
        }else {
            statisSysUser.setTcids(tclist);
        }
        statisSysUser.setLoginName(loginName);
        //  课堂班级学生列表
        startPage();
        List<StatisSysUser> users = sysUserService.selectStudentByCourseIdsAndTeacherIds(statisSysUser);
        System.out.println("startPage"+users);
        users.forEach(u -> {
            u.setPassword("");
            Student student = studentService.getStudentById(u.getUserAttrId());
            Clbum clbum = clbumService.getById(student.getCid());
            AtomicReference<Integer> watchVideoCount = new AtomicReference<>(0);
            VideoChapter vc = new VideoChapter();
            vc.setCourseTeacherId(Integer.valueOf(u.getTcid()));
            List<VideoChapter> vcs = videoChapterService.list(vc);
            VideoChapterUser videoUser = new VideoChapterUser();
            videoUser.setUserId(Integer.valueOf(u.getId()));
            vcs.forEach(v -> {
                videoUser.setVideoChapterId(Integer.valueOf(v.getId()));
                if (!videoChapterUserService.list(videoUser).isEmpty()) {
                    watchVideoCount.getAndSet(watchVideoCount.get() + 1);
                }
            });
            TeacherCourse teacherCourse =teacherCourseService.getById(u.getTcid());
            System.out.println("teacherCourse"+teacherCourse);
            //TestPaper Count
            UserTest param = new UserTest();
            param.setcId(teacherCourse.getCid());
            param.setUserId(u.getId());
            param.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            List userTests = examService.selectUserTestListBase(param);
            //PPT
            ChapterResource chapterResource = new ChapterResource();
            chapterResource.setTcId(u.getTcid());
            List<ChapterResource> crlist = chapterResourceService.list(chapterResource);
            List<String> crlist2=new ArrayList<>();
            crlist.forEach(c->{
                crlist2.add(c.getId());
            });

            PptChapterUser pptChapterUser=new PptChapterUser();
            pptChapterUser.setUserId(Integer.valueOf(u.getId()));
            pptChapterUser.setCrids(crlist2);
            List<PptChapterUser> pptChapterUserList=pptChapterUserService.list(pptChapterUser);
            AtomicInteger pptsize= new AtomicInteger();
            pptChapterUserList.forEach(p->{
                pptsize.set(p.getCout() + pptsize.get());
            });

            //考试
            UserExam param1 = new UserExam();
            param1.setcId(teacherCourse.getCid());
            param1.setUserId(u.getId());
            param1.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            List userExams = examService.selectUserExamListBase(param1);
            Map<String, Object> object = new HashMap<>();
            object.put("clbumName", clbum.getName());
            object.put("questionCount", userTests.size());
            object.put("pptCount", pptsize);
            object.put("questionCount1", userExams.size());
            object.put("watchVideoCount", watchVideoCount);
            u.setParams(object);
        });
        fillCourse(users);
        PageInfo pageInfo = new PageInfo(users);
        System.out.println("分页数据"+ pageInfo.getTotal()+"分页数据"+pageInfo.getPageNum()+"分页数据"+pageInfo.getPageSize()+"分页数据"+pageInfo.getPages());
        return wrapTable(users,pageInfo.getTotal(),pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getPages());
    }



    @GetMapping("/showvideoChapterUserDetail")
    public String tovideoChapterUserList(@RequestParam("id") String userId,  ModelMap modelMap) {
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        modelMap.put("userId", userId);
        modelMap.put("cid", cid);
        return "school/classadmin/video";
    }

    @GetMapping("/showUserPPTDetail")
    public String toshowUserPPTDetail(@RequestParam("id") String userId,  ModelMap modelMap) {
        System.out.println("schoolclassadminvideo");
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        modelMap.put("userId", userId);
        modelMap.put("cid", cid);
        return "school/classadmin/detailpptPaper";
    }




    /**
     * 查询用户观看PPT列表
     */
//    @RequiresPermissions("teacher:chapter:view")
    @PostMapping("/PPT/list/{userId}")
    @ResponseBody
    public TableDataInfo pptlist( @PathVariable("userId") String userId, VideoChapterUserVo vo) throws Exception{
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        System.out.println("classadmin---videoChapterUser");
        SysUser stu = null;
        try {
            stu = sysUserService.getById(userId);
        } catch (Exception e) {
            throw new Exception("未找到该学生用户");
        }

        ClbumCourse clbumCourse=new ClbumCourse();
        clbumCourse.setCid(cid);
        List<ClbumCourse> list= clbumCourseService.list(clbumCourse);
        List<String> tclist=new ArrayList<>();
        list.forEach(t->{
            tclist.add(t.getTcid());
        });
//        startPage();
        List<ChapterResource> chapterResources=new ArrayList<>();
        tclist.forEach(tcl->{
            ChapterResource cr = new ChapterResource();
            cr.setTcId(tcl);
            System.out.println("tcId====="+tcl);
            List<ChapterResource> crlist = chapterResourceService.list(cr);
            chapterResources.addAll(crlist);
        });
        List<String> crlist=new ArrayList<>();
        chapterResources.forEach(c->{
            crlist.add(c.getId());

        });
        PptChapterUser pptChapterUser=new PptChapterUser();
        pptChapterUser.setCrids(crlist);
        pptChapterUser.setUserId(Integer.valueOf(userId));
        System.out.println("list"+list);
        startPage();
        List<PptChapterUser> vcs = pptChapterUserService.list(pptChapterUser);
        if (!CollectionUtils.isEmpty(vcs)) {
            SysUser finalStu = stu;
            vcs.forEach(v -> {
                ChapterResource cr = chapterResourceService.getById(String.valueOf(v.getResourceChapterId()));
                Chapter chapter = null;
                Recourse recourse = null;
                if (cr != null) {
                    chapter = chapterService.getById(cr.getCid());
                    recourse = recourseService.getById(cr.getRid());
                }

                int cout=v.getCout();

                Map<String, Object> params = new HashMap<>();
                if (cout != 0) {
                    params.put("cout", cout);
                }else {
                    params.put("cout", "未阅读");
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

        return wrapTable(vcs);
    }


    /**
     * 查询用户观看列表
     */
//    @RequiresPermissions("teacher:chapter:view")
    @PostMapping("/videoChapterUser/list/{userId}")
    @ResponseBody
    public TableDataInfo list(@PathVariable("userId") String userId, VideoChapterUserVo vo) throws Exception{
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        System.out.println("classadmin---videoChapterUser");
        SysUser stu = null;
        try {
            stu = sysUserService.getById(userId);
        } catch (Exception e) {
            throw new Exception("未找到该学生用户");
        }

        ClbumCourse clbumCourse=new ClbumCourse();
        clbumCourse.setCid(cid);
        List<ClbumCourse> list= clbumCourseService.list(clbumCourse);
        List<String> tclist=new ArrayList<>();
        list.forEach(t->{
            tclist.add(t.getTcid());
        });
//        startPage();
        List<VideoChapter> vcs=new ArrayList<>();
        tclist.forEach(tcl->{
            VideoChapter vc = new VideoChapter();
            vc.setCourseTeacherId(Integer.valueOf(tcl));
            List<VideoChapter> vcsVo = videoChapterService.list(vc);
            vcs.addAll(vcsVo);
        });
        if (!CollectionUtils.isEmpty(vcs)) {
            SysUser finalStu = stu;
            vcs.forEach(v -> {
                ChapterResource cr = chapterResourceService.getById(String.valueOf(v.getResourceChapterId()));
                Chapter chapter = null;
                Recourse recourse = null;
                if (cr != null) {
                    chapter = chapterService.getById(cr.getCid());
                    recourse = recourseService.getById(cr.getRid());
                }

                VideoChapterUser vcu = new VideoChapterUser();
                vcu.setVideoChapterId(Integer.valueOf(v.getId()));
                System.out.println("VideoChapterId2====="+v.getId());
                vcu.setUserId(Integer.valueOf(userId));

                Map<String, Object> params = new HashMap<>();

                List vcus = videoChapterUserService.list(vcu);
                if (!vcus.isEmpty()) {
                    params.put("videoChapterUser", vcus.get(0));
                    System.out.println("???????"+vcus.get(0));
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
        return wrapTable(vcs);
    }


//    @RequiresPermissions("teacher:chapter:view")
    @GetMapping("/showUserTestDetail")
    public String toList(@RequestParam("id") String userId, ModelMap modelMap) {
        System.out.println("showUserTestDetail");
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        modelMap.put("userId", userId);
        modelMap.put("cid", cid);
        return "school/classadmin/detailPaper";
    }

    @RequiresPermissions("teacher:chapter:view")
    @PostMapping("/userTestDetail/list/{userId}")
    @ResponseBody
    public TableDataInfo toDetail(@PathVariable("userId") String userId) throws Exception {
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        SysUser stu = sysUserService.getById(userId);
        ClbumCourse clbumCourse=new ClbumCourse();
        clbumCourse.setCid(cid);
        List<ClbumCourse> list= clbumCourseService.list(clbumCourse);
        List<String> tclist=new ArrayList<>();
        list.forEach(t->{
            tclist.add(t.getTcid());
        });
        List<TestPaper> testPapers=new ArrayList<>();
        tclist.forEach(tcl->{
            TeacherCourse tc = teacherCourseService.getById(tcl);
            TestPaper paper = new TestPaper();
            paper.setCoursrId(tc.getCid());
            paper.setCreateId(tc.getTid());
            paper.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            PageDomain pageDomain = null;
            try {
                pageDomain = TableSupport.buildPageRequest();
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<TestPaper> testPapersVo = examService.testPaperList(paper,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
            testPapersVo.forEach(p -> {
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
            testPapers.addAll(testPapersVo);
        });

        return wrapTable(testPapers);
    }


//    @RequiresPermissions("teacher:chapter:view")
    @GetMapping("/showExamDetail")
    public String toExamList(@RequestParam("id") String userId, ModelMap modelMap) {
        System.out.println("showExamDetail");
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        modelMap.put("userId", userId);
        modelMap.put("cid", cid);
        return "school/classadmin/ExamdetailPaper";
    }


//    @RequiresPermissions("teacher:chapter:view")
    @PostMapping("/ExamDetail/list/{userId}")
    @ResponseBody
    public TableDataInfo toExamDetail(@PathVariable("userId") String userId) throws Exception {
        String cid=  WebUtils.getSession().getAttribute("cid").toString();
        SysUser stu = sysUserService.getById(userId);
        ClbumCourse clbumCourse=new ClbumCourse();
        clbumCourse.setCid(cid);
        List<ClbumCourse> list= clbumCourseService.list(clbumCourse);
        List<String> tclist=new ArrayList<>();
        list.forEach(t->{
            tclist.add(t.getTcid());
        });
        startPage();
        List<TestPaperOne> testPapers = new ArrayList<>();
        tclist.forEach(tcl->{
            TeacherCourse tc = teacherCourseService.getById(tcl);
            TestPaperOne paper = new TestPaperOne();
            paper.setCoursrId(tc.getCid());
            paper.setCreateId(tc.getTid());
            paper.setType(Constants.TEST_PAPER_TYPE_CHAPTER);
            List<TestPaperOne> testPapersvo = examService.testPaperOneList(paper);
            testPapersvo.forEach(p -> {
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
            testPapersvo.addAll(testPapersvo);
        });
        return wrapTable(testPapers);
    }


    /*
     * 获取系部信息
     * */
    private void fillDepartments(List<ClbumVo> sdsadminVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        sdsadminVos.forEach(t -> {
            t.setDepartment(departmentMap.get(String.valueOf(t.getDepartment())));
        });
    }

    /**
     * 批量转换clbumVo类型
     *
     * @param clbums
     * @return
     */
    private List<ClbumVo> trans(List<Clbum> clbums) {
        List<ClbumVo> clbumVos = new LinkedList<>();
        clbums.forEach(c -> clbumVos.add(trans(c)));
        return clbumVos;
    }
    /**
     * 转换clbumVo类型
     *
     * @param clbum
     * @return
     */
    private ClbumVo trans(Clbum clbum) {
        ClbumVo clbumVo = new ClbumVo();
        BeanUtils.copyProperties(clbum, clbumVo);
        return clbumVo;
    }

    private List<SysMenu> getSysMenus() {
        List<SysMenu> menus1 = menuService.selectMenusByUser(getUser());
        menus1.forEach(menus -> {
            menus.getChildren().forEach( c -> {
                c.setUrl(c.getUrl());
            });
        });
        return menus1;
    }


    /**
     * 获取课程信息
     *
     */
    private void fillCourse(List<StatisSysUser> statisSysUsers) {
        Map<String, Course> courseMap = courseService.map(null);
        statisSysUsers.forEach(t -> {
            TeacherCourse tc=teacherCourseService.getById(t.getTcid()) ;
            t.setCoursename((courseMap.get(tc.getCid())).getName());
        });
    }



    /**
     * 学习统计 获取班级
     */
    @GetMapping("/findClbumByCid")
    @RequiresPermissions("teacher:course:view")
    @ResponseBody
    public List<TeacherCourse> findClbums(@RequestParam("cid") String cid) {
        ClbumCourse clbumCourse=new ClbumCourse();
        clbumCourse.setCid(cid);
        List<ClbumCourse> list= clbumCourseService.list(clbumCourse);
        List<String> tclist=new ArrayList<>();
        list.forEach(t->{
            tclist.add(t.getTcid());
        });
        List<TeacherCourse> teacherCourseList=new ArrayList<>();
        Map<String, Course> courseMap = courseService.map(null);
        tclist.forEach(t->{
            TeacherCourse teacherCourse=teacherCourseService.getById(t);
            teacherCourse.setCoursename((courseMap.get(teacherCourse.getCid())).getName());
            teacherCourseList.add(teacherCourse);
        });
        System.out.println("teacherCourseList"+teacherCourseList);
        return teacherCourseList;
    }


}
