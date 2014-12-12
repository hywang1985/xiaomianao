package com.jianfeng.xiaomianao.handler.impl.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_217")
public class SearchFavoriteResourceHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String keyword = clientRequest.getParameter(Constants.KEYWORD_PARAMETER_KEY);
        Integer firstResult = getFirstResult(clientRequest);
        Integer maxResults = getMaxResult(clientRequest);
        String typeString = clientRequest.getParameter(Constants.TYPES_PARAMETER_KEY);
        logger.info("handler_217 搜索收藏的资源 userid:{},keyword:{},类型:{}",
                new String[] { userId, keyword, String.valueOf(firstResult), String.valueOf(maxResults), typeString });
        List<Integer> types = JsonUtil.fromJsonArrayToObjects(typeString, Integer.class);
        return queryService.searchFavoriteResource(userId, types, keyword, firstResult, maxResults);
    }
}
