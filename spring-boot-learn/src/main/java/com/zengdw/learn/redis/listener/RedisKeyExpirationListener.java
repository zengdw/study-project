package com.zengdw.learn.redis.listener;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author : zengd
 * @date : Created in 2021/1/19 14:07
 * @description: 监听redis key过期事件
 * @version: 1.0
 */
@Component
public class RedisKeyExpirationListener implements MessageListener, DisposableBean {
    private static final Topic KEYEVENT_EXPIRED_TOPIC = new PatternTopic("__keyevent@*__:expired");

    private final RedisMessageListenerContainer container;

    public RedisKeyExpirationListener(RedisMessageListenerContainer container){
        this.container = container;
        container.addMessageListener(this, KEYEVENT_EXPIRED_TOPIC);
    }

    @Override
    public void destroy() {
        container.removeMessageListener(this);
    }

    @Override
    public void onMessage(Message message, byte[] bytes) {
        if (!ObjectUtils.isEmpty(message.getChannel()) && !ObjectUtils.isEmpty(message.getBody())) {
            System.out.println(message);
        }
    }
}
