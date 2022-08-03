package com.kuang.servlet.provider;

import com.kuang.pojo.Provider;
import com.kuang.service.provider.ProviderService;
import com.kuang.service.provider.ProviderServiceImpl;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        req.getParameter("id");
        req.getParameter("proCode");
        req.getParameter("proName");
        req.getParameter("proContact");
        req.getParameter("proPhone");
        req.getParameter("proAddress");
        req.getParameter("proFax");
        req.getParameter("proDesc");
        
    }
}
