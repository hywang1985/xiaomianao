package com.jianfeng.xiaomianao.processor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.Tag;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;


public class AbstractTagProcessor<R, T> implements IPostProcessor<R, T> {
    
    @Autowired
    private SubscriptionCaculator caculator;

    @Override
    public R postProcess(ClientRequest clientRequest, T processedResult, String userId) throws Exception {
        return null;
    }
    
    protected void setSubscribed(String userId, Tag tagToHandle) {
        Set<MianaouserinfoBean> subscribers = tagToHandle.getSubscribers();
        boolean subscribed = caculator.isSubscribed(subscribers, userId);
        tagToHandle.setSubscribed(subscribed);
    }
    

}
