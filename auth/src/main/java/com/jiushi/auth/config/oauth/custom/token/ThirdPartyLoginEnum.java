package com.jiushi.auth.config.oauth.custom.token;

public enum ThirdPartyLoginEnum {
  QQ(1, "QQ登录"),
  WX(2, "微信登录"),
  IPHONE(3, "苹果登录"),
  MICROBLOG(4, "支付宝登录"),
  MINI_PGM(5,"小程序");
  private Integer key;
  private String name;

  ThirdPartyLoginEnum(Integer key, String name) {
    this.key = key;
    this.name = name;
  }

  public static ThirdPartyLoginEnum getObjByKey(Integer thirdPartyType) {
    for (ThirdPartyLoginEnum obj : ThirdPartyLoginEnum
        .values()) {
      if(obj.getKey().equals(thirdPartyType)){
        return obj;
      }
    }
    return  null;
  }

  public Integer getKey() {
    return key;
  }

  public String getName() {
    return name;
  }
}
