package com.jianfeng.xiaomianao.handler.impl.comment;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_802")
public class FindCommentsByNewsIdHandler extends AbstractHandler {


    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        Integer newsId = clientRequest.getIntParameter(Constants.NEWSID_PARAMETER_KEY);
        return queryService.findCommentsByNewsId(getUserid(clientRequest),newsId, getFirstResult(clientRequest), getMaxResult(clientRequest));
    }
}
