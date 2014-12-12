package com.jianfeng.xiaomianao.push.handler;

import java.util.HashMap;
import java.util.Map;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.push.PushInfo;
import com.jianfeng.xiaomianao.push.event.MianaoEvent;

public class AddPostEventHandler extends MianaoEventHandler {

    @SuppressWarnings("rawtypes")
    @Override
    protected PushInfo parseEventData(MianaoEvent event, Object... parameters) {
        PushInfo toReturn = null;
        Map data = (Map) event.getData();
        if (data != null && !data.isEmpty()) {
            toReturn = PushInfo.create();
            String userToNotify = (String) data.get(Constants.USERID_PARAMETER_KEY);
            Boolean isTopLevel = (Boolean) data.get("isTopLevel");
            Integer targetId = (Integer) data.get("targetId");
            Map<Object, Object> customContent = new HashMap<Object, Object>();
            customContent.put(Constants.MIANAO_EVENT_TYPE_PARAMETER_KEY, event.getEventType());
            customContent.put("targetId", targetId);
            String title = "您有一条新消息";
            String description = "有人评论您的文章";
            if (!isTopLevel) {
                description = "有人回复了您";
            }
            toReturn.setMianaoUserId(userToNotify);
            toReturn.setTitle(title);
            toReturn.setDescription(description);
            toReturn.setCustomContent(customContent);
        }

        return toReturn;
    }

}
