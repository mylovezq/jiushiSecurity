package com.jiushi.auth.manage.api.hystrix;

import com.jiushi.auth.manage.api.MiniPgmApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MiniPgmHystrix implements FallbackFactory<MiniPgmApi> {
    @Override
    public MiniPgmApi create(Throwable throwable) {
        return new MiniPgmApi() {
            @Override
            public String getSessionKey(String appid, String secret, String js_code, String grant_type) {
                return "微信服务异常";
            }
        };
    }
}