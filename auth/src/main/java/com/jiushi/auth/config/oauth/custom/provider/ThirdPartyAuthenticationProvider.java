package com.jiushi.auth.config.oauth.custom.provider;

import com.jiushi.auth.config.oauth.custom.config.ApplicationContextAwareUtil;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyAuthenticationToken;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.model.entity.UserDO;
import com.jiushi.auth.model.principal.JiushiUser;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/**
 * Description: 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 *
 * @author dengmingyang
 * @date 2020/7/13 13:07
 */
@Log4j2
public class ThirdPartyAuthenticationProvider implements AuthenticationProvider {


  private StringRedisTemplate redisTemplate = ApplicationContextAwareUtil.getBean("stringRedisTemplate");
  private UserDao userDao = ApplicationContextAwareUtil.getBean("userDao");

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    ThirdPartyAuthenticationToken thirdPartyAuthenticationToken = (ThirdPartyAuthenticationToken) authentication;
    String openId = (String) thirdPartyAuthenticationToken.getPrincipal();
    Map<String, String> details = (Map<String, String>) authentication.getDetails();

    Integer thirdPartyLoginType = thirdPartyAuthenticationToken.getThirdPartyLoginType();

    //根据账户类型+openId获取用户access_token，使用策略模式
    //通过openId+access_token获取用户个人信息

    UserDO user = userDao.getUserByMobile("17612520985");
    List<String> permissions = userDao.findPermissionsByUserId(user.getId());
    user.setRoles(permissions);
    //调用三端服务，注册或者更新用户登录信息
    //封装第三方登录类型
    user.setThirdPartyLoginType(thirdPartyLoginType);
    user.setOpenId(openId);
    //返回用户信息构建token
    UserDetails userDetails = buildUserDetails(user);
    //授权通过
    return new ThirdPartyAuthenticationToken(userDetails, userDetails.getAuthorities());
  }

  /**
   * 构建用户认证信息
   *
   * @param user 用户对象
   * @return UserDetails
   */
  private UserDetails buildUserDetails(UserDO user) {
    return new JiushiUser(user);
  }


  /**
   * ProviderManager 选择具体Provider时根据此方法判断 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return ThirdPartyAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
