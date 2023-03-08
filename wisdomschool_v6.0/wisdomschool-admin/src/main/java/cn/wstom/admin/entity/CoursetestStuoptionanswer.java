package cn.wstom.admin.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wstom.admin.entity.BaseEntity;
/**
 * 学生答案（课程测试）表 tk_coursetest_stuoptionanswer
 *
 * @author hzh
 * @date 20190315
 */
@Data
public class CoursetestStuoptionanswer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建人Id
     */
    private Integer createId;
    /**
     * 更新人ID
     */
    private Integer updateId;
    /**
     * 题目分数
     */
    private Integer questionscore;
    /**
     * 学生答案
     */
    private String stuanswer;
    /**
     * 题目答案
     */
    private String testPaperOptionAnswer;
    /**
     * 答案内容
     */
    private String stoption;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("createId", getCreateId())
                .append("updateId", getUpdateId())
                .append("questionscore", getQuestionscore())
                .append("stuanswer", getStuanswer())
                .append("testPaperOptionAnswer", getTestPaperOptionAnswer())
                .append("stoption", getStoption())
                .toString();
    }
}
