package com.jiushi.auth.config;

import com.jiushi.auth.config.oauth.custom.config.MobilePwdSecurityConfig;
import com.jiushi.auth.config.oauth.custom.config.SmsCodeSecurityConfig;
import com.jiushi.auth.config.oauth.custom.config.ThirdPartySecurityConfig;
import com.jiushi.auth.service.JiushiUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author dengmingyang
 * @version 1.0
 **/
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private MobilePwdSecurityConfig mobilePwdSecurityConfig;
    @Resource
    private SmsCodeSecurityConfig smsCodeSecurityConfig;
    @Resource
    private ThirdPartySecurityConfig thirdPartySecurityConfig;

    @Autowired
    private JiushiUserDetailsService jiushiUserDetailsService;
    //认证管理器
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers("/rsa/publicKey","/oauth/token","/login/getSessionId").permitAll()
                .and().authorizeRequests().anyRequest().authenticated()
                .and()
                .apply(mobilePwdSecurityConfig)
                .and()
                .apply(smsCodeSecurityConfig)
                .and()
                .apply(thirdPartySecurityConfig)
                ;

    }

    /**
     * 自定义认证器
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * 其实就是UsernamePasswordAuthenticationToken  的  daoAuthenticationProvider
     * @return
     */
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(jiushiUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
}
