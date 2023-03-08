package cn.wstom.exam.controller;


import cn.wstom.exam.annotation.Log;
import cn.wstom.exam.constants.ActionType;
import cn.wstom.exam.constants.Data;
import cn.wstom.exam.entity.*;
import cn.wstom.exam.feign.AdminService;
import cn.wstom.exam.feign.StudentService;
import cn.wstom.exam.mapper.StuOptionExamanswerMapper;
import cn.wstom.exam.mapper.TestpaperOptinanswerMapper;
import cn.wstom.exam.service.*;
import cn.wstom.exam.utils.Uuid;
import cn.wstom.exam.utils.WordUtilOne;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 试卷与学生关联 信息操作处理
 *
 * @author hzh
 * @date 20190223
 */
@Controller
@RequestMapping("/school/onlineExam/userExam")
public class UserExamController extends BaseController {
    private String prefix = "school/onlineExam/userExam";

    @Autowired
    private UserExamService userExamService;
    @Autowired
    private TestPaperOneListService testPaperOneListService;
    @Autowired
    private CoursepaperCoursequestionsService coursepaperCoursequestionsService;
    @Autowired
    private TestPaperOneService testPaperOneService;
    @Autowired
    private TestpaperOneTestquestionsService testpaperOneTestquestionsService;
    @Autowired
    private TestpaperQuestionsService testpaperQuestionsService;
    @Autowired
    private StuOptionExamanswerService stuOptionExamanswerService;
    @Autowired
    private TestpaperOptinanswerService testpaperOptinanswerService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;


    @RequestMapping("/selectUserExamListBase")
    @ResponseBody
    List<UserExam> selectUserExamListBase(UserExam userExam){
        return userExamService.selectListBase(userExam);
    }

    @RequestMapping("/userExamList")
    @ResponseBody
    List<Map<String, Object>>userExamList(UserExam userExam) throws Exception {
        return userExamService.selectList(userExam);
    }
    @RequestMapping("/selectUserExam")
    @ResponseBody
    UserExam selectUserExam(@RequestParam("testPaperOneId") String testPaperOneId,@RequestParam("userId")String userId){
        return userExamService.selectUserExam(testPaperOneId,userId);
    }

    @RequestMapping("/updateUserExam")
    @ResponseBody
    Boolean updateUserExam(@RequestBody  UserExam userExam) throws Exception {
        return userExamService.update(userExam);
    }

    @RequestMapping("/findStuExamPaperTwo")
    @ResponseBody
    List<UserExam> findStuExamPaperTwo(UserExam userExam){
        return userExamService.findStuExamPaperTwo(userExam);
    }

    @RequestMapping("/findStuExamPaperOne")
    @ResponseBody
    List<UserExam> findStuExamPaperOne(UserExam userExam){
        return userExamService.findStuExamPaperOne(userExam);
    }

    @GetMapping("/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        System.out.println("cid::::" + cid);
        modelMap.put("cid", cid);
        return prefix + "/list";
    }

