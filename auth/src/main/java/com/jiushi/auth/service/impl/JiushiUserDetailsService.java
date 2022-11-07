package com.jiushi.auth.service.impl;

import com.alibaba.fastjson.JSON;

import com.jiushi.auth.constant.MessageConstant;
import com.jiushi.auth.dao.UserDao;
import com.jiushi.auth.model.UserDto;
import com.jiushi.auth.model.entity.UserDO;
import com.jiushi.auth.model.principal.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class JiushiUserDetailsService implements UserDetailsService {

    @Autowired
    UserDao userDao;

    //根据 账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //将来连接数据库根据账号查询用户信息
        UserDO userDto = userDao.getUserByUsername(username);
        if(userDto == null){
            //如果用户查不到，返回null，由provider来抛出异常
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);

        }
        //根据用户的id查询用户的权限
        List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
        userDto.setRoles(permissions);
        UserPrincipal userDetails = new UserPrincipal(userDto);
        return userDetails;
    }
}
