package com.jianfeng.xiaomianao.handler.dto.user;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;

public class UserListMemberDTO extends UserDTO {

    private int followersCount;

    private int mainpostCount;

    public UserListMemberDTO convert(MianaouserinfoBean mianaoUser) {
        super.convert(mianaoUser);
        this.followersCount = mianaoUser.getFollowersCount();
        this.mainpostCount = mianaoUser.getMainpostCount();
        return this;
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
    public String getSignature() {
        return super.getSignature();
    }

    @JsonIgnore
    public List<String> getMianaoStates() {
        return super.getMianaoStates();
    }

    public int getMainpostCount() {
        return mainpostCount;
    }

    public void setMainpostCount(int mainpostCount) {
        this.mainpostCount = mainpostCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

}
