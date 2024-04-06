package com.hml.mall.mapper.money;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hml.mall.entity.money.UsermoneyChange;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hml
 * @since 2021-04-14
 */
public interface UsermoneyChangeMapper extends BaseMapper<UsermoneyChange> {

	@Select("select t.* from (select t1.*,t2.username,t2.nickname,t3.acctname,t4.subname "
			+ " from tb_zj_usermoney_change t1 left join TB_HY_USER t2 on t1.USERNO = t2.USERNO "
			+ " left join tb_zj_account t3 on t1.acctno = t3.acctno "
			+ " left join tb_zj_subject t4 on t1.subno = t4.sunno "
			+ ") t ${ew.customSqlSegment} order by userno asc ")
	Page<UsermoneyChange> findPageByUser(Page<UsermoneyChange> page,@Param(Constants.WRAPPER)Wrapper wrapper);
	
	@Select("select sum(chmoney) as chmoney,sum(befmoney) as befmoney,sum(aftmoney) as aftmoney from ("
			+ " select t1.*,t2.username,t2.nickname,t3.acctname,t4.subname "
			+ " from tb_zj_usermoney_change t1 left join TB_HY_USER t2 on t1.USERNO = t2.USERNO "
			+ " left join tb_zj_account t3 on t1.acctno = t3.acctno "
			+ " left join tb_zj_subject t4 on t1.subno = t4.sunno "
			+ ") t ${ew.customSqlSegment} order by wartno desc ")
	UsermoneyChange findSum(@Param(Constants.WRAPPER)Wrapper wrapper);
}
