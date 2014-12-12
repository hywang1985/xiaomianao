package com.jianfeng.xiaomianao.push.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;

import com.jianfeng.xiaomianao.domain.PushPreference;
import com.jianfeng.xiaomianao.push.MessageHandler;
import com.jianfeng.xiaomianao.push.MianaoEventPusher;
import com.jianfeng.xiaomianao.push.PushInfo;
import com.jianfeng.xiaomianao.push.event.MianaoEvent;
import com.jianfeng.xiaomianao.service.PushService;

public abstract class MianaoEventHandler implements MessageHandler<MianaoEvent> {

    protected Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    
    protected MianaoEventPusher eventPusher = null;
    
    // protected messagePusher;

    public MianaoEventHandler() {
        eventPusher = new MianaoEventPusher();
    }

    /**
     * Template method pattern. 
     */
    public int handle(MianaoEvent event, Object... parameters) throws Exception {
        int succesCount = 0;
        if (parameters != null && parameters.length > 0) {
            logger.info("Injecting push service...");
            BeanFactory beanFactory = (BeanFactory) parameters[0];
            injectPushService(beanFactory);
         }
        PushInfo pushInfo = parseEventData(event, parameters);
        PushPreference pushPreference = eventPusher.getPushService().getPushPreference(pushInfo.getMianaoUserId(),
                event.getEventType());
        if (pushPreference != null) {
            logger.info("Push event,type:{}"+new String[]{Integer.toString(event.getEventType())});
            succesCount = pushPreference.isSendNotification() ? eventPusher.pushUnicastNotification(pushInfo) : eventPusher
                    .pushUnicastMessage(pushInfo);
        } else {
            logger.info("Push event,type:{}"+new String[]{Integer.toString(event.getEventType())});
            succesCount = eventPusher.pushUnicastNotification(pushInfo);
        }
        return succesCount;
    }
    
    protected abstract PushInfo parseEventData(MianaoEvent event, Object... parameters);


    protected void injectPushService(BeanFactory beanFactory) {
        PushService pushServiceBean = beanFactory.getBean("pushService", PushService.class);
        if (pushServiceBean != null) {
            eventPusher.setPushService(pushServiceBean);
        }
    }

    
    public MianaoEventPusher getEventPusher() {
        return eventPusher;
    }

    public void setEventPusher(MianaoEventPusher eventPusher) {
        this.eventPusher = eventPusher;
    }

}
