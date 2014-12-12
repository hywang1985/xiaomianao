package com.jianfeng.xiaomianao.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.NewsInfoBean;

@Repository
public class NewsInfoDao extends AbstractDao<NewsInfoBean, Integer> {

    private static final String FIND_NEWS_BY_CATEGORY = "SELECT o FROM NewsInfoBean o where o.category=? and o.state=? order by o.updatetime desc";

    private Logger logger = LoggerFactory.getLogger(NewsInfoDao.class);

    private static final String GET_NEWS_BY_TAGS = "select distinct n from NewsInfoBean n join n.tags t "
            + "where t.name in (:tags) and n.state=(:state) order by n.updatetime desc";

    private static final String GET_COUNT_NEWS_BY_TAGS = "select count(distinct n) from NewsInfoBean n join n.tags t "
            + "where t.name in (:tags) and n.state=(:state) order by n.updatetime desc";

    private static final String GET_NEWS_BY_IDS = "select n from NewsInfoBean n where n.id in (:newsIds) and n.state=(:state)";

    private static final String GET_FAVORITE_NEWS = "select n from NewsInfoBean n join n.favoritors f "
            + "where f.userid = (:userid) and n.state=(:state)";

    private static final String SEARCH_FAVORITE_NEWS = "select n from NewsInfoBean n join n.favoritors f "
            + "join n.tags t where (t.name like (:keyword)  or n.title like (:keyword) " + "or n.briefContent like (:keyword))"
            + "and f.userid = (:userid) and n.state=(:state)";

    private static final String GET_COUNT_FOR_SEARCHED_FAVORITE_NEWS = "select count(n) from NewsInfoBean n join n.favoritors f "
            + "join n.tags t where (t.name like (:keyword)  or n.title like (:keyword) or n.briefContent like (:keyword))"
            + "and f.userid = (:userid) and n.state=(:state)";

    private static final String GET_TOP_BAR_NEWS = "select n from NewsInfoBean n where n.category in (:categories) and n.onTop = 1 and n.state=(:state)";

    public NewsInfoBean findNewsById(int id) {
        logger.info("查询新闻内容,newid:{}", new String[] { Integer.toString(id) });
        List<NewsInfoBean> resultList = entityManager
                .createQuery("select o from NewsInfoBean o where o.id=?", NewsInfoBean.class).setParameter(1, id).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<NewsInfoBean> findNewsByIds(List<Integer> newsIds) {
        logger.info("查询新闻内容,newsIds:{}", new String[] { newsIds.toString() });
        List<NewsInfoBean> resultList = entityManager.createQuery(GET_NEWS_BY_IDS, NewsInfoBean.class)
                .setParameter("newsIds", newsIds).setParameter("state", "1").getResultList();
        return resultList;
    }

    public List<NewsInfoBean> findNews(int category, Integer firstResult, Integer maxResults) {
        logger.info("查询新闻内容,category:{}", new String[] { Integer.toString(category) });
        TypedQuery<NewsInfoBean> query = entityManager.createQuery(
                FIND_NEWS_BY_CATEGORY, NewsInfoBean.class).setParameter(1, category).setParameter(2, "1");
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

    public Long totalCountForTaggedNews(List<String> tags) {
        return (Long) entityManager.createQuery(GET_COUNT_NEWS_BY_TAGS).setParameter("tags", tags).setParameter("state", "1")
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<NewsInfoBean> findNewsByTags(List<String> tags, Integer firstResult, Integer maxResults) {
        logger.info("根据标签查询新闻，tags:{}", tags.toArray());
        Query query = entityManager.createQuery(GET_NEWS_BY_TAGS).setParameter("tags", tags)
                .setParameter("state", "1");
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<NewsInfoBean> findFavoriteNews(String userId, Integer firstResult, Integer maxResults) {
        logger.info("查询用户收藏的文章");
        Query query = entityManager.createQuery(GET_FAVORITE_NEWS).setParameter("userid", userId)
                .setParameter("state", "1");
        setPageParameters(query, firstResult, maxResults);
        List<NewsInfoBean> resultList = query.getResultList();
        return resultList;
    }

    public Long totalCountSearchedFavNews(String userId, String keyword) {
        return (Long) entityManager.createQuery(GET_COUNT_FOR_SEARCHED_FAVORITE_NEWS).setParameter("userid", userId)
                .setParameter("state", "1").setParameter("keyword", "%" + keyword + "%").getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<NewsInfoBean> searchFavoriteNews(String userId, String keyword, Integer firstResult, Integer maxResults) {
        logger.info("搜索收藏文章，用户ID：", userId);
        Query query = entityManager.createQuery(SEARCH_FAVORITE_NEWS).setParameter("userid", userId)
                .setParameter("state", "1").setParameter("keyword", "%" + keyword + "%");
        setPageParameters(query, firstResult, maxResults);
        return  query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<NewsInfoBean> findTopBarNews(List<Integer> categories, Integer firstResult, Integer maxResults) {
        logger.info("搜索置顶文章，categories：{},firstResult:{},maxResults:{}",
                new String[] { categories.toString(), Integer.toString(firstResult), Integer.toString(maxResults) });
        Query query = entityManager.createQuery(GET_TOP_BAR_NEWS).setParameter("categories", categories)
                .setParameter("state", "1");
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
    
}
