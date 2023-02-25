package com.jiushi.auth.controller;


import com.jiushi.auth.constant.MessageConstant;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.model.Payload;
import com.jiushi.auth.model.entity.UserDO;
import com.jiushi.auth.model.principal.JiushiUser;
import com.jiushi.auth.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * @author my deng
 * @since 2022/11/3 11:09
 */
@RestController
@RefreshScope
@RequestMapping("/jiushi")
public class TestAuth {

    @Value("${jiushi.name}")
    private String name;
    @Value("${server.port}")
    private String port;
    @Resource
    private KeyPair keyPair;
    @Autowired
    UserDao userDao;
    @Autowired
    DefaultTokenServices userDaow;
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @PostMapping("/add")
    public String add(){

     //将来连接数据库根据账号查询用户信息
        UserDO userDto = userDao.getUserByUsername("zhangsan");
        if(userDto == null){
            //如果用户查不到，返回null，由provider来抛出异常
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);

        }
        //根据用户的id查询用户的权限
        List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
        userDto.setRoles(permissions);

        // 获取私钥
        // 生成token
        String token = JwtUtils.generateTokenExpireInMinutes(new JiushiUser(userDto), keyPair.getPrivate(), 10);
        System.out.println("token = " + token);

        // 解析token
        Payload<Object> info = JwtUtils.getInfoFromToken(token, keyPair.getPublic(), Object.class);

        System.out.println("info.getExpiration() = " + info.getExpiration());
        System.out.println("info.getUserInfo() = " + info.getUserInfo());
        System.out.println("info.getId() = " + info.getId());


        return "hello world" + name + port;
    }
}
