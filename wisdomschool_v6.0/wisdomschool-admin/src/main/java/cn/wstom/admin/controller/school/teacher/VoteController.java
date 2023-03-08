package cn.wstom.admin.controller.school.teacher;

import cn.wstom.admin.controller.BaseController;

import cn.wstom.admin.event.WebUtils;
import cn.wstom.admin.feign.StudentService;
import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;

import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/teacher/vote")
public class VoteController extends BaseController {
    private String prefix = "/school/teacher/vote";


    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private ClbumService clbumService;
    private VoteAnswer voteAnswer;
    @Autowired
    private StudentService studentService;
    /***
     * 登录ID
     */
    String loginid;
    /**
     * SysUser
     */
    SysUser sysUser;
    /**
     * 用户ID
     */
    String uid;
    /**
     * 跳转添加页面
     *
     * @return
     */


    /**
     * 获取列表页面
     *
     * @return
     */
//
    @GetMapping("/{cid}")
    public String toList(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        String tid = sysUserService.getById(getUserId()).getUserAttrId();
        modelMap.put("tid", tid);
        TeacherCourse teacherCourse=teacherCourseService.selectId(cid,getUser().getUserAttrId());
        String  tcid =teacherCourse.getId();
        modelMap.put("tcid", tcid);
        System.out.println("toList---vote");
        return prefix + "/list";
    }


    /**
     * 题库页面
     *
     * @param vote
     * @return
     */
//
    @PostMapping("/list/{cid}")
    @ResponseBody
    public TableDataInfo list(@PathVariable String cid, Vote vote) {
        vote.setXzsubjectsId(cid);
        vote.setCreateId(getUser().getUserAttrId());
        startPage();
        List<Vote> list = studentService.VoteList(vote);
        return wrapTable(list);
    }

    //
    @GetMapping("/add/{cid}")
    public String toAdd(@PathVariable String cid, ModelMap modelMap) {
        modelMap.put("id", cid);
        return prefix + "/add";
    }

//
    @PostMapping("/add")
    @ResponseBody
    public Data addVote(Vote vote) throws Exception {
        System.out.println("vote的值"+vote);
        loginid = getUserId();
        sysUser = sysUserService.getById(loginid);
        uid = sysUser.getUserAttrId();
        String[] optionanswersArr2 = null;
        String[] optionanswerArr1 = null;
        String trimstr = "option=";
        String myoaStr = "";
        String optionanswers = vote.getMyoptionAnswerArr();
        List<MyOptionAnswer> myoalist = new ArrayList<MyOptionAnswer>();
        optionanswersArr2 = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr2按;进行分割，这里分割成4个
        for (int i = 0; i < optionanswersArr2.length; i++) {
            optionanswerArr1 = optionanswersArr2[i].substring(0, optionanswersArr2[i].length() - 1).split(":");//按：进行分割
            String optionone = "";
            if (optionanswerArr1[0].contains(trimstr)) {
//                System.out.println(optionanswerArr1[0]);
                optionone = optionanswerArr1[0].substring(7);// 去掉option=开头字符串
            } else {
                optionone = optionanswerArr1[0];
            }
            String answerone = optionanswerArr1[1];
            try {
                String optionstr = java.net.URLDecoder.decode(optionone, "UTF-8");
                System.out.println("optionstr的值是"+optionstr);
                voteAnswer = new VoteAnswer();
                Uuid answerId = new Uuid();
                voteAnswer.setCreateBy(getUser().getLoginName());
                voteAnswer.setCreateId(uid);
                voteAnswer.setId(answerId.UId());
                voteAnswer.setStoption(optionstr);
                voteAnswer.setStanswer(answerone);
                System.out.println("voteAnswer的值是"+voteAnswer);
                toAjax(studentService.saveVoteAnswer(voteAnswer));
                myoaStr += voteAnswer.getId() + ";";

            } catch (Exception e) {
                System.out.println("乱码解决错误：" + e);
            }
        }
        vote.setCreateBy(getUser().getLoginName());
        vote.setCreateId(uid);
        vote.setMyoptionAnswerArr(myoaStr);
        vote.setOptionSize(optionanswersArr2.length);
        studentService.saveVote(vote);
//        return toAjax(voteService.save(vote));
        return Data.success();
    }

    /**
     * 编辑
     *
     * @param id
     * @param mmap
     * @return
     */

    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") String id, ModelMap mmap) {
        mmap.put("myQuestions", studentService.getVoteById(id));
        return prefix + "/edit";
    }

    @Log(title = "修改题目", actionType = ActionType.INSERT)
