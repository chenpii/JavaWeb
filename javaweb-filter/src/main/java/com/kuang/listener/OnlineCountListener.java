package com.kuang.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 统计网站在线人数：统计session
 *
 * @author chenpi
 * @create 2022-07-21 10:44
 */
public class OnlineCountListener implements HttpSessionListener {
    //创建session的监听：一旦创建一个session，就会触发一次这个事件
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
        System.out.println("session创建，id：" + httpSessionEvent.getSession().getId());
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");
        if (onlineCount == null) {
            onlineCount = new Integer(1);
        } else {
            int count = onlineCount.intValue();
            onlineCount = new Integer(count + 1);
        }
        System.out.println("OnlineCount:" + onlineCount);

        servletContext.setAttribute("OnlineCount", onlineCount);
    }

    //销毁session的监听：一旦销毁一个session，就会触发一次这个事件
    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        ServletContext servletContext = httpSessionEvent.getSession().getServletContext();
        httpSessionEvent.getSession().invalidate();
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");

        if (onlineCount == null) {
            onlineCount = new Integer(0);
        } else {
            int count = onlineCount.intValue();
            onlineCount = new Integer(count - 1);
        }

        servletContext.setAttribute("OnlineCount", onlineCount);
        System.out.println("session销毁");
    }

    /*
    session销毁的2中情况：
    1.手动销毁 getSession().invalidate();
    2.自动销毁 web.xml配置失效时间
     */
}
