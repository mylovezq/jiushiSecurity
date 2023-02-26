package com.jiushi.auth.service.impl;

import com.jiushi.auth.model.constant.RedisConstant;
import com.jiushi.auth.model.principal.PermissionDto;
import com.jiushi.auth.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源与角色匹配关系管理业务类
 *
 * @author  [dengmingyang] 2021/3/16
 */
@Service
public class ResourceServiceImpl implements ResourceService {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Resource
  private  RedisTemplate<String, Object> redisTemplate;



  @PostConstruct
  public void initData() {
    redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, findPermissionsByUserId());
  }

  //用户角色对应的权限
  public Map<String, List<String>> findPermissionsByUserId(){
    String sql = "SELECT tp.url,tr.role_name  FROM t_permission  tp JOIN  t_role_permission trp ON tp.id = trp.permission_id JOIN t_role tr on trp.role_id = tr.id";
    List<PermissionDto> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PermissionDto.class));
    Map<String, List<String>> collect = list.parallelStream().collect(Collectors.groupingBy(PermissionDto::getUrl, Collectors.mapping(PermissionDto::getRoleName,Collectors.toList())));
    return collect;
  }
}
