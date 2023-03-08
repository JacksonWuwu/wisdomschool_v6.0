package cn.wstom.student.entity;



import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 年级实体 tb_grades
 * @author dws
 * @date 2019/01/02
 */
@Data
public class Grades extends BaseEntity {

    private static final long serialVersionUID = 4927917289676558983L;

    /**
     * 年级名称
     */
    private String name;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("id", id)
                .toString();
    }
}
