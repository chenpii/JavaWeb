package com.kuang.service.bill;

import com.kuang.pojo.Bill;

import java.util.List;

public interface BillService {
    //查询订单列表
    public List<Bill> getBillList(String productName, int providerId, int isPayment);

    //根据供应商id查询订单总数
    public int getBillCountByProviderId(int providerId);
}
