package com.jianfeng.xiaomianao.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.dao.PushPreferenceDao;
import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.Comment;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.exception.XiaoMianAoException;
import com.jianfeng.xiaomianao.util.ErrorCode;

public class AbstractUserNeededService {

    @Autowired
    protected UserDao userDao;
    
    @Autowired
    protected PushPreferenceDao preferenceDao;
    
    protected Logger logger = LoggerFactory.getLogger(AbstractUserNeededService.class);

    protected MianaouserinfoBean findUser(String userId) {
        MianaouserinfoBean user = null;
        if (userId == null || StringUtils.isBlank(userId)) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        user = userDao.findUserByUserId(userId);
        if (user == null) {
            throw new XiaoMianAoException(ErrorCode.USER_NOT_EXIST);
        }
        return user;
    }

    protected void setOwned(String userId, Comment commentToHandle) {

        if (userId == null || StringUtils.isBlank(userId)) {
            commentToHandle.setOwned(false);
        } else {
            commentToHandle.setOwned(commentToHandle.getOwner().getUserid().equals(userId));
        }

    }

    protected void validateInternalParameter(String parameterName, String parameter) {
        if (parameter == null || StringUtils.isBlank(parameter)) {
            XiaoMianAoException xiaoMianAoException = new XiaoMianAoException(ErrorCode.INTERNERL_PARAMETER_INVALID);
            xiaoMianAoException.setErrorValue("Parameter " + parameterName + " is invalid, make sure it's not empty or null.");
            throw xiaoMianAoException;
        }
    }
}
