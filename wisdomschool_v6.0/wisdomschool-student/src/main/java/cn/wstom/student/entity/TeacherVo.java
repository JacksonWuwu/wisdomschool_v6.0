package cn.wstom.student.entity;



import cn.wstom.student.annotation.Excel;
import lombok.Data;

/**
 * 教师Vo
 *
 * @author xyl
 * @date 2019/02/15
 */
@Data
public class TeacherVo extends SysUser {
    private static final long serialVersionUID = -4765542647341463846L;

    /**
     * 教师信息
     */
    @Excel(name = "入职时间", targetAttr = "workDate")
    private Teacher teacher;

    /**
     * 系部信息
     */
    @Excel(name = "院系名称", comboField = "name", targetAttr = "name")
    private Department department;


    private Integer departmentid;
    /**
     * 专业信息
     */
    @Excel(name = "专业", comboField = "name", targetAttr = "name")
    private Major major;



    private Integer majorid;

    private String[] courseIds;
}
