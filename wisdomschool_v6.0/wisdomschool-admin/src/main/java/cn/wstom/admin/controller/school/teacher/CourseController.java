package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.exception.ApplicationException;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.config.Global;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.StorageConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.exception.file.FileNameLimitExceededException;
import cn.wstom.common.utils.FileUtils;
import cn.wstom.common.utils.StringUtil;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 教师课程
 * @author dws
 * @date 2019/02/18
 */
@Controller
@RequestMapping("/teacher/course")
public class CourseController extends BaseController {
    private String prefix = "/school/teacher/course";

    /**
     * 页面操作类型，展示或编辑
     */
    private static final int OPT_TYPE_VIEW = 0;
    private static final int OPT_TYPE_EDIT = 1;

    @Value("${wstom.contextPath}")
    private String contextPath;

    @Resource
    private TopicService topicService;

    private final TeacherCourseService teacherCourseService;
    private final TeacherCourseExamService teacherCourseExamService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final SysUserService userService;
    private final IntegralDetailService integralDetailService;


    private final ForumService forumService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private ReplyService replySerivce;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private ClbumCourseService clbumCourseService;


    @Autowired
    public CourseController(TeacherCourseService teacherCourseService,
                            TeacherCourseExamService teacherCourseExamService, CourseService courseService,
                            TeacherService teacherService,
                            SysUserService userService,
                            IntegralDetailService integralDetailService,
                             ForumService forumService) {
        this.teacherCourseService = teacherCourseService;
        this.teacherCourseExamService = teacherCourseExamService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.userService = userService;
        this.integralDetailService = integralDetailService;
        this.forumService = forumService;
    }

    @RequestMapping(value = "/getCourseById/{courseId}")
    @ResponseBody
    Course getCourseById(@PathVariable(value = "courseId")String courseId){
        return courseService.getById(courseId);
    }

    @RequestMapping(value = "/selectCoursesByStuId/{studentId}")
    @ResponseBody
    List<ClbumCourseVo> selectCoursesByStuId(@PathVariable(value = "studentId")int studentId){
        return courseService.selectCoursesByStuId(studentId);
    }
    @RequestMapping(value = "/selectClbumCourseVosByTeacherId/{teacherId}")
    @ResponseBody
    List<ClbumCourseVo> selectClbumCourseVosByTeacherId(String teacherId){
        return courseService.selectClbumCourseVosByTeacherId(teacherId);
    }

    @RequestMapping(value = "/teacher/course/getCourseCommentListPage")
    @ResponseBody
    PageVo<Comment> getCourseCommentListPage(
            @RequestParam("typeId") String typeId,
            @RequestParam("userId") String userId,
            @RequestParam("createTime") String createTime,
            @RequestParam("parentId") String parentId,
            @RequestParam("orderBy") String orderBy,
            @RequestParam("order") String order,
            @RequestParam("pageNum") int  pageNum,
            @RequestParam("pageSize") int pageSize){
        return courseService.getCourseCommentListPage(typeId,userId,createTime,parentId,orderBy,order,pageNum,pageSize);
    }



    @RequestMapping(value = "/courseList")
    @ResponseBody
    List<Course> courseList (@RequestBody Course course){
        return courseService.list(course);
    }


    @RequestMapping(value = "/courseCount")
    @ResponseBody
    int courseCount(@RequestBody Map<String, Object> params){
        return clbumCourseService.count(params);
    }

    @RequestMapping(value = "/courseMap")
    @ResponseBody
    Map<String, Course> courseMap(@RequestBody Course course){
        return courseService.map(course);
    }

    @RequestMapping(value = "/courseMapByClbumId")
    @ResponseBody
    Map<String, Course> courseMapByClbumId(@RequestBody  List<String> cid){
        return courseService.mapByClbumId(cid);
    }

    @GetMapping("/{cid}")
    public String toList(ModelMap modelMap, @PathVariable String cid) {
        List<SysMenu> menus1 = getSysMenus(cid);
        List<SysRole> roles=sysRoleService.selectAllRolesByUserId(getUserId());
        Map<String, Object> params = new HashMap<>(2);
        params.put("cid", cid);
        params.put("tid", getUser().getUserAttrId());
        TeacherCourseVo tcVo = new TeacherCourseVo();
        TeacherCourse tc = teacherCourseService.getOne(params);
        Course course = courseService.getById(cid);
        modelMap.put("course", course);
        modelMap.put("teacherCourse", tc);
        modelMap.put("menus", menus1);
        modelMap.put("nowrole", "教师");
        modelMap.put("sysUser", getUser());
        modelMap.put("roles", roles);
        modelMap.put("copyrightYear", Global.getCopyrightYear());
        return "/school/teacher/course";
    }



