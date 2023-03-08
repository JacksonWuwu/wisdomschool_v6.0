package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;
import lombok.Data;

@Data
public class CourseVo extends Course {

    private Course course;
    /**
     * 系部信息
     */
    @Excel(name = "系部", comboField = "name", targetAttr = "name")
    private Department department;
}
