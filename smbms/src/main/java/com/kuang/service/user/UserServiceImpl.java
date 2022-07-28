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

    /**
     * 用户登录
     * @param userCode 用户编码
     * @param password 密码
     * @return
     */
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

    /**
     * 根据user_id修改密码
     * @param id 用户id
     * @param password 新密码
     * @return
     */
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

    /**
     * 根据用户名或者角色查询用户总数
     * @param userName 用户名称
     * @param userRole 用户角色
     * @return
     */
    public int getUserCount(String userName, int userRole) {

        Connection connection = null;
        int userCount = 0;
        try {
            connection = BaseDao.getConnection();
            userCount = userDao.getUserCount(connection, userName, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userCount;
    }

    @Test
    public void test_login() {
        User admin = this.login("admin", "88888888");
        System.out.println(admin.getUserPassword());
    }

    @Test
    public void test_getUserCount(){
        int count = this.getUserCount(null,2);
        System.out.println(count);
        count = this.getUserCount("张",0);
        System.out.println(count);
        count = this.getUserCount(null,0);
        System.out.println(count);
        count = this.getUserCount("张",3);
        System.out.println(count);

    }
}
