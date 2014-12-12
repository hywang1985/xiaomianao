package com.jianfeng.xiaomianao.handler;

import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

public interface Handler {

    public EventsContainedHandlerResult process(ClientRequest clientRequest,Object... params) throws Exception;

    public boolean checkToken();
    
    public void setMethod(String method);
    
}
