package com.kuang.dao.bill;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Bill;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillDaoImpl implements BillDao {
    /**
     * 查询订单列表
     *
     * @param connection
     * @param productName 商品名称
     * @param providerId  供应商id
     * @param isPayment   是否付款
     * @return
     */
    public List<Bill> getBillList(Connection connection, String productName, int providerId, int isPayment) throws SQLException {
        System.out.println("BillDaoImpl->getBillList");
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Bill> billList = new ArrayList<Bill>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select sb.*,sp.proName as proName from smbms_bill sb left join smbms_provider sp on sb.providerId=sp.id ");
            List<Object> paramsList = new ArrayList<Object>();
            if ((!StringUtils.isNullOrEmpty(productName))
                    && (providerId != 0)
                    && (isPayment != 0)) {

                sql.append("where sb.productName like ? and sb.providerId=? and sb.isPayment=? ");
                paramsList.add("%" + productName + "%");
                paramsList.add(providerId);
                paramsList.add(isPayment);

            } else if ((!StringUtils.isNullOrEmpty(productName)) && (providerId != 0)) {

                sql.append("where sb.productName like ? and sb.providerId=? ");
                paramsList.add("%" + productName + "%");
                paramsList.add(providerId);

            } else if ((!StringUtils.isNullOrEmpty(productName)) && (isPayment != 0)) {

                sql.append("where sb.productName like ? and sb.isPayment=? ");
                paramsList.add("%" + productName + "%");
                paramsList.add(isPayment);

            } else if (providerId != 0 && isPayment != 0) {

                sql.append("where sb.providerId=? and sb.isPayment=? ");
                paramsList.add(providerId);
                paramsList.add(isPayment);

            } else if (!StringUtils.isNullOrEmpty(productName)) {

                sql.append("where sb.productName like ? ");
                paramsList.add("%" + productName + "%");

            } else if (providerId != 0) {

                sql.append("where sb.providerId=? ");
                paramsList.add(providerId);


            } else if (isPayment != 0) {

                sql.append("where sb.isPayment=? ");
                paramsList.add(isPayment);
            }

            System.out.println("BillDaoImpl->getBillList:" + sql);
            System.out.println("BillDaoImpl->getBillList:" + paramsList.toString());

            Object[] params = paramsList.toArray();
            rs = BaseDao.executeQuery(connection, pstm, String.valueOf(sql), params);
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setProviderName(rs.getString("proName"));
                bill.setCreationDate(rs.getDate("creationDate"));
                billList.add(bill);
            }
            BaseDao.closeResource(null, pstm, rs);
        }

        return billList;
    }

    /**
     * 根据供应商id查询订单数量
     *
     * @param connection
     * @param providerId
     * @return
     */
    public int getBillCountByProviderId(Connection connection, int providerId) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection != null) {
            String sql = "select count(1) as count from smbms_bill where providerId=?";
            Object[] params = {providerId};
            rs = BaseDao.executeQuery(connection, pstm, sql, params);
            while (rs.next()) {
                count = rs.getInt("count");
            }
        }
        return count;
    }

    @Test
    public void test_getBillList() throws SQLException {
        Connection connection = BaseDao.getConnection();
        this.getBillList(connection, "张", 1, 1);
        this.getBillList(connection, "张", 1, 0);
        this.getBillList(connection, "张", 0, 1);
        this.getBillList(connection, "", 1, 1);
        this.getBillList(connection, "张", 0, 0);
        this.getBillList(connection, "", 1, 0);
        this.getBillList(connection, "", 0, 1);
        this.getBillList(connection, "", 0, 0);
    }
}
