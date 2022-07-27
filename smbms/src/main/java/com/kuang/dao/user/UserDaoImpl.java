package com.kuang.dao.user;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author chenpi
 * @create 2022-07-23 23:01
 */
public class UserDaoImpl implements UserDao {

    //得到要登录的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        if (connection != null) {
            String sql = "select * from smbms_user where userCode=? ";
            Object[] params = {userCode};
            resultSet = BaseDao.executeQuery(connection, preparedStatement, sql, params);

            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getDate("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getDate("modifyDate"));
            }
            BaseDao.closeResource(null, preparedStatement, resultSet);
        }
        return user;
    }

    //修改当前用户密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null) {
            String sql = "update smbms_user set userPassword=? where id=?";
            Object[] params = {password, id};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    //查询用户数总数
    public int getUserCount(Connection connection, String userName, int userRole) {
        PreparedStatement pstm = null;
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) from smbms_user su left join smbms_role sr on su.userRole = sr.id");

        //存放参数
        ArrayList<Object> arrayList = new ArrayList<Object>();

        if (!(StringUtils.isNullOrEmpty(userName)) || userRole > 0) { //userName和userRole有一个不为空
            sql.append(" where");
            if (!(StringUtils.isNullOrEmpty(userName))) { //userName 不为空
                sql.append(" su.userName like ?");
                arrayList.add("%" + userName + "%"); //index：0
                if (userRole > 0) { //userName和userRole 都不为空
                    sql.append(" and su.userRole = ?");
                    arrayList.add(userRole);//index：1
                }
            } else {
                if (userRole > 0) { //userName为空，userRole不为空
                    sql.append(" su.userRole = ?");
                    arrayList.add(userRole);//index：1
                }
            }
        }
        //转换成数组
        Object[] params = arrayList.toArray();
              

        System.out.println(sql.toString());
        return 0;
    }

    @Test
    public void testgetUserCount() {
        getUserCount(null, "admin", 1);
        getUserCount(null, "admin", 0);
        getUserCount(null, null, 1);
        getUserCount(null, null, 0);
    }
}
