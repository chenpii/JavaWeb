package com.kuang.dao.role;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-28 17:30
 */
public class RoleDaoImpl implements RoleDao{
    /**
     * 获取用户角色列表
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public List<Role> getRoleList(Connection connection) throws SQLException {
        PreparedStatement pstm = null;
        List<Role> roleList = new ArrayList<Role>();
        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            ResultSet rs = BaseDao.executeQuery(connection, pstm, sql, params);
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleCode(rs.getString("roleCode"));
                role.setRoleName(rs.getString("roleName"));
                role.setCreatedBy(rs.getInt("createdBy"));
                role.setCreationDate(rs.getDate("creationDate"));
                role.setModifyBy(rs.getInt("modifyBy"));
                role.setModifyDate(rs.getDate("modifyDate"));
                roleList.add(role);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return roleList;
    }
}
