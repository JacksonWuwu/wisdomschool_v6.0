package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.*;
import cn.wstom.student.service.StudentService;
import cn.wstom.student.service.TopicCommentService;
import cn.wstom.student.service.TopicService;
import cn.wstom.student.utils.AtomicIntegerUtil;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TopicCommentService commentService;
    @Autowired
    private TopicService topicService;

    @ApiOperation("学生个人信息")
    @RequestMapping(method = RequestMethod.GET, value = "/info")
    public Data info() {
        String studentId = getUser().getUserAttrId();
        Student student = studentService.getById(studentId);
        Clbum clbum = adminService.getClbumById(student.getCid());
        Major major = adminService.getMajorById(clbum.getMid());
        Department department = adminService.getDepartmentById(major.getDid());

        // department
        Map<String, Object> data = new HashMap<>();
        data.put("student", student);
        data.put("clbum", clbum);
        data.put("major", major);
        data.put("department", department);
        data.put("userId", getUserId());
        data.put("userName", getUser().getUserName());
        data.put("avatar", getUser().getAvatar());

        return Data.success(data);
    }
    @ApiOperation("教师个人信息")
    @RequestMapping(method = RequestMethod.GET, value = "/infoteacher")
    public Data infoTeacher() {
        String teacherId = getUser().getUserAttrId();
        Teacher teacher = adminService.getTeacherById(teacherId);
        Major major = adminService.getMajorById(teacher.getMid());
        Department department = adminService.getDepartmentById(major.getDid());

        // department
        Map<String, Object> data = new HashMap<>();
        data.put("teacher", teacher);
//        data.put("clbum", clbum);
        data.put("major", major);
        data.put("department", department);
        data.put("userId", getUserId());
        data.put("userName", getUser().getUserName());
        data.put("avatar", getUser().getAvatar());
//        System.out.println("teacher"+teacher);
        return Data.success(data);
    }
    //@ApiOperation("密码修改")
    //@RequestMapping(method = RequestMethod.POST, value = "/pwd")
    //public Data pwd(@RequestParam("old") String old, @RequestParam("new") String newPwd) {
    //    SysUser user = sysUserService.getById(getUserId());
    //    try {
    //        SysUser findUser = loginService.login(user.getLoginName(), old);
    //
    //        if (findUser == null) {
    //            return Data.error("输入原密码有误");
    //        }
    //        if (StringUtils.isEmpty(newPwd) || newPwd.length() < 6 || newPwd.length() > 25) {
    //            return Data.error("输入新密码有误");
    //        }
    //
    //        findUser.setSalt(randomSalt());
    //        findUser.setPassword(encryptPassword(findUser.getLoginName(), newPwd, findUser.getSalt()));
    //
    //        int result = sysUserService.updateUserPassword(findUser);
    //        if (result > 0) {
    //            //验证成功了，返回重置密码的页面
    //            return Data.success("密码修改成功");
    //        }
    //
    //        return Data.error("密码修改异常");
    //
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        return Data.error(e.getMessage());
    //    }
    //}

    @ApiOperation("个人评论")
    @RequestMapping(method = RequestMethod.GET, value = "/reply")
    public Data reply(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum) {
        TopicComment topicComment = new TopicComment();
        topicComment.setCreateBy(getUserId());
        Page page = loadNumData(pageNum);
        List<TopicComment> comments = commentService.list(topicComment);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", getUserId());
        data.put("comments", comments);
        data.put("pageSize", page.getPages());
        return Data.success(data);
    }

    @ApiOperation("个人话题")
    @RequestMapping(method = RequestMethod.GET, value = "/topic")
    public Data topic(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum) {
        Topic topic = new Topic();
        topic.setCreateBy(getUserId());
        startPage();//  分页
        List<Topic> topicList = topicService.list(topic);

        /*  Atomic __ */
        topicList.forEach( t -> {
            t.setBrowse((long) AtomicIntegerUtil.getInstance(t.getClass(), t.getId(), t.getBrowse()).get());
        });

        Map<String, Object> data = new HashMap<>();
        data.put("userId", getUserId());
        data.put("topics", topicList);

        return Data.success(data);
    }

    @ApiOperation("视频观看历史")
    @RequestMapping(method = RequestMethod.GET, value = "/video/history")
    public Data videoHistory(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum) {
        //  TODO: tb_user_video     (userId/videoChapterId/progross/lastTime)
        return Data.success();
    }
}
