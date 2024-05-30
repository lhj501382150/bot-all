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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hml.redis.RedisKey;
import com.hml.redis.RedisUtils;
import com.hml.utils.IPUtils;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@javax.servlet.annotation.WebFilter(filterName = "sessionFilter", urlPatterns = "/*")
@Order(1)
@Component
public class WebFilter implements Filter {
	
	 private static final int CONNECTION_RATE_LIMIT = 10; // 每秒允许的最大连接数
	 private static final int CONNECTION_RATE_INTERVAL = 500; // 时间间隔（毫秒）
	 
	 private static final ConcurrentHashMap<String, Long> connectionTimes = new ConcurrentHashMap<>();
	 private static final String BLACK_IP = ",43.134.222.90,";
	 @Autowired
	 private RedisUtils redisUtils;
	 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String ipAddr = IPUtils.getIpAddr(req);
        if("43.134.222.90".equals(ipAddr)) {
        	return;
        }
        Object ipObj = redisUtils.get(RedisKey.BLACK_IP_LIST);
        String blackIps = "";
        if(ipObj!= null) {
        	blackIps = ipObj.toString() + BLACK_IP;
        }else {
        	blackIps = BLACK_IP;
        }
        if(blackIps.indexOf(ipAddr) > -1) {
        	return;
        }
        boolean flag = checkAuth(req,ipAddr);
        if(flag) {
        	filterChain.doFilter(servletRequest, servletResponse);
        }else {
        	 log.error("非法请求：{}-{}",ipAddr,flag);
        	 HttpServletResponse response = (HttpServletResponse)servletResponse;
        	 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    public boolean checkAuth(HttpServletRequest req,String remoteAddress) {
    	boolean flag  = false;
        long currentTime = System.currentTimeMillis();
 
        Long lastConnectionTime = connectionTimes.get(remoteAddress);
        if (lastConnectionTime != null && currentTime - lastConnectionTime < CONNECTION_RATE_INTERVAL) {
        	log.info("黑名单:【{}】" ,remoteAddress);
        	Object obj = redisUtils.hget(RedisKey.BLACK_IP_REQUEST, remoteAddress);
        	IPRecord ipData = null;
        	if(obj==null) {
        		ipData = new IPRecord();
        	}else {
        		ipData = JSONObject.parseObject(obj.toString(),IPRecord.class);
        	}
        	ipData.setIpAddr(remoteAddress);
        	ipData.setNum(ipData.getNum() + 1);
        	ipData.setCurTime(currentTime);
        	ipData.setPreTime(lastConnectionTime);
        	redisUtils.hset(RedisKey.BLACK_IP_REQUEST, remoteAddress, JSONObject.toJSONString(ipData));
            if(ipData.getNum() >  CONNECTION_RATE_LIMIT) {
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