package com.jiushi.order.dao.pojo;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 */
@Data
@TableName("tb_courseware")
public class CoursewareDO implements Serializable {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;
    @NotNull(message = "姓名不能为空")

    @TableField(value = "name")
    private String name;

    @NotNull(message = "价格不能为空")
    @TableField(value = "price")
    private BigDecimal price;

    @Min(value = 0, message = "购买量不能小于0")
    @TableField(value = "count")
    private Integer count;

    @NotNull(message = "课件地址不能为空")
    @TableField(value = "url")
    private String url;

    @NotNull(message = "封面不能为空")
    @TableField(value = "cover")
    private String cover;

    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(value = "carousel_url")
    private String carouselUrl;

    @TableField(value = "is_carousel")
    private Integer isCarousel;
}
