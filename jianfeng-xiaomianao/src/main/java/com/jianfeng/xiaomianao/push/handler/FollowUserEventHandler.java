package com.jianfeng.xiaomianao.push.handler;

import java.util.HashMap;
import java.util.Map;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.push.PushInfo;
import com.jianfeng.xiaomianao.push.event.MianaoEvent;


public class FollowUserEventHandler extends MianaoEventHandler {

    @SuppressWarnings("rawtypes")
    protected PushInfo parseEventData(MianaoEvent event, Object... parameters) {
        PushInfo toReturn = null;
        Map data = (Map) event.getData();
        if (data != null && !data.isEmpty()) {
            toReturn = PushInfo.create();
            String userId = (String) data.get(Constants.USERID_PARAMETER_KEY);
            String title = "您有一条新的消息";
            String description = "有人关注了您";
            Map<Object, Object> customContent = new HashMap<Object, Object>();
            customContent.put(Constants.MIANAO_EVENT_TYPE_PARAMETER_KEY, event.getEventType());
            toReturn.setMianaoUserId(userId);
            toReturn.setTitle(title);
            toReturn.setDescription(description);
            toReturn.setCustomContent(customContent);
        }
        return toReturn;
    }

}
