package com.jianfeng.xiaomianao.processor.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.AbstractNewsInfoProcessor;

@Service("processor_203")
public class FindNewsByTagsProcessor extends AbstractNewsInfoProcessor<Map<String, Object>, Map<String, Object>> {

    @Override
    public Map<String, Object> postProcess(ClientRequest clientRequest, Map<String, Object> processedResult, String userId)
            throws Exception {
        List<Map<String, Object>> newsFields = (List<Map<String, Object>>) processedResult.get("records");
        for (Map<String, Object> news : newsFields) {
            List<NewsInfoBean> taggedNews = (List<NewsInfoBean>) news.get("news");
            if (taggedNews != null && !taggedNews.isEmpty()) {
                for (NewsInfoBean newsToHandle : taggedNews) {
                    setFavorited(userId, newsToHandle);
                    setLiked(userId, newsToHandle);
                }
            }
        }
        return processedResult;
    }

}
