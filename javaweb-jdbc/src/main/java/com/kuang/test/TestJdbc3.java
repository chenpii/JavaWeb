package com.kuang.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author chenpi
 * @create 2022-07-23 20:58
 */
public class TestJdbc3 {
    @Test
    public void test() {
        //配置信息
        //useUnicode=true&characterEncoding=utf-8 解决中文乱码
        String url = "jdbc:mysql://150.158.162.234:3306/smbms?jdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "root";
        String password = "310012";

        Connection connection=null;
        try {
            //1.加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.连接数据库,代表数据库
            connection = DriverManager.getConnection(url, username, password);

            //3.通知数据库开启事务,false 不自动提交 而是手动提交
            connection.setAutoCommit(false);
            String sql1 = "update account set money =money-100 where name='A';";
            connection.prepareStatement(sql1).executeUpdate();

            //制造错误
//            int i = 1 / 0;

            String sql2 = "update account set money =money+100 where name='B';";
            connection.prepareStatement(sql2).executeUpdate();
            connection.commit();//以上两天sql都执行成功了，就提交事务。
            System.out.println("success");
        } catch (Exception e) {
            try {
                //如果出现异常，就通知数据库回滚事务
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
