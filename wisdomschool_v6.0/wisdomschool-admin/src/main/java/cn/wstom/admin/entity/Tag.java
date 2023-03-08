package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : dws
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Tag extends BaseEntity {
    private static final long serialVersionUID = 3776111407841066819L;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    /**
     * 标签名
     */
    private String name;

    /**
     * 预览图
     */
    private String thumbnail;

    /**
     * 描述
     */
    private String description;

    /**
     * 文章数
     */
    private Integer posts;
}
