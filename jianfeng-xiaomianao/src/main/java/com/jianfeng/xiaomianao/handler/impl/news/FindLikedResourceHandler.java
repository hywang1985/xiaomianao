package com.jianfeng.xiaomianao.handler.impl.news;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_216")
public class FindLikedResourceHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String targetUserId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        String typeString = clientRequest.getParameter(Constants.TYPES_PARAMETER_KEY);
        List<Integer> types = JsonUtil.fromJsonArrayToObjects(typeString, Integer.class);
        logger.info("handler_603 获取赞过的资源-用户id:{},类型:{}", new String[] { targetUserId, typeString });
        return queryService.findLikedResource(types, targetUserId, getFirstResult(clientRequest), getMaxResult(clientRequest));
    }

}
