package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Data;

@Data
public class PreviewStudent extends BaseEntity {
    private Integer pid;

    private Integer sid;

    private Integer tcid;
}
