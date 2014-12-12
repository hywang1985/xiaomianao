package com.jianfeng.xiaomianao.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTest extends AbstractServiceTests {

    private static String TOKEN = "";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationTokenService tokenService;

    @Test
    public void testRegisterUser() {
        String token = userService.register("wanghaoyu", "123456", "102","imei1");
        Assert.assertNotNull(token);
    }

    @Test
    public void testLogin() {
        String token = userService.login("wanghaoyu", "123456","imei1");
        TOKEN = token;
        Assert.assertNotNull(token);
    }

    @Test
    public void testLogout() {
        String userId =userService.logout(TOKEN);
        Assert.assertNotNull(userId);
    }
}
