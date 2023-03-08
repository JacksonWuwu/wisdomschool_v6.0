package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 文章统计
 *
 * @author dws
 */
@Setter
@Getter
public class ArticleCount extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 顶
     */
    private Integer countDig;
    /**
     * 踩
     */
    private Integer countBury;
    /**
     * 评论次数
     */
    private Integer countComment;
    /**
     * 浏览数量
     */
    private Integer countView;
    /**
     * 权重
     */
    private Double weight;
}
