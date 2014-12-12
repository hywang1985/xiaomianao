package com.jianfeng.xiaomianao.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.jianfeng.xiaomianao.util.JsonUtil;

public class AuthenticationToken {

    private String token;

    private Date createDate;

    private Long expire;

    private String userid;

    private Integer version;

    /**
     * token失效原因 1-用户被封
     */
    private Integer invalidType;
    
    AuthenticationToken(){
        
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getInvalidType() {
        return invalidType;
    }

    public void setInvalidType(Integer invalidType) {
        this.invalidType = invalidType;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public static AuthenticationToken fromJsonToAuthenticationToken(String json) {
        return JsonUtil.fromJsonToObject(json, AuthenticationToken.class);
    }
}
