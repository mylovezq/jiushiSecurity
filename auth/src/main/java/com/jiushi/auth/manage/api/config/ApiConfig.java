package com.jiushi.auth.manage.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "wxminipro")
@Data
public class ApiConfig {

    private String appid;

    private String appsecret;

}
