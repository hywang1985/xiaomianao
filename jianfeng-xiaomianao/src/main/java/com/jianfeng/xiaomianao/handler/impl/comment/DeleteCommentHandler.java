package com.jianfeng.xiaomianao.handler.impl.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.CommentService;

@Service("handler_804")
public class DeleteCommentHandler extends AbstractHandler {

    @Autowired
    private CommentService commentService;

    public Object processInPractice(ClientRequest clientRequest,Object... params) throws Exception {
        Integer commentId = clientRequest.getIntParameter(Constants.COMMENT_ID_PARAMETER_KEY);
        commentService.deleteComment(getUserid(clientRequest), commentId);
        return commentId;
    }
}
