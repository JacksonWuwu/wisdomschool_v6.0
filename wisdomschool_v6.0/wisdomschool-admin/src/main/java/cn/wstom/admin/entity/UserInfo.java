package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dws
 * @date 2019/03/28
 */
@Getter
@Setter
@ToString
public class UserInfo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String userHead;
    /**
     * 消息
     */
    private String message;
    /**
     * 状态
     */
    private int status;
}
