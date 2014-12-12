package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_208")
public class FindMainPostsHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        logger.info("handler_208 查询社区主贴");
        Integer communityId = clientRequest.getIntParameter(Constants.COMMUNITY_ID_PARAMETER_KEY);
        return queryService.findMainPosts(communityId, getFirstResult(clientRequest), getMaxResult(clientRequest));
    }
}
