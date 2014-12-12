package com.jianfeng.xiaomianao.processor.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemeberPageDTO;
import com.jianfeng.xiaomianao.processor.IPostProcessor;

@Service("processor_214")
public class FindCommunityMemberPageProcessor implements IPostProcessor<CommunityMemeberPageDTO, MianaouserinfoBean> {

    @Autowired
    private UserDao userDao;

    public CommunityMemeberPageDTO postProcess(ClientRequest clientRequest, MianaouserinfoBean processedResult, String userId)
            throws Exception {
        setFollowed(userId, processedResult);
        return (CommunityMemeberPageDTO) DTOFactory.eInstance.createCommunityMemeberPageDTO().convert(processedResult);
    }

    private void setFollowed(String userId, MianaouserinfoBean targetUser) {
        MianaouserinfoBean user = userDao.findUserByUserId(userId);
        if (user != null && targetUser != null && targetUser.getFollowers().contains(user)) {
            targetUser.setFollowed(true);
        }
    }
}
