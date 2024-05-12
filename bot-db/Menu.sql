DELETE FROM TB_SYS_MENU;

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('100000','系统管理','0','','',0,'el-icon-s-unfold',1);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('101000','用户管理','100000','/sys/user','',1,'el-icon-user',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('101001','查看','101000','','sys:user:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('101002','新增','101000','','sys:user:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('101003','修改','101000','','sys:user:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('101004','删除','101000','','sys:user:del',2,'',4);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('102000','角色管理','100000','/sys/role','',1,'el-icon-view',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('102001','查看','102000','','sys:role:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('102002','新增','102000','','sys:role:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('102003','修改','102000','','sys:role:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('102004','删除','102000','','sys:role:del',2,'',4);


INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('103000','系统参数管理','100000','/sys/para','',1,'el-icon-setting',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('103001','查看','103000','','sys:para:view',2,'',1);
#INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('103002','新增','103000','','sys:para:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('103003','修改','103000','','sys:para:edit',2,'',3);
#INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('103004','删除','103000','','sys:para:del',2,'',4);

#客户管理
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('200000','客户管理','0','','',0,'el-icon-s-unfold',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201000','客户管理','200000','/firm/member','',1,'el-icon-user',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201001','查看','201000','','firm:member:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201002','新增','201000','','firm:member:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201003','修改','201000','','firm:member:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201007','上分','201000','','firm:member:addMoney',2,'',7);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201008','下分','201000','','firm:member:subMoney',2,'',8);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201009','一键清理','201000','','firm:member:clear',2,'',9);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('202000','客户统计','200000','/firm/memberCount','',1,'el-icon-reading',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('202001','查看','202000','','firm:member:view',2,'',1);

 #群组管理
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('400000','群组管理','0','','',0,'el-icon-s-unfold',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('401000','机器人管理','400000','/bot/bot','',1,'el-icon-user',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('401001','查看','401000','','sys:bot:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('401002','新增','401000','','sys:bot:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('401003','修改','401000','','sys:bot:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('401004','删除','401000','','sys:bot:del',2,'',4);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402000','群管理','400000','/bot/chat','',1,'el-icon-reading',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402001','查看','402000','','sys:chat:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402002','新增','402000','','sys:chat:add',2,'',2);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402003','修改','402000','','sys:chat:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402004','删除','402000','','sys:chat:del',2,'',4);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402005','下庄','402000','','sys:chat:down',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402006','消息暂停','402000','','sys:chat:pause',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('402007','消息重启','402000','','sys:chat:start',2,'',3);
 

#资金管理
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('300000','资金管理','0','','',0,'el-icon-s-unfold',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('301000','账户类型管理','300000','/money/account','',1,'el-icon-tickets',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('301001','查看','301000','','money:account:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('301002','新增','301000','','money:account:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('301003','修改','301000','','money:account:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('301004','删除','301000','','money:account:del',2,'',4);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('302000','资金科目管理','300000','/money/subject','',1,'el-icon-document-copy',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('302001','查看','302000','','money:subject:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('302002','新增','302000','','money:subject:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('302003','修改','302000','','money:subject:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('302004','删除','302000','','money:subject:del',2,'',4);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('303000','账户资金信息','300000','/money/usermoney','',1,'el-icon-medal',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('303001','查看','303000','','money:usermoney:view',2,'',1);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('304000','账户资金变动明细','300000','/money/moneychange','',1,'el-icon-notebook-2',4);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('304001','查看','304000','','money:moneychange:view',2,'',1);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('305000','手工调账申请','300000','/money/moneychangeadd','',1,'el-icon-sort',5);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('305001','保存','305000','','money:moneychange:add',2,'',1);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('306000','手工调账审核','300000','/money/changeapply','',1,'el-icon-notebook-2',6);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('306001','查看','306000','','money:changeapply:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('306002','审核','306000','','money:changeapply:edit',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('306003','删除','306000','','money:changeapply:del',2,'',3);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('309000','账户资金月统计','300000','/money/moneymonth','',1,'el-icon-notebook-2',4);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('309001','查看','309000','','money:moneymonth:view',2,'',1);



#订单管理
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('500000','订单管理','0','','',0,'el-icon-s-unfold',5);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('501000','下单管理','500000','/order/order','',1,'el-icon-tickets',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('501001','查看','501000','','order:order:view',2,'',1);

 #运营管理
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('600000','运营管理','0','','',0,'el-icon-s-unfold',6);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('604000','分公司数据统计','600000','/operations/levelResult','',1,'el-icon-document-copy',4);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('604001','查看','604000','','operations:levelResult:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('601000','用户输赢','600000','/operations/result','',1,'el-icon-trophy',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('601001','查看','601000','','operations:result:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('602000','用户上下分','600000','/operations/money','',1,'el-icon-star-off',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('602001','查看','602000','','operations:money:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('603000','开奖记录','600000','/operations/draw','',1,'el-icon-reading',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('603001','查看','603000','','operations:draw:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('603002','下发结算图','603000','','operations:draw:edit',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('603003','行情补录结算','603000','','operations:draw:add',2,'',3);
 
#业绩奖励
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('700000','邀请管理','0','','',0,'el-icon-s-unfold',7);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('701000','邀请统计','700000','/invite/inviteCount','',1,'el-icon-reading',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('701001','查看','701000','','invite:inviteCount:view',2,'',1);


#推荐奖励
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('800000','推荐奖励','0','','',0,'el-icon-s-unfold',7);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('801000','推荐奖励参数设置','800000','/reward/orgpara','',1,'el-icon-reading',4);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('801001','查看','801000','','reward:orgpara:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('801002','新增','801000','','reward:orgpara:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('801003','修改','801000','','reward:orgpara:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('801004','删除','801000','','reward:orgpara:del',2,'',4);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('802000','推荐手续费统计','800000','/reward/orgtotal','',1,'el-icon-trophy',5);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('802001','查看','802000','','reward:orgtotal:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('802002','计算','802000','','reward:orgtotal:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('802003','办理','802000','','reward:orgtotal:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('802004','删除','802000','','reward:orgtotal:del',2,'',3);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('803000','推荐奖励办理','800000','/reward/orghandle','',1,'el-icon-star-off',6);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('803001','查看','803000','','reward:orghandle:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('803002','审核','803000','','reward:orghandle:edit',2,'',2);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('900000','通知管理','0','','',0,'el-icon-s-unfold',20);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('901000','公告管理','900000','/info/notice','',1,'el-icon-document-copy',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('901001','查看','901000','','info:notice:view',2,'',1);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('901002','新增','901000','','info:notice:add',2,'',2);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('901003','修改','901000','','info:notice:edit',2,'',3);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('901004','删除','901000','','info:notice:del',2,'',4);
