package com.jianfeng.xiaomianao.handler.impl.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.LikeService;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_702")
public class UnlikeNewsHandler extends AbstractHandler {

    @Autowired
    private LikeService likeService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String newsIdsJson = clientRequest.getParameter(Constants.NEWS_IDS_PARAMETER_KEY);
        List<Integer> newsIds = JsonUtil.fromJsonArrayToObjects(newsIdsJson, Integer.class);
        logger.info("handler_702 取消赞资讯-用户id:{} ,资讯id:{}", new String[] { userId, newsIds.toString() });
        return likeService.unlikeNews(userId, newsIds).getUserid();
    }
}
