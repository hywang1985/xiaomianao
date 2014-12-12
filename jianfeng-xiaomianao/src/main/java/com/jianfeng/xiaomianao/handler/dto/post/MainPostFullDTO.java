package com.jianfeng.xiaomianao.handler.dto.post;

import com.jianfeng.xiaomianao.domain.MainPost;

public class MainPostFullDTO extends MainPostDTO {

    private String mainPostContent;

    private int visits;

    private String source;

    private boolean favorited;

    public String getMainPostContent() {
        return mainPostContent;
    }

    public void setMainPostContent(String mainPostContent) {
        this.mainPostContent = mainPostContent;
    }

    public int getVisits() {
        return visits;
    }

    public void setVisits(int visits) {
        this.visits = visits;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public MainPostFullDTO converMainPost(MainPost mainPost) {
        super.converMainPost(mainPost);
        if (mainPost != null) {
            this.mainPostContent = mainPost.getMainPostContent();
            this.visits = mainPost.getVisits();
            this.source = mainPost.getSource();
            this.favorited = mainPost.isFavorited();
        }
        return this;
    }

}
