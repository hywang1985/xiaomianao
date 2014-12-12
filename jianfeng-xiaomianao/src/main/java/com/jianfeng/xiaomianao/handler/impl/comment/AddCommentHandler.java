package com.jianfeng.xiaomianao.handler.impl.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.CommentService;

@Service("handler_801")
public class AddCommentHandler extends AbstractHandler {

    @Autowired
    private CommentService commentService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        Integer newsId = clientRequest.getIntParameter(Constants.NEWSID_PARAMETER_KEY);
        Integer parentId = clientRequest.getIntParameter(Constants.PARENT_ID_PARAM_KEY);
        String content = clientRequest.getParameter(Constants.CONTENT_PARAMETER_KEY);
        String userId = getUserid(clientRequest);
        return commentService.addComment(newsId, parentId, content, userId).getId();
    }
}
