package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程章节资源表 tb_course_resource
 *
 * @author dws
 * @date 20190223
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseResource extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    private String rid;

    /**
     * 参照：ResourceConstant常量
     * 资源类型：1、视频，2、课件，3、试题
     */
    private Integer resourceType;

    /**
     * 资源后缀名
     */
    private String ext;

    /**
     * 课程id
     */
    private String cid;

    /**
     * 序号
     */
    private Integer order;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 教师课程id
     */
    private String tcId;

    /**
     * 临时填充attrId
     */
    private String attrId;
}
