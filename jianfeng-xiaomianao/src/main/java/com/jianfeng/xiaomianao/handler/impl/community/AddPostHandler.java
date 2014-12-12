package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.EventsContainedHandlerResult;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.push.event.AddPostEvent;
import com.jianfeng.xiaomianao.service.PostService;

@Service("handler_805")
public class AddPostHandler extends AbstractHandler {
    
    @Autowired
    private PostService postService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        EventsContainedHandlerResult toReturn = new EventsContainedHandlerResult();
        Integer mainpostId = clientRequest.getIntParameter(Constants.MAIN_POST_ID_PARAM_KEY);
        Integer parentId = clientRequest.getIntParameter(Constants.PARENT_ID_PARAM_KEY);
        String content = clientRequest.getParameter(Constants.CONTENT_PARAMETER_KEY);
        List imageList = (List)clientRequest.getObjectParameter(Constants.IMAGES_PARAMEER_KEY);
        logger.info("handler_804 添加主贴评论: mainpostId:{},parentId:{},",new String[]{mainpostId.toString(),parentId!=null?parentId.toString():null});
        String userid = getUserid(clientRequest);
        Post addedPost = postService.addPost(userid,mainpostId, parentId, content, imageList);
        AddPostEvent event = generateEvent(mainpostId, parentId, userid);
        toReturn.setData(addedPost.getId());
        toReturn.addEvent(event);
        return toReturn;
    }

    private AddPostEvent generateEvent(Integer mainpostId, Integer parentId, String userid) {
        MainPost mainPost = queryService.findMainPostById(mainpostId);
        if (mainPost != null && (parentId == null || parentId < 0)) {
            return generateTopLevelPostEvent(userid, mainPost);
        }else if (parentId != null && parentId > 0) {
            return generateSecondLevelPostEvent(parentId, userid);
        }
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private AddPostEvent generateSecondLevelPostEvent(Integer parentId, String userid) {
        AddPostEvent event = null;
        Post parentPost = queryService.findPostById(parentId);
        if (parentPost != null) {
            String parentPostUserId = parentPost.getUser().getUserid();
            if (!userid.equals(parentPostUserId)) { // If reply other's post,send notification
                Map eventData = new HashMap();
                eventData.put(Constants.USERID_PARAMETER_KEY, parentPostUserId);
                eventData.put("isTopLevel", false);
                eventData.put("targetId", parentPost.getId());
                event = new AddPostEvent(eventData);
            }
        }
        return event;
    }
   
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private AddPostEvent generateTopLevelPostEvent(String userid, MainPost mainPost) {
        AddPostEvent event = null;
        String mainPostUserId = mainPost.getUser().getUserid();
        if (!mainPostUserId.equals(userid)) { // Post on other's main post,send notification.
            Map eventData = new HashMap();
            eventData.put(Constants.USERID_PARAMETER_KEY, mainPostUserId);
            eventData.put("isTopLevel", true);
            eventData.put("targetId", mainPost.getId());
            event = new AddPostEvent(eventData);
        }
        return event;
    }
}
