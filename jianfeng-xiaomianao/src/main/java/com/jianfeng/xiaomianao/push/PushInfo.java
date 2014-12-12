package com.jianfeng.xiaomianao.push;

import java.util.Map;

public class PushInfo {

    private String mianaoUserId;

    private int messagType;

    private String title;

    private String description;

    private Map<Object, Object> customContent;

    PushInfo(){
        
    }

    public int getMessagType() {
        return messagType;
    }

    public void setMessagType(int messagType) {
        this.messagType = messagType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<Object, Object> getCustomContent() {
        return customContent;
    }

    public void setCustomContent(Map<Object, Object> customContent) {
        this.customContent = customContent;
    }

    public String getMianaoUserId() {
        return mianaoUserId;
    }

    public void setMianaoUserId(String mianaoUserId) {
        this.mianaoUserId = mianaoUserId;
    }
    
    public static PushInfo create(){
        return new PushInfo();
    }

}
