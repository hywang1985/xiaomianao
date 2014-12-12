package com.jianfeng.xiaomianao.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.jianfeng.xiaomianao.consts.CacheConsts;
import com.jianfeng.xiaomianao.dao.PushPreferenceDao;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.PushChannelInfo;
import com.jianfeng.xiaomianao.domain.PushPreference;
import com.jianfeng.xiaomianao.util.JsonUtil;
import com.jianfeng.xiaomianao.util.StringUtil;

@Service
public class PushService extends AbstractUserNeededService {

    private static final String API_KEY = "pfnFGjz8RsuOFErvPOXCeEe9";
    
    private static final String SECRET_KEY = "ZGOoC7EYqrxzD86WLaEMKA9LipDAbq8Z";
    
    private static ChannelKeyPair pair = new ChannelKeyPair(API_KEY, SECRET_KEY);
    
    @Autowired
    private JedisPoolService poolService;
    
    @Autowired
    private PushPreferenceDao preferenceDao;

    private Map<String, PushChannelInfo> pushChannelInfoCache = new ConcurrentHashMap<String, PushChannelInfo>();
    
    public MianaouserinfoBean bindPushChannelInfo(String userId, Long pushChannelId,Long pushUserId,Integer pushAppId,Integer pushDeviceType){
        MianaouserinfoBean user = findUser(userId);
        PushChannelInfo oldChannelInfo = getPushChannelInfo(userId);
        if (oldChannelInfo != null) {
            logger.info("find push channel info,userId:{},pushChannelId:{}", new String[] { userId, pushChannelId.toString() });
            boolean isSame = isSameChannel(pushChannelId, pushUserId, pushAppId, pushDeviceType, oldChannelInfo);
            if(!isSame){
                logger.info("Update push channel info,userId:{},pushChannelId:{}", new String[] { userId, pushChannelId.toString() });
                createChannelInfo(userId, pushChannelId, pushUserId, pushAppId, pushDeviceType, user);
            }
        }else{
            logger.info("New push channel binded,userId:{},pushChannelId:{}", new String[] { userId, pushChannelId.toString() });
            createChannelInfo(userId, pushChannelId, pushUserId, pushAppId, pushDeviceType, user);
        }
        return user;
    }

    private void createChannelInfo(String userId, Long pushChannelId, Long pushUserId, Integer pushAppId, Integer pushDeviceType,
            MianaouserinfoBean user) {
        PushChannelInfo pushChannelInfo = DomainEntityFactory.eInstance.createPushChannelInfo();
        pushChannelInfo.setPushChannelId(pushChannelId);
        pushChannelInfo.setPushUserId(pushUserId);
        pushChannelInfo.setPushAppId(pushAppId);
        pushChannelInfo.setPushDeviceType(pushDeviceType);
        poolService.hset(CacheConsts.MIANAO_PUSH_CHANNEL, userId, JsonUtil.toJson(pushChannelInfo));
        putInCache(user.getUserid(),pushChannelInfo);
    }

    private boolean isSameChannel(Long pushChannelId, Long pushUserId, Integer pushAppId, Integer pushDeviceType,
            PushChannelInfo oldChannelInfo) {
        boolean isSame = true;
        if (oldChannelInfo != null) {
            isSame = isSame && (pushChannelId != null && (pushChannelId.compareTo(oldChannelInfo.getPushChannelId())==0));
            isSame = isSame && (pushUserId != null && (pushUserId.compareTo(oldChannelInfo.getPushUserId()) == 0));
            isSame = isSame && (pushAppId != null && (pushAppId.compareTo(oldChannelInfo.getPushAppId()) == 0));
            isSame = isSame && (pushDeviceType != null && (pushDeviceType.compareTo(oldChannelInfo.getPushDeviceType()) == 0));
        }
        return isSame;
    }
    
    private void putInCache(String userId, PushChannelInfo pushChannelInfo) {
        if (pushChannelInfoCache.size() > 5000) {
            Map<String, PushChannelInfo> oldPushChannelInfoCache = pushChannelInfoCache;
            pushChannelInfoCache = new ConcurrentHashMap<String, PushChannelInfo>();
            oldPushChannelInfoCache.clear();
            logger.info("Clear pushChannelInfoCache , size > 5000");
        }
        pushChannelInfoCache.put(userId, pushChannelInfo);
    }
    
    public void delChannelInfo(String userId) {
        if (!StringUtil.isEmpty(userId)) {
            pushChannelInfoCache.remove(userId);
            poolService.hdel(CacheConsts.MIANAO_PUSH_CHANNEL, userId);
            logger.info("删除推送通道, userId:{}", userId);
        }
    }

    public PushChannelInfo getPushChannelInfo(String userId) {
        if (!StringUtil.isEmpty(userId)) {
            if (pushChannelInfoCache.containsKey(userId)) {
                return pushChannelInfoCache.get(userId);
            }
            String json = poolService.hget(CacheConsts.MIANAO_PUSH_CHANNEL, userId);
            if (!StringUtil.isEmpty(json)) {
                PushChannelInfo channel = PushChannelInfo.fromJsonToPushChannelInfo(json);
                putInCache(userId,channel);
                return channel;
            }
        }
        return null;
    }
    
    public PushPreference getPushPreference(String userId, Integer eventType) {
        return preferenceDao.findPushPreference(userId, eventType);
    }
    
    public static BaiduChannelClient createChannelClient(){
        return new BaiduChannelClient(pair);
    }
}
