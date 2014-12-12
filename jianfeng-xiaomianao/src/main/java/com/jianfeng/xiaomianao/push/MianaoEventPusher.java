package com.jianfeng.xiaomianao.push;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.PushChannelInfo;
import com.jianfeng.xiaomianao.service.PushService;

/**
 * This class is not managed by Spring, need to inject the pushService manually. 
 */
public class MianaoEventPusher implements NotificationPusher, MessagePusher {

//    private static final int OPEN_URL = 1;
    
    private static final int OPEN_APP = 2;
    
    private PushService pushService;

    @Override
    public int pushUnicastNotification(PushInfo pushInfo) throws ChannelClientException, ChannelServerException {

        int successAmount = 0;
        if (pushService != null) {
            PushChannelInfo channelInfo = pushService.getPushChannelInfo(pushInfo.getMianaoUserId());
            if (channelInfo != null) {
                int deviceType = channelInfo.getPushDeviceType();
                long channelId = channelInfo.getPushChannelId();
                long channelUserId = channelInfo.getPushUserId();
                BaiduChannelClient channelClient = PushService.createChannelClient();
                PushUnicastMessageRequest request = new PushUnicastMessageRequestBuilder()
                    .setChannelId(channelId)
                    .setOpenType(OPEN_APP)
                    .setDeviceType(deviceType)
                    .setTitle(pushInfo.getTitle())
                    .setDescription(pushInfo.getDescription())
                    .setMessagType(1)
                    .setUserId(channelUserId)
                    .setCustomContent(pushInfo.getCustomContent())
                    .buildNotificationRequest();
                PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
                successAmount = response.getSuccessAmount();
            }
        }
        return successAmount;
    }
    
    public int pushUnicastMessage(PushInfo pushInfo) throws ChannelClientException, ChannelServerException {
       
        int successAmount = 0;
        if (pushService != null) {
            PushChannelInfo channelInfo = pushService.getPushChannelInfo(pushInfo.getMianaoUserId());
            if (channelInfo != null) {
                int deviceType = channelInfo.getPushDeviceType();
                long channelId = channelInfo.getPushChannelId();
                long channelUserId = channelInfo.getPushUserId();
                BaiduChannelClient channelClient = PushService.createChannelClient();
                PushUnicastMessageRequest request = new PushUnicastMessageRequestBuilder()
                    .setChannelId(channelId)
                    .setDeviceType(deviceType)
                    .setUserId(channelUserId)
                    .setCustomContent(pushInfo.getCustomContent())
                    .buildMessageRequest();
                PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
                successAmount = response.getSuccessAmount();
            }
        }
        return successAmount;
    }

    public PushService getPushService() {
        return pushService;
    }

    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

    private static class PushUnicastMessageRequestBuilder {

        private int deviceType;

        private long channelId;

        private long userId;

        private int messagType;

        private String title;

        private String description;
        
        private int openType;

        private Map<Object, Object> customContent;

        public int getDeviceType() {
            return deviceType;
        }

        public long getChannelId() {
            return channelId;
        }

        public long getUserId() {
            return userId;
        }

        public int getMessagType() {
            return messagType;
        }

        public PushUnicastMessageRequestBuilder setDeviceType(int deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public PushUnicastMessageRequestBuilder setChannelId(long channelId) {
            this.channelId = channelId;
            return this;
        }

        public PushUnicastMessageRequestBuilder setUserId(long userId) {
            this.userId = userId;
            return this;
        }

        public PushUnicastMessageRequestBuilder setMessagType(int messagType) {
            this.messagType = messagType;
            return this;
        }

        public Map<Object, Object> getCustomContent() {
            return customContent;
        }

        public PushUnicastMessageRequestBuilder setCustomContent(Map<Object, Object> customContent) {
            this.customContent = customContent;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public PushUnicastMessageRequestBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public PushUnicastMessageRequestBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        
        public int getOpenType() {
            return openType;
        }

        public PushUnicastMessageRequestBuilder setOpenType(int openType) {
            this.openType = openType;
            return this;
        }

        PushUnicastMessageRequest buildNotificationRequest() {
            PushUnicastMessageRequest pushUnicastMessageRequest = new PushUnicastMessageRequest();
            pushUnicastMessageRequest.setMessageType(1);
            pushUnicastMessageRequest.setDeviceType(deviceType);
            pushUnicastMessageRequest.setUserId(Long.toString(userId));
            JSONObject customContent = null;
            JSONObject messageObject = new JSONObject();
            messageObject.put(Constants.TITLE_PARAMETER_KEY, getTitle());
            messageObject.put(Constants.DESCRIPTION_PARAMETER_KEY, getDescription());
            messageObject.put(Constants.OPEN_TYPE_PARAMETER_KEY, getOpenType());
            switch (deviceType) {
            case Constants.PUSH_ANDROID_DEVICE_TYPE: // android
                if (getCustomContent() != null && !getCustomContent().isEmpty()) {
                    customContent = new JSONObject();
                    customContent = JSONObject.fromObject(getCustomContent());
                }
                messageObject.put(Constants.CUSTOM_CONTENT_PARAMETER_KEY, customContent);

                pushUnicastMessageRequest.setChannelId(channelId);
                pushUnicastMessageRequest.setMessage(messageObject.toString());
                
                break;
            case Constants.PUSH_IOS_DEVICE_TYPE: // ios
                if (getCustomContent() != null && !getCustomContent().isEmpty()) {
                    customContent = new JSONObject();
                    Iterator keyIt = getCustomContent().keySet().iterator();
                    while (keyIt.hasNext()) {
                        Object key = keyIt.next();
                        if (getCustomContent().containsKey(key)) {
                            messageObject.put(key.toString(), getCustomContent().get(key));
                        }
                    }
                }
                pushUnicastMessageRequest.setMessage(messageObject.toString());
                break;
            default:
                break;
            }
            return pushUnicastMessageRequest;
        }
        
        PushUnicastMessageRequest buildMessageRequest() {
            PushUnicastMessageRequest pushUnicastMessageRequest = new PushUnicastMessageRequest();
            pushUnicastMessageRequest.setDeviceType(deviceType);
            pushUnicastMessageRequest.setUserId(Long.toString(userId));
            pushUnicastMessageRequest.setChannelId(channelId);
            JSONObject customContent = null;
            if (getCustomContent() != null && !getCustomContent().isEmpty()) {
                customContent = JSONObject.fromObject(getCustomContent());
                pushUnicastMessageRequest.setMessage(customContent.toString());
            }
            return pushUnicastMessageRequest;
        }
    }

}
