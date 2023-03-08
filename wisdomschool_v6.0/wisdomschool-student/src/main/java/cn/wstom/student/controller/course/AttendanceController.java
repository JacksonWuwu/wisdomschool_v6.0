package cn.wstom.student.controller.course;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.Attendance;
import cn.wstom.student.entity.AttendanceDetail;
import cn.wstom.student.service.AttendanceDetailService;
import cn.wstom.student.service.AttendanceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceDetailService attendanceDetailService;




    /**
     *
     * 课程列表获取
     * @return
     */
    @ApiOperation("列表")
    @GetMapping(value = "/list")
    public Data list(@RequestParam(required = false, defaultValue = "0", value = "tcid") String tcid) {
        String studentId = getUser().getUserAttrId();
        AttendanceDetail detail=new AttendanceDetail();
        detail.setSid(Integer.valueOf(studentId));
        detail.setResults(0);
        List<Attendance> attendanceList=new ArrayList<>();
        List<AttendanceDetail> attendanceDetails=attendanceDetailService.list(detail);
        attendanceDetails.forEach(a->{
           Attendance attendance1 =attendanceService.getById((a.getAid()).toString());
           if (attendance1.getType()==0){
                attendanceList.add(attendance1);
            }
        });
        System.out.println("====voteList--List===="+attendanceList);
        Map<String, Object> map = new HashMap<>();
        return Data.success(attendanceList);
    }

    @ApiOperation("提交")
    @RequestMapping(method = RequestMethod.POST, value = "/submit")
    @ResponseBody
    public Data saveshuzi(@RequestBody Attendance attendance) throws Exception {
        System.out.println("attendance=="+attendance);
        Attendance attendance1=attendanceService.getById(attendance.getId());
        if (attendance1.getPassword().equals(attendance.getPassword())){
            AttendanceDetail detail=new AttendanceDetail();
            detail.setAid(Integer.valueOf(attendance.getId()));
            detail.setSid(Integer.valueOf(getUser().getUserAttrId()));
            detail.setResults(1);
            return toAjax(attendanceDetailService.updateByAidAndSid(detail));
        }
        return Data.error();
    }
    @ApiOperation("二维码提交")
    @RequestMapping(method = RequestMethod.POST, value = "/erweimaSubmit")
    @ResponseBody
    public Data saveerweima(@RequestBody String qrcode) throws Exception {
        System.out.println("qrcode=="+qrcode);
        String qa = qrcode;
        String qrcode2=qa.substring(0, qa.indexOf("-"));
        String id=qa.substring(qrcode2.length()+1, qa.length());
        Attendance attendance1=attendanceService.getById(id);

        if (attendance1.getQrcode().equals(qrcode2)){
            AttendanceDetail detail=new AttendanceDetail();
            detail.setAid(Integer.valueOf(id));
            detail.setSid(Integer.valueOf(getUser().getUserAttrId()));
            detail.setResults(1);
            return toAjax(attendanceDetailService.updateByAidAndSid(detail));
        }
        return Data.error();
    }
}
