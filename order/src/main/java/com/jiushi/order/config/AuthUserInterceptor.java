package com.jiushi.order.config;

import com.alibaba.fastjson.JSONObject;

import context.AuthUserContext;
import model.PayloadDto;
import model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 认证用户拦截器
 *
 */
@Component
public class AuthUserInterceptor implements HandlerInterceptor {

    /**
     * 调用时间：Controller方法处理之前
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String dtoString = request.getHeader("user");
        if (StringUtils.hasText(dtoString)) {
            byte[] bytes = Base64Utils.decodeFromString(dtoString);
            String jsonString = new String(bytes, StandardCharsets.UTF_8);
            PayloadDto payloadDto = JSONObject.parseObject(jsonString, PayloadDto.class);
            AuthUserContext.setJwtPayload(payloadDto);
        }
        return true;
    }

    /**
     * 调用时间：Controller方法处理完之后
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 调用时间：DispatcherServlet进行视图的渲染之后
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除ThreadLocal，防止内存泄露
        AuthUserContext.clear();
    }

}
