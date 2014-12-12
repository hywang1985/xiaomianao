package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.LikeService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_704")
public class UnLikePostHandler extends AbstractHandler {

    @Autowired
    private LikeService likeService;

    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer type = clientRequest.getIntParameter(Constants.TYPE_CODE_PARAMETER_KEY);
        String idsString = clientRequest.getParameter(Constants.IDS_PARAMETER_KEY);
        List<Integer> ids = JsonUtil.fromJsonArrayToObjects(idsString, Integer.class);
        if (type == null || type <= 0) {
            throw new IllegalArgumentException("The parameter type is invalid.");
        }
        String toReturn = "";
        switch (type) {
        case Constants.POST_TYPE:
            toReturn = likeService.unlikePost(getUserid(clientRequest), ids).getUserid();
            break;
        case Constants.MAIN_POST_TYPE:
            toReturn = likeService.unlikeMainPost(getUserid(clientRequest), ids).getUserid();
            break;
        }
        return toReturn;
    }
}
