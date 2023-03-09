package com.jiushi.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiushi.order.dao.pojo.CoursewareDO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCoursewareDto {
    private Long id;

    private Long userId;
    private CoursewareDO courseware;
    private Integer cwId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}