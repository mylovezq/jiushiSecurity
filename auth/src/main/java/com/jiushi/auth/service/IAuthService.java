package com.jiushi.auth.service;

import com.jiushi.core.common.model.Result;

public interface IAuthService {

    Result getSessionId(String code);
}
