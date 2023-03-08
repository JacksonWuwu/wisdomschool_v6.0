package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 文章类别
 *
 * @author dws
 */
@Setter
@Getter
public class ArticleCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String parentId;

    private String name;

    private String keywords;

    private String description;

    private Integer recomment;

    private Integer status;

    private Integer sort;

}
