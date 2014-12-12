package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.CommunityTag;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;

@Repository
public class MainPostDao extends AbstractDao<MainPost, Integer> {

    private Logger logger = LoggerFactory.getLogger(CommunityTag.class);

    private static final String FIND_MAINPOSTS_FOR_COMMUNITY = "select mp from MainPost mp where mp.communityId=(:communityId) order by mp.createtime desc";

    private static final String FIND_DIGEST_MAINPOST = "select mp from MainPost mp where mp.digestType in (1,2,3,4) and mp.communityId=(:communityId) and mp.degest = true order by mp.createtime desc";

    private static final String FIND_LAST_MAINPOST = "select mp from MainPost mp where mp.communityId=(:communityId) order by mp.createtime desc";

    private static final String FIND_MAINPOSTS_BY_IDS = "select mp from MainPost mp where mp.id in (:mainpostIds) order by mp.createtime desc";

    private static final String FIND_REPLIED_MAINPOST = "select mp from MainPost mp where mp.id in "
            + "(select p.mainpostId from Post p join p.user u where u.userid = (:userId)) order by mp.createtime desc";

    private static final String FIND_LIKED_MAINPOST = "select mp from MainPost mp join mp.mpLikers l where l.userid = (:userId) order by mp.createtime desc";

    private static final String FIND_CREATED_MAINPOST = "select mp from MainPost mp where mp.user.userid = (:userId) order by mp.createtime desc";
    
    private static final String SEARCH_FAVORITE_MAINPOST= "select mp from MainPost mp join mp.favoritors f left join mp.tags t "
            + "where (t.name like (:keyword) or mp.briefContent like (:keyword) or mp.title like (:keyword) or mp.mainPostContent like (:keyword)) "
            + "and f.userid = (:userid) order by mp.createtime desc";
    
    private static final String FIND_FAVORITE_MAINPOST = "select mp from MainPost mp join mp.favoritors f where f.userid=(:userId) order by mp.createtime desc";


    public List<MainPost> findMainPostForCommunity(int communityId, Integer firstResult, Integer maxResults) {
        logger.info("查询社区主贴,社区id:{}", new int[] { communityId });
        TypedQuery<MainPost> query = entityManager.createQuery(FIND_MAINPOSTS_FOR_COMMUNITY, MainPost.class).setParameter(
                "communityId", communityId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

    public List<MainPost> findDigestMainPosts(int communityId, Integer firstResult, Integer maxResults) {
        logger.info("查询社区置顶帖,社区id:{}", new int[] { communityId });
        TypedQuery<MainPost> query = entityManager.createQuery(FIND_DIGEST_MAINPOST, MainPost.class).setParameter("communityId",
                communityId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

    public MainPost findLastMainPost(int communityId) {
        List<MainPost> resultList = entityManager.createQuery(FIND_LAST_MAINPOST, MainPost.class)
                .setParameter("communityId", communityId).setFirstResult(0).setMaxResults(1).getResultList();
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }

    public List<MainPost> findMainPostsByIds(List<Integer> mainpostIds) {
        logger.info("查询主贴,mainpostIds:{}", new String[] { mainpostIds.toString() });
        List<MainPost> resultList = entityManager.createQuery(FIND_MAINPOSTS_BY_IDS, MainPost.class)
                .setParameter("mainpostIds", mainpostIds).getResultList();
        return resultList;
    }

    public List<MainPost> findRepliedMainPost(String userId, Integer firstResult, Integer maxResults) {
        logger.info("查询用户回复过的主贴,userId:{},firstResult:{},maxResults:{}", new String[] { userId, firstResult!=null?firstResult.toString():"0",
                maxResults!=null?maxResults.toString():"all" });
        TypedQuery<MainPost> query = entityManager.createQuery(FIND_REPLIED_MAINPOST, MainPost.class).setParameter("userId",
                userId);
        setPageParameters(query, firstResult, maxResults);
        List<MainPost> resultList = query.getResultList();
        return resultList;
    }

    public List<MainPost> findLikedMainPost(String userId, Integer firstResult, Integer maxResults) {
        logger.info("查询用户赞过的主贴,userId:{},firstResult:{},maxResults:{}", new String[] { userId, firstResult!=null?firstResult.toString():"0",
                maxResults!=null?maxResults.toString():"all" });
        TypedQuery<MainPost> query = entityManager.createQuery(FIND_LIKED_MAINPOST, MainPost.class)
                .setParameter("userId", userId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

    public List<MainPost> findCreatedMainPost(String userId, Integer firstResult, Integer maxResults) {
        logger.info("查询用户创建过的主贴,userId:{},firstResult:{},maxResults:{}", new String[] { userId, firstResult!=null?firstResult.toString():"0",
                maxResults!=null?maxResults.toString():"all" });
        TypedQuery<MainPost> query = entityManager.createQuery(FIND_CREATED_MAINPOST, MainPost.class).setParameter("userId",
                userId);
        setPageParameters(query, firstResult, maxResults);
        List<MainPost> resultList = query.getResultList();
        return resultList;
    }
    
    @SuppressWarnings("unchecked")
    public List<MainPost> searchFavoriteMainPost(String userId, String keyword, Integer firstResult, Integer maxResults) {
        logger.info("搜索收藏的主贴，用户ID：", userId);
        Query query = entityManager.createQuery(SEARCH_FAVORITE_MAINPOST).setParameter("userid", userId)
                .setParameter("keyword", "%" + keyword + "%");
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
    
    @SuppressWarnings("unchecked")
    public List<MainPost> findFavoriteMainPost(String userId,Integer firstResult, Integer maxResults) {
        logger.info("搜索收藏的主贴，用户ID：", userId);
        Query query = entityManager.createQuery(FIND_FAVORITE_MAINPOST).setParameter("userId", userId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
}
