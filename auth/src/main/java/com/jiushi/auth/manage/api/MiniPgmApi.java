package com.jiushi.auth.manage.api;

import com.jiushi.auth.manage.api.hystrix.MiniPgmHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "wxapi", url = "${wxapi.url}",fallbackFactory = MiniPgmHystrix.class)
public interface MiniPgmApi {
    @GetMapping("/sns/jscode2session")
    String getSessionKey(@RequestParam("appid") String appid,
                         @RequestParam("secret") String secret,
                         @RequestParam("js_code") String js_code,
                         @RequestParam("grant_type")String grant_type);
}