package cn.wstom.student.controller.student;


import cn.wstom.student.annotation.Log;
import cn.wstom.student.constants.*;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import cn.wstom.student.feign.ExamService;
import cn.wstom.student.service.*;
import cn.wstom.student.utils.AtomicIntegerUtil;
import cn.wstom.student.utils.FileUploadUtils;
import cn.wstom.student.utils.FileUtils;
import cn.wstom.student.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;



/**
 * @author dws
 * @date 2019/02/23
 */
@Controller
@RequestMapping("/user")
public class StudentController extends BaseController {
    private final String FRONT_USER_PREFIX = "/front/user";

    private final FavoriteService favoriteService;
    private final StudentService studentService;
    private final ForumService forumService;
    private final TopicService topicService;
    private final ReplyService replyService;
    private final DeckService deckService;
    private final ArticleService articleService;
    @Autowired
    private ExamService examService;

    @Autowired
    private ShuatiHistoryService shuatiHistoryService;


    @Value("${wstom.storageContextPath}")
    private String storageContextPath;
    @Value("${wstom.file.storage-path}")
    private String AdjunctURl ;
    @Autowired
    public StudentController(FavoriteService favoriteService,
                             ArticleService articleService,
                             StudentService studentService,
                             ForumService forumService,
                             TopicService topicService,
                             ReplyService replyService,
                             DeckService deckService
                           ) {

        this.favoriteService = favoriteService;
        this.studentService = studentService;
        this.forumService = forumService;
        this.topicService = topicService;
        this.replyService = replyService;
        this.deckService = deckService;
        this.articleService = articleService;
    }

    @Autowired
    private IntegralDetailService integralDetailService;

    @Autowired
    private StudentVoService studentVoService;

    @RequestMapping(value = "/{studentId}")
    @ResponseBody
    Student getStudentById(@PathVariable(value = "studentId")String studentId){
        return studentService.getById(studentId);
    }

    @RequestMapping(value = "/getStatisticsById/{userId}")
    @ResponseBody
    Statistics getStatisticsById(@PathVariable(value = "userId")String userId){
        return studentService.statisticsById(userId);
    }

    @RequestMapping(value = "/getStudentVoById/{studentVoId}")
    @ResponseBody
    StudentVo getStudentVoById(@PathVariable(value = "studentVoId")String studentVoId){
        return studentVoService.getById(studentVoId);
    }


    @RequestMapping(value = "/studentList")
    @ResponseBody
    List<Student> studentList(@RequestBody Student student){
        return studentService.list(student);
    }

    @RequestMapping(value = "/saveStudent")
    @ResponseBody
    Boolean saveStudent(@RequestBody Student student) throws Exception {
        return studentService.save(student);
    }

    @RequestMapping(value = "/updateStudent")
    @ResponseBody
    Boolean updateStudent(@RequestBody Student student) throws Exception {
        return studentService.update(student);
    }

    @RequestMapping(value = "/removeStudent")
    @ResponseBody
    Boolean removeStudent(@RequestBody List<String> sids) throws Exception {
        return studentService.removeByIds(sids);
    }

    @RequestMapping(value = "/selectByStudentVos")
    @ResponseBody
    TableDataInfo selectByStudentVos(@RequestBody StudentVo studentVo,
                                        @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                        @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                        @RequestParam(required = false,value = "orderBy")String orderBy
                                         ){

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<StudentVo> studentVos = studentVoService.selectByStudentVos(studentVo);
        //PageInfo<Adjunct> pageInfo = new PageInfo(studentVos);
        //System.out.println(pageInfo.getTotal());
        return wrapTable(studentVos,new PageInfo(studentVos).getTotal());
    }

