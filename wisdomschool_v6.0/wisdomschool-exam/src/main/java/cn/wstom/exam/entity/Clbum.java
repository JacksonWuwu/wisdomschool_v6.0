package cn.wstom.exam.entity;


import cn.wstom.exam.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 班级实体 tb_clbum
 * @author dws
 * @date 2019/01/02
 */
@Data
public class Clbum extends BaseEntity {
    private static final long serialVersionUID = -4609483955100995024L;

    /**
     * 班级名称
     */
    @Excel(name = "班级名称")
    private String name;

    /**
     * 专业id
     */
    private String mid;

    private String tid;
    private String tcid;
    private int schoolId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("mid", mid)
                .append("id", id)
                .append("tid", tid)
                .toString();
    }
}
