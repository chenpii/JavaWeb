package com.kuang.service.user;

import com.kuang.pojo.Role;
import com.kuang.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-23 23:18
 */
public interface UserService {
    //用户登录
    public User login(String userCode, String password);

    //根据user_id修改密码
    public boolean updatePwd(int id, String password);

    //根据用户名或者角色查询用户总数
    public int getUserCount(String userName, int userRole);

    //根据查询条件获取用户列表
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize);

    //获取用户角色列表
    public List<Role> getRoleList();
}
