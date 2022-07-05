package com.kuang.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author chenpi
 * @create 2022-07-05 14:05
 */
public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.要下载文件的路径
//        String filePath = this.getServletContext().getRealPath("/WEB-INF/classes/1.png");
        //文件名中文
        String filePath = this.getServletContext().getRealPath("/WEB-INF/classes/猫.png");
        System.out.println("下载文件的路径：" + filePath);
        //2.下载的文件名是啥？
        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);
        //3.设置想办法让浏览器能够支持(Content-disposition)下载我们需要的东西,中文文件名使用URLEncoder.encode进行编码,否则有可能乱码
        //web下载文件头
        resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        //4.获取下载文件的输入流
        FileInputStream is = new FileInputStream(filePath);
        //5.创建缓冲区
        int len;
        byte buff[] = new byte[1024];
        //6.获取OutputStream对象
        ServletOutputStream os = resp.getOutputStream();
        //7.将FileOutputStream流写入到bufer缓冲区
        //8.使用OutputStream将缓冲区中的数据输出到客户端！
        while ((len = is.read(buff)) != -1) {
            os.write(buff, 0, len);
        }
        //9.关闭流
        is.close();
        os.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
