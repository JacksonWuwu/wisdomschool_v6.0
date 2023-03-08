package cn.wstom.exam.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 试卷做的题目答案表 tk_testpaper_testquestions
 *
 * @author hzh
 * @date 20190223
 */
@Data
public class TestpaperTestquestions extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建人Id
     */
    private String createId;
    /**
     * 更新Id
     */
    private String updateId;
    /**
     * 学校No
     */
    private String schoolNo;
    /**
     * 试卷Id
     */
    private String testPaperId;
    /**
     * 题目Id
     */
    private String testQuestionsId;

    /**
     * 考试系统Id
     */
    private String testPaperOneId;
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("schoolNo", getSchoolNo())
                .append("testPaperId", getTestPaperId())
                .append("testQuestionsId", getTestQuestionsId())
                .append("testPaperOneId", getTestQuestionsId())
                .toString();
    }
}
