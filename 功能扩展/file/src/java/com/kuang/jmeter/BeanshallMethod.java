package com.kuang.jmeter;


import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

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

}