    private List<SysMenu> getSysMenus(@PathVariable String cid) {
        SysUser sysUser = getUser();
        List<SysMenu> menus1 = menuService.selectMenusByUserId_teacher(sysUser.getId());
        menus1.forEach(menus -> {
            menus.getChildren().forEach( c -> {
                c.setUrl(c.getUrl() + "/" + cid);
            });
        });
        return menus1;
    }

    /**
     * 获取我的课程列表
     */

    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        TeacherCourse tc = new TeacherCourse();
        tc.setTid(getUser().getUserAttrId());
        startPage();
        List<TeacherCourse> list = teacherCourseService.list(tc);

        PageInfo<TeacherCourse> pageInfo = new PageInfo(list);
        List<TeacherCourseVo> tcVo = trans(list);
        return wrapTable(tcVo, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }


    @GetMapping("/edit/{cid}")
    public String toEdit(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        Assert.notNull(teacherCourse, "非法参数");

        TeacherCourseVo tcVo = new TeacherCourseVo();
        BeanUtils.copyProperties(teacherCourse, tcVo);

        fillCourse(tcVo);
        modelMap.put("teacherCourse", tcVo);
        return prefix + "/edit";
    }

    @GetMapping("/editexam/{cid}")
    public String toEditExam(@PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("cid", cid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        Assert.notNull(teacherCourse, "非法参数");
        TeacherCourseVo tcVo = new TeacherCourseVo();
        TeacherCourse tc = teacherCourseService.getOne(params);
        Assert.notNull(tc, "非法参数！");
        BeanUtils.copyProperties(tc, tcVo);
        Course course = courseService.getById(tcVo.getCid());
        tcVo.setCourse(course);
        modelMap.put("course",course);
        BeanUtils.copyProperties(teacherCourse, tcVo);

        fillCourse(tcVo);
        modelMap.put("teacherCourse", tcVo);
        return prefix + "/examedit";
    }


    @GetMapping("/topicManager/{tcid}")
    public String topicManager(@PathVariable String tcid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("tid", getUser().getUserAttrId());
        params.put("tcid", tcid);
        TeacherCourse teacherCourse = teacherCourseService.getOne(params);
        Assert.notNull(teacherCourse, "非法参数");

        TeacherCourseVo tcVo = new TeacherCourseVo();
        BeanUtils.copyProperties(teacherCourse, tcVo);

        fillCourse(tcVo);
        modelMap.put("teacherCourse", tcVo);
        return prefix + "/edit";
    }

    /**
     * 修改课程
     */

    @Log(title = "教师课程", actionType = ActionType.UPDATE)
    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Data edit(TeacherCourseVo teacherCourseVo, MultipartFile thumbnail) throws Exception {
        String filename = null;
        if (!thumbnail.isEmpty()) {
            try {
                //保存图片
                Data result = FileUploadUtils.upload(getUserId(),StorageConstants.STORAGE_THUMBNAIL, thumbnail, false, FileUtils.allowImage);
                System.out.println(result);
                if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
                    return result;
                }
                filename = (String) result.get(StorageConstants.FILE_ID);
            } catch (FileNameLimitExceededException | IOException | ApplicationException e) {
                return Data.error(e.getMessage());
            }
        }
        TeacherCourse tc = new TeacherCourse();
        BeanUtils.copyProperties(teacherCourseVo, tc);
        tc.setCreateBy(getUser().getLoginName());
        tc.setThumbnailPath(filename);
        return toAjax(teacherCourseService.update(tc));
    }

