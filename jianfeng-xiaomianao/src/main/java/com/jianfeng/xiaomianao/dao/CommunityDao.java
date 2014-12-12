package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jianfeng.xiaomianao.domain.Community;


@Component
public class CommunityDao extends AbstractDao<Community, Integer> {
    
    private Logger logger = LoggerFactory.getLogger(CommunityDao.class);
    
    private static final String FIND_COMMUNITIES_BY_CLASS = "select c from Community c where c.className = (:className)";
    
    private static final String FIND_COMMUNITIES_BY_IDS = "select c from Community c where c.id in (:communityIds)";
    
    private static final String FIND_SUBSCRIBED_COMMUNITIES = "select c from Community c join c.subscribers s where s.userid = (:userId)";
    
    public List<Community> findCommunityByClass(String className, Integer firstResult, Integer maxResults){
        logger.info("根据分类查询社区,分类名称:{}", new String[] { className });
        TypedQuery<Community> query = entityManager.createQuery(FIND_COMMUNITIES_BY_CLASS, Community.class).setParameter("className", className);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
    
    public List<Community> findCommunityByIds(List<Integer> communityIds){
        logger.info("根据IDS查询社区,Ids:{}", new String[] { communityIds.toArray().toString() });
        return entityManager.createQuery(FIND_COMMUNITIES_BY_IDS, Community.class).setParameter("communityIds", communityIds).getResultList();
    }
    
    public List<Community> findSubscribedCommunities(String userId, Integer firstResult, Integer maxResults){
        TypedQuery<Community> query = entityManager.createQuery(FIND_SUBSCRIBED_COMMUNITIES, Community.class).setParameter("userId", userId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
}
