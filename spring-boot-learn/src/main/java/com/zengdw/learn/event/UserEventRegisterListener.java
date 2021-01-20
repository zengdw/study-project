package com.zengdw.learn.event;

import com.zengdw.learn.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author : zengd
 * @date : Created in 2021/1/19 15:23
 * @description: 事件监听
 * @version: 1.0
 */
@Slf4j
@Component
public class UserEventRegisterListener {
    @EventListener
    public void handleEvent(User user){
        log.info(user.toString());
    }

    @Async
    @EventListener
    public void handleEvent1(String obj) throws Exception {
        log.info(Thread.currentThread().getName());
        log.info(obj);
        throw new Exception("exception");
    }
}
