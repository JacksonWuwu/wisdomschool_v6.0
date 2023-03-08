package cn.wstom.student.controller.comment;



import cn.wstom.student.constants.Data;
import cn.wstom.student.controller.BaseController;
import cn.wstom.student.core.http.ResultContracts;
import cn.wstom.student.core.http.ResultInfo;
import cn.wstom.student.entity.ArticleVotes;
import cn.wstom.student.entity.Deck;
import cn.wstom.student.entity.Reply;
import cn.wstom.student.entity.Topic;
import cn.wstom.student.service.ArticleService;
import cn.wstom.student.service.DeckService;
import cn.wstom.student.service.ReplyService;
import cn.wstom.student.service.TopicService;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/topic")
public class TopicController extends BaseController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private ArticleService articleService;

    /**
     *
     * @param pageNum
     * @param topicId
     * @return
     */
    @ApiOperation("话题内容")
    @RequestMapping(method = RequestMethod.GET, value = "/{topicId}")
    public Data topic(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum,
                      @PathVariable(value = "topicId") String topicId) {
        Topic topic = topicService.getById(topicId);

        //  已采纳回复
        Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("adopt", 1);
        params.put("topic", topic);
        params.put("order_by", "r.create_time asc");
        Reply adopt = replyService.getOne(params);

        //  其他回复
        Reply reply = new Reply();
        reply.setAdopt(0);
        reply.setTopic(topic);
        params.clear();
        params.put("orderBy", "create_time ASC");
        reply.setParams(params);
        loadNumData(pageNum);
        List<Reply> replies = replyService.list(reply);
        Collections.reverse(replies);
        Deck deck = new Deck();
        replies.forEach(r -> {
            deck.setReply(r);
            r.setDecks(new HashSet<>());
        });

        HashMap<String, Object> map = new HashMap<>();
        map.put("topic", topic);
        map.put("adopt", adopt);
        map.put("replies", wrapTable(replies));
        return Data.success(map);
    }


    /**
     * 发布回复
     * @param topicId
     * @param replyContent
     * @param request
     * @return
     */
    @ApiOperation("发布回复")
    @RequestMapping(method = RequestMethod.POST, value = "/{topicId}/push")
    @ResponseBody
    public ResultInfo replyPush(
            @PathVariable(value = "topicId") String topicId, String replyContent, HttpServletRequest request) {
        if (StringUtils.isEmpty(replyContent)) {
            return new ResultInfo(ResultContracts.FAILED);
        }

        Reply reply = new Reply();
        reply.setContent(replyContent);
        Topic topic = topicService.getById(topicId);
        reply.setTopic(topic);
        reply.setCreateBy(getUser().getId());
        reply.setUserType(String.valueOf(getUser().getUserType()));
        reply.setIpAddr(request.getRemoteAddr());
        reply.setThumbsUp(0L);
        reply.setCreateName(getUser().getUserName());

        topic.setLastReply(reply);
        try {
            replyService.save(reply);
            topicService.update(topic);


            Reply selfReply = new Reply();
            selfReply.setCreateTime(new Date());
            selfReply.setCreateName(reply.getCreateName());
            selfReply.setCreateBy(reply.getCreateBy());
            selfReply.setContent(reply.getContent());
            selfReply.setUserType(reply.getUserType());
            return new ResultInfo(ResultContracts.SUCCESS, selfReply);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(ResultContracts.FAILED);
        }
    }

    /**
     *
     * @param pageNum
     * @param replyId
     * @return
     */
    @ApiOperation("二级回复列表")
    @RequestMapping(method = RequestMethod.GET, value = "/reply/{replyId}")
    public Data deckList(@RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum,
                         @PathVariable(value = "replyId") String replyId){
        Reply reply = replyService.getById(replyId);
        Deck deck = new Deck();
        deck.setReply(reply);
        loadNumData(pageNum);
        List<Deck> result = deckService.list(deck);
        return Data.success(result);
    }

    @ApiOperation("发布二级回复")
    @RequestMapping(method = RequestMethod.POST, value = "/reply/{replyId}/push")
    public ResultInfo deckPush(@PathVariable(value = "replyId") String replyId, String deckContent, HttpServletRequest request) {
        if (StringUtils.isEmpty(deckContent)) {
            return new ResultInfo(ResultContracts.FAILED);
        }
        Reply reply = replyService.getById(replyId);
        Deck deck = new Deck();
        deck.setContent(deckContent);
        deck.setCreateBy(getUser().getId());
        deck.setUserType(String.valueOf(getUser().getUserType()));
        deck.setReply(reply);
        deck.setCreateName(getUser().getUserName());
        deck.setIpAddr(request.getRemoteAddr());
        deck.setThumbsUp(0L);
        try {
            deckService.save(deck);
            Deck selfDeck = new Deck();
            selfDeck.setContent(deck.getContent());
            selfDeck.setCreateName(deck.getCreateName());
            selfDeck.setCreateBy(deck.getCreateBy());
            selfDeck.setCreateTime(new Date());
            return new ResultInfo(ResultContracts.SUCCESS, selfDeck);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultInfo(ResultContracts.FAILED);
        }
    }

    @ApiOperation("点赞")
    @RequestMapping(method = RequestMethod.POST, value = "/reply/{replyId}/like")
    public Data replyList(@PathVariable(value = "replyId") String replyId) {
        ArticleVotes articleVotes = new ArticleVotes();
        articleVotes.setUserId(getUserId());
        articleVotes.setInfoType(2);
        articleVotes.setInfoId(replyId);
        //  查询是否已点赞
        if (articleService.checkArticleVotes(2, replyId, getUserId())) {
            articleVotes = articleService.findArticleVotes(articleVotes).get(0);
            if (articleVotes != null) {
                switch (articleVotes.getDig()) {
                    case 0:
                        articleVotes.setDig(1);
                        break;
                    case 1:
                        articleVotes.setDig(0);
                        break;
                }
                articleService.updateArticleVotesById(articleVotes);
            }
        } else {
            articleVotes.setDig(1);
            articleService.addArticleVotes(articleVotes);
        }
        //更新点赞数
        try {
            long digCount = articleService.selectDigCount(2, replyId);
            Reply reply = replyService.getById(replyId);
            reply.setThumbsUp(digCount);
            replyService.update(reply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Data.success();
    }


    @ApiOperation("删除话题")
    @RequestMapping(method = RequestMethod.POST, value = "/remove")
    public Data topicRemove(String topicId) throws Exception {
        Topic topic = topicService.getById(topicId);
        if (topic.getCreateBy().equals(getUserId())) {
            topicService.removeById(topicId);
            return Data.success();
        }
        return Data.error();
    }
}
