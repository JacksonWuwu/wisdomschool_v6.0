package cn.wstom.common.web.exception.user;

/**
 * 验证码错误异常类
 *
 * @author dws
 */
public class CaptchaException extends UserException {
    private static final long serialVersionUID = 1L;

    public CaptchaException() {
        super("sysUser.jcaptcha.error", null);
    }
}
