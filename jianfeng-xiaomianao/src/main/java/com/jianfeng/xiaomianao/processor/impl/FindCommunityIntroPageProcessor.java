package com.jianfeng.xiaomianao.processor.impl;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.AbstractCommunityProcessor;

@Service("processor_213")
public class FindCommunityIntroPageProcessor extends AbstractCommunityProcessor<Community, Community> {

    @Override
    public Community postProcess(ClientRequest clientRequest, Community processedResult, String userId) throws Exception {
       
        if (processedResult != null) {
            setSubscribed(userId, processedResult);
            setAdvancefans(processedResult);
            setAdmins(processedResult);
        }
        return processedResult;
    }

}
