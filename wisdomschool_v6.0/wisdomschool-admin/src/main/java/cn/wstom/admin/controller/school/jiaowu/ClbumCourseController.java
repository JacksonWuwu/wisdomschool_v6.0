package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.UserConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 班级课程 信息操作处理
 *
 * @author dws
 * @date 20190218
 */
@Controller
@RequestMapping("/jiaowu/clbumcourse")
public class ClbumCourseController extends BaseController {
    private String prefix = "school/jiaowu/clbumcourse/";
    private Integer department;
    @Autowired
    private ClbumCourseVoService clbumCourseVoService;

    @Autowired
    private ClbumCourseService clbumCourseService;

    @Autowired
    private GradesService gradesService;

    @Autowired
    private TeacherCourseService teacherCourseService;

    @Autowired
    private ClbumService clbumService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SysUserService sysUserService;


    @GetMapping()
    public String toList() {
        return prefix + "list";
    }



    @RequestMapping(value = "/clbumCourseVos")
    @ResponseBody
    List<ClbumCourseVo> clbumCourseVos (@RequestBody ClbumCourseVo clbumCourseVo){
        return clbumCourseVoService.selectByClbumCourseVos(clbumCourseVo);
    }

    @RequestMapping(value = "/clbumCourseList")
    @ResponseBody
    List<ClbumCourse> clbumCourseList (@RequestBody ClbumCourse clbumCourse,
                                       @RequestParam(required = false,value = "pageNum")Integer pageNum,
                                       @RequestParam(required = false,value = "pageSize")Integer pageSize,
                                       @RequestParam(required = false,value = "orderBy")String orderBy){
        PageHelper.startPage(pageNum, pageSize, orderBy);
        return clbumCourseService.list(clbumCourse);
    }
    /**
     * 查询班级课程列表
     */
//    @RequiresPermissions("jiaowu:clbumcourse:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClbumCourseVo clbumCourse) {
        department= (Integer) WebUtils.getSession().getAttribute("did");
        if(department !=null ){
            Department departments=departmentService.getById(department);
            clbumCourse.setDepartment(departments);
        }


        clbumCourse.setSchoolId(String.valueOf(getUser().getSchoolId()));
        startPage();
        List<ClbumCourse> clbumCourselist = clbumCourseVoService.selectByClbumCourseVos(clbumCourse);
        List<String> tcId = new LinkedList<>();
        //获取教师课程id
        clbumCourselist.forEach(cc -> tcId.add(cc.getTcid()));
        List<TeacherCourse> teacherCourseList = new LinkedList<>();
        //获取教师课程信息集合
        if(!tcId.isEmpty()) {
            teacherCourseList = teacherCourseService.listByIds(tcId);
        }
        List<String> teaId = new LinkedList<>();
        //获取教师属性id
        teacherCourseList.forEach(tc -> teaId.add(tc.getTid()));
        String[] teaIds=teaId.toArray(new String[teaId.size()]);
        List<SysUser> sysUsers = new LinkedList<>();
        //sysUsers填充角色属性和教师id
        for (String tid : teaIds) {
            sysUsers.add(new SysUser(UserConstants.USER_TEACHER, tid));
        }
        List<SysUser> sysUserList = new LinkedList<>();
        if (!sysUsers.isEmpty()) {
            sysUserList = sysUserService.selectUserName(sysUsers);
        }
        Map<String, SysUser> userMap = new LinkedHashMap<>();
        //List转Map
        sysUserList.forEach(u -> userMap.put(u.getUserAttrId(), u));

        List<ClbumCourseVo> clbumCourseVos = trans(clbumCourselist);


        //填充年级班级课程等信息
        fillGrades(clbumCourseVos);
        fillClbum(clbumCourseVos);
        fillMajor(clbumCourseVos);
        fillDepartment(clbumCourseVos);
        fillTeacherCourse(clbumCourseVos);
        clbumCourseVos.forEach(ccv -> ccv.setSysUser(userMap.get(ccv.getTeacherCourse().getTid())));
        //fillTeacherCourse(clbumCourseVos);

        return wrapTable(clbumCourseVos, new PageInfo<>(clbumCourselist).getTotal());

    }

    /**
     * 查询教师课程列表
     */
