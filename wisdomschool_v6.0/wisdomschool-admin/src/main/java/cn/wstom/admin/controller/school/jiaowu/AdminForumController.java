package cn.wstom.admin.controller.school.jiaowu;


import cn.wstom.common.annotation.Log;
import cn.wstom.common.base.Data;
import cn.wstom.common.enums.ActionType;
import cn.wstom.common.support.Convert;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author liniec
 * @date 2020/01/20 14:58
 *  admin论坛管理
 */
@Controller
@RequestMapping("/jiaowu/forum")
public class AdminForumController extends BaseController {

    @Autowired
    private TeacherCourseService teacherCourseService;
    @Autowired
    private ForumService forumService;
    @Autowired
    private TopicService topicService;


    /**
     * 论坛区的管理
     */

    /**
     * 查询列表
     * @param modelMap
     * @param request
     * @return
     */
   // @ApiOperation("系统级论坛管理")
    @GetMapping("/toList")
    @RequiresPermissions("jiaowu:comment:view")
    public String toForum(ModelMap modelMap, HttpServletRequest request) {
        String cid = ServletRequestUtils.getStringParameter(request, "cid", "");
        if (!"".equals(cid)) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("cid", cid);
            params.put("tid", getUser().getUserAttrId());
            TeacherCourse tc = teacherCourseService.getOne(params);
            modelMap.put("tcid", tc.getId());
            modelMap.put("id", cid);
        }
        return "/school/jiaowu/forum/list";
    }

    /**
     * 添加评论区
     * @param modelMap
     * @param request
     * @return
     */
    @GetMapping("/add")
    @RequiresPermissions("jiaowu:comment:view")
    public String toForumAdd(ModelMap modelMap, HttpServletRequest request) {
        String cid = ServletRequestUtils.getStringParameter(request, "cid", "");
        if (!"".equals(cid)) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("cid", cid);
            params.put("tid", getUser().getUserAttrId());
            TeacherCourse tc = teacherCourseService.getOne(params);
            modelMap.put("tcid", tc.getId());
        }
        return "/school/jiaowu/forum/add";
    }

    @PostMapping("/list")
    @RequiresPermissions("jiaowu:comment:view")
    @ResponseBody
    public TableDataInfo forumList(HttpServletRequest request, ModelMap modelMap) {
        String cid = ServletRequestUtils.getStringParameter(request, "cid", "");
        Forum forum = new Forum();
        if (!"".equals(cid)) {
            Map<String, Object> params = new HashMap<>(2);
            params.put("cid", cid);
            params.put("tid", getUser().getUserAttrId());
            TeacherCourse tc = teacherCourseService.getOne(params);
            modelMap.put("tcid", tc.getId());
            forum.setTcid(tc.getId());
        }
        forum.setCreateBy(getUser().getId());
        startPage();
        List<Forum> list = forumService.list(forum);
        return wrapTable(list);
    }

    @PostMapping("/add")
    @RequiresPermissions("jiaowu:comment:view")
    @ResponseBody
    public Data forumAdd(HttpServletRequest request, Forum forum) throws Exception {
        forum.setCreateBy(getUser().getId());
        if (forum.getTcid() == null || "".equals(forum.getTcid())) {
            forum.setTcid(null);
        }
        return toAjax(forumService.save(forum));
    }

    @PostMapping("/remove")
    @RequiresPermissions("jiaowu:comment:view")
    @Log(title = "节的试卷", actionType = ActionType.DELETE)
    @ResponseBody
    public Data forumMove(String ids) throws Exception {
        return toAjax(forumService.removeById(ids));
    }

    /**
     * 用户话题的管理
     */
 //   @ApiOperation("话题管理")
    @RequiresPermissions("jiaowu:comment:view")
    @GetMapping("/topic/list")
    public String list() {
        return "/school/jiaowu/topic/list";
    }

    @RequiresPermissions("jiaowu:comment:view")
    @PostMapping("/topic/list/{forumId}")
    public TableDataInfo list(@PathVariable("forumId") String forumId) {
        Topic topic = new Topic();
        topic.setForumId(forumId);
        return wrapTable(topicService.list(topic));
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequiresPermissions("jiaowu:comment:view")
    @PostMapping("/topic/remove")
    public Data remove(String ids) throws Exception {
        try {
            List<String> idList = Arrays.asList(Convert.toStrArray(ids));
            return toAjax(topicService.removeByIds(idList));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

}
