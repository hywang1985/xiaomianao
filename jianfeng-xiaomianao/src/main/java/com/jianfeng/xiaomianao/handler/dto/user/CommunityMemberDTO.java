package com.jianfeng.xiaomianao.handler.dto.user;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;

public class CommunityMemberDTO extends UserDTO{

    @JsonIgnore
    public Integer getState() {
        return super.getState();
    }

    @JsonIgnore
    public Date getBirth() {
        return super.getBirth();
    }

    @JsonIgnore
    public String getLocation() {
        return super.getLocation();
    }

    @JsonIgnore
    public String getSignature() {
        return super.getSignature();
    }

    @JsonIgnore
    public List<String> getMianaoStates() {
        return super.getMianaoStates();
    }

    public UserDTO convert(MianaouserinfoBean mianaoUser) {
        this.userId = mianaoUser.getUserid();
        this.userName = mianaoUser.getUsername();
        this.snapshot = mianaoUser.getSnapshot();
        this.nickName = mianaoUser.getNickName();
        return this;
    }

}
