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
   BOTID               bigint not null AUTO_INCREMENT comment 'Ⱥ��',
   TOKEN                varchar(64) not null comment '������TOKEN',
   BOTNAME                varchar(64) not null comment '����������',
   KFURL               varchar(64) comment '�ͷ�����',
   PLAYURL               varchar(64) comment '�淨˵��',
   STATUS               varchar(1) comment '��ǰ״̬ Y ���� N ֹͣ',
   LAST_UPDATE          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '������ʱ��',
   primary key (BOTID)
);

drop table if exists TB_JY_CHAT;
/*==============================================================*/
/* Table: TB_JY_CHAT                                            */
/*==============================================================*/
create table TB_JY_CHAT
(
   CHATID               varchar(64) not null comment 'Ⱥ��',
   CHATNAME             varchar(32) not null comment 'Ⱥ����',
   USERNO		        varchar(32) comment 'ׯ��ID',
   INTEGE								int(11) comment '���',
   STATUS               varchar(1) comment '��ǰ״̬ Y ���� N ֹͣ',
   LAST_UPDATE          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '������ʱ��',
   primary key (CHATID)
);

drop table if exists TB_JY_CHAT_USER;
/*==============================================================*/
/* Table: TB_JY_CHAT_USER                                            */
/*==============================================================*/
create table TB_JY_CHAT_USER
(
   CHATID               varchar(16) not null comment 'Ⱥ��',
   USERNO             varchar(32) not null comment 'Ⱥ����',
   STATUS               varchar(1) comment '��ǰ״̬ Y ���� N ֹͣ',
   LAST_UPDATE          timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '������ʱ��',
   primary key (CHATID,USERNO)
);

INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201007','�Ϸ�','201000','','firm:member:addMoney',2,'',7);
INSERT INTO TB_SYS_MENU(MENU_ID,MENU_NAME,PARENT_ID,MENU_URL,PERMS,TYPE,ICON,ORDER_NO) VALUES('201008','�·�','201000','','firm:member:subMoney',2,'',8);