package com.zengdw.oauth2.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zengd
 * @date: 2020/11/5 17:20
 */
@RestController
public class TestController {
    @GetMapping("/tset")
    public String test(){
        return "test";
    }
}
