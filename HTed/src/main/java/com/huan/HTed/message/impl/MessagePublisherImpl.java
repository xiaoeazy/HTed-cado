
package com.huan.HTed.message.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huan.HTed.message.IMessagePublisher;

@Component
public class MessagePublisherImpl implements IMessagePublisher {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(MessagePublisherImpl.class);

    @Override
    public void publish(String channel, Object message) {
        if (message instanceof String || message instanceof Number) {
            redisTemplate.convertAndSend(channel, message.toString());
        } else {
            try {
                redisTemplate.convertAndSend(channel, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("publish message failed.", e);
                }
            }
        }
    }

    @Override
    public void rPush(String list, Object message) {
        if (message instanceof String || message instanceof Number) {
            redisTemplate.opsForList().rightPush(list, message.toString());
        } else {
            try {
                redisTemplate.opsForList().rightPush(list, objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                if (logger.isErrorEnabled()) {
                    logger.error("push data failed.", e);
                }
            }
        }
    }
}
