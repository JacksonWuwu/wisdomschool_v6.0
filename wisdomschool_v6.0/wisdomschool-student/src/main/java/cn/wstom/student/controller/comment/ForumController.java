package cn.wstom.student.controller.comment;


import cn.wstom.student.controller.BaseController;
import cn.wstom.student.core.http.ResultContracts;
import cn.wstom.student.core.http.ResultInfo;
import cn.wstom.student.entity.Forum;
import cn.wstom.student.entity.SysUser;
import cn.wstom.student.entity.Topic;
import cn.wstom.student.service.ForumService;
import cn.wstom.student.service.TopicService;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController extends BaseController {
    private static final String PREFIX = "/front/forum";
    private static final String TOPIC_PREFIX = "/school/teacher/forum";

    @Autowired
    private ForumService forumService;
    @Autowired
    private TopicService topicService;

    @ApiOperation("板块列表")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResultInfo forumList() {
        List<Forum> forums = forumService.list(new Forum());
        Map<String, Object> map = new HashMap<>();
        map.put("forums", forums);
        System.out.println("forums="+forums);
        return new ResultInfo(ResultContracts.SUCCESS, map);
    }

    @ApiOperation("板块内容")
    @RequestMapping(method = RequestMethod.GET, value = "/{forumId}")
    public ResultInfo topic(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum,
                      @PathVariable(value = "forumId") String forumId) {
        Forum forum = new Forum();
        forum.setId(forumId);
        Topic topic = new Topic();
        topic.setForum(forum);
        Page page = loadNumData(pageNum);
        List<Topic> topics = topicService.list(topic);

        topics.forEach(t -> {
            SysUser user = adminService.getUserById(t.getCreateBy());
            if (user != null && StringUtils.isNotEmpty(user.getAvatar())) {
                t.setUserAvatar(user.getAvatar());
            }
        });


        Map<String, Object> data = new HashMap<>();
        data.put("userId", getUserId());
        data.put("topics", topics);
        data.put("pageSize", page.getPages());
        return new ResultInfo(ResultContracts.SUCCESS, data);
    }



    @RequestMapping(value = {"", "/index", "/index/{sort}"})
    public String index(@PathVariable(value = "sort", required = false) String sort,
                        @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        if ("hot".equals(sort)) {
            sort = "hot";
        } else if ("recommend".equals(sort)) {
            sort = "recommend";
        } else {
            sort = null;
        }
        modelMap.addAttribute("sort", sort);
        modelMap.addAttribute("p", p);
        return PREFIX + "/index";
    }

    // TODO: 2019/4/1 后续和上面合并

    /**
     * @param sort
     * @param p
     * @param modelMap
     * @return
     */
    @RequestMapping(value = {"/course", "/course/index", "/course/index/{sort}"})
    public String index2(@PathVariable(value = "sort", required = false) String sort,
                         @RequestParam(value = "p", defaultValue = "1") int p, ModelMap modelMap) {
        if ("hot".equals(sort)) {
            sort = "hot";
        } else if ("recommend".equals(sort)) {
            sort = "recommend";
        } else {
            sort = null;
        }
        modelMap.addAttribute("sort", sort);
        modelMap.addAttribute("p", p);
        return TOPIC_PREFIX + "/topicManage";
    }

}
