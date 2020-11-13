package com.zengdw.oauth2.client.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: zengd
 * @date: 2020/11/5 17:20
 */
@RestController
public class TestController {
    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/test")
    public String test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", details.getTokenType()+details.getTokenValue());
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("http://127.0.0.1:9002/test", HttpMethod.GET, entity, String.class).getBody();
    }

    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }
}
