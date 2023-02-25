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
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 520L;

  /**
   * 账号主体信息，手机号验证码登录体系中代表 手机号码
   */
  private final Object principal;
  private  String smsCode;



  /**
   * 构建未授权的 SmsCodeAuthenticationToken
   *
   * @param mobile 手机号码
   */
  public SmsCodeAuthenticationToken(String mobile,String smsCode) {
    super(null);
    this.principal = mobile;
    this.smsCode = smsCode;
    setAuthenticated(false);
  }


  /**
   * 构建已经授权的 SmsCodeAuthenticationToken
   */
  public SmsCodeAuthenticationToken(Object principal,
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

  public String getSmsCode() {
    return smsCode;
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
