package com.jianfeng.xiaomianao.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.Post;

@Repository
public class UserDao extends AbstractDao<MianaouserinfoBean, Long> {

    private static Logger logger = LoggerFactory.getLogger(UserDao.class);

    private static final String FIND_USER_BY_USERID = "select u from MianaouserinfoBean u where u.userid=(:userId)";

    private static final String FIND_USER_BY_USERNAME = "select u from MianaouserinfoBean u where u.username=(:userName)";
    
    private static final String FIND_ADVANCE_FANS_FOR_COMMUNITY = "select new com.jianfeng.xiaomianao.dao.AdvanceFansResult(mp.user.id,count(mp.user.id)) from MainPost as mp "
            + "where mp.communityId=(:communityId) group by mp.user.id order by count(mp.user.id) desc";
    
    private static final String FIND_FOLLOWERS = "select u from MianaouserinfoBean u join u.friends f where f.userid=(:userId) order by u.createtime desc";
    
    private static final String FIND_FRIENDS = "select u from MianaouserinfoBean u join u.followers f where f.userid=(:userId) order by u.createtime desc";
    
    /**
     * @Deprecated No need to retrieve user from database via token. The proper way is firstly retrieve userId via
     * token,then get the user from database via the userId.
     */
    @Deprecated
    public MianaouserinfoBean findUser(String token, String channel) {
        logger.info("查询用户的基本信息, token:{},udid:{},channel:{},version:{}", new String[] { token, channel });
        List<MianaouserinfoBean> resultList = entityManager
                .createQuery("select o from MianaouserinfoBean o where o.channel=? and o.token=?", MianaouserinfoBean.class)
                .setParameter(1, channel).setParameter(2, token).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public MianaouserinfoBean findUserByUserId(String userId) {
        List<MianaouserinfoBean> resultList = entityManager.createQuery(FIND_USER_BY_USERID, MianaouserinfoBean.class)
                .setParameter("userId", userId).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public MianaouserinfoBean findUserByUserName(String userName) {
        List<MianaouserinfoBean> resultList = entityManager.createQuery(FIND_USER_BY_USERNAME, MianaouserinfoBean.class)
                .setParameter("userName", userName).getResultList();
        if (resultList == null || resultList.isEmpty() == true) {
            return null;
        } else {
            return resultList.get(0);
        }
    }
    
    
    public List<MianaouserinfoBean> findAdvanceFansForCommunity(int communityId) {
        List<MianaouserinfoBean> advFans = null;
        TypedQuery<AdvanceFansResult> query = entityManager.createQuery(FIND_ADVANCE_FANS_FOR_COMMUNITY, AdvanceFansResult.class);
        List<AdvanceFansResult> resultList = query.setParameter("communityId", communityId).getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            advFans = new ArrayList<MianaouserinfoBean>();
            for (AdvanceFansResult result : resultList) {
                long userId = result.getUserid();
                advFans.add(this.read(userId));
            }
        }
        return advFans;
    }
    
    public List<MianaouserinfoBean> findFollowers(String userId, Integer firstResult, Integer maxResults){
        logger.info("查找粉丝,用户id:{}", new String[] { userId });
        TypedQuery<MianaouserinfoBean> query = entityManager.createQuery(FIND_FOLLOWERS, MianaouserinfoBean.class).setParameter("userId",
                userId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }
    
    public List<MianaouserinfoBean> findFriends(String userId, Integer firstResult, Integer maxResults){
        logger.info("查找好友,用户id:{}", new String[] { userId });
        TypedQuery<MianaouserinfoBean> query = entityManager.createQuery(FIND_FRIENDS, MianaouserinfoBean.class).setParameter("userId",
                userId);
        setPageParameters(query, firstResult, maxResults);
        return query.getResultList();
    }

}
