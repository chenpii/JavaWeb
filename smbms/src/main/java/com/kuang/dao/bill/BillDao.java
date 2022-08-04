package com.kuang.dao.bill;

import com.kuang.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BillDao {
    //查询订单列表
    public List<Bill> getBillList(Connection connection,String productName,int providerId,int isPayment) throws SQLException;

    //根据供应商id查询订单总数
    public int getBillCountByProviderId(Connection connection, int providerId) throws SQLException;
}
