package com.jianfeng.xiaomianao.handler.dto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.jianfeng.xiaomianao.util.JsonUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Success {

    private String udid = null;

    private String errorcode;

    private Object entity = null;

    public Success(String sn) {
        super();
    }

    public Success(String udid, String errorcode, Object entity) {
        super();
        this.udid = udid;
        this.errorcode = errorcode;
        this.entity = entity;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }
}
