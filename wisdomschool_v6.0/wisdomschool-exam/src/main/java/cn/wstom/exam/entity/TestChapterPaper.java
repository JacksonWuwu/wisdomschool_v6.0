package cn.wstom.exam.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* 节的试卷表 tk_test_chapter_paper
*
* @author hzh
* @date 20190314
*/
@Data
public class TestChapterPaper extends BaseEntity {
private static final long serialVersionUID = 1L;

    /** 名字 */
    private String name;
    /** 试卷Id */
    private String paperId;
    /** 节id */
    private Integer cId;
    /**
     * 试卷标题
     */
    private String paperHeadline;
    /**
     * 试卷名
     */
    private String paperName;


@Override
public String toString() {
return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
    .append("name", getName())
    .append("paperId", getPaperId())
        .append("cId", getCId())
        .append("paperHeadline", getPaperHeadline())
        .append("paperName", getPaperName())
.toString();
}
}
