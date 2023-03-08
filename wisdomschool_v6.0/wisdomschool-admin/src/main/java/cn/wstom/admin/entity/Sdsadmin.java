package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Data;

@Data
public class Sdsadmin extends BaseEntity {
    private static final long serialVersionUID = -4989759857194731384L;
    private int grades;
    private int department;
    private int rid;
}
