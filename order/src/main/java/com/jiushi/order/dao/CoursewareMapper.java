package com.jiushi.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiushi.order.dao.pojo.CoursewareDO;
import com.jiushi.order.model.UserCoursewareDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoursewareMapper extends BaseMapper<CoursewareDO> {
    List<UserCoursewareDto> findMyCourseware(Long userId);
}