package com.hml.websocket.filter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.hml.utils.IPUtils;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@javax.servlet.annotation.WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
@Order(1)
@Component
public class WebFilter implements Filter {
	
	 private static final int CONNECTION_RATE_LIMIT = 10; // 每秒允许的最大连接数
	 private static final int CONNECTION_RATE_INTERVAL = 1; // 时间间隔（秒）
	 
	 private static final ConcurrentHashMap<String, Long> connectionTimes = new ConcurrentHashMap<>();
	 private static final ConcurrentHashMap<String, Integer> blackMap = new ConcurrentHashMap<>();
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String ipAddr = IPUtils.getIpAddr(req);
        Integer num = blackMap.get(ipAddr);
        if(num != null && num > 10) {
        	log.error("禁止请求：{}",ipAddr);
        	return;
        }else {
        	 boolean flag = checkAuth(ipAddr);
             if(flag) {
             	filterChain.doFilter(servletRequest, servletResponse);
             }else {
            	 log.error("非法请求：{}-{}",ipAddr,flag);
             }
        }
       
    }
    
    public boolean checkAuth(String ip) {
    	boolean flag  = false;
    	String remoteAddress = ip;
        long currentTime = System.currentTimeMillis();
 
        Long lastConnectionTime = connectionTimes.get(remoteAddress);
        if (lastConnectionTime != null && currentTime - lastConnectionTime < CONNECTION_RATE_INTERVAL * 1000) {
           log.info("请求IP：{}",remoteAddress);
           Integer num = blackMap.get(ip);
           if(num == null) {
        	   num = 0 ;
           }
           blackMap.put(ip, num++);
           if(num >  10) {
        	   flag = false;
           }else {
        	   flag = true;
           }
        } else {
            connectionTimes.put(remoteAddress, currentTime);
            // 允许连接
            flag = true;
        }
        return flag;
    }
 
}