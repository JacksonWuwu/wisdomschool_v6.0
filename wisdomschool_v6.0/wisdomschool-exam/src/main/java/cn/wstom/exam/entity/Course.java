package cn.wstom.exam.entity;



import cn.wstom.exam.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 注释表 tb_course
 *
 * @author hyb
 * @date 2019-01-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Course extends BaseEntity implements Comparable<Course>{
    private static final long serialVersionUID = 1L;

    public Course(String id) {
        this.id = id;
    }

    public Course() {
    }

    /**
     * 课程名称
     */
    @Excel(name = "课程名称")
    private String name;
    /**
     * 学时
     */
    @Excel(name = "课程学时")
    private String period;
    /**
     * 学分
     */
    @Excel(name = "课程学分")
    private String credit;
    /**x`
     * 课程类型
     */
    private String courseType;
    /*
     * 系部id
     * */
    private String dept_id;

    /*
     * 学校id
     * */
    private int schoolId;
    /*
     * 系部名称
     * */
    private Department department;
    /**
     * 课程信息
     */
    private String courseInfo;
    /**
     * 课程容量
     */
    private Integer courseNum;
    /**
     * 课程现状
     */
    private String courseStatus;
    /**
     * 开课时间
     */
//    @Excel(name = "创建时间")
    @JSONField(format = "yyyy-MM-dd")
    private Date courseTime;
    /**
     * 课程学习人数
     */
    private Integer courseUserNum;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", getName())
                .append("dept_id", getDept_id())
                .append("department", getDepartment())
                .append("period", getPeriod())
                .append("credit", getCredit())
                .append("courseType", getCourseType())
                .append("courseInfo", getCourseInfo())
                .append("courseNum", getCourseNum())
                .append("courseStatus", getCourseStatus())
                .append("courseTime", getCourseTime())
                .toString();
    }

    @Override
    public int compareTo(Course c) {
        if (Integer.valueOf(this.id) >= Integer.valueOf(c.getId())) {
            return 1;
        }
        return -1;
    }
}
