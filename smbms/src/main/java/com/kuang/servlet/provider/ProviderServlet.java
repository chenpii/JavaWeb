package com.kuang.servlet.provider;

import com.kuang.pojo.Provider;
import com.kuang.pojo.User;
import com.kuang.service.provider.ProviderService;
import com.kuang.service.provider.ProviderServiceImpl;
import com.kuang.util.Constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.util.Date;
import java.util.List;

public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (!StringUtils.isNullOrEmpty(method)) {
            if (method.equals("query")) {
                this.query(req, resp);
            }
            if (method.equals("view")) {
                this.getProviderById(req, resp, "/jsp/providerview.jsp");
            }
            if (method.equals("modify")) {
                this.getProviderById(req, resp, "/jsp/providermodify.jsp");
            }
            if (method.equals("modifysave")) {
                this.modifyProvider(req, resp);
            }
            if (method.equals("add")) {

                this.addProvider(req, resp);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 获取供应商列表
     *
     * @param req
     * @param resp
     */
    private void query(HttpServletRequest req, HttpServletResponse resp) {
        //前端获取查询条件
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");

        //获取供应商列表
        ProviderService providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList(queryProCode, queryProName);

        req.setAttribute("providerList", providerList);

        //保留查询条件返回
        req.setAttribute("queryProCode", queryProCode);
        req.setAttribute("queryProName", queryProName);

        try {
            req.getRequestDispatcher("/jsp/providerlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看供应商信息
     *
     * @param req
     * @param resp
     */
    private void getProviderById(HttpServletRequest req, HttpServletResponse resp, String url) {
        String id = req.getParameter("proid");
        int proid = 0;
        try {
            proid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ProviderService providerService = new ProviderServiceImpl();
        Provider provider = providerService.getProviderById(proid);
        req.setAttribute("provider", provider);

        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 修改供应商信息
     *
     * @param req
     * @param resp
     */
    private void modifyProvider(HttpServletRequest req, HttpServletResponse resp) {
        //获取供应商信息
        Provider provider = new Provider();
        provider.setId(Integer.valueOf(req.getParameter("id")));
        provider.setProCode(req.getParameter("proCode"));
        provider.setProName(req.getParameter("proName"));
        provider.setProContact(req.getParameter("proContact"));
        provider.setProPhone(req.getParameter("proPhone"));
        provider.setProAddress(req.getParameter("proAddress"));
        provider.setProFax(req.getParameter("proFax"));
        provider.setProDesc(req.getParameter("proDesc"));

        //获取修改者信息
        User modifyUser = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        provider.setModifyBy(modifyUser.getId());
        provider.setModifyDate(new Date());

        ProviderService providerService = new ProviderServiceImpl();
        if (providerService.modifyProvider(provider)) {

            //成功，重定向到查询页面
            try {
                resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //失败，转发到修改页面
            try {
                req.getRequestDispatcher("/jsp/providermodify.jsp").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 新增供应商
     *
     * @param req
     * @param resp
     */
    private void addProvider(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("ProviderServlet-->addProvider");
        //获取请求信息
        Provider provider = new Provider();
        provider.setProCode(req.getParameter("proCode"));
        provider.setProName(req.getParameter("proName"));
        provider.setProContact(req.getParameter("proContact"));
        provider.setProPhone(req.getParameter("proPhone"));
        provider.setProAddress(req.getParameter("proAddress"));
        provider.setProFax(req.getParameter("proFax"));
        provider.setProDesc(req.getParameter("proDesc"));

        //获取创建者信息
        User creator = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        provider.setCreatedBy(creator.getId());
        provider.setCreationDate(new Date());

        ProviderService providerService = new ProviderServiceImpl();
        if (providerService.addProvider(provider)) {
            //成功，重定向到查询页面
            try {
                resp.sendRedirect(req.getContextPath() + "/jsp/provider.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //失败，转发到新建页面
            req.setAttribute("provider", provider);
            try {
                req.getRequestDispatcher("/jsp/provideradd.jsp").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
