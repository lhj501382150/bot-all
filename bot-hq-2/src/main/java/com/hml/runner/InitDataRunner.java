package com.hml.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.hml.back.service.BackCoreService;
import com.hml.redis.RedisKey;
import com.hml.redis.RedisUtils;
import com.hml.task.TaskManager;

@Component
public class InitDataRunner implements ApplicationRunner {

	@Autowired
	private BackCoreService backCoreService;
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Autowired
	private TaskManager taskManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
	
		redisUtils.del(RedisKey.ORDER_QUERY,RedisKey.CURRENT_STATUS);
		
		taskManager.checkSysAuth();
	}

}
