package com.jiushi.auth.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/add")
    public String add(){
        System.out.println("66666666666666666666666666");
        return "hello world" + name + port;
    }
}
