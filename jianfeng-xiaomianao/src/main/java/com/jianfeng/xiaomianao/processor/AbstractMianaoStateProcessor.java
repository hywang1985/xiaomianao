package com.jianfeng.xiaomianao.processor;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.jianfeng.xiaomianao.domain.MianaoState;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.handler.dto.ClientRequest;


public class AbstractMianaoStateProcessor<R,T> implements IPostProcessor<R, T> {

    @Override
    public R postProcess(ClientRequest clientRequest, T processedResult, String userId) throws Exception {
        return null;
    }
    
    protected void setOwned(String userId, MianaoState stateToHandle) {
        Set<MianaouserinfoBean> owners = stateToHandle.getOwners();
        boolean isOwned = isOwnedBySpecificUser(owners, userId);
        stateToHandle.setOwned(isOwned);
    }

    // For every returned MianaoState,find if is owned by current user via userid.
    protected boolean isOwnedBySpecificUser(Set<MianaouserinfoBean> owners, String userId) {
        if (userId == null || StringUtils.isBlank(userId)) {
            return false;
        }
        boolean find = false;
        if (owners != null && !owners.isEmpty()) {
            Iterator<MianaouserinfoBean> ownersIt = owners.iterator();
            while (ownersIt.hasNext()) {
                MianaouserinfoBean owner = ownersIt.next();
                if (owner.getUserid().equals(userId)) {
                    find = true;
                    break;
                }
            }
        }
        return find;
    }

}
