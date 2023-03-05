package com.jiushi.auth.controller;

import com.jiushi.auth.service.IAuthService;
import com.jiushi.core.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/login")
public class UserController {
    @Resource
    private  IAuthService authService;

    @GetMapping("/getSessionId")
    public Result getSessionId(@RequestParam String code) {
        return authService.getSessionId(code);
    }
}
