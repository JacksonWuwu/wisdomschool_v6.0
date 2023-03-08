package cn.wstom.common.constant;

/**
 * 消息事件常量
 *
 * @author dws
 * @date 2019/02/27
 */
public interface EventConstants {


    /**
     * 文章事件 -- 发布
     */
    int EVENT_POST_PUBLISH = 0;

    /**
     * 文章事件 -- 删除
     */
    int EVENT_POST_DELETE = 1;

    /**
     * 文章事件 -- 收藏
     */
    int EVENT_ARTICLE_FAVORITES = 2;

    /**
     * 文章事件 --  评论
     */
    int EVENT_ARTICLE_COMMENT = 10;

    /**
     * 课程事件 -- 加入
     */
    int EVENT_COURSE_JOIN = 11;

    /**
     * 课程事件 -- 退出
     */
    int EVENT_COURSE_QUIT = 12;

    /**
     * 课程事件 -- 收藏
     */
    int EVENT_COURSE_FAVORITES = 13;

    /**
     * 论坛事件 -- 评论
     */
    int EVENT_FORUM_REPLY = 20;

    /**
     * 论坛事件 -- 点赞
     */
    int EVENT_FORUM_LIKED = 21;

    /**
     * 用户事件 -- 关注
     */
    int EVENT_USER_FOLLOW = 30;

    /**
     * 文章事件
     */
    int EVENT_POST = 50;
    /**
     * 课程事件
     */
    int EVENT_COURSE = 51;
}
