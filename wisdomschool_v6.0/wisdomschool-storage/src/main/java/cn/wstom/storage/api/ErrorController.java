package cn.wstom.storage.api;

import cn.wstom.storage.server.mapper.NodeMapper;
import cn.wstom.storage.server.util.FileBlockUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 错误控制层
 *
 * @author dws
 */
@ControllerAdvice
@RequestMapping({"/errorController"})
public class ErrorController {
    private final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @Autowired
    private NodeMapper nodeMapper;

    @RequestMapping({"/pageNotFound.do"})
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        return response.encodeURL("/prv/error.html");
    }

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e) {
        // TODO: 2019/2/23 记录日志
        e.printStackTrace();
        FileBlockUtil.checkFileBlocks(nodeMapper);
        LOGGER.error("\u5904\u7406\u8bf7\u6c42\u65f6\u53d1\u751f\u9519\u8bef\uff1a\n\r------\u4fe1\u606f------\n\r"
                + e.getMessage() + "\n\r------\u4fe1\u606f------");
    }
}
