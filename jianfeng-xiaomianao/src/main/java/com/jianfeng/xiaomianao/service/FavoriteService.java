package com.jianfeng.xiaomianao.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.dao.FavoriteRelationDao;
import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.dao.NewsInfoDao;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.FavoriteRelation;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.util.ErrorCode;

@Service
public class FavoriteService extends AbstractUserNeededService {

    @Autowired
    private NewsInfoDao newsDao;

    @Autowired
    private MainPostDao mainpostDao;
    
    @Autowired
    private FavoriteRelationDao favoriteDao;
 
    private void createFavoriteRelation(int type, long userId, int resourceId)  {
        FavoriteRelation favRelation = DomainEntityFactory.eInstance.createFavoriteRelation();
        favRelation.setType(type);
        favRelation.setUserId(userId);
        favRelation.setResourceId(resourceId);
        favRelation.setTime(Calendar.getInstance().getTime());
        favoriteDao.create(favRelation);
    }
    
    private void deleteFavoriteRelation(int type,long userId,int resourceId){
        FavoriteRelation favRelation = favoriteDao.findFavoriteRelation(type, userId, resourceId);
        if(favRelation!=null){
            favoriteDao.delete(favRelation);
        }
    }
    
    public MianaouserinfoBean favoriteNews(String userId, List<Integer> newsIds) {

        MianaouserinfoBean user = findUser(userId);
        if (newsIds == null || newsIds.isEmpty()) {
            throw new IllegalArgumentException("The argument newsIds is empty or null");
        }
        List<NewsInfoBean> matchedNews = newsDao.findNewsByIds(newsIds);
        if (matchedNews != null && !matchedNews.isEmpty()) {
            Set<NewsInfoBean> favoriteNews = user.getFavoriteNews();
            for (NewsInfoBean news : matchedNews) {
                Set<MianaouserinfoBean> favoritors = news.getFavoritors();
                if (!favoriteNews.contains(news) && !favoritors.contains(user)) {
                    favoriteNews.add(news);
                    favoritors.add(user);
                    user.setFavNewsCount(user.getFavNewsCount() + 1);
                    createFavoriteRelation(Constants.FAVORITE_NEWS_TYPE,user.getId(),news.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.ALREADY_FAVORITED_NEWS);
                }
            }
        }
        return userDao.update(user);
    }
    

    public MianaouserinfoBean favoriteMainPost(String userId, List<Integer> mainpostIds){

        MianaouserinfoBean user = findUser(userId);
        if (mainpostIds == null || mainpostIds.isEmpty()) {
            throw new IllegalArgumentException("The argument mainpostIds is empty or null");
        }
        List<MainPost> matchedMainposts = mainpostDao.findMainPostsByIds(mainpostIds);
        if (matchedMainposts != null && !matchedMainposts.isEmpty()) {
            Set<MainPost> favoriteMainpost = user.getFavoriteMainpost();
            for (MainPost mainPost : matchedMainposts) {
                Set<MianaouserinfoBean> favoritors = mainPost.getFavoritors();
                if (!favoriteMainpost.contains(mainPost) && !favoritors.contains(user)) {
                    favoritors.add(user);
                    favoriteMainpost.add(mainPost);
                    user.setFavPostCount(user.getFavPostCount() + 1);
                    createFavoriteRelation(Constants.FAVORITE_MAINPOST_TYPE, user.getId(), mainPost.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.ALREADY_FAVORITED_MAINPOST);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unfavoriteNews(String userId, List<Integer> newsIds) {

        if (newsIds == null || newsIds.isEmpty()) {
            throw new IllegalArgumentException("The argument newsIds is empty or null");
        }

        if (userId == null || StringUtils.isEmpty(userId)) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        MianaouserinfoBean user = userDao.findUserByUserId(userId);
        List<NewsInfoBean> matchedNews = newsDao.findNewsByIds(newsIds);
        if (matchedNews != null && !matchedNews.isEmpty()) {
            Set<NewsInfoBean> favoriteNews = user.getFavoriteNews();
            for (NewsInfoBean news : matchedNews) {
                Set<MianaouserinfoBean> favoritors = news.getFavoritors();
                if (favoriteNews.contains(news) && favoritors.contains(user)) {
                    favoritors.remove(user);
                    favoriteNews.remove(news);
                    user.setFavNewsCount(user.getFavNewsCount() - 1);
                    deleteFavoriteRelation(Constants.FAVORITE_NEWS_TYPE, user.getId(), news.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.NEWS_HAS_NOT_BEEN_FAVORITED);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unfavoriteMainPost(String userId, List<Integer> mainpostIds) {

        MianaouserinfoBean user = findUser(userId);
        if (mainpostIds == null || mainpostIds.isEmpty()) {
            throw new IllegalArgumentException("The argument mainpostIds is empty or null");
        }
        List<MainPost> matchedMainposts = mainpostDao.findMainPostsByIds(mainpostIds);
        if (matchedMainposts != null && !matchedMainposts.isEmpty()) {
            Set<MainPost> favoriteMainpost = user.getFavoriteMainpost();
            for (MainPost mainPost : matchedMainposts) {
                Set<MianaouserinfoBean> favoritors = mainPost.getFavoritors();
                if (favoriteMainpost.contains(mainPost) && favoritors.contains(user)) {
                    favoritors.remove(user);
                    favoriteMainpost.remove(mainPost);
                    user.setFavPostCount(user.getFavPostCount() - 1);
                    deleteFavoriteRelation(Constants.FAVORITE_MAINPOST_TYPE, user.getId(), mainPost.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.MAINPOST_HAS_NOT_BEEN_FAVORITED);
                }
            }
        }
        return userDao.update(user);
    }
}
