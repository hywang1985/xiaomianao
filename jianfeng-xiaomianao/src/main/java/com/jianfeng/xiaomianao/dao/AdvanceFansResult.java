package com.jianfeng.xiaomianao.dao;

public class AdvanceFansResult {

    private long userid;

    private long count;

    public AdvanceFansResult(long userid, long count) {
        this.userid = userid;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
