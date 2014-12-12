package com.jianfeng.xiaomianao.processor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.PostDTO;
import com.jianfeng.xiaomianao.processor.AbstractPostProcessor;

@Service("processor_209")
public class FindPostsProcessor extends AbstractPostProcessor<List<PostDTO>, List<Post>> {

    @Override
    public List<PostDTO> postProcess(ClientRequest clientRequest, List<Post> processedResult, String userId) throws Exception {

        List<PostDTO> toReturn = null;
        if (processedResult != null && !processedResult.isEmpty()) {
            toReturn = new ArrayList<PostDTO>();
            for (Post post : processedResult) {
                setOwned(userId, post);
                setParentPost(userId, post);
                setMainPostOwner(post);
                setLiked(userId, post);
                toReturn.add(DTOFactory.eInstance.createPostDTO().convertPost(post));
            }
        }
        return toReturn;
    }
}
