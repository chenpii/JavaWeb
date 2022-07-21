package com.kuang.listener;

import java.awt.*;

/**
 * @author chenpi
 * @create 2022-07-21 14:58
 */
public class TestPanel {
    public static void main(String[] args) {
        Frame frame = new Frame("大家中秋节快乐");//新建一个窗体
        Panel panel = new Panel(null);//面板
        frame.setLayout(null);//设置窗体布局

        frame.setBounds(300,300,500,500);
        frame.setBackground(new Color(0,0,255));//设置背景颜色

        panel.setBounds(50,50,300,300);
        panel.setBackground(new Color(0,255,0)); //设置背景颜色

        frame.add(panel);

        frame.setVisible(true);

    }
}
