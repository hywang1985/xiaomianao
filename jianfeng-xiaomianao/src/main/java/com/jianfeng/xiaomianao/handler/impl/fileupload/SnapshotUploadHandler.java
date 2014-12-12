package com.jianfeng.xiaomianao.handler.impl.fileupload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.FileService;

@Service("handler_901")
public class SnapshotUploadHandler extends AbstractHandler {

    @Autowired
    private FileService uploadService;

    @Override
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        String key = clientRequest.getParameter(Constants.UPLOAD_KEY_PARAMETER_KEY);
        return uploadService.uploadSnapshot(getUserid(clientRequest), key).getUserid();
    }
}
