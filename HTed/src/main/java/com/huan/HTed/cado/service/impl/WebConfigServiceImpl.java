package com.huan.HTed.cado.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huan.HTed.cache.CacheSet;
import com.huan.HTed.cado.dto.WebConfig;
import com.huan.HTed.cado.service.IWebConfigService;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.message.IMessagePublisher;
import com.huan.HTed.system.dto.GlobalProfile;
import com.huan.HTed.system.service.impl.BaseServiceImpl;

@Service
@Transactional(rollbackFor = Exception.class)
public class WebConfigServiceImpl extends BaseServiceImpl<WebConfig> implements IWebConfigService{
		@Autowired
	    private IMessagePublisher messagePublisher;
		
		
		 @Override
	    @CacheSet(cache = "config")
	    public WebConfig updateByPrimaryKeySelective(IRequest request, WebConfig config) {
	        super.updateByPrimaryKeySelective(request, config);
	        //配置更改时 通知监听者
	        messagePublisher.publish("config",new GlobalProfile(config.getConfigKey(), config.getConfigValue()));
	        return config;
	    }
}