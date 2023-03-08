package cn.wstom.exam.entity;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Preview extends BaseEntity {

    /**
     * 名称
     */
    private String previewname;
    /**
     * 课件
     */
    private Integer recourserid;

    /**
     * 时间
     */
    private Date endtime;
    /*
    * 状态
    * */
    private Integer state;
    /**
     * 时间
     */
    private Date starttime;

    /**
     * 老师课堂id
     */
    private String tcid;
    /*
    * 班级id
    * */
    private String cid;
    /**
     * 章节
     */
    private String chapterids;

    /*
     *学生id数组
     * */
    private String userId;
    /*
     * 班级
     * */
    private Clbum clbum;
    /*
     * 班级名字
     * */
    private List<String> clbumName;
    /*
     * 章节名字
     * */
    private List<String> chapterName;
}
