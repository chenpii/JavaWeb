package com.kuang.servlet.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.kuang.pojo.Role;
import com.kuang.pojo.User;
import com.kuang.service.role.RoleService;
import com.kuang.service.role.RoleServiceImpl;
import com.kuang.service.user.UserService;
import com.kuang.service.user.UserServiceImpl;
import com.kuang.util.Constants;
import com.kuang.util.PageSupport;
import com.mysql.cj.util.StringUtils;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenpi
 * @create 2022-07-25 21:41
 */
//实现Servlet复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method != null && method.equals("savepwd")) {
            this.updatePwd(req, resp);
        } else if (method != null && method.equals("pwdmodify")) {
            this.pwdmodify(req, resp);
        } else if (method != null && method.equals("query")) {
            this.query(req, resp);
        } else if (method != null && method.equals("add")) {
            this.add(req, resp);
        } else if (method != null && method.equals("deluser")) {
            this.deluser(req, resp);
        } else if (method != null && method.equals("getrolelist")) {
            this.getRoleList(req, resp);
        } else if (method != null && method.equals("ucexist")) {
            this.userCodeExist(req, resp);
        } else if (method != null && method.equals("view")) {
            this.getUserById(req, resp, "userview.jsp");
        } else if (method != null && method.equals("modify")) {
            this.getUserById(req, resp, "usermodify.jsp");
        } else if (method != null && method.equals("modifyexe")) {
            this.modifyUser(req, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * 修改密码
     *
     * @param req
     * @param resp
     */
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) {
        //从Session中获取user
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        boolean flag = false;

        if (o != null && (!StringUtils.isNullOrEmpty(newpassword))) {
            UserService userService = new UserServiceImpl();
            User user = (User) o;
            flag = userService.updatePwd(user.getId(), newpassword);
            if (flag) {
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
                //密码修改成功，移除Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            } else {
                req.setAttribute("message", "修改密码失败");
            }
        } else {
            req.setAttribute("message", "新密码有问题");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证输入的旧密码
     *
     * @param req
     * @param resp
     */
    public void pwdmodify(HttpServletRequest req, HttpServletResponse resp) {
        //从Session中获取密码
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        System.out.println("pwdmodify:" + ((User) o).getUserPassword());
        System.out.println("pwdmodify:" + oldpassword);

        //万能的map
        Map<String, String> resultMap = new HashMap<String, String>();

        if (o == null) {//Session失效，或者Session过期
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) { //旧密码输入为空
            resultMap.put("result", "error");
        } else {
            if (((User) o).getUserPassword().equals(oldpassword)) {//旧密码跟原密码一样
                resultMap.put("result", "true");
            } else {
                resultMap.put("result", "false");//旧密码跟原密码不一样
            }
        }

        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray : 阿里巴巴Json工具类 转换格式
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户管理页面查询
     *
     * @param req
     * @param resp
     */
    //重点、难点
    private void query(HttpServletRequest req, HttpServletResponse resp) {

        //查询用户列表

        //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        UserService userService = new UserServiceImpl();
        List<User> userList = null;

        //第一次走这个请求，一定是第一页，页面大小固定的
        int pageSize = Constants.USER_LIST_PAGESIZE;
        int currentPageNo = 1;

        if (queryUserName == null) {
            queryUserName = "";
        }
        if (!StringUtils.isNullOrEmpty(temp)) {
            queryUserRole = Integer.parseInt(temp); //给查询赋值 0,1,2,3
        }
        if (!StringUtils.isNullOrEmpty(pageIndex)) {
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总数(分页：存在上一页、下一页的情况)
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setPageSize(pageSize);//需先赋值，否则默认值为0报 除0异常
        pageSupport.setTotalCount(totalCount);
        pageSupport.setCurrentPageNo(currentPageNo);

        //获取总页数
        int totalPageCount = pageSupport.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {//当前页小于1，显示首页
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) { //当前页大于总页数，显示尾页
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList", userList);

        //获取用户角色列表
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList", roleList);

        req.setAttribute("totalCount", totalCount);
        req.setAttribute("currentPageNo", currentPageNo);
        req.setAttribute("totalPageCount", totalPageCount);

        req.setAttribute("queryUserName", queryUserName);//每次查询完保留查询条件
        req.setAttribute("queryUserRole", queryUserRole);

        //返回页面
        try {
            req.getRequestDispatcher("/jsp/userlist.jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 新增用户
     *
     * @param req
     * @param resp
     */
    private void add(HttpServletRequest req, HttpServletResponse resp) {

        //获取Session
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        int createBy = 0;
        if (o != null) {
            User createUser = (User) o;
            createBy = createUser.getId();
        }

        //从前端获取待新建用户的数据
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        int gender = Integer.parseInt(req.getParameter("gender"));
        Date birthday = com.kuang.util.StringUtils.transferString2Date(req.getParameter("birthday"));
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int userRole = Integer.parseInt(req.getParameter("userRole"));
        Date creationDate = new Date();

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(userRole);
        user.setCreatedBy(createBy);
        user.setCreationDate(creationDate);

        UserService userService = new UserServiceImpl();
        if (userService.addUser(user)) {
            try {
                resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                req.getRequestDispatcher("useradd.jsp").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除用户
     *
     * @param req
     * @param resp
     */
    private void deluser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("uid");
        int userId = 0;
        try {
            userId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        HashMap<String, String> resultMap = new HashMap<String, String>();
        UserService userService = new UserServiceImpl();
        if (userId < 0) {
            resultMap.put("delResult", "notexist");
        } else if (userService.delUserById(userId)) {
            resultMap.put("delResult", "true");
        } else {
            resultMap.put("delResult", "false");
        }

        //把resultMap转换成json返回
        resp.setContentType("application/json");
        PrintWriter outWriter = resp.getWriter();
        outWriter.write(JSONArray.toJSONString(resultMap));
        outWriter.flush();
        outWriter.close();

    }

    /**
     * 获取角色列表
     *
     * @param req
     * @param resp
     */
    private void getRoleList(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //获取用户角色列表
        RoleService roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList", roleList);

        //把roleList转换成json返回
        resp.setContentType("application/json");
        PrintWriter outWriter = resp.getWriter();
        outWriter.write(JSONArray.toJSONString(roleList));
        outWriter.flush();
        outWriter.close();

    }

    /**
     * 根据userCode查询用户是否存在
     *
     * @param req
     * @param resp
     */
    private void userCodeExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userCode = req.getParameter("userCode");

        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            resultMap.put("userCode", "exist");
        } else {
            UserService userService = new UserServiceImpl();
            User user = userService.userCodeExist(userCode);
            if (user != null) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "notexist");
            }
        }

        //转成json返回
        resp.setContentType("application/json");
        PrintWriter outWriter = resp.getWriter();
        outWriter.write(JSONArray.toJSONString(resultMap));
        outWriter.flush();
        outWriter.close();
    }

    /**
     * 根据userId获取用户
     *
     * @param req
     * @param resp
     */
    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String url) throws  IOException {
        String id = req.getParameter("uid");
        int userId = 0;
        if (!StringUtils.isNullOrEmpty(id)) {
            try {
                userId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        UserService userService = new UserServiceImpl();
        User user = userService.getUserById(userId);
        req.setAttribute("user", user);
        try {
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新用户信息
     *
     * @param req
     * @param resp
     */
    private void modifyUser(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        //获取前端修改后的用户信息
        int uid = Integer.parseInt(req.getParameter("uid"));
        String userName = req.getParameter("userName");
        int gender = Integer.parseInt(req.getParameter("gender"));
        Date birthday = com.kuang.util.StringUtils.transferString2Date(req.getParameter("birthday"));
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int userRole = Integer.parseInt(req.getParameter("userRole"));

        User user = new User();
        user.setId(uid);
        user.setUserName(userName);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(userRole);

        //获取修改者id和修改时间
        User modifyUser = (User) req.getSession().getAttribute(Constants.USER_SESSION);
        user.setModifyBy(modifyUser.getId());
        user.setModifyDate(new Date());

        UserService userService = new UserServiceImpl();
        if(userService.modifyUser(user)){//修改成功
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        }else {
            try {
                req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }

    }
}
