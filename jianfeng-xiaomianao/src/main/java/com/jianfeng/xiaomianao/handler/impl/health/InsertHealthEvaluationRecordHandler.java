package com.jianfeng.xiaomianao.handler.impl.health;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.handler.AbstractHandler;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.service.HealthEvaluationService;

@Service("handler_1001")
public class InsertHealthEvaluationRecordHandler extends AbstractHandler {

    @Autowired
    private HealthEvaluationService healthService;

    @SuppressWarnings("rawtypes")
    protected Object processInPractice(ClientRequest clientRequest, Object... params) throws Exception {
        List itemRecordsList = (List) clientRequest.getObjectParameter(Constants.HEALTH_EVALUATION_ITEM_RECORDS);
        return healthService.insertHealthEvaluationItemRecord(getUserid(clientRequest), itemRecordsList);
    }

}
