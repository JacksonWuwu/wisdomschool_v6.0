package cn.wstom.exam.entity;




import cn.wstom.exam.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 学生实体 tb_student
 * @author dws
 * @date 2019/01/02
 */
@Data
public class Student extends BaseEntity {
    private static final long serialVersionUID = -9117240729751724939L;

    /**
     * 所处班级id
     */
    private String cid;

    /**
     * 所在年级id
     */
    private String gid;

    /**
     * 入学日期
     */
    @Excel(name = "入学日期")
    @JSONField(format = "yyyy-MM-dd")
    private Date enrollmentDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cid", cid)
                .append("gid", gid)
                .append("enrollmentDate", enrollmentDate)
                .append("id", id)
                .toString();
    }
}
