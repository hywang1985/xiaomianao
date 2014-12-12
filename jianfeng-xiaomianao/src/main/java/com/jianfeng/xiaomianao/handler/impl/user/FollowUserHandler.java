package com.jianfeng.xiaomianao.handler.impl.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.EventsContainedHandlerResult;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.push.event.FollowUserEvent;
import com.jianfeng.xiaomianao.service.UserService;

@Service("handler_109")
public class FollowUserHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        EventsContainedHandlerResult toReturn = new EventsContainedHandlerResult();
        String userId = getUserid(clientRequest);
        String targetUserId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        logger.info("handler 109 follow user, userid:{},targetUserId:{}", new String[] { userId, targetUserId });
        toReturn.setData(userService.follow(userId, targetUserId).getUserid());
        toReturn.addEvent(generateFollowUserEvent(targetUserId));
        return toReturn;
    }

    protected FollowUserEvent generateFollowUserEvent(String targetUserId) {
        Map<String, Object> eventData = new HashMap<String, Object>();
        eventData.put(Constants.USERID_PARAMETER_KEY, targetUserId);
        FollowUserEvent event = new FollowUserEvent(eventData);
        return event;
    }

}
