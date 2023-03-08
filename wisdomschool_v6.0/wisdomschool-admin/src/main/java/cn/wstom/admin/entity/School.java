package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class School extends BaseEntity {
    /**
     * 学校名字
     */
    private String name;

    /**
     * 学校类型
     */
    private String type;

    /**
     * 学校官网
     */
    private String website;

    /**
     * 学校所在地
     */
    private String location;

    /**
     * 学校性质
     */
    private String nature;

    /**
     * 学校隶属
     */
    private String affiliation;


    private String administrator;



}
