package com.jianfeng.xiaomianao.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author kailong
 * 
 * @updated hywang
 * */
public class AbstractDao<T, E> {

    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public T create(T t) {
        this.entityManager.persist(t);
        return t;
    }

    public T read(E id) {
        return this.entityManager.find(entityClass, id);
    }

    public T update(T t) {
        return this.entityManager.merge(t);
    }

    public void delete(T t) {
        t = this.entityManager.merge(t);
        this.entityManager.remove(t);
    }
    
    protected void setPageParameters(Query query, Integer firstResult, Integer maxResults) {
        
        if (firstResult!=null && firstResult > 0) {
            query.setFirstResult(firstResult);
        }
        if (maxResults!=null && maxResults > 0) {
            query.setMaxResults(maxResults);
        }
    }
}
