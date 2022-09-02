package com.kuang.dao.bill;

import com.kuang.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {

    //查询订单列表
    List<Bill> getBillList(@Param("providerId") int providerId, @Param("isPayment") int isPayment);

    //根据供应商id查询订单总数
    int getBillCountByProviderId(int providerId);

    //新增订单
    int addBill(Bill bill);

    //根据billid获取订单
    Bill getBillById(@Param("id") int billId);

    //修改订单信息
    int modifyBill(Bill bill);

    //删除订单
    int delBill(int billId);
}
