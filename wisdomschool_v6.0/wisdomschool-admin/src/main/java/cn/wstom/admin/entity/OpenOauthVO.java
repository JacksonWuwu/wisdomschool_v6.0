package cn.wstom.admin.entity;

import lombok.Data;

/**
 * @ClassName OpenOauthVO
 * @Description 授权第三方登陆
 * @Author dws
 * @Date 2018/9/1
 */
@Data
public class OpenOauthVO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 认证类型：QQ、微信、新浪
     */
    private Integer oauthType;

    /**
     * 第三用户id
     */
    private String oauthUserId;

    /**
     * 第三方返回的code
     */
    private String oauthCode;

    /**
     * 访问令牌
     */
    private String accessToken;

    private String expireIn;

    private String refreshToken;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

}
