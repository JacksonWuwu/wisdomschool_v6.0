package cn.wstom.admin.controller.school.teacher;


import cn.wstom.admin.feign.ExamService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.ResourceConstant;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 课程章节 信息操作处理
 *
 * @author dws
 * @date 20190223
 */
@Controller
@RequestMapping("/teacher/chapter")
public class ChapterController extends BaseController {
    private String prefix = "/school/teacher/chapter";

    private final ChapterService chapterService;
    private final CourseService courseService;

    private final ChapterResourceService chapterResourceService;
    private final CourseResourceService courseResourceService;
    private final TeacherCourseService teacherCourseService;
    private final TeacherCourseExamService teacherCourseExamService;
    private final RecourseService recourseService;
    public long midTime = 1 * 60 * 1000/1000;

    @Autowired
    public ChapterController(ChapterService chapterService,
                             CourseService courseService, ChapterResourceService chapterResourceService,
                             CourseResourceService courseResourceService, TeacherCourseService teacherCourseService,
                             TeacherCourseExamService teacherCourseExamService, RecourseService recourseService) {
        this.chapterService = chapterService;
        this.courseService = courseService;
        this.chapterResourceService = chapterResourceService;
        this.courseResourceService = courseResourceService;
        this.teacherCourseService = teacherCourseService;
        this.teacherCourseExamService = teacherCourseExamService;
        this.recourseService = recourseService;
    }

    @Autowired
    private ExamService examService;
    @Autowired
    private VideoChapterService videoChapterService;

    @Value("${wstom.storageContextPath}")
    private String storageContextPath;

    @RequestMapping(value = "/chapterList")
    @ResponseBody
    List<Chapter> chapterList(@RequestBody Chapter chapter){
        return chapterService.list(chapter);
    }

    @RequestMapping("/getChapterResourceById/{chapterResourceId}")
    @ResponseBody
    ChapterResource getChapterResourceById(@PathVariable(value = "chapterResourceId")String chapterResourceId){
        return chapterResourceService.getById(chapterResourceId);
    }

    @RequestMapping(value = "/chapterResourceList")
    @ResponseBody
    List<ChapterResource> chapterResourceList(@RequestBody ChapterResource chapterResource){
        return chapterResourceService.list(chapterResource);
    }

    @RequestMapping(value = "/selectListByPreviewid")
    @ResponseBody
    List<Chapter>  selectListByPreviewid(@RequestBody Chapter chapter){
        return chapterService.selectListByPreviewid(chapter);
    }

    @RequestMapping("/updateChapterResource")
    @ResponseBody
    boolean updateChapterResource(@RequestBody ChapterResource chapterResource) throws Exception {
        return chapterResourceService.update(chapterResource);
    }
    @RequestMapping("/deleteChapterResource/{chapterResourceId}")
    @ResponseBody
    boolean deleteChapterResource(@PathVariable(value = "chapterResourceId")String chapterResourceId) throws Exception {
        return chapterResourceService.removeById(chapterResourceId);
    }

    @RequestMapping("/{chapterId}")
    @ResponseBody
    Chapter getChapterById(@PathVariable(value = "chapterId")String chapterId){
        return chapterService.getById(chapterId);
    }

    @RequestMapping("/get/{cid}")
    public String toList(ModelMap modelMap, @PathVariable ("cid")String cid) {
        modelMap.put("id", cid);
        return prefix + "/chapter_list";
    }

    @RequestMapping(value = "/getCourseChapterInfoTree/{cid}")
    List<Map<String, Object>> getCourseChapterInfoTree(@PathVariable(value = "cid")String cid){
        return chapterService.getCourseChapterInfoTree(cid);
    }
    /**
     * lzj
     * @param modelMap
     * @param cid
     * @return
     */

    @GetMapping("/getCourse/{cid}")
    public String toCourseList(ModelMap modelMap, @PathVariable String cid) {
        modelMap.put("id", cid);
        return prefix + "/listCourse";
    }

    /**
     * 新增保存课程章节
     */

    @Log(title = "课程章节", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(Chapter chapter) throws Exception {
        chapter.setCreateBy(getUser().getLoginName());
        return toAjax(chapterService.save(chapter));
    }

    /**
     * 新增课程
     */
    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        Chapter chapter;
        chapter = new Chapter();
        chapter.setPid(Constants.CHAPTER_PARENT_ID);
        chapter.setTitle("主目录");
        chapter.setCid(cid);
        modelMap.put("chapter", chapter);
        return prefix + "/add";
    }

