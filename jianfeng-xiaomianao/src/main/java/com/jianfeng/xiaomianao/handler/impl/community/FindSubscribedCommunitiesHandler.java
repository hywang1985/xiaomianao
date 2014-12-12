package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_207")
public class FindSubscribedCommunitiesHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String targetUserid = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        logger.info("handler_207 查询订阅的社区,用户id:{}", targetUserid);
        return queryService.findSubscribedCommunities(targetUserid, getFirstResult(clientRequest), getMaxResult(clientRequest));
    }
}
