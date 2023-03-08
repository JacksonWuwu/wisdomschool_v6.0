package cn.wstom.common.web.exception.user;

/**
 * 用户锁定异常类
 *
 * @author dws
 */
public class UserBlockedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserBlockedException(String reason) {
        super("sysUser.blocked", new Object[]{reason});
    }
}
