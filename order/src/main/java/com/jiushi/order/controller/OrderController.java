package com.jiushi.order.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import com.jiushi.order.model.UserDTO;

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
        String userStr = request.getHeader("user");
        JSONObject userJsonObject = new JSONObject(userStr);
        return UserDTO.builder()
                .username(userJsonObject.getStr("user_name"))
                .id(Convert.toLong(userJsonObject.get("id")))
                .mobile(userJsonObject.getStr("mobile"))
                .roles(Convert.toList(String.class, userJsonObject.get("authorities"))).build();
    }

}
