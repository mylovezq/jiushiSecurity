package com.jiushi.auth.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author  [dengmingyang] 2021/3/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("t_user_role")
public class RoleDO implements Serializable {
  @TableField(value = "user_id")
  private Long userId;

  @TableField(value = "role_id")
  private Long roleId;

  @TableField(value = "create_time")
  private LocalDateTime createTime;



}
