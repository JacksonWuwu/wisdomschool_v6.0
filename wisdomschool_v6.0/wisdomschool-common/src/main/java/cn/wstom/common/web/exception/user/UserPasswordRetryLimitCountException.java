package cn.wstom.common.web.exception.user;

/**
 * 用户错误记数异常类
 *
 * @author dws
 */
public class UserPasswordRetryLimitCountException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount) {
        super("sysUser.password.retry.limit.count", new Object[]{retryLimitCount});
    }
}
