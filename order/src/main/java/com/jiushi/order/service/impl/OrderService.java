package com.jiushi.order.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiushi.core.common.context.AuthUserContext;
import com.jiushi.order.dao.OrderMapper;
import com.jiushi.order.dao.UserCoursewareMapper;
import com.jiushi.order.dao.pojo.UserCourseware;
import com.jiushi.order.model.Order;
import com.jiushi.order.model.UserCoursewareDto;
import org.springframework.transaction.annotation.Transactional;
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
    @Resource
    private UserCoursewareMapper userCoursewareMapper;
    @Resource
    private OrderMapper orderMapper;

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

    @Override
    public Result myCourseware() {
        Long userId = AuthUserContext.getUserId();
        List<UserCoursewareDto> myCourseware = coursewareMapper.findMyCourseware(userId);
        return Result.SUCCESS(myCourseware);
    }

    @Override
    @Transactional
    public Result createOrder(Order order) {
        Long userId = AuthUserContext.getUserId();
        UserCourseware userCourseware = userCoursewareMapper
                .selectOne(Wrappers.<UserCourseware>lambdaQuery().eq(UserCourseware::getUserId, userId)
                        .eq(UserCourseware::getCwId, order.getCwId()));
        if (userCourseware == null) {
            order.setIsPay(null);
            order.setCreateTime(null);
            order.setPayTime(null);
            order.setId(null);
            CoursewareDO courseware = coursewareMapper.selectById(order.getCwId());
            order.setOrderSn(UUID.randomUUID().toString());
            order.setPrice(courseware.getPrice());
            order.setUserId(userId);
            orderMapper.insert(order);
//            JSONObject jsonObject = WxPay.minAppPay(order.getOrderSn(), "" + order.getPrice(), mchid, "购买课件ID为:" + order.getCwId(), "码神课件", null, "http://a4tuaki.nat.ipyingshe.com/cwApi/order/callback", null, "0", null, key);
            return Result.SUCCESS(null);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "已经购买请勿重复购买");
            return Result.SUCCESS(jsonObject);
        }
    }
}
