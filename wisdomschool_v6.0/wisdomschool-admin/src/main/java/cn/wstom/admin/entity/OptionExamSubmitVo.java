package cn.wstom.admin.entity;

import java.io.Serializable;

/**
 * @author lzj
 * @date 2020/06/08 21:28
 * 考试选项记录表
 */
public class OptionExamSubmitVo implements Serializable {

    private String uUid;
    //  学生选项Id
    private String stuOptionExamanswerId;
    //  题目Id
    private String testPaperQuestionId;
    //  试卷Id
    private String testPaperOneId;
    //  选项答案（option）多选单选
    private String stuOptionAnswer;
    //  文字答案
    private String stuAnswer;
    //  题目类型
    private String answerType;
    private String paperId;
    private String studentId;
    private String tutId;
    //  题目分数
    private Integer score;
    //  是否正确
    private Boolean isTrue;
    //当前时间戳
    private Long nowDate;

    public Long getNowDate() {
        return nowDate;
    }

    public void setNowDate(Long nowDate) {
        this.nowDate = nowDate;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTutId() {
        return tutId;
    }

    public void setTutId(String tutId) {
        this.tutId = tutId;
    }

    public Boolean getTrue() {
        return isTrue;
    }

    public void setTrue(Boolean aTrue) {
        isTrue = aTrue;
    }

    public Integer getScore() {
        return score;
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

    public String getTestPaperOneId() {
        return testPaperOneId;
    }

    public void setTestPaperOneId(String testPaperOneId) {
        this.testPaperOneId = testPaperOneId;
    }

    public String getStuOptionAnswer() {
        return stuOptionAnswer;
    }

    public void setStuOptionAnswer(String stuOptionAnswer) {
        this.stuOptionAnswer = stuOptionAnswer;
    }

    public String getStuOptionExamanswerId() {
        return stuOptionExamanswerId;
    }

    public void setStuOptionExamanswerId(String stuOptionExamanswerId) {
        this.stuOptionExamanswerId = stuOptionExamanswerId;
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
                ", testPaperOneId='" + testPaperOneId + '\'' +
                ", stuOptionAnswer='" + stuOptionAnswer + '\'' +
                ", stuAnswer='" + stuAnswer + '\'' +
                ", answerType='" + answerType + '\'' +
                ", stuOptionExamanswerId='" + stuOptionExamanswerId + '\'' +
                ", score='" + score + '\'' +
                '}';
    }

    public String getStuOptionanswerId() {
        return stuOptionExamanswerId;
    }
}
