package com.jiushi.order.service;

import com.jiushi.core.common.model.Result;
import com.jiushi.order.dao.pojo.CoursewareDO;
import com.jiushi.order.model.Order;

public interface IOrderService {
    Result<CoursewareDO> listCourseWare(Integer start);

    Result getCarousel();

    Result myCourseware();

    Result createOrder(Order order);
}
