package com.jianfeng.xiaomianao.processor;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;

@Component
public class SubscriptionCaculator {

    public boolean isSubscribed(Set<MianaouserinfoBean> subscribers, String userId) {
        if (userId == null || StringUtils.isBlank(userId)) {
            return false;
        }
        boolean find = false;
        if (subscribers != null && !subscribers.isEmpty()) {
            Iterator<MianaouserinfoBean> subscriberIt = subscribers.iterator();
            while (subscriberIt.hasNext()) {
                MianaouserinfoBean subscriber = subscriberIt.next();
                if (subscriber.getUserid().equals(userId)) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }
}
