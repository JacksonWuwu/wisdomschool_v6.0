package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dws
 */
@Setter
@Getter
public class Question extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String title;
    private String content;
    /**
     * 该话题浏览数量
     */
    private Integer countView;
    /**
     * 关注该话题数量
     */
    private Integer countFollow;
    /**
     * 评论数量
     */
    private Integer countAnswer;
    /**
     * 顶
     */
    private Integer countDig;
    /**
     * 踩
     */
    private Integer countBury;
    /**
     * 推荐设置，数字越大越靠前
     */
    private Integer recommend;
    /**
     * 权重
     */
    private Double weight;
    private Integer status;

    private String tabs;
}
