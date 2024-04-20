package com.hml.mall.controller.bot;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hml.core.http.HttpResult;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.bot.DataSource;
import com.hml.mall.service.bot.IDataSourceService;
import com.hml.redis.RedisKey;
import com.hml.redis.RedisUtils;

import lombok.extern.slf4j.Slf4j;


/**
*  前端控制器
* @author hml
* @since 2023-12-14
*/

@Slf4j
@RestController
@RequestMapping("/dataSource" )
public class DataSourceController {

    @Autowired
    private IDataSourceService  dataSourceService;
    
    @Autowired
    private RedisUtils redisUtils;

//    /**
//    * 保存
//    *
//    * @param model
//    * @return
//    */
//    @PreAuthorize("hasAuthority('operations:draw:add')")
//    @RequestMapping("/save")
//    public HttpResult save(@RequestBody DataSource model) {
//        DataSourceService.save(model);
//        return HttpResult.ok();
//    }
 	/**
    * 修改
    *
    * @param model
    * @return
    */
    @PreAuthorize("hasAuthority('operations:draw:edit')")
    @RequestMapping("/edit")
    public HttpResult edit(@RequestBody DataSource model) {
    	String key  = RedisKey.ROB_SEND_PIC + model.getWareno();
    	
    	redisUtils.lSet(key, model.getContnum());
    	
    	log.info("重新发送图片：{}-{}", key, model.getContnum() );
        return HttpResult.ok();
    }
	/**
    * 删除
    *
    * @param model
    * @return
    */
    @PreAuthorize("hasAuthority('operations:draw:del')")
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody DataSource model) {
    	
        return HttpResult.ok();
    }
    /**
    * 查询详情
    *
    * @param id
    * @return
    */
    @PreAuthorize("hasAuthority('operations:draw:view')")
    @RequestMapping("/getById")
    public HttpResult getById(Integer id) {
     DataSource model = dataSourceService.getById(id);
        // todo 再包装一层
        return HttpResult.ok(model);
    }


    /**
    * 列表查询（非分页）
    *
    * @return
    */
    @PreAuthorize("hasAuthority('operations:draw:view')")
    @RequestMapping("/list")
    public HttpResult list() {
        DataSource model = new  DataSource();
        List<DataSource> list =  dataSourceService.list(model);
        // todo 再包装一层
        return HttpResult.ok(list);
    }


    /**
    * 列表查询（分页）
    *
    * @return
    */
    @PreAuthorize("hasAuthority('operations:draw:view')")
    @RequestMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {

    	pageRequest.getParams().put("sTime@desc", "1");
        PageResult page = dataSourceService.findPage(pageRequest);
        // todo 再包装一层
        return HttpResult.ok(page);
    }

}
