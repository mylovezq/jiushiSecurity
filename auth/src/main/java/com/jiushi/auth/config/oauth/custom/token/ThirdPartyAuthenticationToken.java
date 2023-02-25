//package com.jiushi.auth.config.oauth.custom.token;
//
//import com.edocyun.td.sih.auth.enums.ClientTypeEnum;
//import com.edocyun.td.sih.auth.enums.ThirdPartyLoginEnum;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// * @program: framk
// * @description:
// * @author:dengmingyang
// * @create:2022-02-24 17:26:14
// */
//public class ThirdPartyAuthenticationToken extends AbstractAuthenticationToken {
//
//    private static final long serialVersionUID = 520L;
//
//  /**
//   * 账号主体信息，手机号验证码登录体系中代表 手机号码
//   */
//  private final Object principal;
//
//  private ThirdPartyLoginEnum accountType;
//
//  public ClientTypeEnum getClientTypeEnum() {
//    return clientTypeEnum;
//  }
//
//  private ClientTypeEnum clientTypeEnum;
//
//  public ThirdPartyLoginEnum getAccountType() {
//    return accountType;
//  }
//
//
//  /**
//   * 构建未授权的 SmsCodeAuthenticationToken
//   *
//   * @param accountType 三方登录类型
//   * @param openId
//   */
//  public ThirdPartyAuthenticationToken(ClientTypeEnum clientTypeEnum,
//      ThirdPartyLoginEnum accountType, String openId) {
//    super(null);
//    this.principal = openId;
//    this.clientTypeEnum = clientTypeEnum;
//    this.accountType = accountType;
//    setAuthenticated(false);
//  }
//
//
//  /**
//   * 构建已经授权的 SmsCodeAuthenticationToken
//   */
//  public ThirdPartyAuthenticationToken(Object principal,
//      Collection<? extends GrantedAuthority> authorities) {
//    super(authorities);
//    this.principal = principal;
//    super.setAuthenticated(true);
//  }
//
//
//  @Override
//  public Object getCredentials() {
//    return null;
//  }
//
//  @Override
//  public Object getPrincipal() {
//    return this.principal;
//  }
//
//
//  @Override
//  public void setAuthenticated(boolean isAuthenticated) {
//    if (isAuthenticated) {
//      throw new IllegalArgumentException(
//          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//    } else {
//      super.setAuthenticated(false);
//    }
//  }
//
//  @Override
//  public void eraseCredentials() {
//    super.eraseCredentials();
//  }
//}
