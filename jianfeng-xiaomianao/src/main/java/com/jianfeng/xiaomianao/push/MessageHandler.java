package com.jianfeng.xiaomianao.push;

import com.jianfeng.xiaomianao.push.event.Message;


public interface MessageHandler<E extends Message> {

    public int handle(E event, Object... parameters) throws Exception;
}
