package com.kuang.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenpi
 * @create 2022-06-30 16:24
 */
public class HelloServlet extends HttpServlet {

    //由于get或者post只是请求实现的不同的方式，可以互相调用，业务逻辑都一样

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入了doGet");
        PrintWriter writer = resp.getWriter();//响应流
        writer.print("Hello,Servlet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
