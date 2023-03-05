package com.jiushi.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiushi.auth.dao.pojo.UserDO;
import com.jiushi.auth.model.principal.PermissionDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMapper extends BaseMapper<UserDO> {

    List<PermissionDto> findPermissionsByUserId(Long openId);
}