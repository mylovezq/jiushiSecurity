package com.jiushi.auth.config.oauth.custom.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @program: framk
 * @description:
 * @author:dengmingyang
 * @create:2022-02-24 17:26:14
 */
public class ThirdPartyAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 520L;

  /**
   * 账号主体信息，openId 手机号码
   */
  private final Object principal;

  private Integer thirdPartyLoginType;

  public Integer getThirdPartyLoginType() {
    return thirdPartyLoginType;
  }


  /**
   * 构建未授权的 ThirdPartyAuthenticationToken
   *
   * @param thirdPartyLoginType 三方登录类型
   * @param openId
   */
  public ThirdPartyAuthenticationToken(Integer thirdPartyLoginType, String openId) {
    super(null);
    this.principal = openId;
    this.thirdPartyLoginType = thirdPartyLoginType;
    setAuthenticated(false);
  }


  /**
   * 构建已经授权的 ThirdPartyAuthenticationToken
   */
  public ThirdPartyAuthenticationToken(Object principal,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    super.setAuthenticated(true);
  }


  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return this.principal;
  }


  @Override
  public void setAuthenticated(boolean isAuthenticated) {
    if (isAuthenticated) {
      throw new IllegalArgumentException(
          "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    } else {
      super.setAuthenticated(false);
    }
  }

  @Override
  public void eraseCredentials() {
    super.eraseCredentials();
  }
}
