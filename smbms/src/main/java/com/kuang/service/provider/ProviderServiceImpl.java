package com.kuang.service.provider;

import com.kuang.dao.BaseDao;
import com.kuang.dao.provider.ProviderDao;
import com.kuang.dao.provider.ProviderDaoImpl;
import com.kuang.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderServiceImpl implements ProviderService {
    //引入Dao
    private ProviderDao providerDao;

    public ProviderServiceImpl() {
        this.providerDao = new ProviderDaoImpl();
    }

    /**
     * 获取供应商总数
     *
     * @param proCode 供应商编码
     * @param proName 供应商名称
     * @return
     */
    public int getProviderCount(String proCode, String proName) {
        Connection connection = null;
        int providerCount = 0;
        try {
            connection = BaseDao.getConnection();
            providerCount = providerDao.getProviderCount(connection, proCode, proName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return providerCount;
    }

    /**
     * 获取供应商列表-分页
     *
     * @param proCode 供应商编码
     * @param proName 供应商名称
     * @return
     */
    public List<Provider> getProviderList(String proCode, String proName) {
        Connection connection = null;
        List<Provider> providers = new ArrayList<Provider>();
        try {
            connection = BaseDao.getConnection();
            providers = providerDao.getProviderList(connection, proCode, proName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return providers;
    }

    /**
     * 获取供应商列表-分页
     *
     * @param proCode       供应商编码
     * @param proName       供应商名称
     * @param currentPageNo 当前页码
     * @param pageSize      页面大小
     * @return
     */
    public List<Provider> getProviderList(String proCode, String proName, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<Provider> providers = new ArrayList<Provider>();
        try {
            connection = BaseDao.getConnection();
            providers = providerDao.getProviderList(connection, proCode, proName, currentPageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return providers;
    }

    /**
     * 根据供应商id查询供应商信息
     *
     * @param providerId 供应商id
     * @return
     */
    public Provider getProviderById(int providerId) {
        Connection connection = null;
        Provider provider = null;
        try {
            connection = BaseDao.getConnection();
            provider = providerDao.getProviderById(connection, providerId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return provider;
    }

    /**
     * 修改供应商信息
     *
     * @param provider 供应商
     * @return
     */
    public boolean modifyProvider(Provider provider) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            int updateRows = providerDao.modifyProvider(connection, provider);
            connection.commit();

            if (updateRows > 0) {
                flag = true;
                System.out.println("ProviderServiceImpl-->modifyProvider:successed!");
            } else {
                System.out.println("ProviderServiceImpl-->modifyProvider:failed!");
            }
        } catch (SQLException e) {
            try {
                System.out.println("==================rollback==================");
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
}
