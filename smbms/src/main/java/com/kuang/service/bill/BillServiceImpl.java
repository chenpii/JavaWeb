package com.kuang.service.bill;

import com.kuang.dao.BaseDao;
import com.kuang.dao.bill.BillDao;
import com.kuang.dao.bill.BillDaoImpl;
import com.kuang.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillServiceImpl implements BillService {
    private BillDao billDao;

    public BillServiceImpl() {
        this.billDao = new BillDaoImpl();
    }

    /**
     * 查询订单列表
     *
     * @param productName
     * @param providerId
     * @param isPayment
     * @return
     */
    public List<Bill> getBillList(String productName, int providerId, int isPayment) {
        System.out.println("BillServiceImpl->getBillList");
        Connection connection = null;
        List<Bill> billList = new ArrayList<Bill>();

        try {
            connection = BaseDao.getConnection();
            billList = billDao.getBillList(connection, productName, providerId, isPayment);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return billList;
    }

    /**
     * 根据供应商id查询订单数量
     *
     * @param providerId
     * @return
     */
    public int getBillCountByProviderId(int providerId) {
        Connection connection = null;
        int count = 0;
        try {
            connection = BaseDao.getConnection();
            count = billDao.getBillCountByProviderId(connection, providerId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }

        return count;
    }
}