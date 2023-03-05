package com.jiushi.auth.model.entity;

import lombok.Data;

@Data
public class WXAuth {
    private String encryptedData;
    private String iv;
    private String sessionId;
}