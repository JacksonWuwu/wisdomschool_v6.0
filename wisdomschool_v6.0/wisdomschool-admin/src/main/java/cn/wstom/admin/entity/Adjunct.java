package cn.wstom.admin.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Adjunct extends BaseEntity {

    /**
     * 教师课堂id 多对一关系
     */
    private String tcid;

    /**
     * 作业名称
     */
    private String adjunctname;
    /**
     * 截止时间
     */
    private String deadline;
    /**
     * 开始时间
     */
    private Date starline;
    /**
     * 是否可以超时
     */
    private Integer timeout;
    /**
     * 作业内容
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
     * 文件id
     */
    private String rid;
    /**
     * 班级id
     */
    private String cid;
    /*
     * 班级名字
     * */
    private List<String> clbumName;
    /*
    * 班级
    * */
    private Clbum clbum;

    /*
     *学生id数组
     * */
    private String userId;

    private String clbums;


}
