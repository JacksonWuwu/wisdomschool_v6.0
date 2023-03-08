package cn.wstom.admin.entity;

import lombok.Data;

import java.util.List;

@Data
public class AdjunctStudent extends BaseEntity {

    /**
     * 课堂id 多对一关系
     */
    private String adjid;

    /**
     * 学生id
     */
    private String stuid;

    /**
     * 班级id
     */
    private String cid;

    /**
     * 提交时间
     */
    private String submitline;

    /**
     * 作业备注
     */
    private String jobcontent;

    /**
     * 状态
     */
    private Integer States;
    /**
     * 文件名称
     */
    private String filename;
    /**
     * 文件
     */
    private String results;
    /**
     * 作业备注
     */
    private String teachercontent;

    private String loginName;

    private String userName;

    private Adjunct adjunct;

    /*
     * 班级
     * */
    private Clbum clbum;

    /*
     * 班级名字
     * */
    private List<String> clbumName;
}
