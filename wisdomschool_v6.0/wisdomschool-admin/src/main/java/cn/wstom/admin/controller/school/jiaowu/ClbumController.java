package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 班级 信息操作处理
 *
 * @author xyl
 * @date 2019-01-25
 */
@Controller
@RequestMapping("/jiaowu/clbum")
public class ClbumController extends BaseController {
    private String prefix = "school/jiaowu/clbum";
    private Integer department;
    private final ClbumService clbumService;

    private final DepartmentService departmentService;

    private final MajorService majorService;

    private final StudentService studentService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    public ClbumController(ClbumService clbumService, DepartmentService departmentService, MajorService majorService, StudentService studentService) {
        this.clbumService = clbumService;
        this.departmentService = departmentService;
        this.majorService = majorService;
        this.studentService = studentService;
    }


    @RequestMapping(value = "/{clbumId}")
    @ResponseBody
    public Clbum getClbumById(@PathVariable(value = "clbumId")String clbumId){
        return clbumService.getById(clbumId);
    }

    @RequestMapping(value = "/clbumList")
    @ResponseBody
    List<Clbum> clbumList (Clbum clbum){
        return clbumService.list(clbum);
    }

    @GetMapping()
    public String toList() {
        return prefix + "/list";
    }

    /**
     * 查询班级列表
     */
//    @RequiresPermissions("jiaowu:clbum:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ClbumVo clbum) {
        clbum.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        Object fid = clbum.getParams().get("fid");
        if (fid != null) {
            clbum.setMid(fid.toString());
        }
        department= (Integer) WebUtils.getSession().getAttribute("did");
        if(department !=null ){
            Major major1=new Major();
            major1.setDid(department.toString());
            Map<String ,Major> majorMap=new HashMap<>();

        }
        startPage();
        System.out.println(clbum);
        List<Clbum> list = clbumService.list(clbum);
        List<ClbumVo> clbumVos = trans(list);
        //填充系部信息
        fillMajor(clbumVos);
        fillDepartment(clbumVos);
        return wrapTable(clbumVos, new PageInfo<>(list).getTotal());
    }

    /**
     * 下拉框班级列表
     */
//    @RequiresPermissions("jiaowu:clbum:list")
    @PostMapping("/listpage")
    @ResponseBody
    public TableDataInfo listpage(ClbumVo clbum) {
        clbum.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        Object fid = clbum.getParams().get("fid");
        if (fid != null) {
            clbum.setMid(fid.toString());
        }
        List<Clbum> list = clbumService.list(clbum);
        List<ClbumVo> clbumVos = trans(list);
        //填充系部信息
        fillMajor(clbumVos);
        fillDepartment(clbumVos);
        return wrapTable(clbumVos, new PageInfo<>(list).getTotal());
    }


    /*
    * 根据tcid查询课程
    * */
//    @PostMapping("/tclistpage/{tcid}")
//    @ResponseBody
//    public TableDataInfo tclistpage(@PathVariable String tcid) {
//        System.out.println("clumb-tcid"+tcid);
//        List<Clbum> list =clbumService.selectBytcid(tcid);
//        List<ClbumVo> clbumVos = trans(list);
//        //填充系部信息
//        fillMajor(clbumVos);
//        fillDepartment(clbumVos);
//        return wrapTable(clbumVos, new PageInfo<>(list).getTotal());
//    }

    /**
     * 新增班级
     */
    @GetMapping("/add")
    public String toAdd(ModelMap mmap) {
        List<SysUser> users=null;
        users = sysUserService.selectByridTwo();
        mmap.put("list",users);
        return prefix + "/add";
    }

    /**
     * 新增保存班级
     */

    @Log(title = "班级", actionType = ActionType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public Data add(ClbumVo clbumVo) throws Exception {
        //保存班级信息
        Clbum clbum = new Clbum();
        clbum.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        BeanUtils.copyProperties(clbumVo, clbum);
        clbum.setMid(clbumVo.getMajor().getId());
        clbum.setTid(clbumVo.getTid());
        clbum.setCreateBy(getUser().getId());
        return toAjax(clbumService.save(clbum));
    }

    /**
     * 修改班级
     */
    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        Clbum clbum = clbumService.getById(id);
        ClbumVo clbumVo = trans(clbum);
        clbumVo.setMajor(majorService.getById(clbum.getMid()));
        clbumVo.setDepartment(departmentService.getById(clbumVo.getMajor().getDid()));
        mmap.put("clbum", clbumVo);
        List<SysUser> users=null;
        users = sysUserService.selectByridTwo();
        mmap.put("list",users);
        return prefix + "/edit";
    }

    /**
     * 修改保存班级
     */

    @Log(title = "班级", actionType = ActionType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(ClbumVo clbumVo) throws Exception {
        //保存班级信息
        Clbum clbum = new Clbum();
        clbum.setSchoolId(Integer.parseInt(getUser().getSchoolId()));
        BeanUtils.copyProperties(clbumVo, clbum);
        clbum.setMid(clbumVo.getMajor().getId());
        clbum.setUpdateBy(getUser().getId());
        clbum.setTid(clbumVo.getTid());
        System.out.println("clum"+clbum);
        return toAjax(clbumService.update(clbum));
    }

    /**
     * 删除班级
     */

    @Log(title = "班级", actionType = ActionType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {

        Student student = new Student();
        student.setCid(ids);
        List<Student> list = studentService.studentList(student);
        if (list != null && list.size() > 0) {
            return error("该班级存在学生用户，不允许删除！请检查");
        }
        return toAjax(clbumService.removeById(ids));
    }

    /**
     * 批量转换clbumVo类型
     *
     * @param clbums
     * @return
     */
    private List<ClbumVo> trans(List<Clbum> clbums) {
        List<ClbumVo> clbumVos = new LinkedList<>();
        clbums.forEach(c -> clbumVos.add(trans(c)));
        return clbumVos;
    }

    /**
     * 转换clbumVo类型
     *
     * @param clbum
     * @return
     */
    private ClbumVo trans(Clbum clbum) {
        ClbumVo clbumVo = new ClbumVo();
        BeanUtils.copyProperties(clbum, clbumVo);
        return clbumVo;
    }

    /**
     * 获取系部信息
     *
     * @param clbumVos
     */
    private void fillMajor(List<ClbumVo> clbumVos) {
        Map<String, Major> majorMap = majorService.map(null);
        clbumVos.forEach(c -> c.setMajor(majorMap.get(c.getMid())));
    }

    /**
     * 获取系部信息
     *
     * @param clbumVos
     */
    private void fillDepartment(List<ClbumVo> clbumVos) {
        Map<String, Department> departmentMap = departmentService.map(null);
        clbumVos.forEach(c -> c.setDepartment(departmentMap.get(c.getMajor().getDid())));
    }

    /**
     * 校验班级名称
     */
    @PostMapping("/checkClbumNameUnique")
    @ResponseBody
    public boolean checkClbumNameUnique(ClbumVo clbumVo) {
        Map<String, Object> parms = new HashMap<>(1);
        parms.put("name", clbumVo.getName());
        parms.put("mid",clbumVo.getMajor().getId());
        return clbumService.count(parms)<=0;
    }

    /*  Lin_    */

    @PostMapping("/export")
    @ResponseBody
    public Data export(Clbum clbum) {
        try {
            List<Clbum> list = clbumService.list(clbum);
            ExcelUtil<Clbum> util = new ExcelUtil<>(Clbum.class);
            return util.exportExcel(list, "clbum");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
