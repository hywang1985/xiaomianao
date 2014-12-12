package com.jianfeng.xiaomianao.service;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;

public class LikeNewsServiceTests extends AbstractServiceTests {

    @Autowired
    private LikeService likeServcie;

    @Autowired
    private UserDao userDao;

    @Test
    public void testLikeNews() {
        likeServcie.likeNews(USER_ID, NEWS_IDS);
        MianaouserinfoBean user = userDao.findUserByUserId(USER_ID);
        Set<NewsInfoBean> likedNews = user.getLikedNews();
        Assert.assertNotNull(likedNews);
        Assert.assertEquals(2, likedNews.size());
    }

    @Test
    public void testUnlikeNews() {
        likeServcie.unlikeNews(USER_ID, NEWS_IDS);
        MianaouserinfoBean user = userDao.findUserByUserId(USER_ID);
        Set<NewsInfoBean> likedNews = user.getLikedNews();
        Assert.assertEquals(0, likedNews.size());
    }
}
