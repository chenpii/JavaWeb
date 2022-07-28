package com.kuang.dao.role;

import com.kuang.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-28 17:29
 */
public interface RoleDao {
    //获取用户角色列表
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
