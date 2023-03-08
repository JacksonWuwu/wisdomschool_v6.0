package cn.wstom.student.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 试卷答案表 tk_testpaper_optinanswer
 *
 * @author hzh
 * @date 20190223
 */
@Data
public class TestpaperOptinanswer extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
     * 题目分数
     */
    private Integer questionScore;
    /**
     * 答案
     */
    private String stanswer;
    /**
     * 选项
     */
    private String stoption;

    /**
     * 学生答案
     */
    private String stuAns;


    /**
     * 题目外键
     */
    private String questionId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("schoolNo", getSchoolNo())
                .append("questionScore", getQuestionScore())
                .append("stanswer", getStanswer())
                .append("stoption", getStoption())
                .append("stuAns", getStuAns())
                .toString();
    }
}
