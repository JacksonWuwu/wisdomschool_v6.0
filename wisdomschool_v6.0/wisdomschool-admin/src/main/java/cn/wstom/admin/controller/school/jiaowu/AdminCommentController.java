package cn.wstom.admin.controller.school.jiaowu;

import cn.wstom.common.base.Data;
import cn.wstom.admin.controller.BaseController;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import cn.wstom.common.web.page.TableDataInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author liniec
 * @date 2020/01/20 17:47
 *  admin评论管理
 */
@Controller
@RequestMapping("/jiaowu/comment")
public class AdminCommentController extends BaseController {

    @Autowired
    private ReplyService replyService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private TopicCommentService topicCommentService;

   // @ApiOperation("用户评论管理")
    @GetMapping("/list")
    public String listView() {
        return "/school/jiaowu/comment/list";
    }

   // @ApiOperation("用户评论管理")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TopicComment comment) {
        startPage();

        List<TopicComment> result = topicCommentService.list(comment);
        return wrapTable(result);
    }

   // @ApiOperation("评论删除")
    @PostMapping("/remove")
    @ResponseBody
    public Data remove(String ids, String type) throws Exception {
        if ("reply".equals(type)) {
            return toAjax(replyService.removeById(ids));
        }
        if ("deck".equals(type)) {
            return toAjax(deckService.removeById(ids));
        }
        return Data.error();
    }
}
