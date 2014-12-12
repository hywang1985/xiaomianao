package com.jianfeng.xiaomianao.handler.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.UserService;

@Service("handler_106")
public class UpdateUserLocationHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String location = clientRequest.getParameter(Constants.LOCATION_PARAMETER_KEY);
        return userService.updateUserLocation(getUserid(clientRequest), location).getUserid();
    }

}
