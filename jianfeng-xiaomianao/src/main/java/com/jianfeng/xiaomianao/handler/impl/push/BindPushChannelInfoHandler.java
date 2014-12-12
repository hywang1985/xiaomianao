package com.jianfeng.xiaomianao.handler.impl.push;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.PushService;

@Service("handler_1101")
public class BindPushChannelInfoHandler extends AbstractHandler {
    
    @Autowired
    private PushService pushService;
    
    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        Long pushChannelId = clientRequest.getLongParameter(Constants.PUSH_CHANNEL_ID_PARAMETER_KEY);
        Long pushUserId= clientRequest.getLongParameter(Constants.PUSH_USER_ID_PARAMETER_KEY);
        Integer pushAppId = clientRequest.getIntParameter(Constants.PUSH_APP_ID_PARAMETER_KEY);
        Integer pushDeviceType = clientRequest.getIntParameter(Constants.PUSH_DEVICE_TYPE_PARAMETER_KEY);
        return pushService.bindPushChannelInfo(userId, pushChannelId, pushUserId, pushAppId,pushDeviceType).getUserid();
    }

}