//    @RequiresPermissions("jiaowu:clbumcourse:tclist")
    @PostMapping("/tclist")
    @ResponseBody
    public TableDataInfo tclist(TeacherCourseVo teacherCourse) {
        Object fid = teacherCourse.getParams().get("fid");
        if (fid != null) {
            teacherCourse.setCid(fid.toString());
        }
        startPage();
        List<TeacherCourse> teacherCourseList = teacherCourseService.list(teacherCourse);
        List<String> teaId = new LinkedList<>();
        //获取教师属性id
        teacherCourseList.forEach(tc -> teaId.add(tc.getTid()));
        String[] teaIds=teaId.toArray(new String[teaId.size()]);
        List<SysUser> sysUsers = new LinkedList<>();
        //sysUsers填充角色属性和教师id
        for (String tid : teaIds) {
            sysUsers.add(new SysUser(UserConstants.USER_TEACHER, tid));
        }
        List<SysUser> sysUserList = sysUserService.selectUserName(sysUsers);
        Map<String, SysUser> userMap = new HashMap<>();
        //List转Map
        sysUserList.forEach(u -> userMap.put(u.getUserAttrId(), u));

        List<TeacherCourseVo> teacherCourseVos = trans2(teacherCourseList,userMap);
        //填充教师信息
        fillTeacher(teacherCourseVos);
        //fillSysuser(teacherCourseVos);
        return wrapTable(teacherCourseVos, new PageInfo<>(teacherCourseList).getTotal());
    }

    //    @RequiresPermissions("jiaowu:clbumcourse:tclist")
    @PostMapping("/tclistpage")
    @ResponseBody
    public TableDataInfo tclistpage(TeacherCourseVo teacherCourse) {
        Object fid = teacherCourse.getParams().get("fid");
        if (fid != null) {
            teacherCourse.setCid(fid.toString());
        }
        List<TeacherCourse> teacherCourseList = teacherCourseService.list(teacherCourse);
        System.out.println("teacherCourseList======"+teacherCourseList);
        List<String> teaId = new LinkedList<>();
        //获取教师属性id
        teacherCourseList.forEach(tc -> teaId.add(tc.getTid()));
        String[] teaIds=teaId.toArray(new String[teaId.size()]);
        List<SysUser> sysUsers = new LinkedList<>();
        //sysUsers填充角色属性和教师id
        for (String tid : teaIds) {
            sysUsers.add(new SysUser(UserConstants.USER_TEACHER, tid));
        }
        List<SysUser> sysUserList = sysUserService.selectUserName(sysUsers);
        Map<String, SysUser> userMap = new HashMap<>();
        //List转Map
        sysUserList.forEach(u -> userMap.put(u.getUserAttrId(), u));
        List<TeacherCourseVo> teacherCourseVos = trans2(teacherCourseList,userMap);
        //填充教师信息
        fillTeacher(teacherCourseVos);
        //fillSysuser(teacherCourseVos);
        return wrapTable(teacherCourseVos, new PageInfo<>(teacherCourseList).getTotal());
    }


    /**
     * 新增班级课程
     */
    @GetMapping("/add")
    public String toAdd( ModelMap modelMap) {
//        Teacher teacher=new Teacher();
//        List<Teacher> teachers=teacherService.list(teacher);
//        List<String> teaId = new LinkedList<>();
//        //获取教师属性id
//        teachers.forEach(tc -> teaId.add(tc.getId()));
//        String[] teaIds=teaId.toArray(new String[teaId.size()]);
//        List<SysUser> sysUsers = new LinkedList<>();
//        //sysUsers填充角色属性和教师id
//        for (String tid : teaIds) {
//            sysUsers.add(new SysUser(UserConstants.USER_TEACHER, tid));
//        }
//        List<SysUser> sysUserList = sysUserService.selectUserName(sysUsers);
//        Map<String, SysUser> userMap = new HashMap<>();
//        //List转Map
//        sysUserList.forEach(u -> userMap.put(u.getUserAttrId(), u));
//        List<TeacherCourseVo> teacherCourseVos = trans2(teacherCourseList,userMap);
//        //填充教师信息
//        fillTeacher(teacherCourseVos);
        return prefix + "add";
    }

    /**
     * 新增保存班级课程
     */
    @RequiresPermissions("jiaowu:clbumcourse:add")
    @Log(title = "班级课程", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(ClbumCourseVo clbumCourseVo) throws Exception {
        //保存班级课程信息
        ClbumCourse clbumCourse = new ClbumCourse();

        BeanUtils.copyProperties(clbumCourseVo, clbumCourse);
        clbumCourse.setCid(clbumCourseVo.getClbum().getId());
        clbumCourse.setGid(clbumCourseVo.getGrades().getId());
        clbumCourse.setTcid(clbumCourseVo.getTeacherCourse().getId());
        clbumCourse.setGname(clbumCourseVo.getGrades().getName());
        clbumCourse.setCreateBy(getUser().getId());
        int con=clbumCourseService.ClbumCourseSelectCount(clbumCourse);
        if (con != 0) {
            return error();
        } else {
            return toAjax(clbumCourseService.save(clbumCourse));
        }
    }

    /**
     * 修改班级课程
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap modelMap) {
        ClbumCourse clbumCourse = clbumCourseService.getById(id);
        SysUser sysUser = new SysUser();
        String tcId = clbumCourse.getTcid();
        TeacherCourse teacherCourse = teacherCourseService.getById(tcId);
        String tid =teacherCourse.getTid();
        sysUser.setUserType(UserConstants.USER_TEACHER);
        sysUser.setUserAttrId(tid);
        sysUser = sysUserService.selectOneUserName(sysUser);

        ClbumCourseVo clbumCourseVo = trans(clbumCourse);
        clbumCourseVo.setTeacherCourse(teacherCourseService.getById(clbumCourse.getTcid()));
        clbumCourseVo.setClbum(clbumService.getById(clbumCourse.getCid()));
        clbumCourseVo.setGrades(gradesService.getById(clbumCourse.getGid()));
        clbumCourseVo.setMajor(majorService.getById(clbumCourseVo.getClbum().getMid()));
        clbumCourseVo.setDepartment(departmentService.getById(clbumCourseVo.getMajor().getDid()));
        clbumCourseVo.setCourse(courseService.getById(clbumCourseVo.getTeacherCourse().getCid()));
        clbumCourseVo.setSysUser(sysUser);

        modelMap.put("clbumCourse", clbumCourseVo);
        return prefix + "edit";
    }

    /**
     * 修改保存班级课程
     */
    @RequiresPermissions("jiaowu:clbumcourse:edit")
    @Log(title = "班级课程", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(ClbumCourseVo clbumCourseVo) throws Exception {
        //保存班级课程信息
        ClbumCourse clbumCourse = new ClbumCourse();
        BeanUtils.copyProperties(clbumCourseVo, clbumCourse);
        clbumCourse.setGid(clbumCourseVo.getGrades().getId());
        clbumCourse.setTcid(clbumCourseVo.getTeacherCourse().getId());
        clbumCourse.setCid(clbumCourseVo.getClbum().getId());
        clbumCourse.setGname(clbumCourseVo.getGrades().getName());
        clbumCourse.setCreateBy(getUser().getId());
        return toAjax(clbumCourseService.update(clbumCourse));
    }
    @RequiresPermissions("jiaowu:clbumcourse:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public Data importTemplate() {
        try {
            ExcelUtil<ClbumCourseVo> util = new ExcelUtil<>(ClbumCourseVo.class);
            return util.importTemplateExcel("clbumcourse");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }

    @Log(title = "班级课程", actionType = ActionType.IMPORT)
    @RequiresPermissions("jiaowu:clbumcourse:import")
    @PostMapping("/importData")
    @ResponseBody
    public Data importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<ClbumCourseVo> util = new ExcelUtil<>(ClbumCourseVo.class);
        List<ClbumCourseVo> userList = util.importExcel(file.getInputStream());
        List<Major> majors = majorService.list(null);
        Map<String, Major> majorMap = transMajor(majors);
        List<Clbum> clbums = clbumService.list(null);
        Map<String, Clbum> clbumMap = transClbum(clbums);
        List<Course> courses = courseService.list(null);
        Map<String, Course> courseMap = transCourse(courses);
        List<Department> departments = departmentService.list(null);
        Map<String, Department> departmentMap = transDepartment(departments);
        List<Grades> grades = gradesService.list(null);
        Map<String, Grades> gradesMap = transGrades(grades);
        List<SysUser> sysUsers = sysUserService.list(null);
        Map<String, SysUser> sysUserMap = transSysUser(sysUsers);
        StringBuilder errorMsg = new StringBuilder();
        for (ClbumCourseVo u : userList) {

            //校验数据
            if (!majorMap.get(u.getMajor().getName()).getDid().equals(departmentMap.get(u.getDepartment().getName()).getId())) {
                errorMsg.append("系部【").append(u.getDepartment().getName()).append("】不存在专业【").append(u.getMajor().getName()).append("】");
                return Data.error(errorMsg.toString());
            }
            Clbum clbum = clbumMap.get(u.getClbum().getName());
            SysUser sysUser = sysUserMap.get(u.getSysUser().getUserName());
            Course course = courseMap.get(u.getCourse().getName());
            if (!clbum.getMid().equals(majorMap.get(u.getMajor().getName()).getId())) {
                errorMsg.append("专业【").append(u.getClbum().getName()).append("】不存在班级【").append(u.getClbum().getName()).append("】");
                return Data.error(errorMsg.toString());
            }
            TeacherCourse teacherCourse = new TeacherCourse();
//            BeanUtils.copyProperties(u.getTeacherCourse(), teacherCourse);
            teacherCourse.setCid(course.getId());
            teacherCourse.setTid(sysUser.getUserAttrId());
            teacherCourseService.save(teacherCourse);
            System.out.println(teacherCourse);
            TeacherCourse teacherCourses = teacherCourseService.selectId(course.getId(),sysUser.getUserAttrId());
            System.out.println(teacherCourses);
            System.out.println(teacherCourses.getId());
            System.out.println(teacherCourse.getId());
            ClbumCourse clbumCourse = new ClbumCourse();
            BeanUtils.copyProperties(u, clbumCourse);
            clbumCourse.setCid(clbum.getId());
            clbumCourse.setGid(gradesMap.get(u.getGrades().getName()).getId());
            clbumCourse.setTcid(teacherCourses.getId());
            clbumCourseService.save(clbumCourse);
        }
        /*String message = userService.importData(userList, updateSupport); */
        return Data.success();
    }
    /**
     * 删除班级课程
     */
    @RequiresPermissions("jiaowu:clbumcourse:remove")
    @Log(title = "班级课程", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(clbumCourseService.removeById(ids));
    }

    /**
     * 批量转换clbumCourseVo类型
     *
     * @param clbumCourses
     * @return
     */
    private List<ClbumCourseVo> trans(List<ClbumCourse> clbumCourses) {
        List<ClbumCourseVo> clbumCourseVos = new LinkedList<>();
        clbumCourses.forEach(cc -> clbumCourseVos.add(trans(cc)));
        return clbumCourseVos;
    }


    /**
     * 转换clbumCourseVo类型
     *
     * @param clbumCourse
     * @return
     */
    private ClbumCourseVo trans(ClbumCourse clbumCourse) {
        ClbumCourseVo clbumCourseVo = new ClbumCourseVo();
        BeanUtils.copyProperties(clbumCourse, clbumCourseVo);
        return clbumCourseVo;
    }

    /**
     * 批量转换teacherCourseVo类型
     *
     * @param teacherCourse
     * @return
     */
    private List<TeacherCourseVo> trans2(List<TeacherCourse> teacherCourse,Map<String, SysUser> sysUsers) {
        List<TeacherCourseVo> teacherCourseVos = new LinkedList<>();
        teacherCourse.forEach(tc -> teacherCourseVos.add(trans(tc,sysUsers.get(tc.getTid()))));
        return teacherCourseVos;
    }


    /**
     * 转换teacherCourseVo类型
     *
     * @param teacherCourse
     * @return
     */
    private TeacherCourseVo trans(TeacherCourse teacherCourse,SysUser sysUser) {
        TeacherCourseVo teacherCourseVo = new TeacherCourseVo();
        BeanUtils.copyProperties(teacherCourse, teacherCourseVo);
        teacherCourseVo.setSysUser(sysUser);
        return teacherCourseVo;
    }
    /**
     * 转换majorMap类型
     *
     * @param majors
     * @return
     */
    private Map<String, Major> transMajor(List<Major> majors) {
        Map<String, Major> majorMap = new HashMap<>(majors.size());
        majors.forEach(m -> majorMap.put(m.getName(), m));
        return majorMap;
    }
    /**
     * 转换SysUserMap类型
     *
     * @param sysUsers
     * @return
     */
    private Map<String, SysUser> transSysUser(List<SysUser> sysUsers) {
        Map<String, SysUser> sysUserMap = new HashMap<>(sysUsers.size());
        sysUsers.forEach(m -> sysUserMap.put(m.getUserName(), m));
        return sysUserMap;
    }
    /**
     * 转换teacherMap类型
     *
     * @param majors
     * @return
     */
    private Map<String, Teacher> transTeacher(List<Teacher> majors) {
        Map<String, Teacher> majorMap = new HashMap<>(majors.size());
        majors.forEach(m -> majorMap.put(m.getId(), m));
        return majorMap;
    }
    /**
     * 转换teacherMap类型
     *
     * @param teachers
     * @return
     */
    private Map<String, TeacherCourse> transTeacherCourse(List<TeacherCourse> teachers) {
        Map<String, TeacherCourse> teacherMap = new HashMap<>(teachers.size());
        teachers.forEach(m -> teacherMap.put(m.getTid(), m));
        return teacherMap;
    }

    /**
     * 转换clbumMap类型
     *
     * @param clbums
     * @return
     */
    private Map<String, Clbum> transClbum(List<Clbum> clbums) {
        Map<String, Clbum> majorMap = new HashMap<>(clbums.size());
        clbums.forEach(c -> majorMap.put(c.getName(), c));
        return majorMap;
    }
    /**
     * 转换courseMap类型
     *
     * @param courses
     * @return
     */
    private Map<String, Course> transCourse(List<Course> courses) {
        Map<String, Course> courseMap = new HashMap<>(courses.size());
        courses.forEach(c -> courseMap.put(c.getName(), c));
        return courseMap;
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

    /**
     * 转换departmentMap类型
     *
     * @param grades
     * @return
     */
    private Map<String, Grades> transGrades(List<Grades> grades) {
        Map<String, Grades> departmentMap = new HashMap<>(grades.size());
        grades.forEach(g -> departmentMap.put(g.getName(), g));
        return departmentMap;
    }

    /**
     * 获取班级信息
     *
     * @param clbumCourseVos
     */
    private void fillGrades(List<ClbumCourseVo> clbumCourseVos) {
        Map<String, Grades> gradesMap = gradesService.map(null);
        clbumCourseVos.forEach(c ->{
            c.setGrades(gradesMap.get(c.getGid()));
            c.setGradesname(c.getGrades().getName());
        });
    }

    /**
     * 获取班级信息
     *
     * @param clbumCourseVos
     */
    private void fillClbum(List<ClbumCourseVo> clbumCourseVos) {
        Map<String, Clbum> clbumMap = clbumService.map(null);
        clbumCourseVos.forEach(c -> c.setClbum(clbumMap.get(c.getCid())));
    }

    /**
     * 获取专业信息
     *
     * @param clbumCourseVos
     */
    private void fillMajor(List<ClbumCourseVo> clbumCourseVos) {
        Map<String, Major> majorMap = majorService.map(null);
        clbumCourseVos.forEach(c -> c.setMajor(majorMap.get(c.getClbum().getMid())));
    }

    /**
     * 获取系部信息
     *
     * @param clbumCourseVos
     */
    private void fillDepartment(List<ClbumCourseVo> clbumCourseVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        clbumCourseVos.forEach(c ->{
            c.setDepartment(departmentMap.get(c.getMajor().getDid()));
        });
    }

    /**
     * 获取教师课程信息
     *
     * @param clbumCourseVos
     */
    private void fillTeacherCourse(List<ClbumCourseVo> clbumCourseVos) {
        Map<String, Teacher> teacherMap = teacherService.map(null);
        Map<String, TeacherCourse> teacherCourseMap = teacherCourseService.map(null);
        Map<String, Course> courseMap = courseService.map(null);

        clbumCourseVos.forEach(c -> {
            c.setTeacherCourse(teacherCourseMap.get(c.getTcid()));
            c.setTeacher(teacherMap.get(c.getTeacherCourse().getTid()));
            c.setCourse(courseMap.get(c.getTeacherCourse().getCid()));
        });
    }

    /**
     * 获取教师信息
     *
     * @param teacherCourseVos
     */
    private void fillTeacher(List<TeacherCourseVo> teacherCourseVos) {
        Map<String, Teacher> teacherMap = teacherService.map(null);
        //Map<String, TeacherCourse> teacherCourseMap = teacherCourseService.map(null);
        Map<String, Course> courseMap = courseService.map(null);
        //Map<String, SysUser> sysUserMap = sysUserService.map(null);

        teacherCourseVos.forEach(c -> {
            c.setTeacher(teacherMap.get(c.getTid()));
            //c.setSysUser(sysUserService.);
            //c.setCourse(courseMap.get(c.getCid()));
        });
    }
    /**
     * 获取教师名称信息
     *
     * @param teacherCourseVos
     */
    private void fillSysuser(List<TeacherCourseVo> teacherCourseVos) {

    }
}
