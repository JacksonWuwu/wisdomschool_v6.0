package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.PageDomain;
import cn.wstom.common.web.page.TableDataInfo;
import cn.wstom.common.web.page.TableSupport;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/teacher/study")
public class StudyController extends BaseController {


    private String prefix = "/school/teacher/study";

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private StudentService studentService;
    @GetMapping("/{cid}")
    public String tolist(ModelMap modelMap, @PathVariable String cid) {
        TeacherCourse teacherCourse=teacherCourseService.selectId(cid,getUser().getUserAttrId());
        String tcid =teacherCourse.getId();
        modelMap.put("tcid", tcid);
        return prefix+"/choose";
    }

    @RequestMapping({"/huaban"})
    public String huaban() {
        return prefix+"/huaban";
    }
    @RequestMapping({"/text"})
    public String text() {
        return prefix+"/text";
    }

    @RequestMapping({"/thinking"})
    public String thinking() {
        return prefix+"/thinking";
    }


    @RequestMapping({"/luck"})
    public String luck(ModelMap modelMap) {
        String cid="115";
        modelMap.put("courseid " ,cid);
        return prefix+"/luckydraw";
    }

    @RequestMapping({"/tochoosecourse/{tcid}"})
    public String tochoosecourse(ModelMap modelMap,@PathVariable String tcid) {
        List<Clbum> list =clbumService.selectBytcid(tcid);
        modelMap.put("list", list);
        return prefix+"/choosecourse";
    }

    /**
     * 获取学生信息
     * @param courseid
     * @return
     */
    @GetMapping("/drawform")
    @ResponseBody
    public List drawform(String courseid) throws Exception {
        System.out.println("进入随机抽点，课堂id---------" + courseid);
        List drawform=new ArrayList();
        StudentVo student=new StudentVo();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        TableDataInfo tableDataInfo = studentService.selectByStudentVos(student, pageDomain.getPageNum(), pageDomain.getPageSize(), pageDomain.getOrderBy());
        List<?> rows = tableDataInfo.getRows();
        List<StudentVo> studentVos = (List<StudentVo>) rows;
        // studentService.selectByStudentVos(student,pageDomain.getPageNum(),pageDomain.getPageSize(),pageDomain.getOrderBy());
        for (int i=0;i<=studentVos.size()-1;i++){
            List onestudentform=new ArrayList();
//            SysUser user=userService.findById(Integer.parseInt(studentidlist.get(i).toString()));
            SysUser user=sysUserService.selectUserByUserAttrId(studentVos.get(i).getId());
            onestudentform.add(studentVos.get(i).getUserName());
            onestudentform.add(studentVos.get(i).getLoginName());
            drawform.add(onestudentform);
        }
        return drawform;
    }

}
