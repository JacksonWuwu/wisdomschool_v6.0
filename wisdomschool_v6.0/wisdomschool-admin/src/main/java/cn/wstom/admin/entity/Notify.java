package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;

/**
 * 通知
 *
 * @author langhsu on 2015/8/31.
 */
@Data
public class Notify extends BaseEntity {
    private static final long serialVersionUID = 526185621820198579L;

    /**
     * 事件源用户id
     */
    private String sourceUserId;

    /**
     * 目标用户id
     */
    private String targetUserId;

    /**
     * 事件类型
     * 0：文章通知事件
     * 1：回答通知事件
     * 2：课程通知事件
     * 3：系统通知事件
     */
    private Integer eventType;

    /**
     * 事件目标id
     */
    private String targetId;

    /**
     * 阅读状态
     */
    private Integer status;

}
