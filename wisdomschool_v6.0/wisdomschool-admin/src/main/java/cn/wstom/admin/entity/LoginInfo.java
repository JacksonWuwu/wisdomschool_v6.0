package cn.wstom.admin.entity;

import lombok.Data;

/**
 * @author dws
 * @date 2019/02/12
 */
@Data
public class LoginInfo {

    private String username;

    private char[] password;

    private boolean rememberMe;

    private String time;
}
