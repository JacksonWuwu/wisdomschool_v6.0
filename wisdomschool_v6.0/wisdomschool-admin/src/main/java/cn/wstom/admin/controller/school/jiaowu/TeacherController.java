package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.constant.UserConstants;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.common.utils.StringUtil;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
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

@Controller
@RequestMapping("/jiaowu/teacher")
public class TeacherController extends BaseController {
    private String prefix = "school/jiaowu/teacher";

    private Integer department;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherCourseService teacherCourseService;


    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }


    @RequestMapping(value = "/{teacherId}")
    @ResponseBody
    Teacher getTeacherById(@PathVariable(value = "teacherId")String teacherId){
        return teacherService.getById(teacherId);
    }
    @RequestMapping(value = "/teacherMapByIds")
    @ResponseBody
    Map<String, Teacher> teacherMapByIds(List<String> tIds){
        return teacherService.mapByIds(tIds);
    }
    /**
     * 获取教师列表
     *
     * @param teacher
     * @return
     */
//    @RequiresPermissions("jiaowu:teacher:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TeacherVo teacher) {
        teacher.setUserType(UserConstants.USER_TEACHER);

        List<String> teaId = new LinkedList<>();
        List<SysUser> users=null;
        teacher.setSchoolId(getUser().getSchoolId());
        department= (Integer) WebUtils.getSession().getAttribute("did");
        if(department !=null ){
            startPage();
            List<Teacher> teachers=teacherService.mapByDeptids(department);
            teachers.forEach(teacher1 -> teaId.add(teacher1.getId()));
            users=sysUserService.selectByAttrids(teaId);
        }else {
            startPage();
            users = sysUserService.list(teacher);
            //获取教师属性id
            users.forEach(u -> teaId.add(u.getUserAttrId()));
        }
        List<TeacherVo> teacherVos = null;
        if (!teaId.isEmpty()) {
            Map<String, Teacher> teacherMap = teacherService.mapByIds(teaId);
            teacherVos = trans(users, teacherMap);
            //填充班级信息
            fillMajor(teacherVos);
            fillDepartment(teacherVos);
        }

        return wrapTable(teacherVos, new PageInfo(users).getTotal());
    }

    /**
     * 新增教师
     */
    @GetMapping("/add")
    public String toAdd() {
        return prefix + "/add";
    }





    /**
     * 保存新增教师
     */

    @Log(title = "教师用户管理", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data add(TeacherVo teacherVo) throws Exception {
        System.out.println("teacherVo"+teacherVo);
        //保存教师属性信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherVo.getTeacher(), teacher);
        teacher.setCreateBy(getLoginName());
//        teacher.setDept_id(teacherVo.getDepartmentid());
//        teacher.setMid(String.valueOf(teacherVo.getMajorid()));
        teacher.setDept_id(Integer.parseInt(teacherVo.getDepartment().getId()));
        teacher.setMid(teacherVo.getMajor().getId());
        teacherService.save(teacher);

        //保存教师课程信息
        String[] courseIds = teacherVo.getCourseIds();
        List<TeacherCourse> teacherCourses = new LinkedList<>();
        String tid = teacher.getId();
        System.out.println("tid"+tid);
        for (String cid : courseIds) {
            TeacherCourse tc = new TeacherCourse(tid, cid);
            tc.setThumbnailPath(Constants.BOOK_DEFAULT_AVATAR);
            teacherCourses.add(tc);
        }
        try {
            teacherCourseService.saveBatch(teacherCourses);
        } catch (Exception e) {
            return Data.error("分配教师课程异常!");
        }

        SysUser user = new SysUser();
        BeanUtils.copyProperties(teacherVo, user);
        System.out.println("user12"+user);
        user.setSalt(randomSalt());
        user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setUserType(UserConstants.USER_TEACHER);
        user.setUserAttrId(teacher.getId());
        user.setCreateBy(getLoginName());
        user.setAvatar(Constants.TEACHER_DEFAULT_AVATAR);
        user.setSchoolId(getUser().getSchoolId());
        return toAjax(sysUserService.save(user));
    }

    /**
     * 修改教师页面
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap modelMap) {
        SysUser users = sysUserService.getById(id);

        Teacher teacher = teacherService.getById(users.getUserAttrId());


        TeacherVo teacherVo = trans(users, teacher);

        teacherVo.setMajor(majorService.getById(teacher.getMid()));
        teacherVo.setDepartment(departmentService.getById(teacherVo.getMajor().getDid()));
        fillRole(teacherVo);
        List<SysRole> sysRoles = sysRoleService.selectAllRolesByUserId(id);
        String selected = "";
        for (SysRole sysRole : sysRoles) {
            if (!"student".equals(sysRole.getRoleKey())){
            selected=selected+sysRole.getId()+",";
            }
        }
        selected=selected.substring(0, selected.length()-1);
        //将课程编号转化为数组类型
        List<String> cidList = new LinkedList<>();
        teacher.getCourses().forEach(c -> cidList.add(c.getId()));
        String[] cids = cidList.toArray(new String[]{});
        teacherVo.setCourseIds(cids);
        teacherVo.setTeacher(teacher);
        System.out.println(teacherVo);
        modelMap.put("teacher", teacherVo);
        modelMap.put("selected", selected);
        return prefix + "/edit";
    }

    /**
     * 修改教师
     */

    @Log(title = "教师用户管理", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data edit(TeacherVo teacherVo) throws Exception {
        if (StringUtil.isNotNull(teacherVo.getId()) && UserConstants.ADMIN_ID.equals(teacherVo.getId())) {
            return error("不允许修改超级管理员用户");
        }
        SysUser oldUser = sysUserService.getById(teacherVo.getId());
        SysUser user = new SysUser();
        BeanUtils.copyProperties(teacherVo, user);
        user.setUserAttrId(null);
        user.setUserType(null);
        user.setPassword(null);
        user.setSalt(null);
        user.setUpdateBy(getLoginName());
        if (teacherVo.getPassword() != null && !"".equals(teacherVo.getPassword().trim())) {
            user.setSalt(randomSalt());
            user.setPassword(encryptPassword(oldUser.getLoginName(), teacherVo.getPassword(), user.getSalt()));
        }

        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherVo.getTeacher(), teacher);
        teacher.setUpdateBy(getLoginName());

//        //保存教师课程信息
//        String[] courseIds = teacherVo.getCourseIds();
//        List<Course> courses = new LinkedList<>();
//        if (courseIds != null)
//            for (String cid : courseIds) {
//                courses.add(new Course(cid));
//            }
//        teacher.setCourses(courses);

        teacherService.update(teacher);


        return toAjax(sysUserService.update(user, true));
    }


    @GetMapping("/importTemplate")
    @ResponseBody
    public Data importTemplate() {
        try {
            ExcelUtil<TeacherVo> util = new ExcelUtil<>(TeacherVo.class);
            return util.importTemplateExcel("teacher");
        } catch (Exception e) {
            e.printStackTrace();
            return Data.error(e.getMessage());
        }
    }

    @Log(title = "用户管理", actionType = ActionType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public Data importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<TeacherVo> util = new ExcelUtil<>(TeacherVo.class);
        List<TeacherVo> userList = util.importExcel(file.getInputStream());

        List<Major> majors = majorService.list(null);
        Map<String, Major> majorMap = transMajor(majors);
        List<Department> departments = departmentService.list(null);
        Map<String, Department> departmentMap = transDepartment(departments);


        StringBuilder errorMsg = new StringBuilder();


        for (TeacherVo u : userList) {

            //校验数据
            if (!majorMap.get(u.getMajor().getName()).getDid().equals(departmentMap.get(u.getDepartment().getName()).getId())) {
                errorMsg.append("系部【").append(u.getDepartment().getName()).append("】不存在专业【").append(u.getMajor().getName()).append("】");
                return Data.error(errorMsg.toString());
            }
            Teacher teacher = new Teacher();
            BeanUtils.copyProperties(u.getTeacher(), teacher);
            teacher.setMid(majorMap.get(u.getMajor().getName()).getId());
            teacher.setCreateBy(getLoginName());
            teacher.setOnJobStatus(UserConstants.TEACHER_ON_JOB);
            teacherService.save(teacher);

            SysUser user = new SysUser();
            BeanUtils.copyProperties(u, user);
            user.setPassword("123456");
            user.setCreateBy(getLoginName());
            user.setUserType(UserConstants.USER_TEACHER);
            user.setUserAttrId(teacher.getId());
            user.setSalt(randomSalt());
            user.setPassword(encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
            user.setAvatar(Constants.TEACHER_DEFAULT_AVATAR);
            user.setRoleIds(new String[]{"139"});//写死了教师角色
            sysUserService.save(user);
        }
        return Data.success();
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
        try {
            String[] userIds = Convert.toStrArray(ids);
            List<String> idList = Arrays.asList(userIds);
            if (idList.contains(UserConstants.ADMIN_ID)) {
                throw new Exception("不允许删除超级管理员用户");
            }

            List<SysUser> users = sysUserService.listByIds(idList);
            System.out.println(users);

            List<String> sids = new ArrayList<>(users.size());
            users.forEach(u -> sids.add(u.getUserAttrId()));
            System.out.println(sids);

            teacherService.removeByIds(sids);
            return toAjax(sysUserService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @Log(title = "教师信息", actionType = ActionType.EXPORT)

    @PostMapping("/export")
    @ResponseBody
    public Data export(TeacherVo teacher) {
        teacher.setUserType(UserConstants.USER_TEACHER);
        List<String> teaId = new LinkedList<>();
        List<SysUser> users=null;
        department= (Integer) WebUtils.getSession().getAttribute("did");
        if(department !=null ){
            List<Teacher> teachers=teacherService.mapByDeptids(department);
            teachers.forEach(teacher1 -> teaId.add(teacher1.getId()));
            users=sysUserService.selectByAttrids(teaId);
        }else {
            users = sysUserService.list(teacher);
            //获取教师属性id
            users.forEach(u -> teaId.add(u.getUserAttrId()));
        }
        try {
            List<TeacherVo> teacherVos = null;
            if (!teaId.isEmpty()) {
                Map<String, Teacher> teacherMap = teacherService.mapByIds(teaId);
                teacherVos = trans(users, teacherMap);
                //填充班级信息
                fillMajor(teacherVos);
                fillDepartment(teacherVos);
            }
//            int listSize = teacherVos.size();
//            System.out.println("listSize"+listSize);
            ExcelUtil<TeacherVo> util = new ExcelUtil<>(TeacherVo.class);
            return util.exportExcel(teacherVos, "teacher");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }


    /*+——————————————————————————————————+
      |            教师课程操作           |
      |                                  |
      +——————————————————————————————————+
     */

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
     * 获取专业信息
     *
     * @param teacherVos
     */
    private void fillMajor(List<TeacherVo> teacherVos) {
        Map<String, Major> majorMap = majorService.map(null);
        teacherVos.forEach(t -> t.setMajor(majorMap.get(t.getTeacher().getMid())));
    }

    /**
     * 获取系部信息
     *
     * @param teacherVos
     */
    private void fillDepartment(List<TeacherVo> teacherVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        teacherVos.forEach(t -> t.setDepartment(departmentMap.get(t.getMajor().getDid())));
    }

    /**
     * 获取角色信息
     * @param teacherVos
     */
    private void fillRole(TeacherVo teacherVos) {
        List<SysRole> roles = teacherVos.getRoles();
        List<String> roleIds = new ArrayList<>();
        roles.forEach(r -> roleIds.add(r.getId()));
        teacherVos.setRoleIds(roleIds.toArray(new String[]{}));
    }
}
