package com.zengdw.oauth2.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @description:
 * @author: zengd
 * @date: 2020/10/30 15:44
 */
@Slf4j
@RestController
public class UserController {
    @GetMapping("/user")
    public Principal user(HttpServletRequest request, Principal principal) {
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
        OAuth2Request storedRequest = oAuth2Authentication.getOAuth2Request();
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        return principal;
    }
}
