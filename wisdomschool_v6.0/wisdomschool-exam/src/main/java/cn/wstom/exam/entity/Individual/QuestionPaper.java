package cn.wstom.exam.entity.Individual;

import java.io.Serializable;
import java.util.List;

/**
 * 试卷Vo
 */
public class QuestionPaper implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 试题id
     */
    private String tid;
    /**
     * 题目
     */
    private String title;
    /**
     * 困难
     */
    private String difficulty;
    /**
     * 题型
     */
    private String titleType;
    /**
     * 试题已曝光数
     */
    private String qexposed;
    /**
     * 选项
     */
    private List<String> optlist;
    /**
     * 题型编号
     */
    private String number;
    /**
     * 题型分值
     */
    private int score;
    private String testName;

    private String testpaperId;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }

    public String getQexposed() {
        return qexposed;
    }

    public void setQexposed(String qexposed) {
        this.qexposed = qexposed;
    }

    public List<String> getOptlist() {
        return optlist;
    }

    public void setOptlist(List<String> optlist) {
        this.optlist = optlist;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestpaperId() {
        return testpaperId;
    }

    public void setTestpaperId(String testpaperId) {
        this.testpaperId = testpaperId;
    }
}
