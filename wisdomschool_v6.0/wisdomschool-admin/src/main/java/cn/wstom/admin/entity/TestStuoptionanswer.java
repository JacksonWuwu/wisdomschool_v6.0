package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;

/**
* 学生答案（节测试）表 tk_test_stuoptionanswer
*
* @author hzh
* @date 20190308
*/
@Data
public class TestStuoptionanswer extends BaseEntity {
private static final long serialVersionUID = 1L;

    /** 创建人Id */
    private Integer createId;
    /** 更新人ID */
    private Integer updateId;
    /** 题目分数 */
    private Integer questionscore;
    /** 学生答案 */
    private String stuanswer;
    /** 题目答案 */
    private String testPaperOptionAnswer;
    /** 节ID */
    private Integer chapterId;
    /** 答案内容 */
    private String stoption;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Integer getQuestionscore() {
        return questionscore;
    }

    public void setQuestionscore(Integer questionscore) {
        this.questionscore = questionscore;
    }

    public String getStuanswer() {
        return stuanswer;
    }

    public void setStuanswer(String stuanswer) {
        this.stuanswer = stuanswer;
    }

    public String getTestPaperOptionAnswer() {
        return testPaperOptionAnswer;
    }

    public void setTestPaperOptionAnswer(String testPaperOptionAnswer) {
        this.testPaperOptionAnswer = testPaperOptionAnswer;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public String getStoption() {
        return stoption;
    }

    public void setStoption(String stoption) {
        this.stoption = stoption;
    }

    @Override
    public String toString() {
        return "TestStuoptionanswer{" +
                "createId=" + createId +
                ", updateId=" + updateId +
                ", questionscore=" + questionscore +
                ", stuanswer='" + stuanswer + '\'' +
                ", testPaperOptionAnswer='" + testPaperOptionAnswer + '\'' +
                ", chapterId=" + chapterId +
                ", stoption='" + stoption + '\'' +
                '}';
    }
}
