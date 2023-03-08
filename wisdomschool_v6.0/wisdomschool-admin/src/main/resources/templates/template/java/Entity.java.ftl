package ${package}.domain;

import cn.wstom.common.base.entity.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import lombok.Data;

/**
* ${tableComment}表 ${tableName}
*
* @author ${author}
* @date ${datetime}
*/
@Data
public class ${className} extends BaseEntity {
private static final long serialVersionUID = 1L;

<#list columns as column >
    /** ${column.columnComment} */
    private ${column.attrType} ${column.attrname};
</#list>

@Override
public String toString() {
return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
<#list columns as column>
    .append("${column.attrname}", get${column.attrName}())
</#list>
.toString();
}
}
