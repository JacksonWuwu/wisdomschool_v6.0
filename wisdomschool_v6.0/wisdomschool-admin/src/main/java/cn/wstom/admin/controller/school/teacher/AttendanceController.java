package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.base.Data;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/teacher/attendance")
public class AttendanceController extends BaseController {
    private String prefix = "/school/teacher/attendance";
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private AttendanceVoService attendanceVoService;
    @Autowired
    private AttendanceDetailService attendanceDetailService;
    @Autowired
    private StudentService studentService;
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.path}")
    private String penetratePath;

    @RequestMapping("/attendanceVolist")
    @ResponseBody
    List<AttendanceVo> attendanceVolist(@RequestBody AttendanceVo attendanceVo){
        return attendanceVoService.list(attendanceVo);
    }



    @GetMapping("/{cid}")
    public String tolist(ModelMap modelMap, @PathVariable String cid) {
        System.out.println("list");
        System.out.println("cid"+ cid);
        TeacherCourse teacherCourse=teacherCourseService.selectId(cid,getUser().getUserAttrId());
        String tcid =teacherCourse.getId();
        System.out.println("tcid"+ tcid);
        modelMap.put("tcid", tcid);
        return prefix+"/list";
    }


    @PostMapping("/{tcid}/list")
    @ResponseBody
    public TableDataInfo list(@PathVariable String tcid) {
//        System.out.println("list_tcid"+tcid);
        Attendance attendance =new Attendance();
        attendance.setTcid(Integer.valueOf(tcid));
        startPage();
        List<Attendance> list = attendanceService.list(attendance);
        fillClbum(list);
//        System.out.println("list??????"+list);
        PageInfo<Attendance> pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/add")
    public String toadd(ModelMap modelMap, @PathVariable String tcid) {
        modelMap.put("tcid", tcid);
        System.out.println("clumb-tcid"+tcid);
        List<Clbum> list =clbumService.selectBytcid(tcid);
        modelMap.put("list", list);
        return prefix+"/add";
    }


//    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/{tcid}/add")
    @ResponseBody
    public Data add(@PathVariable String tcid,Attendance attendance) throws Exception {
        attendance.setCreateTime(new Date());
        attendance.setState(0);
        String numbercode=NewNumbercode();
        String qrcode=NewQrcode();
        attendance.setQrcode(qrcode);
        attendance.setPassword(Integer.valueOf(numbercode));
        int aid=attendanceService.addreturn(attendance);
        List<String> stulist =studentService.selectBycid(attendance.getCid().toString());
        List<AttendanceDetail> attendanceDetails=new ArrayList<>();
        fillaid(stulist,attendanceDetails,aid,tcid);
        System.out.println("attendanceDetails?????????"+attendanceDetails);
        return toAjax(attendanceDetailService.saveBatch(attendanceDetails));

    }



//    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/edit/{id}")
    public String toedit(ModelMap modelMap, @PathVariable String id, @PathVariable String tcid) {
        Attendance attendance=attendanceService.getById(id);
        Map<String, Clbum> clbumMap = clbumService.map(null);
        attendance.setClbum(clbumMap.get(attendance.getCid().toString()));
        modelMap.put("attendance", attendance);
        System.out.println("attendance"+attendance);
        List<Clbum> list =clbumService.selectBytcid(tcid);
        modelMap.put("list", list);
        return prefix+"/edit";
    }

//    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Attendance attendance) throws Exception {
        System.out.println("attendance"+attendance);
        return toAjax(attendanceService.update(attendance));
    }


    /**
     * ????????????
     */
