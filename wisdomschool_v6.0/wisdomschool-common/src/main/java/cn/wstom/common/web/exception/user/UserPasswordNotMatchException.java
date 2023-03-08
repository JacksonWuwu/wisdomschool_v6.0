package cn.wstom.common.web.exception.user;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author dws
 */
public class UserPasswordNotMatchException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException() {
        super("sysUser.password.not.match", null);
    }
}
