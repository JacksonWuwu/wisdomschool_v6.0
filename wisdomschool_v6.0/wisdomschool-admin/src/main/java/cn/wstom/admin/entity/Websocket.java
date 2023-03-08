package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* 存放考试的临时表 tk_websocket
*
* @author hzh
* @date 20190308
*/
@Data
public class Websocket extends BaseEntity {
private static final long serialVersionUID = 1L;

    /** 名字 */
    private String name;
    /** 试卷或者章节的ID */
    private String tcId;
    /** 考生ID */
    private String stuId;
    /** 考生学号 */
    private String stuNum;

@Override
public String toString() {
return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
    .append("name", getName())
    .append("tcId", getTcId())
    .append("stuId", getStuId())
    .append("stuNum", getStuNum())
.toString();
}
}