//    @RequiresPermissions("module:clbum:remove")
//    @Log(title = "??????", actionType = ActionType.DELETE)
    @PostMapping("{cid}/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(attendanceService.removeById(ids));
    }


    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/detail/{id}")
    public String todetail(ModelMap modelMap, @PathVariable String tcid,@PathVariable String id) {
        modelMap.put("id", id);
        System.out.println("todetail"+id);
        return prefix+"/detail";
    }


    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/{tcid}/changeStatusStart")
    @ResponseBody
    public Data Start(@PathVariable String tcid , String id,String cid,String status) throws Exception {

//        System.out.println("attendanceDetails?????????"+attendanceDetails);
        Attendance attendance =new Attendance();
        attendance.setId(id);
        attendance.setState(Integer.valueOf(status));
        return toAjax(attendanceService.update(attendance));
    }


    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/{tcid}/changeStatusStop")
    @ResponseBody
    public Data Stop(@PathVariable String tcid , String id,String status) throws Exception {
        Attendance attendance =new Attendance();
        attendance.setId(id);
        attendance.setState(Integer.valueOf(status));
        return toAjax(attendanceService.update(attendance));

    }

    /**
     * ????????????????????????
     * @param
     * @return
     */
    @GetMapping("/startsuziattendance")
    @ResponseBody
    public List startsuziattendance(Integer attendanceid) {
        Attendance attendanceEntity=attendanceService.getById(attendanceid.toString());
        AttendanceDetail attendanceDetail=new AttendanceDetail();
        attendanceDetail.setAid(attendanceid);
        List<AttendanceDetail> attendanceDetailList=attendanceDetailService.list(attendanceDetail);
        Integer studentsum=attendanceDetailList.size();
        //??????numbercode
        String numbercode=attendanceEntity.getPassword().toString();
        List startsuzilist = new ArrayList();
        startsuzilist.add(studentsum);
        startsuzilist.add(numbercode);
        return startsuzilist;
    }



    /**
     * ???????????????????????????
     * @param
     * @return
     */
    @GetMapping("/updateanumbercode")
    @ResponseBody
    public String updateanumbercode(Integer attendanceid) throws Exception {
        Attendance attendanceEntity=attendanceService.getById(attendanceid.toString());
        String numbercode=NewNumbercode();
        attendanceEntity.setPassword(Integer.valueOf(numbercode));
        attendanceService.update(attendanceEntity);
        return numbercode;
    }


    /**
     * ????????????????????????modal????????????
     * @param
     * @return
     */
    @GetMapping("/updatesuziokstuonce")
    @ResponseBody
    public Integer updatesuziokstuonce(Integer attendanceid) {
        System.out.println("attendanceid"+attendanceid);
       AttendanceDetail attendanceDetail=new AttendanceDetail();
       attendanceDetail.setResults(1);
       attendanceDetail.setAid(attendanceid);
       List<AttendanceDetail> attendanceDetails= attendanceDetailService.list(attendanceDetail);
       Integer okstudentsum=attendanceDetails.size();
       System.out.println("okstudentsum"+okstudentsum);
       return okstudentsum;
    }

    /**
     * ?????????????????????????????????
     * @param
     * @return
     */
    @GetMapping("/updateyesstudent")
    @ResponseBody
    public Integer updateyesstudent(String attendanceid) {
        System.out.println("attendanceid"+attendanceid);
        AttendanceDetail attendanceDetail=new AttendanceDetail();
        attendanceDetail.setResults(1);
        attendanceDetail.setAid(Integer.valueOf(attendanceid));
        List<AttendanceDetail> attendanceDetails= attendanceDetailService.list(attendanceDetail);
        Integer yesstudenmtsum=attendanceDetails.size();
        return yesstudenmtsum;
    }

    /**
     * ???????????????????????????
     * @param
     * @return
     */
    @GetMapping("/startqrcodeattendance")
    @ResponseBody
    public List startqrcodeattendance(Integer attendanceid) {
        Attendance attendanceEntity=attendanceService.getById(attendanceid.toString());
        AttendanceDetail attendanceDetail=new AttendanceDetail();
        attendanceDetail.setAid(attendanceid);
        List<AttendanceDetail> attendanceDetailList=attendanceDetailService.list(attendanceDetail);
        Integer studentsum=attendanceDetailList.size();
        //??????code
        String qrcode=attendanceEntity.getQrcode();

        //??????
        String appid= appId;
//        String returnurl=penetratePath+"/wx/account/authorize?returnUrl="+penetratePath+"/wx/attendance/whetherattendance?qrcode="+qrcode+"."+attendanceid;
//        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo#wechat_redirect";
//        url=url.replace("APPID",appid).replace("REDIRECT_URI",returnurl);
//        String url=androidurl+"/attendance/whetherattendance?qrcode="+qrcode+"&&attendanceid="+attendanceid;
//        System.out.println("url"+url);


        List startqrcodelist = new ArrayList();
        startqrcodelist.add(studentsum);
//        startqrcodelist.add(url);
        startqrcodelist.add(qrcode+"-"+attendanceid);
        return startqrcodelist;
    }

    /**
     * ?????????????????????
     * @param
     * @return
     */
    @GetMapping("/updateqrcodeonce")
    @ResponseBody
    public String UpdateQrcodeonce(Integer attendanceid) throws Exception {
        Attendance attendanceEntity=attendanceService.getById(attendanceid.toString());;
        String qrcode=NewQrcode();
        attendanceEntity.setQrcode(qrcode);
        attendanceService.update(attendanceEntity);
        //??????
//        String appid=appId;
//        String returnurl=penetratePath+"/wx/account/authorize?returnUrl="+penetratePath+"/wx/attendance/whetherattendance?qrcode="+qrcode+"%26attendanceid="+attendanceid;
//        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo#wechat_redirect";
//        url=url.replace("APPID",appid).replace("REDIRECT_URI",returnurl);
//        System.out.println("????????????code"+qrcode);
        String qa=qrcode+"-"+attendanceid;
        return qa;
    }


    private void fillaid(List<String> list,List<AttendanceDetail> detailList,int aid,String tcid) {
        list.forEach(t ->{
            AttendanceDetail attendanceDetail=new AttendanceDetail();
            attendanceDetail.setAid(aid);
            attendanceDetail.setSid(Integer.valueOf(t));
            attendanceDetail.setTcid(Integer.valueOf(tcid));
            detailList.add(attendanceDetail);
        });
    }


    /**
     * ????????????????????????????????????????????????
     *
     */
    public String NewNumbercode(){
        int n=0;
        String docode;
        List oldcode=attendanceService.findAllnumbercode();
        do {
            n=0;
            docode=DoNumberCode();
            for (int i=0;i<=oldcode.size()-1;i++){
                if (!docode.equals(oldcode.get(i).toString())){
                    n++;
                }
            }
        }while (n!=oldcode.size());
        System.out.println("n?????????"+n);
        System.out.println("Li?????????"+oldcode.size());
        String newcode=docode;
        System.out.println("?????????????????????"+newcode);
        return newcode;
    }
    /**
     * ??????????????????????????????
     *
     */
    public String DoNumberCode(){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//????????????????????????????????????
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<4;i++)
        {
            int index=rand.nextInt(1);
            //            //????????????????????????????????????????????????
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//???????????????0~9
                    sb.append(data);
                    break;
            }
        }
        String result=sb.toString();
        System.out.println("????????????"+result);
        return result;
    }

    /**
     * ????????????????????????????????????????????????
     *
     */
    public String NewQrcode(){
        int n=0;
        String docode;
        List oldcode=attendanceService.findAllqrcode();
        do {
            n=0;
            docode=DoCode();
            for (int i=0;i<=oldcode.size()-1;i++){
                if (!docode.equals(oldcode.get(i).toString())){
                    n++;
                }
            }
        }while (n!=oldcode.size());
//        System.out.println("n?????????"+n);
//        System.out.println("Li?????????"+oldcode.size());
        String newcode=docode;
        System.out.println("?????????????????????"+newcode);
        return newcode;
    }
    /**
     * ???????????????????????????
     *
     */
    public String DoCode(){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//????????????????????????????????????
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<6;i++)
        {
            int index=rand.nextInt(2);
            //            //????????????????????????????????????????????????
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//???????????????0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+97;//??????????????????97~122?????????????????????
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        System.out.println("????????????"+result);
        return result;
    }

    private void fillClbum(List<Attendance> attendances) {
        Map<String, Clbum> clbumMap = clbumService.map(null);
        attendances.forEach(t -> {
            t.setClbum(clbumMap.get(t.getCid().toString()));
//            System.out.println("clbumMap?????????"+clbumMap.get(t.getCid().toString()));
//            System.out.println("t?????????"+t);
        });
    }

}
