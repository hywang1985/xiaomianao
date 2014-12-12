package com.jianfeng.xiaomianao.processor.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.AbstractNewsInfoProcessor;

@Service("processor_202")
public class FindAllNewsProcessor extends AbstractNewsInfoProcessor<List<NewsInfoBean>, List<NewsInfoBean>> {

    public List<NewsInfoBean> postProcess(ClientRequest clientRequest, List<NewsInfoBean> processedResult, String userId)
            throws Exception {
        if (processedResult != null && !processedResult.isEmpty()) {

            for (NewsInfoBean news : processedResult) {
                setFavorited(userId, news);
                setLiked(userId, news);
            }
        }
        return processedResult;
    }

}
