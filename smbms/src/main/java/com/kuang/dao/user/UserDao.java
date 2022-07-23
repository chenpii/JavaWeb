package com.kuang.dao.user;

import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chenpi
 * @create 2022-07-23 22:54
 */
public interface UserDao {

    //得到要登录的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException;


}