    /**
     * 查看课程列表列表
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public String home(HttpServletRequest request, ModelMap model) {
        int pageNum = ServletRequestUtils.getIntParameter(request, "pn", 1);
        model.put("pn", pageNum);
        return FRONT_USER_PREFIX + "/course";
    }

    @GetMapping("/course/paperWork/{tcid}")
    public String toPaperWorkDetail(@PathVariable String tcid, HttpServletRequest request, ModelMap map) {
        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        Course course = adminService.getCourseById(teacherCourse.getCid());
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        //获取课程作业的试卷信息
        UserPaperWork userPaperWork  = new UserPaperWork();
        try {
            userPaperWork.setcId(teacherCourse.getCid());
        }
        catch (Exception e){
            System.out.println("eee"+e);
        }
        userPaperWork.setUserId(getUser().getId());
        userPaperWork.setType("0");
        List<UserPaperWork> userPaperWorkList = examService.getTcoExamPaperByUserPaperWork(userPaperWork);
        map.put("paperList", userPaperWorkList);
        UserPaperWork userPaperWork1 = new UserPaperWork();
        userPaperWork1.setcId(teacherCourse.getCid());
        userPaperWork1.setUserId(getUser().getId());
        userPaperWork1.setType("2");
        userPaperWork1.setSetExam("1");
        List <UserPaperWork>  examTestList = examService.getTcoExamPaperByUserPaperWork(userPaperWork1);
        //System.out.println("examTestList"+examTestList);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm"); //初始化时间格式

        examTestList.forEach(e->{
             //将字符串转化为Long型
            TestPaperWork testPaperWork= examService.getTestPaperWorkById(e.getTestPaperWorkId());
            e.setSetExam(testPaperWork.getSetExam());
            e.setStartTime(testPaperWork.getStartTime());
            e.setEndTime(testPaperWork.getEndTime());
            if(e.getStuStartTime()!=null){
                e.setUser_state("1"); //已参与
            }else{
                e.setUser_state("0");
            }
        });

        map.put("examTestList", examTestList);
        map.put("studentId",getUser().getUserAttrId());
        map.put("tcid",tcid);
        map.put("course",course);
        map.put("teacher",teacher);
        map.put("teacherUser",user);
        return "/front/paperWork";
    }

    @GetMapping("/course/toExam/{tcid}")
    public String toExam(@PathVariable("tcid") String tcid, HttpServletRequest request,ModelMap modelMap){

        List<TestPaperOne> testPaperOneList=new ArrayList<TestPaperOne>();
        //String cid=teacherCourseService.selectCourseById(tcid);
        String cid=adminService.getTeacherCourseById(tcid).getCid();
        String uid = getUser().getId();
        UserExam userExam=new UserExam();
        userExam.setUserId(uid);
        userExam.setcId(cid);
        List<UserExam> userExamList=examService.selectUserExamListBase(userExam);
        for(UserExam user:userExamList){
            TestPaperOne testPaperOne=examService.getTestPaperOneById(user.getTestPaperOneId());
            if(testPaperOne!=null){
                testPaperOne.setUser_state(0);
                if(user.getStuStartTime()!=null){
                    testPaperOne.setUser_state(1);
                }
                testPaperOne.setSumbitState(user.getSumbitState());
                testPaperOneList.add(testPaperOne);
            }
        }
        modelMap.put("tcid",tcid);
        modelMap.put("testPagerOneList",testPaperOneList);
        modelMap.put("cid",cid);
        return "/front/testPager";
    }

    /**
     * 页面跳转 在线作业
     *
     * @return
     */
    @GetMapping("/chapterPaperWork")
    public String chapterPaperWork(String testPaperOneId, String studentId, String tutId, ModelMap modelMap) {
        TestPaperWork testPaperWork = new TestPaperWork();
        testPaperWork = examService.getTestPaperWorkById(testPaperOneId);
        modelMap.put("testPaper", testPaperWork);
        modelMap.put("paperId", testPaperOneId);
        modelMap.put("studentId", studentId);
        modelMap.put("tutId",tutId);
        modelMap.put("stuName", getUser().getLoginName());
        modelMap.put("name", getUser().getUserName());

        return "front/exercise/chapterPaperWork";
    }
    /**
     * 获取学生课程列表
     * 获取我的课程列表
     */
    @PostMapping("/course/list")
    @ResponseBody
    public TableDataInfo list() throws Exception {
        Student student = studentService.getById(getUser().getUserAttrId()); //StudentMapper.xml->tb_student表
        ClbumCourse cc = new ClbumCourse();
        cc.setCid(student.getCid());
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<ClbumCourse> list = adminService.clbumCourseList(cc,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy()); //ClbumCourseMapper->tb_clbum_course
        System.out.println("list:"+list);
        PageInfo<ClbumCourse> pageInfo = new PageInfo(list);
        //填充教师信息
        List<ClbumCourseVo> clbumCourseVos = new LinkedList<>();
        fillTeacherCourse(list, clbumCourseVos);
        return wrapTable(clbumCourseVos, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    /**
     * 获取课程详情页面
     * 学生课程
     *
     * @param tcid 教师课程id
     */
    @GetMapping("/course/learn/{tcid}")
    public String toCourseDetail(@PathVariable("tcid") String tcid, HttpServletRequest request, ModelMap map) {
        System.out.println("tcid,learn,"+tcid);
        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        Course course = adminService.getCourseById(teacherCourse.getCid());
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        SysUser sysUser = new SysUser();
        sysUser.setUserType(UserConstants.USER_TEACHER);
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        Chapter chapter = new Chapter();
        chapter.setCid(course.getId());
        List<Chapter> chapterList = adminService.chapterList(chapter);
        map.put("chapter", transTree(chapterList));
        map.put("teacher", teacher);
        map.put("teacherUser", user);
        map.put("course", course);
        map.put("selected", "");
        map.put("tcid", tcid);
        map.put("studentId", getUser().getUserAttrId());
        return "/front/learn";
    }
    /**
     * 获取课程详情页面
     * 学生课程
     *
     * @param tcid 教师课程id
     */
    @GetMapping("/course/learnone/{tcid}")
    public String toCourseOneDetail(@PathVariable("tcid")String tcid, ModelMap map) {

        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        System.out.println("teacherCourse123"+teacherCourse);

        Course course = adminService.getCourseById(teacherCourse.getCid());
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
//        Teacher teacher = teacherService.getById(textTid);
        SysUser sysUser = new SysUser();
        sysUser.setUserType(UserConstants.USER_TEACHER);
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        Chapter chapter = new Chapter();
        chapter.setCid(course.getId());
        List<Chapter> chapterList = adminService.chapterList(chapter);
        ChapterResource chapterResource = new ChapterResource();
        chapterResource.setTcId(tcid);
        chapterResource.setCid("1");
        List<ChapterResource> chapterResourceList = adminService.chapterResourceList(chapterResource);
        System.out.println(chapterResourceList);
//        UserExam userExam = new UserExam();
//        userExam.setcId(teacherCourse.getCid());
//        userExam.setUserId(getUserId());
//        List<UserExam> userExamList = userExamService.list(userExam);
//        System.out.println(userExamList);
//        System.out.println(transTree(courseList).toString());
        map.put("chapter", transTree(chapterList));
        map.put("teacher", teacher);
        map.put("teacherUser", user);
//        map.put("userExam", userExamList);
        map.put("course", course);
        map.put("chapterResourceList", chapterResourceList);
        map.put("selected", "");
        map.put("studentId", getUser().getUserAttrId());
        map.put("tcid", tcid);
        return "/front/learnone";
    }

    /**
     * 获取课程详情页面
     * 学生课程
     *
     * @param tcid 教师课程id
     */
    @GetMapping("/course/info/{tcid}")
    public String toCourseInfo(@PathVariable("tcid") String tcid, ModelMap map) {
        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        Course course = adminService.getCourseById(teacherCourse.getCid());
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        SysUser sysUser = new SysUser();
        sysUser.setUserType(UserConstants.USER_TEACHER);
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        map.put("teacher", teacher);
        map.put("teacherUser", user);
        map.put("course", course);
        map.put("tcid", tcid);
        map.put("teacherCourse", teacherCourse);
        map.put("sysUser", getUser());
        map.put("types", adminService.recourseTypeList(new RecourseType()));
        return "/front/info";
    }

    @RequestMapping("/shouye/talk")
    @ResponseBody
    public Data findshouyeTalk() throws IOException {
        startPage();
        List<Topic> topics = topicService.list(new Topic());
        TableDataInfo pageInfo = wrapTable(topics);
        List<Topic> list = new ArrayList<>();
        for (Topic topic : topics) {
            Topic temp = new Topic();
            temp.setId(topic.getId());
            temp.setTitle(topic.getTitle());
            temp.setCreateBy(topic.getCreateBy());
            temp.setCreateName(topic.getCreateName());
            // 这里用内容来装转换后的发布时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(topic.getCreateTime());
            temp.setContent(strDate);
            list.add(temp);
        }
        return Data.success().put(Data.RESULT_KEY_DATA, list);
    }

    @RequestMapping("/personal/{id}")
    public String personal(@PathVariable String id, ModelMap modelMap) throws IOException {

        List<IntegralDetail> integralDetails = integralDetailService.selectBatchIntegral(Arrays.asList(id));
        List<Object[]> list = null;
        /*int count1=allintegralService.attentionme(userId);
        int count2=allintegralService.attentionother(userId);*/

        modelMap.put("allintegral", integralDetails);
        //如果是点开自己的个人中心
        if (id.equals(getUserId())) {

            Student student = studentService.getById(getUser().getUserAttrId());
//           String email = userService.selectUserByUserAttrId(getUser().getUserAttrId()).getEmail();
//           System.out.printf(email);
            //  累加积分值
            int sum = 0;
            IntegralDetail integralDetail = new IntegralDetail();
            integralDetail.setUserId(getUserId());
            List<IntegralDetail> integralDetailList = integralDetailService.list(integralDetail);
            for (int j = 0; j < integralDetailList.size(); j++) {
                System.out.println(integralDetailList.get(j).getUserName()+integralDetailList.get(j).getUserId()+",,"+integralDetailList.get(j).getCredit());
                sum += integralDetailList.get(j).getCredit();
            }
            getUser().setCredit(sum);

            if (student != null) {
                try {
                    /*list = testManageService.getAllTestTask(userIds);
                    List<Topic> topiclist=allintegralService.findalltopiclistbyid(userIds,"2");
                    List<Reply> replylist=allintegralService.findallreplybyid(userIds,"2");
                    List<StuReadAnnounce> proclamation=this.stuPublishService.getAllUserRead(userIds);//选出已发布的和该学生有关的公告
*/

                    Reply reply = new Reply();
                    reply.setAdopt(null);
                    reply.setCreateBy(getUserId());
                    List<Reply> replyList = replyService.list(reply);

                    Topic topic = new Topic();
                    topic.setCreateBy(getUserId());
                    List<Topic> topicList = topicService.list(topic);

                    /*  Atomic __ */
                    topicList.forEach( t -> {
                        t.setBrowse((long) AtomicIntegerUtil.getInstance(t.getClass(), t.getId(), t.getBrowse()).get());
                    });


                    modelMap.put("replylist", replyList);
                    modelMap.put("topiclist", topicList);
                    /*this.getRequest().setAttribute("topiclist", topiclist);
                    this.getRequest().setAttribute("replylist", replylist);
                    this.getRequest().setAttribute("student", student);
                    this.getRequest().setAttribute("allTestPaperList", list);
                    this.getRequest().setAttribute("proclamation", proclamation);
                    this.getRequest().setAttribute("size", proclamation.size());
                    if(list!=null){
                        this.getRequest().setAttribute("count3", list.size());
                    }
                    else{
                        this.getRequest().setAttribute("count3", 0);
                    }*/

                    modelMap.put("student", student);
                    modelMap.put("credit", sum);

                } catch (Exception e) {
                    System.out.println(e.getMessage() + "++++++++++++++");
                    e.printStackTrace();
                }
                return "/front/personal";
            } else {
                return "personal2";
            }

        }
        return "/front/personal";

        //点开其他人的个人中心
        /*else{
            JwStudentInfo student=studentService.get(JwStudentInfo.class, userId);
            if(student!=null){
                Attention attention=allintegralService.findattentionlist(userIds,userId);
                List<Topic> topiclist=allintegralService.findalltopiclistbyid(userId,"2");
                List<Reply> replylist=allintegralService.findallreplybyid(userId,"2");
                list = testManageService.getAllTestTask(userId);
                this.getRequest().setAttribute("topiclist", topiclist);
                this.getRequest().setAttribute("replylist", replylist);
                this.getRequest().setAttribute("userId", userId);
                this.getRequest().setAttribute("student", student);
                this.getRequest().setAttribute("attention", attention);
                this.getRequest().setAttribute("allTestPaperList", list);
                if(list!=null){
                    this.getRequest().setAttribute("count3", list.size());
                }
                else{
                    this.getRequest().setAttribute("count3", 0);
                }
                return "personal1";
            }
            else{
                return "personal2";
            }
        }*/
    }

    /**
     * 获取学习指导内容
     *
     * @param type 学习指导类型
     */
    @GetMapping("/guide/{type}")
    public String toCourseGuide(@PathVariable String type, HttpServletRequest request, ModelMap map) {
        TeacherCourse teacherCourse = (TeacherCourse) request.getSession().getAttribute("teacherCourse");
        Map<String, Object> params = new HashMap<>(3);
        params.put("tcId", teacherCourse.getId());
        map.put("type", type);

        if ("lead".equals(type)) {
            Lead lead = adminService.leadGetOne(params);
            map.put("guide", lead);
        } else if ("outline".equals(type)) {
            Outline outline = adminService.outlineGetOne(params);
            map.put("guide", outline);
        }else if ("outpeizhi".equals(type)) {
            Outpeizhi outpeizhi = adminService.outpeizhiGetOne(params);
            map.put("guide", outpeizhi);
        }

        return "/front/learnZhidao";
    }

    /**
     * 获取学习指导内容
     *
     * @param typeId 学习指导类型
     */
    @RequestMapping("/resource/{typeId}/{tcid}")
    public String toCourseResource(@PathVariable("typeId") String typeId,@PathVariable("tcid") String tcid,String search, ModelMap map) throws Exception {
        map.put("tcid", tcid);
        map.put("list", getResource(tcid, typeId, search));
        map.put("type", adminService.recourseTypeMap(new RecourseType()).get(typeId));
        RecourseType recourseType = new RecourseType();
        recourseType.setTcId(tcid);
        map.put("types", adminService.recourseTypeList(recourseType));
        return "/front/resource";
    }

    /**
     * 获取学习指导内容
     *
     * @param typeId 学习指导类型
     */
    @RequestMapping("/newResource/{typeId}/{tcid}")
    @ResponseBody
    public TableDataInfo toNewCourseResource(@PathVariable("tcid")String tcid,@PathVariable("typeId") String typeId, HttpServletRequest request) throws Exception {

        return getResource(tcid, typeId, null);
    }

    @RequestMapping("/courseInfo/{tcid}")
    @ResponseBody
    public Data courseName(@PathVariable("tcid")String tcid) throws IOException {
        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        Course course = adminService.getCourseById(teacherCourse.getCid());
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacherCourse",teacherCourse);
        map.put("course",course);
        return Data.success("0",map);
    }


    private TableDataInfo getResource(String tcid, String typeId, String search) throws Exception {
        Recourse recourse = new Recourse();
        RecourseType recourseType = new RecourseType();
        if (!"0".equals(typeId)) {
            recourseType.setId(typeId);
        }
        recourse.setCategory(recourseType);
        recourse.setTcId(tcid);
        recourse.setName(search);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Recourse> list = adminService.recourseList(recourse,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
//        Collections.reverse(list);
//        list = list.stream().sorted(Comparator.comparing(Recourse::getCreate_time)).collect(Collectors.toList());
        list.forEach( r -> {
            long count = r.getCount() != null ? r.getCount() : 0;
            r.setCount(AtomicIntegerUtil.getInstance(r.getClass(), r.getId(), count).get());
        });

        PageInfo pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    /*public String list(HttpServletRequest request) {
        // 准备数据：topic
        String id = getRequest().getParameter("id");
        Topic topic = topicService.get(Topic.class,id);
        //浏览数+1
        topic.setBrowse(topic.getBrowse()+1);
        topicService.update(topic);
        getRequest().setAttribute("topic",topic);
        //准备被采纳数据：replyList
        List<Reply>  replyAdopt = replyService.findByTopic(topic);
        if(replyAdopt!=null&&replyAdopt.size()>0){
            getRequest().setAttribute("replyAdopt",replyAdopt.get(0));
        }
        //准备其他回复：
        String numb = getRequest().getParameter("pageNum");
        int pageNum;
        if(StringUtils.isNumeric(numb)&&StringUtils.isNotEmpty(numb)){
            pageNum = Integer.valueOf(numb);
        }
        else{
            pageNum=1;
        }
        int pageSize = 10;//每页显示页数
        PageModel<Reply>  pageModel = replyService.findByTopic(pageNum, topic, pageSize);
        //设置头像
        for(Reply reply : pageModel.getResult()){
            //学生添加头像其他不添加
            if(UserType.STUDENT.equals(reply.getUserType())){
                JwStudentInfo studentInfo = this.userService.get(JwStudentInfo.class, reply.getCreateId());
                if(studentInfo.getHeadImage()!=null){
                    //给已上传头像学生设置头像
                    reply.getUser().setHeadImage(studentInfo.getHeadImage());
                }
            }
        }
        getRequest().setAttribute("pageModel",pageModel);
        return "show";
    }*/

    @RequestMapping("/topicList/{tcid}")
    public String topicList(@PathVariable("tcid")String tcid,HttpServletRequest request, ModelMap modelMap) throws ServletRequestBindingException {
        // 加载用户信息
        // 论坛的排序方式
        String course = ServletRequestUtils.getStringParameter(request, "course", "1");
        String forumid = ServletRequestUtils.getStringParameter(request, "forumid", "0");
        System.out.println("forumid"+forumid);
        System.out.println("course"+course);
        /**
         * 1：最新回复
         * 2：最新发表
         * 3：最热
         * 4：精华
         */
        int sort = ServletRequestUtils.getIntParameter(request, "sort", 1);
        // 当前页
        List<Forum> forums = null;

        List<Topic> list;
        Map<String, Object> params = Maps.newLinkedHashMap();
        forums = forumService.list(new Forum());
        try {
            // 判断显示板块方式，0表示全部
            if ("0".equals(forumid)) {
                Topic topic = new Topic();
                params.put("sort", sort);
                topic.setParams(params);
                startPage();
                list = topicService.list(topic);
                /*  Atomic __ */
                list.forEach( t -> {
                    t.setBrowse((long) AtomicIntegerUtil.getInstance(t.getClass(), t.getId(), t.getBrowse()).get());
                });
            } else {
                Forum forum = new Forum();
                forum.setId(forumid);
                Topic topic = new Topic();
                params.put("sort", sort);
                topic.setParams(params);
                topic.setForum(forum);
                startPage();
                list = topicService.list(topic);
            }
        } catch (Exception e) {
            Topic topic = new Topic();
            params.put("sort", 1);
            topic.setParams(params);
            startPage();
            list = topicService.list(topic);
            forumid = "0";
            sort = 1;
        }

//        list.forEach( t -> {
//            System.out.println(t.getThumbsUp());
//        });

        modelMap.put("forumid", forumid);
        modelMap.put("ForumList", forums);
        modelMap.put("pageModel", wrapTable(list));
        modelMap.put("sort", sort);
        modelMap.put("tcid", tcid);
        modelMap.put("userId", getUserId());
        return "/front/topic";
    }

    @RequestMapping("/courseTopicList/{tcid}")
    public String courseTopicList(HttpServletRequest request, ModelMap modelMap, @PathVariable String tcid) throws ServletRequestBindingException {
        // 加载用户信息
        // 论坛的排序方式
        String forumid = ServletRequestUtils.getStringParameter(request, "forumid", "0");
        int sort = ServletRequestUtils.getIntParameter(request, "sort", 1);
        // 当前页
        List<Forum> forums;

        List<Topic> list;
        Map<String, Object> params = Maps.newLinkedHashMap();
        Forum f = new Forum();
        f.setTcid(tcid);
        forums = forumService.list(f);
        Collections.reverse(forums);
        try {
            // 判断显示板块方式，0表示全部
            if ("0".equals(forumid)) {
                Topic topic = new Topic();
                topic.setTcid(tcid);
                params.put("sort", sort);
                topic.setParams(params);
                startPage();
                list = topicService.list(topic);
                Collections.reverse(list);
            } else {
                Forum forum = new Forum();
                forum.setId(forumid);

                Topic topic = new Topic();
                topic.setTcid(tcid);
                params.put("sort", sort);
                topic.setParams(params);
                startPage();
                topic.setForum(forum);
                list = topicService.list(topic);
                Collections.reverse(list);
            }
        } catch (Exception e) {
            Topic topic = new Topic();
            topic.setTcid(tcid);
            params.put("sort", 1);
            topic.setParams(params);
            startPage();
            list = topicService.list(topic);
            Collections.reverse(list);
            forumid = "0";
            sort = 1;
        }
        modelMap.put("forumid", forumid);
        modelMap.put("selected", "forum");
        modelMap.put("ForumList", forums);
        modelMap.put("pageModel", wrapTable(list));
        modelMap.put("sort", sort);
        return "/front/learn";
    }

    @RequestMapping("/courseTopicList/toPush/{tcid}")
    public String toCourseTopicPush(ModelMap modelMap, @PathVariable("tcid") String tcid) {
        /*
         * 判断权限
         * 如果是学生用户,就过滤 forumList
         */
        SysUser user = getUser();
        Forum f = new Forum();
        f.setTcid(tcid);
        List<Forum> forumList = forumService.list(f);
        Collections.reverse(forumList);
        if (user.getUserType() == UserConstants.USER_STUDENT) {
            List<Forum> tempForum = new ArrayList<>();
            for (Forum forum : forumList) {
                if (StringUtils.equalsIgnoreCase("1", forum.getType())) {
                    tempForum.add(forum);
                }
            }
            modelMap.put("forumList", tempForum);
        } else {
            modelMap.put("forumList", forumList);
        }
        modelMap.put("selected", "editor");
        modelMap.put("tcid", tcid);
        return "/front/learn";
    }

    @RequestMapping("/topicList/toPush/{tcid}")
    public String toPush(@PathVariable("tcid")String tcid,ModelMap modelMap) {
        /*
         * 判断权限
         * 如果是学生用户,就过滤 forumList
         */
        SysUser user = getUser();
        List<Forum> forumList = forumService.list(new Forum());
        if (user.getUserType() == UserConstants.USER_STUDENT) {
            List<Forum> tempForum = new ArrayList<>();
            /*for (Forum forum : forumList) {
                if (StringUtils.equalsIgnoreCase("1", forum.getType())) {
                    tempForum.add(forum);
                }
            }*/
            modelMap.put("forumList", forumList);
            modelMap.put("tcid", tcid);
        } else {
            modelMap.put("tcid", tcid);
            modelMap.put("forumList", forumList);
        }
        return "/front/push";
    }

    @RequestMapping("/topic/add")
    @ResponseBody
    public Data topicAdd(HttpServletRequest request) throws Exception {
        /*
         * 判断权限
         * 如果是学生用户,就过滤 forumList
         */
        Topic topic = new Topic();
        org.apache.commons.beanutils.BeanUtils.populate(topic, request.getParameterMap());
        String talktitle = ServletRequestUtils.getStringParameter(request, "talktitle");
        String talkcontent = ServletRequestUtils.getStringParameter(request, "talkcontent");
        String forumId = ServletRequestUtils.getStringParameter(request, "forumid");
        String tcid = ServletRequestUtils.getStringParameter(request, "tcid", null);

        //  topic title 为空
        if (talktitle == null || talktitle.equals("")) {
            return Data.error("标题为空");
        }

        Forum f = forumService.getById(forumId);

        topic.setForum(f);
        if (tcid != null && !"".equals(tcid.trim())) {
            topic.setTcid(tcid);
        }
        // >> 当前可以直接获取的信息
        topic.setUserType(getUser().getUserType() + "");
        // IP地址，当前请求中的IP信息
        topic.setIpAddr(request.getRemoteAddr());
        topic.setThumbsUp(0L);
        topic.setBrowse(0L);
        topic.setContent(talkcontent);
        topic.setTitle(talktitle);
        topic.setCreateName(getUser().getUserName());
        topic.setCreateBy(getUserId());

        // 保存
        return toAjax(topicService.save(topic));
    }

    @RequestMapping("/reply/add")
    @ResponseBody
    public Data replyAdd(HttpServletRequest request, String replycontent, String topicId) throws Exception {

        /*
         * 判断权限
         * 如果是学生用户,就过滤 forumList
         */
        String tcid = ServletRequestUtils.getStringParameter(request, "tcid", null);
        //先维护更新Topic
        Topic topic = topicService.getById(topicId);

        topic.setReplyCount(topic.getReplyCount() + 1);
        Reply reply = new Reply();
        // 1，封装（已经封装了title, content, faceIcon）
        System.out.println("topicId::" + topicId);
        reply.setTopic(topic);
        if (tcid != null && !"".equals(tcid.trim())) {
            reply.setTcid(tcid);
        }
        reply.setCreateBy(getUserId());
        reply.setUserType(getUser().getUserType() + "");
        reply.setContent(replycontent);
        reply.setIpAddr(request.getRemoteAddr());
        reply.setThumbsUp(0L);
        reply.setCreateName(getUser().getUserName());
        // 2，保存
        topic.setLastReply(reply);
        replyService.save(reply);
        topicService.update(topic);
        return Data.success();
    }


    @PostMapping("/reply/delete")
    @ResponseBody
    public String deleteReply(String replyId){
        System.out.println(replyId);
        try{
            replyService.deleteById(replyId);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
        }

        return "error";
    }

    @RequestMapping("/topic/detail/{id}/{tcid}")
    public String topicDtail(ModelMap modelMap, @PathVariable("id")String id,@PathVariable("tcid")String tcid) throws Exception {
        // 准备数据：topic

        Topic topic = topicService.getById(id);
        topic.setBrowse(topic.getBrowse()+1);

        topicService.update(topic);

        /*  Atomic __ */
        if (!AtomicIntegerUtil.isEmplyInMap(topic.getClass(), id)) {
            AtomicIntegerUtil.getInstance(topic.getClass(), id, topic.getBrowse());
        }
        topic.setBrowse((long) AtomicIntegerUtil.getInstance(topic.getClass(), id).getAndIncrement());


        /*topic.setBrowse(topic.getBrowse() + 1);
        topicService.update(topic);*/

        modelMap.put("topic", topic);
        //准备被采纳数据：replyList
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("adopt", 1);
        params.put("topic", topic);
        params.put("order_by", "r.create_time asc");
        Reply replyAdopt = replyService.getOne(params);

        if(replyAdopt!=null){
            Deck adoptDeck=new Deck();
            adoptDeck.setReply(replyAdopt);
            replyAdopt.setDecks(new HashSet<>(deckService.list(adoptDeck)));
            modelMap.put("replyAdopt", replyAdopt);
        }

        //准备其他回复：
        Reply reply = new Reply();
        reply.setAdopt(0);
        reply.setTopic(topic);
        params.clear();
        params.put("orderBy", "create_time ASC");
        reply.setParams(params);


        startPage();
        List<Reply> pageModel = replyService.list(reply);
        Collections.reverse(pageModel);
        Deck deck = new Deck();
        pageModel.forEach(p -> {
            deck.setReply(p);
            p.setDecks(new HashSet<>(deckService.list(deck)));
//            System.out.println(" reply dig count: " + p.getThumbsUp());
        });

        modelMap.put("pageModel", wrapTable(pageModel));
        modelMap.put("topicId",id);
        modelMap.put("tcid",tcid);

        // 保存
        return "/front/reply";
    }


    /**
     * 采纳评论
     * @param replyId
     * @return
     */
    @RequestMapping("/reply/adopt")
    public Data adopt(String replyId) {
        Reply reply = new Reply();
        reply.setId(replyId);
        reply.setAdopt(1);
        try {
            if (replyService.update(reply)) {
                return Data.success("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Data.success("failure");
    }

    /**
     *  - 提问与回复的点赞没有对应映射的数据表
     *  - 对点赞信息的持久化,在文章点赞数据表中实现
     */

    /**
     * 回复点赞
     *  - reply 类型 : 2
     * @param replyId
     * @return
     */
    @PostMapping("/reply/like")
    @ResponseBody
    public Data replyLike(String replyId) {
        SysUser sysUser = getUser();
        ArticleVotes articleVotes = new ArticleVotes();
        articleVotes.setUserId(sysUser.getId());
        articleVotes.setInfoType(2);
        articleVotes.setInfoId(replyId);

        String msg = null;
        //  查询是否已点赞
        if (articleService.checkArticleVotes(2, replyId, sysUser.getId())) {
            articleVotes = articleService.findArticleVotes(articleVotes).get(0);

            if (articleVotes.getDig() == 0) {
                articleVotes.setDig(1);
                msg = "+1success";
            } else {
                articleVotes.setDig(0);
                msg = "-1success";
            }
            articleService.updateArticleVotesById(articleVotes);
        } else {
            articleVotes.setDig(1);
            articleService.addArticleVotes(articleVotes);
            msg = "+1success";
        }
        //更新点赞数
        try {
            long digCount = articleService.selectDigCount(2, replyId);
            Reply reply = replyService.getById(replyId);
            reply.setThumbsUp(digCount);
            replyService.update(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Data.success(msg);
    }

    /**
     * 提问点赞
     *  - topic 类型 : 1
     * @param topicId
     * @return
     */
    @PostMapping("/topic/like")
    @ResponseBody
    public String topicLike(String topicId) {
        SysUser sysUser = getUser();
        ArticleVotes articleVotes = new ArticleVotes();
        articleVotes.setUserId(sysUser.getId());
        articleVotes.setInfoType(1);
        articleVotes.setInfoId(topicId);

        String msg = null;
        //  查询是否已点赞
        if (articleService.checkArticleVotes(1, topicId, sysUser.getId())) {

            articleVotes = articleService.findArticleVotes(articleVotes).get(0);
            if (articleVotes.getDig() == 0) { //查询点赞的状态,未赞点赞，攒了取消赞
                articleVotes.setDig(1);
                msg = "success";
            } else {
                articleVotes.setDig(0);
                msg = "eee";
            }
            articleService.updateArticleVotesById(articleVotes);
        } else {
            articleVotes.setDig(1);
            articleService.addArticleVotes(articleVotes);
            msg = "success";

        }
        // 更新点赞数
        try {
            long digCount = articleService.selectDigCount(1, topicId);
            Topic topic = topicService.getById(topicId);
            topic.setThumbsUp(digCount);
            topicService.update(topic);
        } catch (Exception e) {
            e.printStackTrace();
            msg="error";
        }

        return msg;
    }

    @RequestMapping("/courseTopic/{tcid}/detail/{id}")
    public String courseTopicDtail(ModelMap modelMap, @PathVariable String id, @PathVariable String tcid) throws Exception {
        // 准备数据：topic
        Topic topic = topicService.getById(id);

        /*topic.setBrowse(topic.getBrowse() + 1);
        topicService.update(topic);*/
        //浏览数+1
        /*  Atomic __ */
        if (!AtomicIntegerUtil.isEmplyInMap(topic.getClass(), id)) {
            AtomicIntegerUtil.getInstance(topic.getClass(), id, topic.getBrowse());
        }
        topic.setBrowse((long) AtomicIntegerUtil.getInstance(topic.getClass(), id).getAndIncrement());

        modelMap.put("topic", topic);
        //准备被采纳数据：replyList
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("adopt", 1);
        params.put("topic", topic);
        params.put("tcid", tcid);
        params.put("order_by", "r.create_time asc");
        Reply replyAdopt = replyService.getOne(params);
        modelMap.put("replyAdopt", replyAdopt);

        Reply reply = new Reply();
        reply.setAdopt(0);
        reply.setTopic(topic);
        reply.setTcid(tcid);
        params.clear();
        params.put("orderBy", "create_time ASC");
        reply.setParams(params);

        //准备其他回复：
        startPage();
        List<Reply> pageModel = replyService.list(reply);
        Collections.reverse(pageModel);
        Deck deck = new Deck();
        pageModel.forEach(p -> {
            deck.setReply(p);
            deck.setTcid(tcid);
            p.setDecks(new HashSet<>(deckService.list(deck)));
        });

        modelMap.put("pageModel", wrapTable(pageModel));
        modelMap.put("selected", "detail");

        // 保存
        return "/front/learn";
    }

    @RequestMapping("/deck/add")
    @ResponseBody
    public Data deckAdd(HttpServletRequest request, ModelMap modelMap) throws Exception {
        try {
            String content = ServletRequestUtils.getStringParameter(request, "content");
            String replyId = ServletRequestUtils.getStringParameter(request, "replyId");
            String toUserId = ServletRequestUtils.getStringParameter(request, "toUserId");
            String toUserName = ServletRequestUtils.getStringParameter(request, "toUserName");
            String toUserType = ServletRequestUtils.getStringParameter(request, "toUserType");
            String tcid = ServletRequestUtils.getStringParameter(request, "tcid");
            //准备数据
            Reply reply = replyService.getById(replyId);
            Deck deck = new Deck();
            deck.setContent(content);
            deck.setReply(reply);
            //设置作者的类型
            deck.setUserType(getUser().getUserType() + "");
            //设置回复对象信息
            deck.setToUserId(toUserId);
            deck.setToUserName(toUserName);
            deck.setToUserType(toUserType);
            deck.setCreateBy(getUserId());
            if (tcid != null && !"".equals(tcid)) {
                deck.setTcid(tcid);
            }
            deck.setIpAddr(request.getRemoteAddr());
            deck.setThumbsUp(0L);
            return toAjax(deckService.save(deck));
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error();
        }
    }

    @PostMapping("/deck/delete")
    @ResponseBody
    public Data deskDelete(String deckId){
        String msg=null;
        try{
            deckService.deckDelete(deckId);
            msg="success";
        }catch (Exception e){
            msg="error";
        }
        return Data.success(msg);
    }

    /**
     * 获取试卷列表
     * @param cid
     * @param studentId
     * @param chapterId
     * @param map
     * @return
     */
    @GetMapping("/course/exam/{cid}/{studentId}")
    public String toExam(@PathVariable String cid, @PathVariable String studentId, String chapterId, ModelMap map) throws Exception {
        //获取课程作业的试卷信息
        UserTest userTest = new UserTest();
        userTest.setcId(cid);
        userTest.setUserId(studentId);
        userTest.setType("0");
        PageDomain pageDomain = TableSupport.buildPageRequest();

        List<UserTest> userTestList = examService.getTcoExamPaperByUserTest(userTest,
                pageDomain.getPageNum(),
                pageDomain.getPageSize(),
                pageDomain.getOrderBy());
        map.put("paperList", userTestList);
        UserTest userTest1 = new UserTest();
        userTest1.setcId(cid);
        userTest1.setUserId(studentId);
        userTest1.setType("2");
        userTest1.setSetExam("1");
        List<UserTest> examTestList = examService.getTcoExamPaperByUserTest(userTest,
                pageDomain.getPageNum(),
                pageDomain.getPageSize(),
                pageDomain.getOrderBy());
        map.put("examTestList", examTestList);
        map.put("studentId", getUser().getUserAttrId());
        return "/front/personalExamList";
    }


    /*     获取作业   */
    @Log(title = "获取作业")
    @PostMapping("/course/work/{studentId}")
    @ResponseBody
    public List<UserTest> workList(@PathVariable String studentId) {
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setSumbitState("0");//    只显示未提交的作业
        userTest.setType(Constants.TEST_PAPER_TYPE_WORK);
        return examService.userTestList(userTest);
    }

    /*     获取刷题   */
    @PostMapping("/course/shuati/{studentId}")
    @ResponseBody
    public List<ClbumCourseVo> shuatiList(@PathVariable String studentId) throws Exception {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        Student student = studentService.getById(studentId);
        ClbumCourse cc = new ClbumCourse();
        cc.setCid(student.getCid());
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<ClbumCourse> list = adminService.clbumCourseList(cc,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
        PageInfo<ClbumCourse> pageInfo = new PageInfo(list);
        //填充教师信息
        List<ClbumCourseVo> clbumCourseVos = new LinkedList<>();
        fillTeacherCourse(list, clbumCourseVos);
        return clbumCourseVos;
    }

    /*     获取试卷  */
    @PostMapping("/course/exam/{studentId}")
    @ResponseBody
    public List<UserTest> examList(@PathVariable String studentId) {
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setSumbitState("0");//    只显示未提交的试卷
        userTest.setType(Constants.TEST_PAPER_TYPE_EXAM);
        return examService.userTestList(userTest);
    }

    /*     获取试卷  */
    @PostMapping("/course/keqian/{studentId}")
    @ResponseBody
    public List<UserTest> frontList(@PathVariable String studentId) {
        UserTest userTest = new UserTest();
        userTest.setUserId(studentId);
        userTest.setSumbitState("0");//    只显示未提交的试卷
        userTest.setType("3");
        return examService.userTestList(userTest);
    }


    /**
     * 保存用户评论内容
     *
     * @param
     * @return
     */
    @PostMapping("/course/comment/submit")
    @ResponseBody
    public Data comment(String parentId, String replyId, String replyUserId, String content, String typeId) throws Exception {
        Comment courseComment = new Comment();
        courseComment.setParentId(parentId);
        courseComment.setContent(content);
        courseComment.setReplyId(replyId);
        courseComment.setReplyUserId(replyUserId);
        courseComment.setType(ForumConstants.INFO_TYPE_COURSE);
        courseComment.setUserId(getUserId());
        courseComment.setUserType(UserConstants.USER_STUDENT);
        courseComment.setTypeId(typeId);
        return toAjax(adminService.commentSave(courseComment));
    }

    /**
     * 获取用户文章评论内容
     *
     * @param typeId
     * @return
     */
    @RequestMapping("/course/comments/{typeId}")
    @ResponseBody
    public Data articleComment(@PathVariable("typeId") String typeId, HttpServletRequest request) throws Exception {
        int pageSize = ServletRequestUtils.getIntParameter(request, "pageSize", defaultPageSize);
        int pageNum = ServletRequestUtils.getIntParameter(request, "pageNum", defaultPageNum);

        //用户id
        String userId = ServletRequestUtils.getStringParameter(request, "userId");
        String createTime = ServletRequestUtils.getStringParameter(request, "createTime");
        String orderBy = ServletRequestUtils.getStringParameter(request, "orderBy");
        String order = ServletRequestUtils.getStringParameter(request, "order");

        String parentId = "0";

        //分页获取父级信息
        //分页数据量以父级数据量为基础
        PageVo<Comment> pageVo = adminService.getCourseCommentListPage(
                typeId, userId, createTime, parentId, orderBy, order, pageNum, pageSize);
        List<Comment> list = pageVo.getList();

        List<String> userIds = new LinkedList<>();
        List<ArticleCommentVo> commentVos = fillChildrenCommen(list, userIds);

        //先填充评论信息再处理用户信息
        fillUser(commentVos, userIds);

        PageVo<ArticleCommentVo> page = new PageVo<>(pageVo.getPageNum());
        BeanUtils.copyProperties(pageVo, page);
        page.setList(commentVos);
        return Data.success().put(Data.RESULT_KEY_DATA, page);
    }

    /**
     * @param list 父评论信息
     * @return
     */
    private List<ArticleCommentVo> fillChildrenCommen(List<Comment> list, List<String> userIds) {

        if (list.isEmpty()) {
            return new LinkedList<>();
        }

        //获取子级评论
        Set<String> parentIds = Sets.newLinkedHashSet();
        list.forEach(l -> parentIds.add(l.getId()));
        List<Comment> childrenCommentList = adminService.listByPids(Lists.newArrayList(parentIds));

        //将子评论转为vo类型
        List<ArticleCommentVo> childrenCommentVos = new LinkedList<>();
        childrenCommentList.forEach(l -> {
            userIds.add(l.getUserId());
            ArticleCommentVo vo = new ArticleCommentVo();
            BeanUtils.copyProperties(l, vo);
            childrenCommentVos.add(vo);
        });

        //将父评论转为vo类型
        List<ArticleCommentVo> parentCommentVos = new LinkedList<>();
        list.forEach(l -> {
            userIds.add(l.getUserId());
            ArticleCommentVo vo = new ArticleCommentVo();
            BeanUtils.copyProperties(l, vo);
            parentCommentVos.add(vo);
        });

        //构建评论结构
        parentCommentVos.forEach(l ->
                l.setChildren(
                        childrenCommentVos.stream().filter(articleComment -> l.getId()
                                .equals(articleComment.getParentId())).sorted(Comparator
                                .comparing(BaseEntity::getCreateTime)).collect(Collectors.toList())
                ));
        return parentCommentVos;
    }

    private void fillUser(List<ArticleCommentVo> list, List<String> userIds) {
        Map<String, SysUser> userMap = adminService.userMapByIds(userIds);
        list.forEach(l -> {
            l.setUser(userMap.get(l.getUserId()));
            List<ArticleCommentVo> children = l.getChildren();
            if (children != null) {
                children.forEach(c -> {
                    c.setUser(userMap.get(c.getUserId()));
                    c.setParentUser(userMap.get(c.getReplyUserId()));
                });
            }
        });

    }

    /**
     * 文章详细页面
     *
     * @param cid
     * @param p
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/course/{cid}/comment")
    public String detail(@PathVariable(value = "cid") String cid, @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        Course course = adminService.getCourseById(cid);
        modelMap.put("course", course);
        TeacherCourse teacherCourseQ=new TeacherCourse();
        teacherCourseQ.setCid(cid);
        teacherCourseQ.setTid(getUser().getUserAttrId());
        TeacherCourse teacherCourse = adminService.getTeacherCourse(teacherCourseQ);
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("userType", UserConstants.USER_TEACHER);
        params.put("userAttrId", teacher.getId());
        SysUser sysUser = new SysUser();
        sysUser.setUserType( UserConstants.USER_TEACHER);
        sysUser.setUserAttrId( teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        modelMap.put("teacher", teacher);
        modelMap.put("teacherUser", user);
        modelMap.addAttribute("typeId", teacherCourse.getId());
        modelMap.addAttribute("p", p);
        modelMap.addAttribute("type", ForumConstants.INFO_TYPE_COURSE);
        return FRONT_USER_PREFIX + "/course_forum";
    }

    /**
     * 文章详细页面
     *
     * @param cid
     * @param modelMap
     * @return
     */
    @GetMapping(value = "/course/{cid}/outline")
    public String outline(@PathVariable(value = "cid") String cid, ModelMap modelMap) {
        Course course = adminService.getCourseById(cid);
        modelMap.put("course", course);
        TeacherCourse teacherCourse1 = new TeacherCourse();
        teacherCourse1.setTid(getUser().getUserAttrId());
        teacherCourse1.setTid(cid);
        TeacherCourse teacherCourse = adminService.getTeacherCourse(teacherCourse1);
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        SysUser sysUser = new SysUser();
        sysUser.setUserType(UserConstants.USER_TEACHER);
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        modelMap.put("teacher", teacher);
        modelMap.put("teacherUser", user);
        modelMap.addAttribute("typeId", teacherCourse.getId());

        HashMap<String, Object> params = new HashMap<>();
        params.put("tcId", teacherCourse.getId());
        Outline outline = adminService.outlineGetOne(params);
        modelMap.addAttribute("outline", outline);
        modelMap.addAttribute("type", ForumConstants.INFO_TYPE_COURSE);
        return FRONT_USER_PREFIX + "/outline";
    }

    private List<ChapterVo> transTree(List<Chapter> chapters) {
        Map<String, ChapterVo> chapterMap = new LinkedHashMap<>();
        chapters.forEach(c -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(c, chapterVo);
            chapterMap.put(c.getId(), chapterVo);
        });
        Map<String, ChapterVo> tree = new LinkedHashMap<>();
        chapters.forEach(c -> {
            if ("0".equals(c.getPid())) {
                tree.put(c.getId(), chapterMap.get(c.getPid()));
            } else {
                ChapterVo chapterVo = chapterMap.get(c.getPid());
                tree.putIfAbsent(chapterVo.getId(), chapterVo);
                tree.get(chapterVo.getId()).getChildren().add(c);
            }
        });
        return Lists.newArrayList(tree.values());
    }

    private void fillTeacherCourse(List<ClbumCourse> list, List<ClbumCourseVo> clbumCourseVos) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<String> tcId = new LinkedList<>();
        list.forEach(l -> tcId.add(l.getTcid()));
        // 根据课程id查找教师信息
        Map<String, TeacherCourse> teacherCourseMap = adminService.teacherCourseMapByIds(tcId);
        tcId.clear();
        teacherCourseMap.forEach((k, v) -> tcId.add(v.getTid()));

        Map<String, SysUser> sysUserMap = adminService.userlistByTids(tcId);

        List<String> cid = new LinkedList<>();
        list.forEach(l -> cid.add(l.getCid()));

        Map<String, Course> courseMap = adminService.courseMapByClbumId(cid);

        List<String> tid = new LinkedList<>();
        teacherCourseMap.forEach((k, v) -> tid.add(v.getTid()));
        Map<String, Teacher> teachers = adminService.teacherMapByIds(tid);

        list.forEach(l -> {
            ClbumCourseVo cc = new ClbumCourseVo();
            cc.setTeacher(teachers.get(teacherCourseMap.get(l.getTcid()).getTid()));
            TeacherCourse teacherCourse = teacherCourseMap.get(l.getTcid());
            cc.setTeacherCourse(teacherCourse);
            cc.setCourse(courseMap.get(teacherCourse.getCid()));
            cc.setSysUser(sysUserMap.get(teacherCourse.getTid()));
            clbumCourseVos.add(cc);
        });
    }


    @GetMapping(value = "/forum/{infoType}")
    public String people(@RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap, @PathVariable int infoType) {
        modelMap.addAttribute("p", p);
        modelMap.addAttribute("count", studentService.statisticsById(getUserId()));
        switch (infoType) {
            case ForumConstants.INFO_TYPE_ANSWER:
                infoType = ForumConstants.INFO_TYPE_ANSWER;
                break;
            case ForumConstants.INFO_TYPE_QUESTION:
                infoType = ForumConstants.INFO_TYPE_QUESTION;
                break;
            case ForumConstants.INFO_TYPE_ARTICLE:
                infoType = ForumConstants.INFO_TYPE_ARTICLE;
                break;
            case ForumConstants.INFO_TYPE_SHARE:
                infoType = ForumConstants.INFO_TYPE_SHARE;
                break;
            default:
                infoType = ForumConstants.INFO_TYPE_QUESTION;
                break;
        }
        modelMap.addAttribute("infoType", infoType);
        return FRONT_USER_PREFIX + "/content";
    }


    /**
     * 查看文章列表
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String posts(HttpServletRequest request, ModelMap model) {
        int pageNum = ServletRequestUtils.getIntParameter(request, "pn", 1);
        model.put("pn", pageNum);
        return FRONT_USER_PREFIX + "/posts";
    }

    /**
     * 查看收藏列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/favors", method = RequestMethod.GET)
    public String favors(ModelMap model) {
        Favorite favorite = new Favorite();
        /*favorite.setUid(getUserId());*/

        startPage();
        List<Favorite> list = favoriteService.list(favorite);
        model.put("page", list);
        return FRONT_USER_PREFIX + "/favors";
    }

    @RequestMapping("/checkFavor/{id}")
    @ResponseBody
    public Data checkFavor(@PathVariable String id) {
        if (id != null) {
            boolean b = favoriteService.checkFavoriteByUser(getUserId(), ForumConstants.INFO_TYPE_ARTICLE, id);
            if (b) {
                return Data.success();
            }
        }
        return Data.error();
    }


    @RequestMapping("/comment/delete/{id}")
    @ResponseBody
    public Data commentDel(@PathVariable String id) throws Exception {
        if (id != null) {
            Comment comment = adminService.getCommentById(id);

            if (null != comment) {
                Assert.isTrue(getUserId().equals(comment.getUserId()), "认证失败");
                adminService.removeCommentById(id);
            }
            return Data.success("删除成功");
        }
        return Data.error("删除失败");
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(ModelMap model) {
        SysUser user = getUser();
        Student student = studentService.getById(user.getUserAttrId());
        StudentVo studentVo = trans(user, student);
        //填充班级信息
        fillClbum(studentVo);
        fillGrades(studentVo);
        fillMajor(studentVo);
        fillDepartment(studentVo);

        model.put("user", user);
        return FRONT_USER_PREFIX + "/profile";
    }

    @RequestMapping("/profile/update")
    public String updateProfile(SysUser user, ModelMap model) {

        SysUser sysUser = null;
        try {
            sysUser = new SysUser();
            sysUser.setId(getUserId());
            sysUser.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                sysUser.setPassword(BaseController.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
            }
            sysUser.setSignature(user.getSignature());
            adminService.updateUser(sysUser);
        } catch (Exception e) {
            model.put("message", e.getMessage());
        }

        return FRONT_USER_PREFIX + "/profile";
    }

    /*@RequestMapping("/notifies")
    public String notifies(ModelMap model) {
        Message message = new Message();
        message.setSourceUserId(getUserId());

        startPage();
        List<Message> list = messageService.list(message);

        list.forEach(l -> {
            l.setStatus(Constants.MSG_STATUS_READED);
            messageService.update(l);
        });
        model.put("page", list);
        return FRONT_USER_PREFIX + "/notifies";
    }*/


    @RequestMapping("/avatar/upload")
    @ResponseBody
    public Data upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>(4);
        String originPath = ServletRequestUtils.getStringParameter(request, "o", null);
        int crop = ServletRequestUtils.getIntParameter(request, "crop", 0);
        int size = 265;

        String path;
        Data result = null;
        try {
            //储存图片
            if (crop == 1) {
                result = FileUploadUtils.upload(StorageConstants.STORAGE_THUMBNAIL, file, false, FileUtils.allowImage);
            }
            if (StringUtil.nvl(result.get(Data.RESULT_KEY_CODE).toString(), "").equals(Constants.FAILURE)) {
                return result;
            }
            String filename = file.getOriginalFilename();
            data.put("name", filename);
            data.put("type", getSuffix(filename != null ? filename : ""));
            data.put("path", result.get(StorageConstants.FILE_ID));
            data.put("size", file.getSize());
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
        Data success = Data.success();
        success.putAll(data);
        return success;
    }

    @RequestMapping(value = "/avatar/update", method = RequestMethod.POST)
    @ResponseBody
    public Data avatarUpdate(String path, Float x, Float y, Float width, Float height) throws Exception {

        Data data = Data.success("修改成功");
        if (StringUtils.isEmpty(path)) {
            return Data.error("请选择图片");
        }

        SysUser user = getUser();
        user.setAvatar(path);
        adminService.updateUser(user);
        return data;
    }

    @RequestMapping(value = "/toPassword", method = RequestMethod.GET)
    public String password() {
        return FRONT_USER_PREFIX + "/password";
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public Data post(@Param("oldPassword") String oldPassword,@Param("password") String password, RedirectAttributesModelMap modelMap) {
        Data data = null;
        try {
            SysUser user = getUser();

            Assert.hasLength(password, "密码不能为空！");
            if (null != user) {
                SysUser sysUser = new SysUser();
                sysUser.setPassword(BaseController.encryptPassword(user.getLoginName(), oldPassword, user.getSalt()));
                sysUser.setId(getUserId());
                List<SysUser> list = adminService.getUserList(sysUser);
                Assert.isTrue(list != null && list.size() == 1, "旧密码不正确");
                user.setSalt(BaseController.randomSalt());
                user.setPassword(BaseController.encryptPassword(user.getLoginName(), password, user.getSalt()));
                boolean update = adminService.updateUser(user);
                data = Data.success("success");
                //  修改成功，清除subject
                Subject currentUser = SecurityUtils.getSubject();
                if (SecurityUtils.getSubject().getSession() != null) {
                    currentUser.logout();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = Data.error(e.getMessage());
        }
        modelMap.addFlashAttribute("data", data);
//        return redirect(storageContextPath + "/user/toPassword");
        return data;
    }
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    @ResponseBody
    public Data post(@Param("stuemail") String stuemail, RedirectAttributesModelMap modelMap) {
        Data data = null;
        try {
            SysUser user =getUser();
            if (null != user) {
                SysUser sysUser = new SysUser();
                user.setEmail(stuemail);
                System.out.printf("获得邮箱"+stuemail);
//                sysUser.setPassword(getUser().encryptPassword(user.getLoginName(), oldPassword, user.getSalt()));
                sysUser.setId(getUserId());
                boolean update = adminService.updateUser(user);
                data = Data.success("success");
                //  修改成功，清除subject
                Subject currentUser = SecurityUtils.getSubject();
                if (SecurityUtils.getSubject().getSession() != null) {
                    currentUser.logout();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = Data.error(e.getMessage());
        }
        modelMap.addFlashAttribute("data", data);
        return data;
    }
    @RequestMapping("/log")
    public String log() {
        return FRONT_USER_PREFIX + "/log";
    }


    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/stuToTest")
    public String stuToTest(String testPaperId, String studentId, String tutId, ModelMap modelMap) {
        TestPaper testPaper = new TestPaper();
        testPaper = examService.getTestPaperById(testPaperId);
        modelMap.put("testPaper", testPaper);
        modelMap.put("paperId", testPaperId);
        modelMap.put("studentId", studentId);
        modelMap.put("tutId", tutId);
        modelMap.put("stuName", getUser().getLoginName());
        modelMap.put("name", getUser().getUserName());
        return "/front/stuTest";
    }


    /**
     * 页面跳转 章节测试
     *
     * @return
     */
    @GetMapping("/chapterTest")
    public String chapterTest(String testPaperId, String studentId, String tutId, ModelMap modelMap) {
        TestPaper testPaper = new TestPaper();
        testPaper = examService.getTestPaperById(testPaperId);
        System.out.println("testPaper:" + testPaper);
        modelMap.put("testPaper", testPaper);
        modelMap.put("paperId", testPaperId);


        System.out.println("studentId"+getUser().getUserAttrId());
        System.out.println("stuName"+getUser().getLoginName());
        System.out.println("name"+getUser().getUserName());

        modelMap.put("studentId", getUser().getUserAttrId());
        modelMap.put("stuName", getUser().getLoginName());
        modelMap.put("name", getUser().getUserName());
        return "front/exercise/chapterWork";
    }
    /**
     * 页面跳转 在线考试
     *
     * @return
     */
    @GetMapping("/chapterExam")
    public String chapterExam(String testPaperOneId, String studentId, String tutId, ModelMap modelMap) {
        TestPaperOne testPaper = new TestPaperOne();
        testPaper = examService.getTestPaperOneById(testPaperOneId);
        System.out.println("testPaper:" + testPaper);
        modelMap.put("testPaper", testPaper);
        modelMap.put("paperId", testPaperOneId);
        modelMap.put("studentId", getUser().getUserAttrId());
        modelMap.put("stuName", getUser().getLoginName());
        modelMap.put("name", getUser().getUserName());
        modelMap.put("testPaperOneId",testPaperOneId);
        modelMap.put("cid",testPaper.getCoursrId());

        return "front/exercise/chapterExamWork";
    }
    /**
     * 发送通知
     *
     * @param postId
     */
    //private void sendMessage(String postId) {
    //    NotifyEvent event = new NotifyEvent("MessageEvent" + System.currentTimeMillis());
    //    event.setSourceUserId(getUserId());
    //    event.setEventType(EventConstants.EVENT_ARTICLE_FAVORITES);
    //    // 此处不知道文章作者, 让通知事件补全
    //    event.setTargetId(postId);
    //}

    /**
     * 批量转换teacherVo类型
     *
     * @param users
     * @param teachers
     * @return
     */
    private List<TeacherVo> trans(List<SysUser> users, Map<String, Teacher> teachers) {
        List<TeacherVo> teacherVos = new LinkedList<>();
        users.forEach(u -> teacherVos.add(trans(u, teachers.get(u.getUserAttrId()))));
        return teacherVos;
    }

    /**
     * 转换teacherVo类型
     *
     * @param teacher
     * @return
     */
    private TeacherVo trans(SysUser users, Teacher teacher) {
        TeacherVo teacherVo = new TeacherVo();
        BeanUtils.copyProperties(users, teacherVo);
        teacherVo.setTeacher(teacher);
        return teacherVo;
    }

    /**
     * 转换studentVo类型
     *
     * @param student
     * @return
     */
    private StudentVo trans(SysUser users, Student student) {
        StudentVo studentVo = new StudentVo();
        BeanUtils.copyProperties(users, studentVo);
        studentVo.setStudent(student);
        return studentVo;
    }

    /**
     * 获取班级信息
     *
     * @param studentVos
     */
    private void fillClbum(StudentVo studentVos) {
        Clbum clbum = adminService.getClbumById(studentVos.getStudent().getCid());
        studentVos.setClbum(clbum);
    }

    /**
     * 获取年级信息
     *
     * @param studentVos
     */
    private void fillGrades(StudentVo studentVos) {
        Grades grades = adminService.getGradesById(studentVos.getStudent().getGid());
        studentVos.setGrades(grades);
    }

    /**
     * 获取专业信息
     *
     * @param studentVos
     */
    private void fillMajor(StudentVo studentVos) {
        Major major = adminService.getMajorById(studentVos.getClbum().getMid());
        studentVos.setMajor(major);
    }

    /**
     * 获取系部信息
     *
     * @param studentVos
     */
    private void fillDepartment(StudentVo studentVos) {
        Department department = adminService.getDepartmentById(studentVos.getMajor().getDid());
        studentVos.setDepartment(department);
    }

    private String getSuffix(String path) {
        int pos = path.lastIndexOf(".");
        return path.substring(pos);
    }

    /**
     * 进入刷题页面
     */
    @GetMapping("/personal/shuati/{cid}")
    private String shuati(@PathVariable String cid,HttpServletRequest request, ModelMap map){
        Course course = adminService.getCourseById(cid);
        Chapter chapter = new Chapter();
        chapter.setCid(course.getId());
        List<Chapter> courseList = adminService.chapterList(chapter);
        System.out.println(transTree(courseList).toString());
        map.put("course", course);
        map.put("chapter", transTree(courseList));
        return "/front/shuati";
    }

    /**
     * 进入章节刷题
     */
    @RequestMapping("/brushQuestion")
    private String brushQuestion(HttpServletRequest request, ModelMap map)throws IOException {
        String myQuestionsIds="";
        String chid=request.getParameter("chid");
        System.out.println("!!!!!!!!!!!!!!!!!!"+chid);
        MyQuestions myQuestions=new MyQuestions();
        myQuestions.setChapterId(chid);
        List<MyQuestions> list=examService.myQuestionsList(myQuestions);
        Chapter chapter=new Chapter();
        chapter.setPid(chid);
        List<Chapter> chapters=adminService.chapterList(chapter);
        for(Chapter s : chapters){
            System.out.println("******************"+s.getId());
            myQuestions.setChapterId(s.getId());
            list.addAll(examService.myQuestionsList(myQuestions));
        }
        for (MyQuestions q : list){
            System.out.println("??????????"+q);
        }
        if(list.size()==0){
            System.out.println("此章节没有答案");
            return null;
        }
        List<Shuati> shuatis=new ArrayList<Shuati>();
        for (MyQuestions q : list){
            myQuestionsIds=myQuestionsIds+q.getId()+",";
            Shuati shuati=new Shuati();
            shuati.setMyQuestions(q);
            shuatis.add(shuati);
        }
        List<MyOptionAnswer[]> myOptionAnswers=new ArrayList<MyOptionAnswer[]>();
        for(int i=0;i<list.size();i++){
            String myoptionAnswerArrString=list.get(i).getMyoptionAnswerArr();
            String[] myoptionAnswer=myoptionAnswerArrString.split(";");
            List<MyOptionAnswer> myOptionAnswersArr=new ArrayList<MyOptionAnswer>();
            for(int j=0;j<myoptionAnswer.length;j++){
                MyOptionAnswer myOptionAnswer= examService.myOptionAnswerById(myoptionAnswer[j]);
                myOptionAnswersArr.add(myOptionAnswer);
            }
            shuatis.get(i).setMyOptionAnswerList(myOptionAnswersArr);
        }
        map.put("myQuestionsIds",myQuestionsIds);
        map.put("shuatis",shuatis);
        map.put("listsize",shuatis.size());
        for(Shuati s: shuatis){
            System.out.println(s.toString());
        }
        return "/front/shuatishow";
    }


    /**
     * 提交刷题
     */
    @RequestMapping("/submitShuati")
    private @ResponseBody Integer submitShuati(@RequestBody List<ShuatiHistory> shuatiHistories){
        System.out.println("进入提交答案");
          for(ShuatiHistory s : shuatiHistories){
              s.setUserId(getUserId());
          }
          System.out.println(shuatiHistories.toString());
          try{
              shuatiHistoryService.saveBatch(shuatiHistories);
          }catch (Exception e){
              e.printStackTrace();
              return 0;
          }
        return 1;
    }

    @RequestMapping("/course/attendance/{tcid}")
    public String toAttendance(@PathVariable String tcid,ModelMap map) {
        String userAttrId =getUser().getUserAttrId();
        System.out.println("userAttrId"+userAttrId);
        System.out.println("tcidattendance,"+tcid);
        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        System.out.println("teacherCourse的值是"+teacherCourse);
        Course course = adminService.getCourseById(teacherCourse.getCid());
        AttendanceVo attendanceVo=new AttendanceVo();
        attendanceVo.setSid(Integer.valueOf(userAttrId));
        attendanceVo.setTcid(Integer.valueOf(tcid));
        List<AttendanceVo> list =adminService.attendanceVolist(attendanceVo);
        map.put("course", course);
        map.put("chapterResourceList", list);
        map.put("selected", "attendance");
        map.put("studentId", getUser().getUserAttrId());
        return "/front/attendance";
    }



    @RequestMapping("/toattendance")
    public String toAttendanceTwo( String tcid, String psd,ModelMap map) {
        map.put("sid", getUser().getUserAttrId());
        map.put("tcid", tcid);
        map.put("psd", psd);
        map.put("stuName", getUser().getLoginName());
        map.put("name", getUser().getUserName());
        return "/front/attendancetwo";
    }


    @RequestMapping("/updataattendance")
    @ResponseBody
    public Boolean toUpdataAttendance(ModelMap map) {
        String userAttrId =getUser().getUserAttrId();
        map.put("sid", getUser().getUserAttrId());
        map.put("stuName", getUser().getLoginName());
        map.put("name", getUser().getUserName());
        return true;
    }

    @RequestMapping("/course/adjunct/{tcid}")
    public String toAdjunct(@PathVariable String tcid,ModelMap map) {

        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(teacherCourse.getTid());
        SysUser user = adminService.getUser(sysUser);
        Course course = adminService.getCourseById(teacherCourse.getCid());
        AdjunctStudent adjunctStudent=new AdjunctStudent();
        adjunctStudent.setStuid(getUser().getUserAttrId());
        List<AdjunctStudent> list =adminService.adjunctStudentList(adjunctStudent);
        fillAdjunct(list);
        map.put("course", course);
        map.put("teacherUser", user);
        map.put("tcid", tcid);
        map.put("chapterResourceList", list);
        map.put("studentId", getUser().getUserAttrId());
        return "/front/adjunct";
    }

    @RequestMapping("/toadjunct")
    public String toAdjunctTwo(String id, ModelMap map,HttpServletRequest request) throws Exception {
        String userAttrId =getUser().getUserAttrId();
        TeacherCourse teacherCourse = (TeacherCourse) request.getSession().getAttribute("teacherCourse");
        String search = ServletRequestUtils.getStringParameter(request, "search", null);
        Adjunct adjunct= adminService.getAdjunctById(id);
        System.out.println("adjunct"+adjunct);
        String endTime=adjunct.getDeadline();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sd1=new Date();
        Date sd2=df.parse(endTime);
        if (sd1.after(sd2)){
            return "/front/overdata";
        }
        AdjunctStudent adjunctStudent=new AdjunctStudent();
        adjunctStudent.setAdjid(id);
        adjunctStudent.setStuid(userAttrId);
        map.put("list", getResourceToAdjunct(teacherCourse, adjunct.getRid(), search));
        map.put("sid", getUser().getUserAttrId());
        map.put("tcid", teacherCourse.getId());
        map.put("adjunct", adjunct);
        map.put("stuName", getUser().getLoginName());
        map.put("name", getUser().getUserName());
        map.put("adjunctStudent",adminService.selectListByAdjunctStudent(adjunctStudent).get(0));
        map.put("adjunctid",id);
        return "/front/adjuncttwo";
    }

    @RequestMapping("{adjid}/updataAdjunct")
    @ResponseBody
    public Data toUpdataAdjunct(MultipartFile f,HttpServletRequest request,ModelMap map,@PathVariable String adjid) throws IOException {
        System.out.println("ajid"+adjid);
//        System.out.println("MultipartFile"+f);
        if (FileUtils.checkFileType(f.getOriginalFilename(), FileUtils.adjunctFile)){
            String userAttrId =getUser().getUserAttrId();
            SysUser sysUser = new SysUser();
            sysUser.setUserAttrId(userAttrId);

            List<SysUser> syslist = adminService.getUserList(sysUser);
            String loginName=syslist.get(0).getLoginName();
            Student student = new Student();
            student.setId(userAttrId);
            List<Student> list = studentService.list(student);
            String cid=list.get(0).getCid();
            Clbum clbum = new Clbum();
            clbum.setId(cid);
            List<Clbum> list1 = adminService.clbumList(clbum);

            String clbumName=list1.get(0).getName();
            System.out.println("userAttrId"+userAttrId);
            String url=AdjunctURl+"\\"+adjid+"/";
            String fileBlocks = url;
            String fileName = f.getOriginalFilename();
            int pos = fileName.lastIndexOf(Constants.SEPARATOR_DOT);
            String path = loginName + "_"+clbumName + "." + fileName.substring(pos + 1);
            File file = new File(fileBlocks, path);
            Date date=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            String submitdate=sdf.format(date);
            if(file.exists()){
                f.transferTo(file);
                AdjunctStudent adjunctStudent=new AdjunctStudent();
                adjunctStudent.setStates(1);
                adjunctStudent.setAdjid(adjid);
                adjunctStudent.setStuid(userAttrId);
                adjunctStudent.setFilename(fileName);
                adjunctStudent.setSubmitline(submitdate);
                adminService.updateByAidAndSid(adjunctStudent);
                return success("重新上传文件成功");
            }
            f.transferTo(file);
            AdjunctStudent adjunctStudent=new AdjunctStudent();
            adjunctStudent.setStates(1);
            adjunctStudent.setAdjid(adjid);
            adjunctStudent.setStuid(userAttrId);
            adjunctStudent.setFilename(fileName);
            adjunctStudent.setSubmitline(submitdate);
            System.out.println(adjunctStudent);
            return toAjax(adminService.updateByAidAndSid(adjunctStudent));
        }else {
            return error(-1,"上传格式错误，仅支持doc, docx, xlsx, xls, text, pdf, zip, ppt");
        }

    }


    @RequestMapping("/course/preview/{tcid}")
    public String toPreview(@PathVariable String tcid,ModelMap map) {
        String userAttrId =getUser().getUserAttrId();
        System.out.println("userAttrId"+userAttrId);
        System.out.println("tcidattendance,"+tcid);
        TeacherCourse teacherCourse = adminService.getTeacherCourseById(tcid);
        Course course = adminService.getCourseById(teacherCourse.getCid());
//        Preview preview=new Preview();
//        preview.setTcid(tcid);
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        SysUser sysUser = new SysUser();
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        PreviewStudent previewStudent=new PreviewStudent();
        previewStudent.setTcid(Integer.valueOf(tcid));
        previewStudent.setSid(Integer.valueOf(userAttrId));
        List<String> list2=adminService.listBySidAndTcid(previewStudent);
        List<Preview> list =new ArrayList<>();
        list2.forEach(t->{
            Preview preview=adminService.getPreviewById(t);
            list.add(preview);
        });
        System.out.println("Preview的list,"+list);
        map.put("course", course);
        map.put("chapterResourceList", list);
        map.put("selected", "attendance");
        map.put("studentId", getUser().getUserAttrId());
        map.put("teacherUser", user);
        return "/front/preview";
    }

    @RequestMapping("/topreview")
    public String toPreviewTwo(String pid, ModelMap map,HttpServletRequest request) {
        System.out.println("toPreviewTwo----pid"+pid);
        TeacherCourse teacherCourse = (TeacherCourse) request.getSession().getAttribute("teacherCourse");
        Course course = adminService.getCourseById(teacherCourse.getCid());
        Teacher teacher = adminService.getTeacherById(teacherCourse.getTid());
        SysUser sysUser = new SysUser();
        sysUser.setUserType(UserConstants.USER_TEACHER);
        sysUser.setUserAttrId(teacher.getId());
        SysUser user = adminService.getUser(sysUser);
        Chapter chapter = new Chapter();
        chapter.setCid(course.getId());
        chapter.setPreviewid(pid);
        List<Chapter> courseList = adminService.selectListByPreviewid(chapter);
        System.out.println(transTree(courseList));
        map.put("chapter", transTree(courseList));
        map.put("teacher", teacher);
        map.put("teacherUser", user);
        map.put("course", course);
        map.put("selected", "");
        map.put("studentId", getUser().getUserAttrId());
        return "/front/previewtwo";
    }



    private TableDataInfo getResourceToAdjunct(TeacherCourse teacherCourse, String rid,String search) throws Exception {
        Recourse recourse = new Recourse();
        RecourseType recourseType = new RecourseType();
        recourse.setId(rid);
        recourse.setCategory(recourseType);
        recourse.setTcId(teacherCourse.getId());
        recourse.setName(search);
        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<Recourse> list = adminService.recourseList(recourse,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
//        Collections.reverse(list);
//        list = list.stream().sorted(Comparator.comparing(Recourse::getCreate_time)).collect(Collectors.toList());
        list.forEach( r -> {
            long count = r.getCount() != null ? r.getCount() : 0;
            r.setCount(AtomicIntegerUtil.getInstance(r.getClass(), r.getId(), count).get());
        });

        PageInfo pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    /**
     * 获取上机学生信息
     *
     */
    private void fillAdjunct(List<AdjunctStudent> adjunctStudent) {
        Map<String, Adjunct> Map = adminService.adjunctMap();
        adjunctStudent.forEach(t -> t.setAdjunct(Map.get(t.getAdjid())));
    }
}
