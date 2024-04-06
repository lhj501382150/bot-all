package com.hml.back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BackCoreConfig {
	/*
	 * 后台访问路径
	 */
	public static String URL = "";
	
	public static String IN_MONEY = "/apis/Account/InMoney";
	public static String OUT_MONEY = "/apis/Account/OutMoney";
	public static String QUERY_MONEY = "/apis/Account/QueryMoney";
	public static String ADD_DATA = "/apis/Data/AddData";
	public static String ADD_GROUPID = "/apis/Data/AddGroupId";
	public static String GET_DRAW = "/apis/Data/GetData";
	public static String QUERY_DATA = "/apis/Data/GetData";
	public static String ROB_ORDER = "/apis/Order/RobOrder";
	public static String DOWN_ROB = "/apis/Order/DownRob";
	public static String ORDER = "/apis/Order/Order";
	public static String QUERY_ORDRE = "/apis/Order/OrderQuery";
	public static String ADD_ACCOUNT = "/apis/Account/AddAccount";


	@Value("${backcore.sendUrl}")
	public void setUrl(String url) {
		URL = url;
	}
}
