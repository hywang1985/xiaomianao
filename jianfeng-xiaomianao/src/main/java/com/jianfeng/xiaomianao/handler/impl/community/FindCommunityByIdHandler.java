package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;


@Service("handler_206")
public class FindCommunityByIdHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer communityId = clientRequest.getIntParameter(Constants.COMMUNITY_ID_PARAMETER_KEY);
        logger.info("handler_206 根据id查询社区:{}", communityId);
        return queryService.findCommunityById(getUserid(clientRequest),communityId);
    }
}
