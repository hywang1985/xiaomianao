package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.LikeRelation;

@Repository
public class LikeRelationDao extends AbstractDao<LikeRelation, Integer> {

    private static final String FIND_LIKE_REALTION = "select l from LikeRelation l where l.userId=(:userId) and l.type=(:type) and l.resourceId=(:resourceId)";

    private static final String FIND_USER_LIKED_RESOURCES = "select l from LikeRelation l where l.userId = (:userId) and l.type in (:types) order by l.time desc";

    public LikeRelation findLikeRelation(int type, long userId, int resourceId) {
        List<LikeRelation> resultList = entityManager.createQuery(FIND_LIKE_REALTION, LikeRelation.class)
                .setParameter("type", type).setParameter("userId", userId).setParameter("resourceId", resourceId).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<LikeRelation> findUserLikedResource(List<Integer> types, long userId, Integer firstResult,
            Integer maxResults) {
        TypedQuery<LikeRelation> query = entityManager.createQuery(FIND_USER_LIKED_RESOURCES, LikeRelation.class)
                .setParameter("userId", userId).setParameter("types", types);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

}
