package com.jiushi.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 将登录用户的JWT转化成用户信息的全局过滤器
 * Created by caipeng on 2021/1/24.
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthGlobalFilter {



}
