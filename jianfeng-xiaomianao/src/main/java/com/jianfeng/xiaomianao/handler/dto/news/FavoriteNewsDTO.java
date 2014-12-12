package com.jianfeng.xiaomianao.handler.dto.news;

import java.util.Date;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.handler.dto.Favoritable;

public class FavoriteNewsDTO extends NewsInfoBriefDTO {

    private int type = Constants.FAVORITE_NEWS_TYPE;

    private Date favTime;

    public FavoriteNewsDTO convertNews(NewsInfoBean news) {
        super.convertNews(news);
        this.favTime = news.getFavTime();
        return this;
    }

    public Date getFavTime() {
        return favTime;
    }

    public void setFavTime(Date favTime) {
        this.favTime = favTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
