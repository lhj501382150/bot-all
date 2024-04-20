package com.hml.mall.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hml.mall.iface.user.IUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TaskManager {
	
	@Autowired
	private IUserService userService;

//	@Scheduled(cron ="0 0/1 * * * *")
//	public void initAddUser(){
//		 try {
//			 userService.initRelation();
//		} catch (Exception e) {
//			log.error("添加用户关系异常：{}",e);
//		}
//	}
}
