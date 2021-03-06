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

        //3.编写SQL
        String sql = "insert into users(id, name, password, email, birthday) values (?,?,?,?,?)";

        //4.预编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 4);//给第一个占位符？的值赋值为1
        preparedStatement.setString(2, "狂神说Java");//给第二个占位符？的值赋值为狂神说Java
        preparedStatement.setString(3, "123456");//给第三个占位符？的值赋值为123456
        preparedStatement.setString(4, "kuang@qq.com");//给第四个占位符？的值赋值为kuang@qq.com
        preparedStatement.setDate(5, new Date(new java.util.Date().getTime()));//给第五个占位符？的值赋值为new Date(new java.util.Date().getTime())


        //5.执行sql
        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            System.out.println("插入成功");
        }

        //6.关闭连接释放资源(一定要做）先开后关
        preparedStatement.close();
        connection.close();
    }
}
