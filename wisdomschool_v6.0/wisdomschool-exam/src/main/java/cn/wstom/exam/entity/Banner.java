package cn.wstom.exam.entity;


import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* 轮播图片表 tb_banner
*
* @author dws
* @date 20200131
*/
@Data
public class Banner extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 轮播页标题 */
    private String title;
    /** 图片地址 */
    private String cover;
    /** 外部链接 */
    private String url;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("title", getTitle())
                .append("cover", getCover())
                .append("url", getUrl())
                .toString();
    }
}
