package com.kuang.servlet;


import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //判断上传的的文件是普通表单还是文件表单
        //isMultipartContent判断请求消息中的内容是否是“multipart/form-data”类型，是则返回true，否则返回false
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;//说明是个普通表单，直接返回
        }

        //创建上传文件的保存路径，建议在WEB-INF路径下，安全，用户无法直接访问上传的文件；
        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload");
        File uploadFile = new File(uploadPath);
        if (!uploadFile.exists()) {
            uploadFile.mkdir();//创建目录
        }

        //缓存，临时路径，加入文件超过了预期的大小，我们就把他放到一个临时文件中，过几天自动删除，或者提醒用户转存为永久
        String tmpPath = this.getServletContext().getRealPath("/WEB-INF/tmp");
        File tmpPFile = new File(tmpPath);
        if (!tmpPFile.exists()) {
            tmpPFile.mkdir();//创建目录
        }
        //处理上传的文件，一般都需要通过流来获取，我们可以使用request.getInputStream()，原生态的文件上传流被获取，十分麻烦
        //但是我们都建议使用Apache的文件上传组件来实现，common-fileupload，它需要依赖common-io组件

        //1.创建
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
