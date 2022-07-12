package com.kuang.servlet;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @author chenpi
 * @create 2022-07-07 19:38
 */
public class JmeterTest {

    @Test
    /**
     * 随机生成2位小数的交易金额，并保证不为0，并去掉多余的0
     */
    public void makeRandomMonTrans() {
        Random random = new Random();
        double v = random.nextDouble() * 10;
//        BigDecimal monTrans = new BigDecimal(v).setScale(2,BigDecimal.ROUND_DOWN).stripTrailingZeros();
        BigDecimal monTrans = new BigDecimal("00");
        boolean flag = true;
        while (flag) {
            if (monTrans.compareTo(BigDecimal.ZERO) == 0) {
                //重新随机
                v = random.nextDouble() * 10;
                monTrans = new BigDecimal(v).setScale(2, BigDecimal.ROUND_DOWN).stripTrailingZeros();
            } else {
                flag = false;
            }
        }

        System.out.println("交易金额：" + monTrans.toString());
    }

    @Test
    /**
     * 生成比余额大0.01的交易金额，并保持精度
     *
     */
    public void makeBiggerMonTrans() {
        BigDecimal p1 = new BigDecimal(Double.toString(100.1));
        BigDecimal p2 = new BigDecimal(Double.toString(0.01));
        BigDecimal p3 = new BigDecimal(Double.toString(0.01));

        String monTrans = p1.add(p2).add(p3).doubleValue() + "";
        System.out.println(monTrans);

    }

    /**
     * 计算消费管理费
     */
    @Test
    public void getConsumeMgFee() {
        //交易金额
        BigDecimal monTrans = new BigDecimal("8.00");
        //实际交易金额
        BigDecimal actualMonTrans = monTrans;
        //消费管理费
        BigDecimal consumeMgFee = null;

//        String ChargeFeeMode = vars.get("ChargeFeeMode_1");
//        String ChargeFeeMoney = vars.get("ChargeFeeMoney_1");
        //收取模式
        String ChargeFeeMode = "1";
        //收取额度
        String ChargeFeeMoney = "20";

        if (ChargeFeeMode != null) {
            //按比例收取消费管理费
            if (ChargeFeeMode.equals("1")) {
                consumeMgFee = monTrans.multiply(new BigDecimal(ChargeFeeMoney)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_DOWN);

            } else if (ChargeFeeMode.equals("2")) {
                //定额收取消费管理费
                consumeMgFee = new BigDecimal(ChargeFeeMoney);

            }
        }
        System.out.println("交易金额：" + monTrans);
        System.out.println("消费管理费：" + consumeMgFee);
        actualMonTrans = monTrans.add(consumeMgFee).stripTrailingZeros();
        System.out.println("实际交易金额：" + actualMonTrans);
    }

}
