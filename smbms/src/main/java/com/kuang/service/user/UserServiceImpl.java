package com.kuang.service.user;

import com.kuang.dao.BaseDao;
import com.kuang.dao.user.UserDao;
import com.kuang.dao.user.UserDaoImpl;
import com.kuang.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author chenpi
 * @create 2022-07-23 23:19
 */
public class UserServiceImpl implements UserService {


    //业务层都会调用dao层，所以我们要引入dao层
    private UserDao userDao;

    public UserServiceImpl() {//无参构造器
        userDao = new UserDaoImpl();
    }


    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;
        connection = BaseDao.getConnection();
        try {
            user = userDao.getLoginUser(connection, userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        if (user != null && user.getUserPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    public boolean updatePwd(int id, String password) {
        Connection connection = null;
        boolean flag = false;
        //修改密码
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection, id, password) > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "88888888");
        System.out.println(admin.getUserPassword());
    }
}
