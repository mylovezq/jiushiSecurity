//package com.jiushi.auth.config.custom.provider;
//
//import com.edocyun.td.sih.auth.service.oauth2.custom.config.SecurityUser;
//import com.edocyun.td.sih.auth.dto.UserDTO;
//import com.edocyun.td.sih.auth.enums.ClientTypeEnum;
//import com.edocyun.td.sih.auth.enums.ThirdPartyLoginEnum;
//import com.edocyun.td.sih.auth.service.config.ApplicationContextAwareUtil;
//import com.edocyun.td.sih.auth.service.impl.UserServiceImpl;
//import com.edocyun.td.sih.auth.service.oauth2.custom.token.ThirdPartyAuthenticationToken;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Map;
//
///**
// * Description: 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
// *
// * @author dengmingyang
// * @date 2020/7/13 13:07
// */
//@Log4j2
//public class ThirdPartyAuthenticationProvider implements AuthenticationProvider {
//
//  private UserServiceImpl userService = ApplicationContextAwareUtil.getBean("userServiceImpl");
//  private StringRedisTemplate redisTemplate = ApplicationContextAwareUtil
//      .getBean("stringRedisTemplate");
//
//  @Override
//  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//    ThirdPartyAuthenticationToken thirdPartyAuthenticationToken = (ThirdPartyAuthenticationToken) authentication;
//    String openId = (String) thirdPartyAuthenticationToken.getPrincipal();
//    Map<String, String> details = (Map<String, String>) authentication.getDetails();
//    ThirdPartyLoginEnum accountType = thirdPartyAuthenticationToken.getAccountType();
//    ClientTypeEnum clientType = thirdPartyAuthenticationToken.getClientTypeEnum();
//    //根据账户类型+openId获取用户access_token，使用策略模式
//    //通过openId+access_token获取用户个人信息
//    //openId 查询各端数据库，是否已经有用户。如果没有直接返回。如果存在颁发token
//    UserDTO user = userService.getThirdPartyUserInfo(accountType, openId);
//    //调用三端服务，注册或者更新用户登录信息
//    user.setThirdPartyType(accountType.getKey());
//    user = userService.loginUserByThirdParty(clientType,user,details);
//    user.setClientType(clientType.getKey());
//    //封装第三方登录类型
//    user.setThirdPartyType(accountType.getKey());
//    //返回用户信息构建token
//    UserDetails userDetails = buildUserDetails(user);
//    //授权通过
//    return new ThirdPartyAuthenticationToken(userDetails, userDetails.getAuthorities());
//  }
//
//  /**
//   * 构建用户认证信息
//   *
//   * @param user 用户对象
//   * @return UserDetails
//   */
//  private UserDetails buildUserDetails(UserDTO user) {
//    return new SecurityUser(user);
//  }
//
//
//  /**
//   * ProviderManager 选择具体Provider时根据此方法判断 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
//   */
//  @Override
//  public boolean supports(Class<?> authentication) {
//    return ThirdPartyAuthenticationToken.class.isAssignableFrom(authentication);
//  }
//}
