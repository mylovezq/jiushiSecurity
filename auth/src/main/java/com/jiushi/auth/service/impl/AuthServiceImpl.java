package com.jiushi.auth.service.impl;

import cn.hutool.core.lang.UUID;
import com.jiushi.auth.config.ApiConfig;
import com.jiushi.auth.manage.api.MiniPgmApi;
import com.jiushi.auth.service.IAuthService;

import com.jiushi.core.common.context.AuthUserContext;
import com.jiushi.core.common.model.PayloadDto;
import com.jiushi.core.common.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.jiushi.auth.model.constant.RedisConstant.WX_SESSION_ID;

@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Resource
    private MiniPgmApi miniPgmApi;
    @Resource
    private ApiConfig apiConfig;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result getSessionId(String code) {
        String sessionId = miniPgmApi.getSessionKey(apiConfig.getAppid(),apiConfig.getAppsecret(),code,"authorization_code");
        HashMap<String, String> hashMap = new HashMap<>();
        UUID uuid = UUID.fastUUID();
        stringRedisTemplate.opsForValue().set(WX_SESSION_ID+uuid,sessionId,60, TimeUnit.MINUTES);
        hashMap.put(WX_SESSION_ID,uuid.toString());
        return Result.SUCCESS(hashMap);
    }

    @Override
    public Result userinfo(String token, Boolean refresh) {
        PayloadDto jwtPayload = AuthUserContext.getJwtPayload();

        return Result.SUCCESS(jwtPayload);
    }
}
