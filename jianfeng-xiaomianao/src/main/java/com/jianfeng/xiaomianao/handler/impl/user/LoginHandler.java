package com.jianfeng.xiaomianao.handler.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.UserService;

@Service("handler_101")
public class LoginHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String userName = clientRequest.getParameter(Constants.USER_NAME_PARAMETER_KEY);
        String passWord = clientRequest.getParameter(Constants.PASS_WORD_PARAMETER_KEY);
        String imei = clientRequest.getImei();
        validateBodyParameter(Constants.IMEI_PARAMETER_KEY,imei);
        return userService.login(userName, passWord,imei);
    }
}
