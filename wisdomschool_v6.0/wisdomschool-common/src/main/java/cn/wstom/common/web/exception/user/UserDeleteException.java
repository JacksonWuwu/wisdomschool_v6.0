package cn.wstom.common.web.exception.user;

/**
 * 用户账号已被删除
 *
 * @author dws
 */
public class UserDeleteException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserDeleteException() {
        super("sysUser.password.delete", null);
    }
}
