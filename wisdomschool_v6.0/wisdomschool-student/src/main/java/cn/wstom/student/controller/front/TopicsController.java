package cn.wstom.student.controller.front;


import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.Discuss;
import cn.wstom.student.entity.Forum;
import cn.wstom.student.service.DiscussService;
import cn.wstom.student.service.ForumService;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Controller
public class TopicsController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(TopicsController.class);
    private static final String PREFIX = "/front/topics";

    private final ForumService forumService;
    private final DiscussService discussService;

    @Autowired
    public TopicsController(ForumService forumService, DiscussService discussService) {
        this.forumService = forumService;
        this.discussService = discussService;
    }

    /**
     * 话题首页
     *
     * @return
     */
    @GetMapping(value = {"/topics/"})
    public String topics(@RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        modelMap.addAttribute("p", p);
        return PREFIX + "/index";
    }

    /*@RequestMapping("/toTalk/{teacherCourseId}")
    public String toTalk(HttpServletRequest request, ModelMap model, @PathVariable String teacherCourseId) throws IOException, ServletRequestBindingException {
        System.out.println(teacherCourseId);
        topicList(request, model);
        return TOPIC_PREFIX + "/talkList";
    }

    @RequestMapping("/toTalkManage")
    public String toTopicManage(HttpServletRequest request, ModelMap model) throws ServletRequestBindingException {
        topicList(request, model);
        return TOPIC_PREFIX + "/topicManage";
    }*/

    private void topicList(HttpServletRequest request, ModelMap model) throws ServletRequestBindingException {
        // 加载用户信息
        // 论坛的排序方式

        String course = ServletRequestUtils.getStringParameter(request, "course");
        String forumid = ServletRequestUtils.getStringParameter(request, "forumid");
        String sort = ServletRequestUtils.getStringParameter(request, "sort");
        // 当前页
        String currPage = ServletRequestUtils.getStringParameter(request, "pageNum");
        int pageNum;
        if (StringUtils.isNumeric(currPage) && StringUtils.isNotEmpty(currPage)) {
            pageNum = Integer.valueOf(currPage);
        } else {
            pageNum = 1;
        }
        // 每页显示10条
        int pageSize = 10;

        List<Forum> forums;
        forumid = StringUtils.trim(forumid);
        forums = forumService.list(new Forum());
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("sort", sort);
        try {
            //判断显示板块方式，0表示全部
            List<Discuss> discusses;
            if ("0".equals(forumid)) {
                PageHelper.startPage(pageNum, pageSize);
                Discuss discuss = new Discuss();
                discuss.setParams(params);
                discusses = discussService.list(discuss);
            } else {
                Forum forum = new Forum();
                forum.setId(forumid);
                Discuss disc = new Discuss();
                disc.setForum(forum);
                disc.setParams(params);
                PageHelper.startPage(pageNum, pageSize);
                discusses = discussService.list(disc);
            }
            model.put("forumList", forums);
            model.put("pageModel", wrapTable(discusses));
            model.put("course", course);
            model.put("forumid", forumid);
            model.put("sort", sort);
        } catch (Exception e) {
            Discuss disc = new Discuss();
            params.clear();
            params.put("sort", 1);
            disc.setParams(params);
            PageHelper.startPage(pageNum, pageSize);
            List<Discuss> list = discussService.list(disc);
            model.put("ForumList", forums);
            model.put("pageModel", wrapTable(list));
            model.put("course", 1);
            model.put("forumid", 0);
            model.put("sort", 1);
        }
    }
}
