package com.kuang.jmeter;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class Enc3DES {
    @Test
    public void transPswTo3DES() throws Exception {
        String key = "abcdefgh12345678abcdefgh";
        String iv = "12345678";
        String value = "api";

        System.out.println(getEnc3DES(value, key, iv));
    }

    public static String getEnc3DES(String data, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("utf-8"));
        IvParameterSpec ivs = new IvParameterSpec(iv.getBytes("utf-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey securekey = keyFactory.generateSecret(dks);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, ivs);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(cipher.doFinal(data.getBytes("utf-8")));
    }

}
