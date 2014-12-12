package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.CommunityClassification;

@Repository
public class CommunityClassificationDao extends AbstractDao<CommunityClassification, Integer> {

    private Logger logger = LoggerFactory.getLogger(CommunityClassificationDao.class);

    private static final String FIND_ALL_COMMUNITY_CLASSES = "select c from CommunityClassification c";


    public List<CommunityClassification> findCommunityClasses(Integer firstResult, Integer maxResults) {
        logger.info("查询所有社区分类,from: " + firstResult + "  to: " + maxResults);
        TypedQuery<CommunityClassification> query = entityManager.createQuery(FIND_ALL_COMMUNITY_CLASSES, CommunityClassification.class);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
}
