package com.jianfeng.xiaomianao.push.event;

public interface Message {

    public Class<? extends Message> getType();
}
