package com.zengdw.learn;

import com.zengdw.learn.event.UserEventRegister;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RedisDemoApplicationTests {

    @Resource
    UserEventRegister register;

    @Test
    void contextLoads() {
        register.register();
    }

}
