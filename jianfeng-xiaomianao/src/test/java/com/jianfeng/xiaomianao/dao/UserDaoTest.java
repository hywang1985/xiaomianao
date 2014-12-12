package com.jianfeng.xiaomianao.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.domain.MianaouserinfoBean;


public class UserDaoTest extends AbstractDaoTests{

    private static final int COMMUNITY_ID = 1;
    
    @Autowired
    private UserDao userDao;
    
    @Test
    public void testFindSuperFans(){
       List<MianaouserinfoBean> results =  userDao.findAdvanceFansForCommunity(COMMUNITY_ID);
        Assert.assertNotNull(results);
    }
}
