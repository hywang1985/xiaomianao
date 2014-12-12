package com.jianfeng.xiaomianao.handler.impl.classification;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_301")
public class FindAllClassesHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        return queryService.findClasses(getUserid(clientRequest),getFirstResult(clientRequest), getMaxResult(clientRequest));
    }

}
