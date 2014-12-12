package com.jianfeng.xiaomianao.handler.impl.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.FileService;

@Service("handler_902")
public class QiniuTokenHandler extends AbstractHandler {

    @Autowired
    private FileService fileService;
    
    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String userId = getUserid(clientRequest);
        String saveKey = clientRequest.getParameter(Constants.UPLOAD_KEY_PARAMETER_KEY);
        logger.info("handler_902 请求上传凭证-用户id:{} ,存储路径:{}", new String[] { userId, saveKey});
        return fileService.generateUploadToken(userId, saveKey);
    }
}
