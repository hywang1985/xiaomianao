package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_212")
public class FindMainPostByIdHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer mainpostId = clientRequest.getIntParameter(Constants.MAIN_POST_ID_PARAM_KEY);
        return queryService.findMainPostById(mainpostId);
    }

}
