package com.kuang.servlet;

import com.kuang.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenpi
 * @create 2022-07-21 19:26
 */
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //一般不建议销毁session，而是移除session中的Attribute
        Object user_session = req.getSession().getAttribute(Constant.USER_SESSION);
        if (user_session != null) {
            req.getSession().removeAttribute(Constant.USER_SESSION);
            resp.sendRedirect("/Login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
