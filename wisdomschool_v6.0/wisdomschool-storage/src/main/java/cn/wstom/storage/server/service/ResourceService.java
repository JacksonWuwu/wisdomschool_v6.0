package cn.wstom.storage.server.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dws
 */
public interface ResourceService {

    /**
     * 以严格的HTTP响应格式返回在线资源流，适用于多数浏览器
     *
     * @param request
     * @param response
     */
    void getResource(HttpServletRequest request, HttpServletResponse response);

}
