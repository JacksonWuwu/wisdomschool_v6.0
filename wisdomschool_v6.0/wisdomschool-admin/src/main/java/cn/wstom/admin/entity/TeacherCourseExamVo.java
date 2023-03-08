package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;

import lombok.Data;

/**
 * 教师课程考试Vo
 *
 * @author hyb
 * @date 2019/02/14
 */
@Data
public class TeacherCourseExamVo extends TeacherCourseExam {
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

    /**
     * 考试信息
     */
    @Excel(name = "试卷名", comboField = "testName", targetAttr = "testName")
    private TestPaperOne testPaperOne;
}
