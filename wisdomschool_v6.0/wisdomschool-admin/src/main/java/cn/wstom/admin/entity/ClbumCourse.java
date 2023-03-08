package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 班级课程表 tb_clbum_course
 *
 * @author hyb
 * @date 20190218
 */
@Data
public class ClbumCourse extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 学校id
     */
    private String schoolId;
    /**
     * 教师课程id
     */
    private String tcid;
    /**
     * 班级id
     */
    private String cid;
    /**
     * 年级id
     */
    private String gid;
    /*
    * 年级名
    * */
    private String gname;
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("tcid", getTcid())
                .append("cid", getCid())
                .append("gid", getGid())
                .append("gname", getGname())
                .toString();
    }
}
