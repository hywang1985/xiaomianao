package com.jianfeng.xiaomianao.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.dao.HealthEvaluationItemRecordDao;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.HealthEvaluationItemRecord;

@Service
public class HealthEvaluationService extends AbstractUserNeededService {

    @Autowired
    private HealthEvaluationItemRecordDao healItemRecordDao;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public boolean insertHealthEvaluationItemRecord(String userId, List records) throws Exception {
        boolean result = true;
        try {
            findUser(userId);
            JSONArray itemRecordArray = JSONArray.fromObject(records);
            JSONObject timestamp = (JSONObject) itemRecordArray.get(0);
            Long timeInMill = (Long) timestamp.get("timestamp");
            itemRecordArray.remove(0);
            Iterator<JSONObject> it = itemRecordArray.iterator();
            while (it.hasNext()) {
                JSONObject recordObj = it.next();
                HealthEvaluationItemRecord record = DomainEntityFactory.eInstance.createHealthEvaluationItemRecord();
                record.setItemId(recordObj.getInt("id"));
                record.setTime(new Date(timeInMill));
                record.setValue(recordObj.getString("value"));
                healItemRecordDao.create(record);
            }
        } catch (Exception e) {
            result = false;
            throw e;
        }
        return result;
    }
}
