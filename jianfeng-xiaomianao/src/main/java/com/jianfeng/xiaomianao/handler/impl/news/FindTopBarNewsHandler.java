package com.jianfeng.xiaomianao.handler.impl.news;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service("handler_205")
public class FindTopBarNewsHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String categoryJSON = clientRequest.getParameter(Constants.CATEGORIES_KEY_PARAMETER);
        List<Integer> categories = JsonUtil.fromJsonArrayToObjects(categoryJSON, Integer.class);
        logger.info("handler_205查询置顶资讯 categories:{}", new String[] { categories.toString() });
        return  queryService.findTopBarNews(categories, getFirstResult(clientRequest),
                getMaxResult(clientRequest));
//        List<TopBarNewsDTO> toReturn = new ArrayList<TopBarNewsDTO>();
//        if (findTopBarNews != null && !findTopBarNews.isEmpty()) {
//            for (NewsInfoBean news : findTopBarNews) {
//                TopBarNewsDTO newsDTO = TopBarNewsDTO.convertNewsInfoBean(news);
//                toReturn.add(newsDTO);
//            }
//        }
//        return toReturn;
    
    }

//    protected boolean needPostHandling() {
//        return false;
//    }

}
