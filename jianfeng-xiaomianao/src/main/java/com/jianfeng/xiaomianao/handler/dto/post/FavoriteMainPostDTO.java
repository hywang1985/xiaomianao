package com.jianfeng.xiaomianao.handler.dto.post;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.handler.dto.Favoritable;
import com.jianfeng.xiaomianao.handler.dto.user.CommunityMemberDTO;

public class FavoriteMainPostDTO extends MainPostDTO {

    private int type = Constants.FAVORITE_MAINPOST_TYPE;

    private Date favTime;

    private String image;

    public FavoriteMainPostDTO converMainPost(MainPost mainPost) {
        super.converMainPost(mainPost);
        if (images != null && !images.isEmpty()) {
            this.image = images.get(0);
        }
        this.favTime = mainPost.getFavTime();
        return this;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @JsonIgnore
    public List<String> getTags() {
        return super.getTags();
    }

    @JsonIgnore
    public int getReplyCount() {
        return super.getReplyCount();
    }

    @JsonIgnore
    public boolean isLiked() {
        return super.isLiked();
    }

    @JsonIgnore
    public int getLikeCount() {
        return super.getLikeCount();
    }

    @JsonIgnore
    public CommunityMemberDTO getOwner() {
        return super.getOwner();
    }

    @JsonIgnore
    public boolean isOwned() {
        return super.isOwned();
    }

    @JsonIgnore
    public boolean isMainPostOwner() {
        return super.isMainPostOwner();
    }

    @JsonIgnore
    public Date getCreateDate() {
        return super.getCreateDate();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonIgnore
    public List<String> getImages() {
        return super.getImages();
    }

    public void setFavTime(Date favTime) {
        this.favTime = favTime;
    }

    public Date getFavTime() {
        return favTime;
    }

    @JsonIgnore
    public int getDigestType() {
        return super.getDigestType();
    }
}
