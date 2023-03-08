package cn.wstom.exam.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 课程章节表 tb_chapter
 *
 * @author dws
 * @date 20190223
 */
@Data
public class Chapter extends BaseEntity implements Comparable<Chapter> {
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;
    /**
     * 名称
     */
    private String name;
    /**
     * 父id
     */
    private String pid;
    /**
     * 课程id
     */
    private String cid;

    /**
     * 预习id
     */
    private String previewid;
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("title", getTitle())
                .append("name", getName())
                .append("pid", getPid())
                .append("cid", getCid())
                .append("previewid", getPreviewid())
                .toString();
    }

    @Override
    public int compareTo(Chapter c) {
        if (Integer.valueOf(this.pid) >= Integer.valueOf(c.getPid())) {
            return 1;
        }
        return -1;
    }
}
