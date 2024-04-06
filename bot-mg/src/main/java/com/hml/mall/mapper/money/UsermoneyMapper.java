package com.hml.mall.mapper.money;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hml.mall.entity.money.Usermoney;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hml
 * @since 2021-04-14
 */
public interface UsermoneyMapper extends BaseMapper<Usermoney> {

	
	@Select("select t.* from (select t1.*,t2.username,t2.nickname,t3.acctname "
			+ " from tb_zj_usermoney t1 left join TB_HY_USER t2 on t1.USERNO = t2.USERNO"
			+ " left join tb_zj_account t3 on t1.acctno = t3.acctno "
			+ ") t ${ew.customSqlSegment} order by fdate desc ")
	Page<Usermoney> findPageByUser(Page<Usermoney> page,@Param(Constants.WRAPPER)Wrapper wrapper);
	@Select("select sum(start) as start,sum(inmoney) as inmoney,sum(outmoney) as outmoney,sum(income) as income,sum(payment) as payment,sum(`in`) as `in`,sum(`out`) as `out` "
			+ " ,sum(balance) as balance,sum(freeze) as freeze,sum(lose) as lose,sum(enable) as enable,sum(allquity) as allquity, sum(limtout) as limtout,sum(canout) as canout "
			+ " from (select t1.*,t2.username,t2.nickname ,t3.acctname "
			+ " from tb_zj_usermoney t1 left join TB_HY_USER t2 on t1.USERNO = t2.USERNO"
			+ " left join tb_zj_account t3 on t1.acctno = t3.acctno "
			+ ") t ${ew.customSqlSegment} order by fdate desc ")
	Usermoney findSum(@Param(Constants.WRAPPER)Wrapper wrapper);
	
	
	@Select("select t.* from ("
			+ " select t1.* ,t2.freeze,t2.balance,t2.enable,t3.inmoney,t3.outmoney,t3.income,t3.payment,t4.username,t4.uno1,t4.uno2,t4.uno3,t5.acctname,t6.orgtype  "
			+ " from (                                                                                                "
			+ " 	select left(fdate,7) as fdate,userno,acctno, start from tb_zj_usermoney                           "
			+ " 	where (fdate,userno,acctno) in (                                                                  "
			+ " 		select min(fdate),userno,acctno from tb_zj_usermoney group by left(fdate,7),userno,acctno     "
			+ " 	)) t1                                                                                             "
			+ " left join (                                                                                           "
			+ " 	select left(fdate,7) as fdate,userno,acctno, freeze,balance,enable from tb_zj_usermoney                  "
			+ " 	where (fdate,userno,acctno) in (                                                                  "
			+ " 		select max(fdate),userno,acctno from tb_zj_usermoney group by left(fdate,7),userno,acctno     "
			+ " 	)) t2 on t1.fdate = t2.fdate and t1.acctno = t2.acctno and t1.userno = t2.userno                  "
			+ " left join (                                                                                           "
			+ " 	select fmonth as fdate,userno,acctno                                                              "
			+ " 	,sum(INMONEY) as inmoney                                                                          "
			+ " 	,sum(outmoney) as outmoney                                                                        "
			+ " 	,sum(`in`) as income                                                                              "
			+ " 	,sum(`out`) as payment                                                                            "
			+ " 	from (                                                                                            "
			+ " 	select left(fdate,7) as fmonth, t1.* from tb_zj_usermoney t1                                      "
			+ " 	) t group by fmonth,userno,acctno                                                                 "
			+ " ) t3 on t1.fdate = t3.fdate and t1.acctno = t3.acctno and t1.userno = t3.userno                       "
			+ " left join tb_hy_user_relation t4 on t1.userno = t4.userno                                             "
			+ " left join tb_hy_user t6 on t1.userno = t6.userno                                             "
			+ " left join tb_zj_account t5 on t1.acctno = t5.acctno "
			+ ") t ${ew.customSqlSegment} order by fdate desc")
	Page<Usermoney> findMonthPage(Page<Usermoney> page,@Param(Constants.WRAPPER)Wrapper wrapper);
	
	List<Map<String,Object>> findChnageCount(Map<String,Object> paraMap);
	
	Map<String,Object> findPlatMoney(String fdate);
}
