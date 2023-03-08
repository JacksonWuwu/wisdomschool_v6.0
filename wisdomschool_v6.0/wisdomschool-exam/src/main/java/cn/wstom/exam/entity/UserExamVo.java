package cn.wstom.exam.entity;



import cn.wstom.exam.annotation.Excel;
import lombok.Data;

/**
 * 教师Vo
 *
 * @author xyl
 * @date 2019/02/15
 */
@Data
public class UserExamVo extends UserExam {
    private static final long serialVersionUID = -4765542647341463846L;

    @Excel(name = "姓名", comboField = "userName", targetAttr = "userName")
    private SysUser sysUser;
    @Excel(name = "班级名称", comboField = "name", targetAttr = "name")
    private Clbum clbum;
    @Excel(name = "年级", comboField = "name", targetAttr = "name")
    private Grades grades;
    @Excel(name = "专业", comboField = "name", targetAttr = "name")
    private Major major;
    /**
     * 系部信息
     */
    @Excel(name = "院系名称", comboField = "name", targetAttr = "name")
    private Department department;

    @Excel(name = "成绩", comboField = "stuScore", targetAttr = "stuScore")
    private UserExam userExam;


    private String[] courseIds;
}
