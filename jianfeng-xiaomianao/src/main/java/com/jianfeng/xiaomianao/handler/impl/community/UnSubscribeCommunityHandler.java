package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.SubscribeService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_506")
public class UnSubscribeCommunityHandler extends AbstractHandler {

    @Autowired
    private SubscribeService subService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String communityJson = clientRequest.getParameter(Constants.COMMUNITY_IDS_PARAMETER_KEY);
        List<Integer> communityIds = JsonUtil.fromJsonArrayToObjects(communityJson, Integer.class);
        logger.info("handler_506 取消订阅社区ids:{}", communityIds.toArray().toString());
        return subService.unsubscribeCommunities(userId, communityIds).getUserid();
    }
}
