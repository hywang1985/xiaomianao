package com.jianfeng.xiaomianao.handler.dto.news;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;

public class NewsInfoBriefDTO {

    private int id;

    private String briefContent;

    private String image;
    
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBriefContent() {
        return briefContent;
    }

    public void setBriefContent(String briefContent) {
        this.briefContent = briefContent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NewsInfoBriefDTO convertNews(NewsInfoBean news) {
        if (news != null) {
            this.id = news.getId();
            this.title = news.getTitle();
            this.briefContent = news.getBriefContent();
            this.image = news.getImage();
        }
        return this;
    }

    
}
