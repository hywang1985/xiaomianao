package com.jianfeng.xiaomianao.handler.impl.tag;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_401")
public class FindTagsByClassNameHandler extends AbstractHandler {

    @Override
    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String className = clientRequest.getParameter(Constants.CLASS_NAME_PARAMETER_KEY);
        Integer firstResult = getFirstResult(clientRequest);
        Integer maxResult = getMaxResult(clientRequest);
        logger.info("handler_401 请求标签 className:{},firstResult:{},maxResults:{}",
                new String[] { className, String.valueOf(firstResult), String.valueOf(maxResult) });
        return queryService.findTagsByClass(getUserid(clientRequest),className, firstResult, maxResult);
    }

}
