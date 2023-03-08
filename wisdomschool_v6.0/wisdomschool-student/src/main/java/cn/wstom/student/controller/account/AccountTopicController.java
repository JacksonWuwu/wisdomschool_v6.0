package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.TableDataInfo;
import cn.wstom.student.entity.Topic;
import cn.wstom.student.service.TopicService;
import cn.wstom.student.utils.AtomicIntegerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liniec
 * @date 2020/01/20 18:10
 *  学生管理个人话题
 */
@Controller
@RequestMapping("/account/topic")
public class AccountTopicController extends BaseController {

    @Autowired
    private TopicService topicService;


    /**
     * 获取
     * @return
     */
   // @ApiOperation("个人帖子")
    @GetMapping("/index")
    public String indexTopic(HttpServletRequest request, ModelMap modelMap) {

        int sort = ServletRequestUtils.getIntParameter(request, "sort", 1);
        Topic topic = new Topic();
        topic.setCreateBy(getUserId());
        startPage();//  分页
        List<Topic> topicList = topicService.list(topic);

        /*  Atomic __ */
        topicList.forEach( t -> {
            t.setBrowse((long) AtomicIntegerUtil.getInstance(t.getClass(), t.getId(), t.getBrowse()).get());
        });

        modelMap.put("pageModel", wrapTable(topicList));
        modelMap.put("sort", sort);
        modelMap.put("userId", getUserId());
        return "/front/account/topic";
    }

    /**
     * 获取
     * @return
     */
//    public TableDataInfo topicList() {
//        List<Object> comments = null;
//        return wrapTable(comments);
//    }

   // @ApiOperation("个人话题查询")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list() {
        Topic topic = new Topic();
        topic.setCreateBy(getUserId());
        return wrapTable(topicService.list(topic));
    }

    //@ApiOperation("个人话题删除")
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String id) throws Exception {
        return toAjax(topicService.removeById(id));
    }
}
