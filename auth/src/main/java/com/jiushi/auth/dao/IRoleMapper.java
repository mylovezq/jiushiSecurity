package com.jiushi.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiushi.auth.dao.pojo.RoleDO;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface IRoleMapper extends BaseMapper<RoleDO> {

}