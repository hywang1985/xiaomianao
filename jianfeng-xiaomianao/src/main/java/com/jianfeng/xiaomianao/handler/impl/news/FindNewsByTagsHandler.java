package com.jianfeng.xiaomianao.handler.impl.news;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_203")
public class FindNewsByTagsHandler extends AbstractHandler {
    
    @Override
    public Object processInPractice(ClientRequest clientRequest,Object...params) throws Exception {
        String tagsJSON = clientRequest.getParameter(Constants.TAGS_PARAMETER_KEY);
        List<String> tags = JsonUtil.fromJsonArrayToObjects(tagsJSON, String.class);
        Integer firstResult = getFirstResult(clientRequest);
        Integer maxResults = getMaxResult(clientRequest);
        logger.info("handler_203 新闻内容请求 tags:{},firstResult:{},maxResults:{}",
                new String[] { tagsJSON, String.valueOf(firstResult), String.valueOf(maxResults) });
        return queryService.findNewsByTags(getUserid(clientRequest),tags, firstResult, maxResults);
    }

}
