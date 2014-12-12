package com.jianfeng.xiaomianao.processor;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;

public class AbstractCommunityProcessor<R, T> implements IPostProcessor<R, T> {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SubscriptionCaculator caculator;

    @Autowired
    private MainPostDao mainpostDao;

    @Override
    public R postProcess(ClientRequest clientRequest, T processedResult, String userId) throws Exception {
        return null;
    }

    protected void setSubscribed(String userId, Community communityToHandle) {
        Set<MianaouserinfoBean> subscribers = communityToHandle.getSubscribers();
        boolean subscribed = caculator.isSubscribed(subscribers, userId);
        communityToHandle.setSubscribed(subscribed);
    }

    protected void setLastMainpost(Community communityToHandle) {
        int lastMainpostId = communityToHandle.getLastMainpostId();
        if (lastMainpostId > 0) {
            MainPost last = mainpostDao.read(lastMainpostId);
            if (last != null) {
                communityToHandle.setLastMainPost(DTOFactory.eInstance.createMainPostDTO().converMainPost(last));
            }
        }
    }

    protected void setAdvancefans(Community communityToHandle) {
        List<MianaouserinfoBean> advFans = userDao.findAdvanceFansForCommunity(communityToHandle.getId());
        if (advFans != null && !advFans.isEmpty()) {
            for (MianaouserinfoBean fans : advFans) {
                communityToHandle.getAdvanceFans().add(
                        (CommunityMemberDTO) DTOFactory.eInstance.createCommunityMemberDTO().convert(fans));
            }
        }
    }
    
    protected void setAdmins(Community communityToHandle) {
        Set<MianaouserinfoBean> admins = communityToHandle.getAdmins();
        if (admins != null && !admins.isEmpty()) {
            for (MianaouserinfoBean admin : admins) {
                communityToHandle.getAdministrators().add(
                        (CommunityMemberDTO) DTOFactory.eInstance.createCommunityMemberDTO().convert(admin));
            }
        }
    }

}
