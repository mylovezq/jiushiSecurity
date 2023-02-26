package com.jiushi.auth.config.oauth.custom.granter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jiushi.auth.config.oauth.custom.config.ApplicationContextAwareUtil;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyAuthenticationToken;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyLoginEnum;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.manage.api.MiniPgmApi;
import com.jiushi.auth.manage.api.config.ApiConfig;
import com.jiushi.auth.manage.api.dto.ThirdPartyAccessTokenDTO;
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
 * @description:
 * @author:dengmingyang
 * @create:2022-02-22 23:02:14
 */

public class ThirdPartyTokenGranter extends AbstractTokenGranter {

  private static final String GRANT_TYPE = "third_party";

  private final AuthenticationManager authenticationManager;
  private  MiniPgmApi miniPgmApi  = ApplicationContextAwareUtil.getFeignBean("miniPgmApi",MiniPgmApi.class);

  private UserDao userDao = ApplicationContextAwareUtil.getBean("userDao");
  private ApiConfig apiConfig = ApplicationContextAwareUtil.getBean("apiConfig");

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
    String code = parameters.get("code");
    if(StrUtil.isBlank(code)){
      throw new LoginException("授权码为空");
    }
    //调用feign服务，获取用户的openId
    String openIdStr
            = miniPgmApi.getSessionKey(apiConfig.getAppid(), apiConfig.getAppsecret(),code,"authorization_code");
    ThirdPartyAccessTokenDTO thirdPartyAccessTokenDTO = JSONUtil.toBean(openIdStr, ThirdPartyAccessTokenDTO.class);
    Authentication userAuth = new ThirdPartyAuthenticationToken(accountType.getKey(), thirdPartyAccessTokenDTO.getOpenid());

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
      throw new LoginException("code授权失败: " + code);
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
