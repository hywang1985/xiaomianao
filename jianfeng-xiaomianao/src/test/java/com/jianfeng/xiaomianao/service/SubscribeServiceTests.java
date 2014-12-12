package com.jianfeng.xiaomianao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.dao.UserDao;
import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;
import com.jianfeng.xiaomianao.domain.Tag;

// @TransactionConfiguration(defaultRollback=false)
public class SubscribeServiceTests extends AbstractServiceTests {

    private static final String TAG_OF_NO1 = "私处";

    private static final String TAG_OF_NO2 = "经期";
    
    private static List<String> tagNames = new ArrayList<String>();

    @Autowired
    private SubscribeService subService;

    @Autowired
    private UserDao userDao;

    @Before
    public void resetParameters(){
        tagNames.clear();
    }
    @Test
    public void testSubscribeTags() {
        tagNames.add(TAG_OF_NO1);
        tagNames.add(TAG_OF_NO2);
        subService.subscribeTags(USER_ID, tagNames);
        MianaouserinfoBean user = userDao.findUserByUserId(USER_ID);
        Set<Tag> subscribedTags = user.getSubscribedTags();
        Assert.assertEquals(2, subscribedTags.size());
    }

    @Test
    public void testUnsubscribeTags() {
        tagNames.add(TAG_OF_NO1);
        tagNames.add(TAG_OF_NO2);
        subService.unsubscribeTags(USER_ID, tagNames);
        MianaouserinfoBean user = userDao.findUserByUserId(USER_ID);
        Set<Tag> subscribedTags = user.getSubscribedTags();
        Assert.assertEquals(0, subscribedTags.size());
    }
}
