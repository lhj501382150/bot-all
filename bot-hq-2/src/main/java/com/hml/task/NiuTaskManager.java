package com.hml.task;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.PrivateKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.alibaba.fastjson.JSONObject;
import com.hml.back.bean.RespBean;
import com.hml.config.BotConfig;
import com.hml.redis.Redis2Utils;
import com.hml.redis.RedisKey;
import com.hml.utils.DateTimeUtils;
import com.hml.utils.SM2Utils;
import com.hml.utils.StringUtils;
import com.hml.websocket.config.WebSocketConfig;
import com.hml.websocket.server.WebSocketNiuServerApp;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NiuTaskManager {

	@Autowired
	private Redis2Utils redis2Utils;
	
	private Boolean IS_AUTH = false;
	
	@Scheduled(cron="0 0 7 * * *")
	public void checkSysAuth() {
		IS_AUTH = checkAuth();
		log.info("牛牛-当前系统状态：{}",IS_AUTH);
	}
	
	@Async
	@Scheduled(fixedRate = 1000)
	public void syncStatus() {
		 try {
			 Object obj = redis2Utils.lGetAndPop(RedisKey.CURRENT_STATUS);
			 if(StringUtils.isBlank(obj)) {
				 return;
			 }
			 log.info("【Redis-2信息】:{}",obj);
			 
			 RespBean resp = JSONObject.parseObject(obj.toString(), RespBean.class);
			 int step = resp.getIStatus();
			 int dataId = Integer.parseInt(resp.getDataId());
			 int maxId = DrawInfo.ID + 2;
			 int minId = DrawInfo.ID - 2;
			 if(dataId < minId || dataId > maxId) {
				 log.info("历史信息当前期数：{}--{}" ,DrawInfo.ID  ,resp.getDataId());
				 Object res = redis2Utils.lGetAndPop(RedisKey.ORDER_QUERY);
					
				 log.info("【result】:{}",res);
				 if(res != null && BotConfig.ENABLE) {
					RespBean bean = JSONObject.parseObject(res.toString(), RespBean.class);
					String key = getResultKey();
					redis2Utils.hset(key,bean.getDataId(), res);
					redis2Utils.expire(key, 60 * 60 * 24);
				 }
				 return;
			 }

			 if(Flow.START_ROB.getStep() == step) {
				 log.info("【START_ROB_2】：{}",step);
				 DrawInfo.FLOW = Flow.START_ROB;
				 if(BotConfig.ENABLE) start();
				 if(WebSocketConfig.ENABLE  && IS_AUTH) {
					 JSONObject json = new JSONObject();
					 json.put("ISSUE", DrawInfo.DRAW_ISSUE);
					 json.put("CODE", DrawInfo.PRE_DRAW_CODE);
					 json.put("RESULT", DrawInfo.RESULT);
					 json.put("TIME", DrawInfo.DRAW_TIME);
					 json.put("ID", DrawInfo.ID);
					 WebSocketNiuServerApp.sendInfo(Flow.START_ROB.getStep(),json.toJSONString());
				 }
			 }else if(Flow.CONFIRM_ROB.getStep() == step) {
				 log.info("【CONFIRM_ROB_2】：{}",step);
				 DrawInfo.FLOW = Flow.CONFIRM_ROB;
			 }else if(Flow.DOWN_ORDER.getStep() == step) {
				 log.info("【DOWN_ORDER_2】：{}",step);
				 DrawInfo.FLOW = Flow.DOWN_ORDER;
				 if(BotConfig.ENABLE) drawOrder(resp);
				 if(WebSocketConfig.ENABLE  && IS_AUTH) {
					 JSONObject json = new JSONObject();
					 json.put("ISSUE", DrawInfo.DRAW_ISSUE);
					 json.put("CODE", DrawInfo.PRE_DRAW_CODE);
					 json.put("RESULT", DrawInfo.RESULT);
					 json.put("TIME", DrawInfo.DRAW_TIME);
					 json.put("ID", DrawInfo.ID);
					 WebSocketNiuServerApp.sendInfo(Flow.START_ROB.getStep(),json.toJSONString());
				 }
			 }else if(Flow.STOP_ORDER.getStep() == step) {
				 log.info("【STOP_ORDER_2】：{}",step);
				 DrawInfo.FLOW = Flow.STOP_ORDER;
				 stopOrder();
				 if(WebSocketConfig.ENABLE && IS_AUTH) {
					 WebSocketNiuServerApp.sendInfo(Flow.STOP_ORDER.getStep(),"");
				 }
			 }else if(Flow.OVER.getStep() == step) {
				 log.info("【OVER_2】：{}",step);
				 DrawInfo.FLOW = Flow.OVER;
				 overResult();
			 }else if(Flow.TIPS.getStep() == step) {
				 log.info("【TIME TIPS_2】：{}",step);
				 if(BotConfig.ENABLE)timeTips();
			 }
			 
			 
		} catch (Exception e) {
			log.error("抢庄异常： " + e.getMessage());
			e.printStackTrace();
		}
	}	
	
	private void timeTips() throws TelegramApiException {
		 
	}
	private void start() throws TelegramApiException {
		 
	}
 
	private void drawOrder(RespBean res) throws TelegramApiException {
 
	}
	private void stopOrder() throws TelegramApiException {
//		 查询是否有庄
		boolean flag = true;
		int index = 0;
		while(flag  && index < 5) {
			Object res = redis2Utils.lGetAndPop(RedisKey.ORDER_QUERY);
			  log.info("【QUERY_ORDER_2】:{}",res);
			  if(res != null) {
				  RespBean bean = JSONObject.parseObject(res.toString(), RespBean.class);
				  int dataId = Integer.parseInt(bean.getDataId());
				  int maxId = DrawInfo.ID + 2;
				  int minId = DrawInfo.ID - 2;
				 if(dataId < minId || dataId > maxId) {
					 log.info("历史信息当前期数：{}--{}" ,DrawInfo.ID ,bean.getDataId());
					 return;
				 }
				  flag = false;
			  }else {
				  index++;
			  }
		}
		  
	}
	private void overResult() throws TelegramApiException, IOException {
//		 查询是否有庄
		boolean flag = true;
		int index = 0;
		while(flag && index < 5) {
			Object res = redis2Utils.lGetAndPop(RedisKey.ORDER_QUERY);
			
			log.info("【result_2】:{}",res);
			if(res != null) {
				RespBean bean = JSONObject.parseObject(res.toString(), RespBean.class);
				String key = getResultKey();
				redis2Utils.hset(key,bean.getDataId(), res);
				redis2Utils.expire(key, 60 * 60 * 24);
				log.info("存入结果:{}-{}",bean.getDataId(),res);
				 
				if(WebSocketConfig.ENABLE  && IS_AUTH) {
					DrawInfo.RESULT = String.valueOf(bean.getIWinNo());
					WebSocketNiuServerApp.sendInfo(Flow.OVER.getStep(),res.toString());
				 }
			    flag = false;
			}else {
				index++;
			}
		}
		  
	}
	
	 
	
	private String getResultKey() {
		String date = DateTimeUtils.getCurrentDate("yyyyMMdd");
		String key = RedisKey.ORDER_RESULT + ":" + date;
		 
		return key;
	}
	
	private boolean checkAuth() {
		String priPath = BotConfig.FILE_PATH + "/private_key.pem";
		String listenPath = BotConfig.FILE_PATH + "/listen.lic";
		String expireDate = FileUtil.readString(listenPath,Charset.forName("UTF-8"));
		expireDate = expireDate.replace("\n", "").trim();
		PrivateKey privateKey = SM2Utils.importPrivateKey(priPath);
		String decrypt = SM2Utils.decrypt(expireDate, privateKey);
		String curDate = DateTimeUtils.getCurrentDate("yyyyMMdd");
		log.info(curDate+"---"+ decrypt+ "=====" + curDate.compareTo(decrypt));
		if(curDate.compareTo(decrypt) > 0) {
			return false;
		}else {
			return true;
		}
	}
}
