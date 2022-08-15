package com.kuang.jmeter;

import org.junit.Test;

import java.util.*;

public class RandomStr {
    public String getRandomStr(int leng) {
        UUID uuid = UUID.randomUUID();//获取UID的值
        System.out.println(uuid);
        //去掉“-”
        String uuidSub = uuid.toString().replaceAll("-", "").substring(0, leng);
        return uuidSub;
    }

    public String getRandomIdCard() {

        StringBuilder generater = new StringBuilder();
        int sex = 0; // 1为男 0 为女
        int age = 1979; //1979为大于18岁 2000小于18岁

        //地区号
        Map areaCode = new HashMap();
        areaCode.put("浙江省", 330000);
        areaCode.put("杭州市", 330100);
        areaCode.put("市辖区", 330101);
        areaCode.put("上城区", 330102);
        areaCode.put("下城区", 330103);
        areaCode.put("江干区", 330104);
        areaCode.put("拱墅区", 330105);
        areaCode.put("西湖区", 330106);
        areaCode.put("滨江区", 330108);
        areaCode.put("萧山区", 330109);
        areaCode.put("余杭区", 330110);
        areaCode.put("桐庐县", 330122);
        areaCode.put("淳安县", 330127);
        areaCode.put("建德市", 330182);
        areaCode.put("富阳市", 330183);
        areaCode.put("临安市", 330185);
        String randomAreaCode = "";
        int index = (int) (Math.random() * areaCode.size());
        while (index==0){
            index = (int) (Math.random() * areaCode.size());
        }
        Collection values = areaCode.values();
        Iterator it = values.iterator();
        int j = 0;
        int code = 0;
        while (j < index && it.hasNext()) {
            j++;
            randomAreaCode = it.next().toString();
        }
        generater.append(randomAreaCode);

        //生日
        String randomBirthday = "";
        Calendar birthday = Calendar.getInstance();
        birthday.set(Calendar.YEAR, (int) (Math.random() * 20) + age);
        birthday.set(Calendar.MONTH, (int) (Math.random() * 12));
        birthday.set(Calendar.DATE, (int) (Math.random() * 31));
        StringBuilder builder = new StringBuilder();
        builder.append(birthday.get(Calendar.YEAR));
        long month = birthday.get(Calendar.MONTH) + 1;
        if (month < 10) {
            builder.append("0");
        }
        builder.append(month);
        long date = birthday.get(Calendar.DATE);
        if (date < 10) {
            builder.append("0");
        }
        builder.append(date);
        randomBirthday = builder.toString();
        generater.append(randomBirthday);

        //随机码
        String randomCode = "";
        code = (int) (Math.random() * 1000);
        if (code < 10) {
            //  randomCode= "00" + code;
            if (code % 2 == sex) {
                randomCode = "00" + code;
            } else {
                code = code + 1;
                randomCode = "00" + code;
            }
        } else if (code < 100) {
            // randomCode= "0" + code;
            if (code % 2 == sex) {
                randomCode = "0" + code;
            } else {
                code = code + 1;
                randomCode = "0" + code;
            }
        } else {
            // randomCode= "" + code;
            if (code % 2 == sex) {
                randomCode = "" + code;
            } else {
                code = code + 1;
                randomCode = "" + code;
            }
        }
        generater.append(randomCode);

        //验证码
        char[] chars = generater.toString().toCharArray();
        int[] c = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        char[] r = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int[] n = new int[17];
        int result = 0;
        for (int i = 0; i < n.length; i++) {
            n[i] = Integer.parseInt(chars[i] + "");
        }
        for (int i = 0; i < n.length; i++) {
            result += c[i] * n[i];
        }
        char validateCode = r[result % 11];
        generater.append(validateCode);

        //vars.put("IdCard_Random", generater.toString());
        //SampleResult.setResponseData(generater.toString());
        return generater.toString();
    }

    @Test
    public void test() {

        //System.out.println(getRandomStr(6));
        //System.out.println(getRandomStr(7));
 int sex= Integer.parseInt("1");
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandomIdCard());
        }
    }
}
