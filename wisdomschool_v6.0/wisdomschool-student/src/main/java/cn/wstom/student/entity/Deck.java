package cn.wstom.student.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 楼中楼回复
 */
@Getter
@Setter
@ToString
public class Deck extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 创建者类型：老师、学生、学校管理员=1，2，3；
     */
    private String userType;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表时所用的IP地址
     */
    private String ipAddr;
    /**
     * 所属回复
     */
    private Reply reply;
    /**
     * 回复对象的id
     */
    private String toUserId;
    /**
     * 回复对象的Name
     */
    private String toUserName;
    /**
     * 回复对象的类型
     */
    private String toUserType;
    /**
     * 点赞数
     */
    private Long thumbsUp;
    private String createName;
    private String modifyName;

    private String tcid;
}
