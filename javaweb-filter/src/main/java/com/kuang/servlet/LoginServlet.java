package com.kuang.servlet;

import com.kuang.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenpi
 * @create 2022-07-21 19:10
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取前端请求的参数
        String username = req.getParameter("username");

        if (username.equals("admin")) {//登录成功
            req.getSession().setAttribute(Constant.USER_SESSION, req.getSession().getId());
            //重新向到成功页面
            resp.sendRedirect("/sys/success.jsp");
        } else {//登录失败
            resp.sendRedirect("/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