//
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public Data addUpdate(Vote vote) throws Exception {

        String[] optionanswersArr;
        String[] optionanswerArr;
        String optionAnswerIDList = "";
        String trimstr = "option=";
        String myoaStr = "";
        String optionanswers = vote.getMyoptionAnswerArrContent();
        List<VoteAnswer> myoalist = new ArrayList<VoteAnswer>();
        optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");//optionanswersArr按;进行分割，这里分割成4个
        myoaStr = vote.getMyoptionAnswerArr();
        if (myoaStr != null && !myoaStr.equals("")) {
            String[] myoaArr = myoaStr.substring(0, myoaStr.length() - 1).split(";");
            for (int i = 0; i < myoaArr.length; i++) {
                if (studentService.getVoteAnswerById(myoaArr[i]) == null) {
                } else {
                    myoalist.add(studentService.getVoteAnswerById(myoaArr[i]));
                }
            }
        }

        if (optionanswers != null && !optionanswers.equals("")) {
            optionanswersArr = optionanswers.substring(0, optionanswers.length() - 1).split(";");
            System.out.println("optionanswers"+optionanswers);
            for (int i = 0; i < optionanswersArr.length; i++) {
                optionanswerArr = optionanswersArr[i].substring(0,
                        optionanswersArr[i].length() - 1).split(":");
                String optionone = "";
                if (optionanswerArr[0].contains(trimstr)) {
                    optionone = optionanswerArr[0].substring(7);// 去掉option=开头字符串
                } else {
                    optionone = optionanswerArr[0];
                }
                try {
                    String optionstr = java.net.URLDecoder.decode(optionone,
                            "UTF-8");
                    String answerone = optionanswerArr[1];
                    if (myoalist.size() != 0 && !myoalist.isEmpty()) {
                        if (i < myoalist.size()) {
                            voteAnswer = myoalist.get(i);
                            voteAnswer.setUpdateBy(getUser().getLoginName());
                            voteAnswer.setUpdateId(getUser().getUserAttrId());
                            voteAnswer.setStoption(optionstr);
                            voteAnswer.setStanswer(answerone);
                            System.out.println("%%%%%%%%%%%%%%%%%");
                            studentService.updateVoteAnswer(voteAnswer);
                            System.out.println("*********************");
                        } else {
                            VoteAnswer optionAnswer = new VoteAnswer();
                            Uuid answerId = new Uuid();
                            optionAnswer.setCreateBy(getUser().getLoginName());
                            optionAnswer.setCreateId(uid);
                            optionAnswer.setId(answerId.UId());
                            optionAnswer.setStoption(optionstr);
                            optionAnswer.setStanswer(answerone);
                            studentService.saveVoteAnswer(optionAnswer);
                            myoaStr += optionAnswer.getId() + ";";
                        }
                    }
                } catch (Exception e) {
                    return error("更新出错:" + e.getCause() + ";" + e.getMessage());
                }
            }
        }
        vote.setMyoptionAnswerArr(myoaStr);
        vote.setCreateId(getUser().getUserAttrId());
        vote.setCreateBy(getUser().getLoginName());
        return toAjax(studentService.updateVote(vote));

    }



    /**
     * 查找答案
     * @param getoptionAnswerArr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestionsAns")
    public VoteAnswer getoptionAnswerArr(String getoptionAnswerArr) {
        String answerId = getoptionAnswerArr;
        VoteAnswer myoa =new VoteAnswer();
        myoa = studentService.getVoteAnswerById(answerId);
        System.out.println("stans" + myoa.getStanswer());
        System.out.println("option" + myoa.getStoption());
        return myoa;
    }


    /**
     * 答案数组
     * @param getoptionAnswerArr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getQuestionsAnsList")
    public List<String> getQuestionsAnsList(String getoptionAnswerArr) {
        String myoastr = getoptionAnswerArr;
        List<String> questionsAnsList = new ArrayList<String>();
        if (myoastr != null && !myoastr.equals("")) {
            String[] myoaArr = myoastr.substring(0, myoastr.length() - 1).split(";");
            for (int i = 0; i < myoaArr.length; i++) {
//                System.out.println(voteAnswerService.getById( myoaArr[i]).getStoption()+"????????");
//                System.out.println("dsad");
                questionsAnsList.add(studentService.getVoteAnswerById( myoaArr[i]).getStoption());
            }
        }
        return questionsAnsList;
    }


    @GetMapping("/fabu/{tcid}")
    public String fabu(String id,ModelMap modelMap,@PathVariable String tcid){
        modelMap.put("id",id);
//
        TeacherCourse teacherCourse=teacherCourseService.getById(tcid);
//
        modelMap.put("cid",teacherCourse.getCid());
        modelMap.put("tid", getUser().getUserAttrId());
//        System.out.println("###################");
        return prefix + "/fabu";
    }

    @GetMapping("/luck/{tcid}")
    public String luck(String id,ModelMap modelMap,@PathVariable String tcid){
//        modelMap.put("id",id);
//        System.out.println("fabu adjid:"+id);
//        Vote vote=voteService.getById(id);
        TeacherCourse teacherCourse=teacherCourseService.getById(tcid);
//        System.out.println("fabu---teacherCourse======="+teacherCourse);
        modelMap.put("cid",teacherCourse.getCid());
        modelMap.put("tid", getUser().getUserAttrId());
        return prefix + "/luckfabu";
    }


    @GetMapping("/addluckfabu/{tcid}")
    public String addluckfabu(String userId,ModelMap modelMap){
//        System.out.println("进入随机抽点123---------" + userId);
        WebUtils.getSession().setAttribute("luckuserId",userId);
        return prefix+"/luckydraw";
    }


    /**
     * 获取学生信息
     * @return
     */
    @GetMapping("/drawform")
    @ResponseBody
    public List drawform() {
        String clbums=  WebUtils.getSession().getAttribute("luckuserId").toString();
        System.out.println("进入随机抽点，课堂id---------" + clbums);
        String[] userId = clbums.split(",");
        List<StudentVo> studentVos=new ArrayList<>();
        for (int i = 0; i < userId.length; i++) {
            Clbum clbum = clbumService.getById(userId[i]);
            Student student = new Student();
            student.setCid(clbum.getId());
            List<Student> studentList = studentService.studentList(student);
            System.out.println("studentList"+studentList);
            for (int j = 0; j <studentList.size() ; j++) {
                StudentVo studentVo = new StudentVo();
                studentVo.setStudent(studentList.get(j));
//                List<StudentVo> studentVos2=studentVoService.selectByStudentVos(studentVo);
                StudentVo studentVo1=studentService.getStudentVoById(studentList.get(j).getId());
                System.out.println("studentVos123"+studentVo1.getUserName());
                studentVos.add(studentVo1);
            }
        }

//        StudentVo student=new StudentVo();
//        List<StudentVo> studentVos = studentVoService.selectByStudentVos(student);
        List drawform=new ArrayList();
        for (int i=0;i<=studentVos.size()-1;i++){
            List onestudentform=new ArrayList();
            onestudentform.add(studentVos.get(i).getUserName());
            onestudentform.add(studentVos.get(i).getLoginName());
            drawform.add(onestudentform);
        }
        return drawform;
    }



    //
    @Log(title = "测试关联", actionType = ActionType.INSERT)
    @PostMapping("/addfabu")
    @ResponseBody
    public Data addfabu(Vote vote) throws Exception {
        String[] userId = vote.getUserId().split(",");//班级
        vote.setCid(vote.getUserId());
        System.out.println("adjunct---getCid===="+vote.getCid() );
        vote.setStstatus(1);
        studentService.updateVote(vote);
        List<VoteOptionSubmit> list = new ArrayList<>();
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
                System.out.println(studentList);
                for (int j = 0; j <studentList.size() ; j++) {
//                    SysUser sysUser = sysUserService.selectUserByUserAttrId(studentList.get(j).getId());
                    VoteOptionSubmit voteOptionSubmit = new VoteOptionSubmit();
                    voteOptionSubmit.setVoteId(Integer.valueOf(vote.getId()));
//                    adjunctStudent.setUserId(sysUser.getId());
                    List<VoteOptionSubmit> voteOptionSubmits = new ArrayList<VoteOptionSubmit>();
                    voteOptionSubmits = studentService.listbyVoteOptionSubmit(voteOptionSubmit);
                    if (voteOptionSubmits.size() == 0) {
                        VoteOptionSubmit voteOptionSubmit1 = new VoteOptionSubmit();
                        voteOptionSubmit1.setVoteId(Integer.valueOf(vote.getId()));
                        voteOptionSubmit1.setStuid(studentList.get(j).getId());
                        list.add(voteOptionSubmit1);
                    }
                }
            }
            return toAjax(studentService.savevoteOptionSubmitsBatch(list));
        } catch (Exception e) {
            System.out.println("eee:"+e.getCause()+":"+e);
            System.out.println("456:"+list);
            return error("记录已存在");
        }
    }






    @GetMapping("/detail/{tcid}")
    public String detail(String id,ModelMap modelMap,@PathVariable String tcid){
        modelMap.put("id",id);
        System.out.println("detail:"+id);
        TeacherCourse teacherCourse=teacherCourseService.getById(tcid);
        System.out.println("fabu---teacherCourse======="+teacherCourse);
        modelMap.put("cid",teacherCourse.getCid());
        modelMap.put("tid", getUser().getUserAttrId());
        System.out.println("###################");
        return prefix + "/detail";
    }


    @GetMapping("/detailadate/{voteid}")
    @ResponseBody
    public  List<VoteDetailData> detaildate(@PathVariable String voteid ,ModelMap model){
        String data[]=new String[]{"A","B","C","D","E","F","G"};
        Vote vote=studentService.getVoteById(voteid);
        List<VoteDetailData> vlist=new ArrayList<>();
        if (vote!=null){
            int size= vote.getOptionSize();
            for (int i=0;i<size;i++){
                VoteOptionSubmit voteOptionSubmit=new VoteOptionSubmit();
                voteOptionSubmit.setStuAnswerVo(data[i]);
                voteOptionSubmit.setVoteId(Integer.valueOf(voteid));
                List<VoteOptionSubmit> listbyVoteOptionSubmit= studentService.listbyVoteOptionSubmit(voteOptionSubmit);
                int value= listbyVoteOptionSubmit.size();
                VoteDetailData voteDetailData=new VoteDetailData(data[i],value);
                vlist.add(voteDetailData);
            }
            System.out.println(""+vlist);
        }
        return vlist;
    }

    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids) throws Exception {
        return toAjax(studentService.removeVotes(ids));
    }


    public class Uuid {
        public String UId() {

            //注意replaceAll前面的是正则表达式
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            return uuid;
        }
    }

}
