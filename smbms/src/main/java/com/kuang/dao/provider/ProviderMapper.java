package com.kuang.dao.provider;

import com.kuang.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chenpi
 * @create 2022-09-03 19:14
 */
public interface ProviderMapper {
    //获取供应商总数
    int getProviderCount(@Param("proCode") String proCode, @Param("proName") String proName);

    //查询获取供应商列表
    List<Provider> getProviderList(@Param("proCode") String proCode, @Param("proName") String proName);

    //查询获取供应商列表-分页
    List<Provider> getProviderList(@Param("proCode") String proCode, @Param("proName") String proName, @Param("currentPageNo") int currentPageNo, @Param("pageSize") int pageSize);

    //根据供应商id查询供应商信息
    Provider getProviderById(@Param("id") int providerId);

    //修改供应商信息
    int modifyProvider(Provider provider);

    //新增供应商信息
    int addProvider(Provider provider);

    //删除供应商
    int delProvider(@Param("id") int providerId);
}
