package com.jianfeng.xiaomianao.handler.dto;

import java.util.Date;
import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.jianfeng.xiaomianao.util.JsonUtil;
import com.jianfeng.xiaomianao.util.StringUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRequest {

    private String udid;

    private String token;

    private String method;

    private String channel;

    private String version;

    private String imei;

    private String userid;

    private HashMap<String, Object> params;

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public boolean containsKey(String key) {
        return null != params && params.containsKey(key);
    }

    public boolean hasParameter(String key) {
        if (null != params && params.containsKey(key) && null != params.get(key)) {
            return !StringUtil.isEmpty(String.valueOf(params.get(key)));
        }
        return false;
    }

    public String getParameter(String key) {
        if (null != params && null != params.get(key)) {
            return String.valueOf(params.get(key));
        }
        return null;
    }

    public Object getObjectParameter(String key) {
        return params.get(key);
    }

    public Integer getIntParameter(String key) {
        String parameter = getParameter(key);
        return parameter != null ? Integer.parseInt(parameter) : null;
    }
    
    public Boolean getBooleanParameter(String key){
        String parameter = getParameter(key);
        return parameter != null ? Boolean.parseBoolean(parameter) : null;
    }

    public long getLongParameter(String key) {
        String parameter = getParameter(key);
        return parameter != null ? Long.parseLong(parameter) : null;
    }

    public double getDoubleParameter(String key) {
        String parameter = getParameter(key);
        return parameter != null ? Double.parseDouble(parameter) : null;
    }

    public Date getDateParameter(String key) {
        return new Date(getLongParameter(key));
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public static ClientRequest fromJsonToClientRequest(String json) {
        return JsonUtil.fromJsonToObject(json, ClientRequest.class);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }

}
