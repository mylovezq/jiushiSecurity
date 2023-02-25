package com.jiushi.auth.config.oauth.custom.config;

import com.jiushi.auth.config.oauth.custom.provider.MobilePwdAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * @program: framk
 * @description:
 * @author:dengmingyang
 * @create:2022-02-24 17:54:43
 */
@Component
public class MobilePwdSecurityConfig extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  /**
   * 手机密码配置器 所有的配置都可以移步到WebSecurityConfig builder.authenticationProvider() 相当于
   * auth.authenticationProvider(); 使用外部配置必须要在WebSecurityConfig中用http.apply(smsCodeSecurityConfig)将配置注入进去
   *
   * @param builder
   * @throws Exception
   */
  @Override
  public void configure(HttpSecurity builder) throws Exception {
    //注入MobilePwdAuthenticationProvider
    MobilePwdAuthenticationProvider mobilePwdAuthenticationProvider = new MobilePwdAuthenticationProvider();
    builder.authenticationProvider(mobilePwdAuthenticationProvider);
  }

}
