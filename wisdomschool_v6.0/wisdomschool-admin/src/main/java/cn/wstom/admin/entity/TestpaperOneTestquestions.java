package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 试卷做的题目答案表 tk_testpaperone_testquestions
 *
 * @author hzh
 * @date 20190223
 */
@Data
public class TestpaperOneTestquestions extends BaseEntity {
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
    private String testPaperOneId;
    /**
     * 考试Id
     */
    private String testPaperOneListId;
    /**
     * 作业Id
     */
    private String testPaperWorkId;
    /**
     * 作业库Id
     */
    private String testPaperWorkListId;
    /**
     * 题目Id
     */
    private String testQuestionsId;



    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("schoolNo", getSchoolNo())
                .append("testQuestionsId", getTestQuestionsId())
                .append("testPaperOneId", getTestPaperOneId())
                .append("testPaperOneListId", getTestPaperOneListId())
                .append("testPaperWorkId", getTestPaperWorkId())
                .append("testPaperWorkListId", getTestPaperWorkListId())
                .toString();
    }
}
