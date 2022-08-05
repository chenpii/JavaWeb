package com.kuang.service.bill;

import com.kuang.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BillService {
    //查询订单列表
    public List<Bill> getBillList(String productName, int providerId, int isPayment);

    //根据供应商id查询订单总数
    public int getBillCountByProviderId(int providerId);

    //新增订单
    public boolean addBill(Bill bill);

    //根据billid获取订单
    public Bill getBillById(int billId);

    //修改订单信息
    public boolean modifyBill(Bill bill);

    //删除订单
    public boolean delBill(int billId);

}
