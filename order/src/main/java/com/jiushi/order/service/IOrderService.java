package com.jiushi.order.service;

import com.jiushi.core.common.model.Result;
import com.jiushi.order.dao.pojo.CoursewareDO;

public interface IOrderService {
    Result<CoursewareDO> listCourseWare(Integer start);

    Result getCarousel();
}
