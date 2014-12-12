package com.jianfeng.xiaomianao.handler.dto.news;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;

/**
 * Deprecated until new load mechanism (request-prior or data-prior) is built.
 */
@Deprecated
public class TopBarNewsDTO {

    private int id;

    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    public static TopBarNewsDTO create() {
        return new TopBarNewsDTO();
    }
    
    public static TopBarNewsDTO convertNewsInfoBean(NewsInfoBean news){
        TopBarNewsDTO toReturn = create();
        toReturn.setId(news.getId());
        toReturn.setImage(news.getImage());
        return toReturn;
    }
}
