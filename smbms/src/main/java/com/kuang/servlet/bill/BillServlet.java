package com.kuang.servlet.bill;

import com.alibaba.fastjson.JSONArray;
import com.kuang.pojo.Bill;
import com.kuang.pojo.Provider;
import com.kuang.pojo.User;
import com.kuang.service.bill.BillService;
import com.kuang.service.bill.BillServiceImpl;
import com.kuang.service.provider.ProviderService;
import com.kuang.service.provider.ProviderServiceImpl;
import com.kuang.util.Constants;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
            if (method.equals("view")) {
                this.getBillById(req, resp, "/jsp/billview.jsp");
            }
            if (method.equals("modify")) {
                this.getBillById(req, resp, "/jsp/billmodify.jsp");
            }
            if (method.equals("getproviderlist")) {
                this.getproviderlist(req, resp);
            }
            if (method.equals("add")) {
                this.addBill(req, resp);
            }
            if (method.equals("modifysave")) {
                this.modifySave(req, resp);
            }
            if (method.equals("delbill")) {
                this.delBill(req, resp);
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
        req.setAttribute("queryProductName", queryProductName);
        req.setAttribute("queryProviderId", ProviderId);
        req.setAttribute("queryIsPayment", IsPayment);
        try {
            req.getRequestDispatcher("/jsp/billlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据id获取订单信息
     *
     * @param req
     * @param resp
     * @param url  跳转路径
     */
    private void getBillById(HttpServletRequest req, HttpServletResponse resp, String url) {
        String id = req.getParameter("billid");
        int billid = 0;
        try {
            billid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        BillService billService = new BillServiceImpl();
        Bill bill = billService.getBillById(billid);
        req.setAttribute("bill", bill);

        //ProviderService providerService = new ProviderServiceImpl();
        //List<Provider> providerList = providerService.getProviderList(null, null);
        //req.setAttribute("providerList", providerList);

        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getproviderlist(HttpServletRequest req, HttpServletResponse resp) {

        ProviderService providerService = new ProviderServiceImpl();
        List<Provider> providerList = providerService.getProviderList(null, null);
        req.setAttribute("providerList", providerList);

        resp.setContentType("application/json");

        try {
            PrintWriter outWriter = resp.getWriter();
            outWriter.write(JSONArray.toJSONString(providerList));
            outWriter.flush();
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增订单
     *
     * @param req
     * @param resp
     */
    private void addBill(HttpServletRequest req, HttpServletResponse resp) {
        //获取订单信息
        String billCode = req.getParameter("billCode");
        String productName = req.getParameter("productName");
        String productUnit = req.getParameter("productUnit");
        BigDecimal productCount = new BigDecimal(req.getParameter("productCount"));
        BigDecimal totalPrice = new BigDecimal(req.getParameter("totalPrice"));
        int providerId = Integer.parseInt(req.getParameter("providerId"));
        int isPayment = Integer.parseInt(req.getParameter("isPayment"));
        //获取新建信息
        User creater = (User) req.getSession().getAttribute(Constants.USER_SESSION);

        Bill bill = new Bill();
        bill.setBillCode(billCode);
        bill.setProductName(productName);
        bill.setProductUnit(productUnit);
        bill.setProductCount(productCount);
        bill.setTotalPrice(totalPrice);
        bill.setProviderId(providerId);
        bill.setIsPayment(isPayment);
        bill.setCreatedBy(creater.getId());
        bill.setCreationDate(new Date());

        BillService billService = new BillServiceImpl();
        if (billService.addBill(bill)) {
            try {
                resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("billCode", billCode);
            req.setAttribute("productName", productName);
            req.setAttribute("productUnit", productUnit);
            req.setAttribute("productCount", productCount);
            req.setAttribute("totalPrice", totalPrice);
            req.setAttribute("providerId", providerId);
            req.setAttribute("isPayment", isPayment);
            req.getRequestDispatcher("/jsp/billmodify.jsp");
        }
    }

    /**
     * 修改订单信息
     *
     * @param req
     * @param resp
     */
    private void modifySave(HttpServletRequest req, HttpServletResponse resp) {

        User modifier = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        Bill bill = new Bill();
        bill.setId(Integer.valueOf(req.getParameter("id")));
        bill.setBillCode(req.getParameter("billCode"));
        bill.setProductName(req.getParameter("productName"));
        bill.setProductUnit(req.getParameter("productUnit"));
        bill.setProductCount(new BigDecimal(req.getParameter("productCount")));
        bill.setTotalPrice(new BigDecimal(req.getParameter("totalPrice")));
        bill.setProviderId(Integer.parseInt(req.getParameter("providerId")));
        bill.setIsPayment(Integer.valueOf(req.getParameter("isPayment")));
        bill.setModifyBy(modifier.getId());
        bill.setModifyDate(new Date());

        BillService billService = new BillServiceImpl();

        if (billService.modifyBill(bill)) {
            try {
                resp.sendRedirect(req.getContextPath() + "/jsp/bill.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                req.getRequestDispatcher("/jsp/billmodify.jsp").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除订单
     *
     * @param req
     * @param resp
     */
    private void delBill(HttpServletRequest req, HttpServletResponse resp) {

        String billid = req.getParameter("billid");
        int billId = 0;
        if (!StringUtils.isNullOrEmpty(billid)) {
            billId = Integer.parseInt(billid);
        }

        //返回结果
        HashMap<String, String> resultMap = new HashMap<String, String>();
        BillService billService = new BillServiceImpl();
        Bill getBill = billService.getBillById(billId);

        if (getBill == null) {//订单不存在

            resultMap.put("delResult", "notexist");

        } else if (billService.delBill(billId)) {//订单删除成功

            resultMap.put("delResult", "true");
        } else {

            resultMap.put("delResult", "false");
        }

        resp.setContentType("application/json");
        PrintWriter outWriter = null;
        try {
            outWriter = resp.getWriter();
            outWriter.write(JSONArray.toJSONString(resultMap));
            outWriter.flush();
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
