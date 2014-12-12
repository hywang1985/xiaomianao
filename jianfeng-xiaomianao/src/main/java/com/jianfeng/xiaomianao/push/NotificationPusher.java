package com.jianfeng.xiaomianao.push;

import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.jianfeng.xiaomianao.service.PushService;

/**
 * Push unicast notification to client. 
 */
public interface NotificationPusher {

    public int pushUnicastNotification(PushInfo pushInfo) throws ChannelClientException, ChannelServerException;
    
    public void setPushService(PushService pushService);
}
