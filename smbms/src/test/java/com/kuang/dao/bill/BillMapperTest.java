package com.kuang.dao.bill;

import com.kuang.pojo.Bill;
import com.kuang.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class BillMapperTest {

    @Test
    public void getBillList() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);

        List<Bill> billList = mapper.getBillList("%海%", 1, 1);
        for (Bill bill : billList) {
            System.out.println(bill);
        }

        sqlSession.close();
    }

    @Test
    public void getBillCountByProviderId() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BillMapper mapper = sqlSession.getMapper(BillMapper.class);

        int billCountByProviderId = mapper.getBillCountByProviderId(9);
        System.out.println(billCountByProviderId);

        sqlSession.close();
    }
}
