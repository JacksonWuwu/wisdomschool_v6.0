package cn.wstom.student.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 教职工实体
 * @author dws
 * @date 2019/01/02
 */
@Data
public class Teacher extends BaseEntity {

    private static final long serialVersionUID = -4989759857194731384L;
    /**
     * 在职修读学历
     */
    private String nowSchoolDegree;

    /**
     * 专业技术职称获取时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date majorDegreeGetTime;

    /**
     * 加入党派时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date joinPartyTime;

    /**
     * 任教年限
     */
    private Integer teachYear;

    /**
     * 教师资格种类
     * 1：幼儿园教师资格；
     * 2：小学教师资格；
     * 3：初级中学教师和初级职业学校文化课、专业课教师资格；
     * 4：高级中学教师资格；
     * 5：中等专业学校、技工学校、职业高级中学文化课、专业课教师资格；
     * 6：中等专业学校、技工学校、职业高级中学实习指导教师资格；
     * 7：高等学校教师资格。
     * 8：成人/大学教育的教师资格，按照成人教育的层次，依照上款规定确定类别
     */
    private String teacherQualifyType;

    /**
     * 取得教师资格证时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date getQualifyTime;

    /**
     * 身份
     */
    private String identity;

    /**
     * 第一学历
     */
    private String firstEducation;

    /**
     * 第一学历毕业院校
     */
    private String firstGraduateSchool;

    /**
     * 第一学历所学专业
     */
    private String firstMajor;

    /**
     * 第一学历毕业时间
     */
    private String firstGraduateTime;

    /**
     * 最高学位
     */
    private String hightstDegree;

    /**
     * 最高学历毕业时间
     */
    private String hightGraduateTime;

    /**
     * 入职时间
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date workDate;

    /**
     * 婚姻状况
     */
    private String marryStatus;

    /**
     * 学历
     */
    private String education;

    /**
     * 职称
     */
    private String title;

    /**
     * 毕业院校
     */
    private String graduateInstitution;

    /**
     * 专业id
     */
    private String mid;

    /**
     * 职务
     */
    private String duty;

    /**
     * 编制
     */
    private String edited;

    /**
     * 在职状态 1：在职，2：离职
     */
    private Integer onJobStatus;

    /**
     * 离职日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date dimissionDate;

    /**
     * 离职类型
     */
    private String dimissionType;

    /**
     * 离职原因
     */
    private String dimissionReason;

    /**
     * 学习经历
     */
    private String learningExperience;

    /**
     * 工作经历
     */
    private String workExperience;

    /**
     * 培训经历
     */
    private String trainingExperience;

    /**
     * 专业证书
     */
    private String professionalCertificate;

    /**
     * 奖惩情况
     */
    private String punishSituation;

    /**
     * 建档情况 0：未建，1：已建
     */
    private Integer createArchive;

    /**
     *其他部门
     */
    private String otherDept;

    /**
     *岗位id序列
     */
    private String jobIds;

    /**
     * 其他岗位名称
     */
    private String otherJobName;

    private List<Course> courses;
    /*
     * 系部id
     * */
    private int dept_id;


    private String[] courseIds;


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("nowSchoolDegree", nowSchoolDegree)
                .append("majorDegreeGetTime", majorDegreeGetTime)
                .append("joinPartyTime", joinPartyTime)
                .append("teachYear", teachYear)
                .append("teacherQualifyType", teacherQualifyType)
                .append("getQualifyTime", getQualifyTime)
                .append("identity", identity)
                .append("firstEducation", firstEducation)
                .append("firstGraduateSchool", firstGraduateSchool)
                .append("firstMajor", firstMajor)
                .append("firstGraduateTime", firstGraduateTime)
                .append("hightstDegree", hightstDegree)
                .append("hightGraduateTime", hightGraduateTime)
                .append("workDate", workDate)
                .append("marryStatus", marryStatus)
                .append("education", education)
                .append("title", title)
                .append("graduateInstitution", graduateInstitution)
                .append("mid", mid)
                .append("dept_id", dept_id)
                .append("duty", duty)
                .append("edited", edited)
                .append("onJobStatus", onJobStatus)
                .append("dimissionDate", dimissionDate)
                .append("dimissionType", dimissionType)
                .append("dimissionReason", dimissionReason)
                .append("learningExperience", learningExperience)
                .append("workExperience", workExperience)
                .append("trainingExperience", trainingExperience)
                .append("professionalCertificate", professionalCertificate)
                .append("punishSituation", punishSituation)
                .append("createArchive", createArchive)
                .append("otherDept", otherDept)
                .append("jobIds", jobIds)
                .append("otherJobName", otherJobName)
                .append("id", id)
                .toString();
    }
}
