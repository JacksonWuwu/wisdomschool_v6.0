package cn.wstom.student.controller.comment;


import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.entity.Deck;
import cn.wstom.student.entity.Reply;
import cn.wstom.student.service.DeckService;
import cn.wstom.student.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController extends BaseController {

    private final String COMMENT_TYPE_REPLY = "reply";
    private final String COMMENT_TYPE_DECK = "deck";

    @Autowired
    private ReplyService replyService;
    @Autowired
    private DeckService deckService;

    @ApiOperation("删除个人评论")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public Data remove(@RequestParam("id") String id, @RequestParam("type") String type) throws Exception {
        if (type.equals(COMMENT_TYPE_REPLY)) {
            Reply reply = replyService.getById(id);
            if (reply.getCreateBy().equals(getUserId())) {
                return toAjax(replyService.removeById(id));
            }
        }
        if (type.equals(COMMENT_TYPE_DECK)) {
            Deck deck = deckService.getById(id);
            if (deck.getCreateBy().equals(getUserId())) {
                return toAjax(deckService.removeById(id));
            }
        }
        return Data.error();
    }
}
