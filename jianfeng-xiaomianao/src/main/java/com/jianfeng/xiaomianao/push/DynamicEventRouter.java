package com.jianfeng.xiaomianao.push;

import com.jianfeng.xiaomianao.push.event.Message;


/**
 * @author hywang
 * 
 * A tiny event dispatch framework. 
 */
public interface DynamicEventRouter<E extends Message> {

    public void registerEventHandler(Class<? extends E> event, MessageHandler<? extends E> eventHandler);

    public abstract void fireEvent(E event) throws Exception;
}
