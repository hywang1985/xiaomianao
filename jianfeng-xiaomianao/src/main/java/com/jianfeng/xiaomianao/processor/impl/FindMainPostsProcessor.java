package com.jianfeng.xiaomianao.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;
import com.jianfeng.xiaomianao.processor.AbstractPostProcessor;

@Service("processor_208")
public class FindMainPostsProcessor extends AbstractPostProcessor<List<MainPostDTO>, List<MainPost>> {

    @Override
    public List<MainPostDTO> postProcess(ClientRequest clientRequest, List<MainPost> processedResult, String userId)
            throws Exception {
        List<MainPostDTO> toReturn = null;
        if (processedResult != null && !processedResult.isEmpty()) {
            toReturn = new ArrayList<MainPostDTO>();
            for (MainPost mp : processedResult) {
                setLiked(userId, mp);
                toReturn.add(DTOFactory.eInstance.createMainPostDTO().converMainPost(mp));
            }
        }
        return toReturn;
    }

}
