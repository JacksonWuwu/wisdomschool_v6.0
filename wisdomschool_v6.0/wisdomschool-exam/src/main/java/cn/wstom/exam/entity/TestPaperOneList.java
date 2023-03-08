package cn.wstom.exam.entity;


import lombok.Data;

@Data
public class TestPaperOneList extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 创建人Id
     */
    private String createId;
    /**
     * 更新人Id
     */
    private String updateId;
    /**
     * 学校No
     */
    private String schoolNo;
    /**
     * 试卷名
     */
    private String testName;
    private String headline;
    /**
     * 状态 0：已组 1：未组
     */
    private String state;
    /**
     * 备注
     */
    private String beiZhu;
    /**
     * 课程ID
     */
    private String coursrId;
    /**
     * 试卷类型
     */
    private String type;
    /**
     * 组卷规则 1：随机 0：固定
     */
    private String rule;
    /**
     * 年份
     */
    private String testYear;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTestYear() {
        return testYear;
    }

    public void setTestYear(String testYear) {
        this.testYear = testYear;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBeiZhu() {
        return beiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        this.beiZhu = beiZhu;
    }

    public String getCoursrId() {
        return coursrId;
    }

    public void setCoursrId(String coursrId) {
        this.coursrId = coursrId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
