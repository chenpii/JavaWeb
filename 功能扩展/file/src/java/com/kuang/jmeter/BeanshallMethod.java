package com.kuang.jmeter;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.math.BigDecimal;


public class BeanshallMethod {

    @Test
    public void testISBlankStr() {
        String str1 = "";
        String str2 = null;
        String str3 = "abc";
        System.out.println(StringUtils.isNotBlank(str1));
        System.out.println(StringUtils.isNotBlank(str2));
        System.out.println(StringUtils.isNotBlank(str3));
        System.out.println(str1 instanceof String);
    }

    @Test
    public void testBigDecimal(){
        BigDecimal expect = new BigDecimal("0");
        BigDecimal actual = new BigDecimal("0.0");
        BigDecimal actual2 = new BigDecimal("0.01");
        System.out.println(expect.toString());
        System.out.println(actual.toString());
        if (expect.compareTo(actual) == 0) {
            System.out.println("相等");
        } else {
            System.out.println("不相等");
        }

    }
    @Test
    public void testJSON() throws JSONException {
        JSONObject responseObj = new JSONObject("{\"code\":\"0\",\"data\":{\"consumeMgFee\":\"0.0\",\"monTrans\":\"0.01\",\"accNum\":\"43662\",\"perCode\":\"071901\",\"balance\":\"124.72\",\"accName\":\"ch接口测试4\",\"concessionsMon\":\"0.0\",\"sign\":\"ff51f7e14040b92d96dd478c8166fbda\",\"actualMonTrans\":\"0.01\",\"recId\":\"88611\"},\"msg\":\"请求成功\"}");
        JSONObject data = responseObj.getJSONObject("data");
        String consumeMgFee = data.getString("consumeMgFee");
     }

}
