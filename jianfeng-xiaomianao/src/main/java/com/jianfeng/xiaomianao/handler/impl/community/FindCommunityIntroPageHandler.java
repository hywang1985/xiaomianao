package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;


@Service("handler_213")
public class FindCommunityIntroPageHandler extends AbstractHandler {

    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer communityId=clientRequest.getIntParameter(Constants.COMMUNITY_ID_PARAMETER_KEY);
        return queryService.findCommunityById(getUserid(clientRequest), communityId);
    }

}
