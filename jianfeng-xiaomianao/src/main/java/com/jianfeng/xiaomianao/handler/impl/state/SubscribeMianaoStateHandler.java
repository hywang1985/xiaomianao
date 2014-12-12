package com.jianfeng.xiaomianao.handler.impl.state;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.SubscribeService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_503")
public class SubscribeMianaoStateHandler extends AbstractHandler {

    @Autowired
    private SubscribeService subService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String statesJson = clientRequest.getParameter(Constants.SUB_STATES_PARAMETER_KEY);
        List<String> statesToSub = JsonUtil.fromJsonArrayToObjects(statesJson, String.class);
        return subService.subscribeMianaoStates(getUserid(clientRequest), statesToSub).getUserid();
    }
    
}