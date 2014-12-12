package com.jianfeng.xiaomianao.handler.dto.post;

import java.util.Date;

import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;

/**
 * @author hywang
 * 
 * For a Post, should not have a recurrence relation to its parent, so create the individual DTO to disturb the mapping.
 */
public class ParentPostDTO {

    protected int id;

    protected Date createDate;

    protected int likeCount;

    protected String content;

    protected boolean owned;

    protected CommunityMemberDTO owner;

    protected boolean mainPostOwner;

    protected int floor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }

    public CommunityMemberDTO getOwner() {
        return owner;
    }

    public void setOwner(CommunityMemberDTO owner) {
        this.owner = owner;
    }

    public boolean isMainPostOwner() {
        return mainPostOwner;
    }

    public void setMainPostOwner(boolean mainPostOwner) {
        this.mainPostOwner = mainPostOwner;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

}