    /**
     * 修改课程章节
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
        Chapter chapter = chapterService.getById(id);
        mmap.put("chapter", chapter);
        return prefix + "/edit";
    }

    /**
     * 修改保存课程章节
     */

    @Log(title = "课程章节", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Chapter chapter) throws Exception {
        return toAjax(chapterService.update(chapter));
    }

    /**
     * 删除课程章节
     */

    @Log(title = "课程章节", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(chapterService.removeById(ids));
    }


    /**
     * 加载课程章节树
     *
     * @param cid 课程id
     */
    @GetMapping("/chapterTreeData/{cid}")
    @ResponseBody
    public List<Map<String, Object>> getChapterTree(@PathVariable String cid) {
        return chapterService.getCourseChapterTree(cid);
    }
    /**
     * lzj
     */
    @GetMapping("/courseTreeData/{cid}")
    @ResponseBody
    public List<Map<String, Object>> getCourseTree(@PathVariable String cid) {
        return chapterService.getCourseTree(cid);
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

        map.addAttribute("resources", resources);
        System.out.println(resources);
        map.addAttribute("cid", chapterId);
        map.addAttribute("courseId", courseId);
        return prefix + "/chapterResource";
    }

    /**
     * lzj
     * @param map
     * @param courseId
     * @return
     */
    @GetMapping("/courseResource/{chapterId}/{courseId}")
    public String courseResource(@PathVariable String chapterId,ModelMap map, @PathVariable String courseId) {
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", courseId);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        ChapterResource cr = new ChapterResource();
        cr.setCid(chapterId);
        cr.setTcId(teacherCourse.getId());
        List<ChapterResource> resources = chapterResourceService.list(cr);
        map.addAttribute("resources", resources);
        System.out.println(resources);
        map.addAttribute("cid", chapterId);
        map.addAttribute("courseId", courseId);
        return prefix + "/courseResource";
    }



