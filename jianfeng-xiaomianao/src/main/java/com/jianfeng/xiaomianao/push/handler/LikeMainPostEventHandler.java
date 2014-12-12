package com.jianfeng.xiaomianao.push.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.push.PushInfo;
import com.jianfeng.xiaomianao.push.event.MianaoEvent;

public class LikeMainPostEventHandler extends MianaoEventHandler {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected PushInfo parseEventData(MianaoEvent event, Object... parameters) {

        PushInfo toReturn = null;
        Map data = (Map) event.getData();
        if (data != null && !data.isEmpty()) {
            toReturn = PushInfo.create();
            String userToNotify = (String) data.get(Constants.USERID_PARAMETER_KEY);
            toReturn.setMianaoUserId(userToNotify);
            List<Integer> ids = (List<Integer>) data.get(Constants.MAIN_POST_IDS_PARAM_KEY);
            Map<Object, Object> customContent = new HashMap<Object, Object>();
            customContent.put(Constants.MIANAO_EVENT_TYPE_PARAMETER_KEY, event.getEventType());
            customContent.put(Constants.MAIN_POST_IDS_PARAM_KEY, ids);
            toReturn.setCustomContent(customContent);
            String title = "您有一条新消息";
            toReturn.setTitle(title);
            String description = "有人赞了您的文章";
            toReturn.setDescription(description);
        }
        return toReturn;
    }
}
