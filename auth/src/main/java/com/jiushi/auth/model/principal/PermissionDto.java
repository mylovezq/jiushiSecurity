package com.jiushi.auth.model.principal;

import lombok.Data;

/**
 * @author dengmingyang
 * @version 1.0
 **/
@Data
public class PermissionDto {

    private String id;
    private String code;
    private String description;
    private String url;
    private String roleName;
}
