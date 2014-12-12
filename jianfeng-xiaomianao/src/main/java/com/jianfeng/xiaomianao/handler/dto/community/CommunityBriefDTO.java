package com.jianfeng.xiaomianao.handler.dto.community;

import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.handler.dto.DTOFactory;
import com.jianfeng.xiaomianao.handler.dto.post.MainPostDTO;

public class CommunityBriefDTO {

    protected int id;

    protected String name;

    protected int fansCount;

    protected String snapshot;

    protected boolean subscribed;

    protected int mainpostCount;

    protected MainPostDTO lastMainpost = DTOFactory.eInstance.createMainPostDTO();

    public static CommunityBriefDTO converCommunity(Community community) {
        CommunityBriefDTO communityDTO = null;
        if (community != null) {
            communityDTO = DTOFactory.eInstance.createCommunityBriefDTO();
            communityDTO.id = community.getId();
            communityDTO.fansCount = community.getFansCount();
            communityDTO.mainpostCount = community.getMainPostCount();
            communityDTO.name = community.getName();
            communityDTO.subscribed = community.isSubscribed();
            communityDTO.snapshot = community.getSnapshot();
            communityDTO.lastMainpost = community.getLastMainPost();
        }
        return communityDTO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getMainpostCount() {
        return mainpostCount;
    }

    public void setMainpostCount(int mainpostCount) {
        this.mainpostCount = mainpostCount;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public MainPostDTO getLastMainpost() {
        return lastMainpost;
    }

    public void setLastMainpost(MainPostDTO lastMainpost) {
        this.lastMainpost = lastMainpost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
