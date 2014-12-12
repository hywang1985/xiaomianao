package com.jianfeng.xiaomianao.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.Comment;

@Repository
public class CommentDao extends AbstractDao<Comment,Integer> {

    private Logger logger = LoggerFactory.getLogger(CommentDao.class);

    private static final String GET_COUNT_FOR_COMMENTS_IN_NEWS = "select count(c) from Comment c where c.news.id = (:newsId)";
    
    private static final String GET_BY_NEWS_INFO_ID = "select c from Comment c where c.news.id = (:newsId) order by c.createtime asc";
    
   
    public Long totalCommentsCount(int newsId) {
        logger.info("根据NewsInfoId查询:{}", new Integer[] { newsId });
        return (Long)entityManager.createQuery(GET_COUNT_FOR_COMMENTS_IN_NEWS).setParameter("newsId", newsId).getSingleResult();
    }
    
    public List<Comment> findCommentByNewsInfoId(int newsId, Integer firstResult, Integer maxResult) {
        logger.info("根据NewsInfoId查询:{}", new Integer[] { newsId });
        return entityManager.createQuery(GET_BY_NEWS_INFO_ID, Comment.class).setParameter("newsId", newsId)
                .setFirstResult(firstResult).setMaxResults(maxResult).getResultList();
    }

}
