package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.EventsContainedHandlerResult;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.push.event.LikeMainPostEvent;
import com.jianfeng.xiaomianao.service.LikeService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_703")
public class LikePostHandler extends AbstractHandler {

    @Autowired
    private LikeService likeService;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        EventsContainedHandlerResult toReturn = new EventsContainedHandlerResult();
        Integer type = clientRequest.getIntParameter(Constants.TYPE_CODE_PARAMETER_KEY);
        String idsString = clientRequest.getParameter(Constants.IDS_PARAMETER_KEY);
        List<Integer> ids = JsonUtil.fromJsonArrayToObjects(idsString, Integer.class);
        if (type == null || type <= 0) {
            throw new IllegalArgumentException("The parameter type is invalid.");
        }
        String successUserId = "";
        String userId = getUserid(clientRequest);
        switch (type) {
        case Constants.POST_TYPE:
            successUserId = likeService.likePost(userId, ids).getUserid();
            toReturn.setData(successUserId);
            break;
        case Constants.MAIN_POST_TYPE:
            successUserId = likeService.likeMainPost(userId, ids).getUserid();
            toReturn.setData(successUserId);
            List<LikeMainPostEvent> events = generateLikeMainPostEvents(userId,ids);
            toReturn.addEvents((List)events);
            break;
        }
        return toReturn;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<LikeMainPostEvent> generateLikeMainPostEvents(String userId, List<Integer> ids) {
        List<LikeMainPostEvent> toReturn = null;
        for (Integer mainpostId : ids) {
            MainPost likedMainPost = queryService.findMainPostById(mainpostId);
            if (likedMainPost != null) {
                if (toReturn == null) {
                    toReturn = new ArrayList<LikeMainPostEvent>();
                }
                Map eventData = new HashMap();
                String mainPostUserId = likedMainPost.getUser().getUserid();
                if (userId != null && !userId.equals(mainPostUserId)) { // Should trigger event only on liking other
                                                                        // people's main post.
                    eventData.put(Constants.USERID_PARAMETER_KEY, mainPostUserId);
                }
                eventData.put(Constants.MAIN_POST_IDS_PARAM_KEY, ids);
                LikeMainPostEvent event = new LikeMainPostEvent(eventData);
                toReturn.add(event);
            }
        }
        return toReturn;
    }
}
