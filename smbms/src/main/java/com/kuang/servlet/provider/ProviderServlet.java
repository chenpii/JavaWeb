package com.kuang.servlet.provider;

import com.kuang.pojo.Provider;
import com.kuang.service.provider.ProviderService;
import com.kuang.service.provider.ProviderServiceImpl;
import com.kuang.util.Constants;
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
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void query(HttpServletRequest req, HttpServletResponse resp) {
        String queryProCode = req.getParameter("queryProCode");
        String queryProName = req.getParameter("queryProName");

        ProviderService providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList(queryProCode, queryProName);

        req.setAttribute("providerList", providerList);

        try {
            req.getRequestDispatcher("/jsp/providerlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
