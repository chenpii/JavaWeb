package com.kuang.filter;

import com.kuang.pojo.User;
import com.kuang.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenpi
 * @create 2022-07-25 20:57
 */
public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;

        //过滤器从Session中获取用户
        User user = (User) httpServletRequest.getSession().getAttribute(Constants.USER_SESSION);

        if (user != null) {
            filterChain.doFilter(req, resp);
        } else {//已被移除或注销、或未登录
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/error.jsp");
        }
    }

    public void destroy() {

    }
}
