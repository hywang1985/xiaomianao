package com.jianfeng.xiaomianao.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jianfeng.xiaomianao.domain.AuthenticationToken;


public class AuthenticationTokenServiceTests extends AbstractServiceTests{

    @Autowired
    private AuthenticationTokenService auService;
    
    @Test
    public void testCreateToken(){
        AuthenticationToken createdToken = auService.createToken("100008");
    }
    
    @Test 
    public void testGetTokenByUserId(){
        AuthenticationToken retrievedToken = auService.getTokenByUserid("100008");
        Assert.assertNotNull(retrievedToken);
    }
}
