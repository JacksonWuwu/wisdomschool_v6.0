package cn.wstom.admin.event.handler;

import cn.wstom.admin.entity.NotifyVO;
import cn.wstom.admin.event.NotifyEvent;
import cn.wstom.admin.service.ArticleCountService;
import cn.wstom.admin.service.NotifyService;
import cn.wstom.admin.service.SysUserService;
import cn.wstom.common.constant.EventConstants;
import cn.wstom.common.constant.UserConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author dws
 */
@Component
public class NotifyEventHandler implements ApplicationListener<NotifyEvent> {

    //    private final ArticleService articleService;
    private final ArticleCountService articleCountService;
    private final SysUserService userService;
    private final NotifyService notifyService;

    @Autowired
    public NotifyEventHandler(/*ArticleService articleService,*/
                              ArticleCountService articleCountService,
                              SysUserService userService,
                              NotifyService notifyService) {
//        this.articleService = articleService;
        this.articleCountService = articleCountService;
        this.userService = userService;
        this.notifyService = notifyService;
    }

    @Async
    @Override
    public void onApplicationEvent(NotifyEvent event) {

        NotifyVO nt = new NotifyVO();
        nt.setTargetId(event.getTargetId());
        nt.setSourceUserId(event.getSourceUserId());
        nt.setEventType(event.getEventType());

        switch (event.getEventType()) {
            case EventConstants.EVENT_ARTICLE_FAVORITES:
//                Article article = articleService.getById(event.getTargetId());
//                nt.setTargetUserId(article.getUserId());
                break;
            case EventConstants.EVENT_ARTICLE_COMMENT:
//                article = articleService.getById(event.getTargetId());
//                nt.setTargetUserId(article.getUserId());

                try {
                    // 自增评论数
                    articleCountService.updateComment(event.getTargetId(), UserConstants.STEP_INCREASE);
//                    userService.updateComment(article.getUserId(), UserConstants.STEP_INCREASE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //发布文章
            case EventConstants.EVENT_POST_PUBLISH:
                /*article = articleService.getById(event.getTargetId());
                nt.setTargetUserId(article.getUserId());
                ArticleCount articleCount = new ArticleCount();
                articleCount.setArticleId(article.getId());
                articleCount.setCreateBy(ShiroUtils.getLoginName());
                try {
                    userService.updateArticle(article.getUserId(), UserConstants.STEP_INCREASE);
                    articleCountService.save(articleCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                break;
            default:
                nt.setTargetUserId(event.getTargetUserId());
        }

        try {
            notifyService.save(nt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
