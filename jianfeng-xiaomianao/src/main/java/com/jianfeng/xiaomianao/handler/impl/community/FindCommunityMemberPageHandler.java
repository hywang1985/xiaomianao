package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_214")
public class FindCommunityMemberPageHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String userId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        return queryService.findUserById(userId);
    }

}
