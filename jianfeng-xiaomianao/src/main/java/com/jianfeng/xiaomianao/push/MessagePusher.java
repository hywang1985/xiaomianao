package com.jianfeng.xiaomianao.push;

import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.jianfeng.xiaomianao.service.PushService;

/**
 * Push unicast message to client. 
 */
public interface MessagePusher {

    public int pushUnicastMessage(PushInfo pushInfo) throws ChannelClientException, ChannelServerException;
    
    public void setPushService(PushService pushService);
}
