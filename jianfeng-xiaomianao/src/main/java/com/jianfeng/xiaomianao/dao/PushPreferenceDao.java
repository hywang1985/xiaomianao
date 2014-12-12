package com.jianfeng.xiaomianao.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.PushPreference;

@Repository
public class PushPreferenceDao extends AbstractDao<PushPreference, Integer> {

    private static final String FIND_PUSH_PREFERENCE = "select p from PushPreference p where p.userId = (:userId) and p.eventType = (:eventType)";
    
    private static final String FIND_USER_PUSH_PREFERENCES = "select p from PushPreference p where p.userId = (:userId)";
    
    public PushPreference findPushPreference(String userId, Integer eventType) {
        List<PushPreference> resultList = entityManager.createQuery(FIND_PUSH_PREFERENCE, PushPreference.class)
                .setParameter("userId", userId).setParameter("eventType", eventType).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }
    
    public List<PushPreference> findUserPreferences(String userId) {
        return entityManager.createQuery(FIND_USER_PUSH_PREFERENCES, PushPreference.class).setParameter("userId", userId)
                .getResultList();
    }
}
