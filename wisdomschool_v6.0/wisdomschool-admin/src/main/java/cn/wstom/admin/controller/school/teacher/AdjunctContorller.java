package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.StorageConstants;
import cn.wstom.common.utils.FileUtils;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;

@Slf4j
@Controller
@RequestMapping("/teacher/adjunct")
public class AdjunctContorller extends BaseController {

    private String prefix = "/school/teacher/adjunct";
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private AdjunctService adjunctService;
    @Autowired
    private RecourseTypeService recourseTypeService;
    @Autowired
    private RecourseService recourseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdjunctStudentService adjunctStudentService;




    @Value("${wstom.file.storage-path}")
    private String AdjunctURl ;

    @RequestMapping("/getAdjunctById/{adjunctId}")
    @ResponseBody
    Adjunct getAdjunctById(@PathVariable("adjunctId")String adjunctId){
        return adjunctService.getById(adjunctId);
    }

    @RequestMapping("/adjunctStudentList")
    @ResponseBody
    List<AdjunctStudent> adjunctStudentList(@RequestBody AdjunctStudent adjunctStudent){
        return adjunctStudentService.list(adjunctStudent);
    }

    @RequestMapping("/selectListByAdjunctStudent")
    @ResponseBody
    List<AdjunctStudent> selectListByAdjunctStudent(@RequestBody AdjunctStudent adjunctStudent){
        return adjunctStudentService.selectListByAdjunctStudent(adjunctStudent);
    }
    @RequestMapping("/updateByAidAndSid")
    @ResponseBody
    Boolean updateByAidAndSid (@RequestBody AdjunctStudent adjunctStudent){
        return adjunctStudentService.updateByAidAndSid(adjunctStudent);
    }


    @RequestMapping("/adjunctMap")
    @ResponseBody
    Map<String, Adjunct> adjunctMap(){
        return adjunctService.map(null);
    }

    @GetMapping("/{cid}")
    public String tolist(ModelMap modelMap, @PathVariable String cid) {
        TeacherCourse teacherCourse=teacherCourseService.selectId(cid,getUser().getUserAttrId());
        String  tcid =teacherCourse.getId();
        modelMap.put("tcid", tcid);
        return prefix+"/list";
    }


    @RequestMapping("/toManager/{cid}")
    public String adjunctManager(ModelMap modelMap,@PathVariable String cid){

        TeacherCourse teacherCourse=teacherCourseService.selectId(cid,getUser().getUserAttrId());
        String  tcid =teacherCourse.getId();
        modelMap.put("tcid", tcid);
        return prefix+"/fabu_list";

    }



    @PostMapping("/{tcid}/list")
    @ResponseBody
    public TableDataInfo list(@PathVariable String tcid,Adjunct adjunct) {
        adjunct.setTcid(tcid);
        startPage();
        List<Adjunct> list = adjunctService.list(adjunct);
        fillClbum(list);
        PageInfo<Adjunct> pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }


    @PostMapping("/{tcid}/fabulist")
    @ResponseBody
    public TableDataInfo fabuList(@PathVariable String tcid,Adjunct adjunct) {
        adjunct.setTcid(tcid);
        adjunct.setStates(1);
        startPage();
        List<Adjunct> list = adjunctService.list(adjunct);
        fillClbum(list);
        PageInfo<Adjunct> pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }

    @Log()
//    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/add")
    public String toadd(ModelMap modelMap, @PathVariable String tcid) {
        modelMap.put("tcid", tcid);
//        List<Clbum> list =clbumService.selectBytcid(tcid);
        TeacherCourse teacherCourse = teacherCourseService.getById(tcid);
        RecourseType recourseType = new RecourseType();
        recourseType.setTcId(teacherCourse.getId());
        List<RecourseType> recourseTypelist = recourseTypeService.list(recourseType);
        modelMap.put("type", recourseTypelist);
        modelMap.put("tcid", tcid);
//        modelMap.put("list", list);
        return prefix+"/add";
    }



