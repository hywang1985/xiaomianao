package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.Classification;

@Repository
public class ClassificationDao extends AbstractDao<Classification,Integer> {

    private Logger logger = LoggerFactory.getLogger(ClassificationDao.class);

    private static final String GET_CLASSES = "select c from Classification c order by c.id";
    
    private static final String FIND_CLASSES_BY_IDS = "select c from Classification c where c.id in (:classIds) order by c.id";

    public List<Classification> getClasses(Integer firstResult, Integer maxResults) {
        logger.info("查询所有分类,from: " + firstResult + "  to: " + maxResults);
        TypedQuery<Classification> query = entityManager.createQuery(GET_CLASSES, Classification.class);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
    
    public List<Classification> findClassesByIds(List<Integer> classIds,Integer firstResult, Integer maxResults) {
        logger.info("查询分类,from: " + firstResult + "  to: " + maxResults);
        TypedQuery<Classification> query = entityManager.createQuery(FIND_CLASSES_BY_IDS, Classification.class).setParameter("classIds", classIds);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
}
