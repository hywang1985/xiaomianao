package com.jianfeng.xiaomianao.handler.impl.community;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;

@Service("handler_210")
public class FindDigestMainPostsHandler extends AbstractHandler {

    public Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        logger.info("handler_210 查询社区置顶主贴");
        Integer communityId = clientRequest.getIntParameter(Constants.COMMUNITY_ID_PARAMETER_KEY);
        List<MainPost> mainposts = queryService.findDigestMainPosts(communityId, getFirstResult(clientRequest),
                getMaxResult(clientRequest));
        List<MainPostDTO> toReturn = null;
        if (mainposts != null && !mainposts.isEmpty()) {
            toReturn = new ArrayList<MainPostDTO>();
            for (MainPost mp : mainposts) {
                toReturn.add(DTOFactory.eInstance.createDegestMainPostDTO().converMainPost(mp));
            }
        }
        return toReturn;
    }
}
