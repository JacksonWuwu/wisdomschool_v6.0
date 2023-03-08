package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.UserConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.StringUtil;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.PageDomain;
import cn.wstom.common.web.page.TableDataInfo;
import cn.wstom.common.web.page.TableSupport;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 学生管理
 * @author dws
 * @date 2019/01/04
 */
@Controller
@RequestMapping("/jiaowu/student")
public class AdminStudentController extends BaseController {
    private String prefix = "/school/jiaowu/student";
    private  String grade;
    private String departmentname ;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private GradesService gradesService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ClbumService clbumService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MajorService majorService;

    //@Autowired
    //private StudentVoService studentVoService;

    //@Autowired
    //private SchoolService schoolService;


    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 获取学生列表
     *
     *
     * @return
     */
//    @RequiresPermissions("jiaowu:student:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list( @Param("loginName") String loginName,
                               @Param("userName") String userName,
                              @Param("gradesName") String gradesName,
                              @Param("departmentName") String departmentName,
                              @Param("clbumName") String clbumName,
                              @Param("majorName") String majorName) throws Exception {

        System.out.println("userName"+userName);
        System.out.println("loginName"+loginName);
        System.out.println("gradesName"+gradesName);
        System.out.println("departmentName"+departmentName);
        System.out.println("clbumName"+clbumName);
        System.out.println("majorName"+majorName);
        //grade= (String) WebUtils.getSession().getAttribute("gradename");
        StudentVo student=new StudentVo();
        student.setUserType(UserConstants.USER_STUDENT);
        student.setLoginName(loginName);
        student.setUserName(userName);

        if(grade !=null ){
            Grades grades = new Grades();
            grades.setName(grade);
            student.setGrades(grades);
        }
        if (null != gradesName && !gradesName.equals("")) {
            Grades grades = new Grades();
            grades.setName(gradesName);
            student.setGrades(grades);
        }
        //departmentname=(String) WebUtils.getSession().getAttribute("departmentname");
        if(departmentname !=null ){
            Department departments = new Department();
            departments.setName(departmentname);
            student.setDepartment(departments);
        }
        if (null != departmentName && !departmentName.equals("")) {
            Department department = new Department();
            department.setName(departmentName);
            student.setDepartment(department);
        }
        if (null != majorName && !majorName.equals("")) {
            Major major = new Major();
            major.setName(majorName);
            student.setMajor(major);
        }

        student.setSchoolId(getUser().getSchoolId());


        //System.out.println("pageDomain.getPageNum()"+pageDomain.getPageNum());
        //System.out.println("pageDomain.getOrderBy()"+pageDomain.getOrderBy());
        //System.out.println("pageDomain.getPageSize()"+pageDomain.getPageSize());
        //System.out.println(new PageInfo(studentVos).getTotal());
        PageDomain pageDomain = TableSupport.buildPageRequest();
        return studentService.selectByStudentVos(student, pageDomain.getPageNum(), pageDomain.getPageSize(), pageDomain.getOrderBy());
    }

    /**
     * 新增学生
     */
    @GetMapping("/add")
    public String toAdd(ModelMap modelMap) {
        List<Grades> grades = gradesService.list(new Grades());
        modelMap.put("grades", grades);
        return prefix + "/add";
    }

    /**
     * 保存新增学生
     */

    @Log(title = "学生用户管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data add(StudentVo studentVo) throws Exception {

        Student student = new Student();
        BeanUtils.copyProperties(studentVo.getStudent(), student);
        student.setCreateBy(getLoginName());
        student.setCid(studentVo.getClbum().getId());
        student.setGid(studentVo.getGrades().getId());
        studentService.saveStudent(student);
        List<Student> students = studentService.studentList(student);
        SysUser user = new SysUser();
        BeanUtils.copyProperties(studentVo, user);
        user.setSalt(randomSalt());
        user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setUserType(UserConstants.USER_STUDENT);
        user.setUserAttrId(students.get(0).getId());
        user.setCreateBy(getLoginName());
        user.setSchoolId(getUser().getSchoolId());
        user.setAvatar(Constants.STUDENT_DEFAULT_AVATAR);


        return toAjax(sysUserService.save(user));
    }

    /**
     * 修改学生页面
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap modelMap) {
        SysUser users = sysUserService.getById(id);

        Student student = studentService.getStudentById(users.getUserAttrId());
        StudentVo studentVo = trans(users, student);
        studentVo.setGrades(gradesService.getById(student.getGid()));
        studentVo.setClbum(clbumService.getById(student.getCid()));
        studentVo.setMajor(majorService.getById(studentVo.getClbum().getMid()));
        studentVo.setDepartment(departmentService.getById(studentVo.getMajor().getDid()));

        fillRole(studentVo);
        studentVo.setStudent(student);
        List<Grades> grades = gradesService.list(null);
        modelMap.put("grades", grades);
        modelMap.put("student", studentVo);

        return prefix + "/edit";
    }

    /**
     * 修改学生
     */

    @Log(title = "学生用户管理", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data edit(StudentVo studentVo) throws Exception {
        if (StringUtil.isNotNull(studentVo.getId()) && UserConstants.ADMIN_ID.equals(studentVo.getId())) {
            return error("不允许修改超级管理员用户");
        }
        SysUser oldUser = sysUserService.getById(studentVo.getId());
        SysUser user = new SysUser();
        BeanUtils.copyProperties(studentVo, user);
        user.setUserAttrId(null);
        user.setUserType(null);
        user.setUpdateBy(getLoginName());
        if (studentVo.getPassword() != null && !"".equals(studentVo.getPassword().trim())) {
            user.setSalt(randomSalt());
            user.setPassword(encryptPassword(oldUser.getLoginName(), studentVo.getPassword(), user.getSalt()));
        }

        Student student = new Student();
        BeanUtils.copyProperties(studentVo.getStudent(), student);
        student.setUpdateBy(getLoginName());
        student.setGid(studentVo.getGrades().getId());
        student.setCid(studentVo.getClbum().getId());
        studentService.updateStudent(student);
        return toAjax(sysUserService.update(user, true));
    }


    @GetMapping("/importTemplate")
    @ResponseBody
    public Data importTemplate() {
        try {
            ExcelUtil<StudentVo> util = new ExcelUtil<>(StudentVo.class);
            return util.importTemplateExcel("student");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }

    @Log(title = "用户管理", actionType = ActionType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public Data importData(MultipartFile file, boolean updateSupport) {
       try{
           ExcelUtil<StudentVo> util = new ExcelUtil<>(StudentVo.class);
           List<StudentVo> userList = util.importExcel(file.getInputStream());
           List<Major> majors = majorService.list(null);
           Map<String, Major> majorMap = transMajor(majors);
           List<Clbum> clbums = clbumService.list(null);
           Map<String, Clbum> clbumMap = transClbum(clbums);
           List<Department> departments = departmentService.list(null);
           Map<String, Department> departmentMap = transDepartment(departments);
           List<Grades> grades = gradesService.list(null);
           Map<String, Grades> gradesMap = transGrades(grades);
           StringBuilder errorMsg = new StringBuilder();
           for (StudentVo u : userList) {

               Major major = majorMap.get(u.getMajor().getName());

               Department department = departmentMap.get(u.getDepartment().getName());

               Clbum clbum = clbumMap.get(u.getClbum().getName());

               Grades grade = gradesMap.get(u.getGrades().getName());

               if (major == null && department == null && clbum == null && grade == null){

                   return Data.success();
               }

               if (department == null){
                   return Data.error("该系部不存在！");

               }

               if (major==null){
                   return Data.error("该专业不存在！");
               }

               if (grade == null){
                   return Data.error("该年级不存在！");
               }

               if (clbum == null){
                   return Data.error("该班级不存在！");
               }

               //校验数据
               if (!majorMap.get(u.getMajor().getName()).getDid().equals(departmentMap.get(u.getDepartment().getName()).getId())) {
                   errorMsg.append("系部【").append(u.getDepartment().getName()).append("】不存在专业【").append(u.getMajor().getName()).append("】");
                   return Data.error(errorMsg.toString());
               }

               if (!clbum.getMid().equals(majorMap.get(u.getMajor().getName()).getId())) {
                   errorMsg.append("专业【").append(u.getClbum().getName()).append("】不存在班级【").append(u.getClbum().getName()).append("】");
                   return Data.error(errorMsg.toString());
               }
               Student student = new Student();
               BeanUtils.copyProperties(u.getStudent(), student);
               student.setCid(clbum.getId());
               student.setGid(gradesMap.get(u.getGrades().getName()).getId());
               studentService.saveStudent(student);

               SysUser user = new SysUser();
               BeanUtils.copyProperties(u, user);
               user.setPassword("123456");
               System.out.println(user.getLoginName());
               user.setCreateBy(getLoginName());
               user.setUserType(UserConstants.USER_STUDENT);
               user.setUserAttrId(student.getId());
               user.setSalt(randomSalt());
               user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
               user.setAvatar(Constants.STUDENT_DEFAULT_AVATAR);
               user.setRoleIds(new String[]{"140"});//写死了学生角色信息
               sysUserService.save(user);
           }
           return Data.success();
       }catch (Exception e){
           return Data.error("导入文件格式错误");
        }
        /*String message = userService.importData(userList, updateSupport); */
    }


    /**
     * 删除用户
     *
     * @param ids
     * @return
     */

    @Log(title = "用户管理", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        String[] userIds = Convert.toStrArray(ids);
        List<String> idList = Arrays.asList(userIds);
        if (idList.contains(UserConstants.ADMIN_ID)) {
            throw new Exception("不允许删除超级管理员用户");
        }

        List<SysUser> users = sysUserService.listByIds(idList);
        List<String> sids = new ArrayList<>(users.size());
        users.forEach(u -> sids.add(u.getUserAttrId()));
        studentService.removeStudent(sids);
        return toAjax(sysUserService.removeByIds(idList));
    }

    /**
     * 批量转换studentVo类型
     *
     * @param users
     * @param students
     * @return
     */
    private List<StudentVo> trans(List<SysUser> users, Map<String, Student> students) {
        List<StudentVo> studentVos = new LinkedList<>();
        users.forEach(u -> studentVos.add(trans(u, students.get(u.getUserAttrId()))));
        return studentVos;
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
     * @param studentVos
     */
    private void fillClbum(List<StudentVo> studentVos) {
        Map<String, Clbum> clbumMap = clbumService.map(null);
        studentVos.forEach(s -> s.setClbum(clbumMap.get(s.getStudent().getCid())));
    }

    /**
     * 获取年级信息
     *
     * @param studentVos
     */
    private void fillGrades(List<StudentVo> studentVos) {
        Map<String, Grades> gradesMap = gradesService.map(null);
        studentVos.forEach(s -> s.setGrades(gradesMap.get(s.getStudent().getGid())));
    }

    /**
     * 获取专业信息
     *
     * @param studentVos
     */
    private void fillMajor(List<StudentVo> studentVos) {
        Map<String, Major> majorMap = majorService.map(null);
        studentVos.forEach(s -> s.setMajor(majorMap.get(s.getClbum().getMid())));
    }

    /**
     * 获取系部信息
     *
     * @param studentVos
     */
    private void fillDepartment(List<StudentVo> studentVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        studentVos.forEach(s -> s.setDepartment(departmentMap.get(s.getMajor().getDid())));
    }

    /**
     * 获取角色信息
     *
     * @param studentVos
     */
    private void fillRole(StudentVo studentVos) {
        List<SysRole> roles = studentVos.getRoles();
        List<String> roleIds = new ArrayList<>();
        roles.forEach(r -> roleIds.add(r.getId()));
        studentVos.setRoleIds(roleIds.toArray(new String[]{}));
    }
}
