package com.jianfeng.xiaomianao.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration("file:src/main/resources/META-INF/spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class AbstractServiceTests extends AbstractTransactionalJUnit4SpringContextTests {
    
    protected static final String USER_ID = "100008";

    protected static final List<Integer> NEWS_IDS = new ArrayList<Integer>();

    static {
        NEWS_IDS.add(1);
        NEWS_IDS.add(2);
    }
}
