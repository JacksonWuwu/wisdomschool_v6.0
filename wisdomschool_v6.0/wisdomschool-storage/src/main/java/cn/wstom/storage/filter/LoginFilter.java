package cn.wstom.storage.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author dws
 */
@WebFilter
@Order(1)
public class LoginFilter implements Filter {
    private static final String PROFILE_URL_PREFIX = "http://10.20.55.121:8089";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //存储服务必须登陆，没登陆跳转至应用登陆控制
        /*
        暂不做校验
        System.out.println(request.getParameter("user"));
        SysUser user = null;
        System.out.println(user);
        if (user == null || user.getId() == null || user.getUserName() == null) {
            ((HttpServletResponse) response).sendRedirect(PROFILE_URL_PREFIX + "/login");
        } else {
            chain.doFilter(request, response);
        }
        */
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
