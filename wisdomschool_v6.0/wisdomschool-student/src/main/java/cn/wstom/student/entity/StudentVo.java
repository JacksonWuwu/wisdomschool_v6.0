package cn.wstom.student.entity;



import cn.wstom.student.annotation.Excel;
import lombok.Data;

/**
 * 学生Vo
 *
 * @author dws
 * @date 2019/01/06
 */
@Data
public class StudentVo extends SysUser {
    private static final long serialVersionUID = -4765542647341463846L;

    /**
     * 学生信息
     */
    @Excel(name = "入学时间", targetAttr = "enrollmentDate")
    private Student student;

    /**
     * 班级信息
     */
    @Excel(name = "班级", targetAttr = "name")
    private Clbum clbum;

    /**
     * 年级信息
     */
    @Excel(name = "年级", targetAttr = "name")
    private Grades grades;

    /**
     * 专业信息
     */
    @Excel(name = "专业", targetAttr = "name")
    private Major major;

    /**
     * 系部信息
     */
    @Excel(name = "系部", targetAttr = "name")
    private Department department;


}
