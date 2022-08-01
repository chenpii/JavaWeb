package com.kuang.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenpi
 * @create 2022-07-30 10:25
 */
public class StringUtils {
    /**
     * 字符串转日期时间
     *
     * @param s
     * @return
     */
    public static Date transferString2DateTime(String s) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转日期
     *
     * @param s
     * @return
     */
    public static Date transferString2Date(String s) {
        Date date = new Date();
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Test
    public void test_transferString2DateTime() {
        Date date_util=new Date(System.currentTimeMillis());
        System.out.println(date_util);
        java.sql.Date date_sql =new java.sql.Date(System.currentTimeMillis());
        System.out.println(date_sql);

        String format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date_sql);
        System.out.println(format);
    }

}
