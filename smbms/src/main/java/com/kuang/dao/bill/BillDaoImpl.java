package com.kuang.dao.bill;

import com.kuang.dao.BaseDao;
import com.kuang.pojo.Bill;
import com.mysql.cj.jdbc.ConnectionImpl;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    /**
     * 新增订单
     *
     * @param connection
     * @param bill       订单
     * @return
     * @throws SQLException
     */
    public int addBill(Connection connection, Bill bill) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "insert into smbms_bill (billCode,productName,productDesc,productUnit,productCount,totalPrice,isPayment,createdBy,creationDate,providerId) values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {bill.getBillCode(),
                    bill.getProductName(),
                    bill.getProductDesc(),
                    bill.getProductUnit(),
                    bill.getProductCount(),
                    bill.getTotalPrice(),
                    bill.getIsPayment(),
                    bill.getCreatedBy(),
                    bill.getCreationDate(),
                    bill.getProviderId()};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * 根据订单id获取订单
     *
     * @param connection
     * @param billId     订单id
     * @return
     * @throws SQLException
     */
    public Bill getBillById(Connection connection, int billId) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Bill bill = new Bill();
        if (connection != null) {
            String sql = "select sb.*,sp.proName as proName from smbms_bill sb left join smbms_provider sp on sb.providerId=sp.id where sb.id=?";
            Object[] params = {billId};
            rs = BaseDao.executeQuery(connection, pstm, sql, params);
            while (rs.next()) {
                bill = new Bill();
                bill.setId(rs.getInt("id"));//id
                bill.setBillCode(rs.getString("billCode"));//订单编码
                bill.setProductName(rs.getString("productName")); //商品名称
                bill.setProductDesc(rs.getString("productDesc")); //商品描述
                bill.setProductUnit(rs.getString("productUnit")); //商品单位
                bill.setProductCount(rs.getBigDecimal("productCount"));//商品数量
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));//总金额
                bill.setIsPayment(rs.getInt("isPayment"));//是否支付
                bill.setCreatedBy(rs.getInt("createdBy"));//创建者
                bill.setCreationDate(rs.getDate("creationDate"));//创建时间
                bill.setCreatedBy(rs.getInt("modifyBy"));//更新者
                bill.setCreationDate(rs.getDate("modifyDate"));//更新时间
                bill.setProviderId(rs.getInt("providerId"));//供应商id
                bill.setProviderName(rs.getString("proName"));//供应商名称
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return bill;
    }

    /**
     * 修改订单信息
     *
     * @param connection
     * @param bill
     * @return
     * @throws SQLException
     */
    public int modifyBill(Connection connection, Bill bill) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "update smbms_bill set billCode=?,productName=?,productUnit=?,productCount=?,totalPrice=?,providerId=?,isPayment=?,modifyBy=?,modifyDate=? where id=?";
            Object[] params = {bill.getBillCode(),
                    bill.getProductName(),
                    bill.getProductUnit(),
                    bill.getProductCount(),
                    bill.getTotalPrice(),
                    bill.getProviderId(),
                    bill.getIsPayment(),
                    bill.getModifyBy(),
                    bill.getModifyDate(),
                    bill.getId()};

            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }

    /**
     * 删除订单
     *
     * @param connection
     * @param billId     订单Id
     * @return
     * @throws SQLException
     */
    public int delBill(Connection connection, int billId) throws SQLException {
        PreparedStatement pstm = null;
        int updateRows = 0;
        if (connection != null) {
            String sql = "delete from smbms_bill where id=?";
            Object[] params = {billId};
            updateRows = BaseDao.executeUpdate(connection, pstm, sql, params);
            BaseDao.closeResource(null, pstm, null);
        }
        return updateRows;
    }
}
