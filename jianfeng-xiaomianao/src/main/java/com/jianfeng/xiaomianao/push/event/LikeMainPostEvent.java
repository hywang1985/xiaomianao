package com.jianfeng.xiaomianao.push.event;



public class LikeMainPostEvent extends MianaoEvent {

    public LikeMainPostEvent() {
        super();
    }

    public LikeMainPostEvent(Object data) {
        super(data);
    }

    @Override
    public int getEventType() {
        return 1;
    }

}
