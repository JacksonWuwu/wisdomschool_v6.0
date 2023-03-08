package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.base.Data;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 课程管理
 *
 * @author dws
 * @date 2019/02/09
 */
@Controller
@RequestMapping("/teacher/preview")
public class PreviewController extends BaseController {


    private String prefix = "/school/teacher/preview";
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private SysUserService userService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PreviewService previewService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private ChapterResourceService chapterResourceService;
    @Autowired
    private PptChapterUserService pptChapterUserService;
    @Autowired
    private VideoChapterService videoChapterService;
    @Autowired
    private VideoChapterUserService videoChapterUserService;


    @RequestMapping(value = "/getPreviewById/{previewId}")
    @ResponseBody
    Preview getPreviewById(@PathVariable(value = "previewId")String previewId){
        return previewService.getById(previewId);
    }

    @RequestMapping(value = "/listBySidAndTcid")
    @ResponseBody
    List<String> listBySidAndTcid(@RequestBody PreviewStudent previewStudent){
        return previewService.listBySidAndTcid(previewStudent);
    }




    @GetMapping("/{cid}")
    public String tolist(ModelMap modelMap, @PathVariable String cid) {
        TeacherCourse teacherCourse=teacherCourseService.selectId(cid,getUser().getUserAttrId());
        String  tcid =teacherCourse.getId();
        modelMap.put("tcid", tcid);
        return prefix+"/list";
    }


    @PostMapping("/{tcid}/list")
    @ResponseBody
    public TableDataInfo list(@PathVariable String tcid,Preview preview) {
        System.out.println("preview"+preview);
//        Preview preview=new Preview();
        preview.setTcid(tcid);
        startPage();
        List<Preview> list = previewService.list(preview);
        fillClbum(list);
        fillchapter(list);
        PageInfo<Adjunct> pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    //    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/add")
    public String toadd(ModelMap modelMap, @PathVariable String tcid) {
        TeacherCourse teacherCourse=teacherCourseService.getById(tcid);
        String cid =teacherCourse.getCid();
        modelMap.put("id", cid);
//        List<Clbum> list =clbumService.selectBytcid(tcid);
        modelMap.put("tcid", tcid);
//        modelMap.put("list", list);
        return prefix+"/add";
    }

    /**
     * 查所有班级
     *
     * @return
     */

    @RequestMapping("/findClbum")
    @ResponseBody
    public List<Clbum> findTitleType(String pid) {
        List<Clbum> allSem = new ArrayList<>();
        Preview preview=previewService.getById(pid);
        String cids= preview.getCid();
        String[] userId = cids.split(",");//班级
        for (String s : userId) {
            Clbum clbum = clbumService.getById(s);
            allSem.add(clbum);
        }
        return allSem;
    }

    /**
     * 加载课程章节树
     *
     */
    @GetMapping("/{tcid}/chapterTreeData")
    @ResponseBody
    public List<Map<String, Object>> getChapterTree(@PathVariable String tcid) {
        TeacherCourse teacherCourse=teacherCourseService.getById(tcid);
        String cid =teacherCourse.getCid();
        return chapterService.getCourseChapterInfoTree(cid);
    }

    /**
     * 加载课程章节资源页面
     *
     * @param chapterId 章节id
     */
    @GetMapping("/chapterResource/{chapterId}/{courseId}")
    public String chapterResource(@PathVariable String chapterId, ModelMap map, @PathVariable String courseId) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", courseId);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        ChapterResource cr = new ChapterResource();
        cr.setCid(chapterId);
        cr.setTcId(teacherCourse.getId());
        List<ChapterResource> resources = chapterResourceService.list(cr);
        System.out.println("Preview-----resources"+resources);
        map.addAttribute("resources", resources);
        map.addAttribute("cid", chapterId);
        map.addAttribute("courseId", courseId);
        return  "/school/teacher/preview/chapterResource";
    }