    /**
     * 保存课程资源
     *lzj
     * @param
     */
    @PostMapping("/saveChapterResourceOne")
    @ResponseBody
    public Data saveChapterResourceOne(
                                    ModelMap map,
                                    String courseId,
                                    String rid,
                                    Integer type,
                                    String length) throws Exception {
//        Assert.notNull(testPaperOneId, "非法参数");
        Assert.notNull(courseId, "非法参数");
        Assert.notNull(type, "非法参数");
        ChapterResource cr = new ChapterResource();
        Recourse recourse = recourseService.getById(rid);
        TeacherCourse teacherCourse = new TeacherCourse();
        if(type!=3&&type!=4){
            Assert.notNull(recourse, "非法参数");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("tid", getUser().getUserAttrId());
            //课程id
            params.put("cid", courseId);
            teacherCourse = teacherCourseService.getOne(params);
            cr.setTcId(teacherCourse.getId());
            cr.setCid(courseId);
            cr.setRid(rid);
            cr.setName(recourse.getName());
            cr.setExt(recourse.getExt());
        }
        else if(type==3){
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("tid", getUser().getUserAttrId());
            //课程id
            params.put("cid", courseId);
            teacherCourse = teacherCourseService.getOne(params);
            TestPaper testPaper = new TestPaper();
            testPaper = examService.getTestPaperById(rid);
            cr.setTcId(teacherCourse.getId());
            cr.setCid(courseId);
            cr.setRid(rid);
            cr.setExt("txt");
            cr.setName(testPaper.getName());
            testPaper.setState(Constants.EXAM_TYPE_DONE);
        } else{
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("tid", getUser().getUserAttrId());
            //课程id
            params.put("cid", courseId);
            teacherCourse = teacherCourseService.getOne(params);
            TestPaperOne testPaperOne = new TestPaperOne();
            testPaperOne = examService.getTestPaperOneById(rid);
            cr.setTcId(teacherCourse.getId());
            cr.setCid(courseId);
            cr.setRid(rid);
            cr.setExt("txt");
            cr.setName(testPaperOne.getName());
            testPaperOne.setState(Constants.EXAM_TYPE_DONE);
        }

        //  章节的资源添加
        switch (type) {
            case ResourceConstant.RESOURCE_TYPE_VIDEO:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_VIDEO);
                chapterResourceService.saveBackId(cr);

                System.out.println("获取视频长度：" + length);
                System.out.println("获取章节资源关联Id：" + cr.getId());
                //  tb_video_chapter    添加数据 teacherCourseId, chapterId, time,
                VideoChapter vc = new VideoChapter();
                vc.setCreateId(Integer.valueOf(getUserId()));
                vc.setCourseTeacherId(Integer.valueOf(teacherCourse.getId()));
                vc.setTime(Double.valueOf(length));
                vc.setResourceChapterId(Integer.valueOf(cr.getId()));
//                return Data.success();
                return toAjax(videoChapterService.save(vc));
            case ResourceConstant.RESOURCE_TYPE_COURSEWARE:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_COURSEWARE);
                break;
            case ResourceConstant.RESOURCE_TYPE_PAPER:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_PAPER);
                break;
            case ResourceConstant.RESOURCE_TYPE_PAPERONE:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_PAPERONE);
                break;
            default:
                throw new Exception();
        }
        cr.setResourceType(type);
        return toAjax(chapterResourceService.save(cr));
    }
    /**
     * 保存课程章节资源
     *
     * @param
     */
    @PostMapping("/saveChapterResource")
    @ResponseBody
    public Data saveChapterResource(String chapterId,
                                    String courseId,
                                    ModelMap map,
                                    String rid,
                                    Integer type,
                                    String length) throws Exception {
        Assert.notNull(chapterId, "非法参数");
        Assert.notNull(rid, "非法参数");
        Assert.notNull(type, "非法参数");
        ChapterResource cr = new ChapterResource();
        Recourse recourse = recourseService.getById(rid);
        TeacherCourse teacherCourse = new TeacherCourse();
        if(type!=3&&type!=4){
            Assert.notNull(recourse, "非法参数");
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("tid", getUser().getUserAttrId());
            //课程id
            params.put("cid", courseId);
            teacherCourse = teacherCourseService.getOne(params);
            cr.setTcId(teacherCourse.getId());
            cr.setCid(chapterId);
            cr.setRid(rid);
            cr.setName(recourse.getName());
            cr.setExt(recourse.getExt());
        }
        else if(type==3){
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("tid", getUser().getUserAttrId());
            //课程id
            params.put("cid", courseId);
            teacherCourse = teacherCourseService.getOne(params);
            TestPaper testPaper = new TestPaper();
            testPaper = examService.getTestPaperById(rid);
            cr.setTcId(teacherCourse.getId());
            cr.setCid(chapterId);
            cr.setRid(rid);
            cr.setExt("txt");
            cr.setName(testPaper.getName());
            testPaper.setState(Constants.EXAM_TYPE_DONE);
        } else{
            Map<String, Object> params = new LinkedHashMap<>();
            params.put("tid", getUser().getUserAttrId());
            //课程id
            params.put("cid", courseId);
            teacherCourse = teacherCourseService.getOne(params);
            TestPaperOne testPaperOne = new TestPaperOne();
            testPaperOne = examService.getTestPaperOneById(rid);
            cr.setTcId(teacherCourse.getId());
            cr.setCid(chapterId);
            cr.setRid(rid);
            cr.setExt("txt");
            cr.setState("进行中");
            cr.setName(testPaperOne.getName());
             long curren = System.currentTimeMillis();
            curren += 30 * 60 * 1000;
            Date da = new Date(curren);
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(da);
            Date nowDate = dateFormat.parse(date);
            System.out.println(dateFormat.format(da));
            System.out.println(nowDate);
            cr.setOverTime(nowDate);
            Timer timer = new Timer();

//            timer.schedule(new TimerTask() {
//            public void run() {
//                midTime--;
//                long hh = midTime / 60 / 60 % 60;
//                long mm = midTime / 60 % 60;
//                long ss = midTime % 60;
//                System.out.println("还剩" + hh + "小时" + mm + "分钟" + ss + "秒");
//
//            }
//        }, 0, 1000);
            testPaperOne.setState(Constants.EXAM_TYPE_DONE);
        }
        //  章节的资源添加
        switch (type) {
            case ResourceConstant.RESOURCE_TYPE_VIDEO:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_VIDEO);
                chapterResourceService.saveBackId(cr);

                System.out.println("获取视频长度：" + length);
                System.out.println("获取章节资源关联Id：" + cr.getId());
                //  tb_video_chapter    添加数据 teacherCourseId, chapterId, time,
                VideoChapter vc = new VideoChapter();
                vc.setCreateId(Integer.valueOf(getUserId()));
                vc.setChapterId(Integer.valueOf(chapterId));
                vc.setCourseTeacherId(Integer.valueOf(teacherCourse.getId()));
                vc.setTime(Double.valueOf(length));
                vc.setResourceChapterId(Integer.valueOf(cr.getId()));
//                return Data.success();
                return toAjax(videoChapterService.save(vc));
            case ResourceConstant.RESOURCE_TYPE_COURSEWARE:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_COURSEWARE);
                break;
            case ResourceConstant.RESOURCE_TYPE_PAPER:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_PAPER);
                break;
            case ResourceConstant.RESOURCE_TYPE_PAPERONE:
                cr.setResourceType(ResourceConstant.RESOURCE_TYPE_PAPERONE);
                break;
            default:
                throw new Exception();
        }
        cr.setResourceType(type);
        return toAjax(chapterResourceService.save(cr));
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 章节id
     * @return 视频教学资源页面
     */

    @GetMapping("/resource/list/{cid}/{type}")
    public String toVideoRecourse(@PathVariable String cid, ModelMap modelMap, @PathVariable Integer type) {
        Chapter chapter = chapterService.getById(cid);
        modelMap.put("chapter", chapter);
        modelMap.put("type", type);
        return prefix + "/resourceList";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 章节id
     * @return 视频教学资源页面
     */

    @GetMapping("/resource/editVideoRecourse/{cid}/{rid}")
    public String toeditVideoRecourse(@PathVariable String cid, ModelMap modelMap,@PathVariable String rid) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("cid", cid);
        params.put("rid", rid);
        ChapterResource chapterResource = chapterResourceService.getOne(params);
        Assert.notNull(chapterResource, "非法参数");
        VideoChapter chapter= videoChapterService.selectByRcId(Integer.valueOf(chapterResource.getId()));
        System.out.println("VideoChapter1111======="+chapter.getId());
        modelMap.put("chapter", chapter);
        return prefix + "/edit";
    }



