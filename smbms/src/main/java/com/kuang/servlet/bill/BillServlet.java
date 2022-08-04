package com.kuang.servlet.bill;

import com.kuang.pojo.Bill;
import com.kuang.pojo.Provider;
import com.kuang.service.bill.BillService;
import com.kuang.service.bill.BillServiceImpl;
import com.kuang.service.provider.ProviderService;
import com.kuang.service.provider.ProviderServiceImpl;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("BillServlet->doGet");
        String method = req.getParameter("method");
        if (!StringUtils.isNullOrEmpty(method)) {
            if (method.equals("query")) {
                this.query(req, resp);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 查询订单列表
     *
     * @param req
     * @param resp
     */
    private void query(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("BillServlet->query");
        String queryProductName = req.getParameter("queryProductName");
        String queryProviderId = req.getParameter("queryProviderId");
        String queryIsPayment = req.getParameter("queryIsPayment");
        int ProviderId = 0;
        int IsPayment = 0;
        if (!StringUtils.isNullOrEmpty(queryProviderId)) {
            ProviderId = Integer.parseInt(queryProviderId);
        }
        if (!StringUtils.isNullOrEmpty(queryIsPayment)) {
            IsPayment = Integer.parseInt(queryIsPayment);
        }

        BillService billService = new BillServiceImpl();
        List<Bill> billList = billService.getBillList(queryProductName, ProviderId, IsPayment);

        ProviderService providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList(null, null);

        req.setAttribute("billList", billList);
        req.setAttribute("providerList", providerList);
        req.setAttribute("queryProductName",queryProductName);
        req.setAttribute("queryProviderId",ProviderId);
        req.setAttribute("queryIsPayment",IsPayment);
        try {
            req.getRequestDispatcher("/jsp/billlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
