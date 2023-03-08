package cn.wstom.student.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

/**
 * @author dws
 * @date 2019/03/31
 */
@Getter
@Setter
@ToString
public class Discuss extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 创建者类型：老师、学生、学校管理员=1，2，3；
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
    /**
     * 回复
     */
    private Set<Reply> replies = new HashSet<>();
    /**
     * 类型 普通0 置顶2
     */
    private int type;
    /**
     * 是否精华帖 1是 0不是
     */
    private int essence;
    /**
     * 回复数量
     */
    private int replyCount;
    /**
     * 最后回复
     */
    private Reply lastReply;
    /**
     * 浏览次数
     */
    private Long view;
    /**
     * 点赞数
     */
    private Long thumbsUp;
}
