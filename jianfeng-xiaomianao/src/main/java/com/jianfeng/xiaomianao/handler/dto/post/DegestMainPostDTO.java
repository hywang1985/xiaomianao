package com.jianfeng.xiaomianao.handler.dto.post;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;


public class DegestMainPostDTO extends MainPostDTO {

    @JsonIgnore
    public List<String> getImages() {
        return super.getImages();
    }

    @JsonIgnore
    public Date getCreateDate() {
        return super.getCreateDate();
    }

    @JsonIgnore
    public int getLikeCount() {
        return super.getLikeCount();
    }

    @JsonIgnore
    public String getContent() {
        return super.getContent();
    }

    @JsonIgnore
    public boolean isOwned() {
        return super.isOwned();
    }

    @JsonIgnore
    public CommunityMemberDTO getOwner() {
        return super.getOwner();
    }

    @JsonIgnore
    public boolean isMainPostOwner() {
        return super.isMainPostOwner();
    }

    @JsonIgnore
    public int getFloor() {
        return super.getFloor();
    }
    
    @JsonIgnore
    public ParentPostDTO getParent() {
        return super.getParent();
    }

    @JsonIgnore
    public int getReplyCount() {
        return super.getReplyCount();
    }

    @JsonIgnore
    public List<String> getTags() {
        return super.getTags();
    }

    @JsonIgnore
    public String getBriefContent() {
        return super.getBriefContent();
    }

    @JsonIgnore
    public boolean isDegest() {
        return super.isDegest();
    }

}
