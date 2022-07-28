package com.kuang.service.role;

import com.kuang.dao.role.RoleDao;
import com.kuang.pojo.Role;

import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-28 18:52
 */
public interface RoleService {

    //获取用户角色列表
    public List<Role> getRoleList();
}
