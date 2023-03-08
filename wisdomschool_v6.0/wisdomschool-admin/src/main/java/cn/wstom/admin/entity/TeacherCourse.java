package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 教师课程表 tb_teacher_course
 *
 * @author hyb
 * @date 2019-01-29
 */
@Data
public class TeacherCourse extends BaseEntity {
    private static final long serialVersionUID = 1L;


    private String tcid;

    /**
     * 教师id
     */
    private String tid;
    /**
     * 课程id
     */
    private String cid;

    /**
     * 课程简介
     */
    private String courseBriefIntroduction;

    /**
     * 预览图
     */
    private String thumbnailPath;

    private String coursename;

    public TeacherCourse(String tid, String cid) {
        this.tid = tid;
        this.cid = cid;
    }

    public TeacherCourse() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tid", tid)
                .append("cid", cid)
                .append("courseBriefIntroduction", courseBriefIntroduction)
                .append("thumbnailPath", thumbnailPath)
                .append("id", id)
                .toString();
    }
}
