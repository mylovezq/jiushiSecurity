package com.jiushi.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wxminipro")
@Data
public class ApiConfig {

    private String appid;

    private String appsecret;

    private Pay pay;


    @Data
    public static class Pay{
        private String mchId;
        private String key;
        private String callBack;

    }

}
