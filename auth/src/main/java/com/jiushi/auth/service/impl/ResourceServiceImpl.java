package com.jiushi.auth.service.impl;

import com.jiushi.auth.constant.RedisConstant;
import com.jiushi.auth.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 资源与角色匹配关系管理业务类
 *
 * @author Honghui [wanghonghui_work@163.com] 2021/3/16
 */
@Service
public class ResourceServiceImpl implements ResourceService {

  @Resource
  private  RedisTemplate<String, Object> redisTemplate;


  @PostConstruct
  public void initData() {
    Map<String, List<String>> resourceRolesMap = new TreeMap<>();
    resourceRolesMap.put("/resource/hello", Arrays.asList("ADMIN"));
    resourceRolesMap.put("/resource/user/currentUser",  Arrays.asList("ADMIN", "USER"));
    redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
  }
}
