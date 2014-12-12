package com.jianfeng.xiaomianao.handler.impl.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.SubscribeService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_502")
public class UnSubscribeTagsHandler extends AbstractHandler {

    @Autowired
    private SubscribeService subService;

    @Override
    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String tagsJson = clientRequest.getParameter(Constants.TAGS_PARAMETER_KEY);
        List<String> tags = JsonUtil.fromJsonArrayToObjects(tagsJson, String.class);
        logger.info("handler_502 取消订阅标签 tags:{}", tags.toArray());
        return subService.unsubscribeTags(userId, tags).getUserid();
    }

}
