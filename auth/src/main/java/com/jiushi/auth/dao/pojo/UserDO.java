package com.jiushi.auth.dao.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author  [dengmingyang] 2021/3/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("t_user")
public class UserDO implements Serializable {
  @TableId(value = "id", type = IdType.ASSIGN_ID)
  private Long id;

  @NotNull(message = "姓名不能为空")
  @TableField(value = "username")
  private String username;

  @TableField(value = "password")
  private String password;

  @TableField(exist = false)
  private Integer status;

  @TableField(exist = false)
  private List<String> roles;

  @TableField(value = "fullname")
  private String fullname;

  @TableField(value = "mobile")
  private String mobile;

  @TableField(value = "thirdPartyLoginType")
  private Integer thirdPartyLoginType;

  @TableField(value = "openId")
  private String openId;

  @TableField(value = "wxUnionId")
  private String wxUnionId;

  @TableField(value = "background")
  private String background;

  @TableField(value = "portrait")
  private String portrait;

  @TableField(value = "nickname")
  private String nickname;

  @TableField(value = "gender")
  private Integer gender;


}
