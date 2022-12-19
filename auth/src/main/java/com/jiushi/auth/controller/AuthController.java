package com.jiushi.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * @description: AuthController
 * @date: 2022/11/20 18:57
 * @author: dengmingyang
 * @version：V1.0.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final TokenEndpoint tokenEndpoint;


    @PostMapping("/token")
    public Object postAccessToken(Principal principal, @RequestParam Map<String,String> param) throws HttpRequestMethodNotSupportedException {
        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(principal, param);
        OAuth2AccessToken body = oAuth2AccessTokenResponseEntity.getBody();
        return body;
    }

}
