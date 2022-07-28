package com.kuang.dao.user;

import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-23 22:54
 */
public interface UserDao {

    //得到要登录的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException;

    //修改当前用户密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException;

    //根据用户名或者角色查询用户总数
    public int getUserCount(Connection connection,String userName, int userRole)throws SQLException;

    //根据查询条件获取用户列表
    public List<User> getUserList(Connection connection,String userName, int userRole,int currentPageNo,int pageSize)throws SQLException;
}
