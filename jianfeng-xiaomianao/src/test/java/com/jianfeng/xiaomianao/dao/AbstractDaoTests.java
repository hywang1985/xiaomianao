package com.jianfeng.xiaomianao.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("file:src/main/resources/META-INF/spring/applicationContext.xml")
public class AbstractDaoTests extends AbstractTransactionalJUnit4SpringContextTests {
}
