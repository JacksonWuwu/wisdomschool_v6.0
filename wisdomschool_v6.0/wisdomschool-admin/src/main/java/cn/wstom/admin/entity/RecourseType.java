package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Data;

/**
 * 为避免引起歧义，应改为recourseCategory
 *
 * @author dws
 * @date 2019/02/22
 */
@Data
public class RecourseType extends BaseEntity {
    private static final long serialVersionUID = 6721039349742298409L;

    /**
     * 资源类型名称
     */
    private String name;

    /**
     * 教师课程
     */
    private String tcId;

    /**
     * 排序
     */
    private String order;
    /**
     * 备注
     */
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