    /**
     * 查询测试关联列表
     */

    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, UserExam userTest) {
        userTest.setcId(cid);
        userTest.setCreateId(getUser().getUserAttrId());
        userTest.setType("1");
        String tid = getUser().getUserAttrId();
        userTest.setCreateId(tid);
        startPage();
        List<UserExam> list = userExamService.list(userTest);
        System.out.println("list::::"+list);
        return wrapTable(list);
    }

    @PostMapping("/getlist")
    @ResponseBody
    public List<TestPaperOne> getlist(String cid) {
        startPage();
        TestPaperOne testPaper = new TestPaperOne();
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("1");
        return testPaperOneService.list(testPaper);
    }

    @PostMapping("/getlistExam")
    @ResponseBody
    public List<TestPaperOne> getlistExam(String cid) {
//        startPage();
        TestPaperOne testPaper = new TestPaperOne();
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setCoursrId(cid);
        testPaper.setType("2");
        return testPaperOneService.list(testPaper);
    }



    /**
     * 修改
     */
    @GetMapping("/edit/{id}")

    public String toEdit(@PathVariable("id") Integer id, ModelMap mmap) {
        UserExam userTest = userExamService.getById(id);
        TestPaperOne testPaper = testPaperOneService.getById(userTest.getTestPaperOneId());
        mmap.put("userTest",userTest);
        mmap.put("testPaper",testPaper);
        return prefix + "/edit";
    }

    /**
     * 修改保存
     */

    @Log(title = "我的测试", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(UserExam UserTest) throws Exception {
        String tid1 = getUser().getUserAttrId();
        UserTest.setUpdateId(tid1);
        UserTest.setUpdateBy(getLoginName());
        try {
            return toAjax(userExamService.update(UserTest));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error(e.getMessage());
        }
    }

    /**
     * 新增测试关联
     */

    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        String tid = getUser().getUserAttrId();
        modelMap.put("cid", cid);
        modelMap.put("tid", tid);
        return prefix + "/add";
    }

    /**
     * 新增保存测试关联
     */

    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(UserExam userTest) {
        String[] userId = userTest.getUserId().split(",");
        List<UserExam> list = new ArrayList<UserExam>();
        try {
            for (String s : userId) {
                UserExam userTest2 = new UserExam();
                userTest2.setTestPaperOneId(userTest.getTestPaperOneId());
                userTest2.setUserId(s);
                List<UserExam> userTestList = new ArrayList<UserExam>();
                userTestList = userExamService.list(userTest2);
                if (userTestList.size() == 0) {
                    UserExam userTest1 = new UserExam();
                    userTest1.setTestPaperOneId(userTest.getTestPaperOneId());
                    userTest1.setUserId(s);
                    userTest1.setStuStartTime(userTest.getStuStartTime());
                    userTest1.setStuEndTime(userTest.getStuEndTime());
                    userTest1.setCreateBy(getUser().getLoginName());
                    userTest1.setSumbitState("0"); //0表示未提交,1为提交
                    userTest1.setcId(userTest.getcId());
                    String tid1 = getUser().getUserAttrId();
                    userTest1.setCreateId(tid1);
                    list.add(userTest1);
                }
            }
            return toAjax(userExamService.saveBatch(list));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error("记录已存在");
        }
    }


    //    @GetMapping("/fabu")
