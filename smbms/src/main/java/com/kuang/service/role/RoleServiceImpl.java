package com.kuang.service.role;

import com.kuang.dao.BaseDao;
import com.kuang.dao.provider.ProviderMapper;
import com.kuang.dao.role.RoleDao;
import com.kuang.dao.role.RoleDaoImpl;
import com.kuang.dao.role.RoleMapper;
import com.kuang.pojo.Role;
import com.kuang.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-28 18:53
 */
public class RoleServiceImpl implements RoleService {
    //引入Dao
    private RoleDao roleDao;

    public RoleServiceImpl() {
        this.roleDao = new RoleDaoImpl();
    }

    /**
     * 获取用户角色列表
     *
     * @return
     */
    public List<Role> getRoleList() {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
//        Connection connection = null;
        List<Role> roleList = new ArrayList<Role>();
//        try {
//            connection = BaseDao.getConnection();
//            roleList = roleDao.getRoleList(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }
        roleList = roleMapper.getRoleList();
        sqlSession.close();
        return roleList;
    }

    @Test
    public void test_getRoleList() {
        List<Role> roleList = getRoleList();
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }

}
