package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.domain.CommunityTag;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

@Service("handler_211")
public class FindAllCommunityTagsHandler extends AbstractHandler {

    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        logger.info("handler_211 查询社区标签列表");
        List<CommunityTag> tags = queryService.findAllCommunityTags();
        List<String> toReturn = null;
        if (tags != null && !tags.isEmpty()) {
            toReturn = new ArrayList<String>();
            for (CommunityTag tag : tags) {
                toReturn.add(tag.getName());
            }
        }
        return toReturn;
    }

}
