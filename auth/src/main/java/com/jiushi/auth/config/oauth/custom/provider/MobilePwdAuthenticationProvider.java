package com.jiushi.auth.config.oauth.custom.provider;

import cn.hutool.core.util.StrUtil;
import com.jiushi.auth.config.oauth.custom.config.ApplicationContextAwareUtil;
import com.jiushi.auth.config.oauth.custom.token.MobilePwdAuthenticationToken;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.model.entity.UserDO;
import com.jiushi.auth.model.principal.JiushiUser;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.login.LoginException;
import java.util.List;

/**
 * Description: 手机号密码 Provider，要求实现 AuthenticationProvider 接口
 *
 * @author dengmingyang
 * @date 2020/7/13 13:07
 */
@Log4j2
public class MobilePwdAuthenticationProvider implements AuthenticationProvider {


    private UserDao userDao = ApplicationContextAwareUtil.getBean("userDao");

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        MobilePwdAuthenticationToken mobilePwdAuthenticationToken = (MobilePwdAuthenticationToken) authentication;

        String mobile = (String) mobilePwdAuthenticationToken.getPrincipal();
        String password = mobilePwdAuthenticationToken.getPassword();

        //校验手机号密码验证码
        UserDO user = userDao.getUserByMobilePassword(mobile, password);
        List<String> permissions = userDao.findPermissionsByUserId(user.getId());
        user.setRoles(permissions);
        if (null == user) {
            throw new LoginException("Invalid mobile!");
        }
        //授权通过
        UserDetails userDetails = buildUserDetails(user);
        return new MobilePwdAuthenticationToken(userDetails, userDetails.getAuthorities());
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
     * ProviderManager 选择具体Provider时根据此方法判断 判断 authentication 是不是 MobilePwdAuthenticationToken 的子类或子接口
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePwdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
