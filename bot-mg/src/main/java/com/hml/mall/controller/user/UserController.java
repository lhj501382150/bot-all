package com.hml.mall.controller.user;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hml.core.http.HttpResult;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.money.UsermoneyChange;
import com.hml.mall.entity.money.UsermoneyChangeApply;
import com.hml.mall.entity.user.User;
import com.hml.mall.iface.money.IUsermoneyChangeApplyService;
import com.hml.mall.iface.user.IUserService;
import com.hml.mall.util.IPUtils;
import com.hml.mall.util.PasswordEncoder;
import com.hml.mall.util.SecurityUtils;
import com.hml.utils.DateTimeUtils;
import com.hml.utils.StringUtils;


/**
*  前端控制器
* @author hml
* @since 2021-04-11
*/


@RestController
@RequestMapping("/user" )
public class UserController {

    @Autowired
    private IUserService  userService;
    
    @Autowired
    private IUsermoneyChangeApplyService userMoneyChangeApplyService;
    
    @PreAuthorize("hasAuthority('firm:member:addMoney')")
    @RequestMapping("/addMoney")
    public HttpResult addMoney(@RequestBody UsermoneyChangeApply model,HttpServletRequest request) {
        try {
        	if(StringUtils.isBlank(model.getUserno())) {
        		return HttpResult.error("用户不能为空！");
        	}
        	if(StringUtils.isBlank(model.getChmoney())) {
        		return HttpResult.error("变动金额不能为空！");
        	}
        	model.setAcctno("100");
        	model.setSubno("100");
        	model.setChkip(IPUtils.getIpAddr(request));
        	model.setChktime(DateTimeUtils.getDateTime());
        	model.setChkuser(SecurityUtils.getUsername());
        	model.setStatus(0);
        	userMoneyChangeApplyService.saveAndAudit(model);
			return HttpResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.error(e.getMessage());
		}
        
    }
    
    @PreAuthorize("hasAuthority('firm:member:subMoney')")
    @RequestMapping("/subMoney")
    public HttpResult subMoney(@RequestBody UsermoneyChangeApply model,HttpServletRequest request) {
        try {
        	if(StringUtils.isBlank(model.getUserno())) {
        		return HttpResult.error("用户不能为空！");
        	}
        	if(StringUtils.isBlank(model.getChmoney())) {
        		return HttpResult.error("变动金额不能为空！");
        	}
        	model.setAcctno("100");
        	model.setSubno("101");
        	model.setChkip(IPUtils.getIpAddr(request));
        	model.setChktime(DateTimeUtils.getDateTime());
        	model.setChkuser(SecurityUtils.getUsername());
        	model.setStatus(0);
        	userMoneyChangeApplyService.saveAndAudit(model);
			return HttpResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.error(e.getMessage());
		}
        
    }
    
    @PreAuthorize("hasAuthority('firm:member:clear')")
    @RequestMapping("/clean")
    public HttpResult clean() {
        try {
        	  
        	userService.clearMoney();
			return HttpResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.error(e.getMessage());
		}
        
    }
    /**
    * 保存
    *
    * @param model
    * @return
    */
    @PreAuthorize("hasAuthority('firm:member:add')")
    @RequestMapping("/save")
    public HttpResult save(@RequestBody User model) {
        try {
        	String regDate = DateTimeUtils.getCurrentDate("");
        	model.setRegdate(regDate);
			userService.saveUser(model);
			return HttpResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.error(e.getMessage());
		}
        
    }
 	/**
    * 修改
    *
    * @param model
    * @return
    */
    @PreAuthorize("hasAuthority('firm:member:edit')")
    @RequestMapping("/edit")
    public HttpResult edit(@RequestBody User model) {
        try {
        	User item = userService.getById(model.getUserno());
        	if(item == null) {
        		return HttpResult.error("用户不能为空");
        	}
        	model.setPaypwd(item.getPaypwd());
			userService.updateUser(model);
			return HttpResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.error(e.getMessage());
		}
    }
    @PreAuthorize("hasAuthority('firm:member:edit')")
    @RequestMapping("/editPwd")
    public HttpResult editPwd(@RequestBody User model) {
        try {
        	User item = userService.getById(model.getUserno());
        	if(item == null) {
        		return HttpResult.error("用户不能为空");
        	}
        	String pwd = model.getUserno() + model.getPaypwd();
        	pwd = new PasswordEncoder("").encode(pwd);
        	item.setPaypwd(pwd);
			userService.updateById(item);
			return HttpResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return HttpResult.error(e.getMessage());
		}
    }
    /**
     * 修改
     *
     * @param model
     * @return
     */
     @PreAuthorize("hasAuthority('firm:member:edit')")
     @RequestMapping("/editStatus")
     public HttpResult editStatus(@RequestBody User model) {
         try {
 			userService.updateStatus(model);
 			return HttpResult.ok();
 		} catch (Exception e) {
 			e.printStackTrace();
 			return HttpResult.error(e.getMessage());
 		}
     }
    /**
     * 修改
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('firm:member:del')")
    @RequestMapping("/delete")
    public HttpResult delete(@RequestBody User model) {
    	 try {
 			userService.deleteUser(model);
 			return HttpResult.ok();
 		} catch (Exception e) {
 			e.printStackTrace();
 			return HttpResult.error(e.getMessage());
 		}
    }

    /**
    * 查询详情
    *
    * @param id
    * @return
    */
    @RequestMapping("/getById")
    public HttpResult getById(@RequestBody User model) {
      model = userService.getById(model.getUserno());
        // todo 再包装一层
        return HttpResult.ok(model);
    }


    /**
    * 列表查询（非分页）
    *
    * @return
    */
    @RequestMapping("/list")
    public HttpResult list(@RequestBody User model) {
        List<User> list =  userService.list(model);
        // todo 再包装一层
        return HttpResult.ok(list);
    }
    /**
     * 列表查询（非分页）
     *
     * @return
     */
     @PreAuthorize("hasAuthority('firm:member:view')")
     @RequestMapping("/findParent")
     public HttpResult findParent(@RequestBody User model) {
    	 model.setOrgtype(1);
    	 model.setIsvalid("Y");
    	 
         List<User> list =  userService.list(model);
         // todo 再包装一层
         return HttpResult.ok(list);
     }

    /**
    * 列表查询（分页）
    *
    * @return
    */
//    @PreAuthorize("hasAuthority('firm:member:view')")
    @RequestMapping("/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        PageResult page = userService.findPage(pageRequest);
        // todo 再包装一层
        return HttpResult.ok(page);
    }

}
