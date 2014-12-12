package com.jianfeng.xiaomianao.push.event;


public class MianaoEvent implements Message {

    protected int eventType = 0;

    public MianaoEvent() {
    }

    public MianaoEvent(Object data) {
        this.data = data;
    }

    protected Object data;

    public Class<? extends Message> getType() {
        return getClass();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

}
