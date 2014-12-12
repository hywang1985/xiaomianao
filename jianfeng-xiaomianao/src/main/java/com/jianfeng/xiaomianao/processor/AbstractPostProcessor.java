package com.jianfeng.xiaomianao.processor;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.dao.MainPostDao;
import com.jianfeng.xiaomianao.dao.PostDao;
import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.MainPost;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.domain.Post;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

public class AbstractPostProcessor<R, T> implements IPostProcessor<R, T> {

    @Autowired
    protected UserDao userDao;
    
    @Autowired
    protected PostDao postDao;
    
    @Autowired
    protected MainPostDao mainpostDao;
    
    @Override
    public R postProcess(ClientRequest clientRequest, T processedResult, String userId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    protected void setOwned(String userId, Post postToHandle) {

        if (userId == null || StringUtils.isBlank(userId)) {
            postToHandle.setOwned(false);
        } else {
            postToHandle.setOwned(postToHandle.getUser().getUserid().equals(userId));
        }
    }

    protected void setParentPost(String userId, Post post) {
        int parentId = post.getParentId();
        if (parentId > 0) {
            Post parentPost = postDao.read(parentId);
            setMainPostOwner(parentPost);
            setOwned(userId, parentPost);
            post.setParentPost(parentPost);
        }
    }

    protected void setMainPostOwner(Post postToHandle) {
        MainPost mainPost = mainpostDao.read(postToHandle.getMainpostId());
        postToHandle.setMainPostOwner(mainPost.getUser().getUserid().equals(postToHandle.getUser().getUserid()));
    }
    
    protected void setLiked(String userId, Post postToHandle) {
        Set<MianaouserinfoBean> likers = null;
        if(postToHandle instanceof MainPost){
            likers = ((MainPost)postToHandle).getMpLikers();
        }else{
            likers = postToHandle.getLikers();
        }
        boolean isLiked = isLikedBySpeificUser(likers, userId);
        postToHandle.setLiked(isLiked);
    }
    
    
    protected boolean isFavoritedBySpecificUser(Set<MianaouserinfoBean> favoritors, String userId) {
        if (userId == null || StringUtils.isBlank(userId)) {
            return false;
        }
        boolean find = false;
        if (favoritors != null && !favoritors.isEmpty()) {
            Iterator<MianaouserinfoBean> favoritorsIt = favoritors.iterator();
            while (favoritorsIt.hasNext()) {
                MianaouserinfoBean favoritor = favoritorsIt.next();
                if (favoritor.getUserid().equals(userId)) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }

    protected void setFavorited(String userId, MainPost mainPost) {
        Set<MianaouserinfoBean> favoritors = mainPost.getFavoritors();
        boolean isFavorited = isFavoritedBySpecificUser(favoritors, userId);
        mainPost.setFavorited(isFavorited);
    }

    // For every returned Post/MainPost,find if is liked by current user via userid.
    protected boolean isLikedBySpeificUser(Set<MianaouserinfoBean> likers, String userId) {
        if (userId == null || StringUtils.isBlank(userId)) {
            return false;
        }
        boolean find = false;
        if (likers != null && !likers.isEmpty()) {
            Iterator<MianaouserinfoBean> likersIt = likers.iterator();
            while (likersIt.hasNext()) {
                MianaouserinfoBean liker = likersIt.next();
                if (liker.getUserid().equals(userId)) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }

}
