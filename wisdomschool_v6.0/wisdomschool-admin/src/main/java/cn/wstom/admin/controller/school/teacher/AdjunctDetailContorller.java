package cn.wstom.admin.controller.school.teacher;


import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.constant.Constants;
import cn.wstom.common.enums.ActionType;

import cn.wstom.admin.entity.*;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.service.*;
import cn.wstom.admin.utils.*;
import cn.wstom.common.web.page.TableDataInfo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/teacher/adjunctdetail")
public class AdjunctDetailContorller extends BaseController {
    private String prefix = "/school/teacher/attendanceDetail";
    @Value("${wstom.file.studentadjust-url}")
    private String studentadjust_url;
    @Autowired
    private AdjunctStudentService adjunctStudentService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AdjunctService adjunctService;
    @Autowired
    private StudentService studentService;
    @Value("${wstom.file.storage-path}")
    private String AdjunctURl ;
    @Autowired
    private ClbumService clbumService;
    @Value("${wstom.storageContextPath}")
    private String STORAGE_URL;


    //删除上机预览缓存
    @RequestMapping("/clear")
    @ResponseBody
    public Data clear() {
        String tempPath= ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/htmlTemp";//缓存目录
        return toAjax(WordUtil.deleteDir(new File(tempPath)));
    }

    //导出上机记录
    @Log(title = "学生成绩信息", actionType = ActionType.EXPORT)
    @RequestMapping ("/{id}/exportAdjunct")
    @ResponseBody
    public Data exportAdjunct(@PathVariable("id") String id) {
        try{
            Adjunct adjunct = adjunctService.getById(id);
            List<AdjunctStudentVo> adjunctStudentVos= new LinkedList<>();
            List<AdjunctStudent> adjunctStudents = adjunctStudentService.selectListByaid(id);
            for (AdjunctStudent student : adjunctStudents) {
                AdjunctStudentVo adjunctStudentVo = new AdjunctStudentVo();
                adjunctStudentVo.setAdjName(adjunct.getAdjunctname());
                adjunctStudentVo.setUserName(student.getUserName());
                Clbum clbumName = clbumService.getById(student.getCid());
                adjunctStudentVo.setClbumName(clbumName.getName());
                adjunctStudentVo.setFilename(student.getFilename());
                adjunctStudentVo.setLoginName(student.getLoginName());
                adjunctStudentVo.setSubmitline(student.getSubmitline());
                if (student.getStates() == 1) {
                    adjunctStudentVo.setStates("是");
                    } else {
                    adjunctStudentVo.setStates("否");
                   }

                adjunctStudentVos.add(adjunctStudentVo);
             }
            System.out.println(adjunctStudentVos);
            ExcelUtil<AdjunctStudentVo> util = new ExcelUtil<>(AdjunctStudentVo.class);
            return util.exportExcel(adjunctStudentVos, adjunct.getAdjunctname());
        } catch (Exception e) {
            return Data.error(e.getMessage());
        }
    }




    @PostMapping("/{id}/list")
    @ResponseBody
    public TableDataInfo list(@PathVariable String id,AdjunctStudent adjunctStudent) {
        adjunctStudent.setAdjid(id);
        System.out.println("adjunctStudent=="+adjunctStudent);
        startPage();
        List<AdjunctStudent> list =adjunctStudentService.selectListByAdjunctStudent(adjunctStudent);
        fillClbum(list);
        System.out.println("list"+list);
        PageInfo<AdjunctStudent> pageInfo = new PageInfo(list);
        return wrapTable(list, pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages());
    }


