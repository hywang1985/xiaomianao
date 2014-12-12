package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.CommunityTag;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.Post;

@Repository
public class PostDao extends AbstractDao<Post, Integer> {

    private Logger logger = LoggerFactory.getLogger(CommunityTag.class);

    private static final String FIND_POSTS_FROM_MAINPOST = "select p from Post p where p.mainpostId=(:mainpostId) order by p.createtime asc";

    private static final String FIND_MAINPOSTS_BY_IDS = "select p from Post p where p.id in (:postIds) order by p.createtime desc";
    
    private static final String FIND_LAST_POST = "select p from Post p where p.mainpostId=(:mainpostId) order by p.createtime desc";

    public List<Post> findPostForMainPost(int mainpostId, Integer firstResult, Integer maxResults) {
        logger.info("查询评论,主贴id:{}", new int[] { mainpostId });
        TypedQuery<Post> query = entityManager.createQuery(FIND_POSTS_FROM_MAINPOST, Post.class).setParameter("mainpostId",
                mainpostId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

    public List<Post> findPostsByIds(List<Integer> postIds) {
        logger.info("查询评论,postIds:{}", new String[] { postIds.toString() });
        List<Post> resultList = entityManager.createQuery(FIND_MAINPOSTS_BY_IDS, Post.class).setParameter("postIds", postIds)
                .getResultList();
        return resultList;
    }
    
    public Post findLastMainPost(int mainpostId) {
        List<Post> resultList = entityManager.createQuery(FIND_LAST_POST, Post.class).setParameter("mainpostId", mainpostId)
                .setFirstResult(0).setMaxResults(1).getResultList();
        if (resultList != null && resultList.size() > 0) {
            return resultList.get(0);
        }
        return null;
    }
}
