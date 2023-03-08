package cn.wstom.admin.entity;

import cn.wstom.admin.entity.Notify;

import cn.wstom.admin.entity.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author langhsu on 2015/8/31.
 */
@ToString
@Getter
@Setter
public class NotifyVO extends Notify {
    private static final long serialVersionUID = 7595597442869431988L;
    /**
     * 源用户
     */
    private SysUser sourceUser;
    /**
     * 目标用户
     */
    private SysUser targetUser;
}
