package com.jiushi.auth.service.impl;

import com.jiushi.auth.service.IThirdPartyLogin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ThirdPartyStrategy {

    @Resource
    private Map<String, IThirdPartyLogin>  thirdPartyLoginMap = new HashMap<>();


    public IThirdPartyLogin getThirdPartyLogin(String thirdParty) throws LoginException {
        IThirdPartyLogin iThirdPartyLogin = thirdPartyLoginMap.get(thirdParty);
        if (iThirdPartyLogin == null){
            throw new LoginException("登录失败，没有找到对应登录策略");
        }
        return iThirdPartyLogin;
    }

}
