package cn.wstom.common.web.exception;

import cn.wstom.common.base.Data;
import cn.wstom.common.exception.DeleteException;
import cn.wstom.common.exception.DemoModeException;
//import cn.wstom.main.util.PermissionUtils;
//import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 自定义异常处理器
 *
 * @author dws
 */
@RestControllerAdvice
public class DefaultExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    /**
     * 权限校验失败
     */
    //@ExceptionHandler(AuthorizationException.class)
    //public Data handleAuthorizationException(AuthorizationException e) {
    //    log.error(e.getMessage(), e);
    //    return Data.error(PermissionUtils.getMsg(e.getMessage()));
    //}

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Data handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Data.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Data notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return Data.error("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public Data handleException(Exception e) {
        log.error(e.getMessage(), e);
        String msg = e.getMessage();
        if (msg == null) {
            msg = "服务器错误，请联系管理员";
        }
        return Data.error(msg);
    }

    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public Data demoModeException(DemoModeException e) {
        return Data.error("演示模式，不允许操作");
    }

    @ExceptionHandler(DeleteException.class)
    public Data deleteException(DeleteException e) {
        return Data.error("删除失败，可能存在关联数据无法删除");
    }
}
