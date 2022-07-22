package com.kuang.test;

import java.sql.*;

/**
 * @author chenpi
 * @create 2022-07-22 15:08
 */
public class TestJdbc2 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //配置信息
        //useUnicode=true&characterEncoding=utf-8 解决中文乱码
        String url = "jdbc:mysql://150.158.162.234:3306/smbms?jdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String username = "root";
        String password = "310012";

        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.连接数据库,代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        //3.向数据库发送sql的对象Statement ： CRUD
        Statement statement = connection.createStatement();

        //4.编写SQL
        String sql = "select * from users";
//        String sql = "delete from users where id = 4;";

        //6.关闭连接释放资源(一定要做）先开后关
        statement.close();
        connection.close();
    }
}
