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
//        System.out.println("list的值"+list);
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
        System.out.println("attendanceDetails的值是"+attendanceDetails);
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
     * 删除班级
     */
//    @RequiresPermissions("module:clbum:remove")
//    @Log(title = "班级", actionType = ActionType.DELETE)
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

//        System.out.println("attendanceDetails的值是"+attendanceDetails);
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
     * 开始数字考勤页面
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
        //获取numbercode
        String numbercode=attendanceEntity.getPassword().toString();
        List startsuzilist = new ArrayList();
        startsuzilist.add(studentsum);
        startsuzilist.add(numbercode);
        return startsuzilist;
    }



    /**
     * 更新一次数字考勤码
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
     * 更新一次数字考勤modal签到人数
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
     * 更新二维码考勤详情页面
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
     * 开始二维码考勤页面
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
        //获取code
        String qrcode=attendanceEntity.getQrcode();

        //地址
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
     * 更新一次二维码
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
        //地址
//        String appid=appId;
//        String returnurl=penetratePath+"/wx/account/authorize?returnUrl="+penetratePath+"/wx/attendance/whetherattendance?qrcode="+qrcode+"%26attendanceid="+attendanceid;
//        String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo#wechat_redirect";
//        url=url.replace("APPID",appid).replace("REDIRECT_URI",returnurl);
//        System.out.println("地址上的code"+qrcode);
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
     * 生成与数据库不相同的随机数字编号
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
        System.out.println("n的长度"+n);
        System.out.println("Li的长度"+oldcode.size());
        String newcode=docode;
        System.out.println("最后生成的编码"+newcode);
        return newcode;
    }
    /**
     * 生成新的数字考勤编号
     *
     */
    public String DoNumberCode(){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<4;i++)
        {
            int index=rand.nextInt(1);
            //            //目的是随机选择生成数字，大写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
            }
        }
        String result=sb.toString();
        System.out.println("生成的数"+result);
        return result;
    }

    /**
     * 生成与数据库不相同随机二维码编号
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
//        System.out.println("n的长度"+n);
//        System.out.println("Li的长度"+oldcode.size());
        String newcode=docode;
        System.out.println("最后生成的编码"+newcode);
        return newcode;
    }
    /**
     * 生成随机二维码编号
     *
     */
    public String DoCode(){
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<6;i++)
        {
            int index=rand.nextInt(2);
            //            //目的是随机选择生成数字，大写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的小写字母
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        System.out.println("生成的数"+result);
        return result;
    }

    private void fillClbum(List<Attendance> attendances) {
        Map<String, Clbum> clbumMap = clbumService.map(null);
        attendances.forEach(t -> {
            t.setClbum(clbumMap.get(t.getCid().toString()));
//            System.out.println("clbumMap的值是"+clbumMap.get(t.getCid().toString()));
//            System.out.println("t的值是"+t);
        });
    }

}
