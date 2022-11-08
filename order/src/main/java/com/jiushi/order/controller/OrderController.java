package com.jiushi.order.controller;

import com.jiushi.order.model.UserDTO;

import com.jiushi.core.common.context.AuthUserContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author my deng
 * @since 2022/11/3 17:56
 */
@RestController
public class OrderController {

    @GetMapping(value = "/hello")
    public String r1(){
      return "hello 邓明阳";
    }

    @GetMapping("/currentUser")
    public UserDTO currentUser(HttpServletRequest request) {
        // 从Header中获取用户信息
        System.out.println(AuthUserContext.getJwtPayload());
        System.out.println(AuthUserContext.getUserId());
        return null;

    }

}
