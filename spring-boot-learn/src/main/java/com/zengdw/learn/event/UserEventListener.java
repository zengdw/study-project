package com.zengdw.learn.event;

import com.zengdw.learn.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author : zengd
 * @date : Created in 2021/1/19 15:23
 * @description: 事件监听
 * @version: 1.0
 */
@Slf4j
@Component
public class UserEventListener {
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleEvent(User user){
        log.info("Transactional: {}", user.toString());
    }

    @Async
    @EventListener
    public void handleEvent1(String obj) throws Exception {
        log.info(Thread.currentThread().getName());
        log.info(obj);
        throw new Exception("exception");
    }

    /**
     * 监听上下文刷新事件
     */
    @EventListener
    public void handleEvent2(ContextRefreshedEvent cse) {

    }
}
