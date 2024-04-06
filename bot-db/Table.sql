/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/4/16 9:42:16                            */
/*==============================================================*/


drop table if exists TB_JY_BOT;
/*==============================================================*/
/* Table: TB_JY_BOT                                             */
/*==============================================================*/
create table TB_JY_BOT
(
   BOTID               bigint not null AUTO_INCREMENT comment '群号',
   TOKEN                varchar(64) not null comment '机器人TOKEN',
   BOTNAME                varchar(64) not null comment '机器人名称',
   KFURL               varchar(64) comment '客服链接',
   PLAYURL               varchar(64) comment '玩法说明',
   STATUS               varchar(1) comment '当前状态 Y 开启 N 停止',
   LAST_UPDATE          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后更新时间',
   primary key (BOTID)
);

drop table if exists TB_JY_CHAT;
/*==============================================================*/
/* Table: TB_JY_CHAT                                            */
/*==============================================================*/
create table TB_JY_CHAT
(
   CHATID               varchar(64) not null comment '群号',
   CHATNAME             varchar(32) not null comment '群名称',
   USERNO		        varchar(32) comment '庄家ID',
   INTEGE								int(11) comment '金额',
   STATUS               varchar(1) comment '当前状态 Y 开启 N 停止',
   LAST_UPDATE          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后更新时间',
   primary key (CHATID)
);

drop table if exists TB_JY_CHAT_USER;
/*==============================================================*/
/* Table: TB_JY_CHAT_USER                                            */
/*==============================================================*/
create table TB_JY_CHAT_USER
(
   CHATID               varchar(16) not null comment '群号',
   USERNO             varchar(32) not null comment '群名称',
   STATUS               varchar(1) comment '当前状态 Y 开启 N 停止',
   LAST_UPDATE          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后更新时间',
   primary key (CHATID,USERNO)
);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201007','上分','201000','','firm:member:addMoney',2,'',7);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201008','下分','201000','','firm:member:subMoney',2,'',8);