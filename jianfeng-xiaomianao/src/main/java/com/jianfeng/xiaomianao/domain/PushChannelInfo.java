package com.jianfeng.xiaomianao.domain;

import com.jianfeng.xiaomianao.util.JsonUtil;

public class PushChannelInfo {

    private Long pushChannelId;

    private Long pushUserId;

    private Integer pushAppId;

    private Integer pushDeviceType;

    PushChannelInfo() {

    }

    public Long getPushChannelId() {
        return pushChannelId;
    }

    public void setPushChannelId(Long pushChannelId) {
        this.pushChannelId = pushChannelId;
    }

    public Long getPushUserId() {
        return pushUserId;
    }

    public void setPushUserId(Long pushUserId) {
        this.pushUserId = pushUserId;
    }

    public Integer getPushAppId() {
        return pushAppId;
    }

    public void setPushAppId(Integer pushAppId) {
        this.pushAppId = pushAppId;
    }

    public Integer getPushDeviceType() {
        return pushDeviceType;
    }

    public void setPushDeviceType(Integer pushDeviceType) {
        this.pushDeviceType = pushDeviceType;
    }

    public static PushChannelInfo fromJsonToPushChannelInfo(String json) {
        return JsonUtil.fromJsonToObject(json, PushChannelInfo.class);
    }

}
