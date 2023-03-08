package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 敏感词过滤
 *
 * @author dws
 */
@Setter
@Getter
public class FilterKeyword extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 违禁关键词
     */
    private String keyword;
}
