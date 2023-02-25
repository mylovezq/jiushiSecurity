package com.jiushi.auth.config.oauth.custom.granter;


import com.jiushi.auth.config.oauth.custom.token.MobilePwdAuthenticationToken;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.security.auth.login.LoginException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: framk
 * @description: 自定义手机号+密码登录
 * @author:dengmingyang
 * @create:2022-02-24 17:58:06
 */
public class MobilePwdTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "mobile_pwd";

  private final AuthenticationManager authenticationManager;

  public MobilePwdTokenGranter(AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
  }

  protected MobilePwdTokenGranter(AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory, String grantType) {
    super(tokenServices, clientDetailsService, requestFactory, grantType);
    this.authenticationManager = authenticationManager;
  }

  @SneakyThrows
  @Override
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
      TokenRequest tokenRequest) {
    Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
    String phone = parameters.get("phone");
    String password = parameters.get("password");
    Authentication userAuth = new MobilePwdAuthenticationToken(phone, password);

    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
    try {
      userAuth = this.authenticationManager.authenticate(userAuth);
    } catch (AccountStatusException ex) {
      throw new LoginException(ex.getMessage());
    } catch (BadCredentialsException ex) {
      throw new LoginException(ex.getMessage());
    }

    if (userAuth != null && userAuth.isAuthenticated()) {
      OAuth2Request storedOAuth2Request = this.getRequestFactory()
          .createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);
    } else {
      throw new LoginException("Could not authenticate mobile: " + phone);
    }
  }


}
