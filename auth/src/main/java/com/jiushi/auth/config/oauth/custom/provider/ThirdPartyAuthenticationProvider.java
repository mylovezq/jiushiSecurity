package com.jiushi.auth.config.oauth.custom.provider;

import com.jiushi.auth.config.oauth.custom.config.ApplicationContextAwareUtil;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyAuthenticationToken;
import com.jiushi.auth.config.oauth.custom.token.ThirdPartyLoginEnum;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.dao.pojo.UserDO;
import com.jiushi.auth.model.principal.JiushiUser;
import com.jiushi.auth.service.IThirdPartyLogin;
import com.jiushi.auth.service.impl.ThirdPartyStrategy;
import lombok.SneakyThrows;
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

  private ThirdPartyStrategy thirdPartyLogin = ApplicationContextAwareUtil.getBean("thirdPartyStrategy");

  @SneakyThrows
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {


    Map<String, String> detailsParametersMap = (Map<String, String>) authentication.getDetails();

    ThirdPartyAuthenticationToken thirdPartyAuthenticationToken = (ThirdPartyAuthenticationToken) authentication;
    String sessionId = (String) thirdPartyAuthenticationToken.getPrincipal();
    Integer thirdPartyLoginType = thirdPartyAuthenticationToken.getThirdPartyLoginType();

    //根据thirdPartyLoginType获取用户access_token，使用策略模式
    //通过sessionId返回UserDO
    IThirdPartyLogin thirdPartyLoginService
            = thirdPartyLogin.getThirdPartyLogin(ThirdPartyLoginEnum.getObjByKey(thirdPartyLoginType).name());
    UserDO userDO = thirdPartyLoginService.registerOrLogin(sessionId,detailsParametersMap);

    //返回用户信息构建token
    UserDetails userDetails = buildUserDetails(userDO);
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
