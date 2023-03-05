package com.jiushi.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vo.CommonPage;
import com.jiushi.core.common.model.Result;
import com.jiushi.order.dao.CoursewareMapper;
import com.jiushi.order.dao.pojo.CoursewareDO;
import com.jiushi.order.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Resource
    private CoursewareMapper coursewareMapper;

    @Override
    public Result<CoursewareDO> listCourseWare(Integer start) {
        IPage<CoursewareDO> page = new Page<>(start, 10);
        IPage<CoursewareDO> coursewareIPage = coursewareMapper.selectPage(page, null);
        List<CoursewareDO> collect = coursewareIPage.getRecords().stream().peek(x -> x.setUrl(null)).collect(Collectors.toList());
        coursewareIPage.setRecords(collect);
        return Result.SUCCESS(CommonPage.restPage(coursewareIPage));
    }

    @Override
    public Result getCarousel() {
        LambdaQueryWrapper<CoursewareDO> queryWrapper = Wrappers.<CoursewareDO>lambdaQuery().gt(CoursewareDO::getIsCarousel, 0).orderByAsc(CoursewareDO::getIsCarousel);
        queryWrapper.select(CoursewareDO::getId, CoursewareDO::getCarouselUrl);
        List<CoursewareDO> coursewareDOList = coursewareMapper.selectList(queryWrapper);
        return Result.SUCCESS(coursewareDOList);
    }
}
