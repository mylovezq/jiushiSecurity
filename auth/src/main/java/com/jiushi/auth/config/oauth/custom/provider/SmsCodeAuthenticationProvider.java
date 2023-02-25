package com.jiushi.auth.config.oauth.custom.provider;

import cn.hutool.core.util.StrUtil;
import com.jiushi.auth.config.oauth.custom.config.ApplicationContextAwareUtil;
import com.jiushi.auth.config.oauth.custom.token.SmsCodeAuthenticationToken;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.model.entity.UserDO;
import com.jiushi.auth.model.principal.JiushiUser;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * Description: 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 *
 * @author dengmingyang
 * @date 2020/7/13 13:07
 */
@Log4j2
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDao userDao = ApplicationContextAwareUtil.getBean("userDao");
    private StringRedisTemplate redisTemplate = ApplicationContextAwareUtil
            .getBean("stringRedisTemplate");

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) smsCodeAuthenticationToken.getPrincipal();
        //校验手机号验证码
        String smsCode = smsCodeAuthenticationToken.getSmsCode();
        UserDO user = checkSmsCode(mobile, smsCode);

        if (null == user) {
            throw new LoginException("Invalid mobile!");
        }

        //授权通过
        UserDetails userDetails = buildUserDetails(user);
        return new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
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
     * 校验手机号与验证码的绑定关系是否正确 todo 需要根据业务逻辑自行处理
     *
     * @param phone 手机号码
     * @author dengmingyang
     * @date 2020/7/23 17:31
     */
    @SneakyThrows
    private UserDO checkSmsCode(String phone, String smsCode) {

        if (StrUtil.isBlank(phone)) {
            throw new LoginException("手机号不能为空");
        }
        if (StrUtil.isBlank(smsCode)) {
            throw new LoginException("验证码不能为空");
        }


        UserDO user = userDao.getUserByMobilePassword(phone, "password");
        List<String> permissions = userDao.findPermissionsByUserId(user.getId());
        user.setRoles(permissions);
        if (null == user) {
            throw new LoginException("Invalid mobile!");
        }
        return user;
    }

    /**
     * ProviderManager 选择具体Provider时根据此方法判断 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
