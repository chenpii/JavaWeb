package com.kuang.dao.user;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Role;
import com.kuang.pojo.User;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-23 23:01
 */
public class UserDaoImpl implements UserDao {
    /**
     * 得到要登录的用户
     *
     * @param connection
     * @param userCode   用户编码
     * @return
     * @throws SQLException
     */
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

    /**
     * 修改当前用户密码
     *
     * @param connection
     * @param id         用户id
     * @param password   密码
     * @return
     * @throws SQLException
     */
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

    /**
     * 查询用户数总数
     *
     * @param connection
     * @param userName   用户名称
     * @param userRole   用户角色
     * @return
     * @throws SQLException
     */
    public int getUserCount(Connection connection, String userName, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) as count from smbms_user su left join smbms_role sr on su.userRole = sr.id");

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

        System.out.println("UserDaoImpl->getUserCount:" + sql.toString());//输出最后的sql语句

        if (connection != null) {
            rs = BaseDao.executeQuery(connection, pstm, String.valueOf(sql), params);
            if (rs.next()) {
                count = rs.getInt("count");//从结果集中获取最终的数量
            }
        }
        BaseDao.closeResource(null, pstm, null);//connection 在业务层关闭
        return count;

    }

    /**
     * 根据查询条件获取用户列表
     *
     * @param connection
     * @param userName      用户名称
     * @param userRole      用户角色
     * @param currentPageNo 当前页码
     * @param pageSize      每页最大记录
     * @return
     * @throws SQLException
     */
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();


        StringBuffer sql = new StringBuffer();
        sql.append("select * from smbms_user su left join smbms_role sr on su.userRole = sr.id");
        //存放参数
        ArrayList<Object> arrayList = new ArrayList<Object>();
        //拼接查询条件
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
        // 在数据库中，分页使用 limit startIndex,pageSize;
        // startIndex 当前页起始行 =（当前页码-1）*每页最大记录
        // 1 01234
        // 2 56789
        // 3 10 11 12 13 14
        sql.append(" order by su.creationDate limit ?,?");
        int startIndex = (currentPageNo - 1) * pageSize;
        arrayList.add(startIndex);
        arrayList.add(pageSize);

        //转换成数组
        Object[] params = arrayList.toArray();
        System.out.println("UserDaoImpl->getUserList:" + sql.toString());//输出最后的sql语句
        if (connection != null) {
            rs = BaseDao.executeQuery(connection, pstm, String.valueOf(sql), params);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
                user.setUserRoleName(rs.getString("roleName"));
                userList.add(user);
            }
            BaseDao.closeResource(null, pstm, rs);//connection 在业务层关闭
        }
        return userList;
    }

    /**
     * 新增用户
     *
     * @param connection
     * @param user       用户对象
     * @return
     */
    public int addUser(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        System.out.println("UserDaoImpl->addUser->user:" + user.toString());
        if (connection != null) {

            String sql = "insert into smbms_user (userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate) " +
                    " values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(),
                    user.getUserName(),
                    user.getUserPassword(),
                    user.getGender(),
                    user.getBirthday(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getUserRole(),
                    user.getCreatedBy(),
                    user.getCreationDate()};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * 删除用户
     *
     * @param connection
     * @param userId     用户id
     * @return
     * @throws SQLException
     */
    public int delUser(Connection connection, int userId) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null) {
            String sql = "delete from smbms_user where id=?";
            Object[] params = {userId};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * 根据userCode获取用户
     *
     * @param connection
     * @param userCode   用户编码
     * @return
     * @throws SQLException
     */
    public User getUserByUserCode(Connection connection, String userCode) throws SQLException {
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

    /**
     * 根据用户id获取用户
     *
     * @param connection
     * @param userId     用户id
     * @return
     * @throws SQLException
     */
    public User getUserById(Connection connection, int userId) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        User user = null;
        if (connection != null) {
            String sql = "select su.*,sr.roleName as userRoleName " +
                    "from smbms_user su " +
                    "left outer join smbms_role sr on su.userRole = sr.id " +
                    "where su.id=?";
            Object[] params = {userId};
            resultSet = BaseDao.executeQuery(connection, pstm, sql, params);
            while (resultSet.next()) {
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
                user.setUserRoleName(resultSet.getString("userRoleName"));
            }
            BaseDao.closeResource(null, pstm, resultSet);
        }
        return user;
    }

    /**
     * 更新用户信息
     *
     * @param connection
     * @param user       用户对象
     * @return
     * @throws SQLException
     */
    public int modifyUser(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null) {
            String sql = "update smbms_user set userName=?,gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id=?";
            Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole(), user.getModifyBy(), user.getModifyDate(), user.getId()};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    @Test
    public void test_getUserCount() throws SQLException {
        getUserCount(null, "admin", 1);
        getUserCount(null, "admin", 0);
        getUserCount(null, null, 1);
        getUserCount(null, null, 0);
    }

}
