package cn.wstom.exam.entity;



import cn.wstom.exam.annotation.Excel;
import lombok.Data;

/**
 * 教师课程Vo
 *
 * @author hyb
 * @date 2019/02/14
 */
@Data
public class TeacherCourseVo extends TeacherCourse {
    private static final long serialVersionUID = -4765542647341463846L;

    /**
     * 教师信息
     */
    @Excel(name = "教师编号", comboField = "id", targetAttr = "id")
    private Teacher teacher;

    /**
     * 教师信息
     */
    @Excel(name = "教师名称", comboField = "userName", targetAttr = "userName")
    private SysUser sysUser;

    /**
     * 课程信息
     */
    @Excel(name = "课程", comboField = "name", targetAttr = "name")
    private Course course;
}
