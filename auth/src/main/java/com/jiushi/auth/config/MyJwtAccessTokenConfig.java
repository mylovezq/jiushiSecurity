package com.jiushi.auth.config;

import com.jiushi.auth.model.principal.UserPrincipal;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class MyJwtAccessTokenConfig extends JwtAccessTokenConverter {


    /**
     * 生成token
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken defaultOAuth2AccessToken = new DefaultOAuth2AccessToken(accessToken);
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        // 将用户信息添加到token额外信息中
        defaultOAuth2AccessToken.getAdditionalInformation().put("mobile", user.getMobile());
        defaultOAuth2AccessToken.getAdditionalInformation().put("userId", user.getId());
        return super.enhance(defaultOAuth2AccessToken, authentication);
    }
}
