package com.hml.mall.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hml.mall.entity.sys.Log;
import com.hml.mall.iface.sys.ILogService;
import com.hml.mall.util.HttpUtils;
import com.hml.mall.util.IPUtils;
import com.hml.mall.util.SecurityUtils;

/*
 * 操作日志处理
 */
@Aspect
@Component
public class SysLogAspect {

	private Logger logger = Logger.getLogger(SysLogAspect.class);
	@Autowired
	private ILogService logService;
	
	@Pointcut("execution(* com.hml.mall.controller.*.*.*(..))")
	private void logPointCut(){
		
	}
	
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable{
		long beginTime = System.currentTimeMillis();
//		执行方法
		Object result = point.proceed();
		
		long time = System.currentTimeMillis() - beginTime;
		
		saveSysLog(point, time);
		
		return result;
	}
	
	private void saveSysLog(ProceedingJoinPoint joinPoint,long time){
		
		if(joinPoint.getTarget() instanceof ILogService){
			return;
		}
		String userName = SecurityUtils.getUsername();
		MethodSignature method = (MethodSignature)joinPoint.getSignature();
		Log log = new Log();
		
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = method.getName();

		log.setMethod(className + "."+methodName +"()"); 
		Object[] args = joinPoint.getArgs();
		try {
			String param = JSONObject.toJSONString(args);
			logger.info("请求路径：" + log.getMethod());
			logger.info("请求参数为：" + param);
			if(param.length()>200){
				param = param.substring(0,200)+"...";
			}
			log.setParams(param);
		} catch (Exception e) {
			
		}
		// 获取request
		HttpServletRequest request = HttpUtils.getHttpServletRequest();
		// 设置IP地址
		log.setIp(IPUtils.getIpAddr(request));

		// 用户名
		log.setOptuser(userName);
		
		// 执行时长(毫秒)
		log.setTime(time);
		
		// 保存系统日志
		logService.save(log);
	}
}
