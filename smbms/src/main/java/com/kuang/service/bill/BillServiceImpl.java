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

    /**
     * 新增订单
     *
     * @param bill
     * @return
     */
    public boolean addBill(Bill bill) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            int updateRows = billDao.addBill(connection, bill);
            connection.commit();

            if (updateRows > 0) {
                System.out.println("BillServiceImpl->addBill:successed!");
                flag = true;
            } else {
                System.out.println("BillServiceImpl->addBill:failed!");
            }

        } catch (SQLException e) {
            try {
                System.out.println("==========rollback==========");
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    /**
     * 根据id获取订单
     *
     * @param billId 订单id
     * @return
     */
    public Bill getBillById(int billId) {
        Connection connection = null;
        Bill bill = new Bill();
        try {
            connection = BaseDao.getConnection();
            bill = billDao.getBillById(connection, billId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return bill;
    }
}
