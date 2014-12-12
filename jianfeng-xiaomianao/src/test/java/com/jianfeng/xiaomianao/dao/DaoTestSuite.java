package com.jianfeng.xiaomianao.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    BootinitializationDaoTests.class,
    ClassificationDaoTests.class,
    NewsInfoDaoTests.class,
    TagDaoTests.class
    })
public class DaoTestSuite {

}
