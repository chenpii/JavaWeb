package com.kuang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * @author chenpi
 * @create 2022-07-11 8:48
 */
public class SessionDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //得到Session
        HttpSession session = req.getSession();

        //给Session中存东西
        session.setAttribute("name", new Person("张三", 25));

        //获取Session的id
        String id = session.getId();

        //判断Session是不是新创建
        if (session.isNew()) {
            resp.getWriter().println("session创建成功，ID：" + id);
        } else {
            resp.getWriter().println("session已经在服务器中存在，ID：" + id);

        }

        //Session创建的时候做了什么事情：
//        Cookie cookie = new Cookie("JSESSIONID", id);
//        resp.addCookie(cookie);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
