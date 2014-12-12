package com.jianfeng.xiaomianao.processor.impl;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.AbstractNewsInfoProcessor;


@Service("processor_201")
public class FindNewsByIdProcessor extends AbstractNewsInfoProcessor<NewsInfoBean, NewsInfoBean> {

    @Override
    public NewsInfoBean postProcess(ClientRequest clientRequest, NewsInfoBean processedResult, String userId) throws Exception {
        setFavorited(userId, processedResult);
        setLiked(userId, processedResult);
        return processedResult;
    }

}
