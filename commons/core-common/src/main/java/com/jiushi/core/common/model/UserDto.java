package com.jiushi.core.common.model;

import lombok.Data;

/**
 * @author dengmingyang
 * @version 1.0
 **/
@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
