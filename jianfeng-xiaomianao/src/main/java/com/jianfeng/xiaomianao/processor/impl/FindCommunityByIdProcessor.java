package com.jianfeng.xiaomianao.processor.impl;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.community.CommunityListPageDTO;
import com.jianfeng.xiaomianao.processor.AbstractCommunityProcessor;

@Service("processor_206")
public class FindCommunityByIdProcessor extends AbstractCommunityProcessor<CommunityListPageDTO, Community> {

    @Override
    public CommunityListPageDTO postProcess(ClientRequest clientRequest, Community processedResult, String userId) throws Exception {

        if (processedResult != null) {
            setSubscribed(userId, processedResult);
            setLastMainpost(processedResult);
        }
        return CommunityListPageDTO.converCommunity(processedResult);
    }

}
