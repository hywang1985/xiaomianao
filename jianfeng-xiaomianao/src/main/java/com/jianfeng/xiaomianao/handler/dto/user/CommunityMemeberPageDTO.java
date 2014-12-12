package com.jianfeng.xiaomianao.handler.dto.user;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;

public class CommunityMemeberPageDTO extends UserDTO {

    private int communitiesCount;

    private int friendsCount;

    private int followersCount;

    private int favCount;

    private int mainpostCount;

    private int postCount;

    private int likedNewsCount;

    private int likedPostCount;

    private boolean followed;

    public int getCommunitiesCount() {
        return communitiesCount;
    }

    public void setCommunitiesCount(int communitiesCount) {
        this.communitiesCount = communitiesCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getMainpostCount() {
        return mainpostCount;
    }

    public void setMainpostCount(int mainpostCount) {
        this.mainpostCount = mainpostCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getLikedNewsCount() {
        return likedNewsCount;
    }

    public void setLikedNewsCount(int likedNewsCount) {
        this.likedNewsCount = likedNewsCount;
    }

    public int getLikedPostCount() {
        return likedPostCount;
    }

    public void setLikedPostCount(int likedPostCount) {
        this.likedPostCount = likedPostCount;
    }

    public CommunityMemeberPageDTO convert(MianaouserinfoBean user) {
        this.setUserId(user.getUserid());
        this.setUserName(user.getUsername());
        this.setSnapshot(user.getSnapshot());
        this.setSignature(user.getSignature());
        this.setNickName(user.getNickName());
        this.setCommunitiesCount(user.getCommunitiesCount());
        this.setFriendsCount(user.getFriendsCount());
        this.setFollowersCount(user.getFollowersCount());
        this.setFavCount(user.getFavPostCount() + user.getFavNewsCount());
        this.setMainpostCount(user.getMainpostCount());
        this.setPostCount(user.getPostCount());
        this.setLikedPostCount(user.getLikedPostCount());
        this.setLikedNewsCount(user.getLikedNewsCount());
        this.setFollowed(user.isFollowed());
        return this;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    @JsonIgnore
    public Integer getState() {
        return super.getState();
    }

    @JsonIgnore
    public Date getBirth() {
        return super.getBirth();
    }

    @JsonIgnore
    public List<String> getMianaoStates() {
        return super.getMianaoStates();
    }

    public int getFavCount() {
        return favCount;
    }

    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }
}
