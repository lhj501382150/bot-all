package com.hml.mall.controller.order;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hml.core.http.HttpResult;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.order.HisOrder;
import com.hml.mall.entity.order.Order;
import com.hml.mall.iface.order.IHisOrderService;
import com.hml.mall.iface.user.IUserService;
import com.hml.utils.DateTimeUtils;
import com.hml.utils.StringUtils;


/**
*  前端控制器
* @author hml
* @since 2021-04-18
*/


@RestController
@RequestMapping("/hisOrder" )
public class HisOrderController {

    @Autowired
    private IHisOrderService  hisOrderService;
    
    @Autowired
    private IUserService userService;

     
	/**
    * 删除
    *
    * @param model
    * @return
	 * @throws Exception 
    */
    @PreAuthorize("hasAuthority('order:his:del')")
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody HisOrder model) throws Exception {
        if(StringUtils.isBlank(model.getFdate())) {
        	return HttpResult.error("请传入删除月份");
        }
        hisOrderService.deleteHisData(model.getFdate());
        
        return HttpResult.ok();
    }
     


    /**
    * 列表查询（分页）
    *
    * @return
    */
    @PreAuthorize("hasAuthority('order:his:view')")
    @RequestMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {

        PageResult page = hisOrderService.findPage(pageRequest);
        // todo 再包装一层
        return HttpResult.ok(page);
    }
    
    @RequestMapping("/findCount")
    public HttpResult findCount(@RequestBody PageRequest pageRequest) {
    	
    	PageResult page = hisOrderService.findCount(pageRequest);
    	return HttpResult.ok(page);
    }
    
    @RequestMapping("/findLevelCount")
    public HttpResult findLevelCount(@RequestBody PageRequest pageRequest) throws Exception {
    	
    	PageResult page = userService.findUserLevelCount(pageRequest);
    	return HttpResult.ok(page);
    }
    
    @RequestMapping("/findFYCount")
    public HttpResult findFYCount(@RequestBody PageRequest pageRequest) {
    	
    	PageResult page = hisOrderService.findFYCount(pageRequest);
    	// todo 再包装一层
    	return HttpResult.ok(page);
    }

}
