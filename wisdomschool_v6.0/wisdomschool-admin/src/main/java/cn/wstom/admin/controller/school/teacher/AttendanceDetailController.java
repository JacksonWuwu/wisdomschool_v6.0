package cn.wstom.admin.controller.school.teacher;

import cn.wstom.common.base.Data;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/teacher/attendanceDetail")
public class AttendanceDetailController extends BaseController {
    private String prefix = "/school/teacher/attendance";

    @Autowired
    private AttendanceDetailService attendanceDetailService;

    @Autowired
    private AttendanceService attendanceService;

//    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/{aid}/list")
    @ResponseBody
    public TableDataInfo list(@PathVariable String aid) {
        System.out.println("aid"+aid);
        startPage();
        List<AttendanceDetail> list = attendanceDetailService.getByAid(aid);
        System.out.println("list"+list);
        PageInfo<Attendance> pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    //    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{aid}/edit/{id}")
    public String toedit(ModelMap modelMap, @PathVariable String id) {
        AttendanceDetail attendanceDetail=new AttendanceDetail();
        attendanceDetail.setId(id);
        AttendanceDetail attendanceDetail1=attendanceDetailService.getByattendanceDetail(attendanceDetail);
        modelMap.put("attendanceDetail", attendanceDetail1);
        System.out.println("attendanceDetail1"+attendanceDetail1);
        return prefix+"/detail_edit";
    }

    //    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(AttendanceDetail attendanceDetail) throws Exception {
        System.out.println("attendanceDetail"+attendanceDetail);
        return toAjax(attendanceDetailService.update(attendanceDetail));
    }

}
