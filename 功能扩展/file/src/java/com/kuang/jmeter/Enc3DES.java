package com.kuang.jmeter;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
import java.net.URLEncoder;

public class Enc3DES {

    public static String getEnc3DES(String key, String iv, String data) {
        try {
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            IvParameterSpec ivs = new IvParameterSpec(iv.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            cipher.init(Cipher.ENCRYPT_MODE, securekey, ivs);
            BASE64Encoder base64Encoder = new BASE64Encoder();
            //return base64Encoder.encode(cipher.doFinal(data.getBytes("UTF-8")));
            return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
        } catch (Exception e) {
            return "";
        }
    }

    public static String encode(String key, String ivText, String data) throws Exception {
        String ALGORITHM_DES = "DESede/CBC/PKCS5Padding";
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(ivText.getBytes("UTF-8"));
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            return "";
        }
    }

    @Test
    public void transPswTo3DES() throws Exception {

        System.out.println(getEnc3DES("abcdefgh12345678abcdefgh", "12345678", "api"));//例子
        System.out.println(getEnc3DES("0D43C58D9CC7DDEFD0BAD0A5", "A7EBFC87", "888888"));
        System.out.println(getEnc3DES("0D43C58D9CC7DDEFD0BAD0A5", "A7EBFC87", "123456"));
        System.out.println("=====================================");
        System.out.println(encode("abcdefgh12345678abcdefgh", "12345678", "api"));//例子
        System.out.println(encode("0D43C58D9CC7DDEFD0BAD0A5", "A7EBFC87", "888888"));
        System.out.println(encode("0D43C58D9CC7DDEFD0BAD0A5", "A7EBFC87", "123456"));


        String oripwdEnCode = URLEncoder.encode("C8+6gXnVYjo=", "UTF-8");
        System.out.println(oripwdEnCode);
        oripwdEnCode = URLEncoder.encode(oripwdEnCode, "UTF-8");
        System.out.println(oripwdEnCode);

    }

}
