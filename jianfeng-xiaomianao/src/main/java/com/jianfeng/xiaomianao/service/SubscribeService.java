package com.jianfeng.xiaomianao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.dao.CommunityDao;
import com.jianfeng.xiaomianao.dao.MianaoStateDao;
import com.jianfeng.xiaomianao.dao.TagDao;
import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.domain.MianaoState;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.Tag;

@Service
public class SubscribeService extends AbstractUserNeededService {

    @Autowired
    private TagDao tagDao;

    @Autowired
    private MianaoStateDao stateDao;
    
    @Autowired
    private CommunityDao communityDao; 

    public MianaouserinfoBean subscribeTags(String userId, List<String> tags) {

        MianaouserinfoBean user = findUser(userId);
        if (tags == null || tags.isEmpty()) {
            throw new IllegalArgumentException("The argument tags is empty or null");
        }

        List<Tag> matchedTags = tagDao.findTagsByName(tags);
        if (matchedTags != null && !matchedTags.isEmpty()) {
            user.getSubscribedTags().addAll(matchedTags);
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unsubscribeTags(String userId, List<String> tags) {

        MianaouserinfoBean user = findUser(userId);
        if (tags == null || tags.isEmpty()) {
            throw new IllegalArgumentException("The argument tags is empty or null");
        }

        List<Tag> matchedTags = tagDao.findTagsByName(tags);
        if (matchedTags != null && !matchedTags.isEmpty()) {
            user.getSubscribedTags().removeAll(matchedTags);
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean subscribeMianaoStates(String userId, List<String> statesToSubscribe) {
        MianaouserinfoBean user = findUser(userId);
        List<MianaoState> matchedStates = stateDao.findStatesByName(statesToSubscribe);
        if (matchedStates != null && !matchedStates.isEmpty()) {
            user.getMianaoStates().addAll(matchedStates);
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unsubscribeMianaoStates(String userId, List<String> statesToUnubscribe) {
        MianaouserinfoBean user = findUser(userId);
        List<MianaoState> matchedStates = stateDao.findStatesByName(statesToUnubscribe);
        if (matchedStates != null && !matchedStates.isEmpty()) {
            user.getMianaoStates().removeAll(matchedStates);
        }
        return userDao.update(user);
    }
    public MianaouserinfoBean subscribeCommunities(String userId,List<Integer> communityIds){
        MianaouserinfoBean user = findUser(userId);
        List<Community> matchedCommunities = communityDao.findCommunityByIds(communityIds);
        if (matchedCommunities != null && !matchedCommunities.isEmpty()) {
            for(Community community:matchedCommunities){
                community.setFansCount(community.getFansCount()+1);
                user.getCommunities().add(community);
                user.setCommunitiesCount(user.getCommunitiesCount()+1);
            }
        }
        return userDao.update(user);
    }
    
    public MianaouserinfoBean unsubscribeCommunities(String userId,List<Integer> communityIds){
        MianaouserinfoBean user = findUser(userId);
        List<Community> matchedCommunities = communityDao.findCommunityByIds(communityIds);
        if (matchedCommunities != null && !matchedCommunities.isEmpty()) {
            for(Community community:matchedCommunities){
                community.setFansCount(community.getFansCount()-1);
                user.getCommunities().remove(community);
                user.setCommunitiesCount(user.getCommunitiesCount()-1);
            }
        }
        return userDao.update(user);
    }
}
