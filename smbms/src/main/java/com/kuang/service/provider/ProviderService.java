package com.kuang.service.provider;

import com.kuang.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderService {
    //获取供应商总数
    public int getProviderCount(String proCode, String proName);

    //查询获取供应商列表
    public List<Provider> getProviderList(String proCode, String proName);

    //查询获取供应商列表-分页
    public List<Provider> getProviderList(String proCode, String proName, int currentPageNo, int pageSize);

    //根据供应商id查询供应商信息
    public Provider getProviderById(int providerId);

    //修改供应商信息
    public boolean modifyProvider(Provider provider);
}
