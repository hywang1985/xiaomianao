package com.jianfeng.xiaomianao.push.event;



public class AddPostEvent extends MianaoEvent {

    public AddPostEvent() {
        super();
    }

    public AddPostEvent(Object data) {
        super(data);
    }

    @Override
    public int getEventType() {
        return 2;
    }
}
