package cn.wstom.student.entity;



import lombok.Getter;
import lombok.Setter;

/**
 * 评论实体
 *
 * @author dws
 */
@Setter
@Getter
public class Comment extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 评论类型，0：文章评论，1：课程评论
     */
    private Integer type;

    /**
     * 视<a>type</a>而定
     */
    private String typeId;
    /**
     * 评论归属id，默认为0
     */
    private String parentId;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 评论内容
     */
    private String content;
    /**
     * 顶
     */
    private Integer countDig;
    /**
     * 踩
     */
    private Integer countBury;
    /**
     * 状态
     */
    private Integer status;

    private String replyId;

    private String replyUserId;
}
