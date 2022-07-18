package com.kuang.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author chenpi
 * @create 2022-07-18 11:04
 */
public class CharacterEncodingFilter implements Filter {
    //初始化 ：web服务器启动，就已经初始化了，随时等待过滤对象出现
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharacterEncodingFilter初始化");
    }

    /**
     * filterChain :链
     * 1.过滤器中的所有代码，在过滤特定请求的时候都会执行
     * 2.必须要让过滤器继续通行 filterChain.doFilter(servletRequest, servletResponse);
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=utf-8");

        System.out.println("CharacterEncodingFilter执行前....");
        filterChain.doFilter(servletRequest, servletResponse);//让请求继续走，如果不写，程序到这里就拦截停止！
        System.out.println("CharacterEncodingFilter执行后....");
    }

    //销毁 ：web服务器关闭的时候，过滤器会销毁
    @Override
    public void destroy() {
        System.out.println("CharacterEncodingFilter销毁");

    }
}
