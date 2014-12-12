package com.jianfeng.xiaomianao.service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.dao.LikeRelationDao;
import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.dao.NewsInfoDao;
import com.jianfeng.xiaomianao.dao.PostDao;
import com.jianfeng.xiaomianao.domain.DomainEntityFactory;
import com.jianfeng.xiaomianao.domain.LikeRelation;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.util.ErrorCode;

@Service
public class LikeService extends AbstractUserNeededService {

    @Autowired
    private NewsInfoDao newsDao;

    @Autowired
    private MainPostDao mainpostDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private LikeRelationDao likeDao;

    private void createLikeRelation(int type, long userId, int resourceId) {
        LikeRelation likeRelation = DomainEntityFactory.eInstance.createLikeRelation();
        likeRelation.setType(type);
        likeRelation.setUserId(userId);
        likeRelation.setResourceId(resourceId);
        likeRelation.setTime(Calendar.getInstance().getTime());
        likeDao.create(likeRelation);
    }

    private void deleteLikeRelation(int type, long userId, int resourceId) {
        LikeRelation likeRelation = likeDao.findLikeRelation(type, userId, resourceId);
        if (likeRelation != null) {
            likeDao.delete(likeRelation);
        }
    }

    public MianaouserinfoBean likeNews(String userId, List<Integer> newsIds) {

        MianaouserinfoBean user = findUser(userId);

        if (newsIds == null || newsIds.isEmpty()) {
            throw new IllegalArgumentException("The argument newsIds is empty or null");
        }
        List<NewsInfoBean> matchedNews = newsDao.findNewsByIds(newsIds);
        if (matchedNews != null && !matchedNews.isEmpty()) {
            Set<NewsInfoBean> likedNews = user.getLikedNews();
            for (NewsInfoBean news : matchedNews) {
                Set<MianaouserinfoBean> likers = news.getLikers();
                if (!likedNews.contains(news) && !likers.contains(user)) {
                    likedNews.add(news);
                    likers.add(user);
                    user.setLikedNewsCount(user.getLikedNewsCount() + 1);
                    createLikeRelation(Constants.LIKED_NEWS_TYPE, user.getId(), news.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.ALREADY_LIKED_NEWS);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unlikeNews(String userId, List<Integer> newsIds) {

        if (newsIds == null || newsIds.isEmpty()) {
            throw new IllegalArgumentException("The argument newsIds is empty or null");
        }
        if (userId == null || StringUtils.isEmpty(userId)) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        MianaouserinfoBean user = userDao.findUserByUserId(userId);
        List<NewsInfoBean> matchedNews = newsDao.findNewsByIds(newsIds);
        if (matchedNews != null && !matchedNews.isEmpty()) {
            Set<NewsInfoBean> likedNews = user.getLikedNews();
            for (NewsInfoBean news : matchedNews) {
                Set<MianaouserinfoBean> likers = news.getLikers();
                if (likedNews.contains(news) && likers.contains(user)) {
                    likedNews.remove(news);
                    likers.remove(user);
                    user.setLikedNewsCount(user.getLikedNewsCount() - 1);
                    deleteLikeRelation(Constants.LIKED_NEWS_TYPE, user.getId(), news.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.NEWS_HAS_NOT_BEEN_LIKED);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean likeMainPost(String userId, List<Integer> mainpostIds) {

        MianaouserinfoBean user = findUser(userId);

        if (mainpostIds == null || mainpostIds.isEmpty()) {
            throw new IllegalArgumentException("The argument mainpostIds is empty or null");
        }
        List<MainPost> matchedMainposts = mainpostDao.findMainPostsByIds(mainpostIds);
        if (matchedMainposts != null && !matchedMainposts.isEmpty()) {
            Set<MainPost> likedMainPosts = user.getLikedMainPosts();
            for (MainPost mainPost : matchedMainposts) {
                Set<MianaouserinfoBean> mpLikers = mainPost.getMpLikers();
                if (!likedMainPosts.contains(mainPost) && !mpLikers.contains(user)) {
                    likedMainPosts.add(mainPost);
                    mpLikers.add(user);
                    mainPost.setLikeCount(mainPost.getLikeCount() + 1);
                    user.setLikedPostCount(user.getLikedPostCount() + 1);
                    createLikeRelation(Constants.LIKED_MAINPOSTS_TYPE, user.getId(), mainPost.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.ALREADY_LIKED_MAINPOST);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unlikeMainPost(String userId, List<Integer> mainpostIds) {

        MianaouserinfoBean user = findUser(userId);

        if (mainpostIds == null || mainpostIds.isEmpty()) {
            throw new IllegalArgumentException("The argument mainpostIds is empty or null");
        }
        List<MainPost> matchedMainposts = mainpostDao.findMainPostsByIds(mainpostIds);
        if (matchedMainposts != null && !matchedMainposts.isEmpty()) {
            Set<MainPost> likedMainPosts = user.getLikedMainPosts();
            for (MainPost mainPost : matchedMainposts) {
                Set<MianaouserinfoBean> mpLikers = mainPost.getMpLikers();
                if (likedMainPosts.contains(mainPost) && mpLikers.contains(user)) {
                    likedMainPosts.remove(mainPost);
                    mpLikers.remove(user);
                    mainPost.setLikeCount(mainPost.getLikeCount() - 1);
                    user.setLikedPostCount(user.getLikedPostCount() - 1);
                    deleteLikeRelation(Constants.LIKED_MAINPOSTS_TYPE, user.getId(), mainPost.getId());
                } else {
                    throw new XiaoMianAoException(ErrorCode.MAINPOST_HAS_NOT_BEEN_LIKED);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean likePost(String userId, List<Integer> postIds) {

        MianaouserinfoBean user = findUser(userId);

        if (postIds == null || postIds.isEmpty()) {
            throw new IllegalArgumentException("The argument postIds is empty or null");
        }
        List<Post> matchedPosts = postDao.findPostsByIds(postIds);
        if (matchedPosts != null && !matchedPosts.isEmpty()) {
            Set<Post> likedPosts = user.getLikedPosts();
            for (Post post : matchedPosts) {
                Set<MianaouserinfoBean> likers = post.getLikers();
                if (!likedPosts.contains(post) && !likers.contains(user)) {
                    likedPosts.add(post);
                    likers.add(user);
                    post.setLikeCount(post.getLikeCount() + 1);
                } else {
                    throw new XiaoMianAoException(ErrorCode.ALREADY_LIKED_POST);
                }
            }
        }
        return userDao.update(user);
    }

    public MianaouserinfoBean unlikePost(String userId, List<Integer> postIds) {

        MianaouserinfoBean user = findUser(userId);

        if (postIds == null || postIds.isEmpty()) {
            throw new IllegalArgumentException("The argument postIds is empty or null");
        }
        List<Post> matchedPosts = postDao.findPostsByIds(postIds);
        if (matchedPosts != null && !matchedPosts.isEmpty()) {
            Set<Post> likedPosts = user.getLikedPosts();
            for (Post post : matchedPosts) {
                Set<MianaouserinfoBean> likers = post.getLikers();
                if (likedPosts.contains(post) && likers.contains(user)) {
                    likedPosts.remove(post);
                    likers.remove(user);
                    post.setLikeCount(post.getLikeCount() - 1);
                } else {
                    throw new XiaoMianAoException(ErrorCode.POST_HAS_NOT_BEEN_LIKED);
                }
            }
        }
        return userDao.update(user);
    }

}
