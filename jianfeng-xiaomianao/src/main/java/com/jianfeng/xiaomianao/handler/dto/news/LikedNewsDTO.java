package com.jianfeng.xiaomianao.handler.dto.news;

import java.util.Date;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;

public class LikedNewsDTO extends NewsInfoBriefDTO {

    private int likeCount;

    private int replyCount;

    private Date likeTime;

    private int type = Constants.LIKED_NEWS_TYPE;

    public LikedNewsDTO convertNews(NewsInfoBean news) {
        super.convertNews(news);
        this.likeCount = news.getLikeCount();
        this.replyCount = news.getReplyCount();
        this.likeTime = news.getLikeTime();
        return this;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
