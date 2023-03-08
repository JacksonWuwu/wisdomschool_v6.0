package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Setter
@Getter
public class Topic extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 创建者类型：老师、学生
     */
    private String userType;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表文章时所用的IP地址
     */
    private String ipAddr;
    /**
     * 所属版块
     */
    private Forum forum;

    private String tcid;

    /**
     * 回复
     */
    private Set<Reply> replies = new HashSet<>();
    /**
     * 类型 普通0 置顶2
     */
    private Integer type;
    /**
     * 是否精华帖 1是 0不是
     */
    private Integer essence;
    /**
     * 回复数量
     */
    private Integer replyCount;
    /**
     * 最后回复
     */
    private Reply lastReply;
    /**
     * 浏览次数
     */
    private Long browse;
    /**
     * 点赞数 不能用like 因为 数据库默认字段
     */
    private Long thumbsUp;
    private String createName;
    private String modifyName;
    private String forumId;
    private String lastReplyId;

    private String userAvatar;
}
