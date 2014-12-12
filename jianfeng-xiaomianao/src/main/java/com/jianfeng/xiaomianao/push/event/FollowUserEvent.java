package com.jianfeng.xiaomianao.push.event;



public class FollowUserEvent extends MianaoEvent {

    public FollowUserEvent() {
        super();
    }

    public FollowUserEvent(Object data) {
        super(data);
    }

    @Override
    public int getEventType() {
        return 3;
    }
}
