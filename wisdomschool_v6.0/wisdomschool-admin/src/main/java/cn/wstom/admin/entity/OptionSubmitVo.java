package cn.wstom.admin.entity;

import java.io.Serializable;

/**
 * @author liniec
 * @date 2020/01/22 21:28
 * 选项记录表
 */
public class OptionSubmitVo implements Serializable {

    private String uUid;
    //  学生选项Id
    private String stuOptionanswerId;
    //  题目Id
    private String testPaperQuestionId;
    //  试卷Id
    private String testPaperId;
    //  选项答案（option）多选单选
    private String stuOptionAnswer;
    //  文字答案
    private String stuAnswer;
    //  题目类型
    private String answerType;
    //  题目分数
    private Integer score;
    //  是否正确
    private Boolean isTrue;

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }

    public Integer getScore() {
        return score;
    }

    public String getStuOptionanswerId() {
        return stuOptionanswerId;
    }

    public void setStuOptionanswerId(String stuOptionanswerId) {
        this.stuOptionanswerId = stuOptionanswerId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getAnswerType() {
        return answerType;
    }

    public String getuUid() {
        return uUid;
    }

    public void setuUid(String uUid) {
        this.uUid = uUid;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getTestPaperQuestionId() {
        return testPaperQuestionId;
    }

    public void setTestPaperQuestionId(String testPaperQuestionId) {
        this.testPaperQuestionId = testPaperQuestionId;
    }

    public String getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(String testPaperId) {
        this.testPaperId = testPaperId;
    }

    public String getStuOptionAnswer() {
        return stuOptionAnswer;
    }

    public void setStuOptionAnswer(String stuOptionAnswer) {
        this.stuOptionAnswer = stuOptionAnswer;
    }

    public String getStuAnswer() {
        return stuAnswer;
    }

    public void setStuAnswer(String stuAnswer) {
        this.stuAnswer = stuAnswer;
    }

    @Override
    public String toString() {
        return "OptionSubmitVo{" +
                "testPaperQuestionId='" + testPaperQuestionId + '\'' +
                ", testPaperId='" + testPaperId + '\'' +
                ", stuOptionAnswer='" + stuOptionAnswer + '\'' +
                ", stuAnswer='" + stuAnswer + '\'' +
                ", answerType='" + answerType + '\'' +
                '}';
    }
}
