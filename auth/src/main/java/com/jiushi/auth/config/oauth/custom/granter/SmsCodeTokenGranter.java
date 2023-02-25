package com.jiushi.auth.config.oauth.custom.granter;



import com.jiushi.auth.config.oauth.custom.token.SmsCodeAuthenticationToken;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.security.auth.login.LoginException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program: framk
 * @description:
 * @author:dengmingyang
 * @create:2022-02-24 17:58:06
 */
public class SmsCodeTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "sms_code";

  private final AuthenticationManager authenticationManager;

  public SmsCodeTokenGranter(AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
  }

  protected SmsCodeTokenGranter(AuthenticationManager authenticationManager,
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
    String smsCode = parameters.get("sms_code");
    Authentication userAuth = new SmsCodeAuthenticationToken(phone,smsCode);

    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

    try {
      userAuth = this.authenticationManager.authenticate(userAuth);
    } catch (AccountStatusException ex) {
      throw new InvalidGrantException(ex.getMessage());
    } catch (BadCredentialsException ex) {
      throw new InvalidGrantException(ex.getMessage());
    }

    if (userAuth != null && userAuth.isAuthenticated()) {
      OAuth2Request storedOAuth2Request = this.getRequestFactory()
          .createOAuth2Request(client, tokenRequest);
      return new OAuth2Authentication(storedOAuth2Request, userAuth);
    } else {
      throw new LoginException("当前手机号没有权限: " + phone);
    }
  }
}
