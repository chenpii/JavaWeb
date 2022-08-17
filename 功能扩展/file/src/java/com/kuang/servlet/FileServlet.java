package com.kuang.servlet;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //判断上传的的文件是普通表单还是文件表单
        //isMultipartContent判断请求消息中的内容是否是“multipart/form-data”类型，是则返回true，否则返回false
        if (!ServletFileUpload.isMultipartContent(request)) {
            return;//说明是个普通表单，直接返回
        }
        try {
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

        /*
        ServletFileUpload负责处理上传的文件数据，并将表单中每个输入项封装成一个FileItem对象，
        在使用ServletFileUpload对象解析请求时需要DiskFileItemFactory对象。
        所以，我们需要在进行解析工作前构造DiskFileItemFactory对象，
        通过ServletFileUpload对象的构造方法或setFileItemFactory()方法
        设置ServletFileUpload对象的fileItemFactory属性。
         */

            //1.创建DiskFileItemFactory（磁盘文件工厂）对象，处理文件上传的路径或者大小限制。
            DiskFileItemFactory factory = getDiskFileItemFactory(tmpPFile);

            //2.创建ServletFileUpload对象
            ServletFileUpload upload = getServletFileUpload(factory);

            //3.处理上传的文件
            //把前端请求解析，封装成一个FileItem对象，需要从ServletFileUpload对象中获取
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileItem : fileItems) {

            }

        } catch (FileUploadException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static DiskFileItemFactory getDiskFileItemFactory(File file) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //通过这个工厂设置一个缓冲区，当上传的文件大于这个缓冲区的时候，就将它放到临时文件中
        factory.setSizeThreshold(1024 * 1024);//缓冲区大小为1M
        factory.setRepository(file);//临时目录的保存目录，需要一个file
        return factory;
    }

    public static ServletFileUpload getServletFileUpload(DiskFileItemFactory factory) {

        ServletFileUpload upload = new ServletFileUpload(factory);
        //监听文件上传进度
        upload.setProgressListener(new ProgressListener() {
            /**
             *
             * @param pBytesRead 已经读取到的文件大小
             * @param pContentLength 文件大小
             * @param pItems
             */
            @Override
            public void update(long pBytesRead, long pContentLength, int pItems) {
                System.out.println("总大小：" + pContentLength + ",已上传：" + pBytesRead);
            }
        });

        //处理乱码问题
        upload.setHeaderEncoding("UTF-8");
        //设置单个文件的最大值
        upload.setFileSizeMax(1024 * 1024 * 10);
        //设置总共能够上传文件的大小
        //1024 = 1kb * 1024 = 1M * 10 = 10M
        upload.setSizeMax(1024 * 1024 * 10);

        return upload;
    }
}
