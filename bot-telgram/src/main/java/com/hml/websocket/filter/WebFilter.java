package com.hml.websocket.filter;
import java.io.IOException;

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
 
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        session.setAttribute("user_ip", req.getRemoteHost());//获取ip存入session
        System.out.println(req.getRemoteHost());
    }
 
}