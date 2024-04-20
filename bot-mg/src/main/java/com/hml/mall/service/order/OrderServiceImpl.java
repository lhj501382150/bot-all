package com.hml.mall.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hml.core.page.MybatisPlusPageHelper;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.order.Order;
import com.hml.mall.iface.order.IOrderService;
import com.hml.mall.mapper.order.OrderMapper;
import com.hml.mall.security.LoginUser;
import com.hml.mall.util.SecurityUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hml
 * @since 2021-04-18
 */

@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;

    @Override
    public List<Order> list(Order model) {
       QueryWrapper<Order> wrapper = new QueryWrapper();
       return super.list(wrapper);
    }

    @Override
	public PageResult findPage(PageRequest pageRequest) {
    	LoginUser user = SecurityUtils.getLoginInfo();
		if(user==null) {
			return null;
		}
		if(user.getType()<=0) {
			return MybatisPlusPageHelper.findPage(pageRequest, orderMapper,"findPage");
		}else {
			pageRequest.getParams().put(user.getQueryNo(), SecurityUtils.getUsername());
			return MybatisPlusPageHelper.findPage(pageRequest, orderMapper,"findPageByUser");
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
     	Map<String, Object> count = orderMapper.findCountSum(params);
     	List<Map<String, Object>> datas = orderMapper.findCount(params);
     	
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
     	Map<String, Object> count = orderMapper.findFYCountSum(params);
     	List<Map<String, Object>> datas = orderMapper.findFYCount(params);
     	
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

}
