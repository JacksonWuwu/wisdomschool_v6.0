package cn.wstom.exam.entity;



/**
 * 分数 实体
 */
public class StuQuestionScore extends BaseEntity {
    /**
     * 创建人ID
     */
    private String  createId;
    /**
     * 更新人ID
     */
    private String  updateId;
    /**
     *得分
     */
    private String questionScore;
    /**
     * 试卷题目
     */
    private String testPaperQuestion;
    /**
     * 学生ID
     */
    private String stuId;
    /**
     * 章节ID
     */
    private String chapterId;

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

    public String getQuestionScore() {
        return questionScore;
    }

    public void setQuestionScore(String questionScore) {
        this.questionScore = questionScore;
    }

    public String getTestPaperQuestion() {
        return testPaperQuestion;
    }

    public void setTestPaperQuestion(String testPaperQuestion) {
        this.testPaperQuestion = testPaperQuestion;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        return "StuQuestionScore{" +
                "createId='" + createId + '\'' +
                ", updateId='" + updateId + '\'' +
                ", questionScore='" + questionScore + '\'' +
                ", testPaperQuestion='" + testPaperQuestion + '\'' +
                ", stuId='" + stuId + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", id='" + id + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", version=" + version +
                '}';
    }
}
