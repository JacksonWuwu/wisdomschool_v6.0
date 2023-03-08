package cn.wstom.common.web.exception.user;

/**
 * 用户不存在异常类
 *
 * @author dws
 */
public class UserNotExistsException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException() {
        super("sysUser.not.exists", null);
    }
}
