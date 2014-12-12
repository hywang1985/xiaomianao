package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.Tag;

@Repository
public class TagDao extends AbstractDao<Tag,Integer> {

    private Logger logger = LoggerFactory.getLogger(TagDao.class);

    private static final String GET_TAGS_BY_CLASS = "select t from Tag t join t.classes c where c.name = (:className) order by t.id asc";
    
    private static final String GET_TAG_BY_NAME = "select t from Tag t where t.name in (:tags) order by t.id";

    public List<Tag> findTagsByClass(String className, Integer firstResult, Integer maxResults) {
        logger.info("根据分类查询标签,分类名称:{}", new String[] { className });
        TypedQuery<Tag> query = entityManager.createQuery(GET_TAGS_BY_CLASS, Tag.class).setParameter("className", className);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
    
    public List<Tag> findTagsByName(List<String> tags){
        logger.info("根据名称查询标签,标签名称:{}", new String[] { tags.toString() });
        return entityManager.createQuery(GET_TAG_BY_NAME, Tag.class).setParameter("tags", tags).getResultList();
    }
}
