package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author dws
 */
@Setter
@Getter
public class Answer extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 问题id
     */
    private String questionId;
    /**
     * 评论用户id
     */
    private String userId;

    /**
     * 父id
     */
    private String parentId;
    /**
     *
     */
    private Integer status;
    /**
     * 权重计算值
     */
    private BigDecimal weight;
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
     * 评论数量
     */
    private Integer countComment;
}
