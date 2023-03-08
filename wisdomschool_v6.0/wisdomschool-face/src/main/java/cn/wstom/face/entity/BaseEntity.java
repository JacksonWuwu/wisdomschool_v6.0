package cn.wstom.face.entity;


import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 基础Entity
 *
 * 实体基类：字段驼峰命名方式，对应经典数据库列名（即下划线分隔）
 *  如：字段createTime对应数据库表列名create_time
 * @author dws
 * @date 2018/11/23
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -318831055784246157L;


    protected String id;

    /**
     * 创建者
     */
    protected String createBy;

    /**
     * 创建时间
     */

    protected Date createTime;

    /**
     * 更新者
     */
    protected String updateBy;

    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    protected Date updateTime;

    /**
     * 备注
     */

    protected String remark;

    /** 请求参数 */
    private Map<String, Object> params;

    /**
     * 乐观锁
     */
    protected Integer version;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
