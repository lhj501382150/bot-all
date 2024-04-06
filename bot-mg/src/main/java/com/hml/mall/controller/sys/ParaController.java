package com.hml.mall.controller.sys;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hml.core.http.HttpResult;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.sys.Para;
import com.hml.mall.iface.sys.IParaService;


/**
*  前端控制器
* @author hml
* @since 2021-04-05
*/


@RestController
@RequestMapping("/para" )
public class ParaController {

    @Autowired
    private IParaService  paraService;

    /**
    * 保存
    *
    * @param model
    * @return
    */
    @PreAuthorize("hasAuthority('sys:para:edit')")
    @RequestMapping("/edit")
    public HttpResult edit(@RequestBody Para model) {
        paraService.updateById(model);
        return HttpResult.ok();
    }


    /**
    * 查询详情
    *
    * @param id
    * @return
    */
    @PreAuthorize("hasAuthority('sys:para:view')")
    @RequestMapping("/getById")
    public HttpResult getById(@RequestBody Integer id) {
     Para model = paraService.getById(id);
        // todo 再包装一层
        return HttpResult.ok(model);
    }


    

    /**
    * 列表查询（分页）
    *
    * @return
    */
    public HttpResult page(@RequestBody PageRequest pageRequest) {

        Para model = new  Para();
        pageRequest.getParams().put("SYSTYPE@ASC", "ASC");
        PageResult pageResult = paraService.findPage(pageRequest);
        // todo 再包装一层
        return HttpResult.ok(pageResult);
    }
    
    /**
     * 列表查询（分页）
     *
     * @return
     */
     @PreAuthorize("hasAuthority('sys:para:view')")
     @RequestMapping("/findPage")
     public HttpResult findPage(@RequestBody Para para) {
    	 List<Map<String,Object>> list = paraService.list(para);
         // todo 再包装一层
         return HttpResult.ok(list);
     }

}
