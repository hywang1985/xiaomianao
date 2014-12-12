package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_218")
public class FindUserListHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer typeCode = clientRequest.getIntParameter(Constants.TYPE_CODE_PARAMETER_KEY);
        String targetUserId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        logger.info("handler_218 查询用户列表,用户id:{},类型:{}", new String[] { targetUserId, typeCode.toString() });
        return queryService.findUserList(typeCode, targetUserId, getFirstResult(clientRequest), getMaxResult(clientRequest));
    }

}
