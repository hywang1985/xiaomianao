package com.jianfeng.xiaomianao.handler.impl.state;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;


@Service("handler_204")
public class FindMianaoStatesHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        return queryService.findMianaoStates(getUserid(clientRequest));
    }
}
