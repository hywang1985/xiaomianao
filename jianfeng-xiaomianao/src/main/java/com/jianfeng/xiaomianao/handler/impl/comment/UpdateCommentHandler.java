package com.jianfeng.xiaomianao.handler.impl.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.CommentService;

@Service("handler_803")
public class UpdateCommentHandler extends AbstractHandler {

    @Autowired
    private CommentService commentService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        Integer commentId = clientRequest.getIntParameter(Constants.COMMENT_ID_PARAMETER_KEY);
        String content = clientRequest.getParameter(Constants.CONTENT_PARAMETER_KEY);
        return commentService.updateComment(getUserid(clientRequest), commentId, content).getId();
    }
}
