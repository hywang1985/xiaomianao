package com.jianfeng.xiaomianao.handler.impl.news;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

/**
 * 执行新闻资讯查询功能
 * 
 * @author kailon
 * 
 */
@Service("handler_202")
public class FindAllNewsInfoHandler extends AbstractHandler {

    @Override
    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String category = clientRequest.getParameter(Constants.CATEGORY_PARAMETER_KEY);// 新闻类别
        Integer firstResult = getFirstResult(clientRequest);
        Integer maxResults = getMaxResult(clientRequest);
        logger.info("handler_202 新闻内容请求 category:{},firstResult:{},maxResults:{}",
                new String[] { category, String.valueOf(firstResult), String.valueOf(maxResults) });
        return queryService.findNews(getUserid(clientRequest),Integer.parseInt(category), firstResult, maxResults);
    }
}
