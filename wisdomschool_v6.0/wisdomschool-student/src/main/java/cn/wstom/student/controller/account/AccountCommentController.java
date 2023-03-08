package cn.wstom.student.controller.account;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.TopicComment;
import cn.wstom.student.service.DeckService;
import cn.wstom.student.service.ReplyService;
import cn.wstom.student.service.TopicCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liniec
 * @date 2020/01/20 18:10
 *  学生管理个人评论
 *      - reply
 *      - deck
 */
@Controller
@RequestMapping("/account/comment")
public class AccountCommentController extends BaseController {

    private final String COMMENT_TYPE_REPLY = "reply";
    private final String COMMENT_TYPE_DECK = "deck";


    @Autowired
    private ReplyService replyService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private TopicCommentService commentService;

    /**
     * 获取所有回复以及二级回复
     * @return
     */
    @GetMapping("/index")
    public String indexComment(ModelMap modelMap) {

        TopicComment topicComment = new TopicComment();
        topicComment.setCreateBy(getUserId());
        startPage();
        List<TopicComment> comments = commentService.list(topicComment);

        modelMap.put("pageModel", wrapTable(comments));

        return "/front/account/comment";
    }


    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Data remove(@RequestParam("id") String id, @RequestParam("type") String type) throws Exception {
        if (type.equals(COMMENT_TYPE_REPLY)) {
            return toAjax(replyService.removeById(id));
        }
        if (type.equals(COMMENT_TYPE_DECK)) {
            return toAjax(deckService.removeById(id));
        }
        return Data.error();
    }
}
