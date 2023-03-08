package cn.wstom.student.entity;



import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 系别部门实体 tb_department
 * @author dws
 * @date 2019/01/02
 */
@Data
public class Department extends BaseEntity {

    private static final long serialVersionUID = 5692189794446110501L;

    /**
     * 系部名称
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
