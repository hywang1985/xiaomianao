package com.jianfeng.xiaomianao.handler.impl.community;

import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

/**
 * @author hywang
 * 
 * This class is designed to retrieve user concerned main posts via the type code. 
 * 
 * Type code : 
 * 1 - replied main posts.
 * 2 - created main posts.
 */
@Service("handler_215")
public class FindUserConcernedMainPostsHandler extends AbstractHandler {

    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        Integer typCode = clientRequest.getIntParameter(Constants.TYPE_CODE_PARAMETER_KEY);
        String userId = clientRequest.getParameter(Constants.USERID_PARAMETER_KEY);
        return queryService.findUserConcernedMainPosts(typCode, userId, getFirstResult(clientRequest),
                getMaxResult(clientRequest));
    }

}