    @Log(title = "教师课程", actionType = ActionType.UPDATE)
    @PostMapping(value = "/editexam", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Data editExam(TeacherCourseExamVo teacherCourseExamVo,Integer testPaperOneId) throws Exception {
        TeacherCourseExam tc = new TeacherCourseExam();
        BeanUtils.copyProperties(teacherCourseExamVo, tc);
        tc.setCreateBy(getUser().getLoginName());
        tc.setTestPaperOneId(testPaperOneId);


        return toAjax(teacherCourseExamService.update(tc));
    }
    /**
     * 获取课程页面
     *
     * @param opt 操作类型 见变量说明
     * @return 课程页面或课程信息路径
     */
    @ApiOperation("课程信息")

    @GetMapping("/get/{opt}/{cid}")
    public String course(@PathVariable(required = false) Integer opt, @PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("cid", cid);
        params.put("tid", getUser().getUserAttrId());
        TeacherCourseVo tcVo = new TeacherCourseVo();
        TeacherCourse tc = teacherCourseService.getOne(params);
        Assert.notNull(tc, "非法参数！");
        BeanUtils.copyProperties(tc, tcVo);
        Course course = courseService.getById(tcVo.getCid());
        tcVo.setCourse(course);
        modelMap.put("teacherCourse", tcVo);


        if (opt == OPT_TYPE_EDIT) {
            return prefix + "/info";
        }
        return prefix + "/course";
    }

    /**
     * lzj
     * @param opt
     * @param cid
     * @param modelMap
     * @return
     */
    @ApiOperation("课程信息")

    @GetMapping("/getExam/{opt}/{cid}")
    public String courseExam(@PathVariable(required = false) Integer opt, @PathVariable String cid, ModelMap modelMap) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("cid", cid);
        params.put("tid", getUser().getUserAttrId());
        TeacherCourseVo tcVo = new TeacherCourseVo();
        TeacherCourse tc = teacherCourseService.getOne(params);
        Assert.notNull(tc, "非法参数！");
        BeanUtils.copyProperties(tc, tcVo);
        Course course = courseService.getById(tcVo.getCid());
        tcVo.setCourse(course);
        modelMap.put("course",course);
        modelMap.put("teacherCourse", tcVo);

        if (opt == OPT_TYPE_EDIT) {
            return prefix + "/infoexam";
        }
        return prefix + "/course";
    }
    /**
     *  课题内话题评论管理
     * @param cid
     * @return
     */
    @GetMapping("/comment/{cid}")

    public String toCourseComment(@PathVariable("cid") String cid, ModelMap modelMap) {
        TeacherCourse t = new TeacherCourse();
        t.setCid(cid);
        TeacherCourse t1 = teacherCourseService.list(t).get(0);
        if (t1 != null) {
            List<Topic> topics = topicService.getCourseAllTopic(t1.getId(), 1);
            modelMap.put("topics", topics);
        }
        return "/school/comment/list";
    }

    @PostMapping("/comment/{cid}")
    @ResponseBody
    public String toCourseCommentList(@PathVariable("cid") String cid) {
        return "/school/comment/list";
    }


    @GetMapping("/toStatis/{cid}")
    @Log(title = "学习统计")
    public String toCourseStatis(@PathVariable("cid") String cid, ModelMap modelMap) {
        modelMap.put("cid", cid);
        modelMap.put("tid", getUser().getUserAttrId());
        TeacherCourse tcI = new TeacherCourse();
        tcI.setCid(cid);
        tcI.setTid(getUser().getUserAttrId());
        TeacherCourse tc = teacherCourseService.list(tcI).get(0);
        modelMap.put("tcId", tc.getId());
        return "/school/teacher/statis/info";
    }

    @GetMapping("/toVideoStatis/{cid}")
    @Log(title = "观看统计")

    public String toVideoStatis(@PathVariable("cid") String cid, ModelMap modelMap) {
        TeacherCourse tc = new TeacherCourse();
        tc.setCid(cid);
        tc.setTid(userService.getById(getUserId()).getUserAttrId());
        tc = teacherCourseService.list(tc).get(0);
        modelMap.put("tcid", tc.getId());
        return "/school/teacher/statis/video";
    }

    /**
     * 学习统计 获取班级
     */
    @GetMapping("/findClbumByTcid")

    @ResponseBody
    public List<Clbum> findClbums(@RequestParam("tcId") String tcId) {
        ClbumCourse clbumCourse = new ClbumCourse();
        clbumCourse.setTcid(tcId);
        List<ClbumCourse> clbumCourses = clbumCourseService.list(clbumCourse);
        List<Clbum> clbums = new ArrayList<Clbum>();
        for(ClbumCourse c :clbumCourses){
            Clbum index = clbumService.getById(c.getCid());
            clbums.add(index);
        }
        return clbums;
    }

