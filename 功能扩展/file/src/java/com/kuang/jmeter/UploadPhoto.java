package com.kuang.jmeter;

import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class UploadPhoto {
    @Test
    public void transPhotoToBase64() throws IOException {
        File directory = new File("");//设定为当前文件夹
        System.out.println(directory.getCanonicalPath());
        String imgFile = "I:\\Scm_SmartEP\\trunk\\01.开发库\\060.测试\\09_Jmeter接口测试脚本\\融合平台接口测试环境\\jmeter脚本\\data\\import\\facephoto.jpeg";
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
        String photo = encoder.encodeToString(data);
        System.out.println(photo);
    }
}
