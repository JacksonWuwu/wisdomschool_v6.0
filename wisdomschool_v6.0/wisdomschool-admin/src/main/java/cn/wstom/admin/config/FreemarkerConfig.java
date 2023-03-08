package cn.wstom.admin.config;


import cn.wstom.admin.directive.*;
import cn.wstom.admin.entity.TimeAgoMethod;
import cn.wstom.common.config.SiteConfig;

import cn.wstom.common.utils.SpringUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 模板引擎配置
 *
 * @author dws
 * @date 2018/11/25
 */
@Component
public class FreemarkerConfig {
    private final SiteConfig siteConfig;

    private final Configuration configuration;

    @Autowired
    public FreemarkerConfig(SiteConfig siteConfig, Configuration configuration) {
        this.siteConfig = siteConfig;
        this.configuration = configuration;
    }

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        //configuration.setSharedVariable("shiro", new ShiroTags());
        configuration.setSharedVariable("timeAgo", new TimeAgoMethod());
        configuration.setSharedVariable("dictionary", SpringUtils.getBean(DictionaryDirective.class));
        configuration.setSharedVariable("forumPage", SpringUtils.getBean(ForumPageDirective.class));
        configuration.setSharedVariable("topicPage", SpringUtils.getBean(TopicPageDirective.class));
        configuration.setSharedVariable("hotPage", SpringUtils.getBean(HotPageDirective.class));
        configuration.setSharedVariable("articleType", SpringUtils.getBean(ArticleTypeDirective.class));
        configuration.setSharedVariable("contentPage", SpringUtils.getBean(ContentPagesDirective.class));
        configuration.setSharedVariable("articlePage", SpringUtils.getBean(ArticlePageDirective.class));
        configuration.setSharedVariable("feedPage", SpringUtils.getBean(FeedPageDirective.class));
        configuration.setSharedVariable("articleInfo", SpringUtils.getBean(ArticleInfoDirective.class));
        configuration.setSharedVariable("userInfo", SpringUtils.getBean(UserInfoDirective.class));
        configuration.setSharedVariable("avatar", SpringUtils.getBean(AvatarDirective.class));
        configuration.setSharedVariable("questionInfo", SpringUtils.getBean(QuestionInfoDirective.class));
        configuration.setSharedVariable("answerInfo", SpringUtils.getBean(AnswerInfoDirective.class));
        configuration.setSharedVariable("topicInfo", SpringUtils.getBean(TopicInfoDirective.class));
        configuration.setSharedVariable("answerPage", SpringUtils.getBean(AnswerPageDirective.class));
        configuration.setSharedVariable("articleTypeInfo", SpringUtils.getBean(ArticleTypeInfoDirective.class));
        configuration.setSharedVariable("commentPage", SpringUtils.getBean(CommentPageDirective.class));
        configuration.setSharedVariable("articleCommentInfo", SpringUtils.getBean(ArticleCommentInfoDirective.class));
        configuration.setSharedVariable("checkFollow", SpringUtils.getBean(CheckFollowDirective.class));
        configuration.setSharedVariable("newestAnswerInfo", SpringUtils.getBean(NewestAnswerInfoDirective.class));
        configuration.setSharedVariable("subContent", SpringUtils.getBean(SubContentDirective.class));
        configuration.setSharedVariable("chapterResource", SpringUtils.getBean(ChapterResourceDirective.class));
        configuration.setSharedVariable("courseCommentPage", SpringUtils.getBean(CourseCommentPageDirective.class));

        //网站名称
        configuration.setSharedVariable("site-name", siteConfig.getSiteName());
        //网页主页链接
        configuration.setSharedVariable("site-url", siteConfig.getSiteUrl());
        //网页主页链接
        configuration.setSharedVariable("site-logo", siteConfig.getSiteLogo());
        //首页标题
        configuration.setSharedVariable("seo-title", siteConfig.getSeoTitle());
        //网站关键字
        configuration.setSharedVariable("seo-keywords", siteConfig.getSeoKeywords());
        //网站描述
        configuration.setSharedVariable("seo-description", siteConfig.getSeoDescription());
        //站点底部信息
        configuration.setSharedVariable("site-footer-code", siteConfig.getSiteFooterCode());
        //备案信息
        configuration.setSharedVariable("site-beian", siteConfig.getSiteBean());
    }
}
