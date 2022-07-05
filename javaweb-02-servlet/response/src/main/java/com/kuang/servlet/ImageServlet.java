package com.kuang.servlet;


import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author chenpi
 * @create 2022-07-05 15:34
 */
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如何让浏览器3s刷新一次？
        resp.setHeader("refresh", "3");

        //在内存中生成一个图片
        BufferedImage image = new BufferedImage(80, 20, BufferedImage.TYPE_INT_RGB);
        //得到图片
        Graphics2D g = (Graphics2D) image.getGraphics();//笔
        //设置图片的背景颜色
        g.setColor(Color.white);
        g.fillRect(0, 0, 80, 20);
        //给图片写数据
        g.setColor(Color.BLUE);
        g.setFont(new Font(null, Font.BOLD, 20));//设置字体
        g.drawString(makeNum(), 0, 20);

        //告诉浏览器，这个请求用图片的形式打开
        resp.setContentType("image/jpg");
        //网站存在缓存，不让浏览器缓存
        resp.setDateHeader("expires", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        //把图片写给浏览器
        ImageIO.write(image, "jpg", resp.getOutputStream());

    }

    //生成8位随机数
    private String makeNum() {
        Random random = new Random();
        String num = random.nextInt(99999999) + "";
        //不足8位数补0
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < (8 - num.length()); i++) {
            sb.append("0");
        }
        String s = sb.toString() + num;
        return s;
    }

    @Test
    public void test1() {
        String s = makeNum();
        System.out.println(s);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
