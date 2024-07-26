package com.hml.mall.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hml.core.page.MybatisPlusPageHelper;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.money.HisUsermoney;
import com.hml.mall.entity.money.HisUsermoneyChange;
import com.hml.mall.entity.order.HisOrder;
import com.hml.mall.iface.order.IHisOrderService;
import com.hml.mall.mapper.money.HisUsermoneyChangeMapper;
import com.hml.mall.mapper.money.HisUsermoneyMapper;
import com.hml.mall.mapper.order.HisOrderMapper;
import com.hml.mall.security.LoginUser;
import com.hml.mall.util.SecurityUtils;
import com.hml.utils.DateTimeUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hml
 * @since 2021-04-18
 */
@Slf4j
@Service("hisOrderService")
public class HisOrderServiceImpl extends ServiceImpl<HisOrderMapper, HisOrder> implements IHisOrderService {

	@Autowired
	private HisOrderMapper hisOrderMapper;
	
	@Autowired
	private HisUsermoneyMapper hisUsermoneyMapper;
	
	@Autowired
	private HisUsermoneyChangeMapper hisUsermoneyChangeMapper;
	

    @Override
    public List<HisOrder> list(HisOrder model) {
       QueryWrapper<HisOrder> wrapper = new QueryWrapper();
       return super.list(wrapper);
    }

    @Override
	public PageResult findPage(PageRequest pageRequest) {
    	LoginUser user = SecurityUtils.getLoginInfo();
		if(user==null) {
			return null;
		}
		if(user.getType()<=0) {
			return MybatisPlusPageHelper.findPage(pageRequest, hisOrderMapper,"findPage");
		}else {
			pageRequest.getParams().put(user.getQueryNo(), SecurityUtils.getUsername());
			return MybatisPlusPageHelper.findPage(pageRequest, hisOrderMapper,"findPageByUser");
		}
	}
    @Override
    public PageResult findCount(PageRequest pageRequest) {
    	LoginUser user = SecurityUtils.getLoginInfo();
		if(user==null) {
			return null;
		}
    	Map<String, Object> params = pageRequest.getParams();
    	int pageNum = pageRequest.getPageNum();
     	int size = pageRequest.getPageSize();
     	int pageIdx = (pageNum - 1) * size;
     	params.put("pageIndex", pageIdx);
     	params.put("pageSize", size);
     	if(user.getType() > 0) {
     		params.put(user.getQueryNo(), SecurityUtils.getUsername());
     	}
     	Map<String, Object> count = hisOrderMapper.findCountSum(params);
     	List<Map<String, Object>> datas = hisOrderMapper.findCount(params);
     	
     	PageResult result = new PageResult();
     	result.setContent(datas);
     	int total = Integer.parseInt(count.get("TOTAL").toString());
     	int totalPage = total/size +1;
     	result.setTotalPages(totalPage);
     	result.setPageNum(pageNum);
     	result.setPageSize(size);
     	result.setTotalSize(total);
     	result.setSum(count);
     	return result;
    }
    
    @Override
    public PageResult findFYCount(PageRequest pageRequest) {
    	LoginUser user = SecurityUtils.getLoginInfo();
		if(user==null) {
			return null;
		}
    	Map<String, Object> params = pageRequest.getParams();
    	int pageNum = pageRequest.getPageNum();
     	int size = pageRequest.getPageSize();
     	int pageIdx = (pageNum - 1) * size;
     	params.put("pageIndex", pageIdx);
     	params.put("pageSize", size);
     	if(user.getType() > 0) {
     		params.put(user.getQueryNo(), SecurityUtils.getUsername());
     	}
     	Map<String, Object> count = hisOrderMapper.findFYCountSum(params);
     	List<Map<String, Object>> datas = hisOrderMapper.findFYCount(params);
     	
     	PageResult result = new PageResult();
     	result.setContent(datas);
     	int total = Integer.parseInt(count.get("TOTAL").toString());
     	int totalPage = total/size +1;
     	result.setTotalPages(totalPage);
     	result.setPageNum(pageNum);
     	result.setPageSize(size);
     	result.setTotalSize(total);
     	result.setSum(count);
     	return result;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
	public void deleteHisData(String month) throws Exception {
    	String bdate = month + "-01";
    	String edate = DateTimeUtils.lastDayOfMonth(month, "yyyy-MM");
    	String btime = bdate + " 07:00:00";
    	String etime = edate + " 06:00:00";
		log.info("删除历史数据:{}-{}",bdate,edate);
		QueryWrapper<HisOrder> qw = new QueryWrapper<HisOrder>();
        qw.ge("ORDTIME", btime);
        qw.le("ORDTIME", etime);
        hisOrderMapper.delete(qw);
        
        QueryWrapper<HisUsermoney> qw1 = new QueryWrapper<HisUsermoney>();
        QueryWrapper<HisUsermoneyChange> qw2 = new QueryWrapper<HisUsermoneyChange>();
        
        qw1.ge("FDATE", bdate);
        qw1.le("FDATE", edate);
        hisUsermoneyMapper.delete(qw1);
        
        qw2.ge("FDATE", bdate);
        qw2.le("FDATE", edate);
        hisUsermoneyChangeMapper.delete(qw2);
	}
}
