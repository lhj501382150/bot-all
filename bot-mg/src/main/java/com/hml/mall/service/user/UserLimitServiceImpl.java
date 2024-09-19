package com.hml.mall.service.user;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hml.core.page.MybatisPlusPageHelper;
import com.hml.core.page.PageRequest;
import com.hml.core.page.PageResult;
import com.hml.mall.entity.user.UserLimit;
import com.hml.mall.iface.user.IUserLimitService;
import com.hml.mall.mapper.user.UserLimitMapper;
import com.hml.redis.RedisUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hml
 * @since 2024-09-19
 */

@Service("userLimitService")
public class UserLimitServiceImpl extends ServiceImpl<UserLimitMapper, UserLimit> implements IUserLimitService {

	@Autowired
	private UserLimitMapper userLimitMapper;
	
	@Autowired
	private RedisUtils redisUtils;
	
	private String REDIS_KEY = "USER_LIMIT_AUTH:";

    @Override
    public List< UserLimit> list(UserLimit model) {
       QueryWrapper< UserLimit> wrapper = new QueryWrapper();
       return super.list(wrapper);
    }

    @Override
	public PageResult findPage(PageRequest pageRequest) {
		 
		return MybatisPlusPageHelper.findPage(pageRequest, userLimitMapper);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
	public boolean save(UserLimit entity) {
    	 UserLimit item = userLimitMapper.selectById(entity.getUserno());
    	 if(item != null) {
    		 throw new RuntimeException(entity.getUserno() + "已存在");
    	 }
		 boolean flag = super.save(entity);
		 redisUtils.set(REDIS_KEY + entity.getUserno(),  JSONObject.toJSONString(entity));
		 return flag;
	}
    
    @Override
    @Transactional(rollbackFor = Exception.class)
	public boolean updateById(UserLimit entity) {
		
		 boolean flag = super.updateById(entity);
		 redisUtils.set(REDIS_KEY + entity.getUserno(),  JSONObject.toJSONString(entity));
		 return flag;
	}
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(Serializable id) {
    	 boolean flag = super.removeById(id);
    	 redisUtils.del(REDIS_KEY + id);
    	return flag;
    }
}
