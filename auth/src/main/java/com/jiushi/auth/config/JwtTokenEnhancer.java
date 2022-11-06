package com.jiushi.auth.config;


import com.jiushi.auth.model.principal.UserPrincipal;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {
  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
    Map<String, Object> info = new HashMap<>();
    // 把用户ID设置到JWT中
    info.put("id", user.getId());
    info.put("userName", user.getUsername());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
    return accessToken;
  }
}