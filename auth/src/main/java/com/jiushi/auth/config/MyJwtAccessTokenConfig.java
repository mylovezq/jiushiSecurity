package com.jiushi.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class MyJwtAccessTokenConfig extends JwtAccessTokenConverter {


    /**
     * 可以这里补充生成token里面的内容  也可参考JiushiUserAuthenticationConverter
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
//        JiushiUser user = (JiushiUser) authentication.getPrincipal();
//        // 将用户信息添加到token额外信息中 需要解析的token
//        if (user != null){
//            defaultOAuth2AccessToken.getAdditionalInformation().put("mobile", user.getMobile());
//
//        }
        defaultOAuth2AccessToken.getAdditionalInformation().put("userId自定义", "6666666666");
        return super.enhance(defaultOAuth2AccessToken, authentication);
    }
}
