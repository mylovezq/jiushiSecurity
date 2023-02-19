package com.jiushi.auth.controller;

import java.util.List;

public class Root
{
    private List<String> aud;

    private String user_name;

    private List<String> scope;

    private String mobile;

    private int exp;

    private int userId;

    private List<String> authorities;

    private String jti;

    private String client_id;

    public void setAud(List<String> aud){
        this.aud = aud;
    }
    public List<String> getAud(){
        return this.aud;
    }
    public void setUser_name(String user_name){
        this.user_name = user_name;
    }
    public String getUser_name(){
        return this.user_name;
    }
    public void setScope(List<String> scope){
        this.scope = scope;
    }
    public List<String> getScope(){
        return this.scope;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }
    public void setExp(int exp){
        this.exp = exp;
    }
    public int getExp(){
        return this.exp;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setAuthorities(List<String> authorities){
        this.authorities = authorities;
    }
    public List<String> getAuthorities(){
        return this.authorities;
    }
    public void setJti(String jti){
        this.jti = jti;
    }
    public String getJti(){
        return this.jti;
    }
    public void setClient_id(String client_id){
        this.client_id = client_id;
    }
    public String getClient_id(){
        return this.client_id;
    }
}
