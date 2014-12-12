package com.jianfeng.xiaomianao.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.service.UserService;

/**
 * 执行开机接口
 * 
 * @author kailon
 * 
 * @update hywang
 */
@Service("handler_103")
public class BootinitializationHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    @Override
    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception{
            String token = clientRequest.getToken();
            MianaouserinfoBean userBean = userService.findUserByToken(token);
            return DTOFactory.eInstance.createUserDTO().convert(userBean);
    }
}
