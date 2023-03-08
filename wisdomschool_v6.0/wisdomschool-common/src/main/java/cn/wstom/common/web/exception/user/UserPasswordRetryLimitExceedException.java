package cn.wstom.common.web.exception.user;

/**
 * 用户错误最大次数异常类
 *
 * @author dws
 */
public class UserPasswordRetryLimitExceedException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitExceedException(int retryLimitCount) {
        super("sysUser.password.retry.limit.exceed", new Object[]{retryLimitCount});
    }
}