    /**
     * 添加上机作业资源
     *
     * @param recourse 资源
     * @return 添加结果
     */
//    @RequiresPermissions("teacher:course:view")
    @PostMapping("/add")
    @ResponseBody
    public Data addVideoRecourse(Adjunct adjunct, Recourse recourse) throws Exception {
        recourse.setCreateBy(getLoginName());
//        if (recourseService.selectByRecourse(recourse)!=null){
//            return Data.error("添加失败");
//        }
        if (FileUtils.checkFileType(recourse.getFileName(), FileUtils.allowFile)) {
            recourse.setRecourseType(StorageConstants.RECOURSE_FILE);
            int rid=recourseService.addreturn(recourse);
            if (rid!= 0){
                String tcid=recourse.getTcId();
                adjunct.setRid(String.valueOf(rid));
                adjunct.setTcid(tcid);
                adjunct.setFilename(recourse.getFileName());
               int aid= adjunctService.addreturn(adjunct);
//               String url=AdjunctURl+"\\"+recourse.getTcId()+"\\"+aid;
//                String url=AdjunctURl+"\\"+tcid+"\\"+aid;
                String url=AdjunctURl+"\\"+aid;
                System.out.println("url---"+url);
                File file=new File(url);
                file.mkdir();
                if(file.exists()){
                    System.out.println("file生成---");
                }else {
                    return Data.error("生成文件夹失败，请联系统管理员");
                }
                return Data.success("添加成功");
            }else {
                return Data.error("添加失败");
            }
        }else {
            return Data.error("添加失败，请检查上传格式,支持");
        }
    }

//    @RequiresPermissions("teacher:attendance:view")
//    @PostMapping("/{tcid}/changeStatusadd")
//    @ResponseBody
//    public Data add(@PathVariable String tcid , String id,String cid) throws Exception {
//        System.out.println("id"+id);
//        System.out.println("cid"+cid);
//        Adjunct adjunct1=adjunctService.getById(id);
//        if (adjunct1.getStates()==1){
//            return Data.error("发布失败，请勿重复操作");
//        }else {
//            List<String> stulist =studentService.selectBycid(cid);
//            List<AdjunctStudent> adjunctStudentList=new ArrayList<>();
//            fillaid(stulist,adjunctStudentList,id);
//            System.out.println("adjunctStudentList---"+adjunctStudentList);
//            stulist.forEach(t ->{
//                WechatAccount wechatAccount = wechatAccountService.selectBySId(t);
//                if (wechatAccount!=null){
//                    AdjunctStudent adjunctStudent=new AdjunctStudent();
//                    adjunctStudent.setAdjid(id);
//                    adjunctStudent.setStuid(t);
//                    pushMessageService.adjunctStudentStatus(adjunctStudent);
//                }else {
//                    System.out.println("学号为"+t+"的学生未绑定微信公众号");
//                }
//
//            });
////            AdjunctStudent adjunctStudent=new AdjunctStudent();
////            adjunctStudent.setAdjid(id);
////            adjunctStudent.setStuid("3152");
////            adjunctStudent.setFilename("综合测试");
////            pushMessageService.adjunctStudentStatus(adjunctStudent);
//            Adjunct adjunct =new Adjunct();
//            adjunct.setId(id);
//            adjunct.setStates(1);
//            return toAjax(adjunctStudentService.saveBatch(adjunctStudentList)&&adjunctService.update(adjunct));
//        }
//
//    }


    //    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/edit/{id}")
    public String toedit(ModelMap modelMap, @PathVariable String id) {
        Adjunct adjunct=adjunctService.getById(id);
        modelMap.put("adjunct", adjunct);
        System.out.println("adjunct"+adjunct);
        return prefix+"/edit";
    }


//    @RequiresPermissions("teacher:attendance:view")
    @PostMapping("/edit")
    @ResponseBody
    public Data edit(Adjunct adjunct) throws Exception {
        System.out.println("edit--adjunct--"+adjunct);
        return toAjax(adjunctService.update(adjunct));
    }


