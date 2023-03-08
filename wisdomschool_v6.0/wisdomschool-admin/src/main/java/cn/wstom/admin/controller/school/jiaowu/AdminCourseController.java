package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程管理
 *
 * @author dws
 * @date 2019/02/09
 */
@Controller
@RequestMapping("/jiaowu/course")
public class AdminCourseController extends BaseController {
    private String prefix = "/school/jiaowu/course";
    private Integer department;
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private DepartmentService departmentService;
    @RequiresPermissions("jiaowu:course:view")
    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }


    /**
     * 获取课程列表
     *
     * @param
     * @return
     */
//    @RequiresPermissions("jiaowu:course:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Course course) {
        course.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        department= (Integer) WebUtils.getSession().getAttribute("did");
        if(department !=null ){
            course.setDept_id(department.toString());
        }

        startPage();
        List<Course> list = courseService.selectByCourses(course);
        fillDepartment(list);

        return wrapTable(list);
    }

    /**
     * 下拉框获取全部课程
     */
//    @RequiresPermissions("jiaowu:course:list")
    @PostMapping("/listpage")
    @ResponseBody
    public TableDataInfo listpage(Course course) {
        course.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        List<Course> listpage = courseService.list(course);

        return wrapTable(listpage);
    }


    /**
     * 树获取全部课程
     */
//    @RequiresPermissions("jiaowu:course:list")
    @GetMapping("/treelistpage2")
    @ResponseBody
    public List<Map<String, Object>> CourseTreeData2(){
        TeacherVo teacherVo =new TeacherVo();
        teacherVo.setSchoolId(getUser().getSchoolId());
        return courseService.roleCourseTreeData(teacherVo);

    }


    /**
     * 树获取全部课程
     */
//    @RequiresPermissions("jiaowu:course:list")
    @GetMapping("/treelistpage")
    @ResponseBody
    public List<Map<String, Object>> CourseTreeData(TeacherVo teacherVo){
        System.out.println("teacherVo123"+teacherVo);
        return courseService.roleCourseTreeData(teacherVo);

    }


    /**
     * 新增课程
     */
    @GetMapping("/add")
    public String toAdd(){
        return prefix + "/add";
    }

    /**
     * 保存课程
     */
    @RequiresPermissions("jiaowu:course:add")
    @Log(title = "课程管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(Course course) throws Exception {
        course.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        course.setDept_id(course.getDepartment().getId());
        return toAjax(courseService.save(course));
    }

    /**
     * 修改课程
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        Course course = courseService.getById(id);
        mmap.put("course", course);
        return prefix + "/edit";
    }

    /**
     * 修改课程
     */
    @RequiresPermissions("jiaowu:course:edit")
    @Log(title = "课程管理", actionType = ActionType.UPDATE)
    @PostMapping(value = "/edit", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Data edit(Course course) throws Exception {
        return toAjax(courseService.update(course));
    }

    /**
     * 删除课程
     */
    @RequiresPermissions("jiaowu:course:remove")
    @Log(title = "课程管理", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(courseService.removeById(ids));
    }
    @RequiresPermissions("jiaowu:course:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public Data importTemplate() {
        try {
            ExcelUtil<CourseVo> util = new ExcelUtil<>(CourseVo.class);
            return util.importTemplateExcel("course");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }
    @Log(title = "课程管理", actionType = ActionType.IMPORT)
    @RequiresPermissions("jiaowu:course:import")
    @PostMapping("/importData")
    @ResponseBody
    public Data importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<CourseVo> util = new ExcelUtil<>(CourseVo.class);
        List<CourseVo> courseList = util.importExcel(file.getInputStream());

        List<Department> departments = departmentService.list(null);
        Map<String, Department> departmentMap = transDepartment(departments);
        StringBuilder errorMsg = new StringBuilder();
        for (CourseVo u : courseList) {
            //校验数据
            Course course = new Course();
            BeanUtils.copyProperties(u, course);
            course.setDept_id(departmentMap.get(u.getDepartment().getName()).getId());
            course.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
            courseService.save(course);
        }
        /*String message = userService.importData(userList, updateSupport); */
        return Data.success();
    }


    /**
     * 校验年级名称
     */
    @PostMapping("/checkCourseNameUnique")
    @ResponseBody
    public boolean checkCourseNameUnique(Course course) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", course.getName());
        return courseService.count(parms)<=0;
    }
    /*
     * 得到系部的信息
     * */
    private void fillDepartment(List<Course> list) {
        Map<String, Department> departmentMap = departmentService.map(null);
        list.forEach (l -> {
            l.setDepartment(departmentMap.get(String.valueOf(l.getDept_id())));
        });

    }
    /**
     * 转换departmentMap类型
     *
     * @param departments
     * @return
     */
    private Map<String, Department> transDepartment(List<Department> departments) {
        Map<String, Department> departmentMap = new HashMap<>(departments.size());
        departments.forEach(d -> departmentMap.put(d.getName(), d));
        return departmentMap;
    }
}
