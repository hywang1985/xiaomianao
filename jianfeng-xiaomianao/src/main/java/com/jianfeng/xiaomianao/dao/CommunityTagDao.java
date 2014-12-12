package com.jianfeng.xiaomianao.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.CommunityTag;

@Repository
public class CommunityTagDao extends AbstractDao<CommunityTag, Integer> {

    private Logger logger = LoggerFactory.getLogger(CommunityTag.class);

    private static final String FIND_TAG_BY_NAME = "select t from CommunityTag t where t.name in (:tags) order by t.id";
    
    private static final String FIND_ALL_TAGS = "select t from CommunityTag t";

    public List<CommunityTag> findTagsByName(List<String> tags) {
        logger.info("根据名称查询社区标签,标签名称:{}", new String[] { tags.toString() });
        return entityManager.createQuery(FIND_TAG_BY_NAME, CommunityTag.class).setParameter("tags", tags).getResultList();
    }
    
    public List<CommunityTag> findAllCommunityTags(){
        logger.info("查询社区标签列表");
        return entityManager.createQuery(FIND_ALL_TAGS, CommunityTag.class).getResultList();
    }
}
