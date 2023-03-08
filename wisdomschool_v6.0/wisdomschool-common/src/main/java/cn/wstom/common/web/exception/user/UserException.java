package cn.wstom.common.web.exception.user;


import cn.wstom.common.web.exception.base.BaseException;


/**
 * 用户信息异常类
 *
 * @author dws
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
