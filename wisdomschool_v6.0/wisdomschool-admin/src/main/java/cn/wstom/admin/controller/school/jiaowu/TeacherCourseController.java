package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.feign.ExamService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.exception.DeleteException;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/jiaowu/teacherCourse")
public class TeacherCourseController extends BaseController {


    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ExamService examService;


    @RequestMapping(value = "/{teacherCourseId}")
    @ResponseBody
    TeacherCourse getTeacherCourseById(@PathVariable(value = "teacherCourseId")String teacherCourseId){
        return teacherCourseService.getById(teacherCourseId);
    }

    @RequestMapping(value = "/teacherCourseList")
    @ResponseBody
    List<TeacherCourse> teacherCourseList (@RequestBody TeacherCourse teacherCourse){
        return teacherCourseService.list(teacherCourse);
    }

    @RequestMapping(value = "/getTeacherCourse")
    @ResponseBody
    TeacherCourse getTeacherCourse(@RequestBody TeacherCourse teacherCourse){
        return teacherCourseService.selectOne(teacherCourse);
    }

    @RequestMapping(value = "/teacherCourseMapByIds")
    @ResponseBody
    Map<String, TeacherCourse> teacherCourseMapByIds(@RequestBody List<String> tcIds){
        return teacherCourseService.mapByIds(tcIds);
    }

    @RequestMapping(value = "/teacherMapByIds")
    @ResponseBody
    Map<String, Teacher> teacherMapByIds(@RequestBody List<String> tIds){
      return   teacherService.mapByIds(tIds);
    }


    @PostMapping("/noSelect/{id}")
    @ResponseBody
    public TableDataInfo noSelect(@PathVariable("id") String id, ModelMap modelMap) {
        TeacherCourse tc = new TeacherCourse();
        tc.setTid(id);
        List<TeacherCourse> tcs = teacherCourseService.list(tc);

        startPage();
        List<Course> courses = courseService.list(new Course());

        List<Course> courseList = new ArrayList<>();
        Map<String, TeacherCourse> map = new HashMap<>();
        for (TeacherCourse t : tcs) {
            map.put(t.getCid(), t);
        }
        for (Course c : courses) {
            if (map.get(c.getId()) != null) {
                courseList.add(c);
            }
        }

        courseList.forEach(courses::remove);

        return wrapTable(courses);
    }

    /***
     *初始化教师课程题型
     */
    @GetMapping("/chushihua")
    @ResponseBody
    public String chushihua() {
        TeacherCourse tc = new TeacherCourse();
        List<TeacherCourse> tcs = teacherCourseService.list(tc);
        for (TeacherCourse teacherCourse : tcs) {
            List<MyTitleType> mtys = examService.findByCidAndTid(teacherCourse.getCid(), teacherCourse.getTid());
            if (mtys.size() == 0) {
                MyTitleType mty = new MyTitleType();
                mty.setCid(teacherCourse.getCid());
                mty.setCreateId(teacherCourse.getTid());
                mty.setPublicTitleId("40288b0e54964a390154968c219e0000");
                mty.setTitleTypeName("单项选择题");
                try {
                    examService.saveMyTitleType(mty);
                } catch (Exception e) {
                    System.out.println("初始化选择题失败");
                    e.printStackTrace();
                }
                MyTitleType mty1 = new MyTitleType();
                mty1.setCid(teacherCourse.getCid());
                mty1.setCreateId(teacherCourse.getTid());
                mty1.setPublicTitleId("8a94ab4e4d310474014d3168e29c0001");
                mty1.setTitleTypeName("判断题");
                try {
                    examService.saveMyTitleType(mty1);
                } catch (Exception e) {
                    System.out.println("初始化填空题失败");
                    e.printStackTrace();
                }
                MyTitleType mty2 = new MyTitleType();
                mty2.setCid(teacherCourse.getCid());
                mty2.setCreateId(teacherCourse.getTid());
                mty2.setPublicTitleId("8a94be094cdccb13014cdcce42b40000");
                mty2.setTitleTypeName("多项选择题");
                try {
                    examService.saveMyTitleType(mty2);
                } catch (Exception e) {
                    System.out.println("初始化多项选择题失败");
                    e.printStackTrace();
                }
            }
            ;
        }
        return "sususus";
    }

    /**
     * 获取教师该课程的题目类型id
     * // *
     */
//@RequestMapping(value = "getAllSearch",produces = "application/json;charset=utf-8")
    @GetMapping("/gettixingid")
    @ResponseBody
    public List<String> gettixingid(String cid, String tid) {

        List<MyTitleType> mtys = examService.findByCidAndTid(cid, tid);
        List<String> re = new ArrayList<>();
        for (MyTitleType mty : mtys) {
            re.add(mty.getId());
            re.add(mty.getTitleTypeName());
            System.out.println("题型id" + mty.getId());
            System.out.println("题型名称" + mty.getTitleTypeName());
        }

        return re;
    }

    @PostMapping("/already/{id}")
    @ResponseBody
    public TableDataInfo already(@PathVariable("id") String id, ModelMap modelMap) {

        TeacherCourse tc = new TeacherCourse();
        tc.setTid(id);
        startPage();
        List<TeacherCourse> tcs = teacherCourseService.list(tc);

        tcs.forEach(t -> {
            Map<String, Object> map = new HashMap<>();
            Course c = courseService.getById(t.getCid());
            map.put("name", c.getName());
            map.put("tcId", t.getId());
            t.setParams(map);
        });

        return wrapTable(tcs);
    }

    @RequiresPermissions("jiaowu:teacher:add")
    @Log(title = "教师课堂管理", actionType = ActionType.UPDATE)
    @PostMapping("/add/{id}/{cid}")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data edit(@PathVariable("id") String id, @PathVariable("cid")String[] cIds) throws Exception {

        int de =teacherCourseService.deletetids(id);
        List<TeacherCourse> teacherCourses = new LinkedList<>();

        for (String cid : cIds) {
            TeacherCourse tc = new TeacherCourse(id, cid);
            tc.setThumbnailPath("morentupian");
            tc.setCreateBy(getLoginName());
            tc.setThumbnailPath(Constants.BOOK_DEFAULT_AVATAR);
            teacherCourses.add(tc);
        }
        try {
            return toAjax(teacherCourseService.saveBatch(teacherCourses));
        } catch (Exception e) {
            return Data.error("分配教师课程异常!");
        }

    }
//旧版
//    @RequiresPermissions("jiaowu:teacher:add")
//    @Log(title = "教师课堂管理", actionType = ActionType.UPDATE)
//    @PostMapping("/add/{id}")
//    @Transactional(rollbackFor = Exception.class)
//    @ResponseBody
//    public Data edit(@PathVariable("id") String id, String cId) throws Exception {
//        TeacherCourse teacherCourse = new TeacherCourse();
//        teacherCourse.setThumbnailPath("morentupian");
//        teacherCourse.setTid(id);
//        teacherCourse.setCid(cId);
//        teacherCourse.setCreateBy(getLoginName());
//        System.out.println("aaaa"+teacherCourse);
//        try {
//            return toAjax(teacherCourseService.save(teacherCourse));
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new DeleteException();
//        }
//    }


    @RequiresPermissions("jiaowu:teacher:add")
    @Log(title = "教师课堂管理", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data remove(String tcId) throws DeleteException {
        try {
            return toAjax(teacherCourseService.removeById(tcId));
        } catch (Exception e) {
            throw new DeleteException();
        }
    }
}
