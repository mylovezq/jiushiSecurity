package com.jiushi.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import com.jiushi.core.common.model.Result;
import com.jiushi.order.dao.pojo.CoursewareDO;
import com.jiushi.order.model.UserDTO;

import com.jiushi.core.common.context.AuthUserContext;
import com.jiushi.order.service.IOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author my deng
 * @since 2022/11/3 17:56
 */
@RestController
@RequestMapping("/courseWare")
public class OrderController {


    @Resource
    private IOrderService orderService;

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



    @GetMapping("/listCourseWare")
    public Result<CoursewareDO> listCourseWare(@RequestParam Integer start) {
        return orderService.listCourseWare(start);
    }

    @GetMapping("/getCarousel")
    public Result getCarousel() {
        return orderService.getCarousel();
    }

}
