package com.jiushi.auth.controller;


import com.jiushi.auth.dao.Payload;
import com.jiushi.auth.dao.UserInfo;
//import com.jiushi.auth.util.JwtUtils;
//import com.jiushi.auth.util.RsaUtils;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.PublicKey;

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
//    @Resource
//    private KeyPair keyPair;

    @GetMapping("/add")
    public String add() throws Exception {
        // 获取私钥

//        // 生成token
//        String token = JwtUtils.generateTokenExpireInMinutes(new UserInfo(1L, "zhangsan", "ROLE_ADMIN"), keyPair.getPrivate(), 5);
//        System.out.println("token = " + token);
//
//        // 获取公钥
//        // 解析token
//        Payload<UserInfo> info = JwtUtils.getInfoFromToken(token, keyPair.getPublic(), UserInfo.class);
//
//        System.out.println("info.getExpiration() = " + info.getExpiration());
//        System.out.println("info.getUserInfo() = " + info.getUserInfo());
//        System.out.println("info.getId() = " + info.getId());
       return "";

    }
}
