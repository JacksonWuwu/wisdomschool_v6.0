package cn.wstom.admin.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author dws
 * @date 2019/02/22
 */
@Data
public class Recourse extends BaseEntity {
    private static final long serialVersionUID = 6721039349742298409L;

    /**
     * 资源名称
     */
    private String name;

    private Date create_time;
    /**
     * 教师课程
     */
    private String tcId;

    /**
     * 资源附件id
     */
    private String attrId;

    /**
     * 附件后缀名
     */
    private String ext;

    /**
     * 资源附件类型
     */
    private String attrType;

    /**
     * 观看次数
     */
    private Integer count;

    /**
     * 原文件名
     */
    private String fileName;

    /**
     * 资源类型
     * 1、视频
     * 2、音频
     * 3、excel
     * 4、word
     * 5、ppt
     * 6、图片
     */
    private Integer recourseType;

    private RecourseType category;
}
