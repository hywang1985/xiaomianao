package com.jianfeng.xiaomianao.handler.impl.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.FavoriteService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_602")
public class UnfavoriteNewsHandler extends AbstractHandler {

    @Autowired
    private FavoriteService favoriteService;

    public Object  processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String newsIdsJson = clientRequest.getParameter(Constants.NEWS_IDS_PARAMETER_KEY);
        List<Integer> newsIds = JsonUtil.fromJsonArrayToObjects(newsIdsJson, Integer.class);
        logger.info("handler_602 取消收藏资讯-用户id:{} ,资讯id:{}", new String[] { userId, newsIds.toString() });
        return favoriteService.unfavoriteNews(userId, newsIds).getUserid();
    }

}
