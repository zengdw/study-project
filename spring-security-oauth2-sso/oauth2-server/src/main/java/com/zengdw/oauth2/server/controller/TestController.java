package com.zengdw.oauth2.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 15:44
 */
@RestController
public class TestController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
