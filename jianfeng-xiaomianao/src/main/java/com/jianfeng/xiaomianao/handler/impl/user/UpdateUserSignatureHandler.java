package com.jianfeng.xiaomianao.handler.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.UserService;

@Service("handler_108")
public class UpdateUserSignatureHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String signature = clientRequest.getParameter(Constants.SIGNATURE_PARAMETER_KEY);
        return userService.UpdateSignature(getUserid(clientRequest), signature).getUserid();
    }
}