    /**
     * 删除上机
     */
//    @RequiresPermissions("module:clbum:remove")
//    @Log(title = "班级", actionType = ActionType.DELETE)
    @PostMapping("{tcid}/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        adjunctStudentService.deleteByAid(ids);
        return toAjax(adjunctService.removeById(ids));
    }



    /*
    * 学生作业详细
    * */
//    @RequiresPermissions("teacher:attendance:view")
    @GetMapping("/{tcid}/detail/{id}")
    public String todetail(ModelMap modelMap, @PathVariable String tcid,@PathVariable String id) {
        modelMap.put("id", id);
        System.out.println("todetail"+id);
        return prefix+"/detail";
    }


    @GetMapping("/fabu")
    @RequiresPermissions("teacher:course:view")
    public String fabu(String id,String adjid,ModelMap modelMap){
        modelMap.put("id",id);
        System.out.println("fabu adjid:"+id);
//        TestPaperWorkList testPaper = testPaperOneListService.getById(id);
        Adjunct adjunct=adjunctService.getById(id);
        TeacherCourse teacherCourse=teacherCourseService.getById(adjunct.getTcid());
        System.out.println("fabu---teacherCourse======="+teacherCourse);
        modelMap.put("cid",teacherCourse.getCid());
        modelMap.put("tid", getUser().getUserAttrId());
//        modelMap.put("rule",testPaper.getRule());
        System.out.println("###################");
        return prefix + "/fabu";
    }

