package com.jianfeng.xiaomianao.processor.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.MianaoState;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.AbstractMianaoStateProcessor;

@Service("processor_204")
public class FindMianaoStatesProcessor extends AbstractMianaoStateProcessor<List<MianaoState>, List<MianaoState>> {

    @Override
    public List<MianaoState> postProcess(ClientRequest clientRequest, List<MianaoState> processedResult, String userId)
            throws Exception {
        if (processedResult != null && !processedResult.isEmpty()) {
            for (MianaoState stateToHandle : processedResult) {
                setOwned(userId, stateToHandle);
            }
        }
        return processedResult;
    }

}
