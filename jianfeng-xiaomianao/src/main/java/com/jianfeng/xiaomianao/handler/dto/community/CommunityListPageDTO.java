package com.jianfeng.xiaomianao.handler.dto.community;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;


public class CommunityListPageDTO extends CommunityBriefDTO {

    @JsonIgnore
    public MainPostDTO getLastMainpost() {
        return super.getLastMainpost();
    }
    
    public static CommunityListPageDTO converCommunity(Community community) {
        CommunityListPageDTO communityDTO = null;
        if (community != null) {
            communityDTO = DTOFactory.eInstance.createCommunityListPageDTO();
            communityDTO.id = community.getId();
            communityDTO.fansCount = community.getFansCount();
            communityDTO.mainpostCount = community.getMainPostCount();
            communityDTO.name = community.getName();
            communityDTO.subscribed = community.isSubscribed();
            communityDTO.snapshot = community.getSnapshot();
        }
        return communityDTO;
    }

}
