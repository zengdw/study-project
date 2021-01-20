package com.zengdw.learn;

import com.zengdw.learn.event.UserEventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class LearnApplicationTests {

    @Resource
    UserEventPublisher register;

    @Test
    void contextLoads() {
        register.register();
    }

}
