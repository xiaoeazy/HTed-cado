

package com.huan.HTed.message.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.huan.HTed.core.AppContextInitListener;
import com.huan.HTed.core.IRequest;
import com.huan.HTed.core.impl.RequestHelper;
import com.huan.HTed.message.IMessageConsumer;
import com.huan.HTed.message.TopicMonitor;
import com.huan.HTed.message.profile.GlobalProfileListener;
import com.huan.HTed.message.profile.ListenerInitHandler;
import com.huan.HTed.message.profile.SystemConfigListener;
import com.huan.HTed.system.dto.GlobalProfile;

@Component
@TopicMonitor(channel = { "config" })
public class GlobalProfileSubscriber implements IMessageConsumer<GlobalProfile>, ListenerInitHandler, AppContextInitListener {

    private static Map<GlobalProfileListener, List<String>> listenerMap = new HashMap<>();



    private Logger logger = LoggerFactory.getLogger(GlobalProfileSubscriber.class);

    public void addListener(GlobalProfileListener listener) {
        listenerMap.put(listener, listener.getAcceptedProfiles());
        initLoad(listener);
    }

    @Override
    public void initLoad(GlobalProfileListener listener) {
        if (logger.isDebugEnabled()) {
            logger.debug("initial load profile values for:" + listener);
        }
        // 初始化 系统配置
        if (listener instanceof SystemConfigListener) {
//            for (String configCode : listener.getAcceptedProfiles()) {
//                String configValue = configService.getConfigValue(configCode);
//                if (configValue != null) {
//                    listener.updateProfile(configCode, configValue);
//                }
//            }
        }
    }

    @Override
    public void onMessage(GlobalProfile message, String pattern) {
        listenerMap.forEach((k, v) -> {
            // channel=config notify SystemConfig
            if ("config".equalsIgnoreCase(pattern)) {
                if (k instanceof SystemConfigListener) {
                    if (v.contains(message.getProfileName())) {
                        k.updateProfile(message.getProfileName(), message.getProfileValue());
                    }
                }
            }
        });
    }

    /**
     * find all GlobalProfileListener .
     * 
     * @throws Exception
     */
    @Override
    public void contextInitialized(ApplicationContext applicationContext) {
        Map<String, GlobalProfileListener> listeners = applicationContext.getBeansOfType(GlobalProfileListener.class);
        listeners.forEach((k, v) -> addListener(v));
    }

}
