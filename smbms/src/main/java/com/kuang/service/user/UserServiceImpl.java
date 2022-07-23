package com.kuang.service.user;

import com.kuang.dao.user.UserDao;
import com.kuang.dao.user.UserDaoImpl;
import com.kuang.pojo.User;

/**
 * @author chenpi
 * @create 2022-07-23 23:19
 */
public class UserServiceImpl implements UserService {


    //业务层都会调用dao层，所以我们要引入dao层
    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }


    public User login(String userCode, String password) {
        return null;
    }
}
