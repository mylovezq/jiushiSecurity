package com.jiushi.auth.config;

import com.jiushi.auth.model.principal.JiushiUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.concurrent.TimeUnit;

/**
 * @author dengmingyang
 * @version 1.0
 **/
@Configuration
public class  TokenConfig {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public TokenStore tokenStore() {
        //JWT令牌存储方案
        return new JwtTokenStore(jwtAccessTokenConverter()) {
            @Override
            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
                Object object = authentication.getPrincipal();
                if (object instanceof JiushiUser) {
                    JiushiUser securityUser = (JiushiUser) object;
                    //用户主键
                    String userId = securityUser.getId().toString();
                    String prefix = ":";
                    String key = new StringBuilder("jiushi")
                            .append(prefix)
                            .append(userId)
                            .toString();
                    stringRedisTemplate.opsForValue().set(key, token.getValue(), token.getExpiresIn(), TimeUnit.SECONDS);
                }
            }
        };
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new MyJwtAccessTokenConfig();
        jwtAccessTokenConverter.setKeyPair(keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        // 从classpath下的证书中获取秘钥对
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "jiushiyuni".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt", "jiushiyuni".toCharArray());
    }


}
