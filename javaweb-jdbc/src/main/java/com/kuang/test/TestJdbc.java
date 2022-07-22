package com.kuang.test;

import com.mysql.jdbc.Driver;

import java.sql.*;

/**
 * @author chenpi
 * @create 2022-07-22 10:20
 */
public class TestJdbc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //配置信息
        //useUnicode=true&characterEncoding=utf-8 解决中文乱码
        String url = "jdbc:mysql://150.158.162.234:3306/smbms?jdbc?useUnicode=true&characterEncoding=utf-8";
        String username = "root";
        String password = "310012";

        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.连接数据库,代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);

        //3.向数据库发送sql的对象Statement ： CRUD
        Statement statement = connection.createStatement();

        //4.编写SQL
        String sql = "select * from user";

        //5.执行查询sql，返回一个结果集
        ResultSet resultSet = statement.executeQuery(sql);


    }
}
