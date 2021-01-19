package com.zengdw.learn.event;

import com.zengdw.learn.domain.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : zengd
 * @date : Created in 2021/1/19 15:18
 * @description: 事件发布
 * @version: 1.0
 */
@Service
public class UserEventRegister {
    @Resource
    private ApplicationEventPublisher publisher;

    public void register(){
        User user = new User()
                .setId(1L)
                .setName("zengd")
                .setAge(22);
        publisher.publishEvent("user");
        publisher.publishEvent(user);
    }
}
