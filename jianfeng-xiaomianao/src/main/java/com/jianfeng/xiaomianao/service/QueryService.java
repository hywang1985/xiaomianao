package com.jianfeng.xiaomianao.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jianfeng.xiaomianao.consts.Constants;
import com.jianfeng.xiaomianao.dao.ClassificationDao;
import com.jianfeng.xiaomianao.dao.CommentDao;
import com.jianfeng.xiaomianao.dao.CommunityClassificationDao;
import com.jianfeng.xiaomianao.dao.CommunityDao;
import com.jianfeng.xiaomianao.dao.CommunityTagDao;
import com.jianfeng.xiaomianao.dao.FavoriteRelationDao;
import com.jianfeng.xiaomianao.dao.LikeRelationDao;
import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.dao.MianaoStateDao;
import com.jianfeng.xiaomianao.dao.NewsInfoDao;
import com.jianfeng.xiaomianao.dao.PostDao;
import com.jianfeng.xiaomianao.dao.TagDao;
import com.jianfeng.xiaomianao.domain.Classification;
import com.jianfeng.xiaomianao.domain.Comment;
import com.jianfeng.xiaomianao.domain.Community;
import com.jianfeng.xiaomianao.domain.CommunityClassification;
import com.jianfeng.xiaomianao.domain.CommunityTag;
import com.jianfeng.xiaomianao.domain.FavoriteRelation;
import com.jianfeng.xiaomianao.domain.LikeRelation;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.MianaoState;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.domain.Tag;
import com.jianfeng.xiaomianao.handler.dto.Favoritable;
import com.jianfeng.xiaomianao.util.JsonUtil;

@Service
public class QueryService extends AbstractUserNeededService {

    private Logger logger = LoggerFactory.getLogger(QueryService.class);

    @Autowired
    private ClassificationDao classDao;

    @Autowired
    private CommunityClassificationDao communityClassDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private MianaoStateDao stateDao;

    @Autowired
    private NewsInfoDao newsInfoDao;

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private MainPostDao mainpostDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommunityTagDao communityTagDao;

    @Autowired
    private FavoriteRelationDao favDao;

    @Autowired
    private LikeRelationDao likeDao;

    public NewsInfoBean findNewsById(String userId, Integer newsId) {
        if (newsId == null || newsId.intValue() < 0) {
            throw new IllegalArgumentException("The argument newsId is required.");
        }
        NewsInfoBean newsInfoBean = newsInfoDao.findNewsById(newsId);
        if (newsInfoBean != null) {
            logger.info("bootclientAndfinduserinfo 返回数据：", newsInfoBean);
            return newsInfoBean;
        }
        return null;
    }

    public List<NewsInfoBean> findNews(String userId, Integer category, Integer firstResult, Integer maxResults) {
        if (category == null || category < 0) {
            throw new IllegalArgumentException("The argument category is required.");
        }
        List<NewsInfoBean> list = newsInfoDao.findNews(category, firstResult, maxResults);
        logger.info("bootclientAndfinduserinfo 返回数据：" + list);
        return list;
    }

    public Map<String, Object> findNewsByTags(String userId, List<String> tags, Integer firstResult, Integer maxResults) {
        if (tags == null) {
            throw new IllegalArgumentException("The argument tags is required.");
        }
        Map<String, Object> toReturn = new LinkedHashMap<String, Object>();
        List<Map<String, Object>> newsField = new ArrayList<Map<String, Object>>();
        List<String> tagList = new ArrayList<String>();
        for (String tag : tags) {
            tagList.clear();
            tagList.add(tag);
            Map<String, Object> tagMapping = new LinkedHashMap<String, Object>();
            List<NewsInfoBean> matchedNews = newsInfoDao.findNewsByTags(tagList, firstResult, maxResults);
            tagMapping.put("tag", tag);
            tagMapping.put("news", matchedNews);
            newsField.add(tagMapping);
        }
        Long totalCount = newsInfoDao.totalCountForTaggedNews(tags);
        toReturn.put("totalCount", totalCount);
        toReturn.put("records", newsField);
        return toReturn;
    }

