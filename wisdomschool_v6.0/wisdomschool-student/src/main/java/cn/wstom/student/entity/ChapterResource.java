package cn.wstom.student.entity;



import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 课程章节资源表 tb_chapter_resource
 *
 * @author dws
 * @date 20190223
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChapterResource extends BaseEntity {
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
     * 章节id
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
    /**
     * 考试状态
     */
    private String state;
    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date overTime;
}