    @RequestMapping("/tofabu/{adjid}")
    public String tofabu(@PathVariable String adjid){
        System.out.println("xxxx");
        return prefix+"/fabu";
    }



//    @RequiresPermissions("teacher:course:view")
//    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/addfabu")
    @ResponseBody
    public Data addfabu(Adjunct adjunct) throws Exception {

        //重新发布
        if (adjunct.getClbums()!=null){
            String[] userId = adjunct.getClbums().split(",");//班级
            List<AdjunctStudent> list = new ArrayList<>();
            try {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i=0;i<userId.length;i++){
                    Clbum clbum1 = new Clbum();
                    clbum1.setName(userId[i]);
                    List<Clbum> list1 = clbumService.list(clbum1);
                    stringBuffer.append(list1.get(0).getName()+' ');
                }
                for (int i = 0; i < userId.length; i++) {
                    Clbum clbum2 = new Clbum();
                    clbum2.setName(userId[i]);
                    List<Clbum> list2 = clbumService.list(clbum2);
                    Student student = new Student();
                    student.setCid(list2.get(0).getId());
                    List<Student> studentList = studentService.studentList(student);
                    for (int j = 0; j <studentList.size() ; j++) {
                        AdjunctStudent adjunctStudent = new AdjunctStudent();
                        adjunctStudent.setAdjid(adjunct.getId());
                        adjunctStudent.setStuid(studentList.get(j).getId());
                        List<AdjunctStudent> adjunctStudentList = new ArrayList<AdjunctStudent>();
                        adjunctStudentList = adjunctStudentService.list(adjunctStudent);
                        if (adjunctStudentList.size() == 0) {
                            AdjunctStudent adjunctStudent1 = new AdjunctStudent();
                            adjunctStudent1.setCid(list2.get(0).getId());
                            adjunctStudent1.setAdjid(adjunct.getId());
                            adjunctStudent1.setStuid(studentList.get(j).getId());
                            list.add(adjunctStudent1);
                        }
                    }
                }
                return toAjax(adjunctStudentService.saveBatch(list));
            } catch (Exception e) {
                System.out.println("eee:"+e.getCause()+":"+e);
//            System.out.println("123:"+adjunct.getTestPaperWorkId());
                System.out.println("456:"+list);
                return error("记录已存在");
            }
        }else{
            //正常发布
            String[] userId = adjunct.getUserId().split(",");//班级
            adjunct.setCid(adjunct.getUserId());
            System.out.println("adjunct---getCid===="+adjunct.getCid() );
            adjunct.setStates(1);
            adjunctService.update(adjunct);
            List<AdjunctStudent> list = new ArrayList<>();
            try {
                StringBuffer stringBuffer = new StringBuffer();
                for (int i=0;i<userId.length;i++){
                    Clbum clbum = clbumService.getById(userId[i]);
                    stringBuffer.append(clbum.getName()+' ');
                }
                for (int i = 0; i < userId.length; i++) {
                    Clbum clbum = clbumService.getById(userId[i]);
                    Student student = new Student();
                    student.setCid(clbum.getId());
                    List<Student> studentList = studentService.studentList(student);
                    for (int j = 0; j <studentList.size() ; j++) {
                        AdjunctStudent adjunctStudent = new AdjunctStudent();
                        adjunctStudent.setAdjid(adjunct.getId());
                        adjunctStudent.setStuid(studentList.get(j).getId());
                        List<AdjunctStudent> adjunctStudentList = new ArrayList<AdjunctStudent>();
                        adjunctStudentList = adjunctStudentService.list(adjunctStudent);
                        if (adjunctStudentList.size() == 0) {
                            AdjunctStudent adjunctStudent1 = new AdjunctStudent();
                            adjunctStudent1.setCid(clbum.getId());
                            adjunctStudent1.setAdjid(adjunct.getId());
                            adjunctStudent1.setStuid(studentList.get(j).getId());
                            list.add(adjunctStudent1);
                        }
                    }
                }
                return toAjax(adjunctStudentService.saveBatch(list));
            } catch (Exception e) {
                System.out.println("eee:"+e.getCause()+":"+e);
//            System.out.println("123:"+adjunct.getTestPaperWorkId());
                System.out.println("456:"+list);
                return error("记录已存在");
            }
        }
    }

    @RequestMapping("/buend")
    @ResponseBody
    public Data buEnd(String id) throws Exception {
        String dealine="2199-01-27 00:00:00";
        Adjunct adjunct=new Adjunct();
        adjunct.setDeadline(dealine);
        adjunct.setId(id);
        adjunctService.update(adjunct);
        return success("设置成功");
    }



    private void fillaid(List<String> list,List<AdjunctStudent> adjunctStudentList,String aid) {
        list.forEach(t ->{
            AdjunctStudent adjunctStudent=new AdjunctStudent();
            adjunctStudent.setAdjid(aid);
            adjunctStudent.setStuid(t);
            adjunctStudentList.add(adjunctStudent);
        });
    }
    private void fillClbum(List<Adjunct> adjuncts) {
//        String[] userId = adjunct.getUserId().split(",");//班级
        Map<String, Clbum> clbumMap = clbumService.map(null);
        adjuncts.forEach(t -> {
            if (t.getCid()!=null){
                String[] cids = t.getCid().split(",");//班级
                if (cids!=null){
                    List<String> clbumNames =new ArrayList<>();
                    for (int i = 0; i < cids.length; i++) {
                        String clbumname=(clbumMap.get(cids[i])).getName();
                        System.out.println("班级名"+clbumname);
                        clbumNames.add(clbumname);
                    }
                    t.setClbumName(clbumNames);
                }
            }
        });

    }


//    @Log(title = "教师信息", actionType = ActionType.EXPORT)
//    @RequiresPermissions("jiaowu:teacher:export")
    @PostMapping("/{tcid}/export")
    @ResponseBody
    public Data export(@PathVariable String tcid) {
        System.out.println("export________adj");

        try {
        Adjunct adjunct=new Adjunct();
        adjunct.setTcid(tcid);
        List<AdjunctVo> adjunctVos=adjunctService.listAll(adjunct);
            System.out.println("adjunctVos"+adjunctVos);
            ExcelUtil<AdjunctVo> util = new ExcelUtil<>(AdjunctVo.class);
            return util.exportExcel(adjunctVos, "adjuct");
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }
}
