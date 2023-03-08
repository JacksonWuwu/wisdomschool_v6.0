package cn.wstom.exam.entity.Individual;

import java.io.Serializable;

/**
 * 每种题型的属性
 *
 * @author hzh
 */
public class QuestionType implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7983779572747755052L;
    /**
     * 题型的id 属于哪种类型题 比如选择题还是填空题
     */
    private String tid;

    /**
     * 每种题型的数量
     */
    private int number;

    /**
     * 每种题型的分数
     */
    private int score;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
