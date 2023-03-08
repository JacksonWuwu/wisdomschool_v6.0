package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Setter
@Getter
public class QuestionCount extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 问题id
     */
    private String questionId;
    /**
     * 回答数量
     */
    private Integer countAnswer;
    /**
     * 关注人数
     */
    private Integer countFollow;
    /**
     * 浏览数量
     */
    private Integer countView;
    /**
     * 顶
     */
    private Integer countDig;
    /**
     * 踩
     */
    private Integer countBury;
    /**
     * 权重
     */
    private Double weight;
}
