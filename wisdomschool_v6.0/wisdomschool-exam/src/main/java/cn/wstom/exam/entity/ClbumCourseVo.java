package cn.wstom.exam.entity;




import cn.wstom.exam.annotation.Excel;
import lombok.Data;

/**
 * 班级课程Vo
 *
 * @author hyb
 * @date 2019/02/18
 */
@Data
public class ClbumCourseVo extends ClbumCourse {
    private static final long serialVersionUID = -4765542647341463846L;
    /**
     * 年级信息
     */
    @Excel(name = "年级", comboField = "name", targetAttr = "name")
    private Grades grades;
    /**
     * 系部信息
     */
    @Excel(name = "系部", comboField = "name", targetAttr = "name")
    private Department department;
    /**
     * 专业信息
     */
    @Excel(name = "专业", comboField = "name", targetAttr = "name")
    private Major major;
    /**
     * 班级信息
     */
    @Excel(name = "班级", comboField = "name", targetAttr = "name")
    private Clbum clbum;
    /**
     * 课程信息
     */
    @Excel(name = "课程", comboField = "name", targetAttr = "name")
    private Course course;
    /**
     * 教师课程信息
     */
//    @Excel(name = "教师课程", targetAttr = "id")
    private TeacherCourse teacherCourse;
    /**
     * 教师用户
     */
    private Teacher teacher;

    /*
    * 年级名
    * */
    private String gradesname;
    /**
     * 教师用户
     */
    @Excel(name = "教师名称", targetAttr = "userName")
    private SysUser sysUser;

    @Excel(name = "学校名称", targetAttr = "name")
    private School school;
    /*
     * 用于判断课程是否签到
     * */
    private Boolean aBoolean;











}
