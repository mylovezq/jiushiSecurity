package com.jiushi.order.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
	//订单号
    private String orderSn;

    @NotNull(message = "课件id不能为空")
    private Integer cwId;

    private Long userId;
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
	//创建时间
    private LocalDateTime createTime;
	//支付时间
    private LocalDateTime payTime;
    
	//是否支付
    private Boolean isPay;

    /**
     * 0->小程序
     */
    private Integer payType;
    //微信订单号
    private String wxOrder;
}