    @RequestMapping("/viewAdjunct/{id}")
    public String viewAdjunct(@PathVariable String id, ModelMap modelMap) throws UnsupportedEncodingException {
        AdjunctStudent adjunctStudent=adjunctStudentService.getById(id);
        Student student = studentService.getStudentById(adjunctStudent.getStuid());
        SysUser sysUser = sysUserService.selectUserByUserAttrId(student.getId());
        String fileName = adjunctStudent.getFilename();
        int pos = fileName.lastIndexOf(Constants.SEPARATOR_DOT);
        Clbum clbum= clbumService.getById(adjunctStudent.getCid());
        String aid=adjunctStudent.getAdjid();
        String wordName = sysUser.getLoginName()+"_" + clbum.getName() + "." + fileName.substring(pos + 1);
        String HtmlName = sysUser.getLoginName()+"_" + clbum.getName() + ".html";
        String tempPath= ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/htmlTemp/";//缓存目录
        //查找文件
        File dir = new File(AdjunctURl+"\\"+aid);
        String[] files = dir.list();
        assert files != null;
        for (String f : files) {
            if(wordName.substring(0,12).equals(f.substring(0,12)))
            {
                HtmlName=f.substring(0, f.lastIndexOf("."))+".html";
                wordName=f;
                System.out.println(wordName);
                break;
            }
        }
        //如果位zip文件就执行下载
        String suffix = wordName.substring(wordName.lastIndexOf("."));
        if (!".doc".equals(suffix) && !".docx".equals(suffix)){
            System.out.println(wordName.substring(wordName.lastIndexOf(".")));
            wordName = URLEncoder.encode(wordName, "utf-8");
            return "redirect:" + STORAGE_URL + "/downloadFileTwo?Filename="+aid+"/"+wordName;
        }
        //如果缓存目录不存在就创建
        File folder = new File(tempPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String htmlPath=tempPath+HtmlName;//转出来的html路径
        String wordPath=AdjunctURl+"\\"+aid+"\\"+wordName;//文档路径
        if (!(new File(htmlPath).exists())){ //如果没有该html文件
                Boolean b = WordUtil.WordToHtml(wordPath, htmlPath);
                if (b){
                    modelMap.put("path", "htmlTemp/"+HtmlName);
                }
            }else {//如果有该html文件
               modelMap.put("path", "htmlTemp/"+HtmlName);
        }
        return "common/recourse/manager/demo";
    }



    @RequestMapping("/downloadAll/{adjid}")
    @ResponseBody
    public String redirectall(@PathVariable String adjid) throws FileNotFoundException {
        System.out.println("==========downloadALLtwo========"+adjid);
        Adjunct adjunct=adjunctService.getById(adjid);
        String Filename=adjunct.getAdjunctname();
        String url=AdjunctURl+"\\"+adjid;
        List<AdjunctStudent> adjunctStudents = adjunctStudentService.selectListByaid(adjid);
        for (AdjunctStudent adjunctStudent : adjunctStudents) {
            Clbum clbum = clbumService.getById(adjunctStudent.getCid());
            String oldName=adjunctStudent.getLoginName()+"_"+clbum.getName();
            String newName=oldName+"_"+adjunctStudent.getResults();
            System.out.println(oldName);
            System.out.println(newName);
            ZipUtils.changeFileName(url,oldName,newName);
        }
        String outurl=AdjunctURl+"\\"+Filename+".zip";
        FileOutputStream fos1 = new FileOutputStream(outurl);
        ZipUtils.toZip2(url,fos1,true);
        try {
            Filename=Filename+".zip";
            Filename = URLEncoder.encode(Filename, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Filename;
    }
    private void fillClbum(List<AdjunctStudent> adjuncts) {
//        String[] userId = adjunct.getUserId().split(",");//班级
        Map<String, Clbum> clbumMap = clbumService.map(null);
        adjuncts.forEach(t -> {
            if (t.getCid()!=null) {
                t.setClbum(clbumMap.get(t.getCid()));
            }
        });
//        Map<String, Clbum> clbumMap = clbumService.map(null);
//        adjuncts.forEach(t -> {
//            if (t.getCid()!=null){
//                String[] cids = t.getCid().split(",");//班级
//                if (cids!=null){
//                    List<String> clbumNames =new ArrayList<>();
//                    for (int i = 0; i < cids.length; i++) {
//                        String clbumname=(clbumMap.get(cids[i])).getName();
//                        System.out.println("班级名"+clbumname);
//                        clbumNames.add(clbumname);
//                    }
//                    t.setClbumName(clbumNames);
//                }
//            }
//        });
    }

    /**
     * 查所有班级
     *
     * @param adjid
     * @return
     */

    @RequestMapping("/findClbum")
    @ResponseBody
    public List<Clbum> findTitleType(String adjid) {
        List<Clbum> allSem = new ArrayList<Clbum>();
        Adjunct adjunct=adjunctService.getById(adjid);
        String cids= adjunct.getCid();
        String[] userId = cids.split(",");//班级
        for (int i=0;i<userId.length;i++) {
            Clbum clbum = clbumService.getById(userId[i]);
            allSem.add(clbum);
        }
        System.out.println("allSem"+allSem);
        return allSem;
    }

    //
    //@RequestMapping("/evaluate/{id}")
    //public String toevaluate(@PathVariable String id, ModelMap modelMap) {
    //    AdjunctStudent adjunctStudent=adjunctStudentService.getById(id);
    //     String results=adjunctStudent.getResults();
    //        modelMap.put("id",id);
    //        modelMap.put("results",results);
    //    return "/school/teacher/adjunct/evaluate";
    //}
//    adjunctdetailedit
    @PostMapping("/adjunctdetailedit")
    @ResponseBody
    public Data adjunctdetailedit(AdjunctStudent adjunctStudent) throws Exception {
        System.out.println("adjunctdetailedit"+adjunctStudent);
        return toAjax(adjunctStudentService.update(adjunctStudent));
    }


}
