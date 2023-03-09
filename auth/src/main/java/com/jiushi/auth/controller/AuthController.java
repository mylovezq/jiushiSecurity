package com.jiushi.auth.controller;

import com.jiushi.auth.manage.api.MiniPgmApi;
import com.jiushi.auth.service.IAuthService;
import com.jiushi.core.common.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: AuthController
 * @date: 2022/11/20 18:57
 * @author: dengmingyang
 * @version：V1.0.0
 */
@RestController
@Slf4j
@RequestMapping("/oauth")
public class AuthController {
    @Resource
    private  TokenEndpoint tokenEndpoint;

    @Resource
    private  IAuthService authService;

    @SneakyThrows
    @GetMapping("/token")
    public Result postAccessToken(Principal principal, @RequestParam Map<String,String> param) {
        ResponseEntity<OAuth2AccessToken> oAuth2AccessTokenResponseEntity = null;
        OAuth2AccessToken body = null;
        try {
            oAuth2AccessTokenResponseEntity = tokenEndpoint.postAccessToken(principal, param);
            body = oAuth2AccessTokenResponseEntity.getBody();
        } catch (Exception e) {
            log.error("授权登录失败",e.getLocalizedMessage());
            throw e;
        }

        return Result.SUCCESS(body);
    }

}
