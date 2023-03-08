package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 专业实体 tb_major
 *
 * @author xyl
 * @date 2019/01/13
 */
@Data
public class Major extends BaseEntity {
    private static final long serialVersionUID = 3543218008555894314L;

    /**
     * 专业名称
     */
    private String name;

    /**
     * 系部id
     */
    private String did;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("did", did)
                .append("id", id)
                .toString();
    }
}
