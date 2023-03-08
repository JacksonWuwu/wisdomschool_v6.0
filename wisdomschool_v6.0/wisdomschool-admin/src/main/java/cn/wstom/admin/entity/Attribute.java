package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Attribute extends BaseEntity {

    private String modelId;

    private Integer type;

    private String name;

    private Integer search;

    private String value;

    private static final long serialVersionUID = 1L;

}
