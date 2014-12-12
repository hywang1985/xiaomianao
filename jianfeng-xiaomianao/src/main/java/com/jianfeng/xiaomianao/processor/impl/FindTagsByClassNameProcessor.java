package com.jianfeng.xiaomianao.processor.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.Tag;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.AbstractTagProcessor;

@Service("processor_401")
public class FindTagsByClassNameProcessor extends AbstractTagProcessor<List<Tag>, List<Tag>> {

    @Override
    public List<Tag> postProcess(ClientRequest clientRequest, List<Tag> processedResult, String userId) throws Exception {
        if (processedResult != null && !processedResult.isEmpty()) {
            for (Tag tag : processedResult) {
                setSubscribed(userId, tag);
            }
        }
        return processedResult;
    }

}
