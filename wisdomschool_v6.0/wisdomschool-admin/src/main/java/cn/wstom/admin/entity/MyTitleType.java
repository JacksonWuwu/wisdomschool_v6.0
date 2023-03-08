package cn.wstom.admin.entity;

import cn.wstom.common.annotation.Excel;
import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
* 我的题型表 tk_my_title_type
*
* @author lrs
* @date 20190307
*/
@Data
public class MyTitleType extends BaseEntity {
private static final long serialVersionUID = 1L;

    /** 教师Id */
    private String createId;

    /** 更新人Id */
    private String updateId;

    /** 平台题型ID */
    private String publicTitleId;

    /** 题型名 */
    @Excel(name = "题型")
    private String titleTypeName;

    /** 课程ID */
    private String cid;

    /** 课程名称 */
    private String cname;

    /** 平台类型名 */
    private String publicTitleName;
    /** 平台类型模板 */
    private String templateNum;

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getPublicTitleId() {
        return publicTitleId;
    }

    public void setPublicTitleId(String publicTitleId) {
        this.publicTitleId = publicTitleId;
    }

    public String getTitleTypeName() {
        return titleTypeName;
    }

    public void setTitleTypeName(String titleTypeName) {
        this.titleTypeName = titleTypeName;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPublicTitleName() {
        return publicTitleName;
    }

    public void setPublicTitleName(String publicTitleName) {
        this.publicTitleName = publicTitleName;
    }

    public String getTemplateNum() {
        return templateNum;
    }

    public void setTemplateNum(String templateNum) {
        this.templateNum = templateNum;
    }

    @Override
public String toString() {
return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
    .append("createId", getCreateId())
    .append("updateId", getUpdateId())
    .append("publicTitleId", getPublicTitleId())
    .append("titleTypeName", getTitleTypeName())
    .append("cid", getCid())
    .append("templateNum", getTemplateNum())
.toString();
}
}
