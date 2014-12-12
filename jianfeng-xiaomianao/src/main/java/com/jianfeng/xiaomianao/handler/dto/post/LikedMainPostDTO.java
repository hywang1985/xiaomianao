package com.jianfeng.xiaomianao.handler.dto.post;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.MainPost;

public class LikedMainPostDTO extends FavoriteMainPostDTO {

    private Date likeTime;

    private int type = Constants.LIKED_MAINPOSTS_TYPE;

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public LikedMainPostDTO converMainPost(MainPost mainPost) {
        super.converMainPost(mainPost);
        this.likeTime = mainPost.getLikeTime();
        return this;
    }

    @JsonIgnore
    public Date getFavTime() {
        return super.getFavTime();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
