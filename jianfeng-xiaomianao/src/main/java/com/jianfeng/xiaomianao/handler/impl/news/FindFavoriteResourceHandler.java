package com.jianfeng.xiaomianao.handler.impl.news;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_603")
public class FindFavoriteResourceHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer firstResult = getFirstResult(clientRequest);
        Integer maxResults = getMaxResult(clientRequest);
        String userId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        String typeString = clientRequest.getParameter(Constants.TYPES_PARAMETER_KEY);
        List<Integer> types = JsonUtil.fromJsonArrayToObjects(typeString, Integer.class);
        logger.info("handler_603 获取收藏的资源-用户id:{},类型:{}", new String[] { userId, typeString });
        return queryService.findFavoriteResource(types, userId, firstResult, maxResults);
    }

}