    public List<Object> findFavoriteResource(List<Integer> types, String userId, Integer firstResult, Integer maxResults) {

        MianaouserinfoBean user = findUser(userId);
        List<Object> toReturn = null;
        if (types == null) {
            types = new ArrayList<Integer>();
        }
        if (types.isEmpty()) {
            setFavoriteResourceType(types);
        }
        List<FavoriteRelation> relations = favDao.findUserFavoriteResource(types, user.getId(), firstResult, maxResults);
        if (relations != null && !relations.isEmpty()) {
            toReturn = new ArrayList<Object>();
            for (FavoriteRelation relation : relations) {
                int resourceId = relation.getResourceId();
                Date favTime = relation.getTime();
                switch (relation.getType()) {
                case Constants.FAVORITE_NEWS_TYPE:
                    NewsInfoBean news = newsInfoDao.read(resourceId);
                    if (news != null) {
                        news.setFavTime(favTime);
                        toReturn.add(news);
                    }
                    break;
                case Constants.FAVORITE_MAINPOST_TYPE:
                    MainPost mainPost = mainpostDao.read(resourceId);
                    if (mainPost != null) {
                        toReturn.add(mainPost);
                    }
                    break;
                default:
                    break;
                }
            }
        }
        return toReturn;
    }

    private void setFavoriteResourceType(List<Integer> types) {
        types.add(Constants.FAVORITE_NEWS_TYPE);
        types.add(Constants.FAVORITE_MAINPOST_TYPE);
    }

    public List<Object> findLikedResource(List<Integer> types, String targetUserId, Integer firstResult, Integer maxResults) {
        List<Object> toReturn = null;
        MianaouserinfoBean user = findUser(targetUserId);
        List<LikeRelation> likeRelations = likeDao.findUserLikedResource(types, user.getId(), firstResult, maxResults);
        if (likeRelations != null && !likeRelations.isEmpty()) {
            toReturn = new ArrayList<Object>();
            for (LikeRelation likeRelation : likeRelations) {
                Date likeTime = likeRelation.getTime();
                switch (likeRelation.getType()) {
                case Constants.LIKED_NEWS_TYPE:
                    NewsInfoBean likedNews = newsInfoDao.read(likeRelation.getResourceId());
                    if (likedNews != null) {
                        likedNews.setLikeTime(likeTime);
                        toReturn.add(likedNews);
                    }
                    break;
                case Constants.LIKED_MAINPOSTS_TYPE:
                    MainPost likedMainPost = mainpostDao.read(likeRelation.getResourceId());
                    if (likedMainPost != null) {
                        likedMainPost.setLikeTime(likeTime);
                        toReturn.add(likedMainPost);
                    }
                    break;

                default:
                    break;
                }
            }
        }
        return toReturn;
    }

    public List<Favoritable> searchFavoriteResource(String targetUserId, List<Integer> types, String keyword,
            Integer firstResult, Integer maxResults) {
        List<Favoritable> favoritedResources = new ArrayList<Favoritable>();
        MianaouserinfoBean user = findUser(targetUserId);
        if (types == null) {
            types = new ArrayList<Integer>();
        }
        if (types.isEmpty()) {
            setFavoriteResourceType(types);
        }
        for (int type : types) {
            switch (type) {
            case Constants.FAVORITE_NEWS_TYPE:
                List<NewsInfoBean> news = searchFavoriteNews(targetUserId, keyword, -1, -1);
                if (news != null && !news.isEmpty()) {
                    setNewsFavTime(user, news);
                    favoritedResources.addAll(news);
                }
                break;
            case Constants.FAVORITE_MAINPOST_TYPE:
                List<MainPost> mainPosts = searchFavoriteMainPosts(targetUserId, keyword, -1, -1);
                if (mainPosts != null && !mainPosts.isEmpty()) {
                    setMainPostFavTime(user, mainPosts);
                    favoritedResources.addAll(mainPosts);
                }
                break;
            default:
                break;
            }
        }
        Collections.sort(favoritedResources);

        if (firstResult == null || firstResult < 0) {
            firstResult = 0;
        }
        if (maxResults == null || maxResults < 0) {
            maxResults = favoritedResources.size();
        }
           return favoritedResources.subList(firstResult, maxResults);
    }

