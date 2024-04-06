package com.hml.mall.iface.trade;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.trade.WareScHandorder;

/**
* <p>
    *  服务类
    * </p>
*
* @author hml
* @since 2022-05-13
*/


public interface IWareScHandorderService extends IService<WareScHandorder> {

   /**
   * 查询
   *
   * @param model
   * @return
   */
   List<WareScHandorder> list(WareScHandorder model);

    /**
    * 分页查询
    *
    * @param model
    * @param pageNum  第几页
    * @param pageSize 每页数量
    * @return
    */
   PageResult findPage(PageRequest pageRequest);

   void audit(WareScHandorder model)throws Exception;
   
   void reject(WareScHandorder model)throws Exception;
}
