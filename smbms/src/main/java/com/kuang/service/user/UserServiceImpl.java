package com.kuang.service.user;

import com.kuang.dao.BaseDao;
import com.kuang.dao.role.RoleMapper;
import com.kuang.dao.user.UserDao;
import com.kuang.dao.user.UserDaoImpl;
import com.kuang.dao.user.UserMapper;
import com.kuang.pojo.User;
import com.kuang.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenpi
 * @create 2022-07-23 23:19
 */
public class UserServiceImpl implements UserService {


    //业务层都会调用dao层，所以我们要引入dao层
    private UserDao userDao;

    public UserServiceImpl() {//无参构造器
        userDao = new UserDaoImpl();
    }

    /**
     * 用户登录
     *
     * @param userCode 用户编码
     * @param password 密码
     * @return
     */
    public User login(String userCode, String password) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        User user = null;
//        try {
//            connection = BaseDao.getConnection();
//            user = userDao.getLoginUser(connection, userCode);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }

        user = userMapper.getLoginUser(userCode);
        sqlSession.close();

        if (user != null && user.getUserPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * 根据user_id修改密码
     *
     * @param id       用户id
     * @param password 新密码
     * @return
     */
    public boolean updatePwd(int id, String password) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        boolean flag = false;
        //修改密码
//        try {
//            connection = BaseDao.getConnection();
//            if (userDao.updatePwd(connection, id, password) > 0) {
//                flag = true;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }

        if (userMapper.updatePwd(id, password) > 0) {
            flag = true;
        }
        sqlSession.close();

        return flag;
    }

    /**
     * 根据用户名或者角色查询用户总数
     *
     * @param userName 用户名称
     * @param userRole 用户角色
     * @return
     */
    public int getUserCount(String userName, int userRole) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        int userCount = 0;
//        try {
//            connection = BaseDao.getConnection();
//            userCount = userDao.getUserCount(connection, userName, userRole);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }
        userCount = userMapper.getUserCount(userName, userRole);
        sqlSession.close();

        return userCount;
    }

    /**
     * 根据查询条件获取用户列表
     *
     * @param userName      用户名称
     * @param userRole      用户角色
     * @param currentPageNo 当前页码
     * @param pageSize      每页最大记录
     * @return
     */
    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        List<User> userList = new ArrayList<User>();
        System.out.println("UserServiceImpl-->getUserList-->userName:" + userName);
        System.out.println("UserServiceImpl-->getUserList-->userRole:" + userRole);
        System.out.println("UserServiceImpl-->getUserList-->currentPageNo:" + currentPageNo);
        System.out.println("UserServiceImpl-->getUserList-->pageSize:" + pageSize);

//        try {
//            connection = BaseDao.getConnection();
//            userList = userDao.getUserList(connection, userName, userRole, currentPageNo, pageSize);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }

        int startIndex = (currentPageNo - 1) * pageSize;
        userList = userMapper.getUserList(userName, userRole, startIndex, pageSize);
        sqlSession.close();

        return userList;
    }

    /**
     * 新增用户
     *
     * @param user 用户
     * @return
     */
    public boolean addUser(User user) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        boolean flag = false;

//        try {
//            connection = BaseDao.getConnection();
//            //取消自动提交，开启手动管理事务
//            connection.setAutoCommit(false);
//            int updateRows = userDao.addUser(connection, user);
//            connection.commit();//手动提交事务
//
//            if (updateRows > 0) {
//                flag = true;
//                System.out.println("UserServiceImpl-->addUser:successed!");
//            } else {
//                System.out.println("UserServiceImpl-->addUser:failed!");
//
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                System.out.println("=============rollback=============");
//                connection.rollback();//失败则回滚事务
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            //在service层进行对connection的关闭
//            BaseDao.closeResource(connection, null, null);
//        }

        if (userMapper.addUser(user) > 0) {
            flag = true;
        }
        sqlSession.close();

        return flag;
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return
     */
    public boolean delUserById(int userId) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        boolean flag = false;

//        try {
//            connection = BaseDao.getConnection();
//            connection.setAutoCommit(false);
//            int updateRows = userDao.delUser(connection, userId);
//            connection.commit();
//            if (updateRows > 0) {
//                flag = true;
//                System.out.println("UserServiceImpl-->delUser:successed!");
//            } else {
//                System.out.println("UserServiceImpl-->delUser:failed!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                System.out.println("=============rollback=============");
//                connection.rollback();//失败则回滚事务
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }

        if (userMapper.delUser(userId) > 0) {
            flag = true;
        }
        sqlSession.close();
        return flag;
    }

    /**
     * 根据userCode查询是否已存在
     *
     * @param userCode
     * @return
     */
    public User userCodeExist(String userCode) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
//        PreparedStatement pstm = null;
        User user = null;

//        try {
//            connection = BaseDao.getConnection();
//            user = userDao.getUserByUserCode(connection, userCode);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, pstm, null);
//        }

        user = userMapper.getUserByUserCode(userCode);
        sqlSession.close();

        return user;
    }

    /**
     * 根据用户Id获取用户
     *
     * @param userId
     * @return
     */
    public User getUserById(int userId) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        User user = null;
//        try {
//            connection = BaseDao.getConnection();
//            user = userDao.getUserById(connection, userId);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }

        user = userMapper.getUserById(userId);
        sqlSession.close();

        return user;
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public boolean modifyUser(User user) {

        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

//        Connection connection = null;
        boolean flag = false;
//        try {
//            connection = BaseDao.getConnection();
//            connection.setAutoCommit(false);
//            int updateRows = userDao.modifyUser(connection, user);
//            connection.commit();
//            if (updateRows > 0) {
//                flag = true;
//                System.out.println("UserServiceImpl-->modifyUser:successed!");
//            } else {
//                System.out.println("UserServiceImpl-->modifyUser:failed!");
//            }
//        } catch (SQLException e) {
//            System.out.println("=============rollback=============");
//            e.printStackTrace();
//            try {
//                connection.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            BaseDao.closeResource(connection, null, null);
//        }

        if (userMapper.modifyUser(user) > 0) {
            flag = true;
        }

        sqlSession.close();
        return flag;
    }

    @Test
    public void test_login() {
        User admin = this.login("admin", "88888888");
        System.out.println(admin.getUserPassword());
    }

    @Test
    public void test_getUserCount() {
        int count = this.getUserCount(null, 2);
        System.out.println(count);
        count = this.getUserCount("张", 0);
        System.out.println(count);
        count = this.getUserCount(null, 0);
        System.out.println(count);
        count = this.getUserCount("张", 3);
        System.out.println(count);

    }

}
