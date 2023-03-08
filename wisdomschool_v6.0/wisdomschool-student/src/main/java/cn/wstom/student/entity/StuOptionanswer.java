package cn.wstom.student.entity;



import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生考试答案表 tk_stu_optionanswer
 *
 * @author hzh
 * @date 20190304
 */
@Data
public class StuOptionanswer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建人Id
     */
    private Integer createId;
    /**
     * 更新人Id
     */
    private Integer updateId;
    /**
     * 题目分数
     */
    private Integer questionScore;
    /**
     * 学生答案
     */
    private String stuAnswer;
    /**
     * 试卷答案
     */
    private String testpaperOptionanswer;

    /**
     * 节Id
     */
    private Integer chapterId;
    /**
     * 试卷Id
     */
    private Integer paperId;
    /**
     * 答案
     */
    private String stoption;
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("questionScore", getQuestionScore())
                .append("stuAnswer", getStuAnswer())
                .append("testpaperOptionanswer", getTestpaperOptionanswer())
                .append("chapterId", getChapterId())
                .append("paperId", getPaperId())
                .append("stoption", getStoption())

                .toString();
    }
}