//
//    public String fabu(String id,String cid,ModelMap modelMap){
//        modelMap.put("id",id);
//        System.out.println("fabu id:"+id);
//        TestPaperOne testPaper = testPaperOneService.getById(id);
//        modelMap.put("cid",testPaper.getCoursrId());
//        modelMap.put("tid", getUser().getUserAttrId());
//         modelMap.put("rule",testPaper.getRule());
//        System.out.println("###################");
//        return prefix + "/fabu";
//    }
    @GetMapping("/fabu")

    public String fabu(String id,String cid,ModelMap modelMap){
        modelMap.put("id",id);
        TestPaperOneList testPaper = testPaperOneListService.getById(id);
        modelMap.put("cid",testPaper.getCoursrId());
        modelMap.put("tid", getUser().getUserAttrId());
        modelMap.put("rule",testPaper.getRule());
        return prefix + "/fabu";
    }



    @PostMapping("/addfabu")
    @ResponseBody
    public Data addfabu(UserExam userExam) {
        String[] userId = userExam.getUserId().split(",");//班级
        TestPaperOneList testPaperOneList = testPaperOneListService.getById(userExam.getTestPaperOneId());

        List<UserExam> list = new ArrayList<UserExam>(); //学生考试的记录

        /**
         *  -  发布后 添加学生试题
         *  -  因为可以重复更新题型，此处进行的操作为若不存在该题型的试卷则insert，存在则更新
         */

        try {
            StringBuilder stringBuffer = new StringBuilder();
            for (String s : userId) {
                Clbum clbum = adminService.getClbumById(s);
                stringBuffer.append(clbum.getName()).append(' ');
            }
//            SysUser sysUser1 = sysUserService.getById(userId[0]);
//            Student student = studentService.getById(sysUser1.getUserAttrId());
//            Clbum clbum = clbumService.getById(student.getCid());
//            Grades grades = gradesService.getById(student.getGid());
            String examStartTime=null;
            String examEndTime=null;

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //初始化时间格式
            Long startTime = new Long(userExam.getStuStartTime()); //将字符串转化为Long型

            Date startTimeDate = new Date(startTime);
            examStartTime = sdf.format(startTimeDate);
            Long endTime = new Long(userExam.getStuEndTime());
            Date endTimeDate = new Date(endTime);
            examEndTime = sdf.format(endTimeDate);
            TestPaperOne testPaperOne = new TestPaperOne();
            testPaperOne.setTestPaper(userExam.getTestPaperOneId());
            testPaperOne.setCoursrId(userExam.getcId());
            testPaperOne.setTime(userExam.getTime());
            testPaperOne.setStartTime(examStartTime);
            testPaperOne.setEndTime(examEndTime);
            testPaperOne.setCreateId(testPaperOneList.getCreateId());

            testPaperOne.setScope(stringBuffer.toString()); //设置考试的范围
            testPaperOne.setHeadline(testPaperOneList.getHeadline());
            testPaperOne.setTestName(testPaperOneList.getTestName());
            testPaperOne.setTestYear(testPaperOneList.getTestYear());
            testPaperOne.setType(testPaperOneList.getType());
            //组卷规则是否有改变
            if(userExam.getRule().equals("")){
                testPaperOne.setRule(testPaperOneList.getRule());
            }else{
                testPaperOne.setRule(userExam.getRule());
            }
            testPaperOne.setScore(testPaperOneList.getScore());
            //保存发布试卷信息
            testPaperOneService.save(testPaperOne);


            Timestamp timestamp=new Timestamp(System.currentTimeMillis());
            Long localtime=timestamp.getTime();
            System.out.println(localtime);

            //查询发布考试的试卷
            TestPaperOne testPaperOne2 = testPaperOneService.selectOne(testPaperOne);

            //有效时段定时
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    TestPaperOne testPaperOne3 = testPaperOneService.getById(testPaperOne2.getId());
                    SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date startdate = form.parse(testPaperOne3.getStartTime());
                        System.out.println(startdate.getTime());
                        if (localtime>=new Long(startdate.getTime())){
                            testPaperOne3.setSetExam("1"); //是否开始考试  0为否，1为是
                            try {
                                testPaperOneService.updateSetExam(testPaperOne3);
                                System.out.println("考试开始");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Date enddate = form.parse(testPaperOne3.getEndTime());
                        if (localtime>=new Long(enddate.getTime())){
                            testPaperOne3.setSetExam("2"); //考试失效
                            try {
                                testPaperOneService.updateSetExam(testPaperOne3);
                                System.out.println("考试有效时间已过");
                                timer.cancel();
                                timer.purge();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
            // 定义开始等待时间  --- 等待 5 秒
            // 1000ms = 1s
            long delay = 5000;
            // 定义每次执行的间隔时间
            long intevalPeriod = 10 * 1000;
            // schedules the task to be run in an interval
            // 安排任务在一段时间内运行
            timer.scheduleAtFixedRate(timerTask, delay, intevalPeriod);
            TestpaperOneTestquestions testpaperOneTestquestions = new TestpaperOneTestquestions();
            testpaperOneTestquestions.setTestPaperOneId(userExam.getTestPaperOneId());
            List<TestpaperOneTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperOneTestquestions>();
            testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperOneTestquestions);
            for (int i = 0; i < testpaperTestquestionsList.size(); i++) {
                Uuid tpUuid = new Uuid();
                TestpaperOneTestquestions testpaperOneTestquestions1 = testpaperOneTestquestionsService.getById(testpaperTestquestionsList.get(i).getId());
                TestpaperOneTestquestions testpaperOneTestquestions2 = new TestpaperOneTestquestions();
                testpaperOneTestquestions2.setId(tpUuid.UId());
                testpaperOneTestquestions2.setCreateId(testpaperOneTestquestions1.getCreateId());
                testpaperOneTestquestions2.setCreateBy(testpaperOneTestquestions1.getCreateBy());
                testpaperOneTestquestions2.setTestQuestionsId(testpaperOneTestquestions1.getTestQuestionsId());

                testpaperOneTestquestions2.setTestPaperOneListId(testPaperOne2.getId());

                testpaperOneTestquestionsService.save(testpaperOneTestquestions2);
            }
            //保存学生考生信息
            for (String s : userId) {
                Clbum clbum = adminService.getClbumById(s);
                Student student = new Student();
                student.setCid(clbum.getId());
                List<Student> studentList = studentService.studentList(student); //查找所有考试的学生
                for (Student value : studentList) {
                    SysUser dbUser = new SysUser();
                    dbUser.setUserAttrId(value.getId());
                    SysUser sysUser = adminService.getUser(dbUser);
                    UserExam userTest2 = new UserExam();
                    userTest2.setTestPaperOneId(userExam.getTestPaperOneId());
                    userTest2.setUserId(sysUser.getId());
                    List<UserExam> userTestList = new ArrayList<UserExam>();
                    userTestList = userExamService.list(userTest2); //UserExamMapper.xml->selectList //涉及的数据表：tk_user_exam
                    if (userTestList.size() == 0) {
                        UserExam userTest1 = new UserExam();
                        userTest1.setTestPaperOneId(testPaperOne2.getId());
                        userTest1.setUserId(sysUser.getId());
                        userTest1.setCreateBy(getUser().getLoginName());
                        userTest1.setSumbitState("0"); //0表示未提交,1为提交
                        userTest1.setcId(userExam.getcId());
                        String tid1 = getUser().getUserAttrId();
                        userTest1.setCreateId(tid1);
                        list.add(userTest1);

                    }
                }
            }
            return toAjax(userExamService.saveBatch(list));
        } catch (Exception e) {
            System.out.println("eee:"+e.getCause()+":"+e);
            System.out.println("123:"+userExam.getTestPaperOneId());
            System.out.println("456:"+list);

            return error("记录已存在");
        }
    }


    /**
     * 新增保存测试关联  期末考
     */

    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/addQ")
    @ResponseBody
    public Data addQ(UserExam userTest) {
        System.out.println("userTest:" + userTest);
        String[] userId = userTest.getUserId().split(",");
        List<UserExam> list = new ArrayList<UserExam>();
        try {
            for (String s : userId) {
                UserExam userTest2 = new UserExam();
                userTest2.setTestPaperOneId(userTest.getTestPaperOneId());
                userTest2.setUserId(s);
                List<UserExam> userTestList = new ArrayList<UserExam>();
                userTestList = userExamService.list(userTest2);
                if (userTestList.size() == 0) {
                    System.out.println("新纪录");
                    UserExam userTest1 = new UserExam();
                    userTest1.setTestPaperOneId(userTest.getTestPaperOneId());
                    userTest1.setUserId(s);
                    userTest1.setStuStartTime(userTest.getStuStartTime());
                    userTest1.setCreateBy(getUser().getLoginName());
                    userTest1.setStuEndTime(userTest.getStuEndTime());
                    userTest1.setSumbitState("0"); //0表示未提交,1为提交
                    userTest1.setcId(userTest.getcId());
                    String tid1 = getUser().getUserAttrId();
                    userTest1.setCreateId(tid1);
                    list.add(userTest1);
                }
            }
            return toAjax(userExamService.saveBatch(list));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }


    /**
     * 删除测试关联
     */

    @Log(title = "测试关联", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        String[] userId = ids.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(userId));
        try {
            return toAjax(userExamService.removeByIds(list));
        } catch (Exception e) {
            System.out.println(e.getCause());
            return error(e.getMessage());
        }
    }


    /**
     * 获取年级
     */
    @PostMapping("/getStudents")
    @ResponseBody
    public List<UserExam> getStudents(String cId, String tid) {
        startPage();
        List<UserExam> studentInfo = userExamService.getStudentInfo(tid, cId);
        System.out.println(studentInfo);
        return studentInfo;

    }

    /**
     * 获取班级
     */
    @PostMapping("/getTcoName")
    @ResponseBody
    public List<UserExam> getTcoName(String cId, String tid) {
        UserExam userTest = new UserExam();
        String crid = getUser().getUserAttrId();
        userTest.setCreateId(crid);
        userTest.setTcoId(cId);
        List<UserExam> list = userExamService.getTcoName(crid, cId);
        System.out.println(list);
        return userExamService.getTcoName(crid, cId);

    }

    /**
     * 获取学生
     */
    @PostMapping("/getTcoStu")
    @ResponseBody
    public List<UserExam> getTcoStu(String cId, String tid) {
        UserExam userTest = new UserExam();
        String crid = getUser().getUserAttrId();
        userTest.setCreateId(crid);
        userTest.setTcoId(cId);
        return userExamService.getTcoStu(crid, cId);

    }

    /**
     * 查询期末试卷列表
     */

    @PostMapping("/testPaperlist/{cid}")
    @ResponseBody
    public TableDataInfo testPaperlist(@PathVariable String cid, TestPaperOne testPaper) {
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setType("1");
        List<TestPaperOne> list = testPaperOneService.list(testPaper);
        return wrapTable(list);
    }

    /**
     * 查询期末试卷列表
     */

    @PostMapping("/testCoursePaperlist/{cid}")
    @ResponseBody
    public TableDataInfo testCoursePaperlist(@PathVariable String cid, TestPaperOne testPaper) {
        startPage();
        testPaper.setCoursrId(cid);
        testPaper.setCreateId(getUser().getUserAttrId());
        testPaper.setType("0");
        List<TestPaperOne> list = testPaperOneService.list(testPaper);
        return wrapTable(list);
    }
    /**
     * 设置提交的状态
     */
//    @RequestMapping("/setState")
//    @ResponseBody
//    public Data setState(@RequestParam String paperId,
//                         @RequestParam String studentId, @RequestParam String tutId)throws Exception {
//        System.out.println("====================设置提交状态paperId:" +paperId+"--studentId--"+studentId+"--tutId--"+tutId);
//        UserExam userTest1 = new UserExam();
//        List<UserExam> userTestList;
//        userTestList = userExamService.list(userTest1);
//        userTest1.setId(tutId);
//        userTest1.setSumbitState("1");
//        userTest1.setSumScore("1");
//        userTest1.setUpdateBy(getUser().getLoginName());
//        userTest1.setUserId(getUser().getUserAttrId());
//        userTest1.setUpdateBy(getUser().getLoginName());
//        return toAjax( userExamService.update(userTest1));
//    }
    @RequestMapping("/setState")
    @ResponseBody
    public Data setState(@RequestBody List<OptionExamSubmitVo> options, HttpServletResponse response)throws Exception {
        System.out.println("====================设置提交状态paperId:" + options.get(0).getPaperId() + "--studentId--" + options.get(0).getStudentId() + "--tutId--" + options.get(0).getTutId());
        UserExam userTest1 = new UserExam();
        List<UserExam> userTestList;
        userTestList = userExamService.list(userTest1);
        userTest1.setId(options.get(0).getTutId());
        userTest1.setSumbitState("1");
        userTest1.setSumScore("1");
        userTest1.setUpdateBy(getUser().getLoginName());
        userTest1.setUserId(options.get(0).getStudentId());

        XWPFDocument document = new XWPFDocument();
        try {
            List<TestpaperQuestions> tqvolist = new ArrayList<TestpaperQuestions>();
            System.out.println("options"+options);
            TestPaperOne testPaperOne = testPaperOneService.getById(options.get(0).getPaperId());
            UserExam userExam = userExamService.selectByExamId(options.get(0).getTutId());
            SysUser sysUser = adminService.getUserById(userExam.getUserId());
            SysUser dbUser=new SysUser();
            dbUser.setUserAttrId(testPaperOne.getCreateId());
            SysUser sysUser1 = adminService.getUser(sysUser);
            Student student = studentService.getStudentById(sysUser.getUserAttrId());
            Clbum clbum = adminService.getClbumById(student.getCid());
            Course course = adminService.getCourseById(testPaperOne.getCoursrId());
            if(testPaperOne.getRule().equals("1")){
                System.out.println("课程考试");
                CoursepaperCoursequestions coursepaperCoursequestions = new CoursepaperCoursequestions();
                coursepaperCoursequestions.setTestPaperId(options.get(0).getPaperId());
                coursepaperCoursequestions.setStudentId(options.get(0).getStudentId());
                List<CoursepaperCoursequestions> coursepaperCoursequestionsList = new ArrayList<CoursepaperCoursequestions>();
                coursepaperCoursequestionsList = coursepaperCoursequestionsService.list(coursepaperCoursequestions);
                for (int i = 0; i < coursepaperCoursequestionsList.size(); i++) {
                    System.out.println("题目数量："+coursepaperCoursequestionsList.size());
                    TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                    testpaperQuestions = testpaperQuestionsService.getById(coursepaperCoursequestionsList.get(i).getTestQuestionsId());
                    System.out.println("testpaperQuestions"+testpaperQuestions);
                    if (testpaperQuestions != null) {
                        System.out.println("加入");
                        tqvolist.add(testpaperQuestions);
                    }
                    for (TestpaperQuestions aTqvolist : tqvolist) {
                        //  填充选项对象
                        TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                        String[] anList = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
                        List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
                        for (String anAnList : anList) {
                            testpaperOptinanswer = testpaperOptinanswerService.getById(anAnList);
                            if (testpaperOptinanswer != null) {
                                testpaperOptinanswerList.add(testpaperOptinanswer);
                            }
                        }

                        //  填充学生选项
                        List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                        List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                        StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                        stuOptionExamanswer.setCreateId(Integer.valueOf(options.get(0).getStudentId()));
                        stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                        stuOptionExamanswer.setPaperOneId(Integer.valueOf(options.get(0).getPaperId()));
                        stuOptionExamanswerList1 = stuOptionExamanswerService.list(stuOptionExamanswer);//  查询学生作答记录

                        if (stuOptionExamanswerList1.size() != 0) {
                            stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                        }

                        aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                        aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项

                    }
                }

            }
            else {
                TestpaperOneTestquestions testpaperTestquestions = new TestpaperOneTestquestions();
                testpaperTestquestions.setTestPaperOneId(options.get(0).getPaperId());
                List<TestpaperOneTestquestions> testpaperTestquestionsList = new ArrayList<TestpaperOneTestquestions>();
                testpaperTestquestionsList = testpaperOneTestquestionsService.list(testpaperTestquestions);
                for (TestpaperOneTestquestions testpaperOneTestquestions : testpaperTestquestionsList) {
                    TestpaperQuestions testpaperQuestions = new TestpaperQuestions();
                    testpaperQuestions = testpaperQuestionsService.getById(testpaperOneTestquestions.getTestQuestionsId());
                    if (testpaperQuestions != null) {
                        tqvolist.add(testpaperQuestions);
                    }
                }
                for (TestpaperQuestions aTqvolist : tqvolist) {
                    //  填充选项对象
                    TestpaperOptinanswer testpaperOptinanswer = new TestpaperOptinanswer();
                    String[] anList = aTqvolist.getTestPaperOptionAnswerArr().split(";");// 题目选项
                    List<TestpaperOptinanswer> testpaperOptinanswerList = new ArrayList<TestpaperOptinanswer>();
                    for (String anAnList : anList) {
                        testpaperOptinanswer = testpaperOptinanswerService.getById(anAnList);
                        if (testpaperOptinanswer != null) {
                            testpaperOptinanswerList.add(testpaperOptinanswer);
                        }
                    }

                    //  填充学生选项
                    List<StuOptionExamanswer> stuOptionExamanswerListLater = new ArrayList<StuOptionExamanswer>();
                    List<StuOptionExamanswer> stuOptionExamanswerList1 = new ArrayList<StuOptionExamanswer>();
                    StuOptionExamanswer stuOptionExamanswer = new StuOptionExamanswer();
                    stuOptionExamanswer.setCreateId(Integer.valueOf(options.get(0).getStudentId()));
                    stuOptionExamanswer.setTestpaperOptionanswer(aTqvolist.getId());//  ps：添加外键工作量过大，直接修改该变量
                    stuOptionExamanswer.setPaperOneId(Integer.valueOf(options.get(0).getPaperId()));
                    stuOptionExamanswerList1 = stuOptionExamanswerService.list(stuOptionExamanswer);//  查询学生作答记录

                    if (stuOptionExamanswerList1.size() != 0) {
                        stuOptionExamanswerListLater.add(stuOptionExamanswerList1.get(0));
                    }

                    aTqvolist.setStuOptionExamanswerList(stuOptionExamanswerListLater);// 填加学生作答记录
                    aTqvolist.setTestpaperOptinanswerList(testpaperOptinanswerList);// 添加正确选项
                }
            }
            int flag = 64;
            int flag1 = 64;
            int num = 0;
            //然后循环你的数据，因为有多条，不想循环就直接set
            XWPFParagraph titleParagraph = document.createParagraph();
            //设置段落居中
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);

            // 标题
            XWPFRun titleParagraphRun = titleParagraph.createRun();
            //标题图
//            InputStream is = new FileInputStream("E:\\course\\csxyexam.jpg");
//            // 因为FileInputStream没有重写reset() 所有将流转为了byte数组
//            byte[] bs = IOUtils.toByteArray(is);
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bs));
//            titleParagraphRun.addPicture(new ByteArrayInputStream(bs), Document.PICTURE_TYPE_JPEG, "", 1385800, 785800);
            titleParagraphRun.setText("东莞理工学院城市学院（本科）试卷（A卷）\r");
            titleParagraphRun.setBold(true);

            // 设置字体颜色
            titleParagraphRun.setColor("000000");
            // 设置字体大小
            titleParagraphRun.setFontSize(16);
            XWPFRun titleParagraphRun1 = titleParagraph.createRun();
            titleParagraphRun1.setText("2020 -2021 学年第二学期\r");
            titleParagraphRun1.setBold(true);

            // 设置字体颜色
            titleParagraphRun1.setColor("000000");
            // 设置字体大小
            titleParagraphRun1.setFontSize(12);
            XWPFRun titleParagraphRun11 = titleParagraph.createRun();
            titleParagraphRun11.setText(testPaperOne.getTestName()+"\r\r");
            titleParagraphRun11.setBold(true);

            // 设置字体颜色
            titleParagraphRun11.setColor("000000");
            // 设置字体大小
            titleParagraphRun11.setFontSize(12);
            XWPFRun titleParagraphRun2 = titleParagraph.createRun();
            titleParagraphRun2.setText("开课单位："+testPaperOne.getHeadline()+"  考试形式：机试（闭） 卷，允许带\t入场\r科目："+course.getName()+" 班级："+clbum.getName()+"\r学号：" + sysUser.getLoginName() + " 姓名：" + sysUser.getUserName() + "\r");

            // 设置字体颜色
            titleParagraphRun2.setColor("000000");
            // 设置字体大小
            titleParagraphRun2.setFontSize(10);

            for (TestpaperQuestions oralHisStructureText : tqvolist) {
                num += 1;
                //添加表格
//                        XWPFTable xwpfTable = document.createTable(3,6);
//                    XWPFTableCell cell;
//                    XWPFTableRow row1 = xwpfTable.getRow(0);
//                        XWPFTableRow row2 = xwpfTable.getRow(1);
//                        XWPFTableRow row3 = xwpfTable.getRow(2);
//                        row1.setHeight(500);
//                        row2.setHeight(500);
//                        row3.setHeight(500);
//                    CTTblPr tablePr = xwpfTable.getCTTbl().addNewTblPr();
//                    CTTblWidth width = tablePr.addNewTblW();
//                    width.setW(BigInteger.valueOf(8000));
//
//                    row1.getCell(0).setText("题序");
//                        row1.getCell(1).setText("一");
//                        row1.getCell(2).setText("二");
//                        row1.getCell(3).setText("三");
//                        row1.getCell(4).setText("四");
//                        row1.getCell(5).setText("总分");
//                        row2.getCell(0).setText("得分");
//                        row3.getCell(0).setText("评卷人");
                //段落
                XWPFParagraph firstParagraph = document.createParagraph();
                XWPFRun run = firstParagraph.createRun();
                run.setText(oralHisStructureText.getTitleTypeName() + "\r");
                run.setColor("000000");
                run.setFontSize(13);
                XWPFRun run1 = firstParagraph.createRun();
                if (oralHisStructureText.gettQuestiontemplateNum().equals("1")) {
                    String[] stuAnswerList = oralHisStructureText.getStuOptionExamanswerList().get(0).getStuAnswer().split(";");
                    String stuAnswer = null;
                    for (int i = 0; i < stuAnswerList.length; i++) {
                        if(stuAnswerList[i].equals("0")){

                        }else {
                            stuAnswer = stuAnswerList[i];
                        }
                    }

                    run1.setText(num + "、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）" + oralHisStructureText.getTitle() + "\r学生答案：" + stuAnswer + "\r");
//                        run1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
                    for (int i = 0; i < oralHisStructureText.getTestpaperOptinanswerList().size(); i++) {
                        flag += 1;
                        char fla = (char) flag;
                        run1.setText(fla + "、" + oralHisStructureText.getTestpaperOptinanswerList().get(i).getStoption() + "\r");
                    }
                    run1.setColor("000000");
                    run1.setFontSize(10);
                } else if (oralHisStructureText.gettQuestiontemplateNum().equals("2")) {
                    String[] stuAnswerList = oralHisStructureText.getStuOptionExamanswerList().get(0).getStuAnswer().split(";");
                    String stuAnswer = "";
                    for (int i = 0; i < stuAnswerList.length; i++) {
                        System.out.println("stuAnswerList[i]"+stuAnswerList[i]);
                        if(stuAnswerList[i].equals("0")){

                        }else {
                            stuAnswer += stuAnswerList[i];
                        }
                    }
                    System.out.println("stuAnswer"+stuAnswer);
                    run1.setText(num + "、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）" + oralHisStructureText.getTitle() +"\r学生答案："+stuAnswer + "\r");
                    for (int i = 0; i < oralHisStructureText.getTestpaperOptinanswerList().size(); i++) {
                        flag1 += 1;
                        char fla = (char) flag1;
                        run1.setText(fla + "、" + oralHisStructureText.getTestpaperOptinanswerList().get(i).getStoption()  + "\r");
                    }
                    run1.setColor("000000");
                    run1.setFontSize(10);
                } else {
                    for (int i = 0; i <oralHisStructureText.getStuOptionExamanswerList().size() ; i++) {
                        if(oralHisStructureText.getStuOptionExamanswerList().get(i).getAnswerType()!="") {
                            String[] s = oralHisStructureText.getStuOptionExamanswerList().get(i).getAnswerType().split(";");
                            run1.setText(num + "、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）" + oralHisStructureText.getTitle() +"\r学生答案：\r"+oralHisStructureText.getStuOptionExamanswerList().get(i).getStuAnswer()+"\r");
                            run1.setColor("000000");
                            run1.setFontSize(10);
                            for (int j = 0; j <s.length; j++) {
                                String path = "http://localhost:8081/storage/showCondensedPicture?fileId=" + s[j];
                                System.out.println("path"+path);
                                URL url = new URL(path);
                                InputStream is1 = url.openStream();
                                // 因为FileInputStream没有重写reset() 所有将流转为了byte数组
                                byte[] bs1 = IOUtils.toByteArray(is1);
                                BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(bs1));
                                run1.addPicture(new ByteArrayInputStream(bs1), Document.PICTURE_TYPE_JPEG, "", 1385800, 785800);
                            }

                        }else {
                            run1.setText(num + "、(得分：_______)（"+oralHisStructureText.getQuestionScore()+"分）" + oralHisStructureText.getTitle() + "\r学生答案：" + oralHisStructureText.getStuOptionExamanswerList().get(i).getStuAnswer() + "\r");
                            run1.setColor("000000");
                            run1.setFontSize(10);
                        }
                    }

                }
                //换行
                XWPFParagraph paragraph1 = document.createParagraph();
                XWPFRun paragraphRun1 = paragraph1.createRun();
                paragraphRun1.setText("\r");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MMddHHmmss");
            String s = String.valueOf(System.currentTimeMillis());
//                String fileName = sysUser.getLoginName() + "" + s.substring(7, 13);
            String fileName = sysUser.getLoginName() + ""+sysUser.getUserName()+""+testPaperOne.getTestName();
            File file = new File("F:\\"+sysUser1.getUserName()+"\\"+clbum.getName()+"_"+course.getName());
//                File file = new File("/root/soft/"+sysUser1.getUserName()+"/"+clbum.getName()+"_"+course.getName());
            if (!file.exists()){
                file.mkdirs();
            }
            new WordUtilOne().exportWord(document, response, fileName,file);
        } catch(Exception e1){
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        System.out.println("导出成功！！！！");

        return toAjax( userExamService.update(userTest1));
    }


}
