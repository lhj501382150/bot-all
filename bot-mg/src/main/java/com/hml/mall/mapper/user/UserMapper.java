package com.hml.mall.mapper.user;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hml.mall.entity.user.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hml
 * @since 2021-04-11
 */
public interface UserMapper extends BaseMapper<User> {

	@Select("select t.* from (	select t1.* ,COALESCE(t3.BALANCE,0) as BALANCE,COALESCE(t3.ENABLE,0) as ENABLE,COALESCE(t3.FREEZE,0) as FREEZE"
			+ "  ,t4.username as tjusername,t4.nickname as tjnickname  " 
			+ "  from tb_hy_user t1  " + 
			"	left join ( SELECT USERNO, max( FDATE ) AS FDATE FROM tb_zj_usermoney GROUP BY USERNO ) t2 ON t1.USERNO = t2.USERNO " + 
			"	LEFT JOIN tb_zj_usermoney t3 ON t2.FDATE = t3.FDATE and t2.userno = t3.userno " + 
			"	LEFT JOIN tb_hy_user t4 ON t4.userno = t1.openid " + 
			"  ) t ${ew.customSqlSegment}")
	Page<User> findPage(Page<User> page,@Param(Constants.WRAPPER)Wrapper wrapper);
	
	@Select("select t1.*,t2.CLEVEL as clevel,t2.PARENTNO as parentno,t2.TJNO as tjno from TB_HY_USER t1 left join TB_HY_USER_RELATION t2 on t1.USERNO = t2.USERNO ${ew.customSqlSegment}")
	List<User> findUserList(@Param(Constants.WRAPPER)Wrapper wrapper);
	
	@Select("select t.* from (select t1.*,t2.CLEVEL as clevel,t2.PARENTNO as parentno,t2.TJNO as tjno"
			+ ",t3.tjnum,(case when t4.userno is null then 'N' else 'Y' end) as isCARD,t5.ROLE_ID as roleId "
			+ ",t2.uno1,t2.uno2,t2.uno3,t2.uno4,t2.uno5,t2.uno6,t2.uno7,t2.uno8,t2.uno9,t2.uno10,t2.uno11,t2.uno12,t2.uno13,t2.uno14 "
			+ " from TB_HY_USER t1 "
			+ " left join TB_SYS_USER_ROLE t5 on t1.USERNO = t5.LOGINNO "
			+ " left join TB_HY_USER_RELATION t2 on t1.USERNO = t2.USERNO "
			+ " left join (select tjno as userno,count(1) as tjnum from tb_hy_user_relation where tjno != '' group by tjno) t3 on t1.userno = t3.userno "
			+ " left join (select distinct userno from tb_hy_user_cardinfo where CTYPE = 'C') t4 on t1.userno = t4.userno "
			+ " ) t ${ew.customSqlSegment}")
	Page<User> findPageByUser(Page<User> page,@Param(Constants.WRAPPER)Wrapper wrapper);
	
	@Delete("delete from tb_zj_usermoney")
	void deleteMoney();
	
	@Delete("delete from tb_zj_usermoney_change ")
	void deleteChangeMoney();
	
	@Delete("delete from tb_zj_usermoney_change_apply ")
	void deleteChangeMoneyApply();
	
	@Select("select t.* from tb_hy_user t where userno not in (select userno from TB_HY_USER_RELATION )")
	List<User> findUnInitUser();
}

