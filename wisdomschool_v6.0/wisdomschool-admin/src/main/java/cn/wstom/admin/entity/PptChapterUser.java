package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

@Data
public class PptChapterUser  extends BaseEntity {
    private static final long serialVersionUID = 1L;


    private Integer userId;
    /**  */
    private String resourceChapterId;


    private Integer cout;

    private String tcid;

    List<String> crids;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("resourceChapterId", getResourceChapterId())
                .append("cout", getCout())
                .append("tcid", getTcid())
                .toString();
    }
}
