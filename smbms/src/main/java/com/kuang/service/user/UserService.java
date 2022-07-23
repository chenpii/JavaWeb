package com.kuang.service.user;

import com.kuang.pojo.User;

/**
 * @author chenpi
 * @create 2022-07-23 23:18
 */
public interface UserService {
    //用户登录
    public User login(String userCode, String password);
}
