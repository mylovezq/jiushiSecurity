package com.jiushi.auth.model.entity;

import lombok.*;

import java.util.List;

/**
 * @author Honghui [wanghonghui_work@163.com] 2021/3/16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDO {
  private Long id;
  private String username;
  private String password;
  private Integer status;
  private List<String> roles;
  private String fullname;
  private String mobile;
}
