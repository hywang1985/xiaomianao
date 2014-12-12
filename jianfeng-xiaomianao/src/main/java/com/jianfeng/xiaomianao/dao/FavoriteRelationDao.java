package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.FavoriteRelation;

@Repository
public class FavoriteRelationDao extends AbstractDao<FavoriteRelation, Integer> {

    private static final String FIND_FAVORITE_REALTION = "select r from FavoriteRelation r where r.userId=(:userId) and r.type=(:type) and r.resourceId=(:resourceId)";

    private static final String FIND_USER_FAVORITED_RESOURCES = "select r from FavoriteRelation r where r.userId = (:userId) and r.type in (:types) order by r.time desc";

    public FavoriteRelation findFavoriteRelation(int type, long userId, int resourceId) {
        List<FavoriteRelation> resultList = entityManager.createQuery(FIND_FAVORITE_REALTION, FavoriteRelation.class)
                .setParameter("type", type).setParameter("userId", userId).setParameter("resourceId", resourceId).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<FavoriteRelation> findUserFavoriteResource(List<Integer> types, long userId, Integer firstResult,
            Integer maxResults) {
        TypedQuery<FavoriteRelation> query = entityManager.createQuery(FIND_USER_FAVORITED_RESOURCES, FavoriteRelation.class)
                .setParameter("userId", userId).setParameter("types", types);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

}
