package com.kuang.dao.provider;

import com.kuang.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {
    //获取供应商总数
    public int getProviderCount(Connection connection, String proCode, String proName)throws SQLException ;

    //查询获取供应商列表
    public List<Provider> getProviderList(Connection connection, String proCode, String proName,int currentPageNo, int pageSize) throws SQLException;
}