    public void setMainPostFavTime(MianaouserinfoBean user, List<MainPost> mainPosts) {
        List<FavoriteRelation> favMpRelations = favDao.findUserFavoriteResource(
                Arrays.asList(new Integer[] { Constants.FAVORITE_MAINPOST_TYPE }), user.getId(), -1, -1);
        if (favMpRelations != null && !favMpRelations.isEmpty()) {
            for (MainPost mainPost : mainPosts) {
                for (FavoriteRelation relation : favMpRelations) {
                    if (relation.getResourceId() == mainPost.getId()) {
                        mainPost.setFavTime(relation.getTime());
                    }
                }
            }
        }
    }

    public void setNewsFavTime(MianaouserinfoBean user, List<NewsInfoBean> news) {
        List<FavoriteRelation> favNewsRelations = favDao.findUserFavoriteResource(
                Arrays.asList(new Integer[] { Constants.FAVORITE_NEWS_TYPE }), user.getId(), -1, -1);
        if (favNewsRelations != null && !favNewsRelations.isEmpty()) {
            for (NewsInfoBean favNews : news) {
                for (FavoriteRelation relation : favNewsRelations) {
                    if (relation.getResourceId() == favNews.getId()) {
                        favNews.setFavTime(relation.getTime());
                    }
                }
            }
        }
    }

    private List<NewsInfoBean> searchFavoriteNews(String targetUserId, String keyword, Integer firstResult, Integer maxResults) {
        // toReturn.put("totalCount", newsInfoDao.totalCountSearchedFavNews(userId, keyword));
        return newsInfoDao.searchFavoriteNews(targetUserId, keyword, firstResult, maxResults);
    }

    private List<MainPost> searchFavoriteMainPosts(String targetUserId, String keyword, Integer firstResult, Integer maxResults) {
        return mainpostDao.searchFavoriteMainPost(targetUserId, keyword, firstResult, maxResults);
    }

    public List<NewsInfoBean> findTopBarNews(List<Integer> categories, Integer firstResult, Integer maxResults) {
        if (categories == null || categories.isEmpty()) {
            throw new IllegalArgumentException("The argument categories is required.");
        }
        return newsInfoDao.findTopBarNews(categories, firstResult, maxResults);
    }

    public List<Classification> findClasses(String userId, Integer firstResult, Integer maxResults) {
        List<Classification> classes = classDao.getClasses(firstResult, maxResults);
        logger.info("查询到的类别: " + classes);
        return classes;
    }

    public List<CommunityClassification> findCommunityClasses(Integer firstResult, Integer maxResults) {
        List<CommunityClassification> communityClasses = communityClassDao.findCommunityClasses(firstResult, maxResults);
        logger.info("查询到的社区类别: " + communityClasses.toArray().toString());
        return communityClasses;
    }

    public Map<String, Object> findCommentsByNewsId(String userId, Integer newsId, Integer firstResult, Integer maxResult) {
        Map<String, Object> toReturn = new LinkedHashMap<String, Object>();
        toReturn.put("totalCount", commentDao.totalCommentsCount(newsId));
        List<Comment> comments = commentDao.findCommentByNewsInfoId(newsId, firstResult, maxResult);
        if (comments != null && !comments.isEmpty()) {
            for (Comment commentToHandle : comments) {
                setOwned(userId, commentToHandle);
            }
        }
        toReturn.put("records", comments);
        return toReturn;
    }

