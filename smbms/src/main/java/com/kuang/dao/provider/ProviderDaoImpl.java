package com.kuang.dao.provider;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Provider;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderDaoImpl implements ProviderDao {
    /**
     * 获取供应商总数
     *
     * @param connection
     * @param proCode    供应商编码
     * @param proName    供应商名称
     * @return
     */
    public int getProviderCount(Connection connection, String proCode, String proName) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int providerCount = 0;

        StringBuffer sql = new StringBuffer();
        sql.append("select count(1) as providerCount from smbms_provider ");
        //存放参数
        ArrayList<Object> arrayList = new ArrayList<Object>();
        //拼接查询条件
        if ((!StringUtils.isNullOrEmpty(proCode)) || (!StringUtils.isNullOrEmpty(proName))) {
            sql.append("where ");

            if ((!StringUtils.isNullOrEmpty(proCode))) {
                sql.append("proCode like ? ");
                arrayList.add("%" + proCode + "%");
                if ((!StringUtils.isNullOrEmpty(proName))) {
                    sql.append("and proName like ? ");
                    arrayList.add("%" + proName + "%");
                }
            } else if ((!StringUtils.isNullOrEmpty(proName))) {
                sql.append("proName like ? ");
                arrayList.add("%" + proName + "%");
            }
        }
        System.out.println("ProviderDaoImpl-->getProviderCount:" + sql);

        //参数转换成数组
        Object[] params = arrayList.toArray();
        if (connection != null) {
            rs = BaseDao.executeQuery(connection, pstm, String.valueOf(sql), params);
            while (rs.next()) {
                providerCount = rs.getInt("providerCount");
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return providerCount;
    }


    /**
     * 查询供应商列表
     *
     * @param connection
     * @param proCode    供应商编码
     * @param proName    供应商名称
     * @return
     * @throws SQLException
     */
    public List<Provider> getProviderList(Connection connection, String proCode, String proName) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Provider> providersList = new ArrayList<Provider>();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from smbms_provider ");
        //存放参数
        ArrayList<Object> arrayList = new ArrayList<Object>();
        //拼接查询条件
        if ((!StringUtils.isNullOrEmpty(proCode)) || (!StringUtils.isNullOrEmpty(proName))) {
            sql.append("where ");

            if ((!StringUtils.isNullOrEmpty(proCode))) {
                sql.append("proCode like ? ");
                arrayList.add("%" + proCode + "%");
                if ((!StringUtils.isNullOrEmpty(proName))) {
                    sql.append("and proName like ? ");
                    arrayList.add("%" + proName + "%");
                }
            } else if ((!StringUtils.isNullOrEmpty(proName))) {
                sql.append("proName like ? ");
                arrayList.add("%" + proName + "%");
            }
        }
        System.out.println("ProviderDaoImpl-->getProviderList:" + sql);

        //参数转换成数组
        Object[] params = arrayList.toArray();
        if (connection != null) {
            rs = BaseDao.executeQuery(connection, pstm, String.valueOf(sql), params);
            while (rs.next()) {
                Provider provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getDate("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getDate("modifyDate"));
                providersList.add(provider);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return providersList;
    }

    /**
     * 查询供应商列表-分页
     *
     * @param connection
     * @param proCode       供应商编码
     * @param proName       供应商名称
     * @param currentPageNo 当前页码
     * @param pageSize      每页大小
     * @return
     * @throws SQLException
     */
    public List<Provider> getProviderList(Connection connection, String proCode, String proName, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Provider> providersList = new ArrayList<Provider>();

        StringBuffer sql = new StringBuffer();
        sql.append("select * from smbms_provider ");
        //存放参数
        ArrayList<Object> arrayList = new ArrayList<Object>();
        //拼接查询条件
        if ((!StringUtils.isNullOrEmpty(proCode)) || (!StringUtils.isNullOrEmpty(proName))) {
            sql.append("where ");

            if ((!StringUtils.isNullOrEmpty(proCode))) {
                sql.append("proCode like ? ");
                arrayList.add("%" + proCode + "%");
                if ((!StringUtils.isNullOrEmpty(proName))) {
                    sql.append("and proName like ? ");
                    arrayList.add("%" + proName + "%");
                }
            } else if ((!StringUtils.isNullOrEmpty(proName))) {
                sql.append("proName like ? ");
                arrayList.add("%" + proName + "%");
            }
        }
        // 在数据库中，分页使用 limit startIndex,pageSize;
        // startIndex 当前页起始行 =（当前页码-1）*每页最大记录
        sql.append("order by creationDate limit ?,?");
        int startIndex = (currentPageNo - 1) * pageSize;
        arrayList.add(startIndex);
        arrayList.add(pageSize);
        System.out.println("ProviderDaoImpl-->getProviderList:" + sql);

        //参数转换成数组
        Object[] params = arrayList.toArray();
        if (connection != null) {
            rs = BaseDao.executeQuery(connection, pstm, String.valueOf(sql), params);
            while (rs.next()) {
                Provider provider = new Provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getDate("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getDate("modifyDate"));
                providersList.add(provider);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return providersList;
    }

    /**
     * 根据供应商id获取供应商
     *
     * @param connection
     * @param providerId 供应商id
     * @return
     */
    public Provider getProviderById(Connection connection, int providerId) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Provider provider = new Provider();
        if (connection != null) {
            String sql = "select * from smbms_provider where id =?";
            Object[] params = {providerId};
            rs = BaseDao.executeQuery(connection, pstm, sql, params);
            while (rs.next()) {
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getDate("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getDate("modifyDate"));
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return provider;
    }

    /**
     * 修改供应商信息
     *
     * @param connection
     * @param provider   供应商对象
     * @return
     */
    public int modifyProvider(Connection connection, Provider provider) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null) {
            String sql = "update smbms_provider set proCode=?,proName=?,proContact=?,proPhone=?,proAddress=?,proFax=?,proDesc=?,modifyBy=?,modifyDate=? where id=?";
            Object[] params = {provider.getProCode(),
                    provider.getProName(),
                    provider.getProContact(),
                    provider.getProPhone(),
                    provider.getProAddress(),
                    provider.getProFax(),
                    provider.getProDesc(),
                    provider.getModifyBy(),
                    provider.getModifyDate(),
                    provider.getId()};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * 新增供应商
     *
     * @param connection
     * @param provider   供应商
     * @return
     * @throws SQLException
     */
    public int addProvider(Connection connection, Provider provider) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "insert into smbms_provider (proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?)";
            Object[] params = {provider.getProCode(),
                    provider.getProName(),
                    provider.getProDesc(),
                    provider.getProContact(),
                    provider.getProPhone(),
                    provider.getProAddress(),
                    provider.getProFax(),
                    provider.getCreatedBy(),
                    provider.getCreationDate()};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * 删除供应商
     *
     * @param connection
     * @param providerId 供应商id
     * @return
     * @throws SQLException
     */
    public int delProvider(Connection connection, int providerId) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "delete from smbms_provider where id=?";
            Object[] params = {providerId};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }

        return updateRows;
    }

    @Test
    public void test_getBillList() throws SQLException {
        this.getProviderList(null, "1", "北京", 1, 5);
        this.getProviderList(null, null, "北京", 1, 5);
        this.getProviderList(null, "1", null, 1, 5);
        this.getProviderList(null, null, null, 1, 5);
    }
}
