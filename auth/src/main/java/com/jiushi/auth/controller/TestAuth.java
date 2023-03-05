package com.jiushi.auth.controller;


import com.jiushi.auth.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.KeyPair;

/**
 * @author my deng
 * @since 2022/11/3 11:09
 */
@RestController
@RefreshScope
@RequestMapping("/jiushi")
public class TestAuth {

    @Value("${jiushi.name}")
    private String name;
    @Value("${server.port}")
    private String port;
    @Resource
    private KeyPair keyPair;
    @Autowired
    UserDao userDao;
    @Autowired
    DefaultTokenServices userDaow;
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/add")
    public String add(){
      return "";
    }
}
