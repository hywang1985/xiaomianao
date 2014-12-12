package com.jianfeng.xiaomianao.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.community.CommunityBriefDTO;
import com.jianfeng.xiaomianao.processor.AbstractCommunityProcessor;

@Service("processor_402")
public class FindCommunityByClassProcessor extends AbstractCommunityProcessor<List<CommunityBriefDTO>, List<Community>> {

    @Override
    public List<CommunityBriefDTO> postProcess(ClientRequest clientRequest, List<Community> processedResult, String userId)
            throws Exception {
        List<CommunityBriefDTO> toReturn = null;
        if (processedResult != null && !processedResult.isEmpty()) {
            toReturn = new ArrayList<CommunityBriefDTO>();
            for (Community community : processedResult) {
                setSubscribed(userId, community);
                setAdvancefans(community);
                setLastMainpost(community);
                toReturn.add(CommunityBriefDTO.converCommunity(community));
            }
        }
        return toReturn;
    }
}
