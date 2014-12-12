package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_402")
public class FindCommunityByClassHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String className = clientRequest.getParameter(Constants.CLASS_NAME_PARAMETER_KEY);
        logger.info("handler_402 根据类别名查询社区:{}", className);
        return queryService.findCommunityByClass(getUserid(clientRequest), className, getFirstResult(clientRequest),
                getMaxResult(clientRequest));
    }
}
