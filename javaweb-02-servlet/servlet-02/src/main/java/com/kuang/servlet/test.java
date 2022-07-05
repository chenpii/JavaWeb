package com.kuang.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenpi
 * @create 2022-07-05 9:30
 */
public class test {
    public static void main(String[] args) {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
    }
}
