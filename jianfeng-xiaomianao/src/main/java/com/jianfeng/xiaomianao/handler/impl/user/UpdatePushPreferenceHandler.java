package com.jianfeng.xiaomianao.handler.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.UserService;

@Service("handler_111")
public class UpdatePushPreferenceHandler extends AbstractHandler {

    @Autowired
    private UserService UserService;
    
    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer eventType = clientRequest.getIntParameter(Constants.MIANAO_EVENT_TYPE_PARAMETER_KEY);
        Boolean isSendNotification = clientRequest.getBooleanParameter(Constants.IS_PUSH_NOTIFICATION_PARAMETER_KEY);
        String userid = getUserid(clientRequest);
        logger.info("handler 111 update push preference, userid:{},eventType:{},sendNotice:{}", new String[] { userid, eventType.toString(),isSendNotification.toString() });
        return UserService.updatePushPreference(userid, eventType, isSendNotification).getUserid();
    }

}
