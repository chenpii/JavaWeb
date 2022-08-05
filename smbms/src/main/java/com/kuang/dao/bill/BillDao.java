package com.kuang.dao.bill;

import com.kuang.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BillDao {
    //查询订单列表
    public List<Bill> getBillList(Connection connection, String productName, int providerId, int isPayment) throws SQLException;

    //根据供应商id查询订单总数
    public int getBillCountByProviderId(Connection connection, int providerId) throws SQLException;

    //新增订单
    public int addBill(Connection connection, Bill bill) throws SQLException;

    //根据billid获取订单
    public Bill getBillById(Connection connection, int billId) throws SQLException;

    //修改订单信息
    public int modifyBill(Connection connection, Bill bill) throws SQLException;

    //删除订单
    public int delBill(Connection connection, int billId) throws SQLException;
}
