package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.PostService;

@Service("handler_808")
public class DeleteMainPostHandler extends AbstractHandler {

    @Autowired
    private PostService postService;

    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer mainpostId = clientRequest.getIntParameter(Constants.MAIN_POST_ID_PARAM_KEY);
        return postService.deleteMainPost(getUserid(clientRequest), mainpostId);
    }

}
