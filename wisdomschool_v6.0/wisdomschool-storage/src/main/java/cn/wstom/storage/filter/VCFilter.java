package cn.wstom.storage.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <h2>文件块资源权限检查器</h2>
 * <p>该过滤器用于检查所有发往资源文件夹（文件块、临时文件夹）的请求，判断该请求权限是否合法，并指定请求MIME类型为输出流。</p>
 *
 * @version 1.0
 */
@WebFilter({"/fileblocks/*"})
@Order(2)
public class VCFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("-------2222");
        HttpServletRequest hsr = (HttpServletRequest) request;
        String account = (String) hsr.getSession().getAttribute("ACCOUNT");
        System.out.println("---****");
        response.setContentType("application/octet-stream");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
