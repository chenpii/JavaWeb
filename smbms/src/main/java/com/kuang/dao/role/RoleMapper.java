package com.kuang.dao.role;

import com.kuang.pojo.Role;

import java.util.List;

/**
 * @author chenpi
 * @create 2022-09-03 19:45
 */
public interface RoleMapper {
    //获取用户角色列表
    List<Role> getRoleList();
}
