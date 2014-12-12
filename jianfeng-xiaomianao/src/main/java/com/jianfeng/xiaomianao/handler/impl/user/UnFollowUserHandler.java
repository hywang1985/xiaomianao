package com.jianfeng.xiaomianao.handler.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.UserService;


@Service("handler_110")
public class UnFollowUserHandler extends AbstractHandler {

    @Autowired
    private UserService userService;
    
    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String targetUserId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        logger.info("handler 109 follow user, userid:{},targetUserId:{}", new String[] { userId, targetUserId });
        return userService.unfollow(userId, targetUserId).getUserid();
    }

}
