package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Constants;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import cn.wstom.student.feign.ExamService;
import cn.wstom.student.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedList;
import java.util.List;

/**
 * @author liniec
 * @date 2020/01/20 20:01
 *  所有个人做题练习的控制器
 */
@Controller
@RequestMapping("/account")
public class AccountExerciseController extends BaseController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamService examService;


    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        Integer id = getUser().getSchoolId();
        School school = adminService.getSchoolById(String.valueOf(id));
        modelMap.put("sysUser",getUser());
        modelMap.put("school",school);
        return "/front/account/index";
    }

    //@ApiOperation("个人主页：练习列表")
    @GetMapping("/exercise")
    public String indexExe(ModelMap modelMap) throws Exception {
        //  TODO: 参照牛客，学生自选题型，后台组装试题
        Student student = studentService.getById(getUser().getUserAttrId());
        ClbumCourse cc = new ClbumCourse();
        cc.setCid(student.getCid());
        PageDomain pageDomain = TableSupport.buildPageRequest();

        List<ClbumCourse> list = adminService.clbumCourseList(cc,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
        PageInfo<ClbumCourse> pageInfo = new PageInfo(list);

        //填充教师信息
        List<ClbumCourseVo> clbumCourseVos = new LinkedList<>();
        pageInfo.getList().forEach( c -> {
            ClbumCourseVo clbumCourseVo = new ClbumCourseVo();
            clbumCourseVo.setId(c.getId());
            clbumCourseVo.setCourse(adminService.getCourseById(c.getCid()));
            clbumCourseVo.setTeacherCourse(adminService.getTeacherCourseById(c.getTcid()));
            clbumCourseVos.add(clbumCourseVo);
        });

        modelMap.put("courses", clbumCourseVos);
        return "/front/account/exercise";
    }

   // @ApiOperation("个人主页：试卷列表")
    @GetMapping("/exam")
    public String indexExam(ModelMap modelMap) throws Exception {
        UserTest userTest = new UserTest();
        userTest.setUserId(getUserId());
        userTest.setSetExam(Constants.EXAM_TYPE_ING);
        userTest.setSumbitState(Constants.EXAM_SUBMIT_SCORE_WAIT);//    只显示未提交的试卷
        userTest.setType(Constants.TEST_PAPER_TYPE_EXAM);// 试题类型：试卷

        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<UserTest> exams = examService.getTcoExamPaperByUserTest(userTest,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());


        modelMap.put("exams", exams);
        modelMap.put("userId", getUserId());
        System.out.println(exams.toString());
        return "/front/account/exam";
    }

  //  @ApiOperation("个人主页：作业列表")
    @GetMapping("/work")
    public String indexWork(ModelMap modelMap) throws Exception {
        UserTest userTest = new UserTest();
        userTest.setUserId(getUserId());
        userTest.setSetExam(Constants.EXAM_TYPE_ING);
        userTest.setSumbitState(Constants.EXAM_SUBMIT_SCORE_WAIT);//    只显示未提交的作业
        userTest.setType(Constants.TEST_PAPER_TYPE_WORK);// 试题类型：作业


        PageDomain pageDomain = TableSupport.buildPageRequest();
        List<UserTest> works = examService.getTcoExamPaperByUserTest(userTest,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());

        modelMap.put("works", works);
        modelMap.put("userId", getUserId());
        return "/front/account/work";
    }


//    /**
//     * 页面跳转 章节测试
//     */
//    @ApiOperation("章节作业界面")
//    @GetMapping("/chapterTest")
//    public String chapterTest(String testPaperId, String studentId, String tutId, ModelMap modelMap) {
//        TestPaper testPaper = new TestPaper();
//        testPaper = testPaperService.getById(testPaperId);
//        System.out.println("testPaper:" + testPaper);
//        modelMap.put("testPaper", testPaper);
//        modelMap.put("paperId", testPaperId);
//        modelMap.put("studentId", getUser().getUserAttrId());
//        modelMap.put("stuName", ShiroUtils.getLoginName());
//        modelMap.put("name", getUser().getUserName());
//        return "front/exercise/chapterWork";
//    }

    /**
     * 页面跳转 课程作业
     */
    //@ApiOperation("课程作业界面")
    @GetMapping("/courseWork")
    public String stuToCourseWork(String testPaperId, String studentId, String tutId, ModelMap modelMap) {
        TestPaper testPaper = new TestPaper();
        testPaper = examService.getTestPaperById(testPaperId);
        //  判断试卷状态
        if (Constants.EXAM_TYPE_ING.equals(testPaper.getSetExam())) {
            modelMap.put("testPaper", testPaper);
            modelMap.put("paperId", testPaperId);
            modelMap.put("studentId", studentId);
            modelMap.put("userId", getUserId());
            modelMap.put("tutId", tutId);
            modelMap.put("stuName", getUser().getLoginName());
            modelMap.put("name", getUser().getUserName());
            return "/front/exercise/courseWork";
        } else {
            return "/front/exercise/outTime";
        }
    }


    /**
     * 页面跳转 课程考试
     */
 //   @ApiOperation("课程试卷界面")
    @GetMapping("/courseExam")
    public String stuToCourseExam(String testPaperId, String studentId, String tutId, ModelMap modelMap) {
        TestPaper testPaper = new TestPaper();
        testPaper = examService.getTestPaperById(testPaperId);
        //  判断试卷状态
        if (Constants.EXAM_TYPE_ING.equals(testPaper.getSetExam())) {
            modelMap.put("testPaper", testPaper);
            modelMap.put("paperId", testPaperId);
            modelMap.put("studentId", studentId);
            modelMap.put("tutId", tutId);
            modelMap.put("stuName", getUser().getLoginName());
            modelMap.put("name", getUser().getUserName());
            return "/front/exercise/courseExam";
        } else {
            return "/front/exercise/outTime";
        }
    }
}