//    //    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/editVideoChapter")
    @ResponseBody
    public Data editVideoChapter(VideoChapter chapter) throws Exception {
        System.out.println("VideoChapter2222========="+chapter.getId());
        return toAjax(videoChapterService.update(chapter));
    }


    /**
     * lzj
     * @param cid
     * @param modelMap
     * @param type
     * @return
     */

    @GetMapping("/resource/listOne/{cid}/{type}")
    public String toRecourse(@PathVariable String cid, ModelMap modelMap, @PathVariable Integer type) {
        Course course = courseService.getById(cid);
//        TeacherCourseExam teacherCourseExam = teacherCourseExamService.selectCourseId(cid);
//        modelMap.put("testPaperOneId",teacherCourseExam.getTestPaperOneId());
        modelMap.put("course", course);
        modelMap.put("courseId",course.getId());
        modelMap.put("type", type);
        return prefix + "/resourceListOne";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 章节id
     * @return 视频教学资源页面
     */

    @GetMapping("/resource/remove")
    @ResponseBody
    public Data delResource(String cid, String rid) throws Exception {
        Map<String, Object> params = new HashMap<>(2);
        params.put("cid", cid);
        params.put("rid", rid);
        ChapterResource resource = chapterResourceService.getOne(params);
        Assert.notNull(resource, "非法参数");

        return toAjax(chapterResourceService.removeById(resource.getId()));
    }

    /**
     * 加载课程章节树
     */
    @GetMapping("/chapterTree/{cid}")
    public String chapterTree(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/tree";
    }

    /**
     * 获取视频教学资源页面
     *
     * @param cid 章节id
     * @return 视频教学资源页面
     */

    @GetMapping("/toeditALLVideoRecourse/{cid}")
    public String toeditALLVideoRecourse(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        List<Integer> states= videoChapterService.selecttobeState(teacherCourse.getId());
        String state="1";
        for (int i: states){
            if (i!=1){
                state="0";
                break;
            }
        }
        modelMap.put("state", state);
        System.out.println("state"+state);
        modelMap.put("tcid", teacherCourse.getId());
        return prefix + "/editAll";
    }

    @PostMapping("/editAllVideoChapter/{tcid}")
    @ResponseBody
    public Data editALLVideoChapter(String state,@PathVariable String tcid) throws Exception {
        VideoChapter videoChapter=new VideoChapter();
        videoChapter.setCourseTeacherId(Integer.valueOf(tcid));
        videoChapter.setState(Integer.valueOf(state));
        return toAjax(videoChapterService.updatebytcid(videoChapter));
    }
}
