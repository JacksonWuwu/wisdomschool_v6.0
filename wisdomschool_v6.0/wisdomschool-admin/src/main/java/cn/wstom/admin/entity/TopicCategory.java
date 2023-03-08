package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <b>话题分类实体类</b>
 */
@Setter
@Getter
public class TopicCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long fatherId;
    private String name;
    private String customUrl;
    private String keywords;
    private String description;
    private Integer isRecommend;
    private Integer sort;
    private Integer status;
}
