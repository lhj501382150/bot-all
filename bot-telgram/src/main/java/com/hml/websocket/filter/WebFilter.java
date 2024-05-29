package com.hml.websocket.filter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
 
@javax.servlet.annotation.WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
@Order(1)
@Component
public class WebFilter implements Filter {
	
	 private static final int CONNECTION_RATE_LIMIT = 10; // 每秒允许的最大连接数
	 private static final int CONNECTION_RATE_INTERVAL = 1; // 时间间隔（秒）
	 
	 private static final ConcurrentHashMap<String, Long> connectionTimes = new ConcurrentHashMap<>();
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        session.setAttribute("user_ip", req.getRemoteHost());//获取ip存入session
        System.out.println(req.getRemoteHost());
    }
    
    public boolean checkAuth(String ip) {
    	String remoteAddress = ip;
        long currentTime = System.currentTimeMillis();
 
        Long lastConnectionTime = connectionTimes.get(remoteAddress);
        if (lastConnectionTime != null && currentTime - lastConnectionTime < CONNECTION_RATE_INTERVAL * 1000) {
            // 如果与同一地址在指定时间内的连接数太多，则关闭新连接
            session.close(4001, "Connection rate limit exceeded");
        } else {
            connectionTimes.put(remoteAddress, currentTime);
            // 允许连接
        }
    }
 
}