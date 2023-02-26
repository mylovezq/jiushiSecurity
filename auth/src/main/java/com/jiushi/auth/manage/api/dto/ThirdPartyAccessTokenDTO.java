package com.jiushi.auth.manage.api.dto;

import lombok.Data;

/**
 * @program: back
 * @description:accessTokens数据包
 * @author:caipeng
 * @create:2020-06-30 18:14:30
 */
@Data
public class ThirdPartyAccessTokenDTO {

  private Integer errcode = 0;
  private String errmsg = "成功";
  //公共平台会话token
  private String access_token;
  private Integer expires_in;
  private String refresh_token;
  private String openid;
  private String scope;
  //小程序会话密钥
  private String session_key;
  //用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回，详见 UnionID 机制说明。
  private String unionid;
  //微信返回byte数据
  private byte[] buffer;
}
