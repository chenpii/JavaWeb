package com.kuang.dao.user;

import com.kuang.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Connection;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-09-03 20:28
 */
public interface UserMapper {
    //新增用户
    int addUser(User user);

    //删除
    int delUser(@Param("id") int userId);

    //根据userCode得到要登录的用户
    User getLoginUser(@Param("userCode") String userCode);

    //修改当前用户密码
    int updatePwd(@Param("id") int id, @Param("password") String password);

    //根据用户名或者角色查询用户总数
    int getUserCount(@Param("userName") String userName, @Param("userRole") int userRole);

    //根据查询条件获取用户列表
    List<User> getUserList(@Param("userName") String userName, @Param("userRole") int userRole, @Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    //根据userCode获取用户
    User getUserByUserCode(@Param("userCode") String userCode);

    //根据userId获取用户
    User getUserById(@Param("id") int userId);

    //更新用户信息
    int modifyUser(User user);
}
