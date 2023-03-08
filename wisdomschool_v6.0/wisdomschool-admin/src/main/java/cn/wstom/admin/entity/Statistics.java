package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 数据统计
 *
 * @author dws
 */
@Setter
@Getter
public class Statistics extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String userId;
    /**
     * 发布问题数量
     */
    private Integer countQuestion;
    /**
     * 关注的问题数量
     */
    private Integer countQuestionFollow;
    /**
     * 关注话题数量
     */
    private Integer countTopic;
    /**
     * 回答问题数量
     */
    private Integer countAnswer;
    /**
     * 所有粉丝数量
     */
    private Integer countFans;
    /**
     * 关注数量
     */
    private Integer countFollow;
    /**
     * 发布文章数量
     */
    private Integer countArticle;
    /**
     * 发布分享数量
     */
    private Integer countShare;
    private BigDecimal balance;
    /**
     * 积分
     */
    private Integer score;
    /**
     * 经验
     */
    private Integer exp;
}