    @GetMapping("/forum/toList/{cid}")

    public String toForum(@PathVariable("cid") String cid, ModelMap modelMap, HttpServletRequest request) {
        if (!"".equals(cid)) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("cid", cid);
            params.put("tid", getUser().getUserAttrId());
            TeacherCourse tc = teacherCourseService.getOne(params);
            modelMap.put("tcid", tc.getId());
            modelMap.put("id", cid);
        }
        return "/school/teacher/course/forum";
    }

    @GetMapping("/forum/add")

    public String toForumAdd(ModelMap modelMap, HttpServletRequest request) {
        String cid = ServletRequestUtils.getStringParameter(request, "cid", "");
        if (!"".equals(cid)) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("cid", cid);
            params.put("tid", getUser().getUserAttrId());
            TeacherCourse tc = teacherCourseService.getOne(params);
            modelMap.put("tcid", tc.getId());
        }
        return "/school/teacher/course/forumAdd";
    }

    @PostMapping("/forum/list")

    @ResponseBody
    public TableDataInfo forumList(HttpServletRequest request, ModelMap modelMap) {
        String cid = ServletRequestUtils.getStringParameter(request, "cid", "");
        Forum forum = new Forum();
        if (!"".equals(cid)) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("cid", cid);
            params.put("tid", getUser().getUserAttrId());
            TeacherCourse tc = teacherCourseService.getOne(params);
            modelMap.put("tcid", tc.getId());
            forum.setTcid(tc.getId());
        }

        startPage();
        List<Forum> list = forumService.list(forum);
        return wrapTable(list);
    }

    @PostMapping("/forum/add")

    @ResponseBody
    public Data forumAdd(HttpServletRequest request, Forum forum) throws Exception {
        forum.setCreateBy(getLoginName());
        if (forum.getTcid() == null || "".equals(forum.getTcid())) {
            forum.setTcid(null);
        }
        return toAjax(forumService.save(forum));
    }

    @PostMapping("/forum/remove")

    @Log(title = "节的试卷", actionType = ActionType.DELETE)
    @ResponseBody
    public Data forumMove(String ids) throws Exception {
        return toAjax(forumService.removeById(ids));
    }

    /**
     * 评论管理
     */
    @ApiOperation("评论管理")

    @GetMapping("/comment/list")
    public String commentList() {
        return "/school/teacher/comment/list";
    }

    @ApiOperation("评论管理")

    @PostMapping("/comment/list")
    public TableDataInfo commentList(Reply reply) {
        return wrapTable(replySerivce.list(reply));
    }

    @PostMapping("/weekStatis/{cid}/{tid}")

    @ResponseBody
    public TableDataInfo courseWeekStatis(@PathVariable("cid") String cid, @PathVariable String tid) {

        List<SysUser> users = userService.selectStudentByCourseIdAndTeacherId(cid, tid);

        List<IntegralDetail> integralDetails;
        integralDetails = integralDetailService.selectBatchIntegral();
        return wrapTable(integralDetails);
    }

    /**
     * 批量转换teacherVo类型
     *
     * @param teacherCourses 将<code>TeacherCourse</code>类型转为<code>TeacherCourseVo</code>
     * @return <code>TeacherCourseVo</code>类型数据
     */
    private List<TeacherCourseVo> trans(List<TeacherCourse> teacherCourses) {
        List<TeacherCourseVo> tcVos = new LinkedList<>();
        Map<String, Course> courseMap = courseService.map(null);

        teacherCourses.forEach(u -> {
            TeacherCourseVo teacherCourseVo = new TeacherCourseVo();
            BeanUtils.copyProperties(u, teacherCourseVo);
            teacherCourseVo.setCourse(courseMap.get(u.getCid()));
            tcVos.add(teacherCourseVo);
        });
        return tcVos;
    }

    private void fillCourse(TeacherCourseVo teacherCourse) {
        String cid = teacherCourse.getCid();
        Course course = courseService.getById(cid);
        teacherCourse.setCourse(course);
    }

    private void fillTeacher(TeacherCourseVo teacherCourse) {
        String tid = teacherCourse.getTid();
        Teacher teacher = teacherService.getById(tid);
        teacherCourse.setTeacher(teacher);
    }
}
