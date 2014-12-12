package com.jianfeng.xiaomianao.processor.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.Classification;
import com.jianfeng.xiaomianao.domain.Tag;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.processor.IPostProcessor;

@Service("processor_301")
public class FindAllClassesProcessor implements IPostProcessor<List<Classification>, List<Classification>> {

    @Autowired
    private FindTagsByClassNameProcessor tagsProcessor;

    public List<Classification> postProcess(ClientRequest clientRequest, List<Classification> processedResult, String userId)
            throws Exception {

        if (processedResult != null && !processedResult.isEmpty()) {
            for (Classification classToHandle : processedResult) {
                checkClassification(clientRequest, userId, classToHandle);
            }
        }
        return processedResult;
    }

    protected void checkClassification(ClientRequest clientRequest, String userId, Classification classToHandle) throws Exception {
        List<Tag> tags = classToHandle.getTags();
        if (tags != null && !tags.isEmpty()) {
            tagsProcessor.postProcess(clientRequest, tags, userId);
        }
    }
}
