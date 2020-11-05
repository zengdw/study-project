package com.zengdw.oauth2.server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 15:35
 */
@SpringBootTest
public class Oauth2ServerApplicationTests {
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    public void test1(){
        System.out.println(passwordEncoder.encode("secret1"));
    }
}