    /**
     * 添加资源
     *
     * @return 添加结果
     */
//    @RequiresPermissions("teacher:course:view")
    @PostMapping("/addpreview/{tcid}")
    @ResponseBody
    public Data addVideoRecourse(Preview preview,@PathVariable String tcid) throws Exception {
        preview.setTcid(tcid);
        System.out.println("preview"+preview);
        int pid =previewService.addreturn(preview);
        List<PreviewChapter> list=new ArrayList<>();
        String[] Chapterlist=preview.getChapterids().split(",");
        for (String chapterid : Chapterlist) {
            PreviewChapter pc = new PreviewChapter(pid, Integer.valueOf(chapterid));
            list.add(pc);
        }
        return toAjax(previewService.insertPreviewChapter(list));
    }


    //    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/edit/{id}")
    public String toedit(ModelMap modelMap, @PathVariable String id,@PathVariable String tcid) {
        Preview preview=previewService.getById(id);
        System.out.println("preview"+preview);
        modelMap.put("preview", preview);
//        List<Clbum> list =clbumService.selectBytcid(tcid);
        modelMap.put("tcid", tcid);
//        modelMap.put("list", list);
        return prefix+"/edit";
    }

    /**
     * 删除
     */
//    @RequiresPermissions("module:clbum:remove")
    @PostMapping("{tcid}/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(previewService.removeById(ids));
    }


    @GetMapping("/fabu")
    @RequiresPermissions("teacher:course:view")
    public String fabu(String id,ModelMap modelMap){
        modelMap.put("id",id);
        System.out.println("fabu pid:"+id);
        Preview preview=previewService.getById(id);
        TeacherCourse teacherCourse=teacherCourseService.getById(preview.getTcid());
        System.out.println("fabu---teacherCourse======="+teacherCourse);
        modelMap.put("cid",teacherCourse.getCid());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("tid", preview.getTcid());
        System.out.println("###################");
        return prefix + "/fabu";
    }

    //    @RequiresPermissions("teacher:course:view")
//    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/addfabu")
    @ResponseBody
    public Data addfabu(Preview preview) throws Exception {
        System.out.println("preview---getCid====" + preview);
        preview.setCid(preview.getUserId());
        Preview preview2=previewService.getById(preview.getId());
        preview.setTcid(preview2.getTcid());
        preview.setState(1);
        String[] userId = preview.getUserId().split(",");
        List<PreviewStudent> list = new ArrayList<>();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < userId.length; i++) {
                Clbum clbum = clbumService.getById(userId[i]);
                Student student = new Student();
                student.setCid(clbum.getId());
                List<Student> studentList = studentService.studentList(student);
                studentList.forEach(s -> {
                    PreviewStudent previewStudent = new PreviewStudent();
                    previewStudent.setSid(Integer.valueOf(s.getId()));
                    previewStudent.setPid(Integer.valueOf(preview.getId()));
                    previewStudent.setTcid(Integer.valueOf(preview2.getTcid()));
                    list.add(previewStudent);
                });
            }
            return toAjax(previewService.update(preview)&&previewService.insertPreviewStudent(list));
        }



