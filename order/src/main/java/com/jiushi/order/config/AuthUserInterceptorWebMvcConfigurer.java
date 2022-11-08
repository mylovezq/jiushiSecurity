package com.jiushi.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  为请求添加user的上下文,user从header中获取
 * @author haven
 * @since 2022/3/1 14:30
 */
@Component
@Configurable
public class AuthUserInterceptorWebMvcConfigurer implements WebMvcConfigurer {
  @Autowired
  private AuthUserInterceptor authUserInterceptor;
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    WebMvcConfigurer.super.addInterceptors(registry);
    registry.addInterceptor(authUserInterceptor)
        .addPathPatterns("/**");
  }
}
