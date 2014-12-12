package com.jianfeng.xiaomianao.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.processor.AbstractNewsInfoProcessor;

@Service("processor_216")
public class FindLikedResourceProcessor extends AbstractNewsInfoProcessor<List<Object>, List<Object>> {

    @Override
    public List<Object> postProcess(ClientRequest clientRequest, List<Object> processedResult, String userId)
            throws Exception {
        List<Object> toReturn = null;
        if (processedResult != null && !processedResult.isEmpty()) {
            toReturn = new ArrayList<Object>();
            for (Object obj : processedResult) {
                if (obj != null) {
                    if (obj instanceof NewsInfoBean) {
                        toReturn.add(DTOFactory.eInstance.createLikedNewsDTO().convertNews((NewsInfoBean) obj));
                    }
                    if (obj instanceof MainPost) {
                        toReturn.add(DTOFactory.eInstance.createLikedMainPostDTO().converMainPost((MainPost) obj));
                    }
                }
            }
        }
        return toReturn;
    }

}
