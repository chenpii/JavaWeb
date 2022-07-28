package com.kuang.service.user;

import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chenpi
 * @create 2022-07-23 23:18
 */
public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //根据user_id修改密码
    public boolean updatePwd( int id , String password);

    //根据用户名或者角色查询用户总数
    public int getUserCount(String userName, int userRole);
}
