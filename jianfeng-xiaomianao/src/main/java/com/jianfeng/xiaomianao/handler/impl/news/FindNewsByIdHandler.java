package com.jianfeng.xiaomianao.handler.impl.news;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_201")
public class FindNewsByIdHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest,Object...params) throws Exception {
        Integer id = clientRequest.getIntParameter(Constants.NEWSID_PARAMETER_KEY);
        logger.info("handler_201根据id查询新闻内容请求 id:{}", new String[] { Integer.toString(id) });
        return queryService.findNewsById(getUserid(clientRequest),id);
    }
}
