package com.jianfeng.xiaomianao.processor;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.NewsInfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;

public class AbstractNewsInfoProcessor<R, T> implements IPostProcessor<R, T> {

    @Override
    public R postProcess(ClientRequest clientRequest, T processedResult, String userId) throws Exception {
        return null;
    }

    // For every returned news,find if is favorited by current user via userid.
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

    protected void setFavorited(String userId, NewsInfoBean newsToHandle) {
        Set<MianaouserinfoBean> favoritors = newsToHandle.getFavoritors();
        boolean isFavorited = isFavoritedBySpecificUser(favoritors, userId);
        newsToHandle.setFavorited(isFavorited);
    }
    
    protected void setLiked(String userId, NewsInfoBean newsToHandle) {
        Set<MianaouserinfoBean> likers = newsToHandle.getLikers();
        boolean isLiked = isLikedBySpeificUser(likers, userId);
        newsToHandle.setLiked(isLiked);
    }

    // For every returned news,find if is liked by current user via userid.
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
