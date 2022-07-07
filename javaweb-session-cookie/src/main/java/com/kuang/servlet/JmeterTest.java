package com.kuang.servlet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author chenpi
 * @create 2022-07-07 19:38
 */
public class JmeterTest {

    @Test
    /**
     * 随机生成2位小数的交易金额，并保证最后一位不为0
     */
    public void makeRandomMonTrans() {
        Random random = new Random();
        double v = random.nextDouble() * 10;
        String monTrans = new DecimalFormat("0.00").format(v);
        //如果最后一位是0的话，加0.01
        if (monTrans.lastIndexOf('0') == 3) {
            BigDecimal decimal = new BigDecimal(monTrans);
            BigDecimal decimal1 = new BigDecimal("0.01");
            monTrans = decimal.add(decimal1).doubleValue() + "";

        }
        System.out.println("最终交易金额：" + monTrans);
    }

    @Test
    /**
     * 生成比余额大0.01的交易金额，并保持精度
     *
     */
    public void MakeBiggerMonTrans() {
        BigDecimal p1 = new BigDecimal(Double.toString(100.1));
        BigDecimal p2 = new BigDecimal(Double.toString(0.01));
        BigDecimal p3 = new BigDecimal(Double.toString(0.01));

        String monTrans = p1.add(p2).add(p3).doubleValue() + "";
        System.out.println(monTrans);

    }

}
