package com.zengdw.oauth2.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/user")
    public Map<String, Object> user(Principal user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user);
        return map;
    }
}
