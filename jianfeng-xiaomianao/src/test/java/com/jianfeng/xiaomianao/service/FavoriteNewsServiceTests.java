package com.jianfeng.xiaomianao.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoriteNewsServiceTests extends AbstractServiceTests {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private AbstractUserNeededService queryService;

    @Test
    public void testFavoriteNews() {
        favoriteService.favoriteNews(USER_ID, NEWS_IDS);
    }

    @Test
    public void testUnFavoriteNews() {
        favoriteService.unfavoriteNews(USER_ID, NEWS_IDS);
    }
}
