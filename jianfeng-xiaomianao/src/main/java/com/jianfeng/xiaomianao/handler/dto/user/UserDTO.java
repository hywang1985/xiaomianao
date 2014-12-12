package com.jianfeng.xiaomianao.handler.dto.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.jianfeng.xiaomianao.domain.MianaoState;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;

public class UserDTO {

    protected String userName;

    protected String userId;

    protected Integer state;

    protected Date birth;

    protected String location;

    protected String snapshot;

    protected String signature;

    protected String nickName;

    protected List<String> mianaoStates = new ArrayList<String>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<String> getMianaoStates() {
        return mianaoStates;
    }

    public void setMianaoStates(List<String> mianaoStates) {
        this.mianaoStates = mianaoStates;
    }

    public UserDTO convert(MianaouserinfoBean mianaoUser) {
        if (mianaoUser != null) {
            this.userName = mianaoUser.getUsername();
            this.userId = mianaoUser.getUserid();
            this.state = mianaoUser.getStatue();
            this.birth = mianaoUser.getBirth();
            this.location = mianaoUser.getLocation();
            this.snapshot = mianaoUser.getSnapshot();
            this.signature = mianaoUser.getSignature();
            this.nickName = mianaoUser.getNickName();
            Iterator<MianaoState> it = mianaoUser.getMianaoStates().iterator();
            while (it.hasNext()) {
                MianaoState state = it.next();
                this.getMianaoStates().add(state.getName());
            }
        }

        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