    public Comment findCommentById(String userId, Integer commentId) {
        Comment commentToHandle = commentDao.read(commentId);
        setOwned(userId, commentToHandle);
        return commentToHandle;
    }

    public List<Tag> findTagsByClass(String userId, String className, Integer firstResult, Integer maxResults) {

        if (className == null || StringUtils.isBlank(className)) {
            throw new IllegalArgumentException("The argument className required.");
        }
        List<Tag> result = tagDao.findTagsByClass(className, firstResult, maxResults);
        logger.info("findTagsByClass 返回数据：", JsonUtil.toJson(result));
        return result;
    }

    public List<MianaoState> findMianaoStates(String userId) {
        return stateDao.findStates();
    }

    public List<Community> findCommunityByClass(String userId, String className, Integer firstResult, Integer maxResults) {
        return communityDao.findCommunityByClass(className, firstResult, maxResults);
    }

    public Community findCommunityById(String userId, Integer communityId) {
        if (communityId == null || communityId < 0) {
            throw new IllegalArgumentException("The argument communityId is required.");
        }
        Community find = communityDao.read(communityId);
        return find;
    }

    public List<Community> findSubscribedCommunities(String targetUserid, Integer firstResult, Integer maxResults) {
        findUser(targetUserid);
        return communityDao.findSubscribedCommunities(targetUserid, firstResult, maxResults);
    }

    public List<MainPost> findMainPosts(Integer communityId, Integer firstResult, Integer maxResults) {
        return mainpostDao.findMainPostForCommunity(communityId, firstResult, maxResults);
    }

    public MainPost findMainPostById(Integer mainpostId) {
        return mainpostDao.read(mainpostId);
    }

    public List<MainPost> findUserConcernedMainPosts(Integer typeCode, String targetUserid, Integer firstResult,
            Integer maxResults) {
        findUser(targetUserid);
        if (typeCode == null || typeCode < 0 || typeCode > 2) {
            throw new IllegalArgumentException("The parameter typeCode is invalid.");
        }
        switch (typeCode) {
        case Constants.REPLIED_MAINPOSTS_TYPE:
            return mainpostDao.findRepliedMainPost(targetUserid, firstResult, maxResults);
        case Constants.CREATED_MAINPOSTS_TYPE:
            return mainpostDao.findCreatedMainPost(targetUserid, firstResult, maxResults);
        default:
            break;
        }
        return null;
    }

    public List<MainPost> findDigestMainPosts(int communityId, Integer firstResult, Integer maxResults) {
        return mainpostDao.findDigestMainPosts(communityId, firstResult, maxResults);
    }

    public List<Post> findPosts(String userId, Integer mainpostId, Integer firstResult, Integer maxResults) {
        if (mainpostId == null || mainpostId < 0) {
            throw new IllegalArgumentException("The parameter mainpostId is invalide");
        }
        return postDao.findPostForMainPost(mainpostId, firstResult, maxResults);
    }
    
    public Post findPostById(Integer postId){
        return postDao.read(postId);
    }

    public List<CommunityTag> findAllCommunityTags() {
        return communityTagDao.findAllCommunityTags();
    }

    public MianaouserinfoBean findUserById(String userId) {
        return userDao.findUserByUserId(userId);
    }

    public List<MianaouserinfoBean> findUserList(Integer typeCode, String targetUserid, Integer firstResult, Integer maxResults) {
        findUser(targetUserid);
        if (typeCode == null || typeCode < 0 || typeCode > 2) {
            throw new IllegalArgumentException("The parameter typeCode is invalid.");
        }
        switch (typeCode) {
        case Constants.FOLLOWER_TYPE:
            return userDao.findFollowers(targetUserid, firstResult, maxResults);
        case Constants.FRIEND_TYPE:
            return userDao.findFriends(targetUserid, firstResult, maxResults);
        default:
            break;
        }
        return null;
    }
}
