package com.jiushi.auth.service;

import com.jiushi.auth.dao.pojo.UserDO;

import java.util.Map;

public interface IThirdPartyLogin {

    UserDO registerOrLogin(String sessionId, Map<String, String> detailsParametersMap) throws Exception;
}
