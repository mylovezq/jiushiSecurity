//package com.jiushi.auth.config.oauth.custom.granter;
//
//import cn.hutool.core.util.StrUtil;
//import com.edocyun.td.sih.auth.domain.ThirdPartyAccessTokenDO;
//import com.edocyun.td.sih.auth.enums.ClientTypeEnum;
//import com.edocyun.td.sih.auth.enums.ThirdPartyLoginEnum;
//import com.edocyun.td.sih.auth.service.config.ApplicationContextAwareUtil;
//import com.edocyun.td.sih.auth.service.impl.UserServiceImpl;
//import com.edocyun.td.sih.auth.service.oauth2.custom.token.ThirdPartyAuthenticationToken;
//import com.edocyun.td.sih.common.object.exception.LoginException;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authentication.AccountStatusException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.provider.*;
//import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
//import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * @program: framk
// * @description:
// * @author:dengmingyang
// * @create:2022-02-22 23:02:14
// */
//public class ThirdPartyTokenGranter extends AbstractTokenGranter {
//
//  private static final String GRANT_TYPE = "third_party";
//
//  private final AuthenticationManager authenticationManager;
//
//  private UserServiceImpl userService = ApplicationContextAwareUtil
//      .getBean("userServiceImpl");
//
//  public ThirdPartyTokenGranter(AuthenticationManager authenticationManager,
//      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
//      OAuth2RequestFactory requestFactory) {
//    this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
//  }
//
//  protected ThirdPartyTokenGranter(AuthenticationManager authenticationManager,
//      AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
//      OAuth2RequestFactory requestFactory, String grantType) {
//    super(tokenServices, clientDetailsService, requestFactory, grantType);
//    this.authenticationManager = authenticationManager;
//  }
//
//  protected OAuth2Authentication getOAuth2Authentication(ClientDetails client,
//      TokenRequest tokenRequest) {
//    Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());
//    //获取授权码  获取第三方类型
//    ThirdPartyLoginEnum accountType = getAccountType(parameters);
//    ClientTypeEnum clientType = getClientType(parameters);
//    String code = parameters.get("code");
//    if(StrUtil.isBlank(code)){
//      throw new LoginException("授权码为空");
//    }
//    //调用feign服务，获取用户的openId
//    ThirdPartyAccessTokenDO accessTokenDTO = userService.getOpenId(clientType,accountType, code);
//    Authentication userAuth = new ThirdPartyAuthenticationToken(clientType,accountType, accessTokenDTO.getOpenid());
//
//    ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
//
//    try {
//      userAuth = this.authenticationManager.authenticate(userAuth);
//    } catch (AccountStatusException ex) {
//      throw new LoginException(ex.getMessage());
//    } catch (BadCredentialsException ex) {
//      throw new LoginException(ex.getMessage());
//    }
//
//    if (userAuth != null && userAuth.isAuthenticated()) {
//      OAuth2Request storedOAuth2Request = this.getRequestFactory()
//          .createOAuth2Request(client, tokenRequest);
//      return new OAuth2Authentication(storedOAuth2Request, userAuth);
//    } else {
//      throw new LoginException("code授权失败: " + code);
//    }
//  }
//
//  private ThirdPartyLoginEnum getAccountType(Map<String, String> parameters) {
//    Integer thirdPartyType = Integer.parseInt(parameters.get("thirdPartyType"));
//    if (thirdPartyType == null) {
//      throw new LoginException("第三方登录类型参数不能为空");
//    }
//    ThirdPartyLoginEnum result = ThirdPartyLoginEnum.getObjByKey(thirdPartyType);
//    if (thirdPartyType == null || result == null) {
//      throw new LoginException("第三方登录类型不存在，请核对参数");
//    }
//    return result;
//  }
//
//  private ClientTypeEnum getClientType(Map<String, String> parameters) {
//    Integer clientType = Integer.parseInt(parameters.get("client_type"));
//    if (clientType == null) {
//      throw new LoginException("客户端类型参数不能为空");
//    }
//    ClientTypeEnum clientTypeEnum = ClientTypeEnum.getObjByKey(clientType);
//    if(clientTypeEnum == null){
//      throw new LoginException("客户端类型不存在，请核对参数");
//    }
//    return clientTypeEnum;
//  }
//
//}
