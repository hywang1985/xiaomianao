package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_209")
public class FindPostsHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        logger.info("handler_209 查询主贴评论");
        Integer mainpostId = clientRequest.getIntParameter(Constants.MAIN_POST_ID_PARAM_KEY);
        return queryService.findPosts(getUserid(clientRequest), mainpostId, getFirstResult(clientRequest),
                getMaxResult(clientRequest));
    }
}
