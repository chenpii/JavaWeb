package com.kuang.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author chenpi
 * @create 2022-07-04 16:13
 */
public class ServletDemo04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了ServletDemo04");
        ServletContext context = this.getServletContext();
        //请求转发
//        RequestDispatcher dispatcher = context.getRequestDispatcher("/gp");//转发的请求路径：转发到/gp
//        dispatcher.forward(req, resp);//调用forward实现请求转发
        //合并成一行
        context.getRequestDispatcher("/gp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
