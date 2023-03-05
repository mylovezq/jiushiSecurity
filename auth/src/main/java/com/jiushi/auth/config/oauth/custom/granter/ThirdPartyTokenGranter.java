package com.jiushi.auth.config.oauth.custom.granter;

import cn.hutool.core.util.StrUtil;
import com.jiushi.auth.config.oauth.custom.config.ApplicationContextAwareUtil;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyAuthenticationToken;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyLoginEnum;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.manage.api.MiniPgmApi;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
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
 * @description:
 * @author:dengmingyang
 * @create:2022-02-22 23:02:14
 */

public class ThirdPartyTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "third_party";

  private final AuthenticationManager authenticationManager;
  private  MiniPgmApi miniPgmApi  = ApplicationContextAwareUtil.getFeignBean("miniPgmApi",MiniPgmApi.class);

  private UserDao userDao = ApplicationContextAwareUtil.getBean("userDao");
  private StringRedisTemplate stringRedisTemplate = ApplicationContextAwareUtil.getBean("stringRedisTemplate");


  public ThirdPartyTokenGranter(AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory) {
    this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
  }

  protected ThirdPartyTokenGranter(AuthenticationManager authenticationManager,
      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
      OAuth2RequestFactory requestFactory, String grantType) {
    super(tokenServices, clientDetailsService, requestFactory, grantType);
    this.authenticationManager = authenticationManager;
  }

  @SneakyThrows
  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
                                                         TokenRequest tokenRequest) {
    Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
    //获取授权码  获取第三方类型
    ThirdPartyLoginEnum accountType = getAccountType(parameters);
    String sessionId = parameters.get("sessionId");
    if(StrUtil.isBlank(sessionId)){
      throw new LoginException("sessionId不能为空");
    }
    //构建未授权的 ThirdPartyAuthenticationToken
    Authentication userAuth = new ThirdPartyAuthenticationToken(accountType.getKey(), sessionId);

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
      throw new LoginException("sessionId不合法");
    }
  }

  private ThirdPartyLoginEnum getAccountType(Map<String, String> parameters) throws LoginException {
    Integer thirdPartyType = Integer.parseInt(parameters.get("thirdPartyType"));
    if (thirdPartyType == null) {
      throw new LoginException("第三方登录类型参数不能为空");
    }
    ThirdPartyLoginEnum result = ThirdPartyLoginEnum.getObjByKey(thirdPartyType);
    if (thirdPartyType == null || result == null) {
      throw new LoginException("第三方登录类型不存在，请核对参数");
    }
    return result;
  }


}
