package com.jianfeng.xiaomianao.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    SubscribeServiceTests.class,
    FavoriteNewsServiceTests.class,
    SubscribeServiceTests.class,
    LikeNewsServiceTests.class
    })
public class ServicesTestSuite {

}
