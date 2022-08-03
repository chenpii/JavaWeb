package com.kuang.dao.bill;

import com.kuang.pojo.Bill;

import java.sql.Connection;
import java.util.List;

public interface BillDao {
    //获取订单列表
    public List<Bill> getBillList(Connection connection);
}
