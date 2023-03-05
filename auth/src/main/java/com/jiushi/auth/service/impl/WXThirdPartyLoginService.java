package com.jiushi.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jiushi.auth.dao.IUserMapper;
import com.jiushi.auth.dao.IRoleMapper;
import com.jiushi.auth.dao.pojo.RoleDO;
import com.jiushi.auth.dao.pojo.UserDO;
import com.jiushi.auth.model.entity.WxUserInfo;
import com.jiushi.auth.model.principal.PermissionDto;
import com.jiushi.auth.service.IThirdPartyLogin;
import com.jiushi.auth.service.wx.WxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("WX")
@Slf4j
public class WXThirdPartyLoginService implements IThirdPartyLogin {

    @Resource
    private IUserMapper userMapper;
    @Resource
    private IRoleMapper roleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private WxService wxService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDO  registerOrLogin(String sessionId, Map<String,String> detailsParametersMap) throws Exception {
        String wxResStr
                = wxService.wxDecrypt(sessionId, detailsParametersMap.get("encryptedData"), detailsParametersMap.get("iv"));
        WxUserInfo wxUserInfo = JSON.parseObject(wxResStr,WxUserInfo.class);

        UserDO userDO
                = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery().eq(UserDO::getOpenId, wxUserInfo.getOpenId()));

        if (userDO == null){
            UserDO userDONew = BeanUtil.copyProperties(wxUserInfo, UserDO.class);
            userDONew.setThirdPartyLoginType(2);
            long id = IdWorker.getId();
            userDONew.setId(id);
            userDONew.setPassword(passwordEncoder.encode("123456"));
            userDONew.setMobile(wxUserInfo.getPhoneNumber());
            userMapper.insert(userDONew);
            RoleDO roleDO = RoleDO.builder().roleId(1L)
                    .createTime(LocalDateTime.now())
                    .userId(id).build();
            roleMapper.insert(roleDO);
        }

        UserDO user
                = userMapper.selectOne(Wrappers.<UserDO>lambdaQuery()
                .eq(UserDO::getOpenId,wxUserInfo.getOpenId()));
        List<PermissionDto> permissions = userMapper.findPermissionsByUserId(user.getId());
        List<String> collect = permissions.parallelStream().map(PermissionDto::getRoleName).collect(Collectors.toList());
        user.setRoles(collect);
        return user;
    }
}