//    @RequiresPermissions("teacher:chapter:view")
    @GetMapping("/detail")
    public String toList(String id, ModelMap modelMap) {
        modelMap.put("pid", id);
        return prefix +"/info";
    }

    /**
     * 学习统计
     * @param model
     * @return
     */
    @PostMapping("/statis/{pid}")
    @RequiresPermissions("teacher:course:view")
    @ResponseBody
    public TableDataInfo courseStatis(@PathVariable("pid") String pid, SysUser user, Model model) throws Exception{
        System.out.println("statisdsdsdsdsd");
        String clbumId = user.getNation();
        String loginName = user.getLoginName();

        if (user.getLoginName() == null || user.getLoginName().equals("")) {
            loginName = null;
        }
        if (user.getNation() == null || user.getNation().equals("")) {
            clbumId = null;
        }
        Preview preview=previewService.getById(pid);
        model.addAttribute("tcId", preview.getTcid());

        List<SysUser> users=new ArrayList<>();
        String[] userId = preview.getCid().split(",");//班级 ;
        for (int i = 0; i < userId.length; i++) {
            users.addAll(userService.selectStudentByclbumId(loginName, userId[i]));
        }
        String[] chapterids= preview.getChapterids().split(",");
        List<String> list=new ArrayList<>();
        for (int i = 0; i < chapterids.length; i++) {
            list.add(chapterids[i]);
        }
        System.out.println("list"+list);
        //  课堂班级学生列表


//        ArrayList<SysUser> users=(ArrayList<SysUser> )users1.clone();

        user.setNation(null);
        users.forEach(u -> {
            u.setPassword("");
            Student student = studentService.getStudentById(u.getUserAttrId());
            Clbum clbum = clbumService.getById(student.getCid());

            AtomicReference<Integer> watchVideoCount = new AtomicReference<>(0);
            VideoChapter vc = new VideoChapter();
            vc.setCourseTeacherId(Integer.valueOf(preview.getTcid()));
            List<VideoChapter> vcs = videoChapterService.list(vc);
            VideoChapterUser videoUser = new VideoChapterUser();
            videoUser.setUserId(Integer.valueOf(u.getId()));
            vcs.forEach(v -> {
                videoUser.setVideoChapterId(Integer.valueOf(v.getId()));
                if (!videoChapterUserService.list(videoUser).isEmpty()) {
                    watchVideoCount.getAndSet(watchVideoCount.get() + 1);
                }
            });
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
            System.out.println("pptsize"+pptsize);
            Map<String, Object> object = new HashMap<>();
            object.put("clbumName", clbum.getName());
            object.put("pptCount", pptsize);
            object.put("watchVideoCount", watchVideoCount);
            u.setParams(object);
        });
        startPage();
//        List user1=listToPage(,10,users);
//        List<SysUser> user1=new ArrayList<>();
//        user1.addAll(users);
        PageInfo pageInfo = new PageInfo(users);
        return wrapTable(users,new PageInfo<>(users).getTotal());
    }



    private List listToPage(int currentPage, int rows, List list) {

        currentPage = (currentPage - 1) * rows;   //每页的起始索引
        Integer sum = list.size(); //记录总数
        if (currentPage + rows > sum) {
            return list.subList(currentPage, sum);
        } else {
            return list.subList(currentPage, currentPage + rows);
        }
    }



    private void fillClbum(List<Preview> previews) {
//        String[] userId = adjunct.getUserId().split(",");//班级
        Map<String, Clbum> clbumMap = clbumService.map(null);
        previews.forEach(t -> {
            if (t.getCid()!=null){
                String[] cids = t.getCid().split(",");//班级
                if (cids!=null){
                    List<String> clbumNames =new ArrayList<>();
                    for (int i = 0; i < cids.length; i++) {
                        String clbumname=(clbumMap.get(cids[i])).getName();
                        clbumNames.add(clbumname);
                    }
                    t.setClbumName(clbumNames);
                }
            }
        });

    }
    private void fillchapter(List<Preview> previews) {
//        String[] userId = adjunct.getUserId().split(",");//班级
        Map<String, Chapter> chapterMap = chapterService.map(null);
        previews.forEach(t -> {
            if (t.getChapterids()!=null){
                String[] cids = t.getChapterids().split(",");//班级
                if (cids!=null){
                    List<String> chapterNames =new ArrayList<>();
                    for (int i = 0; i < cids.length; i++) {
                        String clbumname=(chapterMap.get(cids[i])).getTitle()+(chapterMap.get(cids[i])).getName();
                        chapterNames.add(clbumname);
                    }
                    t.setChapterName(chapterNames);
                }
            }
        });
    }



}
