package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.FavoriteService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_605")
public class FavoriteMainPostHandler extends AbstractHandler {

    @Autowired
    private FavoriteService favService;

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String idsJson = clientRequest.getParameter(Constants.MAIN_POST_IDS_PARAM_KEY);
        List<Integer> mainpostIds = JsonUtil.fromJsonArrayToObjects(idsJson, Integer.class);
        return favService.favoriteMainPost(getUserid(clientRequest), mainpostIds).getUserid();
    }

}